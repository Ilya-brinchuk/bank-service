package bankservice.demo.service;

import bankservice.demo.model.User;

public interface UserService {
    User save(User user);

    User update(User user);

    User get(Long id);

    void delete(Long id);

    User getByPhoneNumber(String phoneNumber);
}
