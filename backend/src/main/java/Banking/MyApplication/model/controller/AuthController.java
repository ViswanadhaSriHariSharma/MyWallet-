package Banking.MyApplication.model.controller;
import Banking.MyApplication.model.User;
import Banking.MyApplication.repository.UserRepository;
import Banking.MyApplication.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private Banking.MyApplication.CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserRepository userRepository;

//   --------------------Login---------------
    @PostMapping("/login")
    public Map<String,String> login(@RequestBody User user, HttpServletResponse response) throws Exception{

        try {
//            Authenticate username and password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    )
            );
        } catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password",e);
        }
        final UserDetails userDetails = customUserDetailsService
                .loadUserByUsername(user.getUsername());

        // Generate JWT token
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

// Manually create proper SameSite=None cookie
        Cookie cookie = new Cookie("jwt" , jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(24*60*60);
        cookie.setAttribute("SameSite","Lax");
        response.addCookie(cookie);

// response body
        return Map.of("status", "ok");


    }
//    ----------------REGISTER---------------------
    @PostMapping("/register")
    public String register(@RequestBody User user){
//        In production , hash the password using the Bcrypt
        userRepository.save(user);
        return "User Registered succesfully";
    }

//    ------------------------CHECK ----------------------

    @GetMapping("/check")
    public ResponseEntity<?> checkAuth(HttpServletRequest request){
        String token = null;

        if(request.getCookies() != null){
            for(Cookie cookie : request.getCookies()){
                if(cookie.getName().equals("jwt")){
                    token = cookie.getValue();
                }
            }
        }
        if(token == null){
            return ResponseEntity.status(401).body("No Token");
        }
        try{
            String username = jwtUtil.extractUsername(token); // token valid
            return ResponseEntity.ok(Map.of("status","Authenticated","username",username));
        } catch (Exception e){
            return  ResponseEntity.status(401).body("Invalid token");
        }
    }


//    ------------------LOGOUT--------------------
    @PostMapping("/logout")
    public  String logout(HttpServletResponse response){
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return "Logged out ";
    }

}
