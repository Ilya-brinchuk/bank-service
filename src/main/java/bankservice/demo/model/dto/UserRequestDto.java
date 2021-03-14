package bankservice.demo.model.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class UserRequestDto {
    private String name;
    private String phoneNumber;
    private LocalDate birthday;
    private String password;
}
