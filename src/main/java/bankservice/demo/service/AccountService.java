package bankservice.demo.service;

import bankservice.demo.model.Account;
import java.util.List;

public interface AccountService {
    Account save(Account account);

    Account findAccountByAccountNumber(String accountNumber);

    List<Account> getAllUserAccountsByUserPhoneNumber(String phoneNumber);

    void deactivate(String accountNumber);
}
