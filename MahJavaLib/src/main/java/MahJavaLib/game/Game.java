package MahJavaLib.game;

import MahJavaLib.exceptions.NegativeScoreException;
import MahJavaLib.player.Player;
import MahJavaLib.tile.CombinationType;
import MahJavaLib.tile.Tile;
import MahJavaLib.tile.Combination;
import MahJavaLib.exceptions.WallIsEmptyException;
import MahJavaLib.hand.Hand;
import MahJavaLib.tile.TileContent;

import java.util.*;
import java.util.stream.Collectors;

public class Game {

    static private final int START_SCORE = 500;

    private final Board board = new Board();
    private PlayerTurn playerTurn = PlayerTurn.EAST;
    private PlayerTurn roundTurn = PlayerTurn.EAST;
    private final Map<PlayerTurn, Player> players = new HashMap<>();
    private final Map<Player, Integer> scores = new HashMap<>();
    private final OpenGame open;

    public Game(List<Player> players) {
        assert players.size() == 4 : "List of players doesn't have the correct size";
        assert this.board.getWall().size() == (4*9*3 + 4*4 + 3*4);

        this.open = new OpenGame(players);

        PlayerTurn[] turns = PlayerTurn.values();
        for (int i = 0; i < turns.length; ++i) {
            Player player = players.get(i);
            PlayerTurn seatWind = turns[i];

            List<Tile> hand = new ArrayList<>();
            for (int j = 0; j < 13; ++j) {
                try {
                    hand.add(this.board.removeFirstTileFromWall());
                } catch (WallIsEmptyException ignored) {
                }
            }
            player.setHand(new Hand(hand));

            player.setGame(this.open);
            player.setSeatWind(seatWind);
            this.players.put(seatWind, player);

            this.scores.put(player, START_SCORE);
        }
    }

    private void resetGame() {
        this.board.reset();
        this.open.reset();
        this.playerTurn = PlayerTurn.EAST;

        PlayerTurn turn = PlayerTurn.EAST;
        for (int i = 0; i < 4; i++) {
            List<Tile> hand = new ArrayList<>();
            for (int j = 0; j < 13; ++j) {
                try {
                    hand.add(this.board.removeFirstTileFromWall());
                } catch (WallIsEmptyException ignored) {
                }
            }
            this.players.get(turn).setHand(new Hand(hand));
            turn = turn.next();
        }
    }

    public void gameLoop() {
        try {
            int rounds = 0;
            while (rounds < 4) {
                System.out.println("Round " + this.roundTurn);
                int rotations = 0;
                while (rotations < 4) {
                    System.out.println("\tRotations: " + rotations);
                    if (this.singleGameLoop()) {
                        PlayerTurn turn = PlayerTurn.EAST;
                        Player player = this.players.get(turn);

                        for (int i = 0; i < 4; i++) {
                            PlayerTurn nextTurn = turn.next();
                            Player nextPlayer = this.players.get(nextTurn);

                            this.players.put(nextTurn, player);
                            player.setSeatWind(nextTurn);

                            turn = nextTurn;
                            player = nextPlayer;
                        }
                        rotations++;
                    }
                    this.resetGame();
                }
                this.roundTurn = this.roundTurn.next();
                rounds++;
            }

        } catch (NegativeScoreException e) {
            System.out.println("Game finished abruptly because of negative score");
        }

        System.out.println("Game finished");

        Player winner = null;
        int maxScore = 0;

        for (Map.Entry<Player, Integer> e : this.scores.entrySet()) {
            if (e.getValue() > maxScore) {
                winner = e.getKey();
                maxScore = e.getValue();
            }
        }

        System.out.println(winner.getName() + " is the winner with " + maxScore + " points");

        System.out.println("All scores:");
        this.scores.forEach((k, v) ->
                System.out.println(k.getName() + " with " + v + " points"));
    }

    public boolean singleGameLoop() throws NegativeScoreException {
        boolean noSteal = true;
        List<PlayerTurn> winners = new ArrayList<>();
        while (winners.isEmpty() && !this.isBoardWallEmpty()) {
            if (this.scores.values().stream().anyMatch(s -> s < 0))
                throw new NegativeScoreException();

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

            List<Player> otherPlayers = this.getPlayers().values().stream()
                    .filter((player -> !player.equals(playerToPlay)))
                    .collect(Collectors.toList());

            if (noSteal) {
                try {
                    playerToPlay.addTile(this.board.removeFirstTileFromWall());
                    if (playerToPlay.hasWinningHand()) {
                        this.setWinner(playerToPlay, 1, winners, otherPlayers, Optional.empty());
                        continue;
                    }

                } catch (WallIsEmptyException e) {
                    // This should really never happen, if it happens something went wrong, might as well
                    // abort everything
                    e.printStackTrace();
                    System.exit(-1);
                }
            }

            //System.out.println("\t(Discard Phase) Player to Play: " + playerToPlay.getSeatWind());
            // Query the player on which tile to discard
            Tile tileToDiscard = playerToPlay.chooseTileToDiscard();
            // We need to check if this is even possible, meaning that we need to check
            // if the player has actually chosen a tile that it can discard.
            assert playerToPlay.hasTile(tileToDiscard, 1);
            playerToPlay.removeTile(tileToDiscard);

            //System.out.println("\t\tPlayer discarded " + tileToDiscard);
            // Then we probe all other players, to see if anyone wants the discarded tile
            Map<Player, Optional<Combination>> wantsDiscardedTile = otherPlayers.stream()
                    .collect(Collectors.toMap(
                            player -> player,
                            player -> player.wantsDiscardedTile(tileToDiscard)));

            long wantsKong = wantsDiscardedTile.entrySet().stream()
                    .filter(e -> e.getValue().isPresent() &&
                            e.getValue().get().getCombinationType() == CombinationType.KONG)
                    .count();

            List<Player> winningPlayers = otherPlayers.stream()
                    .filter(player -> player.isWinningTile(tileToDiscard))
                    .collect(Collectors.toList());

            List<Player> losingPlayers = this.players.values().stream()
                    .filter(player -> !player.isWinningTile(tileToDiscard))
                    .collect(Collectors.toList());

            //System.out.println("\t(Stealing phase)");
            if (winningPlayers.size() > 0) {
                winningPlayers.forEach(p -> p.addTile(tileToDiscard));
                winningPlayers.forEach(p -> this.setWinner(
                        p, wantsKong > 0 ? 1 : 0,
                        winners, losingPlayers,
                        Optional.of(this.getPlayerTurn())
                ));
                continue;
            }

            Player playerWithCombination = null;
            Combination playerCombination = null;
            noSteal = true;
            for (Map.Entry<Player, Optional<Combination>> entry : wantsDiscardedTile.entrySet()) {
                // If there is someone who wants it, then lets check if they can/should take it
                // In a real game, you can only take the tile if you make a combination with it AND you have
                // the highest priority of every other requesting player. Need to check for that too.
                if (entry.getValue().isPresent()) {
                    Combination possibleCombination = entry.getValue().get();
                    Player requestingPlayer = entry.getKey();

                    // We start by giving them the tile, and then check if they actually have the combination they say
                    // they have
                    requestingPlayer.addTile(tileToDiscard);
                    boolean hasCombination = true;
                    for (Map.Entry<Tile, Integer> combinationEntry : possibleCombination.getTiles().entrySet()) {
                            if (!requestingPlayer.hasTile(combinationEntry.getKey(), combinationEntry.getValue())) {
                                // They don't have the combination, filthy liar; we need to get back the tile from them
                                hasCombination = false;
                                break;
                            }
                    }

                    // Since claiming the tile involves more operations than just adding the tile, it must be removed
                    // now, regardless of it having a combination or not. If there is a combination, then the tile will
                    // be added further ahead.
                    requestingPlayer.removeTile(tileToDiscard);

                    // They do have the combination, then we need to handle this
                    if (hasCombination) {
                        if (possibleCombination.getCombinationType() == CombinationType.PUNG) {
                            playerWithCombination = requestingPlayer;
                            playerCombination = possibleCombination;
                            break;

                        } else if (possibleCombination.getCombinationType() == CombinationType.KONG) {
                            playerWithCombination = requestingPlayer;
                            playerCombination = possibleCombination;
                            break;

                        } else if (possibleCombination.getCombinationType() == CombinationType.CHOW && requestingPlayer.getSeatWind() == this.playerTurn.next()) {
                            playerWithCombination = requestingPlayer;
                            playerCombination = possibleCombination;
                        }
                    }
                }
            }

            if (playerWithCombination != null) {
                //System.out.println("\t\tPlayer " + this.playerTurn + " has stolen " + tileToDiscard + " to make a " + possibleCombination);
                // We claim the tile associated with the correct combination
                playerWithCombination.claimTile(tileToDiscard, playerCombination);
                // The player that has stolen the tile gets to go next.
                this.playerTurn = playerWithCombination.getSeatWind();
                noSteal = false;
            }

            // If no player has stolen the tile, then we need to actually record the discarding, and update the next
            // player.
            if (noSteal) {
                //System.out.println("\tNo one stole anything what a ripoff");
                this.open.addDiscardedTile(playerToPlay, tileToDiscard);
                this.playerTurn = this.playerTurn.next();
            }
        }

        if (winners.isEmpty())
            System.out.println("No winners PepeHands");

        winners.forEach(p -> System.out.println(this.players.get(p).getName() + " won!"));

        System.out.println("All scores:");
        this.scores.forEach((k, v) ->
                System.out.println(k.getSeatWind() + ": " + k.getName() + " with " + v + " points"));

        return !winners.isEmpty() && !winners.contains(PlayerTurn.EAST);
    }

    public boolean isBoardWallEmpty() {
        return this.board.isWallEmpty();
    }

    public PlayerTurn getPlayerTurn() {
        return this.playerTurn;
    }

    public PlayerTurn getRoundTurn() {
        return this.roundTurn;
    }

    public Map<PlayerTurn, Player> getPlayers() {
        return this.players;
    }

    public Player getPlayer(PlayerTurn seatWind) {
        return this.players.get(seatWind);
    }

    private TileContent turnToTile(PlayerTurn turn) {
        TileContent t;
        switch (turn) {
            case EAST: t = TileContent.EAST; break;
            case NORTH: t = TileContent.NORTH; break;
            case WEST: t = TileContent.WEST; break;
            case SOUTH: t = TileContent.SOUTH; break;
            default:
                // This will never happen
                throw new IllegalStateException("Unexpected value: " + turn);
        }
        return t;
    }

    private Integer points(Integer fan) {
        return fan > 9 ? 64 :
               fan > 6 ? 32 :
               fan > 3 ? 16 :
               fan == 0 ? 1 :
               (int) Math.pow(2, fan);
    }

    private void setWinner(
            Player winner, int bonus,
            List<PlayerTurn> winners, List<Player> losers,
            Optional<PlayerTurn> discarded) {

        TileContent playerWind = this.turnToTile(winner.getSeatWind());
        TileContent roundWind = this.turnToTile(this.getRoundTurn());

        // 1 Extra Fan if it's the last self-drawn or the last discarded tile
        int fan = winner.handValue(playerWind, roundWind) + (this.isBoardWallEmpty() ? bonus + 1 : bonus);

        int points = this.points(fan);

        int score;
        if (discarded.isPresent()) {
            losers.forEach(p -> this.scores.computeIfPresent(p, (k, s) -> s - points));
            this.scores.computeIfPresent(this.players.get(discarded.get()), (k, s) -> s - points);
            score = (losers.size() + 1) * points;
        } else {
            losers.forEach(p -> this.scores.computeIfPresent(p, (k, s) -> s - 2 * points));
            score = (losers.size()) * 2 * points;
        }

        System.out.println("Fan: " + fan);
        System.out.println("Points: " + points);
        System.out.println("Score: " + score);

        this.scores.computeIfPresent(winner, (p, s) -> s + score);
        winners.add(winner.getSeatWind());
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
