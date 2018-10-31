package project.mechanics;

/**
 * Created by Nils Broman.
 * This class is an Enum for all the possible square types in the game.
 */
public enum SquareType {

    /**
     * SquareType outside.
     */

    OUTSIDE,

    /**
     * Empty squareType.
     */

    EMPTY,

    /**
     * Trail after main-character
     */

    TRAIL,

    /**
     * Red-trail, when a enemy collides with the trail it turns red
     */

    RED_TRAIL,

    /**
     * Hard-frame
     */

    HARD_FRAME,

    /**
     * SquareType Done. For when the player surrounds a part of the grid, it turns to done.
     */

    DONE,

    /**
     * SquareType Last-trail. Is so that the player can't collide with the last two squares that
     * was marked "trail". Makes the trail follow PacMan better than if all of the trail could collide with PacMan.
     */

    LAST_TRAIL

}
