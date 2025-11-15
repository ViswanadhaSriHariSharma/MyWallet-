package Banking.MyApplication.model.controller;


import Banking.MyApplication.model.BankAccount;
import Banking.MyApplication.model.Income;
import Banking.MyApplication.model.User;
import Banking.MyApplication.repository.BankAccountRepository;
import Banking.MyApplication.repository.UserRepository;
import Banking.MyApplication.service.IncomeService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/incomes")
@CrossOrigin(origins = "http://localhost:3000")
public class IncomeController {
    private final IncomeService incomeService;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;


    public IncomeController(IncomeService incomeService, UserRepository userRepository, BankAccountRepository bankAccountRepository) {
        this.incomeService = incomeService;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @PostMapping("/add")
    public Income addIncome(@RequestBody Income income,
                            @RequestParam Long userId,
                            @RequestParam Long bankAccountId){
        Optional<User> user = userRepository.findById(userId);
        Optional<BankAccount> bank = bankAccountRepository.findById(bankAccountId);

        if(user.isEmpty() || bank.isEmpty()){
            throw new RuntimeException("Invalid user or ban account ID");
        }
        income.setUser(user.get());
        income.setBankAccount(bank.get());
        income.setDate(LocalDate.now());
        return incomeService.saveIncome(income);
    }
    @GetMapping("/all")
    public List<Income> getAllIncomes(){
        return incomeService.getAllIncomes();
    }
    @GetMapping("/user/{userId}")
    public List<Income> getIncomesByUser(@PathVariable Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new RuntimeException("User not found");
        }
        return  incomeService.getIncomesByUser(user.get());
    }
    @DeleteMapping("/delete/{id}")
    public void deleteIncome(@PathVariable Long id){
        incomeService.deleteIncome(id);
    }
}
