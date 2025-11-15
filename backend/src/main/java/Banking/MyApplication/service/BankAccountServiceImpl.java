package Banking.MyApplication.service;

import Banking.MyApplication.model.BankAccount;
import Banking.MyApplication.model.User;
import Banking.MyApplication.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BankAccountServiceImpl implements BankAccountService{
    private final BankAccountRepository bankAccountRepository;
    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BankAccount saveAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public List<BankAccount> getAllAccounts() {
        return bankAccountRepository.findAll();
    }

    @Override
    public Optional<BankAccount> getAccountById(Long id) {
        return bankAccountRepository.findById(id);
    }

    @Override
    public List<BankAccount> getAccountByUser(User user) {
        return bankAccountRepository.findByUser(user);
    }

    @Override
    public BankAccount updateAccount(Long id, BankAccount updatedAccount) {
        return bankAccountRepository.findById(id)
        .map(account -> {
            account.setBankName(updatedAccount.getBankName());
            account.setAccountNumber(updatedAccount.getAccountNumber());
            account.setAccountType(updatedAccount.getAccountType());
            account.setBalance(updatedAccount.getBalance());
            return bankAccountRepository.save(account);
        })
                .orElseThrow(() -> new RuntimeException("Account not found with id " + id));
    }

    @Override
    public void deleteAccount(Long id) {
        bankAccountRepository.deleteById(id);
    }
}
