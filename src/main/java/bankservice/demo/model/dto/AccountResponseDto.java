package bankservice.demo.model.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class AccountResponseDto {
    private String accountNumber;
    private String currency;
    private BigDecimal balance;
}
