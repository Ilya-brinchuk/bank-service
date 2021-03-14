package bankservice.demo.service.mapper;

import bankservice.demo.model.User;
import bankservice.demo.model.dto.UserRequestDto;
import bankservice.demo.model.dto.UserResponseDto;
import bankservice.demo.service.MapperToDto;
import bankservice.demo.service.MapperToEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements MapperToEntity<User, UserRequestDto>,
        MapperToDto<User, UserResponseDto> {
    @Override
    public User mapToEntity(UserRequestDto requestDto) {
        User user = new User();
        user.setName(requestDto.getName());
        user.setPhoneNumber(requestDto.getPhoneNumber());
        user.setDateOfBirth(requestDto.getDateOfBirth());
        user.setPassword(requestDto.getPassword());
        return user;
    }

    @Override
    public UserResponseDto mapToDto(User user) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setName(user.getName());
        responseDto.setBirthday(user.getDateOfBirth());
        responseDto.setPhoneNumber(user.getPhoneNumber());
        return responseDto;
    }
}
