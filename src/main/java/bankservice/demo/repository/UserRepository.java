package bankservice.demo.repository;

import bankservice.demo.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);

    @Query("select u from users u inner join fetch u.roles where u.phoneNumber = :number")
    Optional<User> findByPhoneNumber(String number);
}
