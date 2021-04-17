package MahJavaLib.hand;

import MahJavaLib.Player.CombinationType;
import MahJavaLib.Tile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This info structure allows us to record all the major information about each hand
 * as the game progresses (ex: how many chows/pungs/kongs does this hand have, how many tiles
 * of each type, etc.)
 * This structure should be created right at the start of each game, with the starting hand
 * and it should be incrementally updated as the game progresses.
 */
public class HandInfo {

    private List<Map<CombinationType, Map<Tile, Integer>>> allPossibleHands;

    public HandInfo() {
        this.allPossibleHands = Collections.singletonList(new HashMap<>(Map.ofEntries(
                Map.entry(CombinationType.NONE, new HashMap<>()),
                Map.entry(CombinationType.PAIR, new HashMap<>()),
                Map.entry(CombinationType.PUNG, new HashMap<>()),
                Map.entry(CombinationType.KONG, new HashMap<>()),
                Map.entry(CombinationType.CHOW, new HashMap<>())
        )));
    }

    public void removeTile(Tile tile) {

    }

    public void addTile(Tile tile) {
        var allChows = tile.getPossibleChowCombinations();
        List<Map<CombinationType, Map<Tile, Integer>>> res = new ArrayList<>();

        for (var hand : this.allPossibleHands) {
            // Chows with the tile that the hand currently has
            var chows = allChows.stream()
                    .filter(chow -> hand.get(CombinationType.CHOW).containsKey(chow.get(0)))
                    .collect(Collectors.toList());

            // Chows that can be made with tile and other tiles that belong to no combination
            List<List<Tile>> possibleChows = allChows.stream()
                    .filter(chow -> chow.stream()
                            .filter(t -> !t.equals(tile))
                            .allMatch(hand.get(CombinationType.NONE)::containsKey))
                    .collect(Collectors.toList());

            boolean hasCombinations = chows.size() != 0;

            if (hand.get(CombinationType.NONE).containsKey(tile)) {
                hasCombinations = true;

                // Scenario: 1 None          -> 1 Pair
                // Scenario: 1 None + 1 Chow -> 1 Pair + 1 Chow
                // Scenario: 1 None + 2 Chow -> 1 Pair + 2 Chow
                var handWithPair = this.copyHand(hand);
                handWithPair.get(CombinationType.PAIR).put(tile, 1);
                handWithPair.get(CombinationType.NONE).remove(tile);
                res.add(handWithPair);

            } if (hand.get(CombinationType.PAIR).containsKey(tile)) {
                hasCombinations = true;

                // Scenario: 1 Pair          -> 1 Pung
                // Scenario: 1 Pair + 1 Chow -> 1 Pung + 1 Chow
                var handWithPung = this.copyHand(hand);
                handWithPung.get(CombinationType.PUNG).put(tile, 1);
                handWithPung.get(CombinationType.PAIR).remove(tile);
                res.add(handWithPung);

                // If 0 Chow
                // Scenario: 1 Pair          -> 1 Pair + 1 Chow
                if (chows.size() == 0) {
                    possibleChows.forEach(chow -> {
                        var handWithChow = this.copyHand(hand);
                        handWithChow.get(CombinationType.CHOW).put(chow.get(0), 1);
                        res.add(handWithChow);
                    });
                }

            } if (hand.get(CombinationType.PUNG).containsKey(tile)) {
                hasCombinations = true;

                // Scenario: 1 Pung          -> 1 None + 1 Pung
                var handWithNone = this.copyHand(hand);
                handWithNone.get(CombinationType.NONE).put(tile, 1);
                res.add(handWithNone);

            } if (chows.size() != 0) {
                // If has no possible chows
                // Scenario:          1 Chow -> 1 None + 1 Chow
                // Scenario:          2 Chow -> 1 None + 2 Chow
                // Scenario:          3 Chow -> 1 None + 3 Chow
                if (possibleChows.size() == 0) {
                    var handWithNone = this.copyHand(hand);
                    handWithNone.get(CombinationType.NONE).put(tile, 1);
                    res.add(handWithNone);
                }

                // If 1 Chow + 0 None and at least 1 tile from the chow(s) has another possible chow
                // Scenario:          1 Chow -> 1 Pair
                // Scenario: 1 Pair + 1 Chow -> 2 Pair
                if (chows.size() == 1 &&  !hand.get(CombinationType.NONE).containsKey(tile) &&
                    chows.get(0).stream()
                            .flatMap(t -> t.getPossibleChowCombinations().stream())
                            .peek(c -> c.removeAll(chows.get(0)))
                            .distinct()
                            .noneMatch(c -> !c.isEmpty() && c.stream().allMatch(hand.get(CombinationType.NONE)::containsKey))
                ) {
                    List<Tile> chow = chows.get(0);
                    var handWithPair = this.copyHand(hand);
                    handWithPair.get(CombinationType.PAIR).merge(tile, 1, Integer::sum);
                    handWithPair.get(CombinationType.CHOW).remove(chow.get(0));
                    chow.stream()
                            .filter(t -> !t.equals(tile))
                            .forEach(t -> handWithPair.get(CombinationType.NONE).put(t, 1));
                    res.add(handWithPair);
                }

                // For each possible Chow
                // Scenario:          1 Chow ->          2 Chow
                // Scenario:          2 Chow ->          3 Chow
                // Scenario: 1 Pair + 1 Chow -> 1 Pair + 2 Chow
                // Scenario:          3 Chow ->          4 Chow
                possibleChows.forEach(chow -> {
                    var handWithChow = this.copyHand(hand);
                    handWithChow.get(CombinationType.CHOW).merge(chow.get(0), 1, Integer::sum);
                    res.add(handWithChow);
                });

            } if (!hasCombinations) {
                // Scenario:                 -> 1 None
                if (possibleChows.size() == 0) {
                    var handWithNone = this.copyHand(hand);
                    handWithNone.get(CombinationType.NONE).put(tile, 1);
                    res.add(handWithNone);
                }

                // For each possible Chow
                // Scenario:                 ->          1 Chow
                possibleChows.forEach(chow -> {
                    var handWithChow = this.copyHand(hand);
                    handWithChow.get(CombinationType.CHOW).merge(chow.get(0), 1, Integer::sum);
                    chow.forEach(t -> handWithChow.get(CombinationType.NONE).remove(t));
                    res.add(handWithChow);
                });

                // Replace already existing chows with new possible chows
                allChows.forEach(newChow -> {
                    // Tiles left to complete this chow
                    List<Tile> remainingTiles = newChow.stream()
                            .filter(t -> !t.equals(tile))
                            .collect(Collectors.toList());

                    Tile firstTile = remainingTiles.get(0);
                    Tile secondTile = remainingTiles.get(1);

                    // Consider existing chows that only contain the first remaining tile
                    this.replaceChowsWithOneCommonTile(hand, tile, firstTile, secondTile, newChow, res);

                    // Consider existing chows that only contain the second remaining tile
                    this.replaceChowsWithOneCommonTile(hand, tile, secondTile, firstTile, newChow, res);

                    // Consider existing chows that contain both remaining tiles
                    List<List<Tile>> commonChows = firstTile.getPossibleChowCombinations().stream().filter(
                            c -> hand.get(CombinationType.CHOW).containsKey(c.get(0)) &&
                                    !c.contains(tile) && c.contains(secondTile)
                    ).collect(Collectors.toList());

                    this.updateHandsWithNewChow(hand, newChow, remainingTiles, new ArrayList<>(), commonChows, res);
                });
            }
        }

        this.allPossibleHands = res.stream().distinct().collect(Collectors.toList());
    }

    private void replaceChowsWithOneCommonTile(
            Map<CombinationType, Map<Tile, Integer>> hand,
            Tile mainTile, Tile commonTile, Tile remainingTile,
            List<Tile> newChow,
            List<Map<CombinationType, Map<Tile, Integer>>> res) {

        // Only replace the chow if we have the remaining tile free
        if (hand.get(CombinationType.NONE).containsKey(remainingTile)) {
            // Get the chows that only contain the common tile
            List<List<Tile>> oldChows = commonTile.getPossibleChowCombinations().stream().filter(
                    c -> hand.get(CombinationType.CHOW).containsKey(c.get(0)) &&
                            c.stream().noneMatch(t -> t.equals(mainTile) || t.equals(remainingTile))
            ).collect(Collectors.toList());

            this.updateHandsWithNewChow(
                    hand,
                    newChow, Collections.singletonList(commonTile), Collections.singletonList(remainingTile),
                    oldChows,
                    res);
        }
    }

    private void updateHandsWithNewChow(
            Map<CombinationType, Map<Tile, Integer>> hand,
            List<Tile> newChow, List<Tile> commonTiles, List<Tile> remainingTiles,
            List<List<Tile>> oldChows,
            List<Map<CombinationType, Map<Tile, Integer>>> res) {

        oldChows.stream()
            .filter(oldChow -> {
                var ignoredTiles = oldChow.stream()
                        .filter(t -> !commonTiles.contains(t))
                        .collect(Collectors.toList());

                return ignoredTiles.stream()
                        .flatMap(t -> t.getPossibleChowCombinations().stream())
                        .distinct()
                        .peek(c -> c.removeAll(ignoredTiles))
                        .noneMatch(c -> c.stream().allMatch(hand.get(CombinationType.NONE)::containsKey));
            })
            .forEach(oldChow -> {
                var handWithNewChow = this.copyHand(hand);

                // Add the new chow
                handWithNewChow.get(CombinationType.CHOW).merge(newChow.get(0), 1, Integer::sum);
                remainingTiles.forEach(t -> handWithNewChow.get(CombinationType.NONE).remove(t));

                // Remove or decrease the count of the old chow
                Tile oldChowTile = oldChow.get(0);
                Integer oldChowCount = handWithNewChow.get(CombinationType.CHOW).get(oldChowTile);
                if (oldChowCount == 1)
                    handWithNewChow.get(CombinationType.CHOW).remove(oldChow.get(0));
                else
                    handWithNewChow.get(CombinationType.CHOW).put(oldChowTile, oldChowCount - 1);

                // Put all tiles of the old chow in NONE, except the tile that is used for the new chow
                oldChow.removeAll(commonTiles);
                oldChow.forEach(t -> handWithNewChow.get(CombinationType.NONE).put(t, 1));

                res.add(handWithNewChow);
            });
    }

    private Map<CombinationType, Map<Tile, Integer>> copyHand(Map<CombinationType, Map<Tile, Integer>> hand) {
        return new HashMap<>(Map.ofEntries(
                Map.entry(CombinationType.NONE, new HashMap<>(hand.get(CombinationType.NONE))),
                Map.entry(CombinationType.PAIR, new HashMap<>(hand.get(CombinationType.PAIR))),
                Map.entry(CombinationType.PUNG, new HashMap<>(hand.get(CombinationType.PUNG))),
                Map.entry(CombinationType.KONG, new HashMap<>(hand.get(CombinationType.KONG))),
                Map.entry(CombinationType.CHOW, new HashMap<>(hand.get(CombinationType.CHOW)))
        ));
    }

    public List<Map<CombinationType, Map<Tile, Integer>>> getAllPossibleHands() {
        return this.allPossibleHands;
    }
}
