package MahJavaLib.player;

import MahJavaLib.game.OpenGame;
import MahJavaLib.hand.CombinationSet;
import MahJavaLib.hand.Hand;
import MahJavaLib.tile.Combination;
import MahJavaLib.tile.Tile;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Reactive agent architecture that chooses to never draw discarded tiles and
 * always opts for concealed hands.
 *
 * This means that the discard policy must be more complex to counter-balance
 * the disadvantage from this restriction.
 */
public class Concealed implements Profile {

    /*
     * No state is kept, so nothing happens.
     */
    @Override
    public void seeClaimedTile(@Nullable Player discarded, Tile tile, List<Player> claimed) {
        // Do nothing
    }

    /*
     * We first check for the possible combination set with the least ignored tiles,
     * i.e., the combination set closer to a winning hand.
     *
     * If we have a non-winning hand with only pairs and Pungs/Kongs/Chows, then we
     * choose to break one of the pairs.
     *
     * From the set of chosen tiles, we compare them through the number of times they
     * are used for a combination across all possible combination sets (tiles that are
     * ignored in the best combination set, may be used in other combination sets). We
     * choose randomly between the tiles that register the lowest count among all tiles.
     */
    @Override
    public Tile chooseTileToDiscard(Hand hand, OpenGame game) {
        List<CombinationSet> combinationSets = hand.getPossibleCombinationSets();
        List<Tile> candidateTiles = new ArrayList<>();
        Integer minCount = 15;
        // Filter the combination sets with the lowest number of ignored tiles and
        // store those tiles as candidates for discard
        for (CombinationSet set : combinationSets) {
            List<Tile> ignored = set.getIgnored();
            Integer count = ignored.size();
            if (count < minCount) { // Update the new lowest count
                candidateTiles.clear();
                candidateTiles.addAll(ignored);
                minCount = count;
            } else if (count.equals(minCount)) { // We have another tile with the current lowest count
                candidateTiles.addAll(ignored);
            }

            // If the lowest count is 0, then we have a non-winning hand composed of combinations and pairs.
            // It can be something like 2 Pungs + 4 Pairs or 2 Chows + 4 Pairs
            // So, we set the pair tiles as candidates for discard
            if (count == 0)
                candidateTiles.addAll(set.getPairs());
        }

        // Filter the candidate tiles that have been discarded/claimed at least once
        List<Tile> exposedTiles = candidateTiles.stream()
                .filter(game.getExposedTiles()::containsKey)
                .distinct()
                .collect(Collectors.toList());

        // If none of the candidate tiles has been exposed yet, then we filter the tiles with least potential
        // By potential we mean, in total, the number of times a tile is used for a combination over all possible
        // combination sets
        if (exposedTiles.isEmpty()) {
            List<Tile> tilesWithLeastPotential = new ArrayList<>();
            // At most, each of the four tiles can be used for a combination (e.g. 4 Chows) in each combination set
            // The higher bound is actually lower but this estimate is good enough
            long minPotential = 4L * combinationSets.size() + 1;
            for (Tile t : candidateTiles) {
                long potential = combinationSets.stream()
                        .map(set -> set.getCombinationCount(t)) // Get the count for each combination set
                        .reduce(Long::sum).get(); // Sum all the counts
                if (minPotential > potential) { // Update the new lowest potential
                    tilesWithLeastPotential.clear();
                    tilesWithLeastPotential.add(t);
                    minPotential = potential;
                } else if (minPotential == potential) { // We have another tile the current lowest potential
                    tilesWithLeastPotential.add(t);
                }
            }

            // We choose randomly from the remaining tiles with the least potential
            int rand = new Random().nextInt(tilesWithLeastPotential.size());
            return tilesWithLeastPotential.get(rand);
        }

        List<Tile> maxTiles = new ArrayList<>();
        Integer maxCount = -1;
        // Filter all tiles with highest count currently in the open
        for (Tile tile : exposedTiles) {
            Integer count = game.getExposedTiles().get(tile);
            if (count > maxCount) { // Update the new highest count
                maxTiles.clear();
                maxTiles.add(tile);
                maxCount = count;
            } else if (count.equals(maxCount)) { // We have another tile with the current highest count
                maxTiles.add(tile);
            }
        }

        // We choose randomly from the remaining tiles
        int rand = new Random().nextInt(maxTiles.size());
        return maxTiles.get(rand);
    }

    /*
     * Never claims a discarded tile.
     */
    @Override
    public Optional<Combination> wantsDiscardedTile(Tile discardedTile, Hand hand, OpenGame game) {
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
