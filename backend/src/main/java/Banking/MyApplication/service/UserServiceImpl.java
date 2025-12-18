package Banking.MyApplication.service;

import Banking.MyApplication.model.User;
import Banking.MyApplication.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User createUser(User user) {
        return repo.save(user);
    }

    @Override
    public List<User> getallUsers() {
        return repo.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return repo.findById(id).orElse(null);
    }

    private User getUserByUsername(String username) {
        return repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User updateUsernameByUsername(String username, String newUsername) {
        User user = getUserByUsername(username);
        user.setUsername(newUsername);
        return repo.save(user);
    }

    @Override
    public User updateFirstNameByUsername(String username, String firstName) {
        User user = getUserByUsername(username);
        user.setFirstName(firstName);
        return repo.save(user);
    }

    @Override
    public User updateLastNameByUsername(String username, String lastName) {
        User user = getUserByUsername(username);
        user.setLastName(lastName);
        return repo.save(user);
    }

    @Override
    public User updateEmailByUsername(String username, String email) {
        User user = getUserByUsername(username);
        user.setEmail(email);
        return repo.save(user);
    }

    @Override
    public User updateDobByUsername(String username, LocalDate dob) {
        User user = getUserByUsername(username);
        user.setDob(dob);
        return repo.save(user);
    }

    @Override
    public User updatePasswordByUsername(String username,
                                         String newPassword,
                                         PasswordEncoder encoder) {
        User user = getUserByUsername(username);
        user.setPassword(encoder.encode(newPassword));
        return repo.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        repo.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return getUserByUsername(username);
    }
}
