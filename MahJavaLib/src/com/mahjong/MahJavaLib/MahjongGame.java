package com.mahjong.MahJavaLib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MahjongGame {

    public enum PlayerTurn {
        EAST,
        NORTH,
        WEST,
        SOUTH
                {
                    @Override
                    public PlayerTurn next() {
                        return values()[0]; // restart
                    }
                };

        public PlayerTurn next() {
            // No bounds checking required here, because the last instance overrides
            return values()[ordinal() + 1];
        }
    }

    private MahjongBoard _board = new MahjongBoard();
    private PlayerTurn _playerTurn = PlayerTurn.EAST;
    private HashMap<PlayerTurn, MahjongPlayer> _players = new HashMap<>();

    public MahjongGame(ArrayList<MahjongPlayer> players) {
        int i = 0;
        for(PlayerTurn seatWind = PlayerTurn.EAST; i < 4; seatWind = seatWind.next(), i++) {
            _players.put(seatWind, players.get(i));
            getPlayer(seatWind).setSeatWind(seatWind);
        }
    }

    public MahjongPlayer nextPlayer() {
        this._playerTurn = this._playerTurn.next();
        return _players.get(_playerTurn);
    }

    public boolean isBoardWallEmpty() { return this._board.isWallEmpty(); }

    public PlayerTurn getPlayerTurn() { return this._playerTurn; }

    public HashMap<PlayerTurn, MahjongPlayer> getPlayers() { return this._players; }

    public MahjongPlayer getPlayer(PlayerTurn seatWind) { return this._players.get(seatWind); }

    @Override
    public String toString() {
        return "MahjongGame{" +
                "_board=" + _board +
                ", _playerTurn=" + _playerTurn +
                ", _players=" + _players +
                '}';
    }

    public static void main(String[] args) {
        ArrayList<MahjongPlayer> players = new ArrayList<>();
        players.add(new MahjongPlayer());
        players.add(new MahjongPlayer());
        players.add(new MahjongPlayer());
        players.add(new MahjongPlayer());

        MahjongGame game = new MahjongGame(players);

        System.out.println("Game: " + game + "; Players: " + game.getPlayers() + "; Player Turn: " + game.getPlayerTurn());
    }
}
