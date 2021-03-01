package bankservice.demo.service.impl;

import bankservice.demo.exception.DataProcessingException;
import bankservice.demo.model.Account;
import bankservice.demo.repository.AccountRepository;
import bankservice.demo.service.AccountService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account findAccountByAccountNumber(String accountNumber) {
        return accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new DataProcessingException(
                        "Can't find account by this number: " + accountNumber));
    }

    @Override
    public List<Account> getAllUserAccountsByUserPhoneNumber(String phoneNumber) {
        return accountRepository.findAllByUser_PhoneNumber(phoneNumber);
    }

    @Override
    public void deactivate(String accountNumber) {
        Account account = accountRepository.findAccountByAccountNumber(accountNumber).orElseThrow(
                () -> new DataProcessingException(
                "Can't find account by this number: " + accountNumber));
        account.setActive(false);
        accountRepository.save(account);
    }
}
