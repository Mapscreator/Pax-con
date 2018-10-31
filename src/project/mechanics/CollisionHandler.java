package project.mechanics;

import project.characters.GameObject;

/**
 * Created by Nils Broman.
 * This class is a interface for the collision handler classes. Every class that implements this inteface has a specific
 * function hasCollision.
 */

public interface CollisionHandler
{
    int hasCollision(GameObject obj);
}
