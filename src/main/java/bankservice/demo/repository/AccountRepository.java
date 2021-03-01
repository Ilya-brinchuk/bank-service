package bankservice.demo.repository;

import bankservice.demo.model.Account;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountByAccountNumber(String accountNumber);

    List<Account> findAllByUser_PhoneNumber(String phoneNumber);
}
