import java.util.ArrayList;

public class CasinoApp {
    private UserManager userManager;
    private User currentUser;
    private boolean isLoggedIn;
    private boolean inGame;
    private Game currentGame;

    public CasinoApp(UserManager userManager) {
        this.userManager = userManager;
        this.isLoggedIn = false;
        this.inGame = false;
    }

    public boolean loginUser(String username, String password) {
        User user = userManager.findUser(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            isLoggedIn = true;
            return true;
        }
        return false;
    }

    public boolean registerUser(String username, String password) {
        if (userManager.findUser(username) != null) {
            return false;
        }
        User newUser = new User(username, password, 0.0, 9999999.0);
        userManager.addUser(newUser);
        return true;
    }

    public void logOutUser() {
        currentUser = null;
        isLoggedIn = false;
    }

    public void playGame(String chosenGame) throws IllegalArgumentException {
        switch (chosenGame.toUpperCase()) {
            case "BLACKJACK" -> currentGame = GameFactory.getGame(GameType.BLACKJACK, currentUser);
            case "ROULETTE" -> currentGame = GameFactory.getGame(GameType.ROULETTE, currentUser);
            default -> throw new IllegalArgumentException("Ogiltigt spel.");
        }
        currentGame.startGame();
        inGame = true;
    }

    public void quitGame() {
        if (currentGame != null) {
            currentGame.closeGame();
        }

        inGame = false;
        currentGame = null;
        System.out.println("Du har avslutat spelet.");
    }

    public void addFunds(double amount) {
        if (amount <= 0) {
            System.out.println("Beloppet måste vara större än 0.");
            return;
        }

        if (amount > currentUser.getDepositLimit()) {
            System.out.println("Beloppet överstiger din insättningsgräns på " + currentUser.getDepositLimit());
            return;
        }

        currentUser.setBalance(currentUser.getBalance() + amount);
        userManager.saveUsers();

        System.out.println("Insättning lyckades! Nytt saldo: " + currentUser.getBalance());
        currentUser.addTransaction(amount, "DEPOSIT");
    }

    public void withdrawFunds(double amount) {

        if (amount <= 0) {
            System.out.println("Beloppet måste vara större än 0.");
            return;
        }

        if (currentUser.getBalance() < amount) {
            System.out.println("Otillräckligt saldo.");
            return;
        }

        currentUser.setBalance(currentUser.getBalance() - amount);
        userManager.saveUsers();

        System.out.println("Uttag lyckades! Nytt saldo: " + currentUser.getBalance());
        currentUser.addTransaction(amount, "WITHDRAW");
    }

    public String viewAccountInfo() {
        return "Användare: " + currentUser.getUsername() + ", Saldo: " + currentUser.getBalance() +
                ", Insättningsgräns: " + currentUser.getDepositLimit();
    }

    public void viewTransactionsHistory() {
        ArrayList<String> transactions =
                currentUser.getTransactionHistory();

        System.out.println("Senaste transaktioner: ");
        transactions.forEach(transaction -> {
            System.out.println(transaction);
        });

    }

    public void setDepositLimit(double limit) {

        if (limit <= 0) {
            System.out.println("Gränsen måste vara större än 0.");
            return;
        }

        currentUser.setDepositLimit(limit);
        userManager.saveUsers();
        System.out.println("Ny insättningsgräns: " + currentUser.getDepositLimit());
    }

    // Getters - - - - - -
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public boolean isInGame() {
        return inGame;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
