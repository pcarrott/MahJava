package MahJavaLib.tile;

import MahJavaLib.exceptions.InvalidTileContentValueException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    Integer value;

    public static boolean isNumber(TileContent content) {
        return content.value >= 1 && content.value <= 9;
    }

    public static boolean isColor(TileContent content) {
        return content.value >= 10 && content.value <= 12;
    }

    public static boolean isDirection(TileContent content) {
        return content.value >= 13 && content.value <= 16;
    }


    // Helper functions that get all values of each main type of content
    public static List<TileContent> getNumbers() {
        return Arrays.stream(TileContent.values()).filter(TileContent::isNumber).collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<TileContent> getColors() {
        return Arrays.stream(TileContent.values()).filter(TileContent::isColor).collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<TileContent> getDirections() {
        return Arrays.stream(TileContent.values()).filter(TileContent::isDirection).collect(Collectors.toCollection(ArrayList::new));
    }

    TileContent(Integer val) {
        this.value = val;
    }

    public static TileContent fromValue(Integer val) {
        // This is a simple helper function to go from an Integer value to the corresponding TileContent (helps
        // for the Chow logic). It will blow up if the Integer value supplied doesn't exist.
        for (TileContent content : TileContent.values()) {
            if (content.value.equals(val)) {
                return content;
            }
        }

        throw new InvalidTileContentValueException("TileContent: Invalid value: " + val);
    }
}
