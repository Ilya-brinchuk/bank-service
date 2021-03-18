package bankservice.demo.model.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String name;
    private String phoneNumber;
    private LocalDate birthday;
}
