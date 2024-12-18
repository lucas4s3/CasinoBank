import java.util.Scanner;

public class CasinoUI {
    static final UserManager userManager = UserManager.getInstance();
    private final CasinoApp app;
    private final Scanner scanner;
    private boolean running;

    public CasinoUI() {
        app = new CasinoApp(userManager);
        scanner = new Scanner(System.in);
        running = true;
    }

    public static void main(String[] args) {
        CasinoUI ui = new CasinoUI();
        ui.run();
    }

    public void run() {
        while (running) {
            if (!app.isLoggedIn()) {
                handleNotLoggedInMenu();
            } else if (!app.isInGame()) {
                handleLoggedInMenu();
            } else {
                handleInGameMenu();
            }
        }

        scanner.close();
        System.out.println("Programmet avslutas.");
    }

    private void handleNotLoggedInMenu() {
        System.out.println("Välj ett alternativ:");
        System.out.println("1. Logga in");
        System.out.println("2. Registrera");
        System.out.println("3. Avsluta");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                handleLogin();
                break;
            case "2":
                handleRegister();
                break;
            case "3":
                running = false;
                break;
            default:
                System.out.println("Ogiltigt val.");
        }
    }

    private void handleLogin() {
        System.out.println("Ange användarnamn:");
        String username = scanner.nextLine();
        System.out.println("Ange lösenord:");
        String password = scanner.nextLine();
        if (app.loginUser(username, password)) {
            System.out.println("Inloggning lyckades! Välkommen " + username);
        } else {
            System.out.println("Inloggning misslyckades.");
        }
    }

    private void handleRegister() {
        System.out.println("Ange nytt användarnamn:");
        String newUsername = scanner.nextLine();
        System.out.println("Ange lösenord:");
        String newPassword = scanner.nextLine();
        if (app.registerUser(newUsername, newPassword)) {
            System.out.println("Registrering lyckades! Logga nu in.");
        } else {
            System.out.println("Användarnamnet är upptaget.");
        }
    }

    private void handleLoggedInMenu() {
        System.out.println("\nVälj ett alternativ:");
        System.out.println("1. Sätt in pengar");
        System.out.println("2. Ta ut pengar");
        System.out.println("3. Sätt insättningsgräns");
        System.out.println("4. Visa konto-info");
        System.out.println("5. Visa transaktionshistorik");
        System.out.println("6. Spela");
        System.out.println("7. Logga ut");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                handleDeposit();
                break;
            case "2":
                handleWithdraw();
                break;
            case "3":
                handleSetDepositLimit();
                break;
            case "4":
                System.out.println(app.viewAccountInfo());
                break;
            case "5":
                app.viewTransactionsHistory();
                break;
            case "6":
                handlePlayGame();
                break;
            case "7":
                app.logOutUser();
                System.out.println("Du är utloggad.");
                break;
            default:
                System.out.println("Ogiltigt val.");
        }
    }

    private void handleDeposit() {
        System.out.println("Ange belopp att sätta in:");
        try {
            double depositAmount = Double.parseDouble(scanner.nextLine());
            app.addFunds(depositAmount);
        } catch (NumberFormatException e) {
            System.out.println("Du måste ange beloppet i siffror");
            handleDeposit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleWithdraw() {
        System.out.println("Ange belopp att ta ut:");
        try {
            double withdrawAmount = Double.parseDouble(scanner.nextLine());
            app.withdrawFunds(withdrawAmount);
        } catch (NumberFormatException e) {
            System.out.println("Du måste ange beloppet i siffror");
            handleWithdraw();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleSetDepositLimit() {
        System.out.println("Ange ny insättningsgräns:");
        try{
        double limit = Double.parseDouble(scanner.nextLine());
        app.setDepositLimit(limit);
        }catch (NumberFormatException e){
            System.out.println("Du måste ange beloppet i siffror");
            handleSetDepositLimit();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void handlePlayGame() {
        while (true) {
            System.out.println("Vilket spel vill du spela? (Blackjack/Roulette)");
            String gameType = scanner.nextLine();

            try {
                app.playGame(gameType);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Fel: " + e.getMessage() + " Försök igen.");
            }
        }
    }

    private void handleInGameMenu() {
        System.out.println("Du är i spelet nu. Skriv 'Avsluta' för att avsluta spelet eller 'hjälp' för instruktioner.");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("Avsluta")) {
            app.quitGame();
        } else if (input.equalsIgnoreCase("hjälp")) {
            Game currentGame = app.getCurrentGame();
            if (currentGame != null) {
                currentGame.displayInstructions();
            }
        } else {
            System.out.println("Spelet ej implementerat i detalj.");
        }
    }
}