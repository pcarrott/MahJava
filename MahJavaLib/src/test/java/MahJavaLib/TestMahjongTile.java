package MahJavaLib;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static org.testng.Assert.*;

public class TestMahjongTile {

    private MahjongTile _tile = null;

    @BeforeMethod
    public void setUp() {
        // Goes Unused
    }

    @AfterMethod
    public void tearDown() {
        this._tile = null;
    }

    @DataProvider
    private Object[][] allValidTiles() {
        return new Object[][]{
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.ONE},
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.TWO},
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.THREE},
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.FOUR},
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.FIVE},
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.SIX},
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.SEVEN},
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.EIGHT},
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.NINE},

                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.ONE},
                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.TWO},
                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.THREE},
                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.FOUR},
                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.FIVE},
                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.SIX},
                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.SEVEN},
                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.EIGHT},
                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.NINE},

                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.ONE},
                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.TWO},
                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.THREE},
                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.FOUR},
                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.FIVE},
                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.SIX},
                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.SEVEN},
                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.EIGHT},
                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.NINE},

                {MahjongTile.TileType.DRAGON, MahjongTile.TileContent.RED},
                {MahjongTile.TileType.DRAGON, MahjongTile.TileContent.GREEN},
                {MahjongTile.TileType.DRAGON, MahjongTile.TileContent.WHITE},

                {MahjongTile.TileType.WIND, MahjongTile.TileContent.EAST},
                {MahjongTile.TileType.WIND, MahjongTile.TileContent.NORTH},
                {MahjongTile.TileType.WIND, MahjongTile.TileContent.WEST},
                {MahjongTile.TileType.WIND, MahjongTile.TileContent.SOUTH}};
    }

    @Test(dataProvider = "allValidTiles")
    public void testConstructorWithValidTiles(MahjongTile.TileType tt, MahjongTile.TileContent tc) {
        // Arrange

        // Act
        this._tile = new MahjongTile(tt, tc);

        // Asserts
        assertEquals(this._tile.getType(), tt);
        assertEquals(this._tile.getContent(), tc);
    }

    @DataProvider
    private Object[][] allInvalidTiles() {
        return new Object[][]{
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.RED},
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.GREEN},
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.WHITE},
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.EAST},
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.NORTH},
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.WEST},
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.SOUTH},

                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.RED},
                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.GREEN},
                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.WHITE},
                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.EAST},
                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.NORTH},
                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.WEST},
                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.SOUTH},

                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.RED},
                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.GREEN},
                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.WHITE},
                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.EAST},
                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.NORTH},
                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.WEST},
                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.SOUTH},

                {MahjongTile.TileType.DRAGON, MahjongTile.TileContent.ONE},
                {MahjongTile.TileType.DRAGON, MahjongTile.TileContent.TWO},
                {MahjongTile.TileType.DRAGON, MahjongTile.TileContent.THREE},
                {MahjongTile.TileType.DRAGON, MahjongTile.TileContent.FOUR},
                {MahjongTile.TileType.DRAGON, MahjongTile.TileContent.FIVE},
                {MahjongTile.TileType.DRAGON, MahjongTile.TileContent.SIX},
                {MahjongTile.TileType.DRAGON, MahjongTile.TileContent.SEVEN},
                {MahjongTile.TileType.DRAGON, MahjongTile.TileContent.EIGHT},
                {MahjongTile.TileType.DRAGON, MahjongTile.TileContent.NINE},

                {MahjongTile.TileType.WIND, MahjongTile.TileContent.ONE},
                {MahjongTile.TileType.WIND, MahjongTile.TileContent.TWO},
                {MahjongTile.TileType.WIND, MahjongTile.TileContent.THREE},
                {MahjongTile.TileType.WIND, MahjongTile.TileContent.FOUR},
                {MahjongTile.TileType.WIND, MahjongTile.TileContent.FIVE},
                {MahjongTile.TileType.WIND, MahjongTile.TileContent.SIX},
                {MahjongTile.TileType.WIND, MahjongTile.TileContent.SEVEN},
                {MahjongTile.TileType.WIND, MahjongTile.TileContent.EIGHT},
                {MahjongTile.TileType.WIND, MahjongTile.TileContent.NINE}};
    }

    @Test(dataProvider = "allInvalidTiles", expectedExceptions = IllegalArgumentException.class)
    public void testConstructorWithInvalidTiles(MahjongTile.TileType tt, MahjongTile.TileContent tc) {
        // Arrange

        // Act
        this._tile = new MahjongTile(tt, tc);

        // Asserts
    }

    @DataProvider
    private Object[][] allValidTilesForChows() {
        return new Object[][]{
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.ONE},
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.TWO},
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.THREE},
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.FOUR},
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.FIVE},
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.SIX},
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.SEVEN},
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.EIGHT},
                {MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.NINE},

                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.ONE},
                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.TWO},
                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.THREE},
                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.FOUR},
                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.FIVE},
                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.SIX},
                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.SEVEN},
                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.EIGHT},
                {MahjongTile.TileType.DOTS, MahjongTile.TileContent.NINE},

                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.ONE},
                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.TWO},
                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.THREE},
                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.FOUR},
                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.FIVE},
                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.SIX},
                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.SEVEN},
                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.EIGHT},
                {MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.NINE}};
    }

    @Test(dataProvider = "allValidTilesForChows")
    public void testGetPossibleChowCombinationsWithValidInput(MahjongTile.TileType tt, MahjongTile.TileContent tc) {
        // Arrange
        this._tile = new MahjongTile(tt, tc);

        // Act
        ArrayList<ArrayList<MahjongTile>> chows = this._tile.getPossibleChowCombinations();

        // Asserts
        switch (tc) {
            case ONE:
            case NINE:
                assertEquals(chows.size(), 1);
                break;

            case TWO:
            case EIGHT:
                assertEquals(chows.size(), 2);
                break;

            default:
                assertEquals(chows.size(), 3);
                break;
        }
    }

    @DataProvider
    private Object[][] allInvalidTilesForChows() {
        return new Object[][]{
                {MahjongTile.TileType.DRAGON, MahjongTile.TileContent.RED},
                {MahjongTile.TileType.DRAGON, MahjongTile.TileContent.GREEN},
                {MahjongTile.TileType.DRAGON, MahjongTile.TileContent.WHITE},

                {MahjongTile.TileType.WIND, MahjongTile.TileContent.EAST},
                {MahjongTile.TileType.WIND, MahjongTile.TileContent.NORTH},
                {MahjongTile.TileType.WIND, MahjongTile.TileContent.WEST},
                {MahjongTile.TileType.WIND, MahjongTile.TileContent.SOUTH}};
    }

    @Test(dataProvider = "allInvalidTilesForChows")
    public void testGetPossibleChowCombinationsWithInvalidInput(MahjongTile.TileType tt, MahjongTile.TileContent tc) {
        // Arrange
        this._tile = new MahjongTile(tt, tc);

        // Act
        ArrayList<ArrayList<MahjongTile>> chows = this._tile.getPossibleChowCombinations();

        // Asserts
        assertTrue(chows.isEmpty());
    }

    @Test
    public void testTileContentGetters() {
        ArrayList<MahjongTile.TileContent> numbers = MahjongTile.TileContent.getNumbers();
        ArrayList<MahjongTile.TileContent> colors = MahjongTile.TileContent.getColors();
        ArrayList<MahjongTile.TileContent> directions = MahjongTile.TileContent.getDirections();

        ArrayList<MahjongTile.TileContent> tmp = new ArrayList<>();
        for (int i = 1; i <= 9; ++i) {
            tmp.add(MahjongTile.TileContent.fromValue(i));
        }
        assertTrue(numbers.size() == 9 && numbers.containsAll(tmp));

        assertTrue(colors.size() == 3 && colors.containsAll(
                Arrays.asList(MahjongTile.TileContent.WHITE,
                        MahjongTile.TileContent.GREEN,
                        MahjongTile.TileContent.RED)));

        assertTrue(directions.size() == 4 && directions.containsAll(
                Arrays.asList(MahjongTile.TileContent.EAST,
                        MahjongTile.TileContent.WEST,
                        MahjongTile.TileContent.NORTH,
                        MahjongTile.TileContent.SOUTH)));
    }
}