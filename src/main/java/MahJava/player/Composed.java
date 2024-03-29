package MahJava.player;

import MahJava.game.OpenGame;
import MahJava.hand.CombinationSet;
import MahJava.hand.Hand;
import MahJava.tile.Combination;
import MahJava.tile.CombinationType;
import MahJava.tile.Tile;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Reactive agent architecture that does thorough processing of the perceptions
 * sensed from the environment.
 *
 * Each decision is made after clever interpretation of the current state of the game,
 * with less randomness involved.
 */
public class Composed implements Profile {

    /*
     * No state is kept, so nothing happens.
     */
    @Override
    public void seeClaimedTile(@Nullable Player discarded, Tile tile, List<Player> claimed) {
        // Do nothing
    }

    /*
     * From the tiles at hand, the player will discard the tile that was discarded and/or claimed the most,
     * by himself and/or by other players.
     *
     * If no tile in hand has been previously exposed, then we follow the eager strategy.
     */
    @Override
    public Tile chooseTileToDiscard(Hand hand, OpenGame game) {
        Map<Tile, Integer> exposedTiles = game.getExposedTiles();

        // Filter all tiles in hand that have been exposed, at least once.
        Map<Tile, Integer> candidateTiles = new HashMap<>();
        hand.getHand().keySet().forEach(tile -> exposedTiles.computeIfPresent(tile, candidateTiles::put));

        if (candidateTiles.isEmpty()) {
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

        List<Tile> maxTiles = new ArrayList<>();
        Integer maxCount = 0;
        // Filter all tiles with highest count currently in the open
        for (Map.Entry<Tile, Integer> e : candidateTiles.entrySet()) {
            Tile tile = e.getKey();
            Integer count = e.getValue();
            if (count > maxCount) { // Update the new highest count
                maxTiles.clear();
                maxTiles.add(tile);
                maxCount = count;
            } else if (count.equals(maxCount)) { // We have another tile with the current highest count
                maxTiles.add(tile);
            }
        }

        // We choose randomly from the collection of candidate tiles
        int rand = new Random().nextInt(maxTiles.size());
        return maxTiles.get(rand);
    }

    /*
     * The player wants a tile in any of the following scenarios:
     *   1. Can claim a Pung without a fourth equal tile
     *   2. Can claim a Pung with a fourth tile belonging to a Chow
     *   3. Can claim a Kong if there is no Chow being made with the tile
     *   4. Can claim a Chow if it does not break a concealed Pair/Pung and if they do not have the tile yet
     */
    @Override
    public Optional<Combination> wantsDiscardedTile(Tile discardedTile, Hand hand, OpenGame game) {
        Map<Combination, List<CombinationSet>> combinations = hand.getPossibleCombinationsForTile(discardedTile);
        Integer count = hand.getHand().get(discardedTile);
        List<Combination> options = new ArrayList<>();
        boolean hasChow = false;
        for (Combination combination : combinations.keySet()) {
            CombinationType type = combination.getCombinationType();
            Set<Tile> tiles = combination.getTiles().keySet();

            if (type == CombinationType.PUNG && count == 2) {
                return Optional.of(combination); // Scenario 1.

            } else if (type == CombinationType.KONG && !hasChow) {
                options.add(combination); // Scenario 3.

            } else if (type == CombinationType.CHOW && !hand.getHand().containsKey(discardedTile) &&
                    tiles.stream().noneMatch(t -> hand.getHand().containsKey(t) && hand.getHand().get(t) > 1)) {

                if (Integer.valueOf(3).equals(count))
                    return Optional.of(
                            new Combination(CombinationType.PUNG, Collections.nCopies(3, discardedTile))
                    ); // Scenario 2.

                hasChow = true; // Scenario 3. no longer possible
                if (!options.isEmpty() && options.get(0).getCombinationType() == CombinationType.KONG)
                    options.clear(); // Delete Scenario 3. from possible options
                options.add(combination); // Scenario 4.
            }
        }

        if (options.isEmpty())
            return Optional.empty();

        // We can have multiple possible Chows so we have to filter them.
        // For example, if we have tiles 1, 2, 4, 5 and 6 when a 3 is discarded,
        // then we can declare 3 Chows: 123, 234 or 345.
        // The best option here is to declare 123 while keeping a 456 Chow concealed,
        // because this way we have 2 Chows while, if we declare a 234 or a 345, we
        // only get 1 Chows.
        // Here we map each Chow to the maximum number of chows possibles in our hand
        // if we declare it and extract the Chow with the highest amount.
        Combination combination = options.stream().map(c -> {
                    int maxChows = combinations.get(c).stream()
                            // Count the chows for each possible combination set
                            .map(set -> set.getCombinations(CombinationType.CHOW).size())
                            // Get the maximum Chows if we declare this Chow
                            .max(Integer::compareTo).get();
                    return Map.entry(c, maxChows);
                }
        )
        // Get the Chow with the highest maximum
        .reduce((e1, e2) -> e1.getValue() > e2.getValue() ? e1 : e2).get().getKey();
        return Optional.of(combination);
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
                        .allMatch(t -> t.equals(tile) || !hand.getHand().containsKey(t))
        ).collect(Collectors.toList());

        if (acceptableKongs.isEmpty())
            return false;

        // We have at least one, so we choose randomly
        // (it will most likely be always one but this is just to make sure)
        int rand = new Random().nextInt(acceptableKongs.size());
        hand.declareConcealedKong(acceptableKongs.get(rand));
        return true;
    }

    @Override
    public String toString() {
        return "Composed";
    }
}
