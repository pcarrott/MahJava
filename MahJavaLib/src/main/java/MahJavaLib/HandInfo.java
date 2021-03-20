package MahJavaLib;

import java.util.*;

import MahJavaLib.Player.CombinationType;
import lombok.Getter;

public class HandInfo {

    static final int MAX_TILES = 14;
    static final int MAX_CHOWS_PER_TILE = 4;

    @Getter
    private final List<List<CombinationType>> winningHands;

    /*
     * Table containing information regarding all the possible chows in the hand.
     * Each tile has a queue of tiles that precede it.
     * For example, if the 3rd tile is a 4 Bamboo and the 4th tile is a 5 Bamboo,
     * then the chow table has a 2 in entry 3.
     */
    private List<Deque<Integer>> chowTable;

    /*
     * Structure to map each index with all the possible combinations it can form.
     * For example, if we have 1 Dot, 2 Dots and 3 Dots, then the index for the 3 Dots tile will be marked with a pung.
     * Only the last tile of a combination needs to marked for our algorithm to work.
     * For simplicity, it will be referred in further comments and used in variables as itc/ITC.
     */
    private Map<Integer, Map<CombinationType, CombinationType>> indexToCombinations = new HashMap<>();

    /**
     * Generates the chow table and ITC from the current hand.
     * This information is used to compute all the possible winning hands.
     *
     * @param tiles The tiles obtained after drawing the 14th tile.
     *              These are assumed to be sorted for the algorithm to work.
     */
    public HandInfo(List<Tile> tiles) {
        this.createChowTable();

        int duplicateCount = 0; // Counter for pairs and pungs
        int sequenceCount = 0; // Counter for sequences

        /*
         * During the for loop we will always compare each tile with the previous,
         * taking advantage of its sorted state.
         */
        Tile previousTile = tiles.get(0);
        for (int i = 1; i < tiles.size(); i++) {
            Tile currentTile = tiles.get(i);

            if (currentTile.equals(previousTile)) {
                /*
                 * We see repeated tiles. Might be a pung or a pair. An interleaved chow is also possible.
                 */

                /* We leave the previous tile with only one of its possible chows.
                 * The remaining chows are passed along to the next tile.
                 */
                Deque<Integer> previousChows = this.chowTable.get(i - 1);
                Deque<Integer> currentChows = this.chowTable.get(i);
                while (previousChows.size() > 1)
                    currentChows.add(previousChows.pop());

                /*
                 * Everytime we see a repeated tile, it is recorded as a potencial pair.
                 * This will always be its first insertion in the ITC for these tiles.
                 */
                this.indexToCombinations.put(
                        i,
                        new HashMap<>(Map.ofEntries(Map.entry(CombinationType.PAIR, CombinationType.PAIR)))
                );

                /*
                 * Since this may be an interleaved chow, we check for its length, using the chow table.
                 * It needs to make, at least, 2 hops.
                 */
                int chowLength = 0;
                while (!currentChows.isEmpty()) {
                    currentChows = this.chowTable.get(currentChows.getFirst());
                    chowLength++;
                }
                if (chowLength > 1)
                    this.indexToCombinations.get(i).put(CombinationType.CHOW, CombinationType.CHOW);

                /*
                 * If we already saw a duplicate before this one, then we have a pung.
                 */
                if (duplicateCount > 0)
                    this.indexToCombinations.get(i).put(CombinationType.PUNG, CombinationType.PUNG);

                duplicateCount++;

            } else if (previousTile.isNext(currentTile)) {
                /*
                 * Our previous tile precedes our current tile. Might be a chow.
                 */

                /*
                 * All duplicate tiles that precede the current tile can be used to form a chow.
                 * So, we must update the current tile's entry in the chow table to point to all these duplicates.
                 */
                for (int d = 0; d <= duplicateCount; d++)
                    this.chowTable.get(i).add(i - 1 - d);

                /*
                 * We already saw enough sequential tiles to form a chow.
                 */
                if (sequenceCount > 0)
                    this.indexToCombinations.put(
                            i,
                            new HashMap<>(Map.ofEntries(Map.entry(CombinationType.CHOW, CombinationType.CHOW)))
                    );

                sequenceCount++;
                duplicateCount = 0;

            } else {
                /*
                 * No combinations are possible. Counters are reset from here.
                 */
                duplicateCount = 0;
                sequenceCount = 0;
            }

            previousTile = currentTile;
        }

        this.winningHands = computeWinningHands(tiles.size() - 1, false);
    }

    /**
     * Recursive function that computes the potential winning hands from the tile set provided.
     * The chow table and the ITC are used to store information.
     * The recursion starts at the end of the tile set and proceeds until its beginning.
     * Associating a given combination with its last tile in the ITC and having the tile set sorted,
     * allows to skip 2 tiles if we see a pair or 3 tiles if we see a pung.
     * Tiles can form multiple combinations with different tiles and all those possibilities are accounted for.
     *
     * @param currentIndex The index of the tile where we are currently checking.
     * @param foundPair    Each winning hand can only have one pair, so a boolean is passed to keep track of the state.
     * @return All the hands that assure victory in the game with the current tile set.
     */
    private List<List<CombinationType>> computeWinningHands(int currentIndex, boolean foundPair) {
        // No more tiles. We have a Mahjong!
        // We return an empty list inside the list of winning hands
        if (currentIndex < 0)
            return Collections.singletonList(new ArrayList<>());

        // If null, there is no Mahjong
        Map<CombinationType, CombinationType> possibleCombinations = this.indexToCombinations.get(currentIndex);
        if (possibleCombinations == null)
            return new ArrayList<>();

        if (possibleCombinations.isEmpty())
            return computeWinningHands(currentIndex - 1, foundPair);

        List<List<CombinationType>> currentWinningHands = new ArrayList<>();

        CombinationType pair = possibleCombinations.get(CombinationType.PAIR);
        if (!foundPair && pair != null) {
            List<List<CombinationType>> possibleWinningHands =
                    computeWinningHands(currentIndex - 2, true);

            possibleWinningHands.forEach(hand -> hand.add(CombinationType.PAIR));
            currentWinningHands.addAll(possibleWinningHands);
        }

        CombinationType pung = possibleCombinations.get(CombinationType.PUNG);
        if (pung != null) {
            List<List<CombinationType>> possibleWinningHands =
                    computeWinningHands(currentIndex - 3, foundPair);

            possibleWinningHands.forEach(hand -> hand.add(CombinationType.PUNG));
            currentWinningHands.addAll(possibleWinningHands);
        }

        CombinationType chow = possibleCombinations.get(CombinationType.CHOW);
        if (chow != null) {
            Map<Integer, Map<CombinationType, CombinationType>> oldITC = this.indexToCombinations;
            Map<Integer, Map<CombinationType, CombinationType>> newITC = copyITC(this.indexToCombinations);

            Deque<Integer> possibleChowsL1 = chowTable.get(currentIndex);
            chowLoop:
            for (Integer currentChowL1 : possibleChowsL1) {
                Map<Integer, Map<CombinationType, CombinationType>> itcL1 = copyITC(newITC);
                setAsChowTile(currentChowL1, itcL1);

                Deque<Integer> possibleChowsL2 = chowTable.get(currentChowL1);
                for (Integer currentChowL2 : possibleChowsL2) {
                    this.indexToCombinations = copyITC(itcL1);
                    setAsChowTile(currentChowL2, this.indexToCombinations);

                    int freeIndex;
                    if (currentIndex - 1 > currentChowL1) {
                        freeIndex = currentIndex - 1;
                    } else if (currentChowL1 - 1 > currentChowL2) {
                        freeIndex = currentChowL1 - 1;
                    } else {
                        freeIndex = currentChowL2 - 1;
                    }

                    List<List<CombinationType>> possibleWinningHands = computeWinningHands(freeIndex, foundPair);
                    if (!possibleWinningHands.isEmpty()) {
                        possibleWinningHands.forEach(hand -> hand.add(CombinationType.CHOW));
                        currentWinningHands.addAll(possibleWinningHands);
                        break chowLoop;
                    }
                }
            }

            this.indexToCombinations = oldITC;
        }

        return currentWinningHands;
    }

    /**
     * Creates the chow table to keep track of the indices for all the possible chows in the hand.
     */
    private void createChowTable() {
        List<Deque<Integer>> chowTable = new ArrayList<>(MAX_TILES);
        for (int i = 0; i < MAX_TILES; i++) {
            Deque<Integer> chowIndexes = new ArrayDeque<>(MAX_CHOWS_PER_TILE);
            chowTable.add(chowIndexes);
        }
        this.chowTable = chowTable;
    }

    /**
     * Performs a deep-copy of a given itc.
     * This is useful because finding out the validity of a chow involves updating multiple positions of the itc.
     *
     * @param itc The current index-to-combinations structure to be copied.
     * @return The index-to-combinations copy.
     */
    private Map<Integer, Map<CombinationType, CombinationType>> copyITC(
            Map<Integer, Map<CombinationType, CombinationType>> itc) {

        Map<Integer, Map<CombinationType, CombinationType>> newITC = new HashMap<>();
        for (Map.Entry<Integer, Map<CombinationType, CombinationType>> entry : itc.entrySet()) {
            Map<CombinationType, CombinationType> value = new HashMap<>();
            for (Map.Entry<CombinationType, CombinationType> combination : entry.getValue().entrySet()) {
                value.put(combination.getKey(), combination.getValue());
            }
            newITC.put(entry.getKey(), value);
        }
        return newITC;
    }

    /**
     * This method sets a tile as part of a chow, invalidating any other combinations with said tile.
     * All pungs, chows and pairs should be invalidated, so its entries in itc should be cleared.
     * If it is not present in the itc, then an empty map should added, in order to not mistake it
     * with other tiles that do not belong to any combination
     *
     * @param index The index of the tile to be set.
     * @param itc   The current index-to-combinations structure to be updated.
     */
    private void setAsChowTile(int index, Map<Integer, Map<CombinationType, CombinationType>> itc) {
        Map<CombinationType, CombinationType> indexCombinations = itc.get(index);
        if (indexCombinations == null)
            itc.put(index, new HashMap<>());
        else
            indexCombinations.clear();

        itc.computeIfPresent(index + 1, (k, v) -> {
            v.remove(CombinationType.PAIR);
            return v;
        });

        itc.computeIfPresent(index + 2, (k, v) -> {
            v.remove(CombinationType.PUNG);
            return v;
        });
    }
}
