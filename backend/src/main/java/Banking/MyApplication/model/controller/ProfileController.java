package Banking.MyApplication.model.controller;

import Banking.MyApplication.model.User;
import Banking.MyApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PutMapping("/update/username")
    public User updateUsername(@RequestBody Map<String, String> body,
                               Authentication authentication) {
        String username = authentication.getName();
        return userService.updateUsernameByUsername(username, body.get("username"));
    }

    @PutMapping("/update/firstName")
    public User updateFirstName(@RequestBody Map<String, String> body,
                                Authentication authentication) {
        String username = authentication.getName();
        System.out.println("updateFirstName called for user: " + username + ", payload: " + body);
        return userService.updateFirstNameByUsername(username, body.get("firstName"));
    }

    @PutMapping("/update/lastName")
    public User updateLastName(@RequestBody Map<String, String> body,
                               Authentication authentication) {
        String username = authentication.getName();
        System.out.println("updateFirstName called for user: " + username + ", payload: " + body);

        return userService.updateLastNameByUsername(username, body.get("lastName"));
    }

    @PutMapping("/update/email")
    public User updateEmail(@RequestBody Map<String, String> body,
                            Authentication authentication) {
        String username = authentication.getName();
        return userService.updateEmailByUsername(username, body.get("email"));
    }

    @PutMapping("/update/dob")
    public User updateDob(@RequestBody Map<String, String> body,
                          Authentication authentication) {
        String username = authentication.getName();
        LocalDate dob = LocalDate.parse(body.get("dob"));
        return userService.updateDobByUsername(username, dob);
    }

    @PutMapping("/update/password")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> body,
                               Authentication authentication) {
        String username = authentication.getName();
        String newPassword = body.get("newPassword");

        if (newPassword == null || newPassword.trim().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Password cannot be empty");
        }

        if(newPassword.length() < 8){
            return ResponseEntity
                    .badRequest()
                    .body("Password must be at least 8 characters");
        }

        userService.updatePasswordByUsername(username, newPassword , passwordEncoder);
        return ResponseEntity.ok("Password updated successfully");

    }
}
