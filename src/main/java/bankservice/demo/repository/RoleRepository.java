package bankservice.demo.repository;

import bankservice.demo.model.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select r from roles r where r.roleName = :name")
    Optional<Role> getRoleByRoleName(@Param("name") String name);
}
