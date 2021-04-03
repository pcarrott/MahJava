package MahJavaLib;

import java.util.ArrayList;
import java.util.Optional;

public class Player {

    public enum CombinationType {
        CHOW,
        PAIR,
        PUNG,
        KONG
    }

    private Game _game;
    private Hand _hand;
    private Game.PlayerTurn _seatWind = Game.PlayerTurn.EAST;
    private ArrayList<Tile> _discardPile = new ArrayList<>();


    public Player() {
        // The player constructor is empty, since it is supposed to be initialized by the game it is currently in
    }

    public void setHand(Hand hand) throws IllegalArgumentException {
        // @TODO: check if hand is valid
        this._hand = hand;
    }

    public void setGame(Game game) {
        this._game = game;
    }

    public Tile chooseTileToDiscard() {
        // @TODO: actually implement decent logic for this, right now it is kinda random
        Tile randomTile = (Tile)this._hand.getHand().keySet().toArray()[0];
        this._hand.removeTile(randomTile);
        return randomTile;
    }

    public void addToDiscardPile(Tile tile) {
        this._discardPile.add(tile);
    }

    public void addTile(Tile tile) {
        this._hand.addTile(tile);
    }

    public Game.PlayerTurn getSeatWind() {
        return this._seatWind;
    }

    public void setSeatWind(Game.PlayerTurn _seatWind) {
        this._seatWind = _seatWind;
    }

    public boolean wantsDiscardedTile(Tile discardedTile) {
        // @TODO: implement logic for this, also change return values since we should inform the Game on the type of
        // combination we want to achieve with the discarded tile (both to make us eligible to get it, and to mark it
        // as Open)
        return false;
    }

    @Override
    public String toString() {
        return "MahjongPlayer{" +
                "_hand=" + _hand +
                ", _seatWind=" + _seatWind +
                ", _discardPile=" + _discardPile +
                '}';
    }
}
