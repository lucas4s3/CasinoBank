package Window;

public class Customer {
    private String username;
    private String password;
    private int currentBalance;
    private int allTimeBalance;

    public Customer(String username, String password, int currentBalance) {
        this.username = username;
        this.password = password;
        this.currentBalance = currentBalance;
    }

    // Getter och setter metoder
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getCurrentBalance() {
        return currentBalance;
    }

    public int getAllTimeBalance() {
        return allTimeBalance;
    }

    @Override
    public String toString() {
        return username + ":" + password + ":" + currentBalance;
    }
}
