package MahJavaLib.player;

import MahJavaLib.game.OpenGame;
import MahJavaLib.hand.Hand;
import MahJavaLib.tile.Combination;
import MahJavaLib.tile.Tile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Reactive agent architecture that does thorough processing of the perceptions
 * sensed from the environment.
 *
 * Each decision is made after clever interpretation of the current state of the game,
 * with less randomness involved.
 */
class ComposedReactive implements Profile {

    /*
     * From the tiles at hand, the player will discard the tile that was discarded and/or claimed the most,
     * by himself and/or by other players.
     *
     * If no tile in hand has been previously exposed, then we discard one of the tiles the lowest count and furthest
     * away from completing a chow. @TODO
     */
    @Override
    public Tile chooseTileToDiscard(Hand hand, OpenGame game) {
        Map<Tile, Integer> exposedTiles = game.getExposedTiles();

        // Filter all tiles in hand that have been exposed, at least once.
        Map<Tile, Integer> candidateTiles = new HashMap<>();
        hand.getHand().keySet().forEach(tile -> exposedTiles.computeIfPresent(tile, candidateTiles::put));

        // @TODO: The result may come out empty, need to handle this
        if (candidateTiles.isEmpty())
            return null;

        // Filter all tiles with highest count currently in the open
        Integer maxCount = candidateTiles.values().stream().max(Integer::compare).get();
        List<Tile> maxTiles = candidateTiles.entrySet().stream()
                .filter(e -> e.getValue().equals(maxCount))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // We choose randomly from the collection of candidate tiles
        int rand = new Random().nextInt(maxTiles.size());
        return maxTiles.get(rand);
    }

    /*
     * The player wants a tile in any of the following scenarios:
     *   - Can claim a Pung without a fourth equal tile
     *   - Can claim a Pung with a fourth tile belonging to a Chow
     *   - Can claim a Kong if there is no Chow being made with the tile
     *   - Can claim a Chow if it does not break a concealed Pair/Pung
     */
    @Override
    public Optional<Combination> wantsDiscardedTile(Tile discardedTile, Hand hand, OpenGame game) {
        // @TODO
        return Optional.empty();
    }

    /*
     * Only declares a concealed Kong if there is no concealed/possible Chow with the same tile.
     */
    @Override
    public boolean declareConcealedKong(Hand hand, OpenGame game) {
        List<Tile> concealedKongs = hand.getConcealedKongs();

        // We don't have any Kongs
        if (concealedKongs.isEmpty())
            return false;

        List<Tile> acceptableKongs = concealedKongs.stream().filter(
                tile -> tile.getPossibleChowCombinations().stream() // We get the possible chows for the tile
                        .flatMap(Collection::stream) // Join all the tiles in a single collection
                        .distinct() // Remove repeated tiles
                        // No tile (other than the Kong tile) must be in the hand.
                        // Otherwise, we choose to wait for/keep a Chow
                        .noneMatch(t -> !t.equals(tile) && hand.getHand().containsKey(t))
        ).collect(Collectors.toList());

        if (acceptableKongs.isEmpty())
            return false;

        // We have at least one, so we choose randomly
        // (it will most likely be always one but this is just to make sure)
        int rand = new Random().nextInt(acceptableKongs.size());
        hand.declareConcealedKong(acceptableKongs.get(rand));
        return true;
    }
}
