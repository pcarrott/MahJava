package MahJavaLib.hand;

import MahJavaLib.tile.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// The MahjongHand structure allows us to record all the information about a specific hand
// Due to the nature of Mahjong, all MahjongHand objects should be created at the start of each
// round (with a 14-tile starting hand), and then incrementally updated.
public class Hand {
    // Each hand is represented as a HashMap, where each Key is an existing type of tile
    // present in the hand, and each associated Value is the number of times that specific tile exists in the hand.
    // One minor concern: ALL VALUES NEED TO BE BIGGER OR EQUAL TO 1.
    // If a tile is removed from a hand, and it was the only tile of that type+content, then
    // the entry containing that tile should be fully removed from the hashmap, to save space. This means that no Value
    // should ever be 0. If a get() method call returns null, then that means that no tile of that specific type+content
    // exists.
    private final Map<Tile, Integer> concealedTiles = new HashMap<>();
    private final Map<Tile, Integer> openTiles = new HashMap<>();
    private List<Combination> openCombinations = new ArrayList<>();
    private List<Tile> concealedKongs = new ArrayList<>();
    private final HandInfo info = new HandInfo();

    public Hand(List<Tile> startingHand) throws IllegalArgumentException {
        if (startingHand.size() != 13) {
            throw new IllegalArgumentException("MahjongHand: Hand must contain 13 tiles, contains: " + startingHand.size());
        } else {
            for (Tile tile : startingHand) {
                this.addTile(tile);
            }
        }
    }

    public boolean hasTile(Tile tile, Integer n) {
        return this.concealedTiles.containsKey(tile) && (this.concealedTiles.get(tile) >= n);
    }

    public List<Tile> getConcealedKongs() {
        return this.concealedTiles.entrySet().stream()
                .filter(e -> e.getValue() == 4)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /*
     * Some special hands don't allow Chows, others only allow Pairs, and so on.
     * This method allows to make the counting of combinations for each special hand generic, receiving as parameters:
     *   - the given hand
     *   - the concrete number of Pairs required
     *   - the concrete total of non-Pair combinations (Pungs/Kongs/Chows) allowed
     *   - the maximum number of Pungs allowed
     *   - the maximum number of Kongs allowed
     *   - the maximum number of Chows allowed
     */
    private boolean checkCombinationCount(
            Map<CombinationType, Map<Tile, Integer>> hand,
            int pairCount, int nonPairCount,
            int maxPungs, int maxKongs, int maxChows) {

        int pairs = hand.get(CombinationType.PAIR).values().stream().reduce(0, Integer::sum);
        int pungs = hand.get(CombinationType.PUNG).values().stream().reduce(0, Integer::sum);
        int kongs = hand.get(CombinationType.KONG).values().stream().reduce(0, Integer::sum);
        int chows = hand.get(CombinationType.CHOW).values().stream().reduce(0, Integer::sum);

        pungs += this.openCombinations.stream()
                .map(Combination::getCombinationType).filter(c -> c == CombinationType.PUNG).count();
        kongs += this.openCombinations.stream()
                .map(Combination::getCombinationType).filter(c -> c == CombinationType.KONG).count();
        chows += this.openCombinations.stream()
                .map(Combination::getCombinationType).filter(c -> c == CombinationType.CHOW).count();

        kongs += this.concealedKongs.size();

        return pairs == pairCount && pungs+kongs+chows == nonPairCount &&
                pungs <= maxPungs && kongs <= maxKongs && chows <= maxChows;
    }

    /*
     * Some special hands only require that the hand's combinations have the specified number of occurrences, without
     *   having restrictions regarding the type and content of the tiles.
     * This method checks this for each possible hand we may have, given the necessary parameters.
     */
    private boolean checkCountForAllHands(int pairCount, int nonPairCount, int maxPungs, int maxKongs, int maxChows) {
        for (Map<CombinationType, Map<Tile, Integer>> hand : this.info.getAllPossibleHands()) {
            if (this.checkCombinationCount(hand, pairCount, nonPairCount, maxPungs, maxKongs, maxChows))
                return true;
        }

        // No hand matches the specified parameters
        return false;
    }

    /*
     * Any 4 sets (Pungs/Kongs/Chows) + any Pair.
     * Scoring: Components
     */
    public Boolean isFourCombinationsPlusPair() {
        return this.checkCountForAllHands(1, 4,4, 4, 4);
    }

    /*
     * Any 7 Pairs.
     * Scoring: 4 fan + components
     */
    public Boolean isSevenPairs() {
        return this.checkCountForAllHands(7, 0,0, 0, 0);
    }

    /*
     * Any 4 pungs + any Pair.
     * All concealed and win by Self-Drawn.
     * Scoring: 64 fan
     */
    public Boolean isHiddenTreasure(boolean selfDrawn) {
        return selfDrawn && this.openCombinations.isEmpty() &&
                this.checkCountForAllHands(1, 4,4, 0, 0);
    }

    /*
     * Any 4 Kongs + any pair.
     * Scoring: 64 fan
     */
    public Boolean isAllKongs() {
        return this.checkCountForAllHands(1, 4,0, 4, 0);
    }

    /*
     * This auxiliary method generalizes the checking process for hands that require a single Pair and 4 sets.
     *
     * For the parameters, we have:
     *   - contents: Allowed contents for the sets in the hand (e.g. only 1s and 9s)
     *   - types: Allowed types for the sets in the hand (e.g. only Dragons and Winds)
     *   - mandatory: Mandatory contents for the sets and/or Pairs (e.g. for a Jade Dragon, we need to make the Green
     *                Dragon mandatory, in order to avoid accepting hands composed of only bamboos)
     *
     * We also have more complex parameters such as:
     *   - pairValue: the tile that composes the Pair may be restricted by its type or content, so we pass one of the
     *                Tile::getType and Tile::getContent methods as an argument.
     *   - pairCondition: since the tile can be restricted by its type or content, we don't now if we should check the
     *                    types or contents maps, so we pass a pairValues::containsKey method as an argument. This
     *                    pairValues can be one of the other supplied maps or a different map.
     *   - maxChows: some hands may allow chows and other do not.
     */
    private <T> boolean isStandardHandWithMandatoryTiles(
            Map<TileContent, TileContent> contents, Map<TileType, TileType> types,
            Map<TileContent, TileContent> mandatory,
            Function<Tile, T> pairValue, Function<T, Boolean> pairCondition,
            int maxChows) {

        for (Map<CombinationType, Map<Tile, Integer>> hand : this.info.getAllPossibleHands()) {
            if (!this.checkCombinationCount(hand, 1, 4, 4, 4, maxChows))
                continue;

            Tile pair = new ArrayList<>(hand.get(CombinationType.PAIR).keySet()).get(0);
            // We extract the type/content of the Pair tile and apply the pairCondition.
            // Ignores the hand if the allowed Pair values do not contain the Pair tile.
            if (!pairCondition.apply(pairValue.apply(pair)))
                continue;

            // Create a map to see if all mandatory tiles are present
            Map<TileContent, Boolean> conditions = new HashMap<>();
            // The Pair tile may be one the mandatory tiles so we need to take that into account when we initialize the
            //    map. All entries different from the Pair tile will be initialised to false.
            mandatory.keySet().forEach(content -> conditions.put(content, content == pair.getContent()));
            // To check if all tiles match the allowed types and contents, a single boolean is needed.
            boolean allTilesMatch = true;

            // Check all pungs
            for (Tile t : hand.get(CombinationType.PUNG).keySet()) {
                // If it is a mandatory tile, then it will be updated to true. Otherwise, it will keep the stored value.
                conditions.replaceAll((k, v) -> v || t.getContent() == k);
                // If the tile's type/content is allowed, then all tiles still match. Otherwise, it will become false.
                allTilesMatch = allTilesMatch &&
                        (contents.containsKey(t.getContent()) || types.containsKey(t.getType()));
            }

            // Check all kongs
            for (Tile t : hand.get(CombinationType.KONG).keySet()) {
                // If it is a mandatory tile, then it will be updated to true. Otherwise, it will keep the stored value.
                conditions.replaceAll((k, v) -> v || t.getContent() == k);
                // If the tile's type/content is allowed, then all tiles still match. Otherwise, it will become false.
                allTilesMatch = allTilesMatch &&
                        (contents.containsKey(t.getContent()) || types.containsKey(t.getType()));
            }

            for (Tile t : hand.get(CombinationType.CHOW).keySet()) {
                // If the tile's type/content is allowed, then all tiles still match. Otherwise, it will become false.
                allTilesMatch = allTilesMatch &&
                        (contents.containsKey(t.getContent()) || types.containsKey(t.getType()));
            }

            for (Combination c : this.openCombinations) {
                Tile t = (Tile) c.getTiles().keySet().stream().sorted().toArray()[0];

                // If it is a mandatory tile, then it will be updated to true. Otherwise, it will keep the stored value.
                conditions.replaceAll((k, v) -> v || t.getContent() == k);
                // If the tile's type/content is allowed, then all tiles still match. Otherwise, it will become false.
                allTilesMatch = allTilesMatch &&
                        (contents.containsKey(t.getContent()) || types.containsKey(t.getType()));
            }

            for (Tile kongTile : this.concealedKongs) {
                // If it is a mandatory tile, then it will be updated to true. Otherwise, it will keep the stored value.
                conditions.replaceAll((k, v) -> v || kongTile.getContent() == k);
                // If the tile's type/content is allowed, then all tiles still match. Otherwise, it will become false.
                allTilesMatch = allTilesMatch &&
                        (contents.containsKey(kongTile.getContent()) || types.containsKey(kongTile.getType()));
            }

            // If all tiles match the allowed types and contents, and all mandatory tiles are present, then we have the
            //   the specified special hand.
            if (allTilesMatch && conditions.entrySet().stream().allMatch(Map.Entry::getValue))
                return true;
        }

        // No hand matches the specified parameters
        return false;
    }

    /*
     * Pung/Kong with 3 Winds + 1 Pair with fourth Wind + any set (Pung/Kong/Chow).
     * May all be melded.
     * Scoring: 64 fan
     */
    public Boolean isLittleFourWinds() {
        Map<TileType, TileType> types = Arrays.stream(TileType.values())
                .collect(Collectors.toMap(Function.identity(), Function.identity()));
        Map<TileContent, TileContent> mandatory = TileContent.getDirections().stream()
                .collect(Collectors.toMap(Function.identity(), Function.identity()));

        return isStandardHandWithMandatoryTiles(
                new HashMap<>(), types, mandatory,
                Tile::getContent, mandatory::containsKey, 1);
    }

    /*
     * Pung/Kong with all four Winds + any Pair.
     * May all be melded.
     * Scoring: 64 fan
     */
    public Boolean isBigFourWinds() {
        Map<TileType, TileType> types = Map.of(TileType.WIND, TileType.WIND);
        Map<TileContent, TileContent> mandatory = TileContent.getDirections().stream()
                .collect(Collectors.toMap(Function.identity(), Function.identity()));
        Map<TileContent, TileContent> pairValues = Stream.of(TileContent.getColors(), TileContent.getNumbers())
                .flatMap(Collection::stream).collect(Collectors.toMap(Function.identity(), Function.identity()));

        return isStandardHandWithMandatoryTiles(
                new HashMap<>(), types, mandatory,
                Tile::getContent, pairValues::containsKey, 0);
    }

    /*
     * Pung/Kong with 2 Dragons + 1 Pair with third Dragon + any 2 sets (Pung/Kong/Chow).
     * May all be melded.
     * Scoring: 4 fan + components
     */
    public Boolean isLittleThreeDragons() {
        Map<TileType, TileType> types = Arrays.stream(TileType.values())
                .collect(Collectors.toMap(Function.identity(), Function.identity()));
        Map<TileContent, TileContent> mandatory = TileContent.getColors().stream()
                .collect(Collectors.toMap(Function.identity(), Function.identity()));

        return isStandardHandWithMandatoryTiles(
                new HashMap<>(), types, mandatory,
                Tile::getContent, mandatory::containsKey, 2);
    }

    /*
     * Pung/Kong with all 3 dragons + any set (Pung/Kong/Chow) + any Pair.
     * May all be melded.
     * Scoring: 64 fan
     */
    public Boolean isThreeGreatScholars() {
        Map<TileType, TileType> types = Arrays.stream(TileType.values())
                .collect(Collectors.toMap(Function.identity(), Function.identity()));
        Map<TileContent, TileContent> mandatory = TileContent.getColors().stream()
                .collect(Collectors.toMap(Function.identity(), Function.identity()));
        Map<TileContent, TileContent> pairValues = Stream.of(TileContent.getDirections(), TileContent.getNumbers())
                .flatMap(Collection::stream).collect(Collectors.toMap(Function.identity(), Function.identity()));

        return isStandardHandWithMandatoryTiles(
                new HashMap<>(), types, mandatory,
                Tile::getContent, pairValues::containsKey, 1);
    }

    /*
     * 4 Pungs/Kongs + 1 Pair of Dragons/Winds.
     * May all be melded.
     * Scoring: 64 fan
     */
    public Boolean isAllHonors() {
        Map<TileType, TileType> types = Map.of(
                TileType.DRAGON, TileType.DRAGON,
                TileType.WIND, TileType.WIND
        );

        return isStandardHandWithMandatoryTiles(
                new HashMap<>(), types, new HashMap<>(),
                Tile::getType, types::containsKey, 0);
    }

    /*
     * 4 Pungs/Kongs + 1 Pair of 1/9.
     * May all be melded.
     * Scoring: 64 fan
     */
    public Boolean isAllTerminals() {
        Map<TileContent, TileContent> contents = Map.of(
                TileContent.ONE, TileContent.ONE,
                TileContent.NINE, TileContent.NINE
        );

        return isStandardHandWithMandatoryTiles(
                contents, new HashMap<>(), new HashMap<>(),
                Tile::getContent, contents::containsKey, 0);
    }

    /*
     * 1 Pung/Kong of Green Dragon
     * 3 Pungs/Kongs + 1 Pair of Bamboos.
     * Scoring: 64 fan
     */
    public Boolean isJadeDragon() {
        Map<TileContent, TileContent> contents = Map.of(TileContent.GREEN, TileContent.GREEN);
        Map<TileType, TileType> types = Map.of(TileType.BAMBOO, TileType.BAMBOO);

        return isStandardHandWithMandatoryTiles(
                contents, types, contents,
                Tile::getType, types::containsKey, 0);
    }

    /*
     * 1 Pung/Kong of Red Dragon
     * 3 Pungs/Kongs + 1 Pair of Characters.
     * Scoring: 64 fan
     */
    public Boolean isRubyDragon() {
        Map<TileContent, TileContent> contents = Map.of(TileContent.RED, TileContent.RED);
        Map<TileType, TileType> types = Map.of(TileType.CHARACTERS, TileType.CHARACTERS);

        return isStandardHandWithMandatoryTiles(
                contents, types, contents,
                Tile::getType, types::containsKey, 0);
    }

    /*
     * 1 Pung/Kong of White Dragon
     * 3 Pungs/Kongs + 1 Pair of Dots.
     * Scoring: 64 fan
     */
    public Boolean isPearlDragon() {
        Map<TileContent, TileContent> contents = Map.of(TileContent.WHITE, TileContent.WHITE);
        Map<TileType, TileType> types = Map.of(TileType.DOTS, TileType.DOTS);

        return isStandardHandWithMandatoryTiles(
                contents, types, contents,
                Tile::getType, types::containsKey, 0);
    }

    /*
     * Three 1's + Three 9's + Sequence from 2 to 8 + any tile that matches the previous tiles
     * All tiles of the same suite.
     * Must be concealed.
     * No Kongs allowed.
     * Scoring: 64 fan
     */
    public Boolean isNineGates() {
        if (this.getHandSize() != 14 || !this.openCombinations.isEmpty()) {
            return false;
        }

        // The hand should have only one suite.
        // Note: it is not possible to compose a hand with only Winds or only Dragons.
        List<TileType> handSuites = this.concealedTiles
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
                .allMatch(this.concealedTiles::containsKey))
            return false;

        // The hand should have at least 3 tiles of 1 and 3 tiles of 9 for the given suite.
        // Since we already checked that we only have one suite and that we have all tiles from 1 to 9,
        //     if there are three 1's and three 9's, then the extra tile must be one from 2 to 8
        //     which completes the hand.
        return this.concealedTiles.get(new Tile(suite, TileContent.ONE)) >= 3 &&
                this.concealedTiles.get(new Tile(suite, TileContent.NINE)) >= 3;
    }

    /*
     * One tile of each 1, 9, Dragon and Wind + any tile that matches the previous ones
     * Must be concealed
     * Scoring: 64 fan
     */
    public Boolean isThirteenOrphans() {
        if (!this.openCombinations.isEmpty()) {
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
        return necessaryTilesToHave.stream().allMatch(this.concealedTiles::containsKey) &&
                this.concealedTiles.containsValue(2);
    }

    public Map<Tile, Integer> getHand() {
        return this.concealedTiles;
    }

    public void setOpenHand(List<Combination> combinations) {
        this.openCombinations = combinations;
    }

    public void setConcealedKongs(List<Tile> concealedKongs) {
        this.concealedKongs = concealedKongs;
    }

    public int getHandSize() {
        Integer count = 0;
        for (Map.Entry<Tile, Integer> entry : this.concealedTiles.entrySet()) {
            count += entry.getValue();
        }
        for (Map.Entry<Tile, Integer> entry : this.openTiles.entrySet()) {
            count += entry.getValue();
        }
        return count;
    }

    public List<Map<CombinationType, Map<Tile, Integer>>> getPossibleHands() {
        return this.info.getAllPossibleHands();
    }

    public void addTile(Tile tileToAdd) throws IllegalArgumentException {
        Integer count = this.concealedTiles.get(tileToAdd);
        if (count == null) {
            this.concealedTiles.put(tileToAdd, 1);
        } else {
            if (count >= 4) {
                throw new IllegalArgumentException("MahjongHand: Hand can only have 4 or less of the same tile");
            }
            this.concealedTiles.put(tileToAdd, count + 1);
        }
        this.info.updateHands(this.concealedTiles);
    }

    public void removeTile(Tile tile) {
        Integer count = this.concealedTiles.get(tile);
        if (count != null) {
            count -= 1;
            if (count > 0) {
                this.concealedTiles.put(tile, count);
            } else {
                this.concealedTiles.remove(tile);
            }
            this.info.updateHands(this.concealedTiles);
        }
    }

    public void claimTile(Tile tileToAdd, Combination combination){
        this.addTile(tileToAdd);
        for (Map.Entry<Tile, Integer> e : combination.getTiles().entrySet()) {
            Integer count = this.concealedTiles.get(e.getKey());
            if (count > e.getValue())
                this.concealedTiles.put(e.getKey(), count - e.getValue());
            else
                this.concealedTiles.remove(e.getKey());
            this.openTiles.merge(e.getKey(), e.getValue(), Integer::sum);
        }
        this.openCombinations.add(combination);
    }

    public void declareConcealedKong(Tile kongTile) {
        this.concealedTiles.remove(kongTile);
        this.concealedKongs.add(kongTile);
    }

    public Map<Combination, List<Map<CombinationType, Map<Tile, Integer>>>> getPossibleCombinationsForTile(
            Tile discardedTile) {

        Map<Combination, List<Map<CombinationType, Map<Tile, Integer>>>> res = new HashMap<>();

        var chows = discardedTile.getPossibleChowCombinations()
                .stream()
                .filter(chow -> chow.stream().allMatch(
                        tile -> tile.equals(discardedTile) || this.concealedTiles.containsKey(tile)
                ))
                .collect(Collectors.toList());

        for (List<Tile> chow : chows) {
            List<Tile> remainingTiles = chow.stream()
                    .filter(t -> !t.equals(discardedTile))
                    .collect(Collectors.toList());

            remainingTiles.forEach(this::removeTile);
            this.info.updateHands(this.concealedTiles);
            this.info.getAllPossibleHands().forEach(h -> {
                h.get(CombinationType.CHOW).merge(chow.get(0), 1, Integer::sum);
                this.openCombinations.forEach(c -> {
                    Tile t = (Tile) c.getTiles().keySet().stream().sorted().toArray()[0];
                    h.get(c.getCombinationType()).merge(t, c.getTiles().get(t), Integer::sum);
                });
                this.concealedKongs.forEach(k -> h.get(CombinationType.KONG).put(k, 1));
            });
            Combination combination = new Combination(CombinationType.CHOW, chow);
            res.put(combination, this.info.getAllPossibleHands());
            remainingTiles.forEach(this::addTile);
        }

        Integer count = this.concealedTiles.get(discardedTile);

        if (count != null && count == 3) {
            this.concealedTiles.remove(discardedTile);
            this.info.updateHands(this.concealedTiles);
            this.info.getAllPossibleHands().forEach(h -> {
                h.get(CombinationType.KONG).merge(discardedTile, 1, Integer::sum);
                this.openCombinations.forEach(c -> {
                    Tile t = (Tile) c.getTiles().keySet().stream().sorted().toArray()[0];
                    h.get(c.getCombinationType()).merge(t, c.getTiles().get(t), Integer::sum);
                });
                this.concealedKongs.forEach(k -> h.get(CombinationType.KONG).put(k, 1));
            });
            Combination combination = new Combination(CombinationType.KONG, Collections.nCopies(4, discardedTile));
            res.put(combination, this.info.getAllPossibleHands());
            this.concealedTiles.put(discardedTile, 3);
        }

        if (count != null && count >= 2) {
            if (count == 2)
                this.concealedTiles.remove(discardedTile);
            else
                this.concealedTiles.put(discardedTile, 1);

            this.info.updateHands(this.concealedTiles);
            this.info.getAllPossibleHands().forEach(h -> {
                        h.get(CombinationType.PUNG).merge(discardedTile, 1, Integer::sum);
                        this.openCombinations.forEach(c -> {
                            Tile t = (Tile) c.getTiles().keySet().stream().sorted().toArray()[0];
                            h.get(c.getCombinationType()).merge(t, c.getTiles().get(t), Integer::sum);
                        });
                        this.concealedKongs.forEach(k -> h.get(CombinationType.KONG).put(k, 1));
            });
            Combination combination = new Combination(CombinationType.PUNG, Collections.nCopies(3, discardedTile));
            res.put(combination, this.info.getAllPossibleHands());

            this.concealedTiles.merge(discardedTile, 2, Integer::sum);
        }

        this.info.updateHands(this.concealedTiles);
        return res;
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

    public boolean isMaxScoreWinningHand(boolean selfDrawn) {
        // Every winning hand that is not just a regular 4 Sets + 1 Pair or a 7 Pairs or a Little Three Dragons
        ArrayList<Function<Hand, Boolean>> winningConditions = new ArrayList<>(Arrays.asList(
                h -> h.isHiddenTreasure(selfDrawn),
                Hand::isAllKongs,
                Hand::isLittleFourWinds,
                Hand::isBigFourWinds,
                Hand::isThreeGreatScholars,
                Hand::isAllHonors,
                Hand::isAllTerminals,
                Hand::isJadeDragon,
                Hand::isRubyDragon,
                Hand::isPearlDragon,
                Hand::isNineGates,
                Hand::isThirteenOrphans));

        return winningConditions.stream().anyMatch((func) -> (func.apply(this)));
    }

    public boolean isWinningTile(Tile discardedTile) {
        this.addTile(discardedTile);
        boolean canWin = this.isWinningHand();
        this.removeTile(discardedTile);
        return canWin;
    }

    private List<Map<CombinationType, Map<Tile, Integer>>> getFourCombinationsPlusPair() {
        List<Map<CombinationType, Map<Tile, Integer>>> res = new ArrayList<>();
        for (Map<CombinationType, Map<Tile, Integer>> hand : this.info.getAllPossibleHands())
            if (this.checkCombinationCount(hand, 1, 4, 4, 4, 4)) {
                for (Combination combination : this.openCombinations) {
                    Tile t = combination.getTiles().keySet().stream().findFirst().get();
                    hand.get(combination.getCombinationType()).merge(t, 1, Integer::sum);
                }
                for (Tile kongTile : this.concealedKongs) {
                    hand.get(CombinationType.KONG).put(kongTile, 1);
                }
                res.add(hand);
            }

        return res;
    }

    private Map<CombinationType, Map<Tile, Integer>> getSevenPairs() {
        return Map.of(
                CombinationType.PUNG, new HashMap<>(),
                CombinationType.KONG, new HashMap<>(),
                CombinationType.CHOW, new HashMap<>(),
                CombinationType.PAIR, this.concealedTiles
        );
    }

    public Integer calculateHandValue(
            TileContent playerWind, TileContent roundWind,
            boolean selfDrawn, boolean firstPlay) {

        if (!this.isWinningHand())
            return 0;

        // Winning in the first play implies one of two things:
        //   - The dealer won with the dealt hand (Heavenly Hands)
        //   - A non-dealer claimed the dealer's first discard (Earthly Hands)
        if (firstPlay || this.isMaxScoreWinningHand(selfDrawn))
            return 64;

        int score = this.isLittleThreeDragons() ? 4 : 0;

        List<Map<CombinationType, Map<Tile, Integer>>> winningHands = new ArrayList<>();

        if (this.isSevenPairs()) {
            score = 4;
            winningHands.add(this.getSevenPairs());
        } else {
            winningHands.addAll(this.getFourCombinationsPlusPair());
        }

        int maxScore = score;
        for (Map<CombinationType, Map<Tile, Integer>> winningHand : winningHands) {
            System.out.println("Winning hand: ");
            winningHand.entrySet().stream()
                    .filter(c -> !c.getValue().isEmpty())
                    .forEach(c -> System.out.println("\t" + c));

            Set<Tile> pungs = winningHand.get(CombinationType.PUNG).keySet();
            Set<Tile> kongs = winningHand.get(CombinationType.KONG).keySet();

            int aux = score;

            // Every Pung/Kong of Dragons/Wind of the Round/Player's own wind is worth 1 Fan
            aux += pungs.stream().filter(t -> t.getType() == TileType.DRAGON).count();
            aux += kongs.stream().filter(t -> t.getType() == TileType.DRAGON).count();
            aux += pungs.stream().filter(t -> t.getType() == TileType.WIND && t.getContent() == playerWind).count();
            aux += kongs.stream().filter(t -> t.getType() == TileType.WIND && t.getContent() == playerWind).count();
            aux += pungs.stream().filter(t -> t.getType() == TileType.WIND && t.getContent() == roundWind).count();
            aux += kongs.stream().filter(t -> t.getType() == TileType.WIND && t.getContent() == roundWind).count();

            if (winningHand.get(CombinationType.CHOW).size() == 4)
                aux += 1; // Hand composed of only Chows is worth 1 Fan
            else if (pungs.size() + kongs.size() == 4)
                aux += 3; // Hand composed of only Pungs/Kongs is worth 3 Fan

            List<TileType> handSuites = winningHand.values().stream()
                    .flatMap(m -> m.keySet().stream())
                    .map(Tile::getType)
                    .distinct()
                    .collect(Collectors.toList());

            if (handSuites.size() == 1)
                aux += 6; // Hand composed of only 1 suite is worth 6 Fan

            handSuites = handSuites.stream().filter(tt -> !tt.isSpecialType()).collect(Collectors.toList());
            if (handSuites.size() == 1)
                aux += 3; // Hand composed of 1 suite and Dragons/Winds is worth 3 Fan

            // Consider only the maximum score over all possible winning hands
            // Although rare, it is possible to have multiple winnings with the same set of tiles
            if (aux > maxScore)
                maxScore = aux;
        }

        return maxScore;
    }

    @Override
    public String toString() {
        return "Your Hand is: " + this.concealedTiles;
    }

    public static Hand generateRandomHand() {
        Random random = new Random();
        Tile tile = null;

        TileType[] tileTypes = TileType.values();
        TileContent[] tileContents = TileContent.values();

        ArrayList<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < 13; i++) {
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

}