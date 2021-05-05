package MahJavaLib.tile;

public enum CombinationType {
    CHOW,
    // @TODO: Pair should probably not be here, since it is not an actual combination
    // Instead, have a different Map just for pairs in HandInfo
    PAIR,
    PUNG,
    KONG
}
