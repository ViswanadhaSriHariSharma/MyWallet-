package Banking.MyApplication.service;

import Banking.MyApplication.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> getallUsers();
    User getUserById(Long id);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    boolean validateUser(String username, String password);
    User findByUsername(String username);
}

