public class GameFactory {
    public static Game createGame(String gameType, User currentUser) {
        switch (gameType.toLowerCase()) {
            case "blackjack":
                return new Blackjack(currentUser);
               case "roulette":
                  return new RouletteGame(currentUser);
            default:
                throw new IllegalArgumentException("Invalid game type: " + gameType);
        }
    }
}
