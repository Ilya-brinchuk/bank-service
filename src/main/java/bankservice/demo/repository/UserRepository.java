package bankservice.demo.repository;

import bankservice.demo.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from users u inner join fetch u.roles where u.id = :id")
    Optional<User> findByIdFetchRoles(@Param(value = "id") Long id);

    @Query("select u from users u inner join fetch u.roles where u.phoneNumber = :number")
    Optional<User> findUserByPhoneNumber(@Param(value = "number") String number);
}
