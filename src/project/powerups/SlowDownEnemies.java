package project.powerups;

import project.Game;
import project.io.Sound;
import project.characters.Enemy;
import project.characters.GameObject;
import project.mechanics.Type;
import project.mechanics.GameObjectHandler;

import java.util.List;

/**
 * Created by Nils Broman.
 * This class handles the power up that slows down the enemies.
 */

public class SlowDownEnemies extends PowerUp
{
    private final static int SPEED_DIVIDER = 1, DURATION = 1000;

    private int timePassed = 0;
    private boolean runTimer = false;
    private final static String SOUND_PATH = "../io/Sounds/oh-yeah-everything-is-fine.wav";

    public SlowDownEnemies(final int x, final int y, final int size, final Type type,
			   final GameObjectHandler handler)
    {
	super(x, y, size, type, handler);
    }

    @Override
    public void tick(){

	if(runTimer){
	    if(DURATION <= timePassed){
		resetSpeeds();
		timePassed = 0;
		runTimer = false;
	    }
	    timePassed++;
	}
    }

    private void resetSpeeds() {

	List<GameObject> objectList = getHandler().getObjects();
	for (GameObject anObjectList : objectList) {
	    if (anObjectList.getType() == Type.BASIC_ENEMY || anObjectList.getType() == Type.DESTROY_ENEMY) {
		changeSpeeds((Enemy) anObjectList, -SPEED_DIVIDER, -SPEED_DIVIDER);
	    }
	}

    }


    public void reactToPacManCollision()
    {
	List<GameObject> objectList = getHandler().getObjects();
	for (int i = 0; i < objectList.size(); ++i) { // Warning. I do not want to change to foreach since an object can be
	    // removed inside the loop
	    if (objectList.get(i).getType() == Type.BASIC_ENEMY || objectList.get(i).getType() == Type.DESTROY_ENEMY) {
		changeSpeeds((Enemy) objectList, SPEED_DIVIDER, SPEED_DIVIDER);

		this.getHandler().removeObject(this);
	    }
	}
	startTimer();
	Sound.playMusic(getClass().getResource(SOUND_PATH));
	Game.getBoard().setNrPowerUps(Game.getBoard().getNrPowerUps() - 1);
    }

    private void changeSpeeds(Enemy enemy, int changeX, int changeY) {

	int velX = enemy.getVelX();
	int velY = enemy.getVelY();
	if(enemy.getVelX() > 0){
	    enemy.setVelX(velX - changeX);
	}else{
	    enemy.setVelX(velX + changeX);
	}
	if(enemy.getVelY() > 0){
	    enemy.setVelY(velY - changeY);
	}else{
	    enemy.setVelY(velY + changeY);
	}

    }

    private void startTimer() {
        timePassed = 0;
	runTimer = true;
    }
}
