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
    private Map<Tile, Tile> blacklist = new HashMap<>();
    private Map<Tile, Tile> visited = new HashMap<>();

    public HandInfo(Hand hand) {
        this.allPossibleHands = computeHands(hand.getHand()).stream().distinct().collect(Collectors.toList());
        System.out.println(this.allPossibleHands.size());
        this.allPossibleHands.forEach(System.out::println);
    }

    private List<Map<CombinationType, Map<Tile, Integer>>> computeHands(HashMap<Tile, Integer> tileCounter) {

        System.out.println("computeHands - Current tile counter: " + tileCounter);
        System.out.println("computeHands - Current blacklist: " + this.blacklist);
        System.out.println("computeHands - Current visited: " + this.visited);

        if (tileCounter.isEmpty()) {
            System.out.println("computeHands - Tile counter is empty");
            return Collections.singletonList(new HashMap<>(Map.ofEntries(
                    Map.entry(CombinationType.PAIR, new HashMap<>()),
                    Map.entry(CombinationType.PUNG, new HashMap<>()),
                    Map.entry(CombinationType.KONG, new HashMap<>()),
                    Map.entry(CombinationType.CHOW, new HashMap<>())
            )));
        }

        Tile tile = null;
        for (Tile t : tileCounter.keySet())
            if (!this.visited.containsKey(t)) {
                tile = t;
                break;
            }

        if (tile == null) {
            System.out.println("computeHands - All tiles have been visited");
            return new ArrayList<>();
        }

        System.out.println("computeHands - Current tile is " + tile);
        Integer count = tileCounter.get(tile);
        List<Map<CombinationType, Map<Tile, Integer>>> currentHands = new ArrayList<>();

        /*
         * Since the piece is in hand, we must include the scenarios where it is possible to make a chow with it
         */
        this.dankChow(tile, tileCounter, currentHands);

        if (count == 1) {
            System.out.println("computeHands - After dankChow");
            this.setCurrentHands(tile, new ArrayList<>(), tileCounter, currentHands);

        } else if (count == 2) {
            /*
             * Included scenarios with tryChow:
             *   - chow + (any combo where count == 1)
             * Scenarios left to be included:
             *   - pair
             */

            this.setCombinations(
                    tile, count, Collections.singletonList(CombinationType.PAIR), tileCounter, currentHands);

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
                    tile, count, Collections.singletonList(CombinationType.PUNG), tileCounter, currentHands);

            this.setCombinations(
                    tile, count, Collections.singletonList(CombinationType.PAIR), tileCounter, currentHands);

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
                    tile, count, Arrays.asList(CombinationType.PAIR, CombinationType.PAIR), tileCounter, currentHands);

            this.setCombinations(
                    tile, count, Collections.singletonList(CombinationType.PUNG), tileCounter, currentHands);
        }

        return currentHands;
    }

    private void dankChow(
            Tile tile,
            HashMap<Tile, Integer> tileCounter,
            List<Map<CombinationType, Map<Tile, Integer>>> currentHands) {

        System.out.println("dankChow - Current tile is " + tile);

        Tile first = tile;
        List<Tile> currentChow = tile.asChow();
        int skippedTiles = 0;

        Map<Tile, Tile> old = new HashMap<>(this.blacklist);
        //Map<Tile, Tile> oldV = new HashMap<>(this.visited);
        this.blacklist = new HashMap<>();
        while (!currentChow.isEmpty() && skippedTiles < 3) {
            if (!currentChow.stream().allMatch(t -> tileCounter.containsKey(t) && !this.blacklist.containsKey(t)) ||
                    this.blacklist.containsKey(tile))
                break;

            this.blacklist.put(tile, tile);
            this.visited = new HashMap<>();

            for (Tile t : currentChow) {
                Integer c = tileCounter.get(t);
                if (c == 1)
                    tileCounter.remove(t);
                else
                    tileCounter.put(t, c - 1);
            }

            System.out.println("dankChow - First chow: " + currentChow);
            // this.setCurrentHands(kek, Collections.singletonList(CombinationType.CHOW), tileCounter, currentHands);
            this.setCurrentHands(tile, Collections.singletonList(CombinationType.CHOW), tileCounter, currentHands);

            for (Tile t : currentChow)
                tileCounter.merge(t, 1, Integer::sum);

            tile = currentChow.get(1);
            currentChow = tile.asChow();
            skippedTiles++;
        }

        this.blacklist = old;
        //this.visited = oldV;
        this.visited.put(first, first);
        //first.asChow().forEach(t -> this.visited.put(t, t));
    }

    private void setCombinations(
            Tile tile,
            Integer count,
            List<CombinationType> combinations,
            HashMap<Tile, Integer> tileCounter,
            List<Map<CombinationType, Map<Tile, Integer>>> currentHands) {

        tileCounter.remove(tile);
        this.setCurrentHands(tile, combinations, tileCounter, currentHands);
        tileCounter.put(tile, count);
    }

    private void setCurrentHands(
            Tile tile,
            List<CombinationType> combinations,
            HashMap<Tile, Integer> tileCounter,
            List<Map<CombinationType, Map<Tile, Integer>>> currentHands) {

        List<Map<CombinationType, Map<Tile, Integer>>> computedHands = computeHands(tileCounter);

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
