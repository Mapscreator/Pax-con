package project.powerups;

import project.characters.GameObject;
import project.mechanics.ID;
import project.mechanics.ObjHandler;

/**
 * Created by Nils Broman
 * This class is
 */
public abstract class PowerUp extends GameObject
{
    private static boolean timer = false;

    PowerUp(int x, int y, double velX, double velY, int size, ID id, ObjHandler handler) {
        super(x, y, velX, velY, size, id, handler);
    }

    @Override
    public void tick() {

        if(timer){

        }
    }

    /*
    private void collision(){
        for(int i = 0; i < handler.objects.size(); ++i){
            GameObject tempObj = handler.objects.get(i);
            if(tempObj.getId() == ID.BASIC_ENEMY){
                if(getBounds().intersects(tempObj.getBounds())){
                    //Collision with enemy
                    System.out.println("Collide");
                }
            }
        }
    }
    */

    public static void startSlowTimer(){
        timer = true;
    }

}
