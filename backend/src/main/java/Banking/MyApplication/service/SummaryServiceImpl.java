package Banking.MyApplication.service;

import Banking.MyApplication.model.SummaryDTO;
import Banking.MyApplication.repository.ExpenseRepository;
import Banking.MyApplication.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SummaryServiceImpl implements SummaryService {
    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public SummaryDTO getSummaryByUser(Long userId){
        double totalIncome = incomeRepository.getTotalIncomeByUser(userId);
        double totalExpense = expenseRepository.getTotalExpenseByUser(userId);
        return new SummaryDTO(totalIncome, totalExpense);
    }
}
