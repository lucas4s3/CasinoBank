import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private double accountBalance;
    private double depositLimit;
    private ArrayList<String> transactionHistory;

    public User( String username, String password, double accountBalance, double depositLimit) {
        this.username = username;
        this.password = password;
        this.accountBalance = accountBalance;
        this.depositLimit = depositLimit;
        this.transactionHistory = new ArrayList<>();
    }

    public void addTransaction(double amount, String transactionType) {
        String amountString = Double.toString(amount);
        if (transactionType == "DEPOSIT") {
            transactionHistory.add("+" + amountString);
        } else {
            transactionHistory.add("-" + amountString);
        }
    }

    public ArrayList<String> getTransactionHistory() {
        return this.transactionHistory;
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public double getBalance() { return accountBalance; }

    public double getDepositLimit() { return depositLimit; }

    public void setBalance(double balance) { this.accountBalance = balance; }

    public void setDepositLimit(double limit) { this.depositLimit = limit; }



    @Override
    public String toString() {
        return username + "," + password + "," + accountBalance + "," + depositLimit;
    }
}
