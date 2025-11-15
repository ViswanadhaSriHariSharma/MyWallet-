package Banking.MyApplication.model.controller;


import Banking.MyApplication.model.CategorySummaryDTO;
import Banking.MyApplication.model.Expense;
import Banking.MyApplication.model.User;
import Banking.MyApplication.repository.ExpenseRepository;
import Banking.MyApplication.repository.UserRepository;
import Banking.MyApplication.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins =  "http://localhost:3000")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final UserRepository userRepository;

    @Autowired
    public ExpenseController(ExpenseService expenseService, UserRepository userRepository){
        this.expenseService = expenseService;
        this.userRepository = userRepository;
    }

    @Autowired
    private ExpenseRepository expenseRepository;

//    Add Expense
    @PostMapping("/add")
    public Expense addExpense(@RequestBody Expense expense, @RequestParam Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new RuntimeException("User not found with ID:" + userId);
        }
        expense.setUser(user.get());
        return expenseService.saveExpense(expense);
    }
//    get all expense
    @GetMapping("/all")
    public List<Expense> getAllExpenses(){
        return expenseService.getAllExpenses();
    }
//    get expenses for a specific user
    @GetMapping("/user/{userId}")
    public List<Expense> getExpenseByUser(@PathVariable Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new RuntimeException(("User not found with ID:" + userId));
        }
        return expenseService.getExpensesByUser(user.get());
    }
//    Update Expense
    @PutMapping("/update/{id}")
    public Expense updateExpense(@PathVariable Long id, @RequestBody Expense updatedExpense){
        return expenseService.updateExpense(id, updatedExpense);
    }
//    delete expense
    @DeleteMapping("/delete/{id}")
    public void deleteExpense(@PathVariable Long id){
        expenseService.deleteExpense(id);
    }

//    GET/api/expenses/category-summary/{userId}
    @GetMapping("/category-summary/{userId}")
    public List<CategorySummaryDTO> getExpenseCategorySummary(@PathVariable Long userId)
    {
        return expenseService.getExpenseCategorySummary(userId);
    }

}

