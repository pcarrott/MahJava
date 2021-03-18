package MahJavaLib;

import java.util.*;

import MahJavaLib.MahjongPlayer.CombinationType;

public class HandInfo {

    List<List<CombinationType>> winningHands;

    // The list of tiles is assumed to be sorted
    public HandInfo(List<MahjongTile> tiles) {
        Map<Integer, Map<CombinationType, CombinationType>> indexToCombination = new HashMap<>();

        MahjongTile previousTile = tiles.get(0);
        int duplicateCount = 0;
        for (int i = 1; i < tiles.size(); i++) {
            MahjongTile currentTile = tiles.get(i);

            if (currentTile.equals(previousTile)) {
                indexToCombination.put(
                        i,
                        new HashMap<>(Map.ofEntries(Map.entry(CombinationType.PAIR, CombinationType.PAIR)))
                );

                if (duplicateCount == 1) {
                    indexToCombination.get(i).put(CombinationType.PUNG, CombinationType.PUNG);
                } else {
                    duplicateCount++;
                }
            } else {
                duplicateCount = 0;
            }

            previousTile = currentTile;
        }

        this.winningHands = getWinningHands(tiles.size() - 1, indexToCombination);
    }

    private List<List<CombinationType>> getWinningHands(
            int currentIndex,
            Map<Integer, Map<CombinationType, CombinationType>> indexToCombination) {

        // No more tiles. We have a Mahjong!
        if (currentIndex < 0) {
            return Arrays.asList(new ArrayList<>());
        }

        // If null, there is no Mahjong
        Map<CombinationType, CombinationType> possibleCombinations = indexToCombination.get(currentIndex);
        if (possibleCombinations == null) {
            return null;
        }

        // Call recursively depending on the possible combinations
        List<List<CombinationType>> winningHands = new ArrayList<>();

        // CombinationType chow = possibleCombinations.get(CombinationType.CHOW);
        // if (chow != null) {
        //
        // }

        CombinationType pair = possibleCombinations.get(CombinationType.PAIR);
        if (pair != null) {
            List<List<CombinationType>> possibleWinningHands =
                    getWinningHands(currentIndex - 2, indexToCombination);

            if (possibleWinningHands != null) {
                possibleWinningHands.forEach(hand -> hand.add(CombinationType.PAIR));
                winningHands.addAll(possibleWinningHands);
            }
        }

        CombinationType pung = possibleCombinations.get(CombinationType.PUNG);
        if (pung != null) {
            List<List<CombinationType>> possibleWinningHands =
                    getWinningHands(currentIndex - 3, indexToCombination);

            if (possibleWinningHands != null) {
                possibleWinningHands.forEach(hand -> hand.add(CombinationType.PUNG));
                winningHands.addAll(possibleWinningHands);
            }
        }

        // CombinationType kong = possibleCombinations.get(CombinationType.KONG);
        // if (kong != null) {
        //
        // }

        return winningHands;
    }

    public List<List<CombinationType>> getWinningHands() {
        return this.winningHands;
    }
}
