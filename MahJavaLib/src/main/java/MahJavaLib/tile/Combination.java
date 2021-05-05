package MahJavaLib.tile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


// Represents a real combination, a Chow, Pung or Kong
public class Combination {
    private final CombinationType combinationType;
    private final Map<Tile, Integer> tiles;

    public Combination(CombinationType type, List<Tile> tiles) {
        assert !type.equals(CombinationType.PAIR);
        assert ((tiles.size() == 4) && type.equals(CombinationType.KONG)) ||
                (tiles.size() == 3 && (type.equals(CombinationType.CHOW) || type.equals(CombinationType.PUNG)));

        this.combinationType = type;
        this.tiles = new HashMap<>();
        for (Tile tile : tiles) {
            this.tiles.compute(tile, (key, value) -> {
                if (value == null) {
                    return 1;
                }
                return value + 1;
            });
        }
    }

    public CombinationType getCombinationType() {
        return combinationType;
    }

    public Map<Tile, Integer> getTiles() {
        return tiles;
    }

    @Override
    public String toString() {
        return "Combination{" +
                "combinationType=" + combinationType +
                ", tiles=" + tiles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Combination that = (Combination) o;
        return combinationType == that.combinationType && tiles.equals(that.tiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.combinationType, this.tiles);
    }
}
