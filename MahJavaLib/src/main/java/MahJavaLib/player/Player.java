package MahJavaLib.player;

import MahJavaLib.game.OpenGame;
import MahJavaLib.game.PlayerTurn;
import MahJavaLib.tile.CombinationType;
import MahJavaLib.tile.Tile;
import MahJavaLib.tile.Combination;
import MahJavaLib.hand.Hand;
import MahJavaLib.tile.TileContent;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Player {

    private final String name;
    private OpenGame game;
    private Hand hand;
    private PlayerTurn seatWind = PlayerTurn.EAST;

    // @TODO: Player should have a DiscardProfile and a CommitProfile

    public Player(String name) {
        // The player constructor is empty, since it is supposed to be initialized by the game it is currently in
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setHand(Hand hand) throws IllegalArgumentException {
        // @TODO: check if hand is valid
        this.hand = hand;
    }

    public void setGame(OpenGame game) {
        this.hand.setOpenHand(game.getOpenCombinations(this));
        this.game = game;
    }

    public Tile chooseTileToDiscard() {
        // @TODO: actually implement decent logic for this, right now it is kinda random
        // @TODO: when we do this in the client, we also need to remove it from our hand, we don't do that right now
        // since the server needs to check if we actually have it, but in the future, the player's hands data structures
        // will be different between the server and the player.
        Integer minCount = this.hand.getHand().values().stream().min(Integer::compare).get();
        Map<Tile, Integer> leftovers = this.hand.getHand().entrySet().stream()
                .filter(e -> e.getValue().equals(minCount))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        List<Tile> kek = leftovers.keySet().stream()
                .filter(e -> e.getPossibleChowCombinations().stream().noneMatch(chow -> {
                    int i = 0;
                    for (Tile t : chow)
                        if (leftovers.containsKey(t))
                            i++;

                    return i == 2;
                }))
                .collect(Collectors.toList());

        return kek.size() != 0 ? kek.get(0) : (Tile) leftovers.keySet().toArray()[0];
    }

    public void claimTile(Tile tileToAdd, Combination combination) {
        this.hand.claimTile(tileToAdd, combination);
    }

    public boolean hasTile(Tile tile, Integer n) {
        return this.hand.hasTile(tile, n);
    }

    public void addTile(Tile tile) {
        this.hand.addTile(tile);
    }

    public PlayerTurn getSeatWind() {
        return this.seatWind;
    }

    public void setSeatWind(PlayerTurn seatWind) {
        this.seatWind = seatWind;
    }

    public Optional<Combination> wantsDiscardedTile(Tile discardedTile) {
        // @TODO: implement logic for this, also change return values since we should inform the Game on the type of
        // combination we want to achieve with the discarded tile (both to make us eligible to get it, and to mark it
        // as Open)
        Optional<Combination> aux = Optional.empty();

        for (var kek : this.hand.getPossibleCombinationsForTile(discardedTile).keySet()) {
            // Pungs have priority over Chows
            if (kek.getCombinationType() == CombinationType.PUNG) {
                aux = Optional.of(kek);
                break;
            }

            // If we get a chow, we only consider it if it does not break a pair, pung or kong
            if (kek.getCombinationType() == CombinationType.CHOW &&
                    kek.getTiles().keySet().stream().noneMatch(t -> this.hand.getHand().containsKey(t) && this.hand.getHand().get(t) > 1)) {
                aux = Optional.of(kek);
                break;
            }
        }

        return aux;
    }

    public void removeTile(Tile tile) {
        this.hand.discardTile(tile);
    }

    public boolean hasWinningHand() {
        return this.hand.isWinningHand();
    }

    public Integer handValue(TileContent playerWind, TileContent roundWind) {
        return this.hand.calculateHandValue(playerWind, roundWind);
    }

    public boolean isWinningTile(Tile discardedTile) {
        return this.hand.isWinningTile(discardedTile);
    }

    @Override
    public String toString() {
        return "MahjongPlayer{" +
                "hand=" + hand +
                ", seatWind=" + seatWind +
                '}';
    }
}
