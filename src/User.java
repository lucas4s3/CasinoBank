public class User {
    private int id;
    private String username;
    private String password;
    private double accountBalance;
    private double depositLimit;

    public User(int id, String username, String password, double accountBalance, double depositLimit) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.accountBalance = accountBalance;
        this.depositLimit = depositLimit;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public double getBalance() { return accountBalance; }
    public double getDepositLimit() { return depositLimit; }

    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setBalance(double balance) { this.accountBalance = balance; }
    public void setDepositLimit(double limit) { this.depositLimit = limit; }

    @Override
    public String toString() {
        return id + "," + username + "," + password + "," + accountBalance + "," + depositLimit;
    }
}
