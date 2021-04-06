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

        // The order of insertion assures that the tiles are sorted, so HandInfo will work
        // Generated tiles: [1B,1B,1B,3D,3D,3D,5C,5C,5C,WE,WE,WE,DR,DR]
        Hand hand = new Hand(tiles);

        List<Map<Tile, Integer>> pairs = Collections.singletonList(Map.ofEntries(
                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1)
        ));

        List<Map<Tile, Integer>> pungs = Collections.singletonList(Map.ofEntries(
                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 1),
                Map.entry(new Tile(TileType.CHARACTERS, TileContent.FIVE), 1),
                Map.entry(new Tile(TileType.WIND, TileContent.EAST), 1)
        ));

        List<Map<Tile, Integer>> kongs = Collections.singletonList(new HashMap<>());

        List<Map<Tile, Integer>> chows = Collections.singletonList(new HashMap<>());

        this.assertCombinations(hand, pairs, pungs, kongs, chows);
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

        List<Map<Tile, Integer>> pairs = Collections.singletonList(Map.ofEntries(
                Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1)
        ));

        List<Map<Tile, Integer>> pungs = Collections.singletonList(Map.ofEntries(
                Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                Map.entry(new Tile(TileType.CHARACTERS, TileContent.FIVE), 1)
        ));

        List<Map<Tile, Integer>> kongs = Collections.singletonList(new HashMap<>());

        List<Map<Tile, Integer>> chows = Collections.singletonList(Map.ofEntries(
                Map.entry(new Tile(TileType.DOTS, TileContent.ONE), 1),
                Map.entry(new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1)
        ));

        this.assertCombinations(hand, pairs, pungs, kongs, chows);
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
        // Generated tiles: [1B,1B,1B,9C,9C,9C,1C,2C,3C,4C,5C,6C,DR,DR]
        Hand hand = new Hand(tiles);

        List<Map<Tile, Integer>> pairs = new ArrayList<>();
        List<Map<Tile, Integer>> pungs = new ArrayList<>();
        List<Map<Tile, Integer>> kongs = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            pairs.add(Map.ofEntries(
                    Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1)
            ));

            pungs.add(Map.ofEntries(
                    Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                    Map.entry(new Tile(TileType.CHARACTERS, TileContent.NINE), 1)
            ));

            kongs.add(new HashMap<>());
        }

        List<Map<Tile, Integer>> chows = Arrays.asList(
                Map.ofEntries(
                        Map.entry(new Tile(TileType.CHARACTERS, TileContent.ONE), 1),
                        Map.entry(new Tile(TileType.CHARACTERS, TileContent.FOUR), 1)
                ),
                Map.ofEntries(
                        Map.entry(new Tile(TileType.CHARACTERS, TileContent.TWO), 1)
                ),
                Map.ofEntries(
                        Map.entry(new Tile(TileType.CHARACTERS, TileContent.THREE), 1)
                )
        );

        this.assertCombinations(hand, pairs, pungs, kongs, chows);
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

        List<Map<Tile, Integer>> pairs = new ArrayList<>();
        List<Map<Tile, Integer>> pungs = new ArrayList<>();
        List<Map<Tile, Integer>> kongs = new ArrayList<>();

        List<Map<Tile, Integer>> chows = Arrays.asList(
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.DOTS, TileContent.ONE), 1),
                        Map.entry(new Tile(TileType.DOTS, TileContent.FOUR), 1),
                        Map.entry(new Tile(TileType.DOTS, TileContent.SEVEN), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.DOTS, TileContent.ONE), 1),
                        Map.entry(new Tile(TileType.DOTS, TileContent.FIVE), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.DOTS, TileContent.ONE), 1),
                        Map.entry(new Tile(TileType.DOTS, TileContent.SIX), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.DOTS, TileContent.TWO), 1),
                        Map.entry(new Tile(TileType.DOTS, TileContent.FIVE), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.DOTS, TileContent.TWO), 1),
                        Map.entry(new Tile(TileType.DOTS, TileContent.SIX), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.DOTS, TileContent.TWO), 1),
                        Map.entry(new Tile(TileType.DOTS, TileContent.SEVEN), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 1),
                        Map.entry(new Tile(TileType.DOTS, TileContent.SIX), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 1),
                        Map.entry(new Tile(TileType.DOTS, TileContent.SEVEN), 1)
                ))
        );

        for (int i = 0; i < 8; i++) {
            pairs.add(Map.ofEntries(
                    Map.entry(new Tile(TileType.DRAGON, TileContent.RED), 1)
            ));

            pungs.add(new HashMap<>());

            kongs.add(new HashMap<>());

            chows.get(i).put(new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1);
        }

        this.assertCombinations(hand, pairs, pungs, kongs, chows);
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

        List<Map<Tile, Integer>> pairs = Arrays.asList(
                new HashMap<>(),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.DOTS, TileContent.ONE), 1),
                        Map.entry(new Tile(TileType.DOTS, TileContent.TWO), 1),
                        Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 1)
                ))
        );
        List<Map<Tile, Integer>> pungs = new ArrayList<>();
        List<Map<Tile, Integer>> kongs = new ArrayList<>();

        List<Map<Tile, Integer>> chows = Arrays.asList(
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.DOTS, TileContent.ONE), 2)
                )),
                new HashMap<>()
        );

        for (int i = 0; i < 2; i++) {
            pairs.get(i).put(new Tile(TileType.DRAGON, TileContent.RED), 1);

            pungs.add(Map.ofEntries(
                    Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1)
            ));

            kongs.add(new HashMap<>());

            chows.get(i).put(new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1);
        }

        this.assertCombinations(hand, pairs, pungs, kongs, chows);
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

        List<Map<Tile, Integer>> pairs = Arrays.asList(
                new HashMap<>(),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.DOTS, TileContent.TWO), 1),
                        Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 1)
                ))
        );
        List<Map<Tile, Integer>> pungs = new ArrayList<>();
        List<Map<Tile, Integer>> kongs = new ArrayList<>();

        List<Map<Tile, Integer>> chows = Arrays.asList(
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.DOTS, TileContent.ONE), 1),
                        Map.entry(new Tile(TileType.DOTS, TileContent.TWO), 1)
                )),
                new HashMap<>()
        );

        for (int i = 0; i < 2; i++) {
            pairs.get(i).put(new Tile(TileType.DRAGON, TileContent.RED), 1);

            pungs.add(Map.ofEntries(
                    Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1)
            ));

            kongs.add(new HashMap<>());

            chows.get(i).put(new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1);
        }

        this.assertCombinations(hand, pairs, pungs, kongs, chows);
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

        List<Map<Tile, Integer>> pairs = Arrays.asList(
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 1)
                )),
                new HashMap<>(),
                new HashMap<>(),
                new HashMap<>(),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 2)
                ))
        );

        List<Map<Tile, Integer>> pungs = Arrays.asList(
                new HashMap<>(),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 1)
                )),
                new HashMap<>()
        );

        List<Map<Tile, Integer>> kongs = new ArrayList<>();

        List<Map<Tile, Integer>> chows = Arrays.asList(
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.DOTS, TileContent.ONE), 1),
                        Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.DOTS, TileContent.ONE), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.DOTS, TileContent.TWO), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.DOTS, TileContent.THREE), 1)
                )),
                new HashMap<>()
        );

        for (int i = 0; i < 5; i++) {
            pungs.get(i).put(new Tile(TileType.DRAGON, TileContent.RED), 1);

            kongs.add(new HashMap<>());

            chows.get(i).put(new Tile(TileType.CHARACTERS, TileContent.SEVEN), 1);
        }

        this.assertCombinations(hand, pairs, pungs, kongs, chows);
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

        List<Map<Tile, Integer>> pairs = Arrays.asList(
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.SEVEN), 1)
                )),
                new HashMap<>(),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.THREE), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.SEVEN), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.THREE), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.THREE), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.SEVEN), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.THREE), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.SEVEN), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.THREE), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.SEVEN), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.SEVEN), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.SEVEN), 1)
                )),
                new HashMap<>(),
                new HashMap<>(),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.SEVEN), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.SEVEN), 1)
                ))
        );
        List<Map<Tile, Integer>> pungs = Arrays.asList(
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.THREE), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.THREE), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1)
                )),
                new HashMap<>(),
                new HashMap<>(),
                new HashMap<>(),
                new HashMap<>(),
                new HashMap<>(),
                new HashMap<>(),
                new HashMap<>(),
                new HashMap<>(),
                new HashMap<>(),
                new HashMap<>()
        );

        List<Map<Tile, Integer>> kongs = new ArrayList<>();

        List<Map<Tile, Integer>> chows = Arrays.asList(
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.FOUR), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.FIVE), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.THREE), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.FIVE), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.FOUR), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.FIVE), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.THREE), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.FIVE), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 3),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.FOUR), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 3),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.FIVE), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 2),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.FIVE), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 2),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.TWO), 1)
                )),
                new HashMap<>(Map.ofEntries(
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.ONE), 2),
                        Map.entry(new Tile(TileType.BAMBOO, TileContent.THREE), 1)
                ))
        );

        for (int i = 0; i < 15; i++) {
            kongs.add(new HashMap<>());
        }

        this.assertCombinations(hand, pairs, pungs, kongs, chows);
    }

    private void assertCombinations(
            Hand hand,
            List<Map<Tile, Integer>> allPairs,
            List<Map<Tile, Integer>> allPungs,
            List<Map<Tile, Integer>> allKongs,
            List<Map<Tile, Integer>> allChows) {

        var allPossibleHands = hand.getPossibleHands();

        int handCount = allPossibleHands.size();
        assertEquals(handCount, allPairs.size());
        assertEquals(handCount, allPungs.size());
        assertEquals(handCount, allKongs.size());
        assertEquals(handCount, allChows.size());

        for (int i = 0; i < handCount; i ++) {
            var pairs = allPairs.get(i);
            var pungs = allPungs.get(i);
            var kongs = allKongs.get(i);
            var chows = allChows.get(i);

            boolean allMatch = false;

            for (var possibleHand : allPossibleHands) {

                if (possibleHand.get(CombinationType.PAIR).size() != pairs.size() ||
                    possibleHand.get(CombinationType.PUNG).size() != pungs.size() ||
                    possibleHand.get(CombinationType.KONG).size() != kongs.size() ||
                    possibleHand.get(CombinationType.CHOW).size() != chows.size()
                )
                    continue;

                if (
                        !pairs.entrySet().stream().allMatch(
                                e -> e.getValue().equals(possibleHand.get(CombinationType.PAIR).get(e.getKey()))) ||
                        !pungs.entrySet().stream().allMatch(
                                e -> e.getValue().equals(possibleHand.get(CombinationType.PUNG).get(e.getKey()))) ||
                        !kongs.entrySet().stream().allMatch(
                                e -> e.getValue().equals(possibleHand.get(CombinationType.KONG).get(e.getKey()))) ||
                        !chows.entrySet().stream().allMatch(
                                e -> e.getValue().equals(possibleHand.get(CombinationType.CHOW).get(e.getKey())))
                )
                    continue;

                allMatch = true;
                break;
            }

            assertTrue(allMatch);
        }

    }
}
