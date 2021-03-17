package bankservice.demo.controller;

import bankservice.demo.model.Role;
import bankservice.demo.model.User;
import bankservice.demo.model.dto.UserRequestDto;
import bankservice.demo.model.dto.UserResponseDto;
import bankservice.demo.service.MapperToDto;
import bankservice.demo.service.MapperToEntity;
import bankservice.demo.service.RoleService;
import bankservice.demo.service.UserService;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final MapperToEntity<User, UserRequestDto> mapToEntity;
    private final MapperToDto<User, UserResponseDto> mapperToDto;
    private final RoleService roleService;

    public UserController(UserService userService,
                          MapperToEntity<User, UserRequestDto> userMapper,
                          MapperToDto<User, UserResponseDto> mapperToDto, RoleService roleService) {
        this.userService = userService;
        this.mapToEntity = userMapper;
        this.mapperToDto = mapperToDto;
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserRequestDto requestDto) {
        User user = mapToEntity.mapToEntity(requestDto);
        Role role = roleService.getByName("USER");
        user.setRoles(Set.of(role));
        userService.save(user);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public void update(@PathVariable Long userId,
                       @RequestBody UserRequestDto requestDto) {
        User user = mapToEntity.mapToEntity(requestDto);
        user.setId(userId);
        Role role = roleService.getByName("USER");
        user.setRoles(Set.of(role));
        userService.save(user);
    }

    @GetMapping("/{userId}")
    public UserResponseDto getById(@PathVariable Long userId) {
        return mapperToDto.mapToDto(userService.get(userId));
    }

    @GetMapping("/by-phone")
    public UserResponseDto findUserByPhoneNumber(@RequestParam String phoneNumber) {
        return mapperToDto.mapToDto(userService.getByPhoneNumber(phoneNumber));
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        userService.delete(userId);
    }
}
