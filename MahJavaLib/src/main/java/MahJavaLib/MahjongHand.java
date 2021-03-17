package MahJavaLib;

import java.util.*;
import java.util.function.Function;

// The MahjongHand structure allows us to record all the information about a specific hand
// Due to the nature of Mahjong, all MahjongHand objects should be created at the start of each
// round (with a 14-tile starting hand), and then incrementally updated.
public class MahjongHand {
    // This info structure allows us to record all the major information about each hand
    // as the game progresses (ex: how many Chows/Pungs/Kongs does this hand have, how many tiles
    // of each type, etc.)
    // This structure should be created right at the start of each game, with the starting hand
    // and it should be incrementally updated as the game progresses.
    // @TODO: add a way to record Kongs, I'm still not fully sure on how to encode it
    public static class MahjongHandInfo {
        // Record all the important combinations you can make in a Mahjong hand
        // @TODO: create the Combination enum, and make this a HashMap<Combination, ArrayList<...>>
        ArrayList<ArrayList<MahJavaLib.MahjongTile>> _chows = new ArrayList<>();
        ArrayList<ArrayList<MahJavaLib.MahjongTile>> _pungs = new ArrayList<>();
        ArrayList<ArrayList<MahJavaLib.MahjongTile>> _kongs = new ArrayList<>();

        // Also, having a way to get the number of all specific tile types in a hand is very useful
        HashMap<MahJavaLib.MahjongTile.TileType, Integer> _numberOfTilesOfType = new HashMap<>();

        public MahjongHandInfo(){}

        public MahjongHandInfo(MahjongHandInfo info) {
            this._chows = new ArrayList<>(info._chows);
            this._pungs = new ArrayList<>(info._pungs);
            this._kongs = new ArrayList<>(info._kongs);
            this._numberOfTilesOfType = new HashMap<>(info._numberOfTilesOfType);
        }
        public MahjongHandInfo(MahjongHand startingHand) throws IllegalArgumentException {
            Integer handSize = startingHand.getHandSize();
            if (handSize != 14) {
                throw new IllegalArgumentException("MahjongHandInfo: Starting hand size must be 14; is: " + handSize);
            }

            // Since this will be a destructive operation (to make sure we are not creating multiple different sequences
            // with the same tiles, we have to copy the tiles objects.
            MahjongHand copyHand = new MahjongHand(startingHand);

            // At the start, no Kongs can be created, since you must declare one before it can be considered a Kong, so
            // there is no need to check for them.

            // We start by checking for Pungs (we will also take advantage of this for loop to also record the number
            // of tiles of each type)

            for (Map.Entry<MahJavaLib.MahjongTile, Integer> entry : copyHand.getHand().entrySet()) {
                // The behaviour of merge is the following: if the key doesn't exist, then it stores the supplied value;
                // else it runs the given function (in this case sum) with the value supplied and the stored value
                // as its arguments, and stores the result with the key. Weird name though.
                this._numberOfTilesOfType.merge(entry.getKey().getType(), 1, Integer::sum);
                if (entry.getValue() >= 3) {
                    _pungs.add(new ArrayList<>(Collections.nCopies(3, entry.getKey())));
                }
            }

            // Remove all done pung tiles, so we don't record the same piece twice
            for (ArrayList<MahjongTile> pung : _pungs) {
                for (MahjongTile tile : pung) {
                    copyHand.removeTile(tile);
                }
            }

            // Theoretically, we would like to simply iterate through all the available tiles, check if there is a
            // chow that can be made with it, and if so, record that chow and remove all its pieces from the hand.
            // Unfortunately, because of this last removal step, it would invalidate the iterator, which would throw us
            // a ConcurrentException. So, instead of that, we will iterate through all the available tiles until we get
            // to a tile that can form a Chow. Then we will stop the iteration, record and remove all the Chow's pieces
            // and start the iteration again. If we got to the end of the hand, then it means that there are no more
            // possible Chows in the hand, and so we can break out of this loop.
            while (true) {
                boolean chowInHand = false;
                outerFor:
                for (Map.Entry<MahJavaLib.MahjongTile, Integer> entry : copyHand.getHand().entrySet()) {
                    ArrayList<ArrayList<MahJavaLib.MahjongTile>> possibleChows = entry.getKey().getPossibleChowCombinations();
                    for (ArrayList<MahJavaLib.MahjongTile> chow : possibleChows) {
                        chowInHand = chow.stream().allMatch(tile -> copyHand.getHand().get(tile) != null);
                        // If we can make a Chow, then we will add it to our information, and remove the tiles that make
                        // this a recorded chow, so we don't make mistakes.
                        if (chowInHand) {
                            this._chows.add(chow);
                            for (MahjongTile chowTile : chow) {
                                copyHand.removeTile(chowTile);
                            }

                            // We need to exit the full hand iteration
                            break outerFor;
                        }
                    }
                }

                if (!chowInHand) {
                    // Got to the end of the hand and there are no more chows
                    break;
                }
            }
        }
    }

    public static Boolean isFourCombinationsPlusPair(MahjongHand hand) {
        // This hand is a normal winning hand of Mahjong, that consists of:
        // - 4 Chow/Pung/Kongs + 1 pair

        // If there is less then 4 total combinations, we can simply move on; we know for sure that
        // this is not a hand of this type
        if (hand._info._chows.size() + hand._info._pungs.size() + hand._info._kongs.size() < 4) {
            return false;
        }

        // If we got here, then we know that there are at least 4 combinations present in the hand
        // Now we just need to find the remaining pair
        for (Map.Entry<MahJavaLib.MahjongTile, Integer> entry : hand.getHand().entrySet()) {
            if (entry.getValue() == 2) {
                return true;
            }
        }
        return false;
    }

    public static Boolean isSevenPairs(MahjongHand hand) {
        // A "Seven Pairs" hand is, as the name implies, a hand solely consisting of seven pairs.
        for (Map.Entry<MahJavaLib.MahjongTile, Integer> entry : hand.getHand().entrySet()) {
            // This one is as easy as it gets: we go through all the hand entries, and check if we have
            // every tile a even number of times (we can only have up to 4 tiles, and 0 can never be in the hand
            // so this is the same as checking if the value is different than 2 or 4).
            if (entry.getValue() % 2 != 0) {
                return false;
            }
        }

        return true;
    }

    public static Boolean isNineGates(MahjongHand hand) {
        // A "Nine Gates" hand is a special hand and consists of: a Pung of 1, Pung of 9, and the sequence of all the
        // numbers in between, all of them of the same type + 1 pair with any available tile
        // The hand must also be closed, and no Kongs are allowed.

        // First check that we have only two pungs and no kongs (no more than 14 tiles total in hand)
        if (hand._info._pungs.size() != 2 || hand._info._kongs.size() != 0 || hand.getHandSize() != 14) {
            return false;
        }

        // Get the assumed type of the Nine Gates hand (basically get any tile and choose its type)
        MahJavaLib.MahjongTile.TileType nineGatesType = hand._info._pungs.get(0).get(0).getType();

        // Check if both Pungs are one Pung of 1s and one Pung of 9s of the same type
        if (hand.getHand().get(new MahJavaLib.MahjongTile(nineGatesType, MahJavaLib.MahjongTile.TileContent.ONE)) != 3 ||
                hand.getHand().get(new MahJavaLib.MahjongTile(nineGatesType, MahJavaLib.MahjongTile.TileContent.NINE)) != 3) {
            return false;
        }

        // For the intermediate sequence, we shall iterate through all the tiles in hand, check if their type is
        // the same as both Pungs checked above, and if they are not a part of the Pung (which means they must be a part
        // of the sequence, store the Content value in an array.
        // Then we sort this array, and check if it is equal to a range of [2, 8]; which means that we have at least
        // one of each Number tiles of the same type.
        ArrayList<Integer> values = new ArrayList<>();
        values.ensureCapacity(hand.getHandSize());

        for (Map.Entry<MahJavaLib.MahjongTile, Integer> entry : hand.getHand().entrySet()) {
            MahJavaLib.MahjongTile tile = entry.getKey();
            // All tiles MUST be of the same type
            if (tile.getType() != nineGatesType) {
                return false;
            }

            // Ignore the 1s and 9s, we already checked their pre-conditions
            if (tile.getContent()._value > 1 && tile.getContent()._value < 9) {
                values.add(tile.getContent()._value);
            }
        }
        // 2021 and Java still does not allow you to create a Range stream of ints to ArrayList<Integers> :)
        ArrayList<Integer> range = new ArrayList<>();
        for (Integer i = 2; i < 9; ++i) {
            range.add(i);
        }

        Collections.sort(values);
        if (!values.equals(range)) {
            return false;
        }

        // Finally, we only need to check if there is a single pair in our hand
        for (Map.Entry<MahJavaLib.MahjongTile, Integer> entry : hand.getHand().entrySet()) {
            if (entry.getValue() == 2) {
                return true;
            }
        }

        return false;
    }

    public static Boolean isThirteenOrphans(MahjongHand hand) {
        // A "Thirteen Orphans" hand is a special hand that consists of one of each 1s, 9s, Dragons, Winds, and a pair
        // with any of them

        // Hand can only have 14 tiles (no Kongs are allowed)
        if (hand.getHandSize() != 14) {
            return false;
        }

        ArrayList<MahJavaLib.MahjongTile> necessaryTilesToHave = new ArrayList<>();

        for (MahJavaLib.MahjongTile.TileType tileType : MahJavaLib.MahjongTile.TileType.values()) {
            if (!tileType.isSpecialType()) {
                // For a non-special type, the hand needs to have both a One and a Nine
                necessaryTilesToHave.add(new MahJavaLib.MahjongTile(tileType, MahJavaLib.MahjongTile.TileContent.ONE));
                necessaryTilesToHave.add(new MahJavaLib.MahjongTile(tileType, MahJavaLib.MahjongTile.TileContent.NINE));
            } else if (tileType.equals(MahJavaLib.MahjongTile.TileType.WIND)) {
                // For a wind, it needs to have all the possible wind values
                for (MahjongTile.TileContent tileContent : MahjongTile.TileContent.getDirections()) {
                    necessaryTilesToHave.add(new MahJavaLib.MahjongTile(tileType, tileContent));
                }
            } else {
                // Lastly, for a dragon, it needs to have all their possible values
                for (MahjongTile.TileContent tileContent : MahjongTile.TileContent.getColors()) {
                    necessaryTilesToHave.add(new MahJavaLib.MahjongTile(tileType, tileContent));
                }
            }
        }

        // If any of the necessary tiles are not present in the final hand, then it can't be a Thirteen Orphans
        if (necessaryTilesToHave.stream().anyMatch(necessaryTile -> (hand.getHand().get(necessaryTile) == null))) {
            return false;
        }

        // If it has all the necessary tiles, then we also need to check if we have a pair
        for (Map.Entry<MahJavaLib.MahjongTile, Integer> entry : hand.getHand().entrySet()) {
            if (entry.getValue() == 2) {
                return true;
            }
        }

        // No pair, no nothing
        return false;
    }


    // Each hand is represented as a HashMap, where each Key is an existing type of tile
    // present in the hand, and each associated Value is the number of times that specific tile exists in the hand.
    // One minor concern: ALL VALUES NEED TO BE BIGGER OR EQUAL TO 1.
    // If a tile is removed from a hand, and it was the only tile of that type+content, then
    // the entry containing that tile should be fully removed from the hashmap, to save space. This means that no Value
    // should ever be 0. If a get() method call returns null, then that means that no tile of that specific type+content
    // exists.
    private final HashMap<MahJavaLib.MahjongTile, Integer> _hand = new HashMap<>();
    private MahjongHandInfo _info = new MahjongHandInfo();

    public HashMap<MahJavaLib.MahjongTile, Integer> getHand() {
        return this._hand;
    }

    private MahjongHand(MahjongHand hand){
        this._hand.putAll(hand.getHand());
        this._info = new MahjongHandInfo(hand._info);
    }

    public MahjongHand(ArrayList<MahJavaLib.MahjongTile> startingHand) throws IllegalArgumentException {
        if (startingHand.size() != 14) {
            throw new IllegalArgumentException("MahjongHand: Hand must contain 14 tiles, contains: " + startingHand.size());
        } else {
            for (MahJavaLib.MahjongTile tile : startingHand) {
                this.addTile(tile);
            }
            this._info = new MahjongHandInfo(this);
        }
    }

    public int getHandSize() {
        Integer count = 0;
        for (Map.Entry<MahJavaLib.MahjongTile, Integer> entry : this._hand.entrySet()) {
            count += entry.getValue();
        }
        return count;
    }

    public void addTile(MahJavaLib.MahjongTile tileToAdd) throws IllegalArgumentException {
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

    private void removeTile(MahJavaLib.MahjongTile tile) {
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

    public void discardTile(MahJavaLib.MahjongTile tileToDiscard) {
        // Right now the only thing this does is simply remove the tile from the internal HashMap.
        // However, discarding a tile can have other game-changing consequences, that will have to be recorded.
        // @TODO: encode those consequences somewhere in someway
        this.removeTile(tileToDiscard);
    }

    public boolean isWinningHand() {
        // In Mahjong, a hand is a winning hand if at least one of the following conditions applies:
        // - A hand has 4 Chow/Pung/Kongs + 1 pair
        // - A hand has 7 pairs
        // - It is a "Nine Gates" hand
        // - It is a "Thirteen Orphans" hand
        // Any other hand is a not-winning hand.

        ArrayList<Function<MahjongHand, Boolean>> winningConditions = new ArrayList<>(Arrays.asList(
                MahjongHand::isFourCombinationsPlusPair,
                MahjongHand::isSevenPairs,
                MahjongHand::isNineGates,
                MahjongHand::isThirteenOrphans));

        return winningConditions.stream().anyMatch((func) -> (func.apply(this)));
    }

//    public Integer calculateHandValue() {
//        return 0;
//    }

    @Override
    public String toString() {
        return "Your Hand is: " + _hand;
    }

    public static MahjongHand generateRandomHand() {
        Random random = new Random();
        MahjongTile tile = null;

        MahjongTile.TileType[] tileTypes = MahjongTile.TileType.values();
        MahjongTile.TileContent[] tileContents = MahjongTile.TileContent.values();

        ArrayList<MahjongTile> tiles = new ArrayList<>();

        for (int i = 0; i < 14; i++) {
            try {
                tile = new MahjongTile(tileTypes[random.nextInt(tileTypes.length)], tileContents[random.nextInt(tileContents.length)]);
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
            return new MahjongHand(tiles);
        } catch (IllegalArgumentException e) {
            // This will never happen because the loop is correctly limited so it will always generate a 14-tile hand
            // However, if it did happen, it would keep trying until it produced a valid hand
            return generateRandomHand();
        }
    }

};