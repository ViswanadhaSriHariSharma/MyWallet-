package Banking.MyApplication.model;

import java.util.List;

public class SummaryDTO {
    private double totalExpense;
    private double totalIncome;
    private double balance;

    public SummaryDTO(double totalExpense , double totalIncome){
        this.totalExpense =totalExpense;
        this.totalIncome = totalIncome;
        this.balance = totalIncome - totalExpense;
    }
    public double getTotalIncome(){
        return totalIncome;
    }
    public double getTotalExpense(){
        return totalExpense;
    }
    public double getBalance(){
        return balance;
    }
}
