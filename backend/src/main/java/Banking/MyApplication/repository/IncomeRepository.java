package Banking.MyApplication.repository;

import Banking.MyApplication.model.Income;
import Banking.MyApplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByUser(User user);

    @Query("SELECT COALESCE(SUM(i.amount),0) FROM Income i where i.user.id = :userId")
    double getTotalIncomeByUser(@Param("userId") Long userId);

}

