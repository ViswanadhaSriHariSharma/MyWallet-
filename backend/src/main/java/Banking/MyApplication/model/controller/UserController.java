package Banking.MyApplication.model.controller;

import Banking.MyApplication.model.User;
import Banking.MyApplication.repository.UserRepository;
import Banking.MyApplication.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // --------------------- Get Logged-in User Profile ------------------
    @GetMapping("/me")
    public User getMyProfile(Authentication authentication) {
        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // --------------------- Update Logged-in User ---------------------
//    @PutMapping("/update")
//    public User updateMyProfile(@RequestBody User updatedUser,
//                                Authentication authentication) {
//
//        String username = authentication.getName();
//
//        User existingUser = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        return userService.updateUser(existingUser.getId(), updatedUser);
//    }

    // --------------------- Delete Logged-in User ---------------------
    @DeleteMapping("/delete")
    public String deleteMyAccount(Authentication authentication) {

        String username = authentication.getName();

        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userService.deleteUser(existingUser.getId());

        return "Your account has been deleted.";
    }
}
