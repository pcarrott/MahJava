package MahJavaLib.game;

import MahJavaLib.tile.Tile;
import MahJavaLib.tile.Combination;
import MahJavaLib.hand.Hand;

import java.util.ArrayList;
import java.util.Optional;

public class Player {

    private Game game;
    private Hand hand;
    private PlayerTurn seatWind = PlayerTurn.EAST;
    private final ArrayList<Tile> discardPile = new ArrayList<>();


    public Player() {
        // The player constructor is empty, since it is supposed to be initialized by the game it is currently in
    }

    public void setHand(Hand hand) throws IllegalArgumentException {
        // @TODO: check if hand is valid
        this.hand = hand;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Tile chooseTileToDiscard() {
        // @TODO: actually implement decent logic for this, right now it is kinda random
        // @TODO: when we do this in the client, we also need to remove it from our hand, we don't do that right now
        // since the server needs to check if we actually have it, but in the future, the player's hands data structures
        // will be different between the server and the player.
        return (Tile) this.hand.getHand().keySet().toArray()[0];
    }

    public boolean hasTile(Tile tile, Integer n) {
        return this.hand.hasTile(tile, n);
    }

    public void addToDiscardPile(Tile tile) {
        this.discardPile.add(tile);
    }

    public void addTile(Tile tile) {
        this.hand.addTile(tile);
    }

    public PlayerTurn getSeatWind() {
        return this.seatWind;
    }

    public void setSeatWind(PlayerTurn seatWind) {
        this.seatWind = seatWind;
    }

    public Optional<Combination> wantsDiscardedTile(Tile discardedTile) {
        // @TODO: implement logic for this, also change return values since we should inform the Game on the type of
        // combination we want to achieve with the discarded tile (both to make us eligible to get it, and to mark it
        // as Open)
        return Optional.empty();
    }

    public void removeTile(Tile tile) {
        this.hand.discardTile(tile);
    }

    public boolean hasWinningHand() {return this.hand.isWinningHand();}

    @Override
    public String toString() {
        return "MahjongPlayer{" +
                "hand=" + hand +
                ", seatWind=" + seatWind +
                ", discardPile=" + discardPile +
                '}';
    }
}
