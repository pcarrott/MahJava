package MahJavaLib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class MahjongTile {

    public enum TileType {
        CHARACTERS,
        DOTS,
        BAMBOO,
        DRAGON,
        WIND;

        public boolean isSpecialType() {
            return (this == DRAGON || this == WIND);
        }
    }

    public enum TileContent {
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        RED(10),
        GREEN(11),
        WHITE(12),
        EAST(13),
        NORTH(14),
        WEST(15),
        SOUTH(16);

        Integer _value;

        public static boolean isNumber(TileContent content) {
            return content._value >= 1 && content._value <= 9;
        }

        public static boolean isColor(TileContent content) {
            return content._value >= 10 && content._value <= 12;
        }

        public static boolean isDirection(TileContent content) {
            return content._value >= 13 && content._value <= 16;
        }


        // Helper functions that get all values of each main type of content
        public static ArrayList<TileContent> getNumbers() {
            return Arrays.stream(TileContent.values()).filter(TileContent::isNumber).collect(Collectors.toCollection(ArrayList::new));
        }

        public static ArrayList<TileContent> getColors() {
            return Arrays.stream(TileContent.values()).filter(TileContent::isColor).collect(Collectors.toCollection(ArrayList::new));
        }

        public static ArrayList<TileContent> getDirections() {
            return Arrays.stream(TileContent.values()).filter(TileContent::isDirection).collect(Collectors.toCollection(ArrayList::new));
        }

        TileContent(Integer val) {
            this._value = val;
        }

        public static TileContent fromValue(Integer val) {
            // This is a simple helper function to go from an Integer value to the corresponding TileContent (helps
            // for the Chow logic). It will blow up if the Integer value supplied doesn't exist.
            for (TileContent content : TileContent.values()) {
                if (content._value.equals(val)) {
                    return content;
                }
            }

            throw new RuntimeException("TileContent: Invalid value: " + val);
        }
    }

    private TileType _type;
    private TileContent _content;

    public MahjongTile(TileType tt, TileContent tc) throws IllegalArgumentException {
        // All non-special types (Characters, Dots and Bamboos) must be Numbers.
        if (!tt.isSpecialType() && !TileContent.isNumber(tc)) {
            throw new IllegalArgumentException("Illegal Content " + tc + " given Type " + tt);
        }

        if (tt.isSpecialType()) {
            // If it is of a special type, then it must be either a Dragon (which means the content must be a color)
            // or a Wind (which means the content must be a Direction).
            if ((tt == TileType.DRAGON && !TileContent.isColor(tc)) || (tt == TileType.WIND && !TileContent.isDirection(tc))) {
                throw new IllegalArgumentException("Illegal Content " + tc + " given Type " + tt);
            }
        }
        this._type = tt;
        this._content = tc;
    }


    public TileType getType() {
        return _type;
    }

    public void setType(TileType _type) {
        this._type = _type;
    }

    public TileContent getContent() {
        return _content;
    }

    public void setContent(TileContent _content) {
        this._content = _content;
    }

    @Override
    public String toString() {
        return _type + " - " + _content;
    }

    static class MahjongTileComparator implements Comparator<MahjongTile> {
        @Override
        public int compare(MahjongTile a, MahjongTile b) {
            if (a._type.equals(b._type)) {
                return a._content.compareTo(b._content);
            }
            return a._type.compareTo(b._type);
        }
    }

    public ArrayList<ArrayList<MahjongTile>> getPossibleChowCombinations() {
        // This function returns all chows that include the supplied tile
        ArrayList<ArrayList<MahjongTile>> possibleChows = new ArrayList<>();

        // It is impossible to make a Chow with a Special tile
        if (this._type.isSpecialType() || !TileContent.isNumber(_content)) {
            return possibleChows;
        }

        // Each tile with content N (where 1 <= N <= 9) can have AT MOST 3 possible Chow combinations:
        // - N - 2, N - 1, N
        // - N - 1, N, N + 1
        // - N, N + 1, N + 2
        // We go through all the possible Chow combinations in a sliding-window like fashion, where the requested tile
        // starts as the bigger value tile, and then we shift until it is the smallest value tile
        int smallerValue = this._content._value - 2;
        int middleValue = this._content._value - 1;
        int biggerValue = this._content._value;

        for (int i = 0; i < 3; ++i) {
            // If the value is still a valid number, then that must mean it is a valid Chow combination
            // Since smallerValue is always smaller than both middleValue and biggerValue, and biggerValue is always
            // bigger than both middleValue and smallerValue, we only need to check both extremes.
            if (1 <= smallerValue && biggerValue <= 9) {
                MahjongTile smallTile = new MahjongTile(this.getType(), TileContent.fromValue(smallerValue));
                MahjongTile middleTile = new MahjongTile(this.getType(), TileContent.fromValue(middleValue));
                MahjongTile bigTile = new MahjongTile(this.getType(), TileContent.fromValue(biggerValue));
                possibleChows.add(new ArrayList<>(Arrays.asList(smallTile, middleTile, bigTile)));
            }

            smallerValue += 1;
            middleValue += 1;
            biggerValue += 1;
        }

        return possibleChows;
    }
}
