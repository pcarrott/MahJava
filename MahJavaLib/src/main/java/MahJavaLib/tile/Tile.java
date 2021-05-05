package MahJavaLib.tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Tile {

    private TileType type;
    private TileContent content;

    public Tile(TileType tt, TileContent tc) throws IllegalArgumentException {
        // All non-special types (Characters, Dots and Bamboos) must be Numbers.
        if (!tt.isSpecialType() && !TileContent.isNumber(tc)) {
            throw new IllegalArgumentException("Illegal Content " + tc + " given Type " + tt);
        }

        // If it is of a special type, then it must be either a Dragon (which means the content must be a color)
        // or a Wind (which means the content must be a Direction).
        if (tt.isSpecialType() && (tt == TileType.DRAGON && !TileContent.isColor(tc)) || (tt == TileType.WIND && !TileContent.isDirection(tc))) {
            throw new IllegalArgumentException("Illegal Content " + tc + " given Type " + tt);
        }
        this.type = tt;
        this.content = tc;
    }

    public TileType getType() {
        return this.type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public TileContent getContent() {
        return this.content;
    }

    public void setContent(TileContent content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return type + " - " + content;
    }

    // These two Overrides (`equals` and `hashCode`) are needed so two different MahjongTile objects
    // that have the same Type and Content, are recognized as the same on a HashMap
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Tile)) {
            return false;
        }
        Tile other = (Tile) o;
        return this.type.equals(other.type) && this.content.equals(other.content);
    }

    @Override
    public int hashCode() {
        return this.type.hashCode() ^ this.content.hashCode();
    }

    static class MahjongTileComparator implements Comparator<Tile> {
        @Override
        public int compare(Tile a, Tile b) {
            if (a.type.equals(b.type)) {
                return a.content.compareTo(b.content);
            }
            return a.type.compareTo(b.type);
        }
    }

    public List<ArrayList<Tile>> getPossibleChowCombinations() {
        // This function returns all chows that include the supplied tile
        ArrayList<ArrayList<Tile>> possibleChows = new ArrayList<>();

        // It is impossible to make a Chow with a Special tile
        if (this.type.isSpecialType() || !TileContent.isNumber(this.content)) {
            return possibleChows;
        }

        // Each tile with content N (where 1 <= N <= 9) can have AT MOST 3 possible Chow combinations:
        // - N - 2, N - 1, N
        // - N - 1, N, N + 1
        // - N, N + 1, N + 2
        // We go through all the possible Chow combinations in a sliding-window like fashion, where the requested tile
        // starts as the bigger value tile, and then we shift until it is the smallest value tile
        int smallerValue = this.content.value - 2;
        int middleValue = this.content.value - 1;
        int biggerValue = this.content.value;

        for (int i = 0; i < 3; ++i) {
            // If the value is still a valid number, then that must mean it is a valid Chow combination
            // Since smallerValue is always smaller than both middleValue and biggerValue, and biggerValue is always
            // bigger than both middleValue and smallerValue, we only need to check both extremes.
            if (1 <= smallerValue && biggerValue <= 9) {
                Tile smallTile = new Tile(this.getType(), TileContent.fromValue(smallerValue));
                Tile middleTile = new Tile(this.getType(), TileContent.fromValue(middleValue));
                Tile bigTile = new Tile(this.getType(), TileContent.fromValue(biggerValue));
                possibleChows.add(new ArrayList<>(Arrays.asList(smallTile, middleTile, bigTile)));
            }

            smallerValue += 1;
            middleValue += 1;
            biggerValue += 1;
        }

        return possibleChows;
    }
}
