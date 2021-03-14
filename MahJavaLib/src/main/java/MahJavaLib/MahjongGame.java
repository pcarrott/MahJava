package MahJavaLib;

import MahJavaLib.MahjongBoard;

import java.util.ArrayList;
import java.util.HashMap;

public class MahjongGame {

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



    private MahjongBoard _board = new MahjongBoard();
    private PlayerTurn _playerTurn = PlayerTurn.EAST;
    private HashMap<PlayerTurn, MahJavaLib.MahjongPlayer> _players = new HashMap<>();

    public MahjongGame(ArrayList<MahJavaLib.MahjongPlayer> players) {
        PlayerTurn[] turns = PlayerTurn.values();
        for (int i = 0; i <= turns.length; ++i) {
            PlayerTurn seatWind = turns[i];
            _players.put(seatWind, players.get(i));
            getPlayer(seatWind).setSeatWind(seatWind);
        }
    }

    public MahJavaLib.MahjongPlayer nextPlayer() {
        this._playerTurn = this._playerTurn.next();
        return _players.get(_playerTurn);
    }

    public boolean isBoardWallEmpty() {
        return this._board.isWallEmpty();
    }

    public PlayerTurn getPlayerTurn() {
        return this._playerTurn;
    }

    public HashMap<PlayerTurn, MahJavaLib.MahjongPlayer> getPlayers() {
        return this._players;
    }

    public MahJavaLib.MahjongPlayer getPlayer(PlayerTurn seatWind) {
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

    public static void main(String[] args) {
        ArrayList<MahJavaLib.MahjongPlayer> players = new ArrayList<>();
        players.add(new MahJavaLib.MahjongPlayer());
        players.add(new MahJavaLib.MahjongPlayer());
        players.add(new MahJavaLib.MahjongPlayer());
        players.add(new MahJavaLib.MahjongPlayer());

        MahjongGame game = new MahjongGame(players);

        System.out.println("Game: " + game + "; Players: " + game.getPlayers() + "; Player Turn: " + game.getPlayerTurn());
    }
}
