package MahJavaLib;

import MahJavaLib.hand.CombinationSet;
import MahJavaLib.hand.Hand;
import MahJavaLib.tile.Tile;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

import java.util.*;
import java.util.stream.Collectors;

import MahJavaLib.tile.*;

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

        tiles.add(new Tile(TileType.DRAGON, TileContent.RED));

        // Generated tiles: [1B,1B,1B,3D,3D,3D,5C,5C,5C,EW,EW,EW,RD,RD]
        Collections.shuffle(tiles);
        Hand hand = new Hand(tiles);

        // Assert that discarded tile is a winning tile
        assertTrue(hand.isWinningTile(new Tile(TileType.DRAGON, TileContent.RED)));

        // Add winning tile
        hand.addTile(new Tile(TileType.DRAGON, TileContent.RED));

        // 111|B 333|D 555|C EEE|W RR|D
        CombinationSet set = new CombinationSet();
        set.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.BAMBOO, TileContent.ONE), 3));
        set.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.DOTS, TileContent.THREE), 3));
        set.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.CHARACTERS, TileContent.FIVE), 3));
        set.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.WIND, TileContent.EAST), 3));
        set.addPair(new Tile(TileType.DRAGON, TileContent.RED));

        // Generate expected hands from adding winning tile
        List<CombinationSet> allPossibleHands = Collections.singletonList(set);

        // Compare generated hands with computed hands
        this.assertHands(hand, allPossibleHands);
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

        tiles.add(new Tile(TileType.DRAGON, TileContent.RED));

        // Generated tiles: [1B,1B,1B,1D,2D,3D,5C,5C,5C,7C,8C,9C,RD,RD]
        Collections.shuffle(tiles);
        Hand hand = new Hand(tiles);

        // Assert that discarded tile is a winning tile
        assertTrue(hand.isWinningTile(new Tile(TileType.DRAGON, TileContent.RED)));

        // Add winning tile
        hand.addTile(new Tile(TileType.DRAGON, TileContent.RED));

        // 111|B 123|D 555|C 789|C RR|D
        CombinationSet set = new CombinationSet();
        set.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.BAMBOO, TileContent.ONE), 3));
        set.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.ONE), 1,
                new Tile(TileType.DOTS, TileContent.TWO), 1,
                new Tile(TileType.DOTS, TileContent.THREE), 1
        ));
        set.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.CHARACTERS, TileContent.FIVE), 3));
        set.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1,
                new Tile(TileType.CHARACTERS, TileContent.EIGHT), 1,
                new Tile(TileType.CHARACTERS, TileContent.NINE), 1
        ));
        set.addPair(new Tile(TileType.DRAGON, TileContent.RED));

        // Generate expected hands from adding winning tile
        List<CombinationSet> allPossibleHands = Collections.singletonList(set);

        // Compare generated hands with computed hands
        this.assertHands(hand, allPossibleHands);
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

        tiles.add(new Tile(TileType.DRAGON, TileContent.RED));

        // Generated tiles: [1B,1B,1B,9C,9C,9C,1C,2C,3C,4C,5C,6C,RD,RD]
        Collections.shuffle(tiles);
        Hand hand = new Hand(tiles);

        // Assert that discarded tile is a winning tile
        assertTrue(hand.isWinningTile(new Tile(TileType.DRAGON, TileContent.RED)));

        // Add winning tile
        hand.addTile(new Tile(TileType.DRAGON, TileContent.RED));

        // 111|B 999|C 123|C 456|C RR|D
        CombinationSet set1 = new CombinationSet();
        set1.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.BAMBOO, TileContent.ONE), 3));
        set1.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.CHARACTERS, TileContent.NINE), 3));
        set1.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.CHARACTERS, TileContent.ONE), 1,
                new Tile(TileType.CHARACTERS, TileContent.TWO), 1,
                new Tile(TileType.CHARACTERS, TileContent.THREE), 1
        ));
        set1.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.CHARACTERS, TileContent.FOUR), 1,
                new Tile(TileType.CHARACTERS, TileContent.FIVE), 1,
                new Tile(TileType.CHARACTERS, TileContent.SIX), 1
        ));
        set1.addPair(new Tile(TileType.DRAGON, TileContent.RED));

        // 111|B 999|C 234|C RR|D
        CombinationSet set2 = new CombinationSet();
        set2.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.BAMBOO, TileContent.ONE), 3));
        set2.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.CHARACTERS, TileContent.NINE), 3));
        set2.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.CHARACTERS, TileContent.TWO), 1,
                new Tile(TileType.CHARACTERS, TileContent.THREE), 1,
                new Tile(TileType.CHARACTERS, TileContent.FOUR), 1
        ));
        set2.addPair(new Tile(TileType.DRAGON, TileContent.RED));
        set2.addIgnored(new Tile(TileType.CHARACTERS, TileContent.ONE));
        set2.addIgnored(new Tile(TileType.CHARACTERS, TileContent.FIVE));
        set2.addIgnored(new Tile(TileType.CHARACTERS, TileContent.SIX));

        // 111|B 999|C 345|C RR|D
        CombinationSet set3 = new CombinationSet();
        set3.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.BAMBOO, TileContent.ONE), 3));
        set3.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.CHARACTERS, TileContent.NINE), 3));
        set3.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.CHARACTERS, TileContent.THREE), 1,
                new Tile(TileType.CHARACTERS, TileContent.FOUR), 1,
                new Tile(TileType.CHARACTERS, TileContent.FIVE), 1
        ));
        set3.addPair(new Tile(TileType.DRAGON, TileContent.RED));
        set3.addIgnored(new Tile(TileType.CHARACTERS, TileContent.ONE));
        set3.addIgnored(new Tile(TileType.CHARACTERS, TileContent.TWO));
        set3.addIgnored(new Tile(TileType.CHARACTERS, TileContent.SIX));

        // Generate expected hands from adding winning tile
        List<CombinationSet> allPossibleHands = Arrays.asList(set1, set2, set3);

        // Compare generated hands with computed hands
        this.assertHands(hand, allPossibleHands);
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

        tiles.add(new Tile(TileType.DRAGON, TileContent.RED));

        // Generated tiles: [1D,2D,3D,4D,5D,6D,7D,8D,9D,7C,8C,9C,RD,RD]
        Collections.shuffle(tiles);
        Hand hand = new Hand(tiles);

        // Assert that discarded tile is a winning tile
        assertTrue(hand.isWinningTile(new Tile(TileType.DRAGON, TileContent.RED)));

        // Add winning tile
        hand.addTile(new Tile(TileType.DRAGON, TileContent.RED));

        // 123|D 456|D 789|D 789|C RR|D
        CombinationSet set1 = new CombinationSet();
        set1.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.ONE), 1,
                new Tile(TileType.DOTS, TileContent.TWO), 1,
                new Tile(TileType.DOTS, TileContent.THREE), 1
        ));
        set1.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.FOUR), 1,
                new Tile(TileType.DOTS, TileContent.FIVE), 1,
                new Tile(TileType.DOTS, TileContent.SIX), 1
        ));
        set1.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.SEVEN), 1,
                new Tile(TileType.DOTS, TileContent.EIGHT), 1,
                new Tile(TileType.DOTS, TileContent.NINE), 1
        ));
        set1.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1,
                new Tile(TileType.CHARACTERS, TileContent.EIGHT), 1,
                new Tile(TileType.CHARACTERS, TileContent.NINE), 1
        ));
        set1.addPair(new Tile(TileType.DRAGON, TileContent.RED));

        // 123|D 567|D 789|C RR|D
        CombinationSet set2 = new CombinationSet();
        set2.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.ONE), 1,
                new Tile(TileType.DOTS, TileContent.TWO), 1,
                new Tile(TileType.DOTS, TileContent.THREE), 1
        ));
        set2.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.FIVE), 1,
                new Tile(TileType.DOTS, TileContent.SIX), 1,
                new Tile(TileType.DOTS, TileContent.SEVEN), 1
        ));
        set2.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1,
                new Tile(TileType.CHARACTERS, TileContent.EIGHT), 1,
                new Tile(TileType.CHARACTERS, TileContent.NINE), 1
        ));
        set2.addPair(new Tile(TileType.DRAGON, TileContent.RED));
        set2.addIgnored(new Tile(TileType.DOTS, TileContent.FOUR));
        set2.addIgnored(new Tile(TileType.DOTS, TileContent.EIGHT));
        set2.addIgnored(new Tile(TileType.DOTS, TileContent.NINE));

        // 123|D 678|D 789|C RR|D
        CombinationSet set3 = new CombinationSet();
        set3.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.ONE), 1,
                new Tile(TileType.DOTS, TileContent.TWO), 1,
                new Tile(TileType.DOTS, TileContent.THREE), 1
        ));
        set3.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.SIX), 1,
                new Tile(TileType.DOTS, TileContent.SEVEN), 1,
                new Tile(TileType.DOTS, TileContent.EIGHT), 1
        ));
        set3.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1,
                new Tile(TileType.CHARACTERS, TileContent.EIGHT), 1,
                new Tile(TileType.CHARACTERS, TileContent.NINE), 1
        ));
        set3.addPair(new Tile(TileType.DRAGON, TileContent.RED));
        set3.addIgnored(new Tile(TileType.DOTS, TileContent.FOUR));
        set3.addIgnored(new Tile(TileType.DOTS, TileContent.FIVE));
        set3.addIgnored(new Tile(TileType.DOTS, TileContent.NINE));

        // 234|D 567|D 789|C RR|D
        CombinationSet set4 = new CombinationSet();
        set4.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.TWO), 1,
                new Tile(TileType.DOTS, TileContent.THREE), 1,
                new Tile(TileType.DOTS, TileContent.FOUR), 1
        ));
        set4.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.FIVE), 1,
                new Tile(TileType.DOTS, TileContent.SIX), 1,
                new Tile(TileType.DOTS, TileContent.SEVEN), 1
        ));
        set4.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1,
                new Tile(TileType.CHARACTERS, TileContent.EIGHT), 1,
                new Tile(TileType.CHARACTERS, TileContent.NINE), 1
        ));
        set4.addPair(new Tile(TileType.DRAGON, TileContent.RED));
        set4.addIgnored(new Tile(TileType.DOTS, TileContent.ONE));
        set4.addIgnored(new Tile(TileType.DOTS, TileContent.EIGHT));
        set4.addIgnored(new Tile(TileType.DOTS, TileContent.NINE));

        // 234|D 678|D 789|C RR|D
        CombinationSet set5 = new CombinationSet();
        set5.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.TWO), 1,
                new Tile(TileType.DOTS, TileContent.THREE), 1,
                new Tile(TileType.DOTS, TileContent.FOUR), 1
        ));
        set5.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.SIX), 1,
                new Tile(TileType.DOTS, TileContent.SEVEN), 1,
                new Tile(TileType.DOTS, TileContent.EIGHT), 1
        ));
        set5.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1,
                new Tile(TileType.CHARACTERS, TileContent.EIGHT), 1,
                new Tile(TileType.CHARACTERS, TileContent.NINE), 1
        ));
        set5.addPair(new Tile(TileType.DRAGON, TileContent.RED));
        set5.addIgnored(new Tile(TileType.DOTS, TileContent.ONE));
        set5.addIgnored(new Tile(TileType.DOTS, TileContent.FIVE));
        set5.addIgnored(new Tile(TileType.DOTS, TileContent.NINE));

        // 234|D 789|D 789|C RR|D
        CombinationSet set6 = new CombinationSet();
        set6.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.TWO), 1,
                new Tile(TileType.DOTS, TileContent.THREE), 1,
                new Tile(TileType.DOTS, TileContent.FOUR), 1
        ));
        set6.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.SEVEN), 1,
                new Tile(TileType.DOTS, TileContent.EIGHT), 1,
                new Tile(TileType.DOTS, TileContent.NINE), 1
        ));
        set6.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1,
                new Tile(TileType.CHARACTERS, TileContent.EIGHT), 1,
                new Tile(TileType.CHARACTERS, TileContent.NINE), 1
        ));
        set6.addPair(new Tile(TileType.DRAGON, TileContent.RED));
        set6.addIgnored(new Tile(TileType.DOTS, TileContent.ONE));
        set6.addIgnored(new Tile(TileType.DOTS, TileContent.FIVE));
        set6.addIgnored(new Tile(TileType.DOTS, TileContent.SIX));

        // 345|D 678|D 789|C RR|D
        CombinationSet set7 = new CombinationSet();
        set7.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.THREE), 1,
                new Tile(TileType.DOTS, TileContent.FOUR), 1,
                new Tile(TileType.DOTS, TileContent.FIVE), 1
        ));
        set7.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.SIX), 1,
                new Tile(TileType.DOTS, TileContent.SEVEN), 1,
                new Tile(TileType.DOTS, TileContent.EIGHT), 1
        ));
        set7.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1,
                new Tile(TileType.CHARACTERS, TileContent.EIGHT), 1,
                new Tile(TileType.CHARACTERS, TileContent.NINE), 1
        ));
        set7.addPair(new Tile(TileType.DRAGON, TileContent.RED));
        set7.addIgnored(new Tile(TileType.DOTS, TileContent.ONE));
        set7.addIgnored(new Tile(TileType.DOTS, TileContent.TWO));
        set7.addIgnored(new Tile(TileType.DOTS, TileContent.NINE));

        // 345|D 789|D 789|C RR|D
        CombinationSet set8 = new CombinationSet();
        set8.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.THREE), 1,
                new Tile(TileType.DOTS, TileContent.FOUR), 1,
                new Tile(TileType.DOTS, TileContent.FIVE), 1
        ));
        set8.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.SEVEN), 1,
                new Tile(TileType.DOTS, TileContent.EIGHT), 1,
                new Tile(TileType.DOTS, TileContent.NINE), 1
        ));
        set8.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1,
                new Tile(TileType.CHARACTERS, TileContent.EIGHT), 1,
                new Tile(TileType.CHARACTERS, TileContent.NINE), 1
        ));
        set8.addPair(new Tile(TileType.DRAGON, TileContent.RED));
        set8.addIgnored(new Tile(TileType.DOTS, TileContent.ONE));
        set8.addIgnored(new Tile(TileType.DOTS, TileContent.TWO));
        set8.addIgnored(new Tile(TileType.DOTS, TileContent.SIX));

        // Generate expected hands from adding winning tile
        List<CombinationSet> allPossibleHands = Arrays.asList(set1, set2, set3, set4, set5, set6, set7, set8);

        // Compare generated hands with computed hands
        this.assertHands(hand, allPossibleHands);
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

        tiles.add(new Tile(TileType.DRAGON, TileContent.RED));

        // Generated tiles: [1B,1B,1B,1D,1D,2D,2D,3D,3D,7C,8C,9C,RD,RD]
        Collections.shuffle(tiles);
        Hand hand = new Hand(tiles);

        // Assert that discarded tile is a winning tile
        assertTrue(hand.isWinningTile(new Tile(TileType.DRAGON, TileContent.RED)));

        // Add winning tile
        hand.addTile(new Tile(TileType.DRAGON, TileContent.RED));

        // 111|B 11|D 22|D 33|D 789|C RR|D
        CombinationSet set1 = new CombinationSet();
        set1.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.BAMBOO, TileContent.ONE), 3));
        set1.addPair(new Tile(TileType.DOTS, TileContent.ONE));
        set1.addPair(new Tile(TileType.DOTS, TileContent.TWO));
        set1.addPair(new Tile(TileType.DOTS, TileContent.THREE));
        set1.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1,
                new Tile(TileType.CHARACTERS, TileContent.EIGHT), 1,
                new Tile(TileType.CHARACTERS, TileContent.NINE), 1
        ));
        set1.addPair(new Tile(TileType.DRAGON, TileContent.RED));

        // 111|B 123|D 123|D 789|C RR|D
        CombinationSet set2 = new CombinationSet();
        set2.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.BAMBOO, TileContent.ONE), 3));
        set2.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.ONE), 1,
                new Tile(TileType.DOTS, TileContent.TWO), 1,
                new Tile(TileType.DOTS, TileContent.THREE), 1
        ), 2);
        set2.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1,
                new Tile(TileType.CHARACTERS, TileContent.EIGHT), 1,
                new Tile(TileType.CHARACTERS, TileContent.NINE), 1
        ));
        set2.addPair(new Tile(TileType.DRAGON, TileContent.RED));

        // Generate expected hands from adding winning tile
        List<CombinationSet> allPossibleHands = Arrays.asList(set1, set2);

        // Compare generated hands with computed hands
        this.assertHands(hand, allPossibleHands);
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

        tiles.add(new Tile(TileType.DRAGON, TileContent.RED));

        // Generated tiles: [1B,1B,1B,1D,2D,2D,3D,3D,4D,7C,8C,9C,RD,RD]
        Collections.shuffle(tiles);
        Hand hand = new Hand(tiles);

        // Assert that discarded tile is a winning tile
        assertTrue(hand.isWinningTile(new Tile(TileType.DRAGON, TileContent.RED)));

        // Add winning tile
        hand.addTile(new Tile(TileType.DRAGON, TileContent.RED));

        // 111|B 22|D 33|D 789|C RR|D
        CombinationSet set1 = new CombinationSet();
        set1.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.BAMBOO, TileContent.ONE), 3));
        set1.addPair(new Tile(TileType.DOTS, TileContent.TWO));
        set1.addPair(new Tile(TileType.DOTS, TileContent.THREE));
        set1.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1,
                new Tile(TileType.CHARACTERS, TileContent.EIGHT), 1,
                new Tile(TileType.CHARACTERS, TileContent.NINE), 1
        ));
        set1.addPair(new Tile(TileType.DRAGON, TileContent.RED));
        set1.addIgnored(new Tile(TileType.DOTS, TileContent.ONE));
        set1.addIgnored(new Tile(TileType.DOTS, TileContent.FOUR));

        // 111|B 123|D 234|D 789|C RR|D
        CombinationSet set2 = new CombinationSet();
        set2.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.BAMBOO, TileContent.ONE), 3));
        set2.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.ONE), 1,
                new Tile(TileType.DOTS, TileContent.TWO), 1,
                new Tile(TileType.DOTS, TileContent.THREE), 1
        ));
        set2.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.TWO), 1,
                new Tile(TileType.DOTS, TileContent.THREE), 1,
                new Tile(TileType.DOTS, TileContent.FOUR), 1
        ));
        set2.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1,
                new Tile(TileType.CHARACTERS, TileContent.EIGHT), 1,
                new Tile(TileType.CHARACTERS, TileContent.NINE), 1
        ));
        set2.addPair(new Tile(TileType.DRAGON, TileContent.RED));

        // Generate expected hands from adding winning tile
        List<CombinationSet> allPossibleHands = Arrays.asList(set1, set2);

        // Compare generated hands with computed hands
        this.assertHands(hand, allPossibleHands);
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

        for (int i = 0; i < 2; i++) {
            tiles.add(new Tile(TileType.DRAGON, TileContent.RED));
        }

        // Generated tiles: [1D,2D,3D,3D,3D,3D,4D,5D,7C,8C,9C,RD,RD,RD]
        Collections.shuffle(tiles);
        Hand hand = new Hand(tiles);

        // Assert that discarded tile is a winning tile
        assertTrue(hand.isWinningTile(new Tile(TileType.DRAGON, TileContent.RED)));

        // Since we can make a Pung with a Red Dragon, we compute all possible hands with that Pung.
        var possibleCombinationsForTile =
                hand.getPossibleCombinationsForTile(new Tile(TileType.DRAGON, TileContent.RED))
                        .values().stream()
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());

        // Add winning tile
        hand.claimTile(
                new Tile(TileType.DRAGON, TileContent.RED),
                new Combination(
                        CombinationType.PUNG,
                        Collections.nCopies(3, new Tile(TileType.DRAGON, TileContent.RED))
                )
        );

        // 123|D 33|D 345|D 789|C RRR|D
        CombinationSet set1 = new CombinationSet();
        set1.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.ONE), 1,
                new Tile(TileType.DOTS, TileContent.TWO), 1,
                new Tile(TileType.DOTS, TileContent.THREE), 1
        ));
        set1.addPair(new Tile(TileType.DOTS, TileContent.THREE));
        set1.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.THREE), 1,
                new Tile(TileType.DOTS, TileContent.FOUR), 1,
                new Tile(TileType.DOTS, TileContent.FIVE), 1
        ));
        set1.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1,
                new Tile(TileType.CHARACTERS, TileContent.EIGHT), 1,
                new Tile(TileType.CHARACTERS, TileContent.NINE), 1
        ));
        set1.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.DRAGON, TileContent.RED), 3));

        // 123|D 333|D 789|C RRR|D
        CombinationSet set2 = new CombinationSet();
        set2.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.ONE), 1,
                new Tile(TileType.DOTS, TileContent.TWO), 1,
                new Tile(TileType.DOTS, TileContent.THREE), 1
        ));
        set2.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.DOTS, TileContent.THREE), 3));
        set2.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1,
                new Tile(TileType.CHARACTERS, TileContent.EIGHT), 1,
                new Tile(TileType.CHARACTERS, TileContent.NINE), 1
        ));
        set2.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.DRAGON, TileContent.RED), 3));
        set2.addIgnored(new Tile(TileType.DOTS, TileContent.FOUR));
        set2.addIgnored(new Tile(TileType.DOTS, TileContent.FIVE));

        // 234|D 333|D 789|C RRR|D
        CombinationSet set3 = new CombinationSet();
        set3.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.TWO), 1,
                new Tile(TileType.DOTS, TileContent.THREE), 1,
                new Tile(TileType.DOTS, TileContent.FOUR), 1
        ));
        set3.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.DOTS, TileContent.THREE), 3));
        set3.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1,
                new Tile(TileType.CHARACTERS, TileContent.EIGHT), 1,
                new Tile(TileType.CHARACTERS, TileContent.NINE), 1
        ));
        set3.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.DRAGON, TileContent.RED), 3));
        set3.addIgnored(new Tile(TileType.DOTS, TileContent.ONE));
        set3.addIgnored(new Tile(TileType.DOTS, TileContent.FIVE));

        // 333|D 345|D 789|C RRR|D
        CombinationSet set4 = new CombinationSet();
        set4.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.DOTS, TileContent.THREE), 3));
        set4.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.DOTS, TileContent.THREE), 1,
                new Tile(TileType.DOTS, TileContent.FOUR), 1,
                new Tile(TileType.DOTS, TileContent.FIVE), 1
        ));
        set4.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1,
                new Tile(TileType.CHARACTERS, TileContent.EIGHT), 1,
                new Tile(TileType.CHARACTERS, TileContent.NINE), 1
        ));
        set4.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.DRAGON, TileContent.RED), 3));
        set4.addIgnored(new Tile(TileType.DOTS, TileContent.ONE));
        set4.addIgnored(new Tile(TileType.DOTS, TileContent.TWO));

        // 33|D 33|D 789|C RRR|D
        CombinationSet set5 = new CombinationSet();
        set5.addPair(new Tile(TileType.DOTS, TileContent.THREE), 2);
        set5.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1,
                new Tile(TileType.CHARACTERS, TileContent.EIGHT), 1,
                new Tile(TileType.CHARACTERS, TileContent.NINE), 1
        ));
        set5.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.DRAGON, TileContent.RED), 3));
        set5.addIgnored(new Tile(TileType.DOTS, TileContent.ONE));
        set5.addIgnored(new Tile(TileType.DOTS, TileContent.TWO));
        set5.addIgnored(new Tile(TileType.DOTS, TileContent.FOUR));
        set5.addIgnored(new Tile(TileType.DOTS, TileContent.FIVE));

        // Generate expected hands from adding winning tile
        List<CombinationSet> allPossibleHands = Arrays.asList(set1, set2, set3, set4, set5);

        // Computed hands from the Pung must be in the generated hands
        assertEquals(possibleCombinationsForTile.size(), 5);
        allPossibleHands.containsAll(possibleCombinationsForTile);

        // Compare generated hands with computed hands
        this.assertHands(hand, allPossibleHands);
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

        tiles.add(new Tile(TileType.BAMBOO, TileContent.SEVEN));

        // Generated tiles: [1B,1B,1B,2B,2B,2B,3B,3B,3B,4B,5B,6B,7B,7B]
        Collections.shuffle(tiles);
        Hand hand = new Hand(tiles);

        // Assert that discarded tile is a winning tile
        assertTrue(hand.isWinningTile(new Tile(TileType.BAMBOO, TileContent.SEVEN)));

        // Since we can make a Chow with a Seven Bamboo, we compute all possible hands with that Chow.
        var possibleCombinationsForTile =
                hand.getPossibleCombinationsForTile(new Tile(TileType.BAMBOO, TileContent.SEVEN))
                        .values().stream()
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());

        // Add winning tile
        hand.addTile(new Tile(TileType.BAMBOO, TileContent.SEVEN));

        // 111|B 222|B 333|B 456|B 77|B
        CombinationSet set1 = new CombinationSet();
        set1.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.BAMBOO, TileContent.ONE), 3));
        set1.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.BAMBOO, TileContent.TWO), 3));
        set1.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.BAMBOO, TileContent.THREE), 3));
        set1.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.FOUR), 1,
                new Tile(TileType.BAMBOO, TileContent.FIVE), 1,
                new Tile(TileType.BAMBOO, TileContent.SIX), 1
        ));
        set1.addPair(new Tile(TileType.BAMBOO, TileContent.SEVEN));

        // 111|B 222|B 333|B 567|B
        CombinationSet set2 = new CombinationSet();
        set2.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.BAMBOO, TileContent.ONE), 3));
        set2.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.BAMBOO, TileContent.TWO), 3));
        set2.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.BAMBOO, TileContent.THREE), 3));
        set2.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.FIVE), 1,
                new Tile(TileType.BAMBOO, TileContent.SIX), 1,
                new Tile(TileType.BAMBOO, TileContent.SEVEN), 1
        ));
        set2.addIgnored(new Tile(TileType.BAMBOO, TileContent.FOUR));
        set2.addIgnored(new Tile(TileType.BAMBOO, TileContent.SEVEN));

        // 111|B 222|B 33|B 345|B 77|B
        CombinationSet set3 = new CombinationSet();
        set3.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.BAMBOO, TileContent.ONE), 3));
        set3.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.BAMBOO, TileContent.TWO), 3));
        set3.addPair(new Tile(TileType.BAMBOO, TileContent.THREE));
        set3.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.THREE), 1,
                new Tile(TileType.BAMBOO, TileContent.FOUR), 1,
                new Tile(TileType.BAMBOO, TileContent.FIVE), 1
        ));
        set3.addIgnored(new Tile(TileType.BAMBOO, TileContent.SIX));
        set3.addPair(new Tile(TileType.BAMBOO, TileContent.SEVEN));

        // 111|B 22|B 33|B 234|B 567|B
        CombinationSet set4 = new CombinationSet();
        set4.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.BAMBOO, TileContent.ONE), 3));
        set4.addPair(new Tile(TileType.BAMBOO, TileContent.TWO));
        set4.addPair(new Tile(TileType.BAMBOO, TileContent.THREE));
        set4.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.TWO), 1,
                new Tile(TileType.BAMBOO, TileContent.THREE), 1,
                new Tile(TileType.BAMBOO, TileContent.FOUR), 1
        ));
        set4.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.FIVE), 1,
                new Tile(TileType.BAMBOO, TileContent.SIX), 1,
                new Tile(TileType.BAMBOO, TileContent.SEVEN), 1
        ));
        set4.addIgnored(new Tile(TileType.BAMBOO, TileContent.SEVEN));

        // 111|B 22|B 33|B 234|B 77|B
        CombinationSet set5 = new CombinationSet();
        set5.addCombination(CombinationType.PUNG, Map.of(new Tile(TileType.BAMBOO, TileContent.ONE), 3));
        set5.addPair(new Tile(TileType.BAMBOO, TileContent.TWO));
        set5.addPair(new Tile(TileType.BAMBOO, TileContent.THREE));
        set5.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.TWO), 1,
                new Tile(TileType.BAMBOO, TileContent.THREE), 1,
                new Tile(TileType.BAMBOO, TileContent.FOUR), 1
        ));
        set5.addPair(new Tile(TileType.BAMBOO, TileContent.SEVEN));
        set5.addIgnored(new Tile(TileType.BAMBOO, TileContent.FIVE));
        set5.addIgnored(new Tile(TileType.BAMBOO, TileContent.SIX));

        // 11|B 22|B 33|B 123|B 456|B 77|B
        CombinationSet set6 = new CombinationSet();
        set6.addPair(new Tile(TileType.BAMBOO, TileContent.ONE));
        set6.addPair(new Tile(TileType.BAMBOO, TileContent.TWO));
        set6.addPair(new Tile(TileType.BAMBOO, TileContent.THREE));
        set6.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.ONE), 1,
                new Tile(TileType.BAMBOO, TileContent.TWO), 1,
                new Tile(TileType.BAMBOO, TileContent.THREE), 1
        ));
        set6.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.FOUR), 1,
                new Tile(TileType.BAMBOO, TileContent.FIVE), 1,
                new Tile(TileType.BAMBOO, TileContent.SIX), 1
        ));
        set6.addPair(new Tile(TileType.BAMBOO, TileContent.SEVEN));

        // 11|B 22|B 33|B 123|B 567|B
        CombinationSet set7 = new CombinationSet();
        set7.addPair(new Tile(TileType.BAMBOO, TileContent.ONE));
        set7.addPair(new Tile(TileType.BAMBOO, TileContent.TWO));
        set7.addPair(new Tile(TileType.BAMBOO, TileContent.THREE));
        set7.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.ONE), 1,
                new Tile(TileType.BAMBOO, TileContent.TWO), 1,
                new Tile(TileType.BAMBOO, TileContent.THREE), 1
        ));
        set7.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.FIVE), 1,
                new Tile(TileType.BAMBOO, TileContent.SIX), 1,
                new Tile(TileType.BAMBOO, TileContent.SEVEN), 1
        ));
        set7.addIgnored(new Tile(TileType.BAMBOO, TileContent.FOUR));
        set7.addIgnored(new Tile(TileType.BAMBOO, TileContent.SEVEN));

        // 11|B 22|B 123|B 345|B 77|B
        CombinationSet set8 = new CombinationSet();
        set8.addPair(new Tile(TileType.BAMBOO, TileContent.ONE));
        set8.addPair(new Tile(TileType.BAMBOO, TileContent.TWO));
        set8.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.ONE), 1,
                new Tile(TileType.BAMBOO, TileContent.TWO), 1,
                new Tile(TileType.BAMBOO, TileContent.THREE), 1
        ));
        set8.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.THREE), 1,
                new Tile(TileType.BAMBOO, TileContent.FOUR), 1,
                new Tile(TileType.BAMBOO, TileContent.FIVE), 1
        ));
        set8.addPair(new Tile(TileType.BAMBOO, TileContent.SEVEN));
        set8.addIgnored(new Tile(TileType.BAMBOO, TileContent.THREE));
        set8.addIgnored(new Tile(TileType.BAMBOO, TileContent.SIX));

        // 11|B 123|B 234|B 567|B
        CombinationSet set9 = new CombinationSet();
        set9.addPair(new Tile(TileType.BAMBOO, TileContent.ONE));
        set9.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.ONE), 1,
                new Tile(TileType.BAMBOO, TileContent.TWO), 1,
                new Tile(TileType.BAMBOO, TileContent.THREE), 1
        ));
        set9.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.TWO), 1,
                new Tile(TileType.BAMBOO, TileContent.THREE), 1,
                new Tile(TileType.BAMBOO, TileContent.FOUR), 1
        ));
        set9.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.FIVE), 1,
                new Tile(TileType.BAMBOO, TileContent.SIX), 1,
                new Tile(TileType.BAMBOO, TileContent.SEVEN), 1
        ));
        set9.addIgnored(new Tile(TileType.BAMBOO, TileContent.TWO));
        set9.addIgnored(new Tile(TileType.BAMBOO, TileContent.THREE));
        set9.addIgnored(new Tile(TileType.BAMBOO, TileContent.SEVEN));

        // 11|B 123|B 234|B 77|B
        CombinationSet set10 = new CombinationSet();
        set10.addPair(new Tile(TileType.BAMBOO, TileContent.ONE));
        set10.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.ONE), 1,
                new Tile(TileType.BAMBOO, TileContent.TWO), 1,
                new Tile(TileType.BAMBOO, TileContent.THREE), 1
        ));
        set10.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.TWO), 1,
                new Tile(TileType.BAMBOO, TileContent.THREE), 1,
                new Tile(TileType.BAMBOO, TileContent.FOUR), 1
        ));
        set10.addPair(new Tile(TileType.BAMBOO, TileContent.SEVEN));
        set10.addIgnored(new Tile(TileType.BAMBOO, TileContent.TWO));
        set10.addIgnored(new Tile(TileType.BAMBOO, TileContent.THREE));
        set10.addIgnored(new Tile(TileType.BAMBOO, TileContent.FIVE));
        set10.addIgnored(new Tile(TileType.BAMBOO, TileContent.SIX));

        // 123|B 123|B 123|B 456|B 77|B
        CombinationSet set11 = new CombinationSet();
        set11.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.ONE), 1,
                new Tile(TileType.BAMBOO, TileContent.TWO), 1,
                new Tile(TileType.BAMBOO, TileContent.THREE), 1
        ), 3);
        set11.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.FOUR), 1,
                new Tile(TileType.BAMBOO, TileContent.FIVE), 1,
                new Tile(TileType.BAMBOO, TileContent.SIX), 1
        ));
        set11.addPair(new Tile(TileType.BAMBOO, TileContent.SEVEN));

        // 123|B 123|B 123|B 567|B
        CombinationSet set12 = new CombinationSet();
        set12.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.ONE), 1,
                new Tile(TileType.BAMBOO, TileContent.TWO), 1,
                new Tile(TileType.BAMBOO, TileContent.THREE), 1
        ), 3);
        set12.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.FIVE), 1,
                new Tile(TileType.BAMBOO, TileContent.SIX), 1,
                new Tile(TileType.BAMBOO, TileContent.SEVEN), 1
        ));
        set12.addIgnored(new Tile(TileType.BAMBOO, TileContent.FOUR));
        set12.addIgnored(new Tile(TileType.BAMBOO, TileContent.SEVEN));

        // 123|B 123|B 234|B 567|B
        CombinationSet set13 = new CombinationSet();
        set13.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.ONE), 1,
                new Tile(TileType.BAMBOO, TileContent.TWO), 1,
                new Tile(TileType.BAMBOO, TileContent.THREE), 1
        ), 2);
        set13.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.TWO), 1,
                new Tile(TileType.BAMBOO, TileContent.THREE), 1,
                new Tile(TileType.BAMBOO, TileContent.FOUR), 1
        ));
        set13.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.FIVE), 1,
                new Tile(TileType.BAMBOO, TileContent.SIX), 1,
                new Tile(TileType.BAMBOO, TileContent.SEVEN), 1
        ));
        set13.addIgnored(new Tile(TileType.BAMBOO, TileContent.ONE));
        set13.addIgnored(new Tile(TileType.BAMBOO, TileContent.SEVEN));

        // 123|B 123|B 234|B 77|B
        CombinationSet set14 = new CombinationSet();
        set14.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.ONE), 1,
                new Tile(TileType.BAMBOO, TileContent.TWO), 1,
                new Tile(TileType.BAMBOO, TileContent.THREE), 1
        ), 2);
        set14.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.TWO), 1,
                new Tile(TileType.BAMBOO, TileContent.THREE), 1,
                new Tile(TileType.BAMBOO, TileContent.FOUR), 1
        ));
        set14.addPair(new Tile(TileType.BAMBOO, TileContent.SEVEN));
        set14.addIgnored(new Tile(TileType.BAMBOO, TileContent.ONE));
        set14.addIgnored(new Tile(TileType.BAMBOO, TileContent.FIVE));
        set14.addIgnored(new Tile(TileType.BAMBOO, TileContent.SIX));

        // 123|B 123|B 345|B 77|B
        CombinationSet set15 = new CombinationSet();
        set15.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.ONE), 1,
                new Tile(TileType.BAMBOO, TileContent.TWO), 1,
                new Tile(TileType.BAMBOO, TileContent.THREE), 1
        ), 2);
        set15.addCombination(CombinationType.CHOW, Map.of(
                new Tile(TileType.BAMBOO, TileContent.THREE), 1,
                new Tile(TileType.BAMBOO, TileContent.FOUR), 1,
                new Tile(TileType.BAMBOO, TileContent.FIVE), 1
        ));
        set15.addPair(new Tile(TileType.BAMBOO, TileContent.SEVEN));
        set15.addIgnored(new Tile(TileType.BAMBOO, TileContent.ONE));
        set15.addIgnored(new Tile(TileType.BAMBOO, TileContent.TWO));
        set15.addIgnored(new Tile(TileType.BAMBOO, TileContent.SIX));

        // Generate expected hands from adding winning tile
        List<CombinationSet> allPossibleHands = Arrays.asList(
                set1, set2, set3, set4, set5, set6, set7, set8,
                set9, set10, set11, set12, set13, set14, set15
        );

        // Computed hands from the Chow must be in the generated hands
        assertEquals(possibleCombinationsForTile.size(), 6);
        allPossibleHands.containsAll(possibleCombinationsForTile);

        // Compare generated hands with computed hands
        this.assertHands(hand, allPossibleHands);
    }

    private void assertHands(Hand hand, List<CombinationSet> allPossibleHands) {
        List<CombinationSet> res = hand.getPossibleCombinationSets();
        assertEquals(res.size(), allPossibleHands.size());
        assertTrue(allPossibleHands.containsAll(res));
    }
}
