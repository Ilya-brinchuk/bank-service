package bankservice.demo.service.mapper;

import bankservice.demo.model.Transaction;
import bankservice.demo.model.dto.TransactionResponseDto;
import bankservice.demo.service.MapperToDto;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper implements MapperToDto<Transaction, TransactionResponseDto> {
    @Override
    public TransactionResponseDto mapToDto(Transaction transaction) {
        TransactionResponseDto responseDto = new TransactionResponseDto();
        responseDto.setAccountNumberFrom(transaction.getAccountFrom().getAccountNumber());
        responseDto.setAccountNumberTo(transaction.getAccountTo().getAccountNumber());
        responseDto.setAmount(transaction.getAmount());
        responseDto.setDateTime(transaction.getDateTime());
        responseDto.setType(transaction.getType().toString());
        responseDto.setId(transaction.getId());
        return responseDto;
    }
}
