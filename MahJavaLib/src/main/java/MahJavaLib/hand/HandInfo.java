package MahJavaLib.hand;

import MahJavaLib.tile.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This info structure allows us to record all the major information about each hand
 * as the game progresses (ex: how many chows/pungs/kongs does this hand have, how many tiles
 * of each type, etc.)
 * This structure should be created right at the start of each game, with the starting hand
 * and it should be incrementally updated as the game progresses.
 */
class HandInfo {

    // @NOTE: rename allPossibleHands to allPossibleCombinations, and add a Set that contains all pieces that can Pair
    private List<CombinationSet> allPossibleHands;
    private final Map<Tile, Tile> ignoredTiles = new HashMap<>();

    public HandInfo() {}

    public void updateHands(Map<Tile, Integer> tileCounter) {
        // Distinct is used because there seems to be some cases where the same hand appears more than once.
        this.allPossibleHands = computeHands(tileCounter).stream().distinct().collect(Collectors.toList());
    }

    /*
     * Recursive function to obtain all possible different combinations for the given hand.
     */
    private List<CombinationSet> computeHands(Map<Tile, Integer> tileCounter) {

        /*
         * No tiles are left to check, so we have the stop case of the recursion:
         *   an empty struct to add the combinations.
         */
        if (tileCounter.isEmpty())
            return Collections.singletonList(new CombinationSet());

        Tile tile = (Tile) tileCounter.keySet().toArray()[0];
        Integer count = tileCounter.get(tile);
        List<CombinationSet> currentHands = new ArrayList<>();

        /*
         * For every tile we must consider the possibility of doing a chow with it
         */
        tryChow(tile, tileCounter, currentHands);

        if (count == 1 && this.canIgnore(tile, tileCounter)) {
            /*
             * We already checked the scenario where the tile is used for a chow.
             * We must now see the hands we can make if we ignore the tile in question.
             * For example, if we have the tiles 1, 2, 3 and 4 of one suite, must consider the following scenarios:
             *   - 123 chow and ignore 4
             *   - 234 chow and ignore 1
             * To avoid repetitions, we must check first if the tile can be ignored.
             * All ignored tiles are removed from the set of available tiles, but are kept in a separate structure.
             */

            this.ignoredTiles.put(tile, tile);
            tileCounter.remove(tile);
            List<CombinationSet> computedHands = computeHands(tileCounter);
            computedHands.forEach(set -> set.addIgnored(tile));
            currentHands.addAll(computedHands);
            tileCounter.put(tile, count);
            this.ignoredTiles.remove(tile);

        } else if (count == 2) {
            /*
             * Included scenarios with tryChow:
             *   - chow + (any combo where count == 1)
             * Scenarios left to be included:
             *   - pair
             */

            tileCounter.remove(tile);
            List<CombinationSet> computedHands = computeHands(tileCounter);
            computedHands.forEach(set -> set.addPair(tile));
            currentHands.addAll(computedHands);
            tileCounter.put(tile, count);

        } else if (count == 3) {
            /*
             * Included scenarios with tryChow:
             *   - chow + (no-chow + no-chow)
             *   - chow + (any combo where count == 2)
             * Scenarios left to be included:
             *   - pung
             */

            tileCounter.remove(tile);
            List<CombinationSet> computedHands = computeHands(tileCounter);
            computedHands.forEach(set -> set.addCombination(CombinationType.PUNG, Map.of(tile, 3)));
            currentHands.addAll(computedHands);
            tileCounter.put(tile, count);

        } else if (count == 4) {
            /*
             * Included scenarios with tryChow:
             *   - chow + (no-chow + no-chow + no-chow)
             *   - chow + (any combo where chow == 3)
             * Scenarios left to be included:
             *   - pair + pair
             *   - kong (not relevant here since this only considers combinations with the concealed tiles)
             */

            tileCounter.remove(tile);
            List<CombinationSet> computedHands = computeHands(tileCounter);
            computedHands.forEach(set -> set.addPair(tile, 2));
            currentHands.addAll(computedHands);
            tileCounter.put(tile, count);
        }

        return currentHands;
    }

    /*
     * For the given tile, we try to see what combinations we can get with each chow.
     * We call setCurrentHands directly instead of calling setCombinations because the tiles are removed differently.
     */
    private void tryChow(
            Tile tile,
            Map<Tile, Integer> tileCounter,
            List<CombinationSet> currentHands) {

        var chows = tile.getPossibleChowCombinations();

        for (var chow : chows) {
            // All tiles for the chow must be present in the remaining tiles
            if (!chow.stream().allMatch(tileCounter::containsKey))
                continue;

            // For each tile of the chow we must decrease its count by 1.
            // If there is only one tile, then it should be removed.
            for (Tile t : chow) {
                int count = tileCounter.get(t);
                if (count == 1)
                    tileCounter.remove(t);
                else
                    tileCounter.put(t, count - 1);
            }

            // We call the recursion and add a chow to each generated hand
            List<CombinationSet> computedHands = computeHands(tileCounter);
            Map<Tile, Integer> chowTiles = new HashMap<>();
            chow.forEach(t -> chowTiles.put(t, 1));
            computedHands.forEach(set -> set.addCombination(CombinationType.CHOW, chowTiles));
            currentHands.addAll(computedHands);

            // We no longer need the chow, so we increment each tile's count so that they have the same count as before
            // Tile that were removed are re-put
            chow.forEach(t -> tileCounter.merge(t, 1, Integer::sum));
        }

    }

    /*
     * For a given tile, we check which chows are possible given the tiles this hand has.
     * We must also consider the tiles that were previously ignored, so as to make sure that we don't ignore 3
     *   consecutive tiles. This means that we would be ignoring a full chow and end up having valid combinations that
     *   is a subset of another valid combination.
     */
    private List<List<Tile>> getPossibleChows(Tile tile, Map<Tile, Integer> tileCounter) {
        return tile.getPossibleChowCombinations().stream()
                .filter(chow -> chow.stream().allMatch(
                        t -> tileCounter.containsKey(t) || this.ignoredTiles.containsKey(t))
                )
                .collect(Collectors.toList());
    }

    /*
     * Checks if a given tile can be ignored.
     * For example, we can have a hand with 1, 2, 3, 4, 5 and 6 of a given suite.
     * The possible combinations are:
     *   - 123 chow + 456 chow
     *   - 234 chow + ignore 1, 5 and 6
     *   - 345 chow + ignore 1, 2 and 6
     * However, if we simply ignore every checked tile, then we could end up ignoring 1, 2 and 3 in succession.
     * This would result in recognizing a 456 chow as valid, without considering the 123 chow.
     * Also, when we are considering the 123 chow we would also consider what would happen if we ignore 4, 5 and 6.
     * Following this reasoning, we would obtain the following combinations:
     *   - 123 chow + 456 chow
     *   - 123 chow
     *   - 456 chow
     * These are all valid combinations but the last two are subsets of the first one.
     * This repetition is to be avoided (henceforth, referred as the subset problem) and this method encapsulates all
     *   that filtering logic.
     *
     * (I can't believe this method has more comments than code...)
     */
    private boolean canIgnore(
            Tile tile,
            Map<Tile, Integer> tileCounter) {

        // We get the possible chows for this tile, given the remaining tiles available.
        var chows = this.getPossibleChows(tile, tileCounter);

        // Here we check if there is a chow with two ignored tiles.
        // If so, then we cannot ignore the tile because we would be ignoring a full chow.
        if (chows.stream().anyMatch(
                c -> c.stream().filter(t -> !t.equals(tile)).allMatch(this.ignoredTiles::containsKey)
        ))
            return false;

        if (chows.size() == 1) {
            /*
             * Possible scenarios:
             *   1) Tile 2 and also have tiles 3 and 4
             *   2) Tile 2 and also have tiles 3, 4 and 5
             *
             * In each scenario, tile 2 can only be part of a 234 chow.
             * In 1), tiles 3 and 4 can only be part of the 234 chow so, if we ignore tile 2, then tiles 3 and 4 won't
             *   have any combinations left and will also be ignored, which will lead to the subset problem. So, the
             *   tile can't be ignored.
             * In 2), tiles 3 and 4 can belong to a 345 chow, so, if we ignore tile 2, then tiles 3 and 4 will still
             *   have a possible combination. So, the tile can be ignored.
             *
             * In essence, if at least one tile can be part of another chow, then we can ignore the tile.
             *
             * Note: another condition was added but it would be another long explanation, so just trust me on this...
             */
            return chows.get(0).stream().anyMatch(
                    t -> this.getPossibleChows(t, tileCounter).size() > 1 ||
                            (tileCounter.get(t) != null && tileCounter.get(t) > 1)
            );

        } else if (chows.size() == 2) {
            /*
             * Possible scenarios:
             *   1) Tile 3 and also have 2, 4, 5 and 6
             *   2) Tile 3 and also have 2, 4 and 5
             *   3) Tile 5 and also have 2, 3, 4 and 6
             *   4) Tile 5 and also have 3, 4 and 6
             *
             * In 1), we can have the chows 234, 345 or 456.
             * We can ignore tile 3 here because the largest tile in a chow with a 3 (tile 5 in 345) can be part
             *   of more than 1 chow (345 and 456), i.e., when we consider the 456 chow, we ignore tiles 2 and 3.
             * In 2), we can have the chows 234 or 345.
             * We can't ignore tile 3 here because the largest tile in a chow with a 3 (tile 5 in 345)
             *   can only be part of 1 chow, i.e., there is no 456 chow.
             * In 3), we can have the chows 234, 345 or 456.
             * We can ignore tile 5 here because the smallest tile in a chow with a 5 (tile 3 in 345) can be part
             *   of more than 1 chow (234 and 345), i.e., when we consider the 234 chow, we ignore tiles 5 and 6.
             * In 4), we can have the chows 345 or 456.
             * We can't ignore tile 5 here because the smallest tile in a chow with a 5 (tile 3 in 345)
             *   can only be part of 1 chow, i.e., there is no 234 chow.
             *
             * In essence, we get the smallest tile and the largest tile amongst the 2 chows of the tile.
             * If one of those edge tiles can be part of another chow, then we can ignore the original tile.
             *
             * Note: another condition was added but it would be... jk, I think this *still* makes sense
             */
            Tile smallest = chows.get(0).get(0);
            Tile largest = chows.get(1).get(2);
            return this.getPossibleChows(smallest, tileCounter).size() > 1 ||
                    this.getPossibleChows(largest, tileCounter).size() > 1;

        } else if (chows.size() == 3) {
            /*
             * Possible scenarios:
             *   1) Tile 4 and also have 2, 3, 5, 6, 7 and 8
             *   2) Tile 6 and also have 2, 3, 4, 5, 7 and 8
             *   3) Tile 5 and also have 2, 3, 4, 6, 7 and 8
             *
             * In 1), if we ignore tile 4, then we can't do anything with tiles 2 and 3, so we would be ignoring
             *   3 consecutive tiles, i.e., a chow.
             * This happens because the smallest tile (tile 2) can't make another
             *   chow besides 234.
             * In 2), if we ignore tile 6, then we can't do anything with tiles 7 and 8, so we would be ignoring
             *   3 consecutive tiles, i.e., a chow.
             * This happens because the largest tile (tile 8) can't make another
             *   chow besides 678.
             * In 3), if we ignore tile 5, then we can still have chows 234 and 678, which is acceptable.
             * This happens because both the smallest (3 for 345) and largest (7 for 567) tiles in a chow with a 5 can
             *   belong to another chow (234 and 678, respectively).
             *
             * In essence, we get the smallest tile of the smallest chow of the tile and the largest tile of the largest
             *   chow of the tile.
             * If both of these tiles can belong to more than 1 chow, then we can ignore the original tile.
             *
             * Note: another condition was added but it would be another long explanation, so just trust me on this...
             */
            Tile smallest = chows.get(0).get(0);
            Tile largest = chows.get(2).get(2);
            return (this.getPossibleChows(smallest, tileCounter).size() > 1 ||
                    (tileCounter.get(smallest) != null && tileCounter.get(smallest) > 1 &&
                            this.getPossibleChows(largest, tileCounter).size() > 1)
                            &&
                            (this.getPossibleChows(largest, tileCounter).size() > 1 ||
                                    (tileCounter.get(largest) != null && tileCounter.get(largest) > 1) &&
                                            this.getPossibleChows(smallest, tileCounter).size() > 1));

        }

        // Number of possible chows is 0, so the tile doesn't belong to any combination and should be ignored.
        return true;
    }

    public List<CombinationSet> getAllPossibleHands() {
        return this.allPossibleHands;
    }
}
