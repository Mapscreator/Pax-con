package project.characters;

import project.Game;
import project.collisiondetection.EnemyCollisionHandler;
import project.mechanics.Type;
import project.mechanics.GameObjectHandler;

/**
 * Created ny Nils Broman.
 * This class handles all the shared functions and variables of all the enemies in the game.
 */
public abstract class Enemy extends GameObject
{
    private int velX;
    private int velY;

    Enemy(final int x, final int y, final int velX, final int velY, final int size, final Type type,
		    final GameObjectHandler handler) {
	super(x, y, size, type, handler, new EnemyCollisionHandler());
	this.velX = velX;
	this.velY = velY;
    }

    @Override
    public void tick() {
	x += velX;
	y += velY;

	int squareSize = Game.getBoard().getSquareSize();

	x = clamp(x, squareSize, squareSize * Game.getBoard().getWidth() - Game.getBoard().getSquareSize() * 2);
	y = clamp(y, squareSize, squareSize * Game.getBoard().getHeight() - Game.getBoard().getSquareSize() * 2);
    }

    /**
     * Method specific to the enemy-type, each enemy reacts diferently on collision with the walls.
     * @param x coordinate of block just collided with
     * @param y coordinate of block just collided with
     */
    public abstract void reactToWallCollision(int x, int y);

    public void bounceX(){
	this.velX *= (-1);
    }

    public void bounceY(){
	this.velY *= (-1);
    }

    public int getVelX() {
	return velX;
    }

    public void setVelX(final int velX) {
	this.velX = velX;
    }

    public int getVelY() {
	return velY;
    }

    public void setVelY(final int velY) {
	this.velY = velY;
    }
}
