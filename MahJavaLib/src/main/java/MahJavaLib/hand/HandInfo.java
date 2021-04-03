package MahJavaLib.hand;

import java.util.*;
import java.util.stream.Collectors;

import MahJavaLib.Player.CombinationType;
import MahJavaLib.Tile;

/**
 * This info structure allows us to record all the major information about each hand
 * as the game progresses (ex: how many chows/pungs/kongs does this hand have, how many tiles
 * of each type, etc.)
 * This structure should be created right at the start of each game, with the starting hand
 * and it should be incrementally updated as the game progresses.
 */
public class HandInfo {

    private final List<Map<CombinationType, Map<Tile, Integer>>> allPossibleHands;
    private final Map<Integer, Map<Tile, Tile>> visitedByLevel = new HashMap<>();

    public HandInfo(Hand hand) {
        this.visitedByLevel.put(0, new HashMap<>());
        this.allPossibleHands = computeHands(hand.getHand(), 0).stream().distinct().collect(Collectors.toList());
        System.out.println(this.allPossibleHands.size());
        this.allPossibleHands.stream().map(h->h.get(CombinationType.CHOW)).forEach(System.out::println);
    }

    private List<Map<CombinationType, Map<Tile, Integer>>> computeHands(HashMap<Tile, Integer> tileCounter, int chowLevel) {

        if (tileCounter.isEmpty())
            return Collections.singletonList(new HashMap<>(Map.ofEntries(
                    Map.entry(CombinationType.PAIR, new HashMap<>()),
                    Map.entry(CombinationType.PUNG, new HashMap<>()),
                    Map.entry(CombinationType.KONG, new HashMap<>()),
                    Map.entry(CombinationType.CHOW, new HashMap<>())
            )));


        Tile tile = null;
        for (Tile t : tileCounter.keySet())
            if (!this.visitedByLevel.get(chowLevel).containsKey(t)) {
                tile = t;
                break;
            }
        if (tile == null)
            return Collections.singletonList(new HashMap<>(Map.ofEntries(
                    Map.entry(CombinationType.PAIR, new HashMap<>()),
                    Map.entry(CombinationType.PUNG, new HashMap<>()),
                    Map.entry(CombinationType.KONG, new HashMap<>()),
                    Map.entry(CombinationType.CHOW, new HashMap<>())
            )));

        Integer count = tileCounter.get(tile);
        List<Map<CombinationType, Map<Tile, Integer>>> currentHands = new ArrayList<>();

        tryChow(tile, tileCounter, currentHands, chowLevel);

        if (count == 1) {
            this.setCurrentHands(
                    tile, new ArrayList<>(), tileCounter, currentHands, chowLevel);

        } else if (count == 2) {
            /*
             * Included scenarios with tryChow:
             *   - chow + (any combo where count == 1)
             * Scenarios left to be included:
             *   - pair
             */

            this.setCombinations(
                    tile, count, Collections.singletonList(CombinationType.PAIR), tileCounter, currentHands, chowLevel);

        } else if (count == 3) {
            /*
             * Included scenarios with tryChow:
             *   - chow + (no-chow + no-chow)
             *   - chow + (any combo where count == 2)
             * Scenarios left to be included:
             *   - no-chow + pair
             *   - pung
             */

            this.setCombinations(
                    tile, count, Collections.singletonList(CombinationType.PUNG), tileCounter, currentHands, chowLevel);

            this.setCombinations(
                    tile, count, Collections.singletonList(CombinationType.PAIR), tileCounter, currentHands, chowLevel);

        } else if (count == 4) {
            /*
             * Included scenarios with tryChow:
             *   - chow + (no-chow + no-chow + no-chow)
             *   - chow + (any combo where chow == 3)
             * Scenarios left to be included:
             *   - no-chow + pung
             *   - pair + pair
             *   - kong TODO
             */

            this.setCombinations(
                    tile, count, Arrays.asList(CombinationType.PAIR, CombinationType.PAIR), tileCounter, currentHands, chowLevel);

            this.setCombinations(
                    tile, count, Collections.singletonList(CombinationType.PUNG), tileCounter, currentHands, chowLevel);
        }

        return currentHands;
    }

    private void tryChow(
            Tile tile,
            HashMap<Tile, Integer> tileCounter,
            List<Map<CombinationType, Map<Tile, Integer>>> currentHands,
            int chowLevel) {


        Tile currentTile = tile;
        List<Tile> chow = currentTile.asChow();
        int skippedTiles = 0;

        this.visitedByLevel.get(chowLevel).put(tile, tile);
        chow.forEach(t -> this.visitedByLevel.get(chowLevel).put(t, t));
        this.visitedByLevel.put(chowLevel + 1, new HashMap<>(this.visitedByLevel.get(chowLevel)));

        while (!chow.isEmpty() && skippedTiles < 3) {

            if (!chow.stream().allMatch(tileCounter::containsKey))
                break;

            for (Tile t : chow) {
                int c = tileCounter.get(t);
                if (c == 1)
                    tileCounter.remove(t);
                else
                    tileCounter.put(t, c - 1);
            }

            this.setCurrentHands(
                    currentTile, Collections.singletonList(CombinationType.CHOW), tileCounter, currentHands, chowLevel + 1);

            chow.forEach(t -> tileCounter.merge(t, 1, Integer::sum));

            currentTile = chow.get(1);
            chow = currentTile.asChow();
            skippedTiles++;
            this.visitedByLevel.put(chowLevel + 1, new HashMap<>(this.visitedByLevel.get(chowLevel)));
        }
    }

    private void setCombinations(
            Tile tile,
            Integer count,
            List<CombinationType> combinations,
            HashMap<Tile, Integer> tileCounter,
            List<Map<CombinationType, Map<Tile, Integer>>> currentHands,
            int chowLevel) {

        tileCounter.remove(tile);
        this.setCurrentHands(tile, combinations, tileCounter, currentHands, chowLevel);
        tileCounter.put(tile, count);
    }

    private void setCurrentHands(
            Tile tile,
            List<CombinationType> combinations,
            HashMap<Tile, Integer> tileCounter,
            List<Map<CombinationType, Map<Tile, Integer>>> currentHands,
            int chowLevel) {

        List<Map<CombinationType, Map<Tile, Integer>>> computedHands = computeHands(tileCounter, chowLevel);

        combinations.forEach(combination ->
                computedHands.stream()
                        .map(hand -> hand.get(combination))
                        .forEach(m -> m.merge(tile, 1, Integer::sum))
        );

        currentHands.addAll(computedHands);
    }

    public boolean removeTile(Tile tile) {

        return false;
    }

    public boolean addTile(Tile tile) {

        return false;
    }

    public List<Map<CombinationType, Map<Tile, Integer>>> getAllPossibleHands() {
        return allPossibleHands;
    }
}
