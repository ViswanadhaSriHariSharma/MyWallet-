package Banking.MyApplication.model.controller;


import Banking.MyApplication.model.BankAccount;

import Banking.MyApplication.model.Income;
import Banking.MyApplication.model.User;
import Banking.MyApplication.repository.BankAccountRepository;
import Banking.MyApplication.repository.UserRepository;
import Banking.MyApplication.service.IncomeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/incomes")
@CrossOrigin(origins = "http://localhost:3000",
                allowCredentials = "true")
public class IncomeController {

    private final IncomeService incomeService;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;


    public IncomeController(IncomeService incomeService, UserRepository userRepository, BankAccountRepository bankAccountRepository) {
        this.incomeService = incomeService;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
    }


    // Add income for authenticated user
    @PostMapping("/add")
    public Income addIncome(@RequestBody Income income,
                            @RequestParam Long bankAccoountId,
                            Authentication authentication,
                            HttpServletRequest request) {
        // DEBUG: print query string and param map
        System.out.println("=== addIncome DEBUG ===");
        System.out.println("Raw query string: " + request.getQueryString());
        System.out.println("bankAccoountId param (getParameter): " + request.getParameter("bankAccoountId"));
        System.out.println("bankAccountId param (getParameter): " + request.getParameter("bankAccountId"));
        System.out.println("========================");

        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not found"));
        BankAccount bank = bankAccountRepository.findById(bankAccoountId)
                .orElseThrow(() -> new RuntimeException("bank account not found"));
        income.setUser(user);
        income.setBankAccount(bank);
        income.setDate(LocalDate.now());

        return incomeService.saveIncome(income);
    }
    // Get all incomes for authenticated
    @GetMapping("/all")
    public List<Income> getAllIncomes(Authentication authentication){
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return incomeService.getIncomesByUser(user);

    }
//    delete income by ID
    @DeleteMapping("/delete/{id}")
    public void deleteIncome (@PathVariable Long id){
        incomeService.deleteIncome(id);
     }
}

