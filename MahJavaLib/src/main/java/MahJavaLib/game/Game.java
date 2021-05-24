package MahJavaLib.game;

import MahJavaLib.exceptions.NegativeScoreException;
import MahJavaLib.player.Player;
import MahJavaLib.tile.CombinationType;
import MahJavaLib.tile.Tile;
import MahJavaLib.tile.Combination;
import MahJavaLib.exceptions.WallIsEmptyException;
import MahJavaLib.hand.Hand;
import MahJavaLib.tile.TileContent;

import javax.annotation.Nullable;
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
                    hand.add(this.board.removeTileFromLiveWall());
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
                    hand.add(this.board.removeTileFromLiveWall());
                } catch (WallIsEmptyException ignored) {
                }
            }
            this.players.get(turn).setHand(new Hand(hand));
            turn = turn.next();
        }
    }

    public void gameLoop() {
        try {
            // A full game has 4 rounds: one round per wind.
            for (int round = 0; round < 4; round++) {
                System.out.println("[ROUND " + this.roundTurn + "]");
                // In each round, every player should have the chance to be the East Wind, at least once.
                // The players switch winds cyclically, so at least 4 rotations are needed to advance to next round.
                int rotations = 0;
                while (rotations < 4) {
                    if (this.singleGameLoop()) {
                        // We have a winner and it's not the East Wind, so we rotate positions.
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
            }
            System.out.println("\nGame finished!");

        } catch (NegativeScoreException e) {
            System.out.println("\nGame finished abruptly because of negative score...");
        }

        int maxScore = 0;
        for (Integer score : this.scores.values())
            if (score > maxScore)
                maxScore = score;

        System.out.println("Final scores:");
        int finalMaxScore = maxScore;
        this.scores.forEach((k, v) -> System.out.println(
                (v == finalMaxScore ? "\tW - " : "\tL - ") + k.getName() + " with " + v + " points"
        ));
    }

    public boolean singleGameLoop() throws NegativeScoreException {
        boolean firstPlay = true;
        boolean noSteal = true;
        boolean claimedKong = false;
        List<PlayerTurn> winners = new ArrayList<>();
        while (!this.isBoardWallEmpty()) {
            // The whole game should stop if any player reaches a negative score
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

            try {
                if (noSteal) {
                    Tile drawnTile = this.board.removeTileFromLiveWall();
                    playerToPlay.addTile(drawnTile);
                    if (playerToPlay.hasWinningHand()) {
                        // Player won by self-drawn and all other players will have to pay up.
                        // Winning by self-drawn always grants a bonus point to the winner.
                        this.setWinner(playerToPlay, 1, winners, otherPlayers, null, firstPlay);
                        playerToPlay.seeClaimedTile(null, drawnTile, Collections.singletonList(playerToPlay));
                        break;
                    }
                } else if (claimedKong) {
                    // A tile must be drawn from the Dead Wall to compensate for the declared kong
                    playerToPlay.addTile(this.board.removeTileFromDeadWall());
                    if (playerToPlay.hasWinningHand()) {
                        // Player won by self-drawn and all other players will have to pay up.
                        // Winning by self-drawn always grants a bonus point to the winner.
                        this.setWinner(playerToPlay, 1, winners, otherPlayers, null, false);
                        break;
                    }
                }
            } catch (WallIsEmptyException e) {
                // This should really never happen, if it happens something went wrong, might as well
                // abort everything
                e.printStackTrace();
                System.exit(-1);
            }

            // Before discarding a tile, we must first check if the player wants to declare a concealed kong.
            // If so, we simply claim the tile and continue to the next iteration, where the player will draw a tile
            // from the Dead Wall.
            if (playerToPlay.declareConcealedKong()) {
                claimedKong = true;
                continue;
            }

            //System.out.println("\t(Discard Phase) Player to Play: " + playerToPlay.getSeatWind());
            // Query the player on which tile to discard
            Tile tileToDiscard = playerToPlay.chooseTileToDiscard();
            // We need to check if this is even possible, meaning that we need to check
            // if the player has actually chosen a tile that it can discard.
            assert playerToPlay.hasTile(tileToDiscard, 1);
            playerToPlay.removeTile(tileToDiscard);

            //System.out.println("\t\tPlayer discarded " + tileToDiscard);
            //System.out.println("\t(Stealing phase)");
            // Then we probe all other players, to see if anyone wants the discarded tile for a combination
            Map<Player, Optional<Combination>> wantsDiscardedTile = otherPlayers.stream()
                    .collect(Collectors.toMap(
                            player -> player,
                            player -> player.wantsDiscardedTile(tileToDiscard)));

            // The we probe all other players, to see if anyone can win with the discarded tile. Multiple winners are a
            // possibility.
            // Note: A winning hand may be completed from a tile that does not generate a new combination. For example,
            //       a Thirteen Orphans.
            List<Player> winningPlayers = otherPlayers.stream()
                    .filter(player -> player.isWinningTile(tileToDiscard))
                    .collect(Collectors.toList());

            // Likewise, we get the players that will pay if there is at least one winner.
            List<Player> losingPlayers = this.players.values().stream()
                    .filter(player -> !player.isWinningTile(tileToDiscard))
                    .collect(Collectors.toList());

            // If a player has winning hand and stops another player from snatching a Kong, then we will be rewarded.
            long wantsKong = wantsDiscardedTile.entrySet().stream()
                    .filter(e -> e.getValue().isPresent() &&
                            e.getValue().get().getCombinationType() == CombinationType.KONG)
                    .count();

            // We first check if we have winners and conclude the game if so.
            if (winningPlayers.size() > 0) {
                winningPlayers.forEach(p -> p.addTile(tileToDiscard));
                boolean finalFirstPlay = firstPlay; // Java made me do this
                winningPlayers.forEach(p -> this.setWinner(
                        p, wantsKong > 0 ? 1 : 0,
                        winners, losingPlayers,
                        this.getPlayerTurn(), finalFirstPlay
                ));
                for (Player p : this.players.values())
                    p.seeClaimedTile(playerToPlay, tileToDiscard, winningPlayers);
                break;
            }

            // More than one player may want the tile for a combination but we only consider the one with the highest
            // priority.
            Player playerWithCombination = null;
            Combination playerCombination = null;

            noSteal = true;
            claimedKong = false;
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
                    // be added properly further ahead.
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
                            claimedKong = true;
                            break;

                        } else if (possibleCombination.getCombinationType() == CombinationType.CHOW &&
                                requestingPlayer.getSeatWind() == this.playerTurn.next()) {
                            // A Chow can only be made by the next player to draw from the wall.
                            playerWithCombination = requestingPlayer;
                            playerCombination = possibleCombination;
                            // Pungs/Kongs have priority over Chows, so we register the Player and the Chow, but still
                            // check the other players.
                        }
                    }
                }
            }

            if (playerWithCombination != null) {
                //System.out.println("\t\tPlayer " + this.playerTurn + " has stolen " + tileToDiscard + " to make a " + possibleCombination);
                // We claim the tile associated with the correct combination.
                playerWithCombination.claimTile(tileToDiscard, playerCombination);
                for (Player p : this.players.values())
                    p.seeClaimedTile(playerToPlay, tileToDiscard, Collections.singletonList(playerWithCombination));
                // The player that has stolen the tile gets to go next.
                this.playerTurn = playerWithCombination.getSeatWind();
                noSteal = false;

            } else {
                // If no player has stolen the tile, then we need to actually record the discarding, and update the next
                // player.
                //System.out.println("\tNo one stole anything what a ripoff");
                this.open.addDiscardedTile(playerToPlay, tileToDiscard);
                this.playerTurn = this.playerTurn.next();
            }
            firstPlay = false;
        }

        if (winners.isEmpty())
            System.out.println("No winners PepeHands");

        winners.forEach(p -> System.out.println(
                "[ROUND " + this.roundTurn + "] " + p + " - " + this.players.get(p).getName() + " won!"
        ));

        System.out.println("All scores:");
        this.scores.forEach((k, v) -> System.out.println(
                "\t" + k.getSeatWind() + ": " + k.getName() + " with " + v + " points"
        ));
        System.out.println();

        // If no player wins or the East Wind is victorious, then all players maintain their seat Wind.
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
            @Nullable PlayerTurn discarded, boolean firstPlay) {

        TileContent playerWind = this.turnToTile(winner.getSeatWind());
        TileContent roundWind = this.turnToTile(this.getRoundTurn());

        System.out.println("Setting " + winner.getName() + " as winner...");

        // 1 Extra Fan if it's the last drawable tile or the last discarded tile
        int fan = winner.handValue(playerWind, roundWind, discarded == null, firstPlay) +
                (this.isBoardWallEmpty() ? bonus + 1 : bonus);
        fan = Math.min(fan, 64); // Max Fan is 64

        int points = this.points(fan);

        int score;
        if (discarded != null) {
            System.out.println("Won by claiming " + this.players.get(discarded).getName() + "'s discarded tile");
            losers.forEach(p -> this.scores.computeIfPresent(p, (k, s) -> s - points));
            this.scores.computeIfPresent(this.players.get(discarded), (k, s) -> s - points);
            score = (losers.size() + 1) * points;
        } else {
            System.out.println("Won by self-drawn");
            losers.forEach(p -> this.scores.computeIfPresent(p, (k, s) -> s - 2 * points));
            score = (losers.size()) * 2 * points;
        }

        System.out.println(fan + " Fan -> " + points + " Points -> Total reward: " + score);

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
