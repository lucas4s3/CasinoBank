public class GameFactory {
    public static Game createGame(String gameType) {
        switch (gameType.toLowerCase()) {
            case "roulette":
               // return new RouletteGame();
            case "slots":
                return new SlotsGame();
            default:
                throw new IllegalArgumentException("Invalid game type: " + gameType);
        }
    }
}
