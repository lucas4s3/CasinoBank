public class BlackJackGame implements Game {
    double MIN_BET = 10;

    public BlackJackGame () {
        displayInstructions();
    }

    @Override
    public void startGame() {


    }

    @Override
    public void displayInstructions() {
        System.out.println("=".repeat(40));
        System.out.println("      INSTRUKTIONER FÖR BLACKJACK          ");
        System.out.println("=".repeat(40));
        System.out.println("1. Målet är att komma så nära 21 som möjligt utan att överskrida det.");
        System.out.println("2. Börja genom att placera en insats.");
        System.out.println("3. Tryck på 'Deal' för att få två kort. Dealern får också två kort, varav ett är öppet.");
        System.out.println("4. Välj 'Hit' för att dra ett till kort eller 'Stand' för att stanna.");
        System.out.println("5. Om din hand överskrider 21, förlorar du automatiskt.");
        System.out.println("6. Om du vinner mot dealern:");
        System.out.println("   - Blackjack (Ess + 10) betalar 3:2.");
        System.out.println("   - En vanlig vinst betalar 1:1.");
        System.out.println("7. Dealern måste stanna på 17 eller högre.");
        System.out.println("-".repeat(40));

    }


    public static void main(String[] args) {
        new
                BlackJackGame();
    }


}
