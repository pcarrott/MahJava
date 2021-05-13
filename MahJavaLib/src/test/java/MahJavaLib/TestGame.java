package MahJavaLib;

import MahJavaLib.game.Game;
import MahJavaLib.player.Player;
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
        String[] names = {"Leonardo", "Raphael", "Donatello", "Michelangelo"};
        for (int i = 0; i < 2; ++i) {
            players.add(Player.EagerReactivePlayer(names[i]));
            players.add(Player.ComposedReactivePlayer(names[i + 2]));
        }

        Game game = new Game(players);
        game.gameLoop();
    }
}