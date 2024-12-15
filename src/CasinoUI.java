import java.util.Scanner;

public class CasinoUI {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        CasinoApp app = new CasinoApp(userManager);
        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {
            if (!app.isLoggedIn()) {
                System.out.println("Välj ett alternativ:");
                System.out.println("1. Logga in");
                System.out.println("2. Registrera");
                System.out.println("3. Avsluta");

                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        System.out.println("Ange användarnamn:");
                        String username = scanner.nextLine();
                        System.out.println("Ange lösenord:");
                        String password = scanner.nextLine();
                        if (app.loginUser(username, password)) {
                            System.out.println("Inloggning lyckades! Välkommen " + username);
                        } else {
                            System.out.println("Inloggning misslyckades.");
                        }
                        break;
                    case "2":
                        System.out.println("Ange nytt användarnamn:");
                        String newUsername = scanner.nextLine();
                        System.out.println("Ange lösenord:");
                        String newPassword = scanner.nextLine();
                        if (app.registerUser(newUsername, newPassword)) {
                            System.out.println("Registrering lyckades! Logga nu in.");
                        } else {
                            System.out.println("Användarnamnet är upptaget.");
                        }
                        break;
                    case "3":
                        running = false;
                        break;
                    default:
                        System.out.println("Ogiltigt val.");
                }
            } else if (!app.isInGame()) {
                System.out.println("\nVälj ett alternativ:");
                System.out.println("1. Sätt in pengar");
                System.out.println("2. Ta ut pengar");
                System.out.println("3. Sätt insättningsgräns");
                System.out.println("4. Visa konto-info");
                System.out.println("5. Spela");
                System.out.println("6. Logga ut");

                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        System.out.println("Ange belopp att sätta in:");
                        double depositAmount = Double.parseDouble(scanner.nextLine());
                        app.addFunds(depositAmount);
                        break;
                    case "2":
                        System.out.println("Ange belopp att ta ut:");
                        double withdrawAmount = Double.parseDouble(scanner.nextLine());
                        app.withdrawFunds(withdrawAmount);
                        break;
                    case "3":
                        System.out.println("Ange ny insättningsgräns:");
                        double limit = Double.parseDouble(scanner.nextLine());
                        app.setDepositLimit(limit);
                        break;
                    case "4":
                        System.out.println(app.viewAccountInfo());
                        break;
                    case "5":
                        System.out.println("Vilket spel vill du spela? (roulette/slots)");
                        String gameType = scanner.nextLine();
                        app.playGame(gameType);
                        break;
                    case "6":
                        app.logOutUser();
                        System.out.println("Du är utloggad.");
                        break;
                    default:
                        System.out.println("Ogiltigt val.");
                }
            } else {
                // Inuti ett spel
                System.out.println("Du är i spelet nu. Skriv 'quit' för att avsluta spelet.");
                String input = scanner.nextLine();
                if ("quit".equalsIgnoreCase(input)) {
                    app.quitGame();
                } else {
                    System.out.println("Spelet ej implementerat i detalj.");
                }
            }
        }

        scanner.close();
        System.out.println("Programmet avslutas.");
    }
}
