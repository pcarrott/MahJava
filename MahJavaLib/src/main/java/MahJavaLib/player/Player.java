package MahJavaLib.player;

import MahJavaLib.game.OpenGame;
import MahJavaLib.game.PlayerTurn;
import MahJavaLib.tile.CombinationType;
import MahJavaLib.tile.Tile;
import MahJavaLib.tile.Combination;
import MahJavaLib.hand.Hand;
import MahJavaLib.tile.TileContent;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Player {

    private final String name;
    private OpenGame game;
    private Hand hand;
    private PlayerTurn seatWind = PlayerTurn.EAST;
    private final Profile profile;

    private Player(String name, Profile profile) {
        this.name = name;
        this.profile = profile;
    }

    static public Player EagerPlayer(String name) {
        return new Player(name, new Eager());
    }

    static public Player ComposedPlayer(String name) {
        return new Player(name, new Composed());
    }

    static public Player ConcealedPlayer(String name) {
        return new Player(name, new Concealed());
    }

    // @TODO: Make the profile decision random between games somehow
    static public Player MixedPlayer(String name) {
        return new Player(name, new Eager());
    }

    public void claimTile(Tile tileToAdd, Combination combination) {
        this.hand.claimTile(tileToAdd, combination);
    }

    public boolean hasTile(Tile tile, Integer n) {
        return this.hand.hasTile(tile, n);
    }

    public void addTile(Tile tile) {
        this.hand.addTile(tile);
    }

    public void removeTile(Tile tile) {
        this.hand.removeTile(tile);
    }

    public Tile chooseTileToDiscard() {
        // @TODO: actually implement decent logic for this, right now it is kinda random
        // @TODO: when we do this in the client, we also need to remove it from our hand, we don't do that right now
        // since the server needs to check if we actually have it, but in the future, the player's hands data structures
        // will be different between the server and the player.

        // Filter out all tiles with lowest count currently in hand
        Integer minCount = this.hand.getHand().values().stream().min(Integer::compare).get();
        Map<Tile, Integer> leftovers = this.hand.getHand().entrySet().stream()
                .filter(e -> e.getValue().equals(minCount))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // From those tiles, filter out the ones less likely to make a chow
        List<Tile> kek = leftovers.keySet().stream()
                .filter(e -> e.getPossibleChowCombinations().stream().noneMatch(chow -> {
                    int i = 0;
                    for (Tile t : chow)
                        if (leftovers.containsKey(t))
                            i++;

                    return i > 1;
                }))
                .collect(Collectors.toList());

        return kek.size() != 0 ? kek.get(0) : (Tile) leftovers.keySet().toArray()[0];

        // This is what should actually be done
        // return this.profile.chooseTileToDiscard(this.hand, this.game);
    }

    public boolean declareConcealedKong() {
        return this.profile.declareConcealedKong(this.hand, this.game);
    }

    public Optional<Combination> wantsDiscardedTile(Tile discardedTile) {
        return this.profile.wantsDiscardedTile(discardedTile, this.hand, this.game);
    }

    public boolean hasWinningHand() {
        return this.hand.isWinningHand();
    }

    public boolean isWinningTile(Tile discardedTile) {
        return this.hand.isWinningTile(discardedTile);
    }

    public Integer handValue(TileContent playerWind, TileContent roundWind, boolean selfDrawn, boolean firstPlay) {
        return this.hand.calculateHandValue(playerWind, roundWind, selfDrawn, firstPlay);
    }

    public String getName() {
        return this.name;
    }

    public void setGame(OpenGame game) {
        this.hand.setOpenHand(game.getOpenCombinations(this));
        this.hand.setConcealedKongs(game.getConcealedKongs(this));
        this.game = game;
    }

    public void setHand(Hand hand) throws IllegalArgumentException {
        // @TODO: check if hand is valid
        this.hand = hand;
    }

    public PlayerTurn getSeatWind() {
        return this.seatWind;
    }

    public void setSeatWind(PlayerTurn seatWind) {
        this.seatWind = seatWind;
    }

    @Override
    public String toString() {
        return "MahjongPlayer{" +
                "hand=" + hand +
                ", seatWind=" + seatWind +
                '}';
    }
}
