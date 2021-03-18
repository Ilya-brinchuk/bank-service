package bankservice.demo.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TransactionResponseDto {
    private Long id;
    private String accountNumberFrom;
    private String accountNumberTo;
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private String type;
}
