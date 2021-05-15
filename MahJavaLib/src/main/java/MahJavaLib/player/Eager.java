package MahJavaLib.player;

import MahJavaLib.game.OpenGame;
import MahJavaLib.hand.Hand;
import MahJavaLib.tile.Combination;
import MahJavaLib.tile.Tile;

import java.util.*;

/**
 * Reactive agent architecture that does minimum processing of the perceptions
 * sensed from the environment.
 *
 * Some decisions are made randomly, while others are constant without taking
 * into consideration the current state of the game.
 */
class Eager implements Profile {

    /*
     * Looks at the tiles with the lowest count and discards one of them random,
     * without any kind of deliberation.
     */
    @Override
    public Tile chooseTileToDiscard(Hand hand, OpenGame game) {
        List<Tile> minTiles = new ArrayList<>();
        Integer minCount = 5;
        // Filter all tiles with lowest count currently in hand
        for (Map.Entry<Tile, Integer> e : hand.getHand().entrySet()) {
            Tile tile = e.getKey();
            Integer count = e.getValue();
            if (count < minCount) { // Update the new lowest count
                minTiles.clear();
                minTiles.add(tile);
                minCount = count;
            } else if (count.equals(minCount)) { // We have another tile with the current lowest count
                minTiles.add(tile);
            }
        }

        // We choose randomly from the collection of candidate tiles
        int rand = new Random().nextInt(minTiles.size());
        return minTiles.get(rand);
    }

    /*
     * If the discarded tile results in any combination, the player will claim it.
     * If more than one combination is possible, then it is randomly selected.
     */
    @Override
    public Optional<Combination> wantsDiscardedTile(Tile discardedTile, Hand hand, OpenGame game) {
        Set<Combination> combinations = hand.getPossibleCombinationsForTile(discardedTile).keySet();
        if (combinations.isEmpty())
            return Optional.empty();

        // We choose randomly from the collection of candidate tiles
        int rand = new Random().nextInt(combinations.size());
        int i = 0;
        for (var combination : combinations) {
            // This loop is needed because Java does not allow Set-indexing
            if (i == rand)
                return Optional.of(combination);
            i++;
        }

        // If the set is empty, then we do not want the tile
        return Optional.empty();
    }

    /*
     * If they can declare a Kong, then they will claim it unconditionally.
     */
    @Override
    public boolean declareConcealedKong(Hand hand, OpenGame game) {
        List<Tile> concealedKongs = hand.getConcealedKongs();

        // We don't have any Kongs
        if (concealedKongs.isEmpty())
            return false;

        // We have at least one, so we choose randomly
        // (it will most likely be always one but this is just to make sure)
        int rand = new Random().nextInt(concealedKongs.size());
        hand.declareConcealedKong(concealedKongs.get(rand));
        return true;
    }
}
