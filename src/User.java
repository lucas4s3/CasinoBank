public class User {
    private String username;
    private String password;
    private int balance;
    private int depositLimit;

    public User(String username, String password, int balance, int depositLimit) {
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
    public int getBalance() {
        return balance;
    }
    public int getDepositLimit() {
        return depositLimit;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setDepositLimit(int depositLimit) {
        this.depositLimit = depositLimit;
    }
    @Override
    public String toString() {
        return username + "," + password + "," + balance + "," + depositLimit;
    }
}
