package bankservice.demo.service.impl;

import bankservice.demo.exception.DataProcessingException;
import bankservice.demo.model.Account;
import bankservice.demo.model.Transaction;
import bankservice.demo.model.dto.TransactionRequestDto;
import bankservice.demo.repository.TransactionRepository;
import bankservice.demo.service.AccountService;
import bankservice.demo.service.TransactionService;
import java.math.BigDecimal;
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
    private final CurrencyConverter currencyConverter;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  AccountService accountService,
                                  CurrencyConverter currencyConverter) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.currencyConverter = currencyConverter;
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
        if (accountFrom.getBalance().compareTo(transactionRequestDto.getAmount()) < 0) {
            throw new DataProcessingException(
                    "There is not enough money in the account for this transfer. Balance: "
                            + accountFrom.getBalance());
        }
        LocalDateTime timeOfTransfer = LocalDateTime.now();

        Transaction transactionFrom = new Transaction();
        transactionFrom.setAccountFrom(accountFrom);
        transactionFrom.setAccountTo(accountTo);
        transactionFrom.setType(Transaction.Type.OUTGOING);

        Transaction transactionTo = new Transaction();
        transactionTo.setAccountFrom(accountFrom);
        transactionTo.setAccountTo(accountTo);
        transactionTo.setType(Transaction.Type.INCOMING);

        accountFrom.setBalance(accountFrom.getBalance()
                .subtract(transactionRequestDto.getAmount()));
        transactionFrom.setAmount(transactionRequestDto.getAmount());
        if (!accountFrom.getCurrency().equals(accountTo.getCurrency())) {
            BigDecimal convertedAmount = currencyConverter.getRate(accountFrom.getCurrency(),
                    accountTo.getCurrency(),
                    timeOfTransfer.toLocalDate());
            BigDecimal transferAmount = transactionRequestDto.getAmount()
                    .multiply(convertedAmount);
            transactionRequestDto.setAmount(transferAmount);
        }
        transactionTo.setAmount(transactionRequestDto.getAmount());
        accountTo.setBalance(accountTo.getBalance().add(transactionRequestDto.getAmount()));

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
}
