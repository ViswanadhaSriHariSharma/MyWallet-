package Banking.MyApplication.model;

import ch.qos.logback.classic.pattern.ClassOfCallerConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id ;

    private String category ;
    private String description ;
    private double amount ;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user ;

    @ManyToOne
    @JoinColumn(name = "bank_account_id", nullable = false)
    private BankAccount bankAccount;

    public Expense(){}

    public Expense(String category, String description , double amount , LocalDate date , User user, BankAccount bankAccount){
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.date = date ;
        this.user = user;
        this.bankAccount = bankAccount;
    }
//    getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public  BankAccount getBankAccount(){
        return  bankAccount;
    }
    public void setBankAccount(BankAccount bankAccount){
        this.bankAccount = bankAccount;
    }
}
