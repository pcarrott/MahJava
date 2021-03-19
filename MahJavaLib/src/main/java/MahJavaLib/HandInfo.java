package MahJavaLib;

import java.util.*;

import MahJavaLib.MahjongPlayer.CombinationType;

public class HandInfo {

    static final int MAX_TILES = 14;
    static final int MAX_CHOWS_PER_TILE = 4;

    private final List<List<CombinationType>> winningHands;
    private List<Deque<Integer>> chowTable;

    // The list of tiles is assumed to be sorted
    public HandInfo(List<MahjongTile> tiles) {
        Map<Integer, Map<CombinationType, CombinationType>> indexToCombination = new HashMap<>();
        this.createChowTable();

        MahjongTile previousTile = tiles.get(0);
        int duplicateCount = 0;
        int sequenceCount = 0;

        for (int i = 1; i < tiles.size(); i++) {
            MahjongTile currentTile = tiles.get(i);

            if (currentTile.equals(previousTile)) {
                // We leave the previous tile with only one of its possible chows
                Deque<Integer> previousChows = this.chowTable.get(i - 1);
                Deque<Integer> currentChows = this.chowTable.get(i);
                while (previousChows.size() > 1)
                    currentChows.add(previousChows.pop());

                int hops = 0;
                while (!currentChows.isEmpty()) {
                    currentChows = this.chowTable.get(currentChows.getFirst());
                    hops++;
                }

                indexToCombination.put(
                    i,
                    new HashMap<>(Map.ofEntries(Map.entry(CombinationType.PAIR, CombinationType.PAIR)))
                );

                if (sequenceCount > 1 && hops > 1)
                    indexToCombination.get(i).put(CombinationType.CHOW, CombinationType.CHOW);

                if (duplicateCount > 0)
                    indexToCombination.get(i).put(CombinationType.PUNG, CombinationType.PUNG);

                duplicateCount++;

            } else if (previousTile.isNext(currentTile)) {
                // If it is the next tile, we may have a chow so we update the current tile's entry
                // with all the duplicate tiles indexes we got until here
                for (int d = 0; d <= duplicateCount; d++)
                    this.chowTable.get(i).add(i - 1 - d);

                if (sequenceCount > 0)
                    indexToCombination.put(
                        i,
                        new HashMap<>(Map.ofEntries(Map.entry(CombinationType.CHOW, CombinationType.CHOW)))
                    );

                sequenceCount++;
                duplicateCount = 0;

            } else {
                duplicateCount = 0;
                sequenceCount = 0;
            }

            previousTile = currentTile;
        }

        this.winningHands = computeWinningHands(tiles.size() - 1, false, indexToCombination);
    }

    private List<List<CombinationType>> computeWinningHands(
            int currentIndex,
            boolean foundPair,
            Map<Integer, Map<CombinationType, CombinationType>> indexToCombination) {

        // No more tiles. We have a Mahjong!
        // We return an empty list inside the list of winning hands
        if (currentIndex < 0)
            return Collections.singletonList(new ArrayList<>());

        // If null, there is no Mahjong
        Map<CombinationType, CombinationType> possibleCombinations = indexToCombination.get(currentIndex);
        if (possibleCombinations == null)
            return new ArrayList<>();

        if (possibleCombinations.isEmpty())
            return computeWinningHands(currentIndex - 1, foundPair, indexToCombination);

        List<List<CombinationType>> winningHands = new ArrayList<>();

        CombinationType pair = possibleCombinations.get(CombinationType.PAIR);
        if (!foundPair && pair != null) {
            List<List<CombinationType>> possibleWinningHands =
                    computeWinningHands(currentIndex - 2, true, indexToCombination);

            possibleWinningHands.forEach(hand -> hand.add(CombinationType.PAIR));
            winningHands.addAll(possibleWinningHands);
        }

        CombinationType pung = possibleCombinations.get(CombinationType.PUNG);
        if (pung != null) {
            List<List<CombinationType>> possibleWinningHands =
                    computeWinningHands(currentIndex - 3, foundPair, indexToCombination);

            possibleWinningHands.forEach(hand -> hand.add(CombinationType.PUNG));
            winningHands.addAll(possibleWinningHands);
        }

        // CombinationType kong = possibleCombinations.get(CombinationType.KONG);
        // if (kong != null) {
        //
        // }

        // Needs to be the last because of the return statement
        // All other combinations were checked already so this is alright
        CombinationType chow = possibleCombinations.get(CombinationType.CHOW);
        if (chow != null) {
            Map<Integer, Map<CombinationType, CombinationType>> newIndexToCombination =
                    copyIndexToCombination(indexToCombination);

            winningHands = expandPossibleChows(
                    2, currentIndex, currentIndex - 1, foundPair,
                    winningHands, newIndexToCombination);
        }

        return winningHands;
    }

    private void createChowTable() {
        List<Deque<Integer>> chowTable = new ArrayList<>(MAX_TILES);
        for (int i = 0; i < MAX_TILES; i++) {
            Deque<Integer> chowIndexes = new ArrayDeque<>(MAX_CHOWS_PER_TILE);
            chowTable.add(chowIndexes);
        }
        this.chowTable = chowTable;
    }

    private Map<Integer, Map<CombinationType, CombinationType>> copyIndexToCombination(
            Map<Integer, Map<CombinationType, CombinationType>> indexToCombination) {

        Map<Integer, Map<CombinationType, CombinationType>> newIndexToCombination = new HashMap<>();
        for (Map.Entry<Integer, Map<CombinationType, CombinationType>> entry : indexToCombination.entrySet()) {
            Map<CombinationType, CombinationType> value = new HashMap<>();
            for (Map.Entry<CombinationType, CombinationType> combination : entry.getValue().entrySet()) {
                value.put(combination.getKey(), combination.getValue());
            }
            newIndexToCombination.put(entry.getKey(), value);
        }
        return newIndexToCombination;
    }

    private List<List<CombinationType>> expandPossibleChows(
            int level,
            int index,
            int freeIndex,
            boolean foundPair,
            List<List<CombinationType>> winningHands,
            Map<Integer, Map<CombinationType, CombinationType>> indexToCombination) {

        if (level < 1) {
            List<List<CombinationType>> possibleWinningHands =
                    computeWinningHands(freeIndex, foundPair, indexToCombination);
            if (!possibleWinningHands.isEmpty()) {
                possibleWinningHands.forEach(hand -> hand.add(CombinationType.CHOW));
                winningHands.addAll(possibleWinningHands);
                return winningHands;
            }
        }

        Deque<Integer> possibleChows = this.chowTable.get(index);
        for (Integer currentChow : possibleChows) {
            Map<CombinationType, CombinationType> currentChowCombinations =
                    indexToCombination.get(currentChow);

            if (currentChowCombinations == null)
                indexToCombination.put(currentChow, new HashMap<>());
            else
                currentChowCombinations.clear();

            indexToCombination.computeIfPresent(currentChow + 1, (k, v) -> {
                v.remove(CombinationType.PAIR);
                return v;
            });
            indexToCombination.computeIfPresent(currentChow + 2, (k, v) -> {
                v.remove(CombinationType.PUNG);
                return v;
            });

            if (freeIndex == currentChow)
                freeIndex = currentChow - 1;

            return expandPossibleChows(
                    level - 1, currentChow, freeIndex, foundPair,
                    winningHands, indexToCombination);
        }

        return winningHands;
    }

    public List<List<CombinationType>> getWinningHands() {
        return this.winningHands;
    }
}
