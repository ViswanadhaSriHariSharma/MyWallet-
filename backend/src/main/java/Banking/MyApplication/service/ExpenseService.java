package Banking.MyApplication.service;

import Banking.MyApplication.model.CategorySummaryDTO;
import Banking.MyApplication.model.Expense;
import Banking.MyApplication.model.User;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {
    Expense saveExpense(Expense expense);
    List<Expense> getAllExpenses();
    List<Expense> getExpensesByUser(User user);
    Optional<Expense> getExpenseById(Long id);
    Expense updateExpense(Long id , Expense updatedExpense);
    void deleteExpense(Long id );
    List<CategorySummaryDTO> getExpenseCategorySummary(Long userId);
}
