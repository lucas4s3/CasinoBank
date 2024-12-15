import java.util.Scanner;

public class RouletteGame implements Game {
    double MIN_BET = 10;
    int balance = 20;

    public RouletteGame() {
        displayInstructions();
        startGame();
    }

    @Override
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Välj insats (Minst " + MIN_BET + ")");
        while (true) {

            int amount = scanner.nextInt();

            if (balance < amount) {
                System.out.println("För lågt saldo, sätt in mer eller avsluta.");
                continue;
            } else if (amount < MIN_BET) {
                System.out.println("För låg insats, försök igen.");
                continue;
            }

            System.out.println("Gör ett val: 1. Röd eller 2. Svart");
            int userChoice = scanner.nextInt();

            spinWheel(userChoice);

        }

    }

    @Override
    public void displayInstructions() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=".repeat(40));
        System.out.println("       INSTRUKTIONER FÖR ROULETTE           ");
        System.out.println("=".repeat(40));
        System.out.println("1. Välj vilken typ av insats du vill göra:");
        System.out.println("   - Nummer (t.ex. 7)");
        System.out.println("   - Färg (Röd eller Svart)");
        System.out.println("   - Udda eller jämna nummer.");
        System.out.println("2. Placera din insats på spelbordet.");
        System.out.println("3. Dealern snurrar hjulet och släpper kulan.");
        System.out.println("4. Om kulan landar på det du satsat på, vinner du enligt följande utbetalningar:");
        System.out.println("   - Enstaka nummer: 35:1.");
        System.out.println("   - Röd/Svart eller Udda/Jämna: 1:1.");
        System.out.println("   - Dussin (1–12, 13–24, 25–36): 2:1.");
        System.out.println("5. Om kulan landar på ett annat nummer, förlorar du insatsen.");
        System.out.println("-".repeat(40));
        System.out.println("Klicka enter för att gå vidare till spelet");
        while (true) {

            if (scanner.nextLine().equalsIgnoreCase("")) {
                break;
            }

        }


    }


    public void spinWheel(int userChoice) {
        System.out.println("Spinning wheel");
    }


    public static void main(String[] args) {
        new RouletteGame();
    }
}

