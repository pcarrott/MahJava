package MahJavaLib;

import MahJavaLib.hand.Hand;
import MahJavaLib.hand.HandInfo;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.List;

import MahJavaLib.Tile.*;
import MahJavaLib.Player.CombinationType;

public class TestHandInfo {
    @Test
    public void testAllPungs() {
        ArrayList<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            tiles.add(new Tile(TileType.BAMBOO, TileContent.ONE));
        }

        for (int i = 0; i < 3; i++) {
            tiles.add(new Tile(TileType.DOTS, TileContent.THREE));
        }

        for (int i = 0; i < 3; i++) {
            tiles.add(new Tile(TileType.CHARACTERS, TileContent.FIVE));
        }

        for (int i = 0; i < 3; i++) {
            tiles.add(new Tile(TileType.WIND, TileContent.EAST));
        }

        for (int i = 0; i < 2; i++) {
            tiles.add(new Tile(TileType.DRAGON, TileContent.RED));
        }

        // The order of insertion assures that the tiles are sorted, so HandInfo will work
        // Generated tiles: [1B,1B,1B,3D,3D,3D,5C,5C,5C,WE,WE,WE,DR,DR]
        Hand hand = new Hand(tiles);
        //List<List<CombinationType>> winningHands = info.getWinningHands();
//
        //assertEquals(winningHands.size(), 1);
        //assertEquals(winningHands.get(0).get(0), CombinationType.PUNG); // 1B,1B,1B
        //assertEquals(winningHands.get(0).get(1), CombinationType.PUNG); // 3D,3D,3D
        //assertEquals(winningHands.get(0).get(2), CombinationType.PUNG); // 5C,5C,5C
        //assertEquals(winningHands.get(0).get(3), CombinationType.PUNG); // WE,WE,WE
        //assertEquals(winningHands.get(0).get(4), CombinationType.PAIR); // DR,DR
    }

    @Test
    public void testSimpleChows() {
        ArrayList<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            tiles.add(new Tile(TileType.BAMBOO, TileContent.ONE));
        }

        for (int i = 1; i < 4; i++) {
            tiles.add(new Tile(TileType.DOTS, TileContent.fromValue(i)));
        }

        for (int i = 0; i < 3; i++) {
            tiles.add(new Tile(TileType.CHARACTERS, TileContent.FIVE));
        }

        for (int i = 7; i < 10; i++) {
            tiles.add(new Tile(TileType.CHARACTERS, TileContent.fromValue(i)));
        }

        for (int i = 0; i < 2; i++) {
            tiles.add(new Tile(TileType.DRAGON, TileContent.RED));
        }

        // The order of insertion assures that the tiles are sorted, so HandInfo will work
        // Generated tiles: [1B,1B,1B,1D,2D,3D,5C,5C,5C,7C,8C,9C,DR,DR]
        Hand hand = new Hand(tiles);
        //List<List<CombinationType>> winningHands = info.getWinningHands();
//
        //assertEquals(winningHands.size(), 1);
        //assertEquals(winningHands.get(0).get(0), CombinationType.PUNG); // 1B,1B,1B
        //assertEquals(winningHands.get(0).get(1), CombinationType.CHOW); // 1D,2D,3D
        //assertEquals(winningHands.get(0).get(2), CombinationType.PUNG); // 5C,5C,5C
        //assertEquals(winningHands.get(0).get(3), CombinationType.CHOW); // 7C,8C,9C
        //assertEquals(winningHands.get(0).get(4), CombinationType.PAIR); // DR,DR
    }

    @Test
    public void testChows() {
        ArrayList<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            tiles.add(new Tile(TileType.BAMBOO, TileContent.ONE));
        }

        for (int i = 0; i < 3; i++) {
            tiles.add(new Tile(TileType.CHARACTERS, TileContent.NINE));
        }

        for (int i = 1; i < 7; i++) {
            tiles.add(new Tile(TileType.CHARACTERS, TileContent.fromValue(i)));
        }

        for (int i = 0; i < 2; i++) {
            tiles.add(new Tile(TileType.DRAGON, TileContent.RED));
        }

        // The order of insertion assures that the tiles are sorted, so HandInfo will work
        // Generated tiles: [1B,1B,1B,1D,2D,3D,5C,5C,5C,7C,8C,9C,DR,DR]
        Hand hand = new Hand(tiles);
        //List<List<CombinationType>> winningHands = info.getWinningHands();
//
        //assertEquals(winningHands.size(), 1);
        //assertEquals(winningHands.get(0).get(0), CombinationType.PUNG); // 1B,1B,1B
        //assertEquals(winningHands.get(0).get(1), CombinationType.CHOW); // 1D,2D,3D
        //assertEquals(winningHands.get(0).get(2), CombinationType.PUNG); // 5C,5C,5C
        //assertEquals(winningHands.get(0).get(3), CombinationType.CHOW); // 7C,8C,9C
        //assertEquals(winningHands.get(0).get(4), CombinationType.PAIR); // DR,DR
    }

    @Test
    public void testBigChow() {
        ArrayList<Tile> tiles = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            tiles.add(new Tile(TileType.DOTS, TileContent.fromValue(i)));
        }

        for (int i = 7; i < 10; i++) {
            tiles.add(new Tile(TileType.CHARACTERS, TileContent.fromValue(i)));
        }

        for (int i = 0; i < 2; i++) {
            tiles.add(new Tile(TileType.DRAGON, TileContent.RED));
        }

        // The order of insertion assures that the tiles are sorted, so HandInfo will work
        // Generated tiles: [1D,2D,3D,4D,5D,6D,7D,8D,9D,7C,8C,9C,DR,DR]
        Hand hand = new Hand(tiles);
        //List<List<CombinationType>> winningHands = info.getWinningHands();
//
        //assertEquals(winningHands.size(), 1);
        //assertEquals(winningHands.get(0).get(0), CombinationType.CHOW); // 1D,2D,3D
        //assertEquals(winningHands.get(0).get(1), CombinationType.CHOW); // 4D,5D,6D
        //assertEquals(winningHands.get(0).get(2), CombinationType.CHOW); // 7D,8D,9D
        //assertEquals(winningHands.get(0).get(3), CombinationType.CHOW); // 7C,8C,9C
        //assertEquals(winningHands.get(0).get(4), CombinationType.PAIR); // DR,DR
    }

    @Test
    public void testInterleavedChows() {
        ArrayList<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            tiles.add(new Tile(TileType.BAMBOO, TileContent.ONE));
        }

        for (int i = 0; i < 2; i++) {
            tiles.add(new Tile(TileType.DOTS, TileContent.ONE));
        }

        for (int i = 0; i < 2; i++) {
            tiles.add(new Tile(TileType.DOTS, TileContent.TWO));
        }

        for (int i = 0; i < 2; i++) {
            tiles.add(new Tile(TileType.DOTS, TileContent.THREE));
        }

        for (int i = 7; i < 10; i++) {
            tiles.add(new Tile(TileType.CHARACTERS, TileContent.fromValue(i)));
        }

        for (int i = 0; i < 2; i++) {
            tiles.add(new Tile(TileType.DRAGON, TileContent.RED));
        }

        // The order of insertion assures that the tiles are sorted, so HandInfo will work
        // Generated tiles: [1B,1B,1B,1D,1D,2D,2D,3D,3D,7C,8C,9C,DR,DR]
        Hand hand = new Hand(tiles);
        //List<List<CombinationType>> winningHands = info.getWinningHands();
//
        //assertEquals(winningHands.size(), 1);
        //assertEquals(winningHands.get(0).get(0), CombinationType.PUNG); // 1B,1B,1B
        //assertEquals(winningHands.get(0).get(1), CombinationType.CHOW); // 1D,2D,3D
        //assertEquals(winningHands.get(0).get(2), CombinationType.CHOW); // 1D,2D,3D
        //assertEquals(winningHands.get(0).get(3), CombinationType.CHOW); // 7C,8C,9C
        //assertEquals(winningHands.get(0).get(4), CombinationType.PAIR); // DR,DR
    }

    @Test
    public void testDelayedInterleavedChows() {
        ArrayList<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            tiles.add(new Tile(TileType.BAMBOO, TileContent.ONE));
        }

        for (int i = 1; i < 4; i++) {
            tiles.add(new Tile(TileType.DOTS, TileContent.fromValue(i)));
            tiles.add(new Tile(TileType.DOTS, TileContent.fromValue(i + 1)));
        }

        for (int i = 7; i < 10; i++) {
            tiles.add(new Tile(TileType.CHARACTERS, TileContent.fromValue(i)));
        }

        for (int i = 0; i < 2; i++) {
            tiles.add(new Tile(TileType.DRAGON, TileContent.RED));
        }

        // The order of insertion assures that the tiles are sorted, so HandInfo will work
        // Generated tiles: [1B,1B,1B,1D,2D,2D,3D,3D,4D,7C,8C,9C,DR,DR]
        Hand hand = new Hand(tiles);
        //List<List<CombinationType>> winningHands = info.getWinningHands();
//
        //assertEquals(winningHands.size(), 1);
        //assertEquals(winningHands.get(0).get(0), CombinationType.PUNG); // 1B,1B,1B
        //assertEquals(winningHands.get(0).get(1), CombinationType.CHOW); // 1D,2D,3D
        //assertEquals(winningHands.get(0).get(2), CombinationType.CHOW); // 2D,3D,4D
        //assertEquals(winningHands.get(0).get(3), CombinationType.CHOW); // 7C,8C,9C
        //assertEquals(winningHands.get(0).get(4), CombinationType.PAIR); // DR,DR
    }

    @Test
    public void testEdgeChows() {
        ArrayList<Tile> tiles = new ArrayList<>();

        for (int i = 1; i < 4; i++) {
            tiles.add(new Tile(TileType.DOTS, TileContent.fromValue(i)));
        }

        for (int i = 0; i < 2; i++) {
            tiles.add(new Tile(TileType.DOTS, TileContent.THREE));
        }

        for (int i = 3; i < 6; i++) {
            tiles.add(new Tile(TileType.DOTS, TileContent.fromValue(i)));
        }

        for (int i = 7; i < 10; i++) {
            tiles.add(new Tile(TileType.CHARACTERS, TileContent.fromValue(i)));
        }

        for (int i = 0; i < 3; i++) {
            tiles.add(new Tile(TileType.DRAGON, TileContent.RED));
        }

        // The order of insertion assures that the tiles are sorted, so HandInfo will work
        // Generated tiles: [1D,2D,3D,3D,3D,3D,4D,5D,7C,8C,9C,DR,DR,DR]
        Hand hand = new Hand(tiles);
        //List<List<CombinationType>> winningHands = info.getWinningHands();
//
        //assertEquals(winningHands.size(), 1);
        //assertEquals(winningHands.get(0).get(0), CombinationType.CHOW); // 1D,2D,3D
        //assertEquals(winningHands.get(0).get(1), CombinationType.PAIR); // 3D,3D
        //assertEquals(winningHands.get(0).get(2), CombinationType.CHOW); // 3D,4D,5D
        //assertEquals(winningHands.get(0).get(3), CombinationType.CHOW); // 7C,8C,9C
        //assertEquals(winningHands.get(0).get(4), CombinationType.PUNG); // DR,DR,DR
    }

    @Test
    public void testKongs() {
        // Not sure how to test the kongs
    }

    @Test
    public void testMultipleWinningHands() {
        ArrayList<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            tiles.add(new Tile(TileType.BAMBOO, TileContent.ONE));
        }

        for (int i = 0; i < 3; i++) {
            tiles.add(new Tile(TileType.BAMBOO, TileContent.TWO));
        }

        for (int i = 0; i < 3; i++) {
            tiles.add(new Tile(TileType.BAMBOO, TileContent.THREE));
        }

        for (int i = 4; i < 7; i++) {
            tiles.add(new Tile(TileType.BAMBOO, TileContent.fromValue(i)));
        }

        for (int i = 0; i < 2; i++) {
            tiles.add(new Tile(TileType.BAMBOO, TileContent.SEVEN));
        }

        // The order of insertion assures that the tiles are sorted, so HandInfo will work
        // Generated tiles: [1B,1B,1B,2B,2B,2B,3B,3B,3B,4B,5B,6B,7B,7B]
        Hand hand = new Hand(tiles);
        //List<List<CombinationType>> winningHands = info.getWinningHands();
//
        //assertEquals(winningHands.size(), 2);
//
        //assertEquals(winningHands.get(0).get(0), CombinationType.PUNG); // 1B,1B,1B
        //assertEquals(winningHands.get(0).get(1), CombinationType.PUNG); // 2B,2B,2B
        //assertEquals(winningHands.get(0).get(2), CombinationType.PUNG); // 3B,3B,3B
        //assertEquals(winningHands.get(0).get(3), CombinationType.CHOW); // 4B,5B,6B
        //assertEquals(winningHands.get(0).get(4), CombinationType.PAIR); // 7B,7B
//
        //assertEquals(winningHands.get(1).get(0), CombinationType.CHOW); // 1B,2B,3B
        //assertEquals(winningHands.get(1).get(1), CombinationType.CHOW); // 1B,2B,3B
        //assertEquals(winningHands.get(1).get(2), CombinationType.CHOW); // 1B,2B,3B
        //assertEquals(winningHands.get(1).get(3), CombinationType.CHOW); // 4B,5B,6B
        //assertEquals(winningHands.get(1).get(4), CombinationType.PAIR); // 7B,7B
    }
}
