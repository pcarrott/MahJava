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
    private Hand sevenPairs;
    private Hand hiddenTreasure;
    private Hand littleFourWinds;
    private Hand bigFourWinds;
    private Hand threeGreatScholars;
    private Hand nineGates;
    private Hand thirteenOrphans;
    private Hand allKongs;
    private Hand allHonors;
    private Hand allTerminals;
    private Hand jadeDragon;
    private Hand rubyDragon;
    private Hand pearlDragon;

    private Stream<Hand> allHands;

    @BeforeMethod
    public void setUp() {
        ArrayList<Tile> fourCombsPlusPair = new ArrayList<>();
        ArrayList<Tile> sevenPairs = new ArrayList<>();
        ArrayList<Tile> hiddenTreasure = new ArrayList<>();
        ArrayList<Tile> littleFourWinds = new ArrayList<>();
        ArrayList<Tile> bigFourWinds = new ArrayList<>();
        ArrayList<Tile> threeGreatScholars = new ArrayList<>();
        ArrayList<Tile> nineGates = new ArrayList<>();
        ArrayList<Tile> thirteenOrphans = new ArrayList<>();
        ArrayList<Tile> allKongs = new ArrayList<>();
        ArrayList<Tile> allHonors = new ArrayList<>();
        ArrayList<Tile> allTerminals = new ArrayList<>();
        ArrayList<Tile> jadeDragon = new ArrayList<>();
        ArrayList<Tile> rubyDragon = new ArrayList<>();
        ArrayList<Tile> pearlDragon = new ArrayList<>();

        /*
         * Populate all the hands with a correct configuration
         */

        // 4 Combinations + 1 Pair
        for (int i = 0; i < 4; ++i) {
            // Add 111 222 333 444 of Bamboo
            for (int j = 0; j < 3; ++j) {
                fourCombsPlusPair.add(new Tile(TileType.BAMBOO, TileContent.fromValue(i + 1)));
            }
        }
        // Add 55 of Bamboo
        fourCombsPlusPair.add(new Tile(TileType.BAMBOO, TileContent.fromValue(5)));
        fourCombsPlusPair.add(new Tile(TileType.BAMBOO, TileContent.fromValue(5)));


        // Seven Pairs
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


        // Hidden Treasure
        for (TileType tp : TileType.values()) {
            // Add 999 of every type (3 types * 3 = 9)
            if (!tp.isSpecialType()) {
                hiddenTreasure.add(new Tile(tp, TileContent.NINE));
                hiddenTreasure.add(new Tile(tp, TileContent.NINE));
                hiddenTreasure.add(new Tile(tp, TileContent.NINE));
            }
        }
        // Add RRR (9 + 3 = 12)
        for (int i = 0; i < 3; i++)
            hiddenTreasure.add(new Tile(TileType.DRAGON, TileContent.RED));
        // Add EE (12 + 2 = 14)
        for (int i = 0; i < 2; i++)
            hiddenTreasure.add(new Tile(TileType.WIND, TileContent.EAST));


        // Little Four Winds
        for (int i = 0; i < 3; i ++) {
            // Add 3 of 3 winds (3 winds * 3 = 9)
            littleFourWinds.add(new Tile(TileType.WIND, TileContent.EAST));
            littleFourWinds.add(new Tile(TileType.WIND, TileContent.SOUTH));
            littleFourWinds.add(new Tile(TileType.WIND, TileContent.WEST));

            // Add a random 555 of Bamboo (9 + 3 = 12)
            littleFourWinds.add(new Tile(TileType.BAMBOO, TileContent.FIVE));
        }
        // Add a pair of the last wind, NN (12 + 2 = 14)
        for (int i = 0; i < 2; i++)
            littleFourWinds.add(new Tile(TileType.WIND, TileContent.NORTH));


        // Big Four Winds
        for (TileContent tc : TileContent.getDirections()) {
            // Add 3 of every wind (4 winds * 3 = 12)
            bigFourWinds.add(new Tile(TileType.WIND, tc));
            bigFourWinds.add(new Tile(TileType.WIND, tc));
            bigFourWinds.add(new Tile(TileType.WIND, tc));
        }
        // Add a 22 of Dots (12 + 2 = 14)
        for (int i = 0; i < 2; i++)
            bigFourWinds.add(new Tile(TileType.DOTS, TileContent.TWO));


        // Three Great Scholars
        for (TileContent tc : TileContent.getColors()) {
            // Add each dragon 3 times (3 dragons * 3 = 9)
            threeGreatScholars.add(new Tile(TileType.DRAGON, tc));
            threeGreatScholars.add(new Tile(TileType.DRAGON, tc));
            threeGreatScholars.add(new Tile(TileType.DRAGON, tc));

            // Add a 6 Characters for each of the 3 dragons (9 + 3 = 12)
            threeGreatScholars.add(new Tile(TileType.CHARACTERS, TileContent.SIX));
        }
        // Add a 22 of Dots (12 + 2 = 14)
        for (int i = 0; i < 2; i++)
            threeGreatScholars.add(new Tile(TileType.DOTS, TileContent.TWO));


        // Nine Gates
        for (int i = 0; i < 2; i++) {
            // Add a 1 and 9 two times (2 tiles * 2 = 4)
            nineGates.add(new Tile(TileType.BAMBOO, TileContent.ONE));
            nineGates.add(new Tile(TileType.BAMBOO, TileContent.NINE));
        }
        // Add each of the remaining number from 1 to 9 (4 + 9 = 13)
        for (TileContent tc : TileContent.getNumbers())
            nineGates.add(new Tile(TileType.BAMBOO, tc));
        // Add an extra tile to complete the hand (13 + 1 = 14)
        nineGates.add(new Tile(TileType.BAMBOO, TileContent.SIX));


        // The Thirteen Orphans
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


        // All Kongs TODO


        // All Honors
        for (int i = 0; i < 3; i++) {
            // Add 3 of 4 Honor tiles, RRR, GGG, EEE, WWW (3 tiles * 4 = 12)
            allHonors.add(new Tile(TileType.DRAGON, TileContent.RED));
            allHonors.add(new Tile(TileType.DRAGON, TileContent.GREEN));
            allHonors.add(new Tile(TileType.WIND, TileContent.EAST));
            allHonors.add(new Tile(TileType.WIND, TileContent.WEST));
        }
        for (int i = 0; i < 2; i++)
            // Add SS (12 + 2 = 14)
            allHonors.add(new Tile(TileType.WIND, TileContent.SOUTH));


        // All Terminals
        for (int i = 0; i < 3; i++) {
            // Add 3 of 4 Terminal tiles (1/9), 111B, 999B, 999D, 111C (3 tiles * 4 = 12)
            allTerminals.add(new Tile(TileType.BAMBOO, TileContent.ONE));
            allTerminals.add(new Tile(TileType.BAMBOO, TileContent.NINE));
            allTerminals.add(new Tile(TileType.DOTS, TileContent.NINE));
            allTerminals.add(new Tile(TileType.CHARACTERS, TileContent.ONE));
        }
        for (int i = 0; i < 2; i++)
            // Add 99C (12 + 2 = 14)
            allTerminals.add(new Tile(TileType.CHARACTERS, TileContent.NINE));


        // Jade Dragon
        for (int i = 0; i < 3; i++) {
            // Add 3 Green Dragons
            jadeDragon.add(new Tile(TileType.DRAGON, TileContent.GREEN));

            // Add 111, 333, 555 of Bamboo (3 tiles * 3 + 3 = 9 +3 = 12)
            jadeDragon.add(new Tile(TileType.BAMBOO, TileContent.ONE));
            jadeDragon.add(new Tile(TileType.BAMBOO, TileContent.THREE));
            jadeDragon.add(new Tile(TileType.BAMBOO, TileContent.FIVE));
        }
        for (int i = 0; i < 2; i++)
            // Add 77B (12 + 2 = 14)
            jadeDragon.add(new Tile(TileType.BAMBOO, TileContent.SEVEN));


        // Ruby Dragon
        for (int i = 0; i < 3; i++) {
            // Add 3 Red Dragons
            rubyDragon.add(new Tile(TileType.DRAGON, TileContent.RED));

            // Add 111, 333, 555 of Characters (3 tiles * 3 + 3 = 9 +3 = 12)
            rubyDragon.add(new Tile(TileType.CHARACTERS, TileContent.ONE));
            rubyDragon.add(new Tile(TileType.CHARACTERS, TileContent.THREE));
            rubyDragon.add(new Tile(TileType.CHARACTERS, TileContent.FIVE));
        }
        for (int i = 0; i < 2; i++)
            // Add 77C (12 + 2 = 14)
            rubyDragon.add(new Tile(TileType.CHARACTERS, TileContent.SEVEN));


        // Pearl Dragon
        for (int i = 0; i < 3; i++) {
            // Add 3 White Dragons
            pearlDragon.add(new Tile(TileType.DRAGON, TileContent.WHITE));

            // Add 111, 333, 555 of Dots (3 tiles * 3 + 3 = 9 +3 = 12)
            pearlDragon.add(new Tile(TileType.DOTS, TileContent.ONE));
            pearlDragon.add(new Tile(TileType.DOTS, TileContent.THREE));
            pearlDragon.add(new Tile(TileType.DOTS, TileContent.FIVE));
        }
        for (int i = 0; i < 2; i++)
            // Add 77D (12 + 2 = 14)
            pearlDragon.add(new Tile(TileType.DOTS, TileContent.SEVEN));


        this.fourCombsPlusPair = new Hand(fourCombsPlusPair);
        this.sevenPairs = new Hand(sevenPairs);
        this.hiddenTreasure = new Hand(hiddenTreasure);
        this.littleFourWinds = new Hand(littleFourWinds);
        this.bigFourWinds = new Hand(bigFourWinds);
        this.threeGreatScholars = new Hand(threeGreatScholars);
        this.nineGates = new Hand(nineGates);
        this.thirteenOrphans = new Hand(thirteenOrphans);
        // this.allKongs = new Hand(allKongs);
        this.allHonors = new Hand(allHonors);
        this.allTerminals = new Hand(allTerminals);
        this.jadeDragon = new Hand(jadeDragon);
        this.rubyDragon = new Hand(rubyDragon);
        this.pearlDragon = new Hand(pearlDragon);

        this.allHands = Stream.of(
                this.pearlDragon,
                this.rubyDragon,
                this.jadeDragon,
                this.allTerminals,
                this.allHonors,
                //this.allKongs,
                this.thirteenOrphans,
                this.nineGates,
                this.threeGreatScholars,
                this.bigFourWinds,
                this.littleFourWinds,
                this.hiddenTreasure,
                this.sevenPairs,
                this.fourCombsPlusPair);
    }

    @AfterMethod
    public void tearDown() {
    }

    @Test
    public void testIsFourCombinationsPlusPair() {
        assertTrue(this.fourCombsPlusPair.isFourCombinationsPlusPair());
        // Change this to 12 for allKongs
        assertEquals(this.allHands.filter(Hand::isFourCombinationsPlusPair).count(), 11);
    }

    @Test
    public void testIsSevenPairs() {
        assertTrue(this.sevenPairs.isSevenPairs());
        assertEquals(this.allHands.filter(Hand::isSevenPairs).count(), 1);
    }

    @Test
    public void testIsHiddenTreasure() {
        assertTrue(this.hiddenTreasure.isHiddenTreasure());
    }

    @Test
    public void testIsLittleFourWinds() {
        assertTrue(this.littleFourWinds.isLittleFourWinds());
        assertEquals(this.allHands.filter(Hand::isLittleFourWinds).count(), 1);
    }

    @Test
    public void testIsBigFourWinds() {
        assertTrue(this.bigFourWinds.isBigFourWinds());
        assertEquals(this.allHands.filter(Hand::isBigFourWinds).count(), 1);
    }

    @Test
    public void testIsThreeGreatScholars() {
        assertTrue(this.threeGreatScholars.isThreeGreatScholars());
        assertEquals(this.allHands.filter(Hand::isThreeGreatScholars).count(), 1);

        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 3; i ++) {
            // Add 3 of 3 winds (3 winds * 3 = 9)
            tiles.add(new Tile(TileType.DRAGON, TileContent.GREEN));
            tiles.add(new Tile(TileType.DRAGON, TileContent.WHITE));

            // Add a random 555 of Bamboo (9 + 3 = 12)
            tiles.add(new Tile(TileType.BAMBOO, TileContent.FIVE));
            tiles.add(new Tile(TileType.BAMBOO, TileContent.ONE));
        }
        // Add a pair of the last wind, NN (12 + 2 = 14)
        for (int i = 0; i < 2; i++)
            tiles.add(new Tile(TileType.DRAGON, TileContent.RED));
        Hand notThreeGreatScholars = new Hand(tiles);

        assertFalse(notThreeGreatScholars.isThreeGreatScholars());
    }

    @Test
    public void testIsNineGates() {
        assertTrue(this.nineGates.isNineGates());
        assertEquals(this.allHands.filter(Hand::isNineGates).count(), 1);
    }

    @Test
    public void testIsThirteenOrphans() {
        assertTrue(this.thirteenOrphans.isThirteenOrphans());
        assertEquals(this.allHands.filter(Hand::isThirteenOrphans).count(), 1);
    }

    @Test
    public void testIsAllKongs() {
        // assertTrue(this.allKongs.isAllKongs());
    }

    @Test
    public void testIsAllHonors() {
        assertTrue(this.allHonors.isAllHonors());
        assertEquals(this.allHands.filter(Hand::isAllHonors).count(), 1);
    }

    @Test
    public void testIsAllTerminals() {
        assertTrue(this.allTerminals.isAllTerminals());
        assertEquals(this.allHands.filter(Hand::isAllTerminals).count(), 1);
    }

    @Test
    public void testIsJadeDragon() {
        assertTrue(this.jadeDragon.isJadeDragon());
        assertEquals(this.allHands.filter(Hand::isJadeDragon).count(), 1);

        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            // Add 111, 222, 444, 555 of Dots (3 tiles * 4 = 12)
            tiles.add(new Tile(TileType.BAMBOO, TileContent.ONE));
            tiles.add(new Tile(TileType.BAMBOO, TileContent.TWO));
            tiles.add(new Tile(TileType.BAMBOO, TileContent.FOUR));
            tiles.add(new Tile(TileType.BAMBOO, TileContent.FIVE));
        }
        for (int i = 0; i < 2; i++)
            // Add 77D (12 + 2 = 14)
            tiles.add(new Tile(TileType.BAMBOO, TileContent.SEVEN));
        Hand notJadeDragon = new Hand(tiles);

        assertFalse(notJadeDragon.isJadeDragon());
    }

    @Test
    public void testIsRubyDragon() {
        assertTrue(this.rubyDragon.isRubyDragon());
        assertEquals(this.allHands.filter(Hand::isRubyDragon).count(), 1);

        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            // Add 111, 222, 444, 555 of Dots (3 tiles * 4 = 12)
            tiles.add(new Tile(TileType.CHARACTERS, TileContent.ONE));
            tiles.add(new Tile(TileType.CHARACTERS, TileContent.TWO));
            tiles.add(new Tile(TileType.CHARACTERS, TileContent.FOUR));
            tiles.add(new Tile(TileType.CHARACTERS, TileContent.FIVE));
        }
        for (int i = 0; i < 2; i++)
            // Add 77D (12 + 2 = 14)
            tiles.add(new Tile(TileType.CHARACTERS, TileContent.SEVEN));
        Hand notRubyDragon = new Hand(tiles);

        assertFalse(notRubyDragon.isRubyDragon());
    }

    @Test
    public void testIsPearlDragon() {
        assertTrue(this.pearlDragon.isPearlDragon());
        assertEquals(this.allHands.filter(Hand::isPearlDragon).count(), 1);

        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            // Add 111, 222, 444, 555 of Dots (3 tiles * 4 = 12)
            tiles.add(new Tile(TileType.DOTS, TileContent.ONE));
            tiles.add(new Tile(TileType.DOTS, TileContent.TWO));
            tiles.add(new Tile(TileType.DOTS, TileContent.FOUR));
            tiles.add(new Tile(TileType.DOTS, TileContent.FIVE));
        }
        for (int i = 0; i < 2; i++)
            // Add 77D (12 + 2 = 14)
            tiles.add(new Tile(TileType.DOTS, TileContent.SEVEN));
        Hand notPearlDragon = new Hand(tiles);

        assertFalse(notPearlDragon.isPearlDragon());
    }

    @Test
    public void testIsWinningHand() {
        assertTrue(this.allHands.allMatch(Hand::isWinningHand));
    }

    @Test
    public void testGenerateRandomHand() {
        Hand hand = Hand.generateRandomHand();
        assertEquals(hand.getHandSize(), 14);
        assertTrue(hand.getHand().values().stream().noneMatch(v -> v > 4));
    }
}