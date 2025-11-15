package Banking.MyApplication.service;


import Banking.MyApplication.model.CategorySummaryDTO;
import Banking.MyApplication.model.Expense;
import Banking.MyApplication.model.User;
import Banking.MyApplication.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository){
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public List<Expense> getExpensesByUser(User user) {
        return expenseRepository.findByUser(user);
    }

    @Override
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    @Override
    public Expense updateExpense(Long id, Expense updatedExpense) {
        return expenseRepository.findById(id)
                .map(expense -> {
                    expense.setCategory(updatedExpense.getCategory());
                    expense.setDescription(updatedExpense.getDescription());
                    expense.setAmount(updatedExpense.getAmount());
                    expense.setDate(updatedExpense.getDate());
                    return expenseRepository.save(expense);
                })
                .orElseThrow(() -> new RuntimeException("Expense not found with ID" + id));
    }

    @Override
    public void deleteExpense(Long id) {
    expenseRepository.deleteById(id);
    }

//


    @Override
    public List<CategorySummaryDTO> getExpenseCategorySummary(Long userId) {
        return expenseRepository.getExpenseCategorySummary(userId);
    }
}

