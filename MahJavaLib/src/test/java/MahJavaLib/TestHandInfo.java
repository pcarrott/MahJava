package MahJavaLib;

import MahJavaLib.hand.Hand;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

import java.util.*;

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

        // Generated tiles: [1B,1B,1B,3D,3D,3D,5C,5C,5C,EW,EW,EW,RD,RD]
        Hand hand = new Hand(tiles);

        List<Map<CombinationType, Map<Tile, Integer>>> allPossibleHands = Collections.singletonList(
                // 111|B 333|D 555|C EEE|W RR|D
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1)
                        )),
                        Map.entry(CombinationType.PUNG, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 1),
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.FIVE), 1),
                                Map.entry(new Tile(TileType.WIND, TileContent.EAST), 1))),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, new HashMap<>())
                ))
        );

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

        for (int i = 0; i < 2; i++) {
            tiles.add(new Tile(TileType.DRAGON, TileContent.RED));
        }

        // Generated tiles: [1B,1B,1B,1D,2D,3D,5C,5C,5C,7C,8C,9C,RD,RD]
        Hand hand = new Hand(tiles);

        List<Map<CombinationType, Map<Tile, Integer>>> allPossibleHands = Collections.singletonList(
                // 111|B 123|D 555|C 789|C RR|D
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1)
                        )),
                        Map.entry(CombinationType.PUNG, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.FIVE), 1)
                        )),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, new HashMap<>(Map.ofEntries(
                                Map.entry(new Tile(TileType.DOTS, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1)
                        )))
                ))
        );

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

        for (int i = 0; i < 2; i++) {
            tiles.add(new Tile(TileType.DRAGON, TileContent.RED));
        }

        // Generated tiles: [1B,1B,1B,9C,9C,9C,1C,2C,3C,4C,5C,6C,RD,RD]
        Hand hand = new Hand(tiles);

        List<Map<CombinationType, Map<Tile, Integer>>> allPossibleHands = Arrays.asList(
                // 111|B 999|C 123|C 456|C RR|D
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1)
                        )),
                        Map.entry(CombinationType.PUNG, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.NINE), 1)
                        )),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.FOUR), 1)
                        ))
                )),
                // 111|B 999|C 234|C RR|D
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1)
                        )),
                        Map.entry(CombinationType.PUNG, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.NINE), 1)
                        )),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                        Map.entry(new Tile(TileType.CHARACTERS, TileContent.TWO), 1)
                                ))
                )),
                // 111|B 999|C 345|C RR|D
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1)
                        )),
                        Map.entry(CombinationType.PUNG, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.NINE), 1)
                        )),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.THREE), 1)
                        ))
                ))
        );

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

        for (int i = 0; i < 2; i++) {
            tiles.add(new Tile(TileType.DRAGON, TileContent.RED));
        }

        // Generated tiles: [1D,2D,3D,4D,5D,6D,7D,8D,9D,7C,8C,9C,RD,RD]
        Hand hand = new Hand(tiles);

        List<Map<CombinationType, Map<Tile, Integer>>> allPossibleHands = Arrays.asList(
                // 123|D 456|D 789|D 789|C RR|D
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1)
                        )),
                        Map.entry(CombinationType.PUNG, new HashMap<>()),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.DOTS, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.FOUR), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.SEVEN), 1),
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1)
                        ))
                )),
                // 123|D 567|D 789|C RR|D
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1)
                        )),
                        Map.entry(CombinationType.PUNG, new HashMap<>()),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.DOTS, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.FIVE), 1),
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1)
                        ))
                )),
                // 123|D 678|D 789|C RR|D
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1)
                        )),
                        Map.entry(CombinationType.PUNG, new HashMap<>()),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.DOTS, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.SIX), 1),
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1)
                        ))
                )),
                // 234|D 567|D 789|C RR|D
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1)
                        )),
                        Map.entry(CombinationType.PUNG, new HashMap<>()),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.DOTS, TileContent.TWO), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.FIVE), 1),
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1)
                        ))
                )),
                // 234|D 678|D 789|C RR|D
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1)
                        )),
                        Map.entry(CombinationType.PUNG, new HashMap<>()),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.DOTS, TileContent.TWO), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.SIX), 1),
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1)
                        ))
                )),
                // 234|D 789|D 789|C RR|D
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1)
                        )),
                        Map.entry(CombinationType.PUNG, new HashMap<>()),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.DOTS, TileContent.TWO), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.SEVEN), 1),
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1)
                        ))
                )),
                // 345|D 678|D 789|C RR|D
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1)
                        )),
                        Map.entry(CombinationType.PUNG, new HashMap<>()),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.SIX), 1),
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1)
                        ))
                )),
                // 345|D 789|D 789|C RR|D
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1)
                        )),
                        Map.entry(CombinationType.PUNG, new HashMap<>()),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.SEVEN), 1),
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1)
                        ))
                ))
        );

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

        for (int i = 0; i < 2; i++) {
            tiles.add(new Tile(TileType.DRAGON, TileContent.RED));
        }

        // Generated tiles: [1B,1B,1B,1D,1D,2D,2D,3D,3D,7C,8C,9C,RD,RD]
        Hand hand = new Hand(tiles);

        List<Map<CombinationType, Map<Tile, Integer>>> allPossibleHands = Arrays.asList(
                // 111|B 11|D 22|D 33|D 789|C RR|D
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.TWO), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 1)
                        )),
                        Map.entry(CombinationType.PUNG, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1)
                        )),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1)
                        ))
                )),
                // 111|B 123|D 123|D 789|C RR|D
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1)
                        )),
                        Map.entry(CombinationType.PUNG, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1)
                        )),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.ONE), 2)
                        ))
                ))
        );

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

        for (int i = 0; i < 2; i++) {
            tiles.add(new Tile(TileType.DRAGON, TileContent.RED));
        }

        // Generated tiles: [1B,1B,1B,1D,2D,2D,3D,3D,4D,7C,8C,9C,RD,RD]
        Hand hand = new Hand(tiles);

        List<Map<CombinationType, Map<Tile, Integer>>> allPossibleHands = Arrays.asList(
                // 111|B 22|D 33|D 789|C RR|D
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.TWO), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 1)
                        )),
                        Map.entry(CombinationType.PUNG, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1)
                        )),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1)
                        ))
                )),
                // 111|B 123|D 234|D 789|C RR|D
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1)
                        )),
                        Map.entry(CombinationType.PUNG, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1)
                        )),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.TWO), 1)
                        ))
                ))
        );

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

        for (int i = 0; i < 3; i++) {
            tiles.add(new Tile(TileType.DRAGON, TileContent.RED));
        }

        // Generated tiles: [1D,2D,3D,3D,3D,3D,4D,5D,7C,8C,9C,RD,RD,RD]
        Hand hand = new Hand(tiles);

        List<Map<CombinationType, Map<Tile, Integer>>> allPossibleHands = Arrays.asList(
                // 123|D 33|D 345|D 789|C RRR|D
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 1)
                        )),
                        Map.entry(CombinationType.PUNG, Map.ofEntries(
                                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1)
                        )),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 1)
                        ))
                )),
                // 123|D 333|D 789|C RRR|D
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, new HashMap<>()),
                        Map.entry(CombinationType.PUNG, Map.ofEntries(
                                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 1)
                        )),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.ONE), 1)
                        ))
                )),
                // 234|D 333|D 789|C RRR|D
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, new HashMap<>()),
                        Map.entry(CombinationType.PUNG, Map.ofEntries(
                                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 1)
                        )),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.TWO), 1)
                        ))
                )),
                // 333|D 345|D 789|C RRR|D
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, new HashMap<>()),
                        Map.entry(CombinationType.PUNG, Map.ofEntries(
                                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 1)
                        )),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1),
                                Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 1)
                        ))
                )),
                // 33|D 33|D 789|C RRR|D
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 2)
                        )),
                        Map.entry(CombinationType.PUNG, Map.ofEntries(
                                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1)
                        )),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1)
                        ))
                ))
        );

        this.assertHands(hand, allPossibleHands);
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

        // Generated tiles: [1B,1B,1B,2B,2B,2B,3B,3B,3B,4B,5B,6B,7B,7B]
        Hand hand = new Hand(tiles);

        List<Map<CombinationType, Map<Tile, Integer>>> allPossibleHands = Arrays.asList(
                // 111|B 222|B 333|B 456|B 77|B
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.SEVEN), 1)
                        )),
                        Map.entry(CombinationType.PUNG, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.THREE), 1)
                        )),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.FOUR), 1)
                        ))
                )),
                // 111|B 222|B 333|B 567|B
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, new HashMap<>()),
                        Map.entry(CombinationType.PUNG, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.THREE), 1)
                        )),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.FIVE), 1)
                        ))
                )),
                // 111|B 222|B 33|B 345|B 77|B
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.THREE), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.SEVEN), 1)
                        )),
                        Map.entry(CombinationType.PUNG, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1)
                        )),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.THREE), 1)
                        ))
                )),
                // 111|B 22|B 33|B 234|B 567|B
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.THREE), 1)
                        )),
                        Map.entry(CombinationType.PUNG, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1)
                        )),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.FIVE), 1)
                        ))
                )),
                // 111|B 22|B 33|B 234|B 77|B
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.THREE), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.SEVEN), 1)
                        )),
                        Map.entry(CombinationType.PUNG, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1)
                        )),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1)
                        ))
                )),
                // 11|B 22|B 33|B 123|B 456|B 77|B
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.THREE), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.SEVEN), 1)
                        )),
                        Map.entry(CombinationType.PUNG, new HashMap<>()),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.FOUR), 1)
                        ))
                )),
                // 11|B 22|B 33|B 123|B 567|B
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.THREE), 1)
                        )),
                        Map.entry(CombinationType.PUNG, new HashMap<>()),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.FIVE), 1)
                        ))
                )),
                // 11|B 22|B 123|B 345|B 77|B
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.SEVEN), 1)
                        )),
                        Map.entry(CombinationType.PUNG, new HashMap<>()),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.THREE), 1)
                        ))
                )),
                // 11|B 123|B 234|B 567|B
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1)
                        )),
                        Map.entry(CombinationType.PUNG, new HashMap<>()),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.FIVE), 1)
                        ))
                )),
                // 11|B 123|B 234|B 77|B
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.SEVEN), 1)
                        )),
                        Map.entry(CombinationType.PUNG, new HashMap<>()),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1)
                        ))
                )),
                // 123|B 123|B 123|B 456|B 77|B
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.SEVEN), 1)
                        )),
                        Map.entry(CombinationType.PUNG, new HashMap<>()),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 3),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.FOUR), 1)
                        ))
                )),
                // 123|B 123|B 123|B 567|B
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, new HashMap<>()),
                        Map.entry(CombinationType.PUNG, new HashMap<>()),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 3),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.FIVE), 1)
                        ))
                )),
                // 123|B 123|B 234|B 567|B
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, new HashMap<>()),
                        Map.entry(CombinationType.PUNG, new HashMap<>()),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 2),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.FIVE), 1)
                        ))
                )),
                // 123|B 123|B 234|B 77|B
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.SEVEN), 1)
                        )),
                        Map.entry(CombinationType.PUNG, new HashMap<>()),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 2),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1)
                        ))
                )),
                // 123|B 123|B 345|B 77|B
                new HashMap<>(Map.ofEntries(
                        Map.entry(CombinationType.PAIR, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.SEVEN), 1)
                        )),
                        Map.entry(CombinationType.PUNG, new HashMap<>()),
                        Map.entry(CombinationType.KONG, new HashMap<>()),
                        Map.entry(CombinationType.CHOW, Map.ofEntries(
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 2),
                                Map.entry(new Tile(TileType.BAMBOO, TileContent.THREE), 1)
                        ))
                ))
        );

        this.assertHands(hand, allPossibleHands);
    }

    private void assertHands(Hand hand, List<Map<CombinationType, Map<Tile, Integer>>> allPossibleHands) {
        var res = hand.getPossibleHands();

        int handCount = res.size();
        assertEquals(handCount, allPossibleHands.size());

        assertTrue(allPossibleHands.stream().allMatch(
                possibleHand -> res.stream().anyMatch(
                     resHand -> possibleHand.get(CombinationType.PAIR).size() == resHand.get(CombinationType.PAIR).size() &&
                                possibleHand.get(CombinationType.PUNG).size() == resHand.get(CombinationType.PUNG).size() &&
                                possibleHand.get(CombinationType.KONG).size() == resHand.get(CombinationType.KONG).size() &&
                                possibleHand.get(CombinationType.CHOW).size() == resHand.get(CombinationType.CHOW).size() &&
                                possibleHand.get(CombinationType.PAIR).entrySet().stream().allMatch(
                                        e -> e.getValue().equals(resHand.get(CombinationType.PAIR).get(e.getKey()))) &&
                                possibleHand.get(CombinationType.PUNG).entrySet().stream().allMatch(
                                        e -> e.getValue().equals(resHand.get(CombinationType.PUNG).get(e.getKey()))) &&
                                possibleHand.get(CombinationType.KONG).entrySet().stream().allMatch(
                                        e -> e.getValue().equals(resHand.get(CombinationType.KONG).get(e.getKey()))) &&
                                possibleHand.get(CombinationType.CHOW).entrySet().stream().allMatch(
                                        e -> e.getValue().equals(resHand.get(CombinationType.CHOW).get(e.getKey())))
                )
        ));
    }
}
