import java.util.ArrayList;
import java.util.Scanner;

public class CasinoApp {
    Scanner scanner = new Scanner(System.in);
    UserManager userManager = new UserManager();
    User currentuser;

    boolean isLoggedIn = false;
    boolean inGame = false;

    public CasinoApp () {

        if (!isLoggedIn) {
            displayNotLoggedIn();
        } else {
            displayLoggedIn();
        }

    }

    // Logga in
    public void logInUser() {
        ArrayList<String> credentials = getCredentials();
        currentuser = userManager.loginUser(credentials.get(0), credentials.get(1));
        isLoggedIn = true;
        System.out.println(currentuser);
    }

    // Registrera konto
    public void registerUser() {
        ArrayList<String> credentials = getCredentials();
        userManager.registerUser(credentials.get(0), credentials.get(1));
    }

    // Logga ut
    public void logOutUser() {
        currentuser = null;
        System.out.println("Du är utloggad.");
    }

    // Uttag
    // Uttag
    public void withdraw(int amount) {
        if (amount <= 0) {
            System.out.println("Beloppet måste vara större än 0.");
            return;
        }

        if (currentuser.getBalance() >= amount) {
            currentuser.setBalance(currentuser.getBalance() - amount);
            System.out.println("Du har tagit ut " + amount + " kr. Din nya balans är: " + currentuser.getBalance() + " kr.");
        } else {
            System.out.println("Otillräcklig balans för att göra detta uttag.");
        }
    }

    // Insättning
    // Insättning
    public void deposit(int amount) {
        if (amount <= 0) {
            System.out.println("Beloppet måste vara större än 0.");
            return;
        }

        if (currentuser.getBalance() + amount <= currentuser.getDepositLimit()) {
            currentuser.setBalance(currentuser.getBalance() + amount);
            System.out.println("Du har satt in " + amount + " kr. Din nya balans är: " + currentuser.getBalance() + " kr.");
        } else {
            System.out.println("Beloppet överskrider din insättningsgräns. Försök med ett lägre belopp.");
        }
    }


    // Sätt insättningsgräns
    public void setDepositLimit() {
        System.out.println("vad ska du ha för insättningsgräns?");
        int DLimit = Integer.parseInt(scanner.nextLine());
        currentuser.setDepositLimit(DLimit);
    }

    // Gå in i spel
    public void enterGame() {

    }

    // Gå ut ur spel
    public void quitGame() {

    }

    private ArrayList<String> getCredentials() {
        ArrayList<String> credentials = new ArrayList<>();
        System.out.println("Användarnamn: ");
        String userName = scanner.next();
        System.out.println("Lösenord: ");
        String password = scanner.next();

        credentials.add(userName);
        credentials.add(password);

        return credentials;

    }

    private void displayNotLoggedIn() {
        String userChoice = "";

        System.out.println("Logga in eller Registrera nytt konto: ");
        userChoice = scanner.nextLine();

        if (userChoice.equalsIgnoreCase("Logga in")) {
            logInUser();
            isLoggedIn = true;
        } else if (userChoice.equalsIgnoreCase("Registrera")) {
            registerUser();
            isLoggedIn = true;
        }
    }

    private void displayLoggedIn() {

    }

    private void displayInGame() {

    }

    public static void main(String[] args) {
        CasinoApp app = new CasinoApp();
    }


}