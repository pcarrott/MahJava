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

    //private MahjongGame _game;
    private Hand _hand;
    private Game.PlayerTurn _seatWind = Game.PlayerTurn.EAST;
    private ArrayList<Tile> _discardPile = new ArrayList<>();
    private Tile _discardedTile;

    public Player() throws IllegalArgumentException {
    }

    /*public MahjongPlayer(MahjongGame game, MahjongHand hand) throws IllegalArgumentException {
        this._game = game;
        //if(hand.isValidHand())
        this._hand = hand;
        //else throw IllegalArgumentException
    }*/

    public void discardTile(Tile tile) {
        //if(!MahjongHand.containsTile(tile)) throws IllegalArgumentException
        //MahjongHand.discardTile(tile)
        this._discardPile.add(tile);
    }

    public void addTile(Tile tile, Optional<CombinationType> combinationType, ArrayList<Tile> combination) {
        //this._hand.addTile(tile);
    }

    public void receiveDiscardedTile(Tile tile) {
        this._discardedTile = tile;
    }

    public boolean drawTileRequest(Optional<CombinationType> combinationType, ArrayList<Tile> combination) {
        //return this._game.isDrawTileRequestValid(combinationType, combination);
        return true;
    }

    public void drawTile() {
        //A way to generate the possible combinations with the discardedTile
        //Present combination options to client
        //Client selects combination to send to server
        //if (drawTileAttempt(combinationType, combination)) {addTile(this._discardedTile); this._discardedTile = null;}
    }

    public Game.PlayerTurn getSeatWind() {
        return this._seatWind;
    }

    public void setSeatWind(Game.PlayerTurn _seatWind) {
        this._seatWind = _seatWind;
    }

    @Override
    public String toString() {
        return "MahjongPlayer{" +
                "_hand=" + _hand +
                ", _seatWind=" + _seatWind +
                ", _discardPile=" + _discardPile +
                ", _discardedTile=" + _discardedTile +
                '}';
    }
}
