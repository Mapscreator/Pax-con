package project.collisiondetection;

import project.Game;
import project.characters.Enemy;
import project.characters.GameObject;
import project.mechanics.Board;
import project.mechanics.SquareType;

/**
 * Created by Nils Broman.
 * This class handles the collisions between enemies and the walls.
 */
public class EnemyCollisionHandler implements CollisionHandler
{

    private Board board = Game.getBoard();

    /**
    * This methods detects collision between enemies and the walls. And calls methods depending on the enemy type and what
    * kind of wall it it detected.
    * @param obj This is the object that collision is checked for.
    */
    @Override public void checkCollisions(GameObject obj) {

	int nrPixelsOff = 3;

	for (int i = obj.getX(); i < obj.getX() + obj.getSize(); ++i) {
	    for (int j = obj.getY(); j < obj.getY() + obj.getSize(); ++j) {
		int sqSize = Game.getBoard().getSquareSize();

		// Collision up or down

		if(board.getSquareType(i/sqSize, (j -nrPixelsOff)/sqSize) != SquareType.EMPTY){
		    detectedCollisionY( i/sqSize, (j -nrPixelsOff)/sqSize, (Enemy) obj);
		    return;
		}else if(board.getSquareType(i/sqSize, (j +nrPixelsOff)/sqSize) != SquareType.EMPTY){
		    detectedCollisionY(i/sqSize, (j +nrPixelsOff)/sqSize, (Enemy) obj);
		    return;

		// Collision left or right

		}else if(board.getSquareType((i + nrPixelsOff)/sqSize, j/sqSize) != SquareType.EMPTY){
		    detectedCollisionX( (i + nrPixelsOff)/sqSize, j/sqSize, (Enemy) obj);
		    return;
		}else if(board.getSquareType((i - nrPixelsOff)/sqSize, j/sqSize) != SquareType.EMPTY){
		    detectedCollisionX( (i - nrPixelsOff)/sqSize, j/sqSize, (Enemy) obj);
		    return;

		// Collision with corners

		}else if(board.getSquareType((i+ nrPixelsOff)/sqSize, (j -nrPixelsOff)/sqSize) != SquareType.EMPTY){
		    detectedCollisionCorner( (i+ nrPixelsOff)/sqSize, (j -nrPixelsOff)/sqSize, (Enemy) obj);
		    return;
		}else if(board.getSquareType((i + nrPixelsOff)/sqSize, (j +nrPixelsOff)/sqSize) != SquareType.EMPTY){
		    detectedCollisionCorner((i + nrPixelsOff)/sqSize, (j +nrPixelsOff)/sqSize, (Enemy) obj);
		    return;
		}else if(board.getSquareType((i - nrPixelsOff)/sqSize, (j - nrPixelsOff)/sqSize) != SquareType.EMPTY){
		    detectedCollisionCorner( (i - nrPixelsOff)/sqSize, (j - nrPixelsOff)/sqSize, (Enemy) obj);
		    return;
		}else if(board.getSquareType((i - nrPixelsOff)/sqSize, (j + nrPixelsOff)/sqSize) != SquareType.EMPTY){
		    detectedCollisionCorner( (i - nrPixelsOff)/sqSize, (j + nrPixelsOff)/sqSize, (Enemy) obj);
		    return;
		}
	    }
	}
    }

    /**
     * This method calls methods for when the enemy collided with a wall to the left or right.
     * @param x coordinate of collided square.
     * @param y coordinate of collided square.
     * @param enemy enemy that collided.
     */
    private void detectedCollisionX(int x, int y, Enemy enemy){
	enemy.reactToWallCollision(x, y);
        enemy.bounceX();
	markTrail(x, y);
    }

    /**
     * This method calls methods for when the enemy collided with a square up or down.
     * @param x coordinate of collided square.
     * @param y coordinate to collided square.
     * @param enemy enemy that collided.
     */
    private void detectedCollisionY(int x, int y, Enemy enemy){
	enemy.reactToWallCollision(x,y);
        enemy.bounceY();
	markTrail(x, y);
    }

    /**
     * This method calls methods for when the enemy collides with a corner of the wall.
     * @param x coordinate of collided square.
     * @param y coordinate of collided square.
     * @param enemy enemy that collided.
     */
    private void detectedCollisionCorner(int x, int y, Enemy enemy){
	enemy.reactToWallCollision(x, y);
        enemy.bounceX();
	enemy.bounceY();
	markTrail(x, y);
    }

    /**
     * This method marks PacMans trail red when an enemy collide with it
     * @param x coordinate on where the enemy hit
     * @param y coordinate on where the enemy hit
     */
    private void markTrail(int x, int y) {
	if(board.getSquareType(x, y) == SquareType.TRAIL || board.getSquareType(x,y) == SquareType.LAST_TRAIL){
	    board.setSquareType(x, y, SquareType.RED_TRAIL);
	}
    }
}
