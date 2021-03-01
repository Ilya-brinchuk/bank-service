package bankservice.demo.service;

import bankservice.demo.model.Role;

public interface RoleService {
    Role save(Role role);

    Role getByName(Role.RoleName roleName);
}
