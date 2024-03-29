package MahJava.player;


import MahJava.game.OpenGame;
import MahJava.hand.Hand;
import MahJava.tile.Combination;
import MahJava.tile.Tile;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

/**
 * Hybrid agent architecture that maintains an internal state,
 * in order to keep track of which of the available reactive
 * profiles should be used.
 *
 * This is the only strategy that does not ignore sensorial input.
 */
public class Mixed implements Profile {

    private Player player;
    private final Deque<Profile> choices = new ArrayDeque<>();
    private boolean canSwitch = false;

    public Mixed() {
        choices.add(new Eager());
        choices.add(new Composed());
        choices.add(new Concealed());
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    /*
     * The first time a player claims our discarded tile, we do nothing. The second time we switch profiles.
     * If we lose the round, we switch regardless.
     * Otherwise, we stick to the same strategy.
     */
    @Override
    public void seeClaimedTile(@Nullable Player discarded, Tile tile, List<Player> claimed) {
        if (discarded == null && claimed.stream().noneMatch(p -> p.equals(this.player))) {
            // We lost the round
            this.choices.addLast(this.choices.pop());
            this.canSwitch = false;

        } else if (discarded != null && discarded.equals(this.player)) {
            if (this.canSwitch) {
                // The second time we discard a claimed tile
                this.choices.addLast(this.choices.pop());
                this.canSwitch = false;

            } else {
                // The first time we discard a claimed tile
                this.canSwitch = true;
            }
        } else if (claimed.stream().anyMatch(p -> p.equals(this.player))) {
            // We won the round, so we stick to the strategy for the next round
            this.canSwitch = false;
        }
    }

    /*
     * We just do what the current profile is supposed to do.
     */
    @Override
    public Tile chooseTileToDiscard(Hand hand, OpenGame game) {
        return this.choices.getFirst().chooseTileToDiscard(hand, game);
    }

    /*
     * We just do what the current profile is supposed to do.
     */
    @Override
    public Optional<Combination> wantsDiscardedTile(Tile discardedTile, Hand hand, OpenGame game) {
        return this.choices.getFirst().wantsDiscardedTile(discardedTile, hand, game);
    }

    /*
     * We just do what the current profile is supposed to do.
     */
    @Override
    public boolean declareConcealedKong(Hand hand, OpenGame game) {
        return this.choices.getFirst().declareConcealedKong(hand, game);
    }

    @Override
    public String toString() {
        return "Mixed";
    }
}
