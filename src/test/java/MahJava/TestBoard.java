package MahJava;

import MahJava.exceptions.WallIsEmptyException;
import MahJava.game.Board;
import MahJava.tile.Tile;
import MahJava.tile.TileContent;
import MahJava.tile.TileType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;

import static org.testng.Assert.assertEquals;

public class TestBoard {

    private Board board = null;
    private ArrayList<Tile> validTiles = new ArrayList<>(Arrays.asList(
            new Tile(TileType.CHARACTERS, TileContent.ONE),
            new Tile(TileType.CHARACTERS, TileContent.ONE),
            new Tile(TileType.CHARACTERS, TileContent.TWO),
            new Tile(TileType.CHARACTERS, TileContent.THREE),
            new Tile(TileType.CHARACTERS, TileContent.FOUR),
            new Tile(TileType.CHARACTERS, TileContent.FIVE),
            new Tile(TileType.CHARACTERS, TileContent.SIX),
            new Tile(TileType.CHARACTERS, TileContent.SEVEN),
            new Tile(TileType.CHARACTERS, TileContent.EIGHT),
            new Tile(TileType.CHARACTERS, TileContent.NINE),

            new Tile(TileType.DOTS, TileContent.ONE),
            new Tile(TileType.DOTS, TileContent.TWO),
            new Tile(TileType.DOTS, TileContent.THREE),
            new Tile(TileType.DOTS, TileContent.FOUR),
            new Tile(TileType.DOTS, TileContent.FIVE),
            new Tile(TileType.DOTS, TileContent.SIX),
            new Tile(TileType.DOTS, TileContent.SEVEN),
            new Tile(TileType.DOTS, TileContent.EIGHT),
            new Tile(TileType.DOTS, TileContent.NINE),

            new Tile(TileType.BAMBOO, TileContent.ONE),
            new Tile(TileType.BAMBOO, TileContent.TWO),
            new Tile(TileType.BAMBOO, TileContent.THREE),
            new Tile(TileType.BAMBOO, TileContent.FOUR),
            new Tile(TileType.BAMBOO, TileContent.FIVE),
            new Tile(TileType.BAMBOO, TileContent.SIX),
            new Tile(TileType.BAMBOO, TileContent.SEVEN),
            new Tile(TileType.BAMBOO, TileContent.EIGHT),
            new Tile(TileType.BAMBOO, TileContent.NINE),

            new Tile(TileType.DRAGON, TileContent.RED),
            new Tile(TileType.DRAGON, TileContent.GREEN),
            new Tile(TileType.DRAGON, TileContent.WHITE),

            new Tile(TileType.WIND, TileContent.EAST),
            new Tile(TileType.WIND, TileContent.NORTH),
            new Tile(TileType.WIND, TileContent.WEST),
            new Tile(TileType.WIND, TileContent.SOUTH)
    ));

    @BeforeMethod
    public void setUp() {
    }

    @AfterMethod
    public void tearDown() {
        this.board = null;
    }

    @Test
    public void testConstructor() {
        //Arrange

        //Act
        this.board = new Board();
        Deque<Tile> wallCopy = this.board.getWall();

        //Assert
        // Confirm it contains every single possible tile
        assert (wallCopy.containsAll(this.validTiles));
        // Confirm that there are 4 copies of 9*3 numbered pieces + 3 dragons + 4 winds = 136
        assertEquals(this.board.getWall().size(), 136);
    }

    @Test
    public void testRemoveFirstTileFromWall() throws WallIsEmptyException {
        // Arrange
        this.board = new Board();
        Deque<Tile> wall = this.board.getWall();

        // Act
        int originalSize = wall.size();
        Tile tilePeeked = wall.getFirst();
        Tile tileRemoved = this.board.removeTileFromLiveWall();

        // Assert
        assertEquals(tileRemoved, tilePeeked);
        assertEquals(this.board.getWall().size(), originalSize - 1);
    }

    @Test(expectedExceptions = WallIsEmptyException.class)
    public void testRemoveFirstTileFromWallFailure() throws WallIsEmptyException {
        // Arrange
        this.board = new Board();
        int maxSize = this.board.getWall().size();

        // Act
        // Exception at maxSize
        for (int i = 0; i <= maxSize; i++) {
            this.board.removeTileFromLiveWall();
        }

        // Assert
    }

    @Test(expectedExceptions = WallIsEmptyException.class)
    public void testRemoveLastTileFromWallFailure() throws WallIsEmptyException {
        // Arrange
        this.board = new Board();
        int maxSize = this.board.getWall().size();

        // Act
        // Exception at maxSize
        for (int i = 0; i <= maxSize; i++) {
            this.board.removeTileFromLiveWall();
        }

        // Assert
    }
}