package Banking.MyApplication.service;

import Banking.MyApplication.model.BankAccount;
import Banking.MyApplication.model.User;

import java.util.List;
import java.util.Optional;

public interface BankAccountService {
    BankAccount saveAccount(BankAccount bankAccount);
    List<BankAccount> getAllAccounts();
    Optional<BankAccount> getAccountById(Long id);
    List<BankAccount> getAccountByUser(User user);
    BankAccount updateAccount(Long id , BankAccount updatedAccount);
    void deleteAccount(Long id );
}
