package MahJavaLib;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.testng.Assert.*;

import MahJavaLib.Tile.*;

public class TestTile {

    private Tile _tile = null;

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
                {TileType.CHARACTERS, TileContent.ONE},
                {TileType.CHARACTERS, TileContent.TWO},
                {TileType.CHARACTERS, TileContent.THREE},
                {TileType.CHARACTERS, TileContent.FOUR},
                {TileType.CHARACTERS, TileContent.FIVE},
                {TileType.CHARACTERS, TileContent.SIX},
                {TileType.CHARACTERS, TileContent.SEVEN},
                {TileType.CHARACTERS, TileContent.EIGHT},
                {TileType.CHARACTERS, TileContent.NINE},

                {TileType.DOTS, TileContent.ONE},
                {TileType.DOTS, TileContent.TWO},
                {TileType.DOTS, TileContent.THREE},
                {TileType.DOTS, TileContent.FOUR},
                {TileType.DOTS, TileContent.FIVE},
                {TileType.DOTS, TileContent.SIX},
                {TileType.DOTS, TileContent.SEVEN},
                {TileType.DOTS, TileContent.EIGHT},
                {TileType.DOTS, TileContent.NINE},

                {TileType.BAMBOO, TileContent.ONE},
                {TileType.BAMBOO, TileContent.TWO},
                {TileType.BAMBOO, TileContent.THREE},
                {TileType.BAMBOO, TileContent.FOUR},
                {TileType.BAMBOO, TileContent.FIVE},
                {TileType.BAMBOO, TileContent.SIX},
                {TileType.BAMBOO, TileContent.SEVEN},
                {TileType.BAMBOO, TileContent.EIGHT},
                {TileType.BAMBOO, TileContent.NINE},

                {TileType.DRAGON, TileContent.RED},
                {TileType.DRAGON, TileContent.GREEN},
                {TileType.DRAGON, TileContent.WHITE},

                {TileType.WIND, TileContent.EAST},
                {TileType.WIND, TileContent.NORTH},
                {TileType.WIND, TileContent.WEST},
                {TileType.WIND, TileContent.SOUTH}};
    }

    @Test(dataProvider = "allValidTiles")
    public void testConstructorWithValidTiles(TileType tt, TileContent tc) {
        // Arrange

        // Act
        this._tile = new Tile(tt, tc);

        // Asserts
        assertEquals(this._tile.getType(), tt);
        assertEquals(this._tile.getContent(), tc);
    }

    @DataProvider
    private Object[][] allInvalidTiles() {
        return new Object[][]{
                {TileType.CHARACTERS, TileContent.RED},
                {TileType.CHARACTERS, TileContent.GREEN},
                {TileType.CHARACTERS, TileContent.WHITE},
                {TileType.CHARACTERS, TileContent.EAST},
                {TileType.CHARACTERS, TileContent.NORTH},
                {TileType.CHARACTERS, TileContent.WEST},
                {TileType.CHARACTERS, TileContent.SOUTH},

                {TileType.DOTS, TileContent.RED},
                {TileType.DOTS, TileContent.GREEN},
                {TileType.DOTS, TileContent.WHITE},
                {TileType.DOTS, TileContent.EAST},
                {TileType.DOTS, TileContent.NORTH},
                {TileType.DOTS, TileContent.WEST},
                {TileType.DOTS, TileContent.SOUTH},

                {TileType.BAMBOO, TileContent.RED},
                {TileType.BAMBOO, TileContent.GREEN},
                {TileType.BAMBOO, TileContent.WHITE},
                {TileType.BAMBOO, TileContent.EAST},
                {TileType.BAMBOO, TileContent.NORTH},
                {TileType.BAMBOO, TileContent.WEST},
                {TileType.BAMBOO, TileContent.SOUTH},

                {TileType.DRAGON, TileContent.ONE},
                {TileType.DRAGON, TileContent.TWO},
                {TileType.DRAGON, TileContent.THREE},
                {TileType.DRAGON, TileContent.FOUR},
                {TileType.DRAGON, TileContent.FIVE},
                {TileType.DRAGON, TileContent.SIX},
                {TileType.DRAGON, TileContent.SEVEN},
                {TileType.DRAGON, TileContent.EIGHT},
                {TileType.DRAGON, TileContent.NINE},

                {TileType.WIND, TileContent.ONE},
                {TileType.WIND, TileContent.TWO},
                {TileType.WIND, TileContent.THREE},
                {TileType.WIND, TileContent.FOUR},
                {TileType.WIND, TileContent.FIVE},
                {TileType.WIND, TileContent.SIX},
                {TileType.WIND, TileContent.SEVEN},
                {TileType.WIND, TileContent.EIGHT},
                {TileType.WIND, TileContent.NINE}};
    }

    @Test(dataProvider = "allInvalidTiles", expectedExceptions = IllegalArgumentException.class)
    public void testConstructorWithInvalidTiles(TileType tt, TileContent tc) {
        // Arrange

        // Act
        this._tile = new Tile(tt, tc);

        // Asserts
    }

    @DataProvider
    private Object[][] allValidTilesForChows() {
        return new Object[][]{
                {TileType.CHARACTERS, TileContent.ONE},
                {TileType.CHARACTERS, TileContent.TWO},
                {TileType.CHARACTERS, TileContent.THREE},
                {TileType.CHARACTERS, TileContent.FOUR},
                {TileType.CHARACTERS, TileContent.FIVE},
                {TileType.CHARACTERS, TileContent.SIX},
                {TileType.CHARACTERS, TileContent.SEVEN},
                {TileType.CHARACTERS, TileContent.EIGHT},
                {TileType.CHARACTERS, TileContent.NINE},

                {TileType.DOTS, TileContent.ONE},
                {TileType.DOTS, TileContent.TWO},
                {TileType.DOTS, TileContent.THREE},
                {TileType.DOTS, TileContent.FOUR},
                {TileType.DOTS, TileContent.FIVE},
                {TileType.DOTS, TileContent.SIX},
                {TileType.DOTS, TileContent.SEVEN},
                {TileType.DOTS, TileContent.EIGHT},
                {TileType.DOTS, TileContent.NINE},

                {TileType.BAMBOO, TileContent.ONE},
                {TileType.BAMBOO, TileContent.TWO},
                {TileType.BAMBOO, TileContent.THREE},
                {TileType.BAMBOO, TileContent.FOUR},
                {TileType.BAMBOO, TileContent.FIVE},
                {TileType.BAMBOO, TileContent.SIX},
                {TileType.BAMBOO, TileContent.SEVEN},
                {TileType.BAMBOO, TileContent.EIGHT},
                {TileType.BAMBOO, TileContent.NINE}};
    }

    @Test(dataProvider = "allValidTilesForChows")
    public void testGetPossibleChowCombinationsWithValidInput(TileType tt, TileContent tc) {
        // Arrange
        this._tile = new Tile(tt, tc);

        // Act
        ArrayList<ArrayList<Tile>> chows = (ArrayList<ArrayList<Tile>>) this._tile.getPossibleChowCombinations();

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
                {TileType.DRAGON, TileContent.RED},
                {TileType.DRAGON, TileContent.GREEN},
                {TileType.DRAGON, TileContent.WHITE},

                {TileType.WIND, TileContent.EAST},
                {TileType.WIND, TileContent.NORTH},
                {TileType.WIND, TileContent.WEST},
                {TileType.WIND, TileContent.SOUTH}};
    }

    @Test(dataProvider = "allInvalidTilesForChows")
    public void testGetPossibleChowCombinationsWithInvalidInput(TileType tt, TileContent tc) {
        // Arrange
        this._tile = new Tile(tt, tc);

        // Act
        ArrayList<ArrayList<Tile>> chows = (ArrayList<ArrayList<Tile>>) this._tile.getPossibleChowCombinations();

        // Asserts
        assertTrue(chows.isEmpty());
    }

    @Test
    public void testTileContentGetters() {
        ArrayList<TileContent> numbers = (ArrayList<TileContent>) TileContent.getNumbers();
        ArrayList<TileContent> colors = (ArrayList<TileContent>) TileContent.getColors();
        ArrayList<TileContent> directions = (ArrayList<TileContent>) TileContent.getDirections();

        ArrayList<TileContent> tmp = new ArrayList<>();
        for (int i = 1; i <= 9; ++i) {
            tmp.add(TileContent.fromValue(i));
        }
        assertTrue(numbers.size() == 9 && numbers.containsAll(tmp));

        assertTrue(colors.size() == 3 && colors.containsAll(
                Arrays.asList(TileContent.WHITE,
                        TileContent.GREEN,
                        TileContent.RED)));

        assertTrue(directions.size() == 4 && directions.containsAll(
                Arrays.asList(TileContent.EAST,
                        TileContent.WEST,
                        TileContent.NORTH,
                        TileContent.SOUTH)));
    }

    @Test
    public void testEquality() {
        Tile oneOfBamboo = new Tile(TileType.BAMBOO, TileContent.ONE);
        Tile anotherOneOfBamboo = new Tile(TileType.BAMBOO, TileContent.ONE);
        assertEquals(oneOfBamboo, anotherOneOfBamboo);
        assertEquals(oneOfBamboo.hashCode(), anotherOneOfBamboo.hashCode());
    }
}