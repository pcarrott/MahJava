package MahJavaLib.player;

import MahJavaLib.tile.Combination;
import MahJavaLib.tile.Tile;

import java.util.Optional;

public interface Profile {
    public Tile chooseTileToDiscard();
    public Optional<Combination> wantsDiscardedTile();
}
