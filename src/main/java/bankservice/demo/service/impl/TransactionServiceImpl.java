package bankservice.demo.service.impl;

import bankservice.demo.exception.DataProcessingException;
import bankservice.demo.model.Account;
import bankservice.demo.model.Currency;
import bankservice.demo.model.Transaction;
import bankservice.demo.model.dto.TransactionRequestDto;
import bankservice.demo.repository.TransactionRepository;
import bankservice.demo.service.AccountService;
import bankservice.demo.service.TransactionService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Override
    @Transactional
    public void transfer(TransactionRequestDto transactionRequestDto) {
        Account accountFrom =
                accountService
                        .findAccountByAccountNumber(transactionRequestDto.getAccountFromNumber());
        Account accountTo =
                accountService
                        .findAccountByAccountNumber(transactionRequestDto.getAccountToNumber());
        if (accountFrom.getBalance() < transactionRequestDto.getAmount()) {
            throw new DataProcessingException(
                    "There is not enough money in the account for this transfer. Balance: "
                            + accountFrom.getBalance());
        }
        Transaction transactionFrom = new Transaction();
        transactionFrom.setAccountFrom(accountFrom);
        transactionFrom.setAccountTo(accountTo);
        transactionFrom.setType(Transaction.Type.OUTGOING);

        Transaction transactionTo = new Transaction();
        transactionTo.setAccountFrom(accountFrom);
        transactionTo.setAccountTo(accountTo);
        transactionTo.setType(Transaction.Type.INCOMING);

        accountFrom.setBalance(accountFrom.getBalance() - transactionRequestDto.getAmount());
        transactionFrom.setAmount(transactionRequestDto.getAmount());
        if (!accountFrom.getCurrency().equals(accountTo.getCurrency())) {
            double convertedAmount = convertCurrency(transactionRequestDto.getAmount(),
                    accountFrom.getCurrency(),
                    accountTo.getCurrency());
            transactionRequestDto.setAmount(convertedAmount);
        }
        transactionTo.setAmount(transactionRequestDto.getAmount());
        accountTo.setBalance(accountTo.getBalance() + transactionRequestDto.getAmount());
        LocalDateTime timeOfTransfer = LocalDateTime.now();
        transactionFrom.setDateTime(timeOfTransfer);
        transactionTo.setDateTime(timeOfTransfer);
        transactionRepository.save(transactionFrom);
        transactionRepository.save(transactionTo);
        accountService.save(accountFrom);
        accountService.save(accountTo);
    }

    @Override
    public List<Transaction> getAllByAccount(int page, int size, Account account) {
        Sort sort = Sort.by("dateTime").ascending();
        PageRequest pageable = PageRequest.of(page, size, sort);
        return transactionRepository.getAllTransactionByAccount(account, pageable);
    }

    private double convertCurrency(double amount, Currency currencyFrom, Currency currencyTo) {
        String path = "https://api.exchangerate.host/convert?from=" + currencyFrom.toString()
                + "&to=" + currencyTo.toString() + "&amount=" + amount;
        try {
            URL url = new URL(path);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonElement root = JsonParser
                    .parseReader(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonObject = root.getAsJsonObject();
            return jsonObject.get("result").getAsDouble();
        } catch (IOException e) {
            throw new DataProcessingException("Can't get connection to the external API", e);
        }
    }
}
