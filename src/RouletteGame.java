public class RouletteGame implements Game {
    public static final int MIN_BET = 5;
    public static final int MAX_BET = 1000;

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
