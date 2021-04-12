package MahJavaLib;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class TestGame {

    @BeforeMethod
    public void setUp() {
    }

    @AfterMethod
    public void tearDown() {
    }

    @Test
    public void testNextPlayer() {
    }

    @Test
    public void testIsBoardWallEmpty() {
    }

    @Test
    public void testGetPlayerTurn() {
    }

    @Test
    public void testGetPlayers() {
    }

    @Test
    public void testGetPlayer() {
    }

    @Test
    public void testTestToString() {
    }

    @Test
    public void testMain() {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 4; ++i) {
            players.add(new Player());
        }
        Game game = new Game(players);
        game.gameLoop();
    }
}