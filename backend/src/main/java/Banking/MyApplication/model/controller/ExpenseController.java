package Banking.MyApplication.model.controller;


import Banking.MyApplication.model.BankAccount;
import Banking.MyApplication.model.CategorySummaryDTO;
import Banking.MyApplication.model.Expense;
import Banking.MyApplication.model.User;
import Banking.MyApplication.repository.BankAccountRepository;
import Banking.MyApplication.repository.ExpenseRepository;
import Banking.MyApplication.repository.UserRepository;
import Banking.MyApplication.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins =  "http://localhost:3000"
        , allowCredentials = "true",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api/expenses")

public class ExpenseController {
    private final ExpenseService expenseService;
    private final UserRepository userRepository;
    private  final BankAccountRepository bankAccountRepository;
    @Autowired
    public ExpenseController(ExpenseService expenseService, UserRepository userRepository, BankAccountRepository bankAccountRepository){
        this.expenseService = expenseService;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Autowired
    private ExpenseRepository expenseRepository;

// --------------------Add Expense for authenticated user--------------------
    @PostMapping("/add")
    public Expense addExpense(@RequestBody Expense expense,
                              @RequestParam Long bankAccountId,
                              Authentication authentication){
      String username = authentication.getName();
      User user = userRepository.findByUsername(username)
              .orElseThrow(() -> new RuntimeException("User not found"));
        BankAccount bank = bankAccountRepository.findById(bankAccountId)
                        .orElseThrow(() -> new RuntimeException("User not found"));
      //verify ownership
        if(!bank.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized: This bank does not belong to you ");
        }
        BigDecimal expenseAmount = BigDecimal.valueOf(expense.getAmount());
        if(bank.getBalance().compareTo(expenseAmount) < 0){
            throw new RuntimeException("Insufficient funds in bank account");
        }
        bank.setBalance(bank.getBalance().subtract(expenseAmount));
        bankAccountRepository.save(bank);

        expense.setUser(user);
        expense.setBankAccount(bank);
      return expenseService.saveExpense(expense);
    }
//   ----------------- get all expense for authenticated user--------------
    @GetMapping("/my")
    public List<Expense> getAllExpenses(Authentication authentication){
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not found"));
        return expenseService.getExpensesByUser(user);
    }
//  -------------------  get expenses for a specific user(Not used bad idea)----------------------
//    @GetMapping("/user/{userId}")
//    public List<Expense> getExpenseByUser(@PathVariable Long userId){
//        Optional<User> user = userRepository.findById(userId);
//        if(user.isEmpty()){
//            throw new RuntimeException(("User not found with ID:" + userId));
//        }
//        return expenseService.getExpensesByUser(user.get());
//    }
//  --------------------------  Update Expense -----------------------
    @PutMapping("/update/{id}")
    public Expense updateExpense(@PathVariable Long id,
                                 @RequestBody Expense updatedExpense,
                                 @RequestParam(required = false) Long bankAccountId,
                                 Authentication authentication){
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)

                .orElseThrow(() -> new RuntimeException("User not found"));
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        if(!expense.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Unauthorized : You cannot update this expense");
        }
        BankAccount oldBank = expense.getBankAccount();
        BankAccount newBank = oldBank;

        // optional : change bank
        if (bankAccountId != null && !bankAccountId.equals(oldBank.getId())) {
            newBank = bankAccountRepository.findById(bankAccountId)
                    .orElseThrow(() -> new RuntimeException("Bank account not found"));

            if (!newBank.getUser().getId().equals(user.getId())) {
                throw new RuntimeException("Unauthorized: Bank account does not belong to you");
            }

            // Refund old bank
            oldBank.setBalance(oldBank.getBalance().add(BigDecimal.valueOf(expense.getAmount())));
            bankAccountRepository.save(oldBank);
        }

        // Adjust new bank balance
        BigDecimal oldAmount = BigDecimal.valueOf(expense.getAmount());
        BigDecimal newAmount = BigDecimal.valueOf(updatedExpense.getAmount());
        BigDecimal diff = newAmount.subtract(oldAmount);

        if (diff.compareTo(newBank.getBalance()) > 0) {
            throw new RuntimeException("Insufficient funds in selected bank account");
        }

        newBank.setBalance(newBank.getBalance().subtract(diff));
        bankAccountRepository.save(newBank);

        // Update expense
        expense.setBankAccount(newBank);
        expense.setCategory(updatedExpense.getCategory());
        expense.setDescription(updatedExpense.getDescription());
        expense.setAmount(updatedExpense.getAmount());
        expense.setDate(updatedExpense.getDate());

        return expenseService.updateExpense(id, updatedExpense);
    }
// -------------------------   delete expense --------------------
    @DeleteMapping("/delete/{id}")
    public void deleteExpense(@PathVariable Long id, Authentication authentication){

        System.out.println("➡ DEBUG: Delete request received for ID = " + id);
        String username = authentication.getName();
        System.out.println("➡ DEBUG: Authenticated user = " + username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> {
                    System.out.println("❌ DEBUG: Expense with ID \" + id + \" NOT FOUND in DB");
                    return new RuntimeException("Expense not found");
                });
        // Debug: print the found expense
        System.out.println("✔ DEBUG: Expense FOUND → ID: " + expense.getId() +
                ", Amount: " + expense.getAmount() +
                ", User ID: " + expense.getUser().getId());

        if (!expense.getUser().getId().equals(user.getId())) {
            System.out.println("DEBUG: Expense belongs to another user!");
            throw new RuntimeException("Unauthorized: You cannot delete this expense");
        }
        // Refund bank balance
         BankAccount bank = expense.getBankAccount();
        bank.setBalance(bank.getBalance().add(BigDecimal.valueOf(expense.getAmount())));
        bankAccountRepository.save(bank);
        expenseService.deleteExpense(id);
    }

//  --------------------  GET/api/expenses/category-summary/{userId} ----------------------------
    @GetMapping("/category-summary")
    public List<CategorySummaryDTO> getExpenseCategorySummary(Authentication authentication)
    {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not found"));
        return expenseService.getExpenseCategorySummary(user.getId());
    }
}

