package project.characters;

import project.mechanics.ID;
import project.mechanics.ObjHandler;

import java.awt.*;

/**
 * Created by Nils Broman.
 */
public abstract class GameObject
{

    private int x;
    private int y;
    private int size;
    private ObjHandler handler;
    private final ID id;
    private double velX;
    private double velY;

    protected GameObject(int x, int y, double velX, double velY, int size, ID id, ObjHandler handler){
        this.x = x;
        this.y = y;
        this.velX = velX;
        this.velY = velY;
        this.size = size;
        this.id = id;
        this.handler = handler;
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

    Rectangle getBounds(){
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
