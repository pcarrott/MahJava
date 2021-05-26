package MahJava.tile;

public enum TileType {
    CHARACTERS,
    DOTS,
    BAMBOO,
    DRAGON,
    WIND;

    public boolean isSpecialType() {
        return (this == DRAGON || this == WIND);
    }
}
