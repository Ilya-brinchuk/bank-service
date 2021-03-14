package bankservice.demo.controller;

import bankservice.demo.model.Account;
import bankservice.demo.model.Currency;
import bankservice.demo.model.Role;
import bankservice.demo.model.User;
import bankservice.demo.model.dto.TransactionRequestDto;
import bankservice.demo.service.AccountService;
import bankservice.demo.service.RoleService;
import bankservice.demo.service.TransactionService;
import bankservice.demo.service.UserService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final RoleService roleService;
    private final UserService userService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    @Autowired
    public DataInitializer(RoleService roleService,
                           UserService userService,
                           AccountService accountService,
                           TransactionService transactionService) {
        this.roleService = roleService;
        this.userService = userService;
        this.accountService = accountService;
        this.transactionService = transactionService;
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
        userService.save(userAdmin);
        Account accountAdmin = new Account();
        accountAdmin.setBalance(BigDecimal.valueOf(5000));
        accountAdmin.setActive(true);
        accountAdmin.setAccountNumber("111");
        accountAdmin.setUser(userAdmin);
        accountAdmin.setCurrency(Currency.UAH);
        accountService.save(accountAdmin);

        Account accountUser = new Account();
        accountUser.setBalance(BigDecimal.valueOf(300));
        accountUser.setActive(true);
        accountUser.setAccountNumber("222");
        accountUser.setUser(userAdmin);
        accountUser.setCurrency(Currency.USD);
        accountService.save(accountUser);
        TransactionRequestDto requestDto = new TransactionRequestDto();
        requestDto.setAmount(BigDecimal.valueOf(52.5));
        requestDto.setAccountFromNumber("222");
        requestDto.setAccountToNumber("111");
        transactionService.transfer(requestDto);
    }
}
