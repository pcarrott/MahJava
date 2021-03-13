package test.mahjong.MahJavaLib;

import com.mahjong.MahJavaLib.MahjongBoard;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TestMahjongBoard {

    private MahjongBoard _board = null;

    @BeforeMethod
    public void setUp() {
    }

    @AfterMethod
    public void tearDown() {
        this._board = null;
    }

    @Test
    public void testConstructor() {
        // Arrange

        // Act
        this._board = new MahjongBoard();

        // Assert
        assertEquals(this._board.getWall().size(), 5);
    }

    @Test
    public void testRemoveOneTileFromWall() {
        // Arrange

        // Act

        // Assert
    }
}