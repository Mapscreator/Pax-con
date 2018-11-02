package project.collisiondetection;

import project.characters.GameObject;
import project.characters.PacMan;
import project.Game;
import project.mechanics.Board;
import project.mechanics.Type;
import project.mechanics.SquareType;

/**
 * Created by Nils Broman.
 * This class handles the collision detection for PacMan.
 */
public class PacManCollisionHandler implements CollisionHandler
{

    private Board board = Game.getBoard();

    @Override public void checkCollisions(final GameObject obj) {
    	checkEnemyCollision(obj);
    	checkWallCollision((PacMan) obj);
    }

    /**
     * This method checks collision between PacMan and enemies.
     * @param pacMan PacMan.
     */
    private void checkEnemyCollision(GameObject pacMan) {
    	for(int i = 0; i < board.getGameObjectHandler().getObjects().size(); ++i){
    	    GameObject collisionObj = board.getGameObjectHandler().getObjects().get(i);
    	    if(pacMan.getBounds().intersects(collisionObj.getBounds())) {
    		if (collisionObj.getType() == Type.BASIC_ENEMY || collisionObj.getType() == Type.DESTROY_ENEMY) {
		    board.getPacMan().resetPacMan();
		    Game.getBoard().makeTrailSquaresEmpty();
		    Game.getBoard().deleteTrail();
		    Game.getBoard().loseOneLife();
    		}
    	    }
    	}
    }

    /**
     * This method calls methods depending on which square type PacMan just stepped on.
     * @param pacMan PacMan.
     */
    private void checkWallCollision(PacMan pacMan){
        int squareSize = Game.getBoard().getSquareSize();
	if(steppingOnSquareType(pacMan) == SquareType.EMPTY){
	    pacMan.steppingOnEmptySquare(pacMan.getX()/squareSize, pacMan.getY()/squareSize);
	}
	else if(steppingOnSquareType(pacMan) == SquareType.HARD_FRAME || steppingOnSquareType(pacMan) == SquareType.DONE){
	    pacMan.steppingOnHardFrame();
	}
	else if(steppingOnSquareType(pacMan) == SquareType.TRAIL || steppingOnSquareType(pacMan) == SquareType.RED_TRAIL){
	    pacMan.steppingOnTrail();
	}
    }

    /**
     * Returns what kind of square type the object is currently on.
     * @param obj Object that is checked.
     * @return type of square.
     */
    private SquareType steppingOnSquareType(GameObject obj){
	return board.getSquareType(obj.getX() / Game.getBoard().getSquareSize(),
				   obj.getY() / Game.getBoard().getSquareSize());
    }

}
