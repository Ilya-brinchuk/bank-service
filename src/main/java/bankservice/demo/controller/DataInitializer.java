package bankservice.demo.controller;

import bankservice.demo.model.Role;
import bankservice.demo.model.User;
import bankservice.demo.service.RoleService;
import bankservice.demo.service.UserService;
import java.time.LocalDate;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private RoleService roleService;
    private UserService userService;

    @Autowired
    public DataInitializer(RoleService roleService,
                           UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    public void inject() {
        Role roleUser = new Role();
        roleUser.setRoleName(Role.RoleName.USER);
        Role roleAdmin = new Role();
        roleAdmin.setRoleName(Role.RoleName.ADMIN);
        roleService.save(roleUser);
        roleService.save(roleAdmin);
        User userAdmin = new User();
        userAdmin.setName("Bob");
        userAdmin.setPassword("2323");
        userAdmin.setPhoneNumber("380931112244");
        userAdmin.setDateOfBirth(LocalDate.of(1993, 8, 6));
        userAdmin.setRoles(Set.of(roleAdmin));
        userService.create(userAdmin);
    }
}
