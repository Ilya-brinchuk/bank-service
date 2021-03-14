package bankservice.demo.controller;

import bankservice.demo.model.Account;
import bankservice.demo.model.dto.AccountRequestDto;
import bankservice.demo.model.dto.AccountResponseDto;
import bankservice.demo.model.dto.TransactionRequestDto;
import bankservice.demo.service.AccountService;
import bankservice.demo.service.MapperToDto;
import bankservice.demo.service.MapperToEntity;
import bankservice.demo.service.TransactionService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final MapperToDto<Account, AccountResponseDto> mapperToDto;
    private final MapperToEntity<Account, AccountRequestDto> mapperToEntity;
    private final AccountService accountService;
    private final TransactionService transactionService;

    public AccountController(MapperToDto<Account, AccountResponseDto> mapperToDto,
                             MapperToEntity<Account, AccountRequestDto> mapperToEntity,
                             AccountService accountService,
                             TransactionService transactionService) {
        this.mapperToDto = mapperToDto;
        this.mapperToEntity = mapperToEntity;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @PostMapping
    public void create(@RequestBody AccountRequestDto requestDto) {
        accountService.save(mapperToEntity.mapToEntity(requestDto));
    }

    @GetMapping("/by-phone")
    public List<AccountResponseDto> findByPhoneNumber(@RequestParam String phoneNumber) {
        return accountService.getAllUserAccountsByUserPhoneNumber(phoneNumber)
                .stream()
                .map(mapperToDto::mapToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/transfer")
    public void transfer(@RequestBody TransactionRequestDto requestDto) {

    }
}
