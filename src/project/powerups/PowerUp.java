package project.powerups;

import project.GameTest;
import project.characters.GameObject;
import project.mechanics.ID;
import project.mechanics.ObjHandler;

/**
 * Created by Nils Broman
 * This class that handles all the shared variables and functions of the power up classes.
 */
abstract class PowerUp extends GameObject
{

    PowerUp(int x, int y, int size, ID id, ObjHandler handler) {
        super(x, y, size, id, handler);
    }


    void collisionWithEnemy(){
        for(int i = 0; i < getHandler().getObjects().size(); ++i){
            GameObject tempObj = getHandler().getObjects().get(i);
            if(tempObj.getId() == ID.BASIC_ENEMY || tempObj.getId() == ID.DESTROY_ENEMY){
                if(getBounds().intersects(tempObj.getBounds())){
                    getHandler().removeObject(this);
                    GameTest.getBoard().changeNrOfPowerUps(-1);
                }
            }
        }
    }


}
