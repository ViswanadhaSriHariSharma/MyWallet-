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


    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user ;

    public Expense(){}

    public Expense(String category, String description , double amount , LocalDate date , User user){
        this.category = category;
        this.description = category;
        this.amount = amount;
        this.date = date ;
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
}
