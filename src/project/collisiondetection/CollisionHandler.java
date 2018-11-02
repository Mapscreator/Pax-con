package project.collisiondetection;

import project.characters.GameObject;

/**
 * Created by Nils Broman.
 * This class is an interface for the collision handler classes. Every class that implements this interface has a specific
 * method checkCollisions.
 */

public interface CollisionHandler
{
    void checkCollisions(GameObject obj);
}
