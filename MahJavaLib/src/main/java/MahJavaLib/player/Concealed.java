package MahJavaLib.player;

import MahJavaLib.game.OpenGame;
import MahJavaLib.hand.Hand;
import MahJavaLib.tile.Combination;
import MahJavaLib.tile.Tile;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Reactive agent architecture that chooses to never draw discarded tiles and
 * always opts for concealed hands.
 *
 * This means that the discard policy must be more complex to counter-balance
 * the disadvantage from this restriction.
 */
public class Concealed implements Profile {

    /*
     * @TODO
     */
    @Override
    public Tile chooseTileToDiscard(Hand hand, OpenGame game) {
        return (Tile) hand.getHand().keySet().toArray()[0];
    }

    /*
     * Never claims a discarded tile.
     */
    @Override
    public Optional<Combination> wantsDiscardedTile(Tile discardedTile, Hand hand, OpenGame game) {
        return Optional.empty();
    }

    /*
     * Only declares a concealed Kong if there is no concealed/possible Chow with the same tile.
     */
    @Override
    public boolean declareConcealedKong(Hand hand, OpenGame game) {
        List<Tile> concealedKongs = hand.getConcealedKongs();

        // We don't have any Kongs
        if (concealedKongs.isEmpty())
            return false;

        List<Tile> acceptableKongs = concealedKongs.stream().filter(
                tile -> tile.getPossibleChowCombinations().stream() // We get the possible chows for the tile
                        .flatMap(Collection::stream) // Join all the tiles in a single collection
                        .distinct() // Remove repeated tiles
                        // No tile (other than the Kong tile) must be in the hand.
                        // Otherwise, we choose to wait for/keep a Chow
                        .noneMatch(t -> !t.equals(tile) && hand.getHand().containsKey(t))
        ).collect(Collectors.toList());

        if (acceptableKongs.isEmpty())
            return false;

        // We have at least one, so we choose randomly
        // (it will most likely be always one but this is just to make sure)
        int rand = new Random().nextInt(acceptableKongs.size());
        hand.declareConcealedKong(acceptableKongs.get(rand));
        return true;
    }
}
