public interface Game {
    void startGame();
    boolean validateBet(double bet);
    double calculateWinnings();
}
