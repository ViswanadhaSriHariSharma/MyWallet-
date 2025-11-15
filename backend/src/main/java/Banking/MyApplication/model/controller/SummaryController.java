package Banking.MyApplication.model.controller;


import Banking.MyApplication.model.SummaryDTO;
import Banking.MyApplication.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/summary")
@CrossOrigin(origins = "http://localhost:3000")
public class SummaryController {
    @Autowired
    private SummaryService summaryService;

    @GetMapping("/{userId}")
    public SummaryDTO getUserSummary(@PathVariable Long userId){
       return summaryService.getSummaryByUser(userId);
    }
}
