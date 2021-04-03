package MahJavaLib;

import MahJavaLib.exceptions.WallIsEmptyException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Game {

    public enum PlayerTurn {
        EAST,
        SOUTH,
        WEST,
        NORTH;

        public PlayerTurn next() {
            PlayerTurn[] turns = PlayerTurn.values();
            return turns[(this.ordinal() + 1) % turns.length];
        }
    }


    private final Board _board = new Board();
    private PlayerTurn _playerTurn = PlayerTurn.EAST;
    private final Map<PlayerTurn, Player> _players = new HashMap<>();
    private final Map<Player, List<Tile>> _discardedTiles = new HashMap<>();

    public Game(List<Player> players) {
        assert players.size() == 4 : "List of players doesn't have the correct size";
        assert this._board.getWall().size() == (4*9*3 + 4*4 + 3*4);

        PlayerTurn[] turns = PlayerTurn.values();
        for (int i = 0; i < turns.length; ++i) {
            Player player = players.get(i);
            PlayerTurn seatWind = turns[i];

            player.setGame(this);
            player.setSeatWind(seatWind);
            List<Tile> hand = new ArrayList<>();
            for (int j = 0; j < 13; ++j) {
                try {
                    hand.add(this._board.removeFirstTileFromWall());
                } catch (WallIsEmptyException ignored) {
                }
            }
            player.setHand(new Hand(hand));

            this._players.put(seatWind, player);
            this._discardedTiles.put(player, new ArrayList<>());
        }
    }

    public void gameLoop() {
        boolean winnerExists = false;
        boolean noSteal = true;
        int turn = 0;
        while (!winnerExists && !this.isBoardWallEmpty()) {
            System.out.println("Turn " + turn);
            // A Mahjong Turn is made up of two phases:
            // - If no piece was "stolen" the round before, then this turn's player needs to be given a tile from the
            // Wall. This turn's player then needs to choose one tile to discard from their hand.
            // This is the discard phase.
            // - After choosing, every other player can try to "steal" the discarded tile (if they meet the
            // requirements). The player with the highest priority receives the discarded tile, and does not need to
            // draw a tile from the Wall. It is also the next player to play. This is the "stealing" phase.
            // If no player has stolen the tile, then this tile is effectively discarded: it is added to the discard
            // pile of the turn's player, and the turn is passed onto the next player.
            Player playerToPlay = this._players.get(this.getPlayerTurn());

            if (noSteal) {
                try {
                    playerToPlay.addTile(this._board.removeFirstTileFromWall());
                } catch (WallIsEmptyException e) {
                    // This should really never happen, if it happens something went wrong, might as well
                    // abort everything
                    e.printStackTrace();
                    System.exit(-1);
                }
            }

            System.out.println("\t(Discard Phase) Player to Play: " + playerToPlay.getSeatWind());
            // Query the player on which tile to discard
            // @TODO: check if this is even possible, meaning that we need to check if the player has actually chosen
            // a tile that it can discard.
            Tile tileToDiscard = playerToPlay.chooseTileToDiscard();

            System.out.println("\t\tPlayer discarded " + tileToDiscard);

            // Then we probe all other players, to see if anyone wants the discarded tile
            List<Player> otherPlayers = this.getPlayers().values().stream()
                    .filter((player -> !player.equals(playerToPlay)))
                    .collect(Collectors.toList());

            Map<Player, Boolean> wantsDiscardedTile = otherPlayers.stream()
                    .collect(Collectors.toMap(
                            player -> player,
                            player -> player.wantsDiscardedTile(tileToDiscard)));

            System.out.println("\t(Stealing phase)");
            noSteal = true;
            for (Map.Entry<Player, Boolean> entry : wantsDiscardedTile.entrySet()) {
                // If there is someone who wants it, then lets check if they can/should take it
                // @TODO: in a real game, you can only take the tile if you make a combination with it AND you have
                // the highest priority of every other requesting player. Need to check for that too.
                if (entry.getValue()) {
                    entry.getKey().addTile(tileToDiscard);
                    System.out.println("\t\tPlayer " + this._playerTurn + " has stolen " + tileToDiscard);
                    // The player that has stolen the tile gets to go next.
                    this._playerTurn = entry.getKey().getSeatWind();
                    noSteal = false;
                    break;
                }
            }

            // If no player has stolen the tile, then we need to actually record the discarding, and update the next
            // player.
            if (noSteal) {
                System.out.println("\tNo one stole anything what a ripoff");
                playerToPlay.addToDiscardPile(tileToDiscard);
                List<Tile> discardedTiles = this._discardedTiles.get(playerToPlay);
                discardedTiles.add(tileToDiscard);
                this._discardedTiles.put(playerToPlay, discardedTiles);
                this._playerTurn = this._playerTurn.next();
            }

            // This is only for testing purposes, in the end it can be deleted
            turn += 1;
            if (turn > 5) {
                winnerExists = true;
            }
    }
}

    public boolean isBoardWallEmpty() {
        return this._board.isWallEmpty();
    }

    public PlayerTurn getPlayerTurn() {
        return this._playerTurn;
    }

    public Map<PlayerTurn, Player> getPlayers() {
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
