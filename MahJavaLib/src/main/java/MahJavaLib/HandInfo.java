package MahJavaLib;

import java.util.*;

import MahJavaLib.MahjongPlayer.CombinationType;

public class HandInfo {

    static final int MAX_TILES = 14;
    static final int MAX_CHOWS_PER_TILE = 4;

    private Map<Integer, Map<CombinationType, CombinationType>> indexToCombinations = new HashMap<>();
    private final List<List<CombinationType>> winningHands;
    private List<Deque<Integer>> chowTable;

    // The list of tiles is assumed to be sorted
    public HandInfo(List<MahjongTile> tiles) {
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

                this.indexToCombinations.put(
                    i,
                    new HashMap<>(Map.ofEntries(Map.entry(CombinationType.PAIR, CombinationType.PAIR)))
                );

                if (sequenceCount > 1 && hops > 1)
                    this.indexToCombinations.get(i).put(CombinationType.CHOW, CombinationType.CHOW);

                if (duplicateCount > 0)
                    this.indexToCombinations.get(i).put(CombinationType.PUNG, CombinationType.PUNG);

                duplicateCount++;

            } else if (previousTile.isNext(currentTile)) {
                // If it is the next tile, we may have a chow so we update the current tile's entry
                // with all the duplicate tiles indexes we got until here
                for (int d = 0; d <= duplicateCount; d++)
                    this.chowTable.get(i).add(i - 1 - d);

                if (sequenceCount > 0)
                    this.indexToCombinations.put(
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

        this.winningHands = computeWinningHands(tiles.size() - 1, false);
    }

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

        List<List<CombinationType>> winningHands = new ArrayList<>();

        CombinationType pair = possibleCombinations.get(CombinationType.PAIR);
        if (!foundPair && pair != null) {
            List<List<CombinationType>> possibleWinningHands =
                    computeWinningHands(currentIndex - 2, true);

            possibleWinningHands.forEach(hand -> hand.add(CombinationType.PAIR));
            winningHands.addAll(possibleWinningHands);
        }

        CombinationType pung = possibleCombinations.get(CombinationType.PUNG);
        if (pung != null) {
            List<List<CombinationType>> possibleWinningHands =
                    computeWinningHands(currentIndex - 3, foundPair);

            possibleWinningHands.forEach(hand -> hand.add(CombinationType.PUNG));
            winningHands.addAll(possibleWinningHands);
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
                        winningHands.addAll(possibleWinningHands);
                        break chowLoop;
                    }
                }
            }

            this.indexToCombinations = oldITC;
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

    public List<List<CombinationType>> getWinningHands() {
        return this.winningHands;
    }
}
