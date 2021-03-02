package bankservice.demo.repository;

import bankservice.demo.model.Account;
import bankservice.demo.model.Transaction;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("From transactions t where t.accountFrom = :account or t.accountTo = :account")
    List<Transaction> getAllTransactionByAccount(@Param("account") Account account,
                                                 Pageable pageable);

}
