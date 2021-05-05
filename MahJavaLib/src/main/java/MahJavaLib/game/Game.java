package MahJavaLib.game;

import MahJavaLib.tile.Tile;
import MahJavaLib.tile.Combination;
import MahJavaLib.exceptions.WallIsEmptyException;
import MahJavaLib.hand.Hand;

import java.util.*;
import java.util.stream.Collectors;

public class Game {

    private final Board board = new Board();
    private PlayerTurn playerTurn = PlayerTurn.EAST;
    private final Map<PlayerTurn, Player> players = new HashMap<>();
    private final Map<Player, List<Tile>> discardedTiles = new HashMap<>();

    public Game(List<Player> players) {
        assert players.size() == 4 : "List of players doesn't have the correct size";
        assert this.board.getWall().size() == (4*9*3 + 4*4 + 3*4);

        PlayerTurn[] turns = PlayerTurn.values();
        for (int i = 0; i < turns.length; ++i) {
            Player player = players.get(i);
            PlayerTurn seatWind = turns[i];

            player.setGame(this);
            player.setSeatWind(seatWind);
            List<Tile> hand = new ArrayList<>();
            for (int j = 0; j < 13; ++j) {
                try {
                    hand.add(this.board.removeFirstTileFromWall());
                } catch (WallIsEmptyException ignored) {
                }
            }
            player.setHand(new Hand(hand));

            this.players.put(seatWind, player);
            this.discardedTiles.put(player, new ArrayList<>());
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
            Player playerToPlay = this.players.get(this.getPlayerTurn());

            if (noSteal) {
                try {
                    playerToPlay.addTile(this.board.removeFirstTileFromWall());
                } catch (WallIsEmptyException e) {
                    // This should really never happen, if it happens something went wrong, might as well
                    // abort everything
                    e.printStackTrace();
                    System.exit(-1);
                }
            }

            System.out.println("\t(Discard Phase) Player to Play: " + playerToPlay.getSeatWind());
            // Query the player on which tile to discard
            Tile tileToDiscard = playerToPlay.chooseTileToDiscard();
            // We need to check if this is even possible, meaning that we need to check
            // if the player has actually chosen a tile that it can discard.
            assert playerToPlay.hasTile(tileToDiscard, 1);
            playerToPlay.removeTile(tileToDiscard);

            System.out.println("\t\tPlayer discarded " + tileToDiscard);

            // Then we probe all other players, to see if anyone wants the discarded tile
            List<Player> otherPlayers = this.getPlayers().values().stream()
                    .filter((player -> !player.equals(playerToPlay)))
                    .collect(Collectors.toList());

            Map<Player, Optional<Combination>> wantsDiscardedTile = otherPlayers.stream()
                    .collect(Collectors.toMap(
                            player -> player,
                            player -> player.wantsDiscardedTile(tileToDiscard)));

            System.out.println("\t(Stealing phase)");
            noSteal = true;
            for (Map.Entry<Player, Optional<Combination>> entry : wantsDiscardedTile.entrySet()) {
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
                        System.out.println("\t\tPlayer " + this.playerTurn + " has stolen " + tileToDiscard + " to make a " + possibleCombination);
                        // @TODO: open the combination
                        // The player that has stolen the tile gets to go next.
                        this.playerTurn = entry.getKey().getSeatWind();
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
                this.discardedTiles.get(playerToPlay).add(tileToDiscard);
                this.playerTurn = this.playerTurn.next();
            }


            List<PlayerTurn> winningPlayers = this.players.entrySet().stream()
                    .filter(entry -> entry.getValue().hasWinningHand())
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            if (winningPlayers.size() > 0) {
                winner = Optional.of(winningPlayers.get(0));
            }
            // This is only for testing purposes, in the end it can be deleted
            turn += 1;
            if (turn > 5) {
                winner = Optional.of(this.players.keySet().toArray(PlayerTurn[]::new)[0]);
            }
        }

        winner.ifPresent(player -> System.out.println("Winner is: " + player));
    }

    public boolean isBoardWallEmpty() {
        return this.board.isWallEmpty();
    }

    public PlayerTurn getPlayerTurn() {
        return this.playerTurn;
    }

    public Map<PlayerTurn, Player> getPlayers() {
        return this.players;
    }

    public Player getPlayer(PlayerTurn seatWind) {
        return this.players.get(seatWind);
    }

    @Override
    public String toString() {
        return "MahjongGame{" +
                "board=" + board +
                ", playerTurn=" + playerTurn +
                ", players=" + players +
                '}';
    }
}
