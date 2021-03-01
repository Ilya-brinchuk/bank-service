package bankservice.demo.repository;

import bankservice.demo.model.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> getByRoleName(Role.RoleName roleName);
}
