package MahJavaLib;

import MahJavaLib.hand.Hand;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.testng.Assert.*;

import MahJavaLib.Tile.*;

public class TestHand {

    private Hand fourCombsPlusPair;
    private Hand thirteenOrphans;
    private Hand sevenPairs;
    private Hand nineGates;

    @BeforeMethod
    public void setUp() {
        ArrayList<Tile> fourCombsPlusPair = new ArrayList<>();
        ArrayList<Tile> thirteenOrphans = new ArrayList<>();
        ArrayList<Tile> sevenPairs = new ArrayList<>();

        // Populate all the hands with a correct configuration
        // First, 4 Combinations + 1 Pair
        for (int i = 0; i < 4; ++i) {
            // Add 111 222 333 444 of Bamboo
            for (int j = 0; j < 3; ++j) {
                fourCombsPlusPair.add(new Tile(TileType.BAMBOO, TileContent.fromValue(i + 1)));
            }
        }
        // Add 55 of Bamboo
        fourCombsPlusPair.add(new Tile(TileType.BAMBOO, TileContent.fromValue(5)));
        fourCombsPlusPair.add(new Tile(TileType.BAMBOO, TileContent.fromValue(5)));


        // Next the Seven Pairs
        for (TileType tp : TileType.values()) {
            // Add 11 of every type (3 types * 2 = 6)
            if (!tp.isSpecialType()) {
                sevenPairs.add(new Tile(tp, TileContent.ONE));
                sevenPairs.add(new Tile(tp, TileContent.ONE));
            } else if (tp == TileType.WIND) {
                // And add a pair of winds of very direction (4 types * 2 = 8)
                for (TileContent tc : TileContent.getDirections()) {
                    sevenPairs.add(new Tile(tp, tc));
                    sevenPairs.add(new Tile(tp, tc));
                }
            }
        }

        // And finally, the Thirteen Orphans
        for (TileType tp : TileType.values()) {
            if (!tp.isSpecialType()) {
                thirteenOrphans.add(new Tile(tp, TileContent.ONE));
                thirteenOrphans.add(new Tile(tp, TileContent.NINE));
            } else if (tp == TileType.DRAGON) {
                for (TileContent tc : TileContent.getColors()) {
                    thirteenOrphans.add(new Tile(tp, tc));
                }
            } else {
                for (TileContent tc : TileContent.getDirections()) {
                    thirteenOrphans.add(new Tile(tp, tc));
                }
            }
        }
        // Add a 1 of Bamboo for the pair
        thirteenOrphans.add(new Tile(TileType.BAMBOO, TileContent.ONE));

        this.fourCombsPlusPair = new Hand(fourCombsPlusPair);
        this.sevenPairs = new Hand(sevenPairs);
//        this.nineGates = new Hand(nineGates);
        this.thirteenOrphans = new Hand(thirteenOrphans);
    }

    @AfterMethod
    public void tearDown() {
    }

    @Test
    public void testIsFourCombinationsPlusPair() {
        assertTrue(this.fourCombsPlusPair.isFourCombinationsPlusPair());
        assertEquals(Stream.of(this.thirteenOrphans,
                this.sevenPairs,
                this.fourCombsPlusPair)
                .filter(Hand::isFourCombinationsPlusPair).count(), 1);
    }

    @Test
    public void testIsSevenPairs() {
        assertTrue(this.sevenPairs.isSevenPairs());
        assertEquals(Stream.of(this.thirteenOrphans,
                this.sevenPairs,
                this.fourCombsPlusPair)
                .filter(Hand::isSevenPairs).count(), 1);
    }


    @Test
    public void testIsThirteenOrphans() {
        assertTrue(this.thirteenOrphans.isThirteenOrphans());
        assertEquals(Stream.of(this.thirteenOrphans,
                this.sevenPairs,
                this.fourCombsPlusPair)
                .filter(Hand::isThirteenOrphans).count(), 1);
    }

    @Test
    public void testIsWinningHand() {
        assertTrue(Stream.of(this.thirteenOrphans,
                this.sevenPairs,
                this.fourCombsPlusPair)
                .allMatch(Hand::isWinningHand));
    }

    @Test
    public void testGenerateRandomHand() {
        Hand hand = Hand.generateRandomHand();
        assertEquals(hand.getHandSize(), 14);
    }
}