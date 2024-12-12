public class User {
    private String username;
    private String password;
    private double balance;
    private double depositLimit;

    public User(String username, String password, double balance, double depositLimit) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.depositLimit = depositLimit;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public double getBalance() {
        return balance;
    }
    public double getDepositLimit() {
        return depositLimit;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setDepositLimit(double depositLimit) {
        this.depositLimit = depositLimit;
    }
    @Override
    public String toString() {
        return username + "," + password + "," + balance + "," + depositLimit;
    }
}
