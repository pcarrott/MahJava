package MahJavaLib;

import java.util.*;

import MahJavaLib.MahjongPlayer.CombinationType;

public class HandInfo {

    static final int MAX_TILES = 14;
    static final int MAX_CHOWS_PER_TILE = 4;

    List<List<CombinationType>> winningHands;

    // The list of tiles is assumed to be sorted
    public HandInfo(List<MahjongTile> tiles) {
        Map<Integer, Map<CombinationType, CombinationType>> indexToCombination = new HashMap<>();
        List<Deque<Integer>> chowTable = this.createChowTable();

        MahjongTile previousTile = tiles.get(0);
        int duplicateCount = 0;
        int sequenceCount = 0;

        for (int i = 1; i < tiles.size(); i++) {
            MahjongTile currentTile = tiles.get(i);

            if (currentTile.equals(previousTile)) {
                // We leave the previous tile with only one of its possible chows
                Deque<Integer> previousChows = chowTable.get(i - 1);
                Deque<Integer> currentChows = chowTable.get(i);
                while (previousChows.size() > 1)
                    currentChows.add(previousChows.pop());

                int hops = 0;
                while (!currentChows.isEmpty()) {
                    currentChows = chowTable.get(currentChows.getFirst());
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
                    chowTable.get(i).add(i - 1 - d);

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

        this.winningHands = computeWinningHands(tiles.size() - 1, false, chowTable, indexToCombination);
    }

    private List<List<CombinationType>> computeWinningHands(
            int currentIndex,
            boolean foundPair,
            List<Deque<Integer>> chowTable,
            Map<Integer, Map<CombinationType, CombinationType>> indexToCombination) {

        // No more tiles. We have a Mahjong!
        // We return an empty list inside the list of winning hands
        if (currentIndex < 0)
            return Arrays.asList(new ArrayList<>());

        // If null, there is no Mahjong
        Map<CombinationType, CombinationType> possibleCombinations = indexToCombination.get(currentIndex);
        if (possibleCombinations == null)
            return new ArrayList<>();

        if (possibleCombinations.isEmpty())
            return computeWinningHands(currentIndex - 1, foundPair, chowTable, indexToCombination);

        List<List<CombinationType>> winningHands = new ArrayList<>();

        CombinationType pair = possibleCombinations.get(CombinationType.PAIR);
        if (!foundPair && pair != null) {
            List<List<CombinationType>> possibleWinningHands =
                    computeWinningHands(currentIndex - 2, true, chowTable, indexToCombination);

            possibleWinningHands.forEach(hand -> hand.add(CombinationType.PAIR));
            winningHands.addAll(possibleWinningHands);
        }

        CombinationType pung = possibleCombinations.get(CombinationType.PUNG);
        if (pung != null) {
            List<List<CombinationType>> possibleWinningHands =
                    computeWinningHands(currentIndex - 3, foundPair, chowTable, indexToCombination);

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
            List<List<CombinationType>> possibleWinningHands;
            Map<Integer, Map<CombinationType, CombinationType>> newIndexToCombination =
                    copyIndexToCombination(indexToCombination);

            Deque<Integer> possibleChowsL1 = chowTable.get(currentIndex);
            for (Integer currentChowL1 : possibleChowsL1) {
                Map<CombinationType, CombinationType> currentChowL1Combinations =
                        newIndexToCombination.get(currentChowL1);

                if (currentChowL1Combinations == null)
                    newIndexToCombination.put(currentChowL1, new HashMap<>());
                else
                    currentChowL1Combinations.clear();

                newIndexToCombination.computeIfPresent(currentChowL1 + 1, (k, v) -> {
                    v.remove(CombinationType.PAIR);
                    return v;
                });
                newIndexToCombination.computeIfPresent(currentChowL1 + 2, (k, v) -> {
                    v.remove(CombinationType.PUNG);
                    return v;
                });

                Deque<Integer> possibleChowsL2 = chowTable.get(currentChowL1);
                for (Integer currentChowL2 : possibleChowsL2) {
                    Map<CombinationType, CombinationType> currentChowL2Combinations =
                            newIndexToCombination.get(currentChowL2);

                    if (currentChowL2Combinations == null)
                        newIndexToCombination.put(currentChowL2, new HashMap<>());
                    else
                        currentChowL2Combinations.clear();

                    newIndexToCombination.computeIfPresent(currentChowL2 + 1, (k, v) -> {
                        v.remove(CombinationType.PAIR);
                        return v;
                    });
                    newIndexToCombination.computeIfPresent(currentChowL2 + 2, (k, v) -> {
                        v.remove(CombinationType.PUNG);
                        return v;
                    });

                    int nextIndex;
                    if (currentIndex - 1 > currentChowL1) {
                        nextIndex = currentIndex - 1;
                    } else if (currentChowL1 - 1 > currentChowL2) {
                        nextIndex = currentChowL1 - 1;
                    } else {
                        nextIndex = currentChowL2 - 1;
                    }

                    possibleWinningHands = computeWinningHands(nextIndex, foundPair, chowTable, newIndexToCombination);
                    if (!possibleWinningHands.isEmpty()) {
                        possibleWinningHands.forEach(hand -> hand.add(CombinationType.CHOW));
                        winningHands.addAll(possibleWinningHands);
                        return winningHands;
                    }
                }
            }
        }

        return winningHands;
    }

    private List<Deque<Integer>> createChowTable() {
        List<Deque<Integer>> chowTable = new ArrayList<>(MAX_TILES);
        for (int i = 0; i < MAX_TILES; i++) {
            Deque<Integer> chowIndexes = new ArrayDeque<>(MAX_CHOWS_PER_TILE);
            chowTable.add(chowIndexes);
        }
        return chowTable;
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

    public List<List<CombinationType>> getWinningHands() {
        return this.winningHands;
    }
}
