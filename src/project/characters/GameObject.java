package project.characters;

import java.awt.*;
import project.collisiondetection.CollisionHandler;
import project.mechanics.Type;
import project.mechanics.GameObjectHandler;

/**
 * Created by Nils Broman.
 * This class handles all the shared functions and variables for any game object.
 */
public abstract class GameObject
{
    int x; // Warning package visible, if I put these variables as private then all the sub-classes cannot access the variables
           // as easily
    int y; // Warning package visible
    private int size;
    private GameObjectHandler handler;
    private CollisionHandler collisionHandler;
    private final Type type;

    /**
     * Constructor
     * @param x x coordinate
     * @param y y coordinate
     * @param size size of object in pixels
     * @param type type of object
     * @param handler handler the game object
     */
    protected GameObject(final int x, final int y, final int size, final Type type, final GameObjectHandler handler, CollisionHandler collisionHandler) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.type = type;
        this.handler = handler;
        this.collisionHandler = collisionHandler;
    }

    public abstract void tick();

    /**
     * Regulates position of the game object so that it never goes outside the board.
     * @param coordinate x or y coordinate of game object
     * @param min min value allowed for coordinate
     * @param max max value allowed for coordinate
     * @return allowed coordinate
     */
    static int clamp(int coordinate, int min, int max){
	if(coordinate >= max){ return max; }
	else if(coordinate <= min){ return min; }
	return coordinate;
    }


    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getSize() {
        return size;
    }


    /**
     * This method is used to detect collision between rectangles. To be adle to call intersects() on two rectangles.
     * @return rectangle
     */
    public Rectangle getBounds(){
        return new Rectangle(x, y, size, size);
    }

    public Type getType(){
        return type;
    }

    public GameObjectHandler getHandler() {
	return handler;
    }

    public void setX(final int x) {
	this.x = x;
    }
    public void setY(final int y) {
        this.y = y;
    }

    public CollisionHandler getCollisionHandler() {
	return collisionHandler;
    }
}
