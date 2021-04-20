package MahJavaLib;

import MahJavaLib.exceptions.WallIsEmptyException;
import MahJavaLib.hand.Hand;

import java.util.*;
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


    private final MahJavaLib.Board _board = new MahJavaLib.Board();
    private PlayerTurn _playerTurn = PlayerTurn.EAST;
    private final Map<PlayerTurn, MahJavaLib.Player> _players = new HashMap<>();
    private final Map<MahJavaLib.Player, List<MahJavaLib.Tile>> _discardedTiles = new HashMap<>();

    public Game(List<MahJavaLib.Player> players) {
        assert players.size() == 4 : "List of players doesn't have the correct size";
        assert this._board.getWall().size() == (4*9*3 + 4*4 + 3*4);

        PlayerTurn[] turns = PlayerTurn.values();
        for (int i = 0; i < turns.length; ++i) {
            MahJavaLib.Player player = players.get(i);
            PlayerTurn seatWind = turns[i];

            player.setGame(this);
            player.setSeatWind(seatWind);
            List<MahJavaLib.Tile> hand = new ArrayList<>();
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
        boolean noSteal = true;
        Optional<PlayerTurn> winner = Optional.empty();
        int turn = 0;
        while (winner.isEmpty() && !this.isBoardWallEmpty()) {
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
            MahJavaLib.Player playerToPlay = this._players.get(this.getPlayerTurn());

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
            MahJavaLib.Tile tileToDiscard = playerToPlay.chooseTileToDiscard();
            // We need to check if this is even possible, meaning that we need to check
            // if the player has actually chosen a tile that it can discard.
            assert playerToPlay.hasTile(tileToDiscard, 1);
            playerToPlay.removeTile(tileToDiscard);

            System.out.println("\t\tPlayer discarded " + tileToDiscard);

            // Then we probe all other players, to see if anyone wants the discarded tile
            List<MahJavaLib.Player> otherPlayers = this.getPlayers().values().stream()
                    .filter((player -> !player.equals(playerToPlay)))
                    .collect(Collectors.toList());

            Map<MahJavaLib.Player, Optional<Combination>> wantsDiscardedTile = otherPlayers.stream()
                    .collect(Collectors.toMap(
                            player -> player,
                            player -> player.wantsDiscardedTile(tileToDiscard)));

            System.out.println("\t(Stealing phase)");
            noSteal = true;
            for (Map.Entry<MahJavaLib.Player, Optional<Combination>> entry : wantsDiscardedTile.entrySet()) {
                // If there is someone who wants it, then lets check if they can/should take it
                // @TODO: in a real game, you can only take the tile if you make a combination with it AND you have
                // the highest priority of every other requesting player. Need to check for that too.
                if (entry.getValue().isPresent()) {
                    Combination possibleCombination = entry.getValue().get();
                    Player requestingPlayer = entry.getKey();

                    // We start by giving them the tile, and then check if they actually have the combination they say
                    // they have
                    boolean hasCombination = true;
                    for (Map.Entry<Tile, Integer> combinationEntry : possibleCombination.getTiles().entrySet()) {
                            if (!requestingPlayer.hasTile(combinationEntry.getKey(), combinationEntry.getValue())) {
                                // They don't have the combination, filthy liar; we need to get back the tile from them
                                requestingPlayer.removeTile(tileToDiscard);
                                hasCombination = false;
                                break;
                            }
                    }

                    // They do have the combination, then we need to handle this
                    if (hasCombination) {
                        System.out.println("\t\tPlayer " + this._playerTurn + " has stolen " + tileToDiscard + " to make a " + possibleCombination);
                        // @TODO: open the combination
                        // The player that has stolen the tile gets to go next.
                        this._playerTurn = entry.getKey().getSeatWind();
                        noSteal = false;
                        break;
                    }
                }
            }


            // If no player has stolen the tile, then we need to actually record the discarding, and update the next
            // player.
            if (noSteal) {
                System.out.println("\tNo one stole anything what a ripoff");
                playerToPlay.addToDiscardPile(tileToDiscard);
                this._discardedTiles.get(playerToPlay).add(tileToDiscard);
                this._playerTurn = this._playerTurn.next();
            }


            List<PlayerTurn> winningPlayers = this._players.entrySet().stream()
                    .filter(entry -> entry.getValue().hasWinningHand())
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            if (winningPlayers.size() > 0) {
                winner = Optional.of(winningPlayers.get(0));
            }
            // This is only for testing purposes, in the end it can be deleted
            turn += 1;
            if (turn > 5) {
                winner = Optional.of(this._players.keySet().toArray(PlayerTurn[]::new)[0]);
            }
        }

        winner.ifPresent(player -> System.out.println("Winner is: " + player));
    }

    public boolean isBoardWallEmpty() {
        return this._board.isWallEmpty();
    }

    public PlayerTurn getPlayerTurn() {
        return this._playerTurn;
    }

    public Map<PlayerTurn, MahJavaLib.Player> getPlayers() {
        return this._players;
    }

    public MahJavaLib.Player getPlayer(PlayerTurn seatWind) {
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
