package MahJavaLib;

import MahJavaLib.MahjongGame;
import MahJavaLib.MahjongHand;

import java.util.ArrayList;
import java.util.Optional;

public class MahjongPlayer {

    public enum CombinationType {
        CHOW,
        PUNG,
        KONG
    }

    //private MahjongGame _game;
    private MahjongHand _hand;
    private MahjongGame.PlayerTurn _seatWind = MahjongGame.PlayerTurn.EAST;
    private ArrayList<MahJavaLib.MahjongTile> _discardPile = new ArrayList<>();
    private MahJavaLib.MahjongTile _discardedTile;

    public MahjongPlayer() throws IllegalArgumentException {
    }

    /*public MahjongPlayer(MahjongGame game, MahjongHand hand) throws IllegalArgumentException {
        this._game = game;
        //if(hand.isValidHand())
        this._hand = hand;
        //else throw IllegalArgumentException
    }*/

    public void discardTile(MahJavaLib.MahjongTile tile) {
        //if(!MahjongHand.containsTile(tile)) throws IllegalArgumentException
        //MahjongHand.discardTile(tile)
        this._discardPile.add(tile);
    }

    public void addTile(MahJavaLib.MahjongTile tile, Optional<CombinationType> combinationType, ArrayList<MahJavaLib.MahjongTile> combination) {
        //this._hand.addTile(tile);
    }

    public void receiveDiscardedTile(MahJavaLib.MahjongTile tile) {
        this._discardedTile = tile;
    }

    public boolean drawTileRequest(Optional<CombinationType> combinationType, ArrayList<MahJavaLib.MahjongTile> combination) {
        //return this._game.isDrawTileRequestValid(combinationType, combination);
        return true;
    }

    public void drawTile() {
        //A way to generate the possible combinations with the discardedTile
        //Present combination options to client
        //Client selects combination to send to server
        //if (drawTileAttempt(combinationType, combination)) {addTile(this._discardedTile); this._discardedTile = null;}
    }

    public MahjongGame.PlayerTurn getSeatWind() {
        return this._seatWind;
    }

    public void setSeatWind(MahjongGame.PlayerTurn _seatWind) {
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
