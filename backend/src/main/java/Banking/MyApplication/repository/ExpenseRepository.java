package Banking.MyApplication.repository;

import Banking.MyApplication.model.CategorySummaryDTO;
import Banking.MyApplication.model.Expense;
import Banking.MyApplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {
//    custom method to fetch all expense for a specific user
    List<Expense> findByUser(User user);
//    total expense for summary
    @Query("SELECT COALESCE(SUM(e.amount),0) FROM Expense e WHERE e.user.id = :userId")
    double getTotalExpenseByUser(@Param("userId") Long userId);
//    category summary : return a list of category sumaryDTO(category , sum)
    @Query("SELECT  new Banking.MyApplication.model.CategorySummaryDTO(e.category,SUM(e.amount))"+
    "FROM Expense e WHERE e.user.id = :userId GROUP BY e.category")
    List<CategorySummaryDTO> getExpenseCategorySummary(@Param("userId") Long userId);

}
