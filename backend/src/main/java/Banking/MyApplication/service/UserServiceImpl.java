package Banking.MyApplication.service;

import Banking.MyApplication.model.User;
import Banking.MyApplication.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo){
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

    @Override
    public User updateUser(Long id, User user) {
        User existing = repo.findById(id).orElse(null);
        if(existing != null){
            existing.setUsername(user.getUsername());
            existing.setPassword(user.getPassword());
            return repo.save(existing);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        repo.deleteById(id);
    }

    @Override
    public boolean validateUser(String username , String password){
        User user = repo.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    @Override
    public User findByUsername(String username) {
        return repo.findByUsername(username);
    }
}
