package MahJava.player;

import MahJava.game.OpenGame;
import MahJava.game.PlayerTurn;
import MahJava.hand.Hand;
import MahJava.tile.Combination;
import MahJava.tile.Tile;
import MahJava.tile.TileContent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    static public Player MixedPlayer(String name) {
        Mixed mixed = new Mixed();
        Player player = new Player(name, mixed);
        mixed.setPlayer(player);
        return player;
    }

    public void seeClaimedTile(@Nullable Player discarded, Tile tile, List<Player> claimed) {
        this.profile.seeClaimedTile(discarded, tile, claimed);
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
        return this.profile.chooseTileToDiscard(this.hand, this.game);
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
