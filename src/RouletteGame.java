public class RouletteGame implements Game {
    public static final double MIN_BET = 5.0;
    public static final double MAX_BET = 1000.0;

    @Override
    public void startGame() {
        System.out.println("Starting Roulette game...");
    }

    @Override
    public boolean validateBet(double bet) {
        return bet >= MIN_BET && bet <= MAX_BET;
    }

    @Override
    public double calculateWinnings() {
        // Roulette-specifik logik
        return Math.random() * MAX_BET;
    }

    public int spinWheel() {
        return (int) (Math.random() * 36); // 0-35, roulette-hjul
    }
}
