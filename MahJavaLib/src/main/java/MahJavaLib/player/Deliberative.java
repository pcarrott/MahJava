package MahJavaLib.player;


import MahJavaLib.game.OpenGame;
import MahJavaLib.hand.Hand;
import MahJavaLib.tile.Combination;
import MahJavaLib.tile.Tile;

import java.util.Optional;

/**
 * Deliberative agent architecture that, from a set of possible winning hands,
 * commits to the one with the least tiles left to complete. Decisions are made
 * based on which tiles are useful to complete the hand.
 *
 * The complexity is in filtering the set of winning hands and assert which is
 * the optimal choice. Once that commitment is made, the decision-making process
 * becomes relatively simple.
 */
class Deliberative implements Profile {

    // @TODO: Should have an attribute for the commitments

    @Override
    public Tile chooseTileToDiscard(Hand hand, OpenGame game) {
        // @TODO
        return null;
    }

    @Override
    public Optional<Combination> wantsDiscardedTile(Tile discardedTile, Hand hand, OpenGame game) {
        // @TODO
        return Optional.empty();
    }

    @Override
    public boolean declareConcealedKong(Hand hand, OpenGame game) {
        // @TODO
        return false;
    }
}
