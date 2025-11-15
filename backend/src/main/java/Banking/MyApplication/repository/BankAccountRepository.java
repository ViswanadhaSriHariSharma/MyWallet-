package Banking.MyApplication.repository;

import Banking.MyApplication.model.BankAccount;
import Banking.MyApplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {
    List<BankAccount> findByUser(User user);
}
