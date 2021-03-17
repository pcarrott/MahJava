package MahJavaLib;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.testng.Assert.*;

public class TestMahjongHand {

    private MahjongHand fourCombsPlusPair;
    private MahjongHand thirteenOrphans;
    private MahjongHand sevenPairs;
    private MahjongHand nineGates;
    
    @BeforeMethod
    public void setUp() {
        ArrayList<MahjongTile> fourCombsPlusPair = new ArrayList<>();
        ArrayList<MahjongTile> thirteenOrphans = new ArrayList<>();
        ArrayList<MahjongTile> sevenPairs = new ArrayList<>();
        ArrayList<MahjongTile> nineGates = new ArrayList<>();

        // Populate all the hands with a correct configuration
        // First, 4 Combinations + 1 Pair
        for (int i = 0; i < 4; ++i) {
            // Add 111 222 333 444 of Bamboo
            for (int j = 0; j < 3; ++j) {
                fourCombsPlusPair.add(new MahjongTile(MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.fromValue(i + 1)));
            }
        }
        // Add 55 of Bamboo
        fourCombsPlusPair.add(new MahjongTile(MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.fromValue(5)));
        fourCombsPlusPair.add(new MahjongTile(MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.fromValue(5)));
        
        
        // Next the Seven Pairs
        for (MahjongTile.TileType tp : MahjongTile.TileType.values()) {
            // Add 11 of every type (3 types * 2 = 6)
            if (!tp.isSpecialType()) {
                sevenPairs.add(new MahjongTile(tp, MahjongTile.TileContent.ONE));
                sevenPairs.add(new MahjongTile(tp, MahjongTile.TileContent.ONE));
            } else if (tp == MahjongTile.TileType.WIND) {
                // And add a pair of winds of very direction (4 types * 2 = 8)
                for (MahjongTile.TileContent tc : MahjongTile.TileContent.getDirections()) {
                    sevenPairs.add(new MahjongTile(tp, tc));
                    sevenPairs.add(new MahjongTile(tp, tc));
                }
            }
        }

        // Next the Nine Gates
        // Add 1 Pung each of 1 and 9 of Bamboo
        for (int i = 0; i < 3; i++) {
            nineGates.add(new MahjongTile(MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.ONE));
            nineGates.add(new MahjongTile(MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.NINE));
        }

        // Add the full sequence between 2 and 8 inclusive
        for (int i = 2; i <= 8; ++i) {
            nineGates.add(new MahjongTile(MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.fromValue(i)));
        }
        // Add an extra 2 of Bamboo for the pair
        nineGates.add(new MahjongTile(MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.TWO));


        // And finally, the Thirteen Orphans
        for (MahjongTile.TileType tp : MahjongTile.TileType.values()) {
            if (!tp.isSpecialType()) {
                thirteenOrphans.add(new MahjongTile(tp, MahjongTile.TileContent.ONE));
                thirteenOrphans.add(new MahjongTile(tp, MahjongTile.TileContent.NINE));
            } else if (tp == MahjongTile.TileType.DRAGON) {
                for (MahjongTile.TileContent tc : MahjongTile.TileContent.getColors()) {
                    thirteenOrphans.add(new MahjongTile(tp, tc));
                }
            } else {
                for (MahjongTile.TileContent tc : MahjongTile.TileContent.getDirections()) {
                    thirteenOrphans.add(new MahjongTile(tp, tc));
                }
            }
        }
        // Add a 1 of Bamboo for the pair
        thirteenOrphans.add(new MahjongTile(MahjongTile.TileType.BAMBOO, MahjongTile.TileContent.ONE));

        this.fourCombsPlusPair = new MahjongHand(fourCombsPlusPair);
        this.sevenPairs = new MahjongHand(sevenPairs);
        this.nineGates = new MahjongHand(nineGates);
        this.thirteenOrphans = new MahjongHand(thirteenOrphans);
    }

    @AfterMethod
    public void tearDown() {
    }

    @Test
    public void testIsFourCombinationsPlusPair() {
        assertTrue(MahjongHand.isFourCombinationsPlusPair(this.fourCombsPlusPair));
        assertEquals(Stream.of(this.nineGates,
                this.thirteenOrphans,
                this.sevenPairs,
                this.fourCombsPlusPair)
                .filter(MahjongHand::isFourCombinationsPlusPair).count(), 2);
    }

    @Test
    public void testIsSevenPairs() {
        assertTrue(MahjongHand.isSevenPairs(this.sevenPairs));
        assertEquals(Stream.of(this.nineGates,
                this.thirteenOrphans,
                this.sevenPairs,
                this.fourCombsPlusPair)
                .filter(MahjongHand::isSevenPairs).count(), 1);
    }


    @Test
    public void testIsNineGates() {
        assertTrue(MahjongHand.isNineGates(this.nineGates));
        assertEquals(Stream.of(this.nineGates,
                this.thirteenOrphans,
                this.sevenPairs,
                this.fourCombsPlusPair)
                .filter(MahjongHand::isNineGates).count(), 1);
    }

    @Test
    public void testIsThirteenOrphans() {
        assertTrue(MahjongHand.isThirteenOrphans(this.thirteenOrphans));
        assertEquals(Stream.of(this.nineGates,
                this.thirteenOrphans,
                this.sevenPairs,
                this.fourCombsPlusPair)
                .filter(MahjongHand::isThirteenOrphans).count(), 1);
    }

    @Test
    public void testIsWinningHand() {
        assertTrue(Stream.of(this.nineGates,
                this.thirteenOrphans,
                this.sevenPairs,
                this.fourCombsPlusPair)
                .allMatch(MahjongHand::isWinningHand));
    }

    @Test
    public void testGenerateRandomHand() {
        MahjongHand hand = MahjongHand.generateRandomHand();
        assertEquals(hand.getHandSize(), 14);
    }
}