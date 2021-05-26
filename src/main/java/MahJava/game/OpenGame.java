package MahJava.game;

import MahJava.player.Player;
import MahJava.tile.Combination;
import MahJava.tile.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenGame {
    private final Map<Player, Map<Tile, Integer>> discardedTiles = new HashMap<>();
    private final Map<Player, List<Combination>> openCombinations = new HashMap<>();
    private final Map<Player, List<Tile>> concealedKongs = new HashMap<>();

    public OpenGame(List<Player> players) {
        for (Player player : players) {
            this.discardedTiles.put(player, new HashMap<>());
            this.openCombinations.put(player, new ArrayList<>());
            this.concealedKongs.put(player, new ArrayList<>());
        }
    }

    public void addDiscardedTile(Player player, Tile discardedTile) {
        this.discardedTiles.get(player).merge(discardedTile, 1, Integer::sum);
    }

    public Map<Tile, Integer> getExposedTiles() {
        Map<Tile, Integer> res = new HashMap<>();

        for (Map<Tile, Integer> playerTiles : this.discardedTiles.values())
            for (Map.Entry<Tile, Integer> e : playerTiles.entrySet())
                res.merge(e.getKey(), e.getValue(), Integer::sum);

        for (List<Combination> combinations : this.openCombinations.values())
            for (Combination combination : combinations)
                for (Map.Entry<Tile, Integer> e : combination.getTiles().entrySet())
                    res.merge(e.getKey(), e.getValue(), Integer::sum);

        for (List<Tile> kongs : this.concealedKongs.values())
            for (Tile kongTile : kongs)
                res.put(kongTile, 4);

        return res;
    }

    public List<Combination> getOpenCombinations(Player player) {
        return this.openCombinations.get(player);
    }

    public List<Tile> getConcealedKongs(Player player) {
        return this.concealedKongs.get(player);
    }

    public void reset() {
        this.discardedTiles.values().forEach(Map::clear);
        this.openCombinations.values().forEach(List::clear);
        this.concealedKongs.values().forEach(List::clear);
    }
}
