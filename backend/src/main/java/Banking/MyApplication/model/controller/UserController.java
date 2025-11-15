package Banking.MyApplication.model.controller;
import Banking.MyApplication.model.User;
import Banking.MyApplication.service.UserService;
import jdk.jfr.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return service.createUser(user);
    }
    @GetMapping
    public List<User> getAllUsers(){
        return service.getallUsers();
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return service.getUserById(id);
    }
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user){
        return service.updateUser(id, user);
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id){
        service.deleteUser(id);
        return "User deleted with id : " + id;
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> loginUser(@RequestBody User user){
        User existingUser = service.findByUsername(user.getUsername());

        if(existingUser != null && existingUser.getPassword().equals(user.getPassword())){
            return ResponseEntity.ok(
                    Map.of(
                            "status" , "ok",
                            "userId", existingUser.getId(),
                            "username", existingUser.getUsername()
                    )
            );
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("status","error", "message", "Invaild credentials "));
        }
    }
}
