package bankservice.demo.service.impl;

import bankservice.demo.exception.DataProcessingException;
import bankservice.demo.model.User;
import bankservice.demo.repository.UserRepository;
import bankservice.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User get(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new DataProcessingException("Can't find user by this id: " + id));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new DataProcessingException(
                        "Can't find user by this phone number: " + phoneNumber)
                );
    }
}
