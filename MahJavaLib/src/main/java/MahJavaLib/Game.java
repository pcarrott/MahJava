package MahJavaLib;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    public enum PlayerTurn {
        EAST,
        NORTH,
        WEST,
        SOUTH;

        public PlayerTurn next() {
            PlayerTurn[] turns = PlayerTurn.values();
            return turns[(this.ordinal() + 1) % turns.length];
        }
    }



    private Board _board = new Board();
    private PlayerTurn _playerTurn = PlayerTurn.EAST;
    private HashMap<PlayerTurn, Player> _players = new HashMap<>();

    public Game(ArrayList<Player> players) {
        PlayerTurn[] turns = PlayerTurn.values();
        for (int i = 0; i <= turns.length; ++i) {
            PlayerTurn seatWind = turns[i];
            _players.put(seatWind, players.get(i));
            getPlayer(seatWind).setSeatWind(seatWind);
        }
    }

    public Player nextPlayer() {
        this._playerTurn = this._playerTurn.next();
        return _players.get(_playerTurn);
    }

    public boolean isBoardWallEmpty() {
        return this._board.isWallEmpty();
    }

    public PlayerTurn getPlayerTurn() {
        return this._playerTurn;
    }

    public HashMap<PlayerTurn, Player> getPlayers() {
        return this._players;
    }

    public Player getPlayer(PlayerTurn seatWind) {
        return this._players.get(seatWind);
    }

    @Override
    public String toString() {
        return "MahjongGame{" +
                "_board=" + _board +
                ", _playerTurn=" + _playerTurn +
                ", _players=" + _players +
                '}';
    }
}
