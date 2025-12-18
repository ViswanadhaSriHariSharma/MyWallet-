package Banking.MyApplication.service;

import Banking.MyApplication.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    User createUser(User user);
    List<User> getallUsers();
    User getUserById(Long id);

    User updateUsernameByUsername(String username, String newUsername);
    User updateFirstNameByUsername(String username, String firstName);
    User updateLastNameByUsername(String username, String lastName);
    User updateEmailByUsername(String username, String email);
    User updateDobByUsername(String username, LocalDate dob);
    User updatePasswordByUsername(String username, String newPassword, PasswordEncoder encoder);

    void deleteUser(Long id);
    User findByUsername(String username);
}
