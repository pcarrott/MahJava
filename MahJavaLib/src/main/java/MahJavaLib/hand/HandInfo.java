package MahJavaLib.hand;

import java.util.*;

import MahJavaLib.Player.CombinationType;
import MahJavaLib.Tile;

public class HandInfo {

    private final List<Map<CombinationType, List<Tile>>> allPossibleHands;

    public HandInfo(Hand hand) {
        this.allPossibleHands = computeHands(hand.getHand());
        this.allPossibleHands.forEach(System.out::println);
    }

    private List<Map<CombinationType, List<Tile>>> computeHands(HashMap<Tile, Integer> tileCounter) {
        if (tileCounter.isEmpty())
            return Collections.singletonList(new HashMap<>());

        List<Map<CombinationType, List<Tile>>> currentHands = new ArrayList<>();
        Tile tile = (Tile) tileCounter.keySet().toArray()[0];
        Integer count = tileCounter.get(tile);

        boolean hasChow = this.tryChow(tile, tileCounter, currentHands);

        if (count == 1) {
            this.setCombinations(
                    tile, count, new ArrayList<>(), tileCounter, currentHands);

        } else if (count == 2) {
            this.setCombinations(
                    tile, count, Collections.singletonList(CombinationType.PAIR), tileCounter, currentHands);

        } else if (count == 3) {
            this.setCombinations(
                    tile, count, Collections.singletonList(CombinationType.PUNG), tileCounter, currentHands);

            if (!hasChow)
                this.setCombinations(
                        tile, count, Collections.singletonList(CombinationType.PAIR), tileCounter, currentHands);

        } else if (count == 4) {
            this.setCombinations(
                    tile, count, Arrays.asList(CombinationType.PAIR, CombinationType.PAIR), tileCounter, currentHands);

            if (!hasChow)
                this.setCombinations(
                        tile, count, Collections.singletonList(CombinationType.PUNG), tileCounter, currentHands);
        }

        return currentHands;
    }

    private boolean tryChow(
            Tile tile,
            HashMap<Tile, Integer> tileCounter,
            List<Map<CombinationType, List<Tile>>> currentHands) {

        boolean hasChow = false;
        for (ArrayList<Tile> chow : tile.getPossibleChowCombinations()) {
            boolean isChowPossible = true;

            List<Tile> tilesToRemove = new ArrayList<>(chow.size());

            for (Tile t : chow) {
                if (!tileCounter.containsKey(t)) {
                    isChowPossible = false;
                    break;
                }

                tilesToRemove.add(t);
            }

            if (!isChowPossible)
                continue;

            for (Tile t : tilesToRemove) {
                Integer c = tileCounter.get(t);
                if (c == 1)
                    tileCounter.remove(t);
                else
                    tileCounter.put(t, c - 1);
            }

            this.setCurrentHands(chow.get(0), Collections.singletonList(CombinationType.CHOW), tileCounter, currentHands);
            hasChow = true;

            for (Tile t : chow)
                tileCounter.merge(t, 1, Integer::sum);
        }

        return hasChow;
    }

    private void setCombinations(
            Tile tile,
            Integer count,
            List<CombinationType> combinations,
            HashMap<Tile, Integer> tileCounter,
            List<Map<CombinationType, List<Tile>>> currentHands) {

        tileCounter.remove(tile);
        this.setCurrentHands(tile, combinations, tileCounter, currentHands);
        tileCounter.put(tile, count);
    }

    private void setCurrentHands(
            Tile tile,
            List<CombinationType> combinations,
            HashMap<Tile, Integer> tileCounter,
            List<Map<CombinationType, List<Tile>>> currentHands) {

        List<Map<CombinationType, List<Tile>>> computedHands = computeHands(tileCounter);

        combinations.forEach(
                combination -> computedHands.forEach(hand -> hand.computeIfAbsent(combination, k -> new ArrayList<>()))
        );

        combinations.forEach(
                combination -> computedHands.stream().map(hand -> hand.get(combination)).forEach(l -> l.add(tile))
        );

        currentHands.addAll(computedHands);
    }

    public boolean removeTile(Tile tile) {

        return false;
    }

    public boolean addTile(Tile tile) {

        return false;
    }

    public List<Map<CombinationType, List<Tile>>> getAllPossibleHands() {
        return allPossibleHands;
    }
}
