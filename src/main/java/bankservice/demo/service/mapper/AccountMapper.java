package bankservice.demo.service.mapper;

import bankservice.demo.model.Account;
import bankservice.demo.model.Currency;
import bankservice.demo.model.dto.AccountRequestDto;
import bankservice.demo.model.dto.AccountResponseDto;
import bankservice.demo.service.MapperToDto;
import bankservice.demo.service.MapperToEntity;
import bankservice.demo.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper implements MapperToEntity<Account, AccountRequestDto>,
        MapperToDto<Account, AccountResponseDto> {
    private final UserService userService;

    public AccountMapper(UserService userService) {
        this.userService = userService;
    }

    @Override
    public AccountResponseDto mapToDto(Account account) {
        AccountResponseDto responseDto = new AccountResponseDto();
        responseDto.setAccountNumber(account.getAccountNumber());
        responseDto.setBalance(responseDto.getBalance());
        responseDto.setCurrency(responseDto.getCurrency());
        return responseDto;
    }

    @Override
    public Account mapToEntity(AccountRequestDto requestDto) {
        Account account = new Account();
        account.setAccountNumber(requestDto.getAccountNumber());
        account.setCurrency(Currency.valueOf(requestDto.getCurrency()));
        account.setUser(userService.get(requestDto.getUserId()));
        return account;
    }
}
