import java.util.Scanner;

public class CasinoApp {
    Scanner scanner = new Scanner(System.in);
    UserManager userManager = new UserManager();
    User currentuser;
    int balance;


    boolean isLoggedIn = false;

    public CasinoApp () {
        String userChoice;

        if (!isLoggedIn) {
            System.out.println("Logga in eller Registrera nytt konto: ");
            userChoice = scanner.nextLine().trim();

            if (userChoice.equalsIgnoreCase("Logga in")) {
                logInUser();
                isLoggedIn = true;
            } else if (userChoice.equalsIgnoreCase("Registrera")) {
                registerUser();
                isLoggedIn = true;
            }
        }





    }

    // Logga in
    public void logInUser() {
        System.out.println("Användarnamn: ");
        String userName = scanner.next().trim();
        System.out.println("Lösenord: ");
        String password = scanner.next().trim();
        User user = userManager.loginUser(userName, password);
        if (user != null) {
            currentuser = user;
            balance = currentuser.getBalance();
            isLoggedIn = true;
        } else {
            System.out.println("Inloggning misslyckades, försök igen.");
        }
    }

    // Registrera konto
    public void registerUser() {
        System.out.println("Användarnamn: ");
        String userName = scanner.next();
        System.out.println("Lösenord: ");
        String password = scanner.next();
        userManager.registerUser(userName, password);
    }

    // Logga ut
    public void logOutUser() {
        currentuser = null;
        System.out.println("Du är utloggad.");
    }

    // Uttag
    public void withdraw(int amount) {
    }

    // Insättning
    public void deposit(int amount) {

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

    public static void main(String[] args) {
        CasinoApp app = new CasinoApp();
    }


}
