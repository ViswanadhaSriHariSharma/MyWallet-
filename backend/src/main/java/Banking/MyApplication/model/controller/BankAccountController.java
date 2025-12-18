package Banking.MyApplication.model.controller;

import Banking.MyApplication.model.BankAccount;
import Banking.MyApplication.model.User;
import Banking.MyApplication.repository.UserRepository;
import Banking.MyApplication.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
@CrossOrigin(origins = "http://localhost:3000"
            ,allowCredentials = "true")
@RestController
@RequestMapping("/api/bankaccounts")

public class BankAccountController {
        private final  BankAccountService bankAccountService;
        private final UserRepository userRepository;
        @Autowired
        public BankAccountController(BankAccountService bankAccountService, UserRepository userRepository) {
            this.bankAccountService = bankAccountService;
            this.userRepository = userRepository;
        }
//  ------------------      add bank account      ------------------
    @PostMapping("/add")
            public BankAccount addBankAccount(@RequestBody BankAccount bankAccount,
                                              Authentication authentication){
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        bankAccount.setUser(user);
        return bankAccountService.saveAccount(bankAccount);
    }
//  --------------------  get all bank accounts     ------------
    @GetMapping("/my")
      public List<BankAccount> getAllAccounts(Authentication authentication){
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return bankAccountService.getAccountByUser(user);
    }
////  ----check with frontend ----  get all accounts for a specific user ------------
//    @GetMapping("/user/{userId}")
//    public List<BankAccount> getAccountByUser(@PathVariable Long userId){
//            Optional<User> user = userRepository.findById(userId);
//            if(user.isEmpty()){
//                throw new RuntimeException("User not found with id" + userId);
//            }
//            return bankAccountService.getAccountByUser(user);
//    }
//    ------------------------------get a single account by ID----------------------------
    @GetMapping("/{id}")
    public BankAccount getAccountById(@PathVariable Long id,
                                      Authentication authentication){
            String username = authentication.getName();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(()-> new RuntimeException("User Not found"));

            BankAccount account = bankAccountService.getAccountById(id)
                    .orElseThrow(() -> new RuntimeException("Bank Account not found"));

            if(!account.getUser().getId().equals(user.getId())){
                throw new RuntimeException("Unauthorized: You Cannot update this account");
            }
            return account;
    }
// -------------------------------  Update bank account ------------------------------------
    @PutMapping("/update/{id}")
    public BankAccount updateAccount(@PathVariable Long id ,
                                     @RequestBody BankAccount updatedAccount,
                                     Authentication authentication){
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            BankAccount account = bankAccountService.getAccountById(id)
                    .orElseThrow(() -> new RuntimeException("Bank account not found"));

            if(!account.getUser().getId().equals(user.getId())){
                throw  new RuntimeException("Unauthorized: You cannot update this accound");
            }
            return bankAccountService.updateAccount(id, updatedAccount);
    }
//  -----------------------------------  Delete account ---------------------------------
    @DeleteMapping("/delete/{id}")
    public void delteAccount(@PathVariable Long id,Authentication authentication){
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BankAccount account = bankAccountService.getAccountById(id)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));

        if (!account.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized: You cannot delete this account");
        }
        bankAccountService.deleteAccount(id);
    }
}
