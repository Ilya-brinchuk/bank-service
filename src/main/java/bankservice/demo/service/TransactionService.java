package bankservice.demo.service;

import bankservice.demo.model.Account;
import bankservice.demo.model.Transaction;
import bankservice.demo.model.dto.TransactionRequestDto;
import java.util.List;

public interface TransactionService {
    void transfer(TransactionRequestDto transactionRequestDto);

    List<Transaction> getAllByAccount(int page, int size, Account account);
}
