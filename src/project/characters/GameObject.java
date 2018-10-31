package project.characters;

import project.mechanics.ID;
import project.mechanics.ObjHandler;

import java.awt.*;

/**
 * Created by Nils Broman.
 * This class handles all the shared functions and variables for any game object.
 */
public abstract class GameObject
{

    int x;
    int y;
    private int size;
    private ObjHandler handler;
    private final ID id;
    double velX;
    double velY;

    protected GameObject(int x, int y, double velX, double velY, int size, ID id, ObjHandler handler){
        this.x = x;
        this.y = y;
        this.velX = velX;
        this.velY = velY;
        this.size = size;
        this.id = id;
        this.handler = handler;
    }

    /**
     * Constructor for the power up class with no velocity
     * @param x x coordinate
     * @param y y coordinate
     * @param size size of the powerup
     * @param id id represnt what kind of power up it is
     * @param handler handler is the handler for all the game objects
     */

    protected GameObject(final int x, final int y, final int size, final ID id, final ObjHandler handler) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.id = id;
        this.handler = handler;
    }

    static int clamp(int compareValue, int min, int max){
	if(compareValue >= max){return max;}
	else if(compareValue <= min){return min;}
	return compareValue;
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

    public Rectangle getBounds(){
        return new Rectangle(x, y, size, size);
    }

    public abstract void handleCollision();

    public ID getId(){
        return id;
    }

    public abstract void tick();

    public double getVelX() {
        return velX;
    }

    public void setVelX(final double velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(final double velY) {
        this.velY = velY;
    }

    public ObjHandler getHandler() {
	return handler;
    }

    public void setX(final int x) {
	this.x = x;
    }

    public void setY(final int y) {
        this.y = y;
    }
}
