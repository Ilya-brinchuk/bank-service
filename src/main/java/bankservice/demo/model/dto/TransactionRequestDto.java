package bankservice.demo.model.dto;

import lombok.Data;

@Data
public class TransactionRequestDto {
    private String accountFromNumber;
    private String accountToNumber;
    private double amount;
}
