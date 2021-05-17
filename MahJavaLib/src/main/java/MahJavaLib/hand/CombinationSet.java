package MahJavaLib.hand;

import MahJavaLib.tile.Combination;
import MahJavaLib.tile.CombinationType;
import MahJavaLib.tile.Tile;
import MahJavaLib.tile.TileType;

import java.util.*;
import java.util.stream.Collectors;

public class CombinationSet {
    private final Map<CombinationType, List<Map<Tile, Integer>>> combinations = new HashMap<>();
    private final List<Tile> pairs = new ArrayList<>();
    private final List<Tile> ignored = new ArrayList<>();

    public CombinationSet() {
        this.combinations.put(CombinationType.PUNG, new ArrayList<>());
        this.combinations.put(CombinationType.KONG, new ArrayList<>());
        this.combinations.put(CombinationType.CHOW, new ArrayList<>());
    }

    public void addCombination(CombinationType type, Map<Tile, Integer> tiles) {
        this.combinations.get(type).add(tiles);
    }

    public void addCombination(CombinationType type, Map<Tile, Integer> tiles, Integer n) {
        for (int i = 0; i < n; i++)
            this.combinations.get(type).add(tiles);
    }

    public void addCombination(Combination combination) {
        this.combinations.get(combination.getCombinationType()).add(combination.getTiles());
    }

    public void addPair(Tile tile) {
        this.pairs.add(tile);
    }

    public void addPair(Tile tile, Integer n) {
        for (int i = 0; i < n; i++)
            this.pairs.add(tile);
    }

    public void addIgnored(Tile tile) {
        this.ignored.add(tile);
    }

    public List<Map<Tile, Integer>> getCombinations(CombinationType type) {
        return new ArrayList<>(this.combinations.get(type));
    }

    public List<Tile> getCombinationTiles(CombinationType type) {
        return this.combinations.get(type).stream().flatMap(m -> m.keySet().stream()).collect(Collectors.toList());
    }

    public List<Tile> getPairs() {
        return this.pairs;
    }

    public List<Tile> getIgnored() {
        return this.ignored;
    }

    public List<TileType> getSuites() {
        List<TileType> suites = new ArrayList<>();
        this.pairs.stream().map(Tile::getType).distinct().forEach(suites::add);
        this.combinations.values().forEach(
                list -> list.forEach(
                        map -> map.keySet().forEach(
                                tile -> suites.add(tile.getType())
        )));
        return suites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CombinationSet that = (CombinationSet) o;

        Map<CombinationType, Set<Map<Tile, Integer>>> combinations = this.combinations.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new HashSet<>(e.getValue())));
        Set<Tile> pairs = new HashSet<>(this.pairs);
        Set<Tile> ignored = new HashSet<>(this.ignored);

        Map<CombinationType, Set<Map<Tile, Integer>>> thatCombinations = that.combinations.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new HashSet<>(e.getValue())));
        Set<Tile> thatPairs = new HashSet<>(that.pairs);
        Set<Tile> thatIgnored = new HashSet<>(that.ignored);

        return Objects.equals(combinations, thatCombinations) && Objects.equals(pairs, thatPairs) && Objects.equals(ignored, thatIgnored);
    }

    @Override
    public int hashCode() {
        Map<CombinationType, Set<Map<Tile, Integer>>> combinations = this.combinations.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new HashSet<>(e.getValue())));
        Set<Tile> pairs = new HashSet<>(this.pairs);
        Set<Tile> ignored = new HashSet<>(this.ignored);
        return Objects.hash(combinations, pairs, ignored);
    }

    @Override
    public String toString() {
        return "Pungs: " + this.combinations.get(CombinationType.PUNG) + "\n" +
               "Kongs: " + this.combinations.get(CombinationType.KONG) + "\n" +
               "Chows: " + this.combinations.get(CombinationType.CHOW) + "\n" +
               "Pairs: " + this.pairs;
    }
}
