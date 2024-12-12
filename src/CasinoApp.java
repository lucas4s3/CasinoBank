import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CasinoApp {
    Scanner scanner = new Scanner(System.in);
    UserManager userManager = new UserManager();
    User currentuser;


    boolean isLoggedIn = false;

    public CasinoApp () {
        String userChoice = "";

        if (!isLoggedIn) {
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





    }

    // Logga in
    public void logInUser() {
        System.out.println("Användarnamn: ");
        String userName = scanner.next();
        System.out.println("Lösenord: ");
        String password = scanner.next();
        userManager.loginUser(userName, password);
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

    }

    // Uttag
    public void withdraw(double amount) {

    }

    // Insättning
    public void deposit(double amount) {

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
