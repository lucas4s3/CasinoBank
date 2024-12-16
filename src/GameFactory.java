public class GameFactory {

    public Game getGame(GameType game) {
        return switch (game) {
            case ROULETTE -> new RouletteGame();
            case BLACKJACK -> new BlackJackGame();
        };
    }
}