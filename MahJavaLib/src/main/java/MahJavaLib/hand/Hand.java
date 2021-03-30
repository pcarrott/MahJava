package MahJavaLib.hand;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import MahJavaLib.Player;
import MahJavaLib.Player.CombinationType;
import MahJavaLib.Tile;
import MahJavaLib.Tile.*;

// The MahjongHand structure allows us to record all the information about a specific hand
// Due to the nature of Mahjong, all MahjongHand objects should be created at the start of each
// round (with a 14-tile starting hand), and then incrementally updated.
public class Hand {

    public Hand(ArrayList<Tile> startingHand) throws IllegalArgumentException {
        if (startingHand.size() != 14) {
            throw new IllegalArgumentException("MahjongHand: Hand must contain 14 tiles, contains: " + startingHand.size());
        } else {
            for (Tile tile : startingHand) {
                this.addTile(tile);
            }
            this._info = new HandInfo(this);
        }
    }

    /*
     * Any 4 sets (Pungs/Kongs/Chows) + any Pair.
     * Scoring: Components
     */
    public Boolean isFourCombinationsPlusPair() {
        for (Map<Player.CombinationType, Map<Tile, Integer>> hand : this._info.getAllPossibleHands()) {
            int pairs = hand.get(CombinationType.PAIR).values().stream().reduce(0, Integer::sum);
            int pungs = hand.get(CombinationType.PUNG).values().stream().reduce(0, Integer::sum);
            int kongs = hand.get(CombinationType.KONG).values().stream().reduce(0, Integer::sum);
            int chows = hand.get(CombinationType.CHOW).values().stream().reduce(0, Integer::sum);

            if (pairs == 1 && pungs + kongs + chows == 4)
                return true;
        }
        return false;
    }

    /*
     * Any 7 Pairs.
     * Scoring: 4 fan + components
     */
    public Boolean isSevenPairs() {
        for (Map<Player.CombinationType, Map<Tile, Integer>> hand : this._info.getAllPossibleHands()) {
            int pairs = hand.get(CombinationType.PAIR).values().stream().reduce(0, Integer::sum);
            int pungs = hand.get(CombinationType.PUNG).values().stream().reduce(0, Integer::sum);
            int kongs = hand.get(CombinationType.KONG).values().stream().reduce(0, Integer::sum);
            int chows = hand.get(CombinationType.CHOW).values().stream().reduce(0, Integer::sum);

            if (pairs == 7 && pungs + kongs + chows == 0)
                return true;
        }
        return false;
    }

    /*
     * Any 4 pungs + any Pair.
     * All concealed and win by Self-Drawn. TODO
     * Scoring: 64 fan
     */
    public Boolean isHiddenTreasure() {
        for (Map<Player.CombinationType, Map<Tile, Integer>> hand : this._info.getAllPossibleHands()) {
            int pairs = hand.get(CombinationType.PAIR).values().stream().reduce(0, Integer::sum);
            int pungs = hand.get(CombinationType.PUNG).values().stream().reduce(0, Integer::sum);
            int kongs = hand.get(CombinationType.KONG).values().stream().reduce(0, Integer::sum);
            int chows = hand.get(CombinationType.CHOW).values().stream().reduce(0, Integer::sum);

            if (pairs == 1 && pungs == 4 && kongs + chows == 0)
                return true;
        }
        return false;
    }

    /*
     * Pung/Kong with three Winds + 1 Pair with fourth wind + any set (Pung/Kong/Chow).
     * May all be melded.
     * Scoring: 64 fan
     */
    public Boolean isLittleFourWinds() {
        for (Map<Player.CombinationType, Map<Tile, Integer>> hand : this._info.getAllPossibleHands()) {
            int pairs = hand.get(CombinationType.PAIR).values().stream().reduce(0, Integer::sum);
            int pungs = hand.get(CombinationType.PUNG).values().stream().reduce(0, Integer::sum);
            int kongs = hand.get(CombinationType.KONG).values().stream().reduce(0, Integer::sum);
            int chows = hand.get(CombinationType.CHOW).values().stream().reduce(0, Integer::sum);

            if (pairs != 1 || chows > 1 || chows + pungs + kongs != 4)
                continue;

            List<Tile> pairsList = new ArrayList<>(hand.get(CombinationType.PAIR).keySet());
            List<Tile> pungsList = new ArrayList<>(hand.get(CombinationType.PUNG).keySet());
            List<Tile> kongsList = new ArrayList<>(hand.get(CombinationType.KONG).keySet());

            TileContent content = pairsList.get(0).getContent();
            Map<TileContent, Boolean> conditions = new HashMap<>();
            TileContent.getDirections().forEach(wind -> conditions.put(wind, wind == content));

            if (!conditions.containsValue(true))
                continue;

            if (this.checkPungsAndKongs(conditions, pungsList, kongsList))
                return true;
        }
        return false;
    }

    /*
     * Pung/Kong with all Winds + any Pair.
     * May all be melded.
     * Scoring: 64 fan
     */
    public Boolean isBigFourWinds() {
        return this.isBigFourWindsOrThreeGreatScholars(Arrays.asList(
                TileContent.EAST,
                TileContent.SOUTH,
                TileContent.WEST,
                TileContent.NORTH
        ));
    }

    /*
     * Pung/Kong with all 3 dragons + any set (Pung/Kong/Chow) + any Pair.
     * May all be melded.
     * Scoring: 64 fan
     */
    public Boolean isThreeGreatScholars() {
        return this.isBigFourWindsOrThreeGreatScholars(Arrays.asList(
                TileContent.RED,
                TileContent.GREEN,
                TileContent.WHITE
        ));
    }

    private boolean isBigFourWindsOrThreeGreatScholars(List<TileContent> contents) {
        for (Map<Player.CombinationType, Map<Tile, Integer>> hand : this._info.getAllPossibleHands()) {
            int pairs = hand.get(CombinationType.PAIR).values().stream().reduce(0, Integer::sum);
            int pungs = hand.get(CombinationType.PUNG).values().stream().reduce(0, Integer::sum);
            int kongs = hand.get(CombinationType.KONG).values().stream().reduce(0, Integer::sum);
            int chows = hand.get(CombinationType.CHOW).values().stream().reduce(0, Integer::sum);

            if (pairs != 1 || chows > 4 - contents.size() || chows + pungs + kongs != 4)
                continue;

            List<Tile> pungsList = new ArrayList<>(hand.get(CombinationType.PUNG).keySet());
            List<Tile> kongsList = new ArrayList<>(hand.get(CombinationType.KONG).keySet());

            Map<TileContent, Boolean> conditions = new HashMap<>();
            contents.forEach(c -> conditions.put(c, false));

            if (this.checkPungsAndKongs(conditions, pungsList, kongsList))
                return true;
        }
        return false;
    }

    private boolean checkPungsAndKongs(
            Map<TileContent, Boolean> conditions,
            List<Tile> pungsList,
            List<Tile> kongsList) {

        for (Tile tile : pungsList) {
            conditions.replaceAll((k, v) -> v || tile.getContent() == k);
            if (conditions.values().stream().allMatch(v -> v))
                return true;
        }

        for (Tile tile : kongsList) {
            conditions.replaceAll((k, v) -> v || tile.getContent() == k);
            if (conditions.values().stream().allMatch(v -> v))
                return true;
        }
        return false;
    }

    /*
     * Three 1's + Three 9's + Sequence from 2 to 8 + any tile that matches the previous tiles
     * All tiles of the same suite.
     * Must be concealed.
     * No kongs allowed. (This is implied by the concealed restriction)
     * Scoring: 64 fan
     */
    public Boolean isNineGates() {
        // The hand should have only one suite.
        // Note: it is not possible to compose a hand with only Winds or only Dragons.
        List<TileType> handSuites = this._hand
                .keySet().stream()
                .map(Tile::getType)
                .distinct()
                .collect(Collectors.toList());
        if (handSuites.size() > 1)
            return false;

        // The hand should contain all numbers of the given suite.
        TileType suite = handSuites.get(0);
        if (!TileContent.getNumbers().stream()
                .map(num -> new Tile(suite, num))
                .allMatch(this._hand::containsKey))
            return false;

        // The hand should have at least 3 tiles of 1 and 3 tiles of 9 for the given suite.
        // Since we already checked that we only have one suite and that we have all tiles from 1 to 9,
        //     if there are three 1's and three 9's, then the extra tile must be one from 2 to 8
        //     which completes the hand with a pair.
        return this._hand.get(new Tile(suite, TileContent.ONE)) >= 3 &&
                this._hand.get(new Tile(suite, TileContent.NINE)) >= 3;
    }

    /*
     * One tile of each 1, 9, Dragon and Wind + any tile that matches the previous ones
     * Must be concealed
     * Scoring: 64 fan
     */
    public Boolean isThirteenOrphans() {
        // Hand can only have 14 tiles (no Kongs are allowed)
        if (this.getHandSize() != 14) {
            return false;
        }

        List<Tile> necessaryTilesToHave = new ArrayList<>();

        for (TileType tileType : TileType.values()) {
            if (!tileType.isSpecialType()) {
                // For a non-special type, the hand needs to have both a One and a Nine
                necessaryTilesToHave.add(new Tile(tileType, TileContent.ONE));
                necessaryTilesToHave.add(new Tile(tileType, TileContent.NINE));
            } else if (tileType.equals(TileType.WIND)) {
                // For a wind, it needs to have all the possible wind values
                for (TileContent tileContent : TileContent.getDirections()) {
                    necessaryTilesToHave.add(new Tile(tileType, tileContent));
                }
            } else {
                // Lastly, for a dragon, it needs to have all their possible values
                for (TileContent tileContent : TileContent.getColors()) {
                    necessaryTilesToHave.add(new Tile(tileType, tileContent));
                }
            }
        }

        // If any of the necessary tiles are not present in the final hand, then it can't be a Thirteen Orphans
        // If it has all the necessary tiles, then we also need to check if we have a pair
        return necessaryTilesToHave.stream().allMatch(this._hand::containsKey) &&
                this._hand.containsValue(2);
    }

    /*
     * Any 4 Kongs + any pair.
     * Scoring: 64 fan
     */
    public Boolean isAllKongs() {
        for (Map<Player.CombinationType, Map<Tile, Integer>> hand : this._info.getAllPossibleHands()) {
            int pairs = hand.get(CombinationType.PAIR).values().stream().reduce(0, Integer::sum);
            int pungs = hand.get(CombinationType.PUNG).values().stream().reduce(0, Integer::sum);
            int kongs = hand.get(CombinationType.KONG).values().stream().reduce(0, Integer::sum);
            int chows = hand.get(CombinationType.CHOW).values().stream().reduce(0, Integer::sum);

            if (pairs == 1 && pungs == 0 && kongs == 4 && chows == 0)
                return true;
        }
        return false;
    }

    /*
     * 4 Pungs/Kongs + 1 Pair of Dragons/Winds.
     * May all be melded.
     * Scoring: 64 fan
     */
    public Boolean isAllHonors() {
        return isAllHonorsOrAllTerminals(
                new ArrayList<>(),
                Arrays.asList(TileType.DRAGON, TileType.WIND)
        );
    }

    /*
     * 4 Pungs/Kongs + 1 Pair of 1/9.
     * May all be melded.
     * Scoring: 64 fan
     */
    public Boolean isAllTerminals() {
        return isAllHonorsOrAllTerminals(
                Arrays.asList(TileContent.ONE, TileContent.NINE),
                new ArrayList<>()
        );
    }

    private boolean isAllHonorsOrAllTerminals(List<TileContent> contents, List<TileType> types) {
        for (Map<CombinationType, Map<Tile, Integer>> hand : this._info.getAllPossibleHands()) {
            int pairs = hand.get(CombinationType.PAIR).values().stream().reduce(0, Integer::sum);
            int pungs = hand.get(CombinationType.PUNG).values().stream().reduce(0, Integer::sum);
            int kongs = hand.get(CombinationType.KONG).values().stream().reduce(0, Integer::sum);
            int chows = hand.get(CombinationType.CHOW).values().stream().reduce(0, Integer::sum);

            if (pairs != 1 || chows > 0 || pungs + kongs != 4)
                continue;

            List<Tile> pairsList = new ArrayList<>(hand.get(CombinationType.PAIR).keySet());
            List<Tile> pungsList = new ArrayList<>(hand.get(CombinationType.PUNG).keySet());
            List<Tile> kongsList = new ArrayList<>(hand.get(CombinationType.KONG).keySet());

            Tile pair = pairsList.get(0);
            if (!contents.contains(pair.getContent()) && !types.contains(pair.getType()))
                continue;

            boolean allTilesMatch = true;

            for (Tile t : pungsList)
                allTilesMatch = allTilesMatch &&
                        (contents.contains(t.getContent()) || types.contains(t.getType()));

            for (Tile t : kongsList)
                allTilesMatch = allTilesMatch &&
                        (contents.contains(t.getContent()) || types.contains(t.getType()));

            if (allTilesMatch)
                return true;
        }
        return false;
    }

    /*
     * 1 Pung/Kong of Green Dragon
     * 3 Pungs/Kongs + 1 Pair of Bamboos.
     * Scoring: 64 fan
     */
    public Boolean isJadeDragon() {
        return isSpecialDragon(TileContent.GREEN, TileType.BAMBOO);
    }

    /*
     * 1 Pung/Kong of Red Dragon
     * 3 Pungs/Kongs + 1 Pair of Characters.
     * Scoring: 64 fan
     */
    public Boolean isRubyDragon() {
        return isSpecialDragon(TileContent.RED, TileType.CHARACTERS);
    }

    /*
     * 1 Pung/Kong of White Dragon
     * 3 Pungs/Kongs + 1 Pair of Dots.
     * Scoring: 64 fan
     */
    public Boolean isPearlDragon() {
        return isSpecialDragon(TileContent.WHITE, TileType.DOTS);
    }

    private boolean isSpecialDragon(TileContent dragonColor, TileType pairSuite) {
        for (Map<CombinationType, Map<Tile, Integer>> hand : this._info.getAllPossibleHands()) {
            int pairs = hand.get(CombinationType.PAIR).values().stream().reduce(0, Integer::sum);
            int pungs = hand.get(CombinationType.PUNG).values().stream().reduce(0, Integer::sum);
            int kongs = hand.get(CombinationType.KONG).values().stream().reduce(0, Integer::sum);
            int chows = hand.get(CombinationType.CHOW).values().stream().reduce(0, Integer::sum);

            if (pairs != 1 || chows > 0 || pungs + kongs != 4)
                continue;

            List<Tile> pairsList = new ArrayList<>(hand.get(CombinationType.PAIR).keySet());
            List<Tile> pungsList = new ArrayList<>(hand.get(CombinationType.PUNG).keySet());
            List<Tile> kongsList = new ArrayList<>(hand.get(CombinationType.KONG).keySet());

            Tile pair = pairsList.get(0);
            if (pairSuite != pair.getType())
                continue;

            boolean allTilesAreValid = true;

            for (Tile t : pungsList)
                allTilesAreValid = allTilesAreValid && (dragonColor == t.getContent() || pairSuite == t.getType());

            for (Tile t : kongsList)
                allTilesAreValid = allTilesAreValid && (dragonColor == t.getContent() || pairSuite == t.getType());

            if (allTilesAreValid)
                return true;
        }
        return false;
    }

    /*
     * East declares "Out" with the dealt hand (after supplement tiles, if any).
     * Scoring: 64 fan
     */
    public Boolean isHeavenlyHands() {
        // TODO This method does not depend on HandInfo but might useful later
        return false;
    }

    /*
     * Non-dealer goes out on dealer's first discard (supplement tiles are allowed).
     * Scoring: 64 fan
     */
    public Boolean isEarthlyHands() {
        // TODO This method does not depend on HandInfo but might useful later
        return false;
    }

    // Each hand is represented as a HashMap, where each Key is an existing type of tile
    // present in the hand, and each associated Value is the number of times that specific tile exists in the hand.
    // One minor concern: ALL VALUES NEED TO BE BIGGER OR EQUAL TO 1.
    // If a tile is removed from a hand, and it was the only tile of that type+content, then
    // the entry containing that tile should be fully removed from the hashmap, to save space. This means that no Value
    // should ever be 0. If a get() method call returns null, then that means that no tile of that specific type+content
    // exists.
    private final HashMap<Tile, Integer> _hand = new HashMap<>();
    private final HandInfo _info;

    public HashMap<Tile, Integer> getHand() {
        return this._hand;
    }

    public int getHandSize() {
        Integer count = 0;
        for (Map.Entry<Tile, Integer> entry : this._hand.entrySet()) {
            count += entry.getValue();
        }
        return count;
    }

    public void addTile(Tile tileToAdd) throws IllegalArgumentException {
        Integer count = this._hand.get(tileToAdd);
        if (count == null) {
            this._hand.put(tileToAdd, 1);
        } else {
            if (count >= 4) {
                throw new IllegalArgumentException("MahjongHand: Hand can only have 4 or less of the same tile");
            }
            this._hand.put(tileToAdd, count + 1);
        }
    }

    private void removeTile(Tile tile) {
        Integer count = this._hand.get(tile);
        if (count != null) {
            count -= 1;
            if (count > 0) {
                this._hand.put(tile, count);
            } else {
                this._hand.remove(tile);
            }
        }
    }

    public void discardTile(Tile tileToDiscard) {
        // Right now the only thing this does is simply remove the tile from the internal HashMap.
        // However, discarding a tile can have other game-changing consequences, that will have to be recorded.
        // @TODO: encode those consequences somewhere in someway
        this.removeTile(tileToDiscard);
    }

    public boolean isWinningHand() {
        // In Mahjong, a hand is a winning hand if at least one of the following conditions applies:
        // - A hand has 4 Chow/Pung/Kongs + 1 pair
        // - A hand has 7 pairs
        // - It is a "Thirteen Orphans" hand
        // Any other hand is a not-winning hand.
        ArrayList<Function<Hand, Boolean>> winningConditions = new ArrayList<>(Arrays.asList(
                Hand::isFourCombinationsPlusPair,
                Hand::isSevenPairs,
                Hand::isThirteenOrphans));

        return winningConditions.stream().anyMatch((func) -> (func.apply(this)));
    }

//    public Integer calculateHandValue() {
//        return 0;
//    }

    @Override
    public String toString() {
        return "Your Hand is: " + _hand;
    }

    public static Hand generateRandomHand() {
        Random random = new Random();
        Tile tile = null;

        TileType[] tileTypes = TileType.values();
        TileContent[] tileContents = TileContent.values();

        ArrayList<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < 14; i++) {
            try {
                tile = new Tile(tileTypes[random.nextInt(tileTypes.length)], tileContents[random.nextInt(tileContents.length)]);
            } catch (IllegalArgumentException e) {
                // Nothing needs to be done because tile will remain null and therefore the loop will try again until success
            }
            if (tile != null) {
                tiles.add(tile);
            } else {
                i--;
            }
            tile = null;
        }

        try {
            return new Hand(tiles);
        } catch (IllegalArgumentException e) {
            // This will never happen because the loop is correctly limited so it will always generate a 14-tile hand
            // However, if it did happen, it would keep trying until it produced a valid hand
            return generateRandomHand();
        }
    }

};