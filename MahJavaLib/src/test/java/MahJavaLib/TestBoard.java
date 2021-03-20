package MahJavaLib;

import MahJavaLib.exceptions.WallIsEmptyException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;

import static org.testng.Assert.assertEquals;

public class TestBoard {

    private Board _board = null;
    private ArrayList<Tile> validTiles = new ArrayList<>(Arrays.asList(
            new Tile(Tile.TileType.CHARACTERS, Tile.TileContent.ONE),
            new Tile(Tile.TileType.CHARACTERS, Tile.TileContent.ONE),
            new Tile(Tile.TileType.CHARACTERS, Tile.TileContent.TWO),
            new Tile(Tile.TileType.CHARACTERS, Tile.TileContent.THREE),
            new Tile(Tile.TileType.CHARACTERS, Tile.TileContent.FOUR),
            new Tile(Tile.TileType.CHARACTERS, Tile.TileContent.FIVE),
            new Tile(Tile.TileType.CHARACTERS, Tile.TileContent.SIX),
            new Tile(Tile.TileType.CHARACTERS, Tile.TileContent.SEVEN),
            new Tile(Tile.TileType.CHARACTERS, Tile.TileContent.EIGHT),
            new Tile(Tile.TileType.CHARACTERS, Tile.TileContent.NINE),

            new Tile(Tile.TileType.DOTS, Tile.TileContent.ONE),
            new Tile(Tile.TileType.DOTS, Tile.TileContent.TWO),
            new Tile(Tile.TileType.DOTS, Tile.TileContent.THREE),
            new Tile(Tile.TileType.DOTS, Tile.TileContent.FOUR),
            new Tile(Tile.TileType.DOTS, Tile.TileContent.FIVE),
            new Tile(Tile.TileType.DOTS, Tile.TileContent.SIX),
            new Tile(Tile.TileType.DOTS, Tile.TileContent.SEVEN),
            new Tile(Tile.TileType.DOTS, Tile.TileContent.EIGHT),
            new Tile(Tile.TileType.DOTS, Tile.TileContent.NINE),

            new Tile(Tile.TileType.BAMBOO, Tile.TileContent.ONE),
            new Tile(Tile.TileType.BAMBOO, Tile.TileContent.TWO),
            new Tile(Tile.TileType.BAMBOO, Tile.TileContent.THREE),
            new Tile(Tile.TileType.BAMBOO, Tile.TileContent.FOUR),
            new Tile(Tile.TileType.BAMBOO, Tile.TileContent.FIVE),
            new Tile(Tile.TileType.BAMBOO, Tile.TileContent.SIX),
            new Tile(Tile.TileType.BAMBOO, Tile.TileContent.SEVEN),
            new Tile(Tile.TileType.BAMBOO, Tile.TileContent.EIGHT),
            new Tile(Tile.TileType.BAMBOO, Tile.TileContent.NINE),

            new Tile(Tile.TileType.DRAGON, Tile.TileContent.RED),
            new Tile(Tile.TileType.DRAGON, Tile.TileContent.GREEN),
            new Tile(Tile.TileType.DRAGON, Tile.TileContent.WHITE),

            new Tile(Tile.TileType.WIND, Tile.TileContent.EAST),
            new Tile(Tile.TileType.WIND, Tile.TileContent.NORTH),
            new Tile(Tile.TileType.WIND, Tile.TileContent.WEST),
            new Tile(Tile.TileType.WIND, Tile.TileContent.SOUTH)
    ));

    @BeforeMethod
    public void setUp() {
    }

    @AfterMethod
    public void tearDown() {
        this._board = null;
    }

    @Test
    public void testConstructor() {
        //Arrange

        //Act
        this._board = new Board();
        Deque<Tile> wallCopy = this._board.getWall();

        //Assert
        // Confirm it contains every single possible tile
        assert (wallCopy.containsAll(this.validTiles));
        // Confirm that there are 4 copies of 9*3 numbered pieces + 3 dragons + 4 winds = 136
        assertEquals(this._board.getWall().size(), 136);
    }

    @Test
    public void testRemoveFirstTileFromWall() throws WallIsEmptyException {
        // Arrange
        this._board = new Board();
        Deque<Tile> wall = this._board.getWall();

        // Act
        int originalSize = wall.size();
        Tile tilePeeked = wall.getFirst();
        Tile tileRemoved = this._board.removeFirstTileFromWall();

        // Assert
        assertEquals(tileRemoved, tilePeeked);
        assertEquals(this._board.getWall().size(), originalSize - 1);
    }

    @Test
    public void testRemoveLastTileFromWall() throws WallIsEmptyException {
        // Arrange
        this._board = new Board();
        Deque<Tile> wall = this._board.getWall();

        // Act
        int originalSize = wall.size();
        Tile tilePeeked = wall.getLast();
        Tile tileRemoved = this._board.removeLastTileFromWall();

        // Assert
        assertEquals(tileRemoved, tilePeeked);
        assertEquals(this._board.getWall().size(), originalSize - 1);
    }

    @Test(expectedExceptions = WallIsEmptyException.class)
    public void testRemoveFirstTileFromWallFailure() throws WallIsEmptyException {
        // Arrange
        this._board = new Board();
        int maxSize = this._board.getWall().size();

        // Act
        // Exception at maxSize
        for (int i = 0; i <= maxSize; i++) {
            this._board.removeFirstTileFromWall();
        }

        // Assert
    }

    @Test(expectedExceptions = WallIsEmptyException.class)
    public void testRemoveLastTileFromWallFailure() throws WallIsEmptyException {
        // Arrange
        this._board = new Board();
        int maxSize = this._board.getWall().size();

        // Act
        // Exception at maxSize
        for (int i = 0; i <= maxSize; i++) {
            this._board.removeLastTileFromWall();
        }

        // Assert
    }
}