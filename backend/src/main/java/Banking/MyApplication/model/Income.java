package Banking.MyApplication.model;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "incomes")
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String source ;
    private double amount;
    private LocalDate date;
    private String description;
    //Many incomes can belong to that one user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    Many incomes can belong to one bank account
    @ManyToOne
    @JoinColumn(name = "bank_account_id",nullable = false)
    private  BankAccount bankAccount;

//    contructors
    public Income() {}

    public Income(Long id, String source, double amount, LocalDate date, String description, User user, BankAccount bankAccount) {
        this.id = id;
        this.source = source;
        this.amount = amount;
        this.date = date;
        this.description = description;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
