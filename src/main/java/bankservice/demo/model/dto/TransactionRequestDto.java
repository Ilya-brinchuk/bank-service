package bankservice.demo.model.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class TransactionRequestDto {
    private String accountFromNumber;
    private String accountToNumber;
    private BigDecimal amount;
}
