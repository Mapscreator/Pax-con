package project.powerups;

import project.Game;
import project.characters.GameObject;
import project.collisiondetection.PowerUpCollisionHandler;
import project.mechanics.Type;
import project.mechanics.GameObjectHandler;

/**
 * Created by Nils Broman
 * This class that handles all the shared variables and functions of the power up classes.
 */
public abstract class PowerUp extends GameObject
{

    PowerUp(int x, int y, int size, Type type, GameObjectHandler handler) {
        super(x, y, size, type, handler, new PowerUpCollisionHandler());
    }


    public void reactToEnemyCollision(){
	getHandler().removeObject(this);
	Game.getBoard().changeNrOfPowerUps(-1);
    }

    public abstract void reactToPacManCollision();


}
