package MahJava.game;

public enum PlayerTurn {
    EAST,
    SOUTH,
    WEST,
    NORTH;

    public PlayerTurn next() {
        PlayerTurn[] turns = PlayerTurn.values();
        return turns[(this.ordinal() + 1) % turns.length];
    }
}
