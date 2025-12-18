package Banking.MyApplication.model.controller;


import Banking.MyApplication.model.SummaryDTO;
import Banking.MyApplication.model.User;
import Banking.MyApplication.repository.UserRepository;
import Banking.MyApplication.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/summary")
@CrossOrigin(origins = "http://localhost:3000")
public class SummaryController {

    @Autowired
    private SummaryService summaryService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public SummaryDTO getUserSummary(Authentication authentication) {

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return summaryService.getSummaryByUser(user.getId());
    }
}
