package project.collisiondetection;

import project.characters.GameObject;
import project.Game;
import project.mechanics.Board;
import project.mechanics.Type;
import project.powerups.PowerUp;

/**
 * Created by Nils Broman.
 * This class handles the collision detection for power ups.
 */
public class PowerUpCollisionHandler implements CollisionHandler
{
    private Board board = Game.getBoard();

    /**
     * This methods checks for collision on power ups and calls the respective methods for handling that.
     * @param obj power up checked.
     */
    @Override public void checkCollisions(final GameObject obj) {
	for(int i = 0; i < board.getGameObjectHandler().getObjects().size(); ++i){
	    GameObject collisionObj = board.getGameObjectHandler().getObjects().get(i);
	    if(obj.getBounds().intersects(collisionObj.getBounds())) {
		if (collisionObj.getType() == Type.PACMAN) {
		    ((PowerUp) obj).reactToPacManCollision();
		}else if(collisionObj.getType() == Type.BASIC_ENEMY || collisionObj.getType() == Type.DESTROY_ENEMY){
		    ((PowerUp) obj).reactToEnemyCollision();
		}
	    }
	}
    }
}
