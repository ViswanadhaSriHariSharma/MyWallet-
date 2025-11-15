package Banking.MyApplication.model.controller;

import Banking.MyApplication.model.BankAccount;
import Banking.MyApplication.model.User;
import Banking.MyApplication.repository.UserRepository;
import Banking.MyApplication.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
@CrossOrigin(origins = "http://localhost:3000")
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
//        add bank account
    @PostMapping("/add")
            public BankAccount addBankAccount(@RequestBody BankAccount bankAccount, @RequestParam Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new RuntimeException("User not found with ID" + userId);
        }
        bankAccount.setUser(user.get());
        return bankAccountService.saveAccount(bankAccount);
    }
//    get all bank accounts
    @GetMapping("/all")
      public List<BankAccount> getAllAccounts(){
            return bankAccountService.getAllAccounts();
    }
//    get all accounts for a specific user
    @GetMapping("/user/{userId}")
    public List<BankAccount> getAccountByUser(@PathVariable Long userId){
            Optional<User> user = userRepository.findById(userId);
            if(user.isEmpty()){
                throw new RuntimeException("User not found with id" + userId);
            }
            return bankAccountService.getAccountByUser(user.get());
    }
//    gget a single account by ID
    @GetMapping("/{id}")
    public Optional<BankAccount> getAccountById(@PathVariable Long id){
            return bankAccountService.getAccountById(id);
    }
//    Update an account
    @PutMapping("/update/{id}")
    public BankAccount updateAccount(@PathVariable Long id , @RequestBody BankAccount updatedAccount){
            return bankAccountService.updateAccount(id,updatedAccount);
    }
//    Delete account
    @DeleteMapping("/delete/{id}")
    public void delteAccount(@PathVariable Long id){
            bankAccountService.deleteAccount(id);
    }
}
