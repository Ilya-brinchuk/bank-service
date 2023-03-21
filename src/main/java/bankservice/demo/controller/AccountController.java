package bankservice.demo.controller;

import bankservice.demo.model.Account;
import bankservice.demo.model.Transaction;
import bankservice.demo.model.dto.AccountRequestDto;
import bankservice.demo.model.dto.AccountResponseDto;
import bankservice.demo.model.dto.TransactionRequestDto;
import bankservice.demo.model.dto.TransactionResponseDto;
import bankservice.demo.service.AccountService;
import bankservice.demo.service.MapperToDto;
import bankservice.demo.service.MapperToEntity;
import bankservice.demo.service.TransactionService;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final MapperToDto<Account, AccountResponseDto> accountMapperToDto;
    private final MapperToEntity<Account, AccountRequestDto> accountMapperToEntity;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final MapperToDto<Transaction, TransactionResponseDto> transactionMapperToDto;

    public AccountController(MapperToDto<Account, AccountResponseDto> accountMapperToDto,
                             MapperToEntity<Account, AccountRequestDto> accountMapperToEntity,
                             AccountService accountService,
                             TransactionService transactionService,
                             MapperToDto<Transaction,
            TransactionResponseDto> transactionMapperToDto) {
        this.accountMapperToDto = accountMapperToDto;
        this.accountMapperToEntity = accountMapperToEntity;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.transactionMapperToDto = transactionMapperToDto;
    }

    @PostMapping
    public void create(@RequestBody AccountRequestDto requestDto) {
        Account account = accountMapperToEntity.mapToEntity(requestDto);
        account.setActive(true);
        accountService.save(account);
    }

    @GetMapping("/by-phone")
    public List<AccountResponseDto> findByPhoneNumber(@RequestParam String phoneNumber) {
        return accountService.getAllUserAccountsByUserPhoneNumber(phoneNumber)
                .stream()
                .map(accountMapperToDto::mapToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/transfer")
    public void transfer(@RequestBody TransactionRequestDto requestDto) {
        transactionService.transfer(requestDto);
    }

    @GetMapping("/{accountNumber}")
    public BigDecimal getBalance(@PathVariable String accountNumber) {
        return accountService.findAccountByAccountNumber(accountNumber).getBalance();
    }

    @GetMapping("/history/{accountNumber}")
    public List<TransactionResponseDto> getTransactionHistory(
            @PathVariable String accountNumber,
            @RequestParam(value = "page",
                    defaultValue = "0") Integer page,
            @RequestParam(value = "size",
                    defaultValue = "10") Integer size) {
        return transactionService.getAllByAccount(page,
                size,
                accountService.findAccountByAccountNumber(accountNumber))
                .stream()
                .map(transactionMapperToDto::mapToDto)
                .collect(Collectors.toList());


    }

    @PatchMapping("/{accountNumber}")
    public void blockAccount(@PathVariable String accountNumber) {
        accountService.deactivate(accountNumber);
    }
}
