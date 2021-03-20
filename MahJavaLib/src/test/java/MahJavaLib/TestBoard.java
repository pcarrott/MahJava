package MahJavaLib;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TestBoard {

    private Board _board = null;

    @BeforeMethod
    public void setUp() {
    }

    @AfterMethod
    public void tearDown() {
        this._board = null;
    }

    @Test
    public void testConstructor() {
        this._board = new Board();

        // There should be 9*3 normal pieces + 3 dragons + 4 winds = 34
        assertEquals(this._board.getWall().size(), 34);
    }

    @Test
    public void testRemoveOneTileFromWall() {
        // Arrange

        // Act

        // Assert
    }
}