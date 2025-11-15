package Banking.MyApplication.service;

import Banking.MyApplication.model.Income;
import Banking.MyApplication.model.User;
import Banking.MyApplication.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;

    public IncomeServiceImpl(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @Override
    public Income saveIncome(Income income) {
        return incomeRepository.save(income);
    }

    @Override
    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    @Override
    public List<Income> getIncomesByUser(User user) {
        return incomeRepository.findByUser(user);
    }

    @Override
    public Optional<Income> getIncomeById(Long id) {
        return incomeRepository.findById(id);
    }

    @Override
    public Income updateIncome(Long id, Income updatedIncome) {
        return incomeRepository.findById(id)
                .map(existing -> {
                    existing.setSource(updatedIncome.getSource());
                    existing.setAmount(updatedIncome.getAmount());
                    existing.setDate(updatedIncome.getDate());
                    existing.setDescription(updatedIncome.getDescription());
                    return incomeRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException(("Income not found with id" + id)));
    }

    @Override
    public void deleteIncome(Long id) {
        if(!incomeRepository.existsById(id)){
            throw new RuntimeException("Income not found with id:" + id);
        }
         incomeRepository.deleteById(id);
    }
}
