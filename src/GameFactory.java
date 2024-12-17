public class GameFactory {

    public static Game getGame(GameType game, User user) {
        return switch (game) {
            case ROULETTE -> new RouletteGame(user);
            case BLACKJACK -> new Blackjack(user);
        };
    }
}