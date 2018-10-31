package project.mechanics;

import project.GameTest;
import project.characters.GameObject;

/**
 * Created by Nils Broman.
 * This class is used to detect collisions between different game objects and
 * collision with the environment.
 */
public class DefaultCollisionHandler implements CollisionHandler
{
    private Board board = GameTest.getBoard();

    /**
     * This function detects collision between enemies and the walls. Returning a int whether the enemy hit a wall to the right
     * or left, a wall up or down or hit a wall on the corner of the wall.
     * @param obj This is the object that collision is checked for.
     * @return Returns an int depending on what type of collision occured.
     */

    public int hasCollision(GameObject obj) {

	int nrPixelsOff = 3;

	removeEnemyIfSurrounded(obj);

	for (int i = obj.getX(); i < obj.getX() + obj.getSize(); ++i) {
	    for (int j = obj.getY(); j < obj.getY() + obj.getSize(); ++j) {
	        int sqSize = GameTest.getBoard().getSquareSize();

	        // Collision up or down

		if(board.getSquareType(i/sqSize, (j -nrPixelsOff)/sqSize) != SquareType.EMPTY){
		    destroyWall(obj, i/sqSize, (j -nrPixelsOff)/sqSize);
		    markTrail(i/sqSize, (j -nrPixelsOff)/sqSize);
		    return 1;
		}else if(board.getSquareType(i/sqSize, (j +nrPixelsOff)/sqSize) != SquareType.EMPTY){
		    destroyWall(obj, i/sqSize, (j +nrPixelsOff)/sqSize);
		    markTrail(i/sqSize, (j +nrPixelsOff)/sqSize);
		    return 1;

		    // Collision left or right

		}else if(board.getSquareType((i + nrPixelsOff)/sqSize, j/sqSize) != SquareType.EMPTY){
		    destroyWall(obj, (i + nrPixelsOff)/sqSize, j/sqSize);
		    markTrail((i + nrPixelsOff)/sqSize, j/sqSize);
		    return 0;
		}else if(board.getSquareType((i - nrPixelsOff)/sqSize, j/sqSize) != SquareType.EMPTY){
		    destroyWall(obj, (i - nrPixelsOff)/sqSize, j/sqSize);
		    markTrail((i - nrPixelsOff)/sqSize, j/sqSize);
		    return 0;

		    // Collision with corners

		}else if(board.getSquareType((i+ nrPixelsOff)/sqSize, (j -nrPixelsOff)/sqSize) != SquareType.EMPTY){
		    destroyWall(obj, (i+ nrPixelsOff)/sqSize, (j -nrPixelsOff)/sqSize);
		    markTrail((i+ nrPixelsOff)/sqSize, (j -nrPixelsOff)/sqSize);
		    return 2;
		}else if(board.getSquareType((i + nrPixelsOff)/sqSize, (j +nrPixelsOff)/sqSize) != SquareType.EMPTY){
		    destroyWall(obj, (i + nrPixelsOff)/sqSize, (j +nrPixelsOff)/sqSize);
		    markTrail((i + nrPixelsOff)/sqSize, (j +nrPixelsOff)/sqSize);
		    return 2;
		}else if(board.getSquareType((i - nrPixelsOff)/sqSize, (j - nrPixelsOff)/sqSize) != SquareType.EMPTY){
		    destroyWall(obj, (i - nrPixelsOff)/sqSize, (j - nrPixelsOff)/sqSize);
		    markTrail((i - nrPixelsOff)/sqSize, (j - nrPixelsOff)/sqSize);
		    return 2;
		}else if(board.getSquareType((i - nrPixelsOff)/sqSize, (j + nrPixelsOff)/sqSize) != SquareType.EMPTY){
		    destroyWall(obj, (i - nrPixelsOff)/sqSize, (j + nrPixelsOff)/sqSize);
		    markTrail((i - nrPixelsOff)/sqSize, (j + nrPixelsOff)/sqSize);
		    return 2;
		}
	    }
	}

	// No Collision

	return -1;
    }

    /**
     * This function marks PacMans trail red when an enemy collide with it
     * @param x coordinate on where the enemy hit
     * @param y coordinate on where the enemy hit
     */

    private void markTrail(int x, int y) {

        if(board.getSquareType(x, y) == SquareType.TRAIL || board.getSquareType(x,y) == SquareType.LAST_TRAIL){
            board.setSquareType(x, y, SquareType.RED_TRAIL);
	}
    }

    /**
     * This function detects if an enemy has been surrounded by PacMan. If it has, the function removes the enemy.
     * @param obj The object that is checked whether it's surrounded or not.
     */

    private void removeEnemyIfSurrounded(GameObject obj) {

	int x = obj.getX() / GameTest.getBoard().getSquareSize();
	int y = obj.getY() / GameTest.getBoard().getSquareSize();


	if(x == 0 && board.getSquareType(x, y + 1) != SquareType.EMPTY && board.getSquareType(x, y - 1) != SquareType.EMPTY ||
	   y == 0 && board.getSquareType(x + 1, y) != SquareType.EMPTY && board.getSquareType(x - 1, y) != SquareType.EMPTY ||
	   board.getSquareType(x + 1, y) != SquareType.EMPTY && board.getSquareType(x - 1, y) != SquareType.EMPTY &&
	   board.getSquareType(x, y + 1) != SquareType.EMPTY && board.getSquareType(x, y - 1) != SquareType.EMPTY &&
	   obj.getId() != ID.DESTROY_ENEMY) {
	    board.removeEnemy(obj);
	}
    }

    /**
     * This function makes the enemy type destroyEnemy destroy walls that PacMan made.
     * @param obj GameObject that is checked what kind of object it is.
     * @param x coordinate of wall that might be destroyed
     * @param y coordinate of wall that might be destroyed
     */

    private void destroyWall(GameObject obj, int x, int y){
        if(obj.getId() == ID.DESTROY_ENEMY){
            if(board.getSquareType(x, y) == SquareType.DONE){
		board.setSquareType(x, y, SquareType.EMPTY);
  		board.setPercentage();
	    }
        }
    }

    public SquareType steppingOnSquareType(GameObject obj){
        /*
        for(int i = obj.getX(); i < obj.getX() + obj.getSize() ; ++i){
            for(int j = obj.getY(); j < obj.getY() + obj.getSize(); ++j){
            */
		return board.getSquareType(obj.getX()/GameTest.getBoard().getSquareSize(), obj.getY()/GameTest.getBoard().getSquareSize());
            //}
        //}
        //return null;
    }
}
