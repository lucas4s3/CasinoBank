public class SlotsGame implements Game {
    public static final double MIN_BET = 1.0;
    public static final double MAX_BET = 500.0;

    @Override
    public void startGame() {
        System.out.println("Starting Slots game...");
    }

    @Override
    public boolean validateBet(double bet) {
        return bet >= MIN_BET && bet <= MAX_BET;
    }

    @Override
    public double calculateWinnings() {
        return Math.random() * MAX_BET;
    }

    public int spinSlots() {
        return (int) (Math.random() * 100);
    }
}
