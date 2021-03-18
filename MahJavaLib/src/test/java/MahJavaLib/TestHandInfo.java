package MahJavaLib;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.List;

import MahJavaLib.MahjongPlayer.CombinationType;

public class TestHandInfo {
    @Test
    public void testAllPungs() {
        List<MahjongTile> tiles = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.ONE));
        }

        for (int i = 0; i < 3; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.DOTS, MahjongTile.TileContent.THREE));
        }

        for (int i = 0; i < 3; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.FIVE));
        }

        for (int i = 0; i < 3; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.WIND, MahjongTile.TileContent.EAST));
        }

        for (int i = 0; i < 2; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.DRAGON, MahjongTile.TileContent.RED));
        }

        // The order of insertion assures that the tiles are sorted, so HandInfo will work
        // Generated tiles: [1B,1B,1B,3D,3D,3D,5C,5C,5C,WE,WE,WE,DR,DR]
        HandInfo info = new HandInfo(tiles);
        List<List<CombinationType>> winningHands = info.getWinningHands();

        assertEquals(winningHands.get(0).get(0), CombinationType.PUNG);
        assertEquals(winningHands.get(0).get(1), CombinationType.PUNG);
        assertEquals(winningHands.get(0).get(2), CombinationType.PUNG);
        assertEquals(winningHands.get(0).get(3), CombinationType.PUNG);
        assertEquals(winningHands.get(0).get(4), CombinationType.PAIR);
    }

    @Test
    public void testSimpleChows() {
        List<MahjongTile> tiles = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.ONE));
        }

        for (int i = 1; i < 4; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.DOTS, MahjongTile.TileContent.fromValue(i)));
        }

        for (int i = 0; i < 3; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.FIVE));
        }

        for (int i = 7; i < 10; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.fromValue(i)));
        }

        for (int i = 0; i < 2; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.DRAGON, MahjongTile.TileContent.RED));
        }

        // The order of insertion assures that the tiles are sorted, so HandInfo will work
        // Generated tiles: [1B,1B,1B,1D,2D,3D,5C,5C,5C,7C,8C,9C,DR,DR]
        HandInfo info = new HandInfo(tiles);
        List<List<CombinationType>> winningHands = info.getWinningHands();

        assertEquals(winningHands.get(0).get(0), CombinationType.PUNG);
        assertEquals(winningHands.get(0).get(1), CombinationType.CHOW);
        assertEquals(winningHands.get(0).get(2), CombinationType.PUNG);
        assertEquals(winningHands.get(0).get(3), CombinationType.CHOW);
        assertEquals(winningHands.get(0).get(4), CombinationType.PAIR);
    }

    @Test
    public void testInterleavedChows() {
        List<MahjongTile> tiles = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.ONE));
        }

        for (int i = 0; i < 2; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.DOTS, MahjongTile.TileContent.ONE));
        }

        for (int i = 0; i < 2; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.DOTS, MahjongTile.TileContent.TWO));
        }

        for (int i = 0; i < 2; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.DOTS, MahjongTile.TileContent.THREE));
        }

        for (int i = 7; i < 10; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.fromValue(i)));
        }

        for (int i = 0; i < 2; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.DRAGON, MahjongTile.TileContent.RED));
        }

        // The order of insertion assures that the tiles are sorted, so HandInfo will work
        // Generated tiles: [1B,1B,1B,1D,1D,2D,2D,3D,3D,7C,8C,9C,DR,DR]
        HandInfo info = new HandInfo(tiles);
        List<List<CombinationType>> winningHands = info.getWinningHands();

        assertEquals(winningHands.get(0).get(0), CombinationType.PUNG);
        assertEquals(winningHands.get(0).get(1), CombinationType.CHOW);
        assertEquals(winningHands.get(0).get(2), CombinationType.CHOW);
        assertEquals(winningHands.get(0).get(3), CombinationType.CHOW);
        assertEquals(winningHands.get(0).get(4), CombinationType.PAIR);
    }

    @Test
    public void testDelayedInterleavedChows() {
        List<MahjongTile> tiles = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.ONE));
        }

        for (int i = 1; i < 4; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.DOTS, MahjongTile.TileContent.fromValue(i)));
            tiles.add(new MahjongTile(MahjongTile.TileType.DOTS, MahjongTile.TileContent.fromValue(i + 1)));
        }

        for (int i = 7; i < 10; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.fromValue(i)));
        }

        for (int i = 0; i < 2; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.DRAGON, MahjongTile.TileContent.RED));
        }

        // The order of insertion assures that the tiles are sorted, so HandInfo will work
        // Generated tiles: [1B,1B,1B,1D,2D,2D,3D,3D,4D,7C,8C,9C,DR,DR]
        HandInfo info = new HandInfo(tiles);
        List<List<CombinationType>> winningHands = info.getWinningHands();

        assertEquals(winningHands.get(0).get(0), CombinationType.PUNG);
        assertEquals(winningHands.get(0).get(1), CombinationType.CHOW);
        assertEquals(winningHands.get(0).get(2), CombinationType.CHOW);
        assertEquals(winningHands.get(0).get(3), CombinationType.CHOW);
        assertEquals(winningHands.get(0).get(4), CombinationType.PAIR);
    }

    @Test
    public void testEdgeChows() {
        List<MahjongTile> tiles = new ArrayList<>();

        for (int i = 1; i < 4; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.DOTS, MahjongTile.TileContent.fromValue(i)));
        }

        for (int i = 0; i < 2; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.DOTS, MahjongTile.TileContent.THREE));
        }

        for (int i = 3; i < 6; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.DOTS, MahjongTile.TileContent.fromValue(i)));
        }

        for (int i = 7; i < 10; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.CHARACTERS, MahjongTile.TileContent.fromValue(i)));
        }

        for (int i = 0; i < 2; i++) {
            tiles.add(new MahjongTile(MahjongTile.TileType.DRAGON, MahjongTile.TileContent.RED));
        }

        // The order of insertion assures that the tiles are sorted, so HandInfo will work
        // Generated tiles: [1D,2D,3D,3D,3D,3D,4D,5D,7C,8C,9C,DR,DR]
        HandInfo info = new HandInfo(tiles);
        List<List<CombinationType>> winningHands = info.getWinningHands();

        assertEquals(winningHands.get(0).get(0), CombinationType.PUNG);
        assertEquals(winningHands.get(0).get(1), CombinationType.CHOW);
        assertEquals(winningHands.get(0).get(2), CombinationType.CHOW);
        assertEquals(winningHands.get(0).get(3), CombinationType.CHOW);
        assertEquals(winningHands.get(0).get(4), CombinationType.PAIR);
    }

    @Test
    public void testKongs() {
        // Not sure how to test the kongs
    }
}
