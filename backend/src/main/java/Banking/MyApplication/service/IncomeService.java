package Banking.MyApplication.service;

import Banking.MyApplication.model.Income;
import Banking.MyApplication.model.User;

import java.util.List;
import java.util.Optional;

public interface IncomeService {
    Income saveIncome(Income income);
    List<Income> getAllIncomes();
    List<Income> getIncomesByUser(User user);
    Optional<Income> getIncomeById(Long id);
    Income updateIncome(Long id, Income updatedIncome);
    void deleteIncome(Long id);
}
