package MahJavaLib.player;

import MahJavaLib.game.OpenGame;
import MahJavaLib.hand.Hand;
import MahJavaLib.tile.Combination;
import MahJavaLib.tile.Tile;

import java.util.Optional;

/**
 * Interface used to encapsulate the decision-making process by each player.
 * This allows for different player profiles to be defined while maintaining
 * modularity for the game logic.
 *
 * We assume that a player with a winning hand, will always declare victory,
 * so we do not include that logic in this interface.
 * @TODO: Maybe include this in the interface
 */
interface Profile {

    // After drawing a tile from the wall or claiming a tile from another player, one of the concealed tiles should
    // be discarded. This method should encapsulate the deliberation for which tile to discard, given the player's
    // preferences.
    Tile chooseTileToDiscard(Hand hand, OpenGame game);

    // After discarding a tile, the other players may claim the tile to complete a combination (Pung/Kong/Chow).
    // This method should encapsulate all the logic for the player to choose which tiles to claim and which to ignore.
    // Since the player may choose to ignore the tile, this method returns an optional value for the combination.
    Optional<Combination> wantsDiscardedTile(Tile discardedTile, Hand hand, OpenGame game);

    // If we want 4 concealed tiles to count as a Kong, we must declare it in order to draw a supplement tile from
    // the wall. This method should encapsulate the decision-making process by the player regarding a Kong declaration.
    // Returns true if the player can declare a concealed kong and wants to declare it; false otherwise.
    boolean declareConcealedKong(Hand hand, OpenGame game);
}
