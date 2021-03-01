package bankservice.demo.service.impl;

import bankservice.demo.exception.DataProcessingException;
import bankservice.demo.model.Role;
import bankservice.demo.repository.RoleRepository;
import bankservice.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getByName(Role.RoleName roleName) {
        return roleRepository.getByRoleName(roleName)
                .orElseThrow(() ->
                        new DataProcessingException("Can't find role by this name: " + roleName));
    }
}
