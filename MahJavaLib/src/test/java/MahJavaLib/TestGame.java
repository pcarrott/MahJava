package MahJavaLib;

import MahJavaLib.game.Game;
import MahJavaLib.player.Player;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
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
        String[] names = {"Leonardo", "Raphael", "Donatello", "Michelangelo"};
        List<Player> players = Arrays.asList(
                Player.EagerPlayer(names[0]),
                Player.ComposedPlayer(names[1]),
                Player.ConcealedPlayer(names[2]),
                Player.MixedPlayer(names[3])
        );

        Game game = new Game(players);
        game.gameLoop();
    }
}