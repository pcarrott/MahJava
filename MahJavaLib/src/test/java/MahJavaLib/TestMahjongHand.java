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
//        this.nineGates = new MahjongHand(nineGates);
        this.thirteenOrphans = new MahjongHand(thirteenOrphans);
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
                .filter(MahjongHand::isFourCombinationsPlusPair).count(), 1);
    }

    @Test
    public void testIsSevenPairs() {
        assertTrue(this.sevenPairs.isSevenPairs());
        assertEquals(Stream.of(this.thirteenOrphans,
                this.sevenPairs,
                this.fourCombsPlusPair)
                .filter(MahjongHand::isSevenPairs).count(), 1);
    }


    @Test
    public void testIsThirteenOrphans() {
        assertTrue(this.thirteenOrphans.isThirteenOrphans());
        assertEquals(Stream.of(this.thirteenOrphans,
                this.sevenPairs,
                this.fourCombsPlusPair)
                .filter(MahjongHand::isThirteenOrphans).count(), 1);
    }

    @Test
    public void testIsWinningHand() {
        assertTrue(Stream.of(this.thirteenOrphans,
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