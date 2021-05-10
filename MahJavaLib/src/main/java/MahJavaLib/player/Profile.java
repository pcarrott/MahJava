package MahJavaLib.player;

import MahJavaLib.tile.Combination;
import MahJavaLib.tile.Tile;

import java.util.Optional;

public interface Profile {
    // These methods should receive arguments but we'll leave them empty for now
    Tile chooseTileToDiscard();
    Optional<Combination> wantsDiscardedTile();
    boolean declareConcealedKong();
}
