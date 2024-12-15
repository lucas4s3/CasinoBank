public class SlotsGame implements Game {
    double MIN_BET = 5;

    public SlotsGame() {
        displayInstructions();
    }

    @Override
    public void startGame() {

    }

    @Override
    public void displayInstructions() {
        System.out.println("=".repeat(40));
        System.out.println("    INSTRUKTIONER FÖR SLOTS            ");
        System.out.println("=".repeat(40));
        System.out.println("1. Välj insatsbeloppet du vill spela med.");
        System.out.println("2. Tryck på 'Spin' för att snurra hjulen.");
        System.out.println("3. Målet är att matcha symboler på vinstlinjerna.");
        System.out.println("4. Vinstutdelning beror på:");
        System.out.println("   - Antal matchande symboler.");
        System.out.println("   - Symbolernas värde.");
        System.out.println("   - Din insats.");
        System.out.println("5. Möjliga vinster:");
        System.out.println("   - Tre '7:or': 10x din insats.");
        System.out.println("   - Tre körsbär: 5x din insats.");
        System.out.println("6. Bonusrundor eller gratissnurr kan aktiveras om du får speciella symboler.");
        System.out.println("-".repeat(40));
    }


    public static void main(String[] args) {
        new SlotsGame();
    }

}
