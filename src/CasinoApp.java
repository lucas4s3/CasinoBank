import java.util.ArrayList;
import java.util.Scanner;

public class CasinoApp {
    Blackjack blackJack;
    RouletteGame roulette;
    Scanner scanner = new Scanner(System.in);
    UserManager userManager = new UserManager();
    User currentuser;

    boolean isLoggedIn = false;
    boolean inGame = false;

    public CasinoApp() {
        displayNotLoggedIn();
    }

    // Logga in
    public void logInUser() {
        ArrayList<String> credentials = getCredentials();
        User user = userManager.loginUser(credentials.get(0), credentials.get(1));
        if (user != null) {
            currentuser = user;
            isLoggedIn = true;
            System.out.println("Välkommen " + currentuser.getUsername() + "!");
            System.out.println(currentuser);
            displayLoggedIn();
        } else {
            System.out.println("Inloggning misslyckades");
            displayNotLoggedIn();
        }
    }

    // Registrera konto
    public void registerUser() {
        ArrayList<String> credentials = getCredentials();
        userManager.registerUser(credentials.get(0), credentials.get(1));
        displayNotLoggedIn();
    }

    // Logga ut
    public void logOutUser() {
        currentuser = null;
        isLoggedIn = false;
        System.out.println("Du är utloggad.");
        displayNotLoggedIn();
    }

    // Uttag
    public void withdraw(int amount) {
        if (!isLoggedIn || currentuser == null) {
            System.out.println("Du måste vara inloggad för att göra ett uttag.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Beloppet måste vara större än 0.");
            return;
        }

        if (currentuser.getBalance() < amount) {
            System.out.println("Du har inte tillräckligt med saldo för detta uttag.");
            return;
        }

        currentuser.setBalance(currentuser.getBalance() - amount);
        userManager.saveUsers();
        System.out.println("Uttag lyckades! Nytt saldo: " + currentuser.getBalance() + " kr.");

    }

    // Insättning
    public void deposit(int amount) {
        if (!isLoggedIn || currentuser == null) {
            System.out.println("Du måste vara inloggad för att göra en insättning.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Beloppet måste vara större än 0.");
            return;
        }

        if (amount > currentuser.getDepositLimit()) {
            System.out.println("Beloppet överstiger din insättningsgräns på " + currentuser.getDepositLimit() + " kr.");
            return;
        }

        currentuser.setBalance(currentuser.getBalance() + amount);
        userManager.saveUsers();
        System.out.println("Insättning lyckades! Nytt saldo: " + currentuser.getBalance() + " kr.");

    }

    // Sätt insättningsgräns
    public void setDepositLimit() {
        if (!isLoggedIn || currentuser == null) {
            System.out.println("Du måste vara inloggad för att sätta insättningsgräns.");
            return;
        }

        System.out.println("Ange ny insättningsgräns: ");
        int DLimit = Integer.parseInt(scanner.nextLine());
        if (DLimit <= 0) {
            System.out.println("Insättningsgränsen måste vara större än 0.");
            return;
        }

        currentuser.setDepositLimit(DLimit);
        userManager.saveUsers();
        System.out.println("Ny insättningsgräns: " + currentuser.getDepositLimit() + " kr.");
    }

    // Gå in i spel
    public void enterGame() {
        inGame = true;
        displayInGame();

    }

    // Gå ut ur spel
    public void quitGame() {
        inGame = false;
        displayLoggedIn();

    }

    private ArrayList<String> getCredentials() {
        ArrayList<String> credentials = new ArrayList<>();
        System.out.println("Användarnamn: ");
        String userName = scanner.nextLine().trim();
        System.out.println("Lösenord: ");
        String password = scanner.nextLine().trim();

        credentials.add(userName);
        credentials.add(password);

        return credentials;

    }

    private void displayNotLoggedIn() {
        String userChoice;

        System.out.println("Logga in eller Registrera nytt konto: ");
        userChoice = scanner.nextLine().trim();

        if (userChoice.equalsIgnoreCase("Logga in")) {
            logInUser();
        } else if (userChoice.equalsIgnoreCase("Registrera")) {
            registerUser();
        } else {
            System.out.println("Ogiltigt val, försök igen.");
            displayNotLoggedIn();
        }
    }


    private void displayLoggedIn() {
        while (isLoggedIn && !inGame) {
            System.out.println("\nVälj ett alternativ:\n1. Sätt in pengar\n2. Ta ut pengar\n3. Sätt insättningsgräns\n4. Visa saldo\n5. Spela spel\n6. Logga ut");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Ange belopp att sätta in: ");
                    int depositAmount = Integer.parseInt(scanner.nextLine());
                    deposit(depositAmount);
                    break;
                case "2":
                    System.out.println("Ange belopp att ta ut: ");
                    int withdrawAmount = Integer.parseInt(scanner.nextLine());
                    withdraw(withdrawAmount);
                    break;
                case "3":
                    setDepositLimit();
                    break;
                case "4":
                    System.out.println("Ditt saldo: " + currentuser.getBalance() + " kr.");
                    break;
                case "5":
                    enterGame();
                    break;
                case "6":
                    logOutUser();
                    return;
                default:
                    System.out.println("Ogiltigt val, försök igen.");

            }
        }
    }

            private void displayInGame() {
                System.out.println("Du är i spelmenyn. Skriv '1' för BlackJack. '2' för Roulette. '3' för Slots");
                String input = scanner.nextLine();
                switch (input) {
                    case "1":
                            blackJack = new Blackjack(currentuser);

                    case "2":
                        roulette = new RouletteGame();
                }

            }

      public static void main(String[] args) {
        CasinoApp app = new CasinoApp();
      }

}
