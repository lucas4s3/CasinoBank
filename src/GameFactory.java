public class GameFactory {
    public GameFactory() {

    }

    public Game getGame(GameType game) {
        if (game == GameType.SLOTGAME) {
            return new SlotsGame();
        } else {
            return new RouletteGame();
        }

    }
}