package project.powerups;

import project.GameTest;
import project.Sound;
import project.characters.GameObject;
import project.mechanics.ID;
import project.mechanics.ObjHandler;

import java.util.List;

/**
 * Created by Nils Broman.
 * This class handles the power up that slows down the enemies.
 */

public class SlowDownEnemies extends PowerUp
{
    private final static int SPEED_DIVIDER = 1, DURATION = 1000;

    private static int timePassed = 0;
    private static boolean runTimer = false;
    private final static String SOUND_PATH = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\Sounds\\oh-yeah-everything-is-fine.wav";

    public SlowDownEnemies(final int x, final int y, final int size, final ID id,
			   final ObjHandler handler)
    {
	super(x, y, size, id, handler);
    }

    public void tick(){

	if(runTimer){
	    if(DURATION <= timePassed){
		resetSpeeds();
		timePassed = 0;
		runTimer = false;
	    }
	    timePassed++;
	}
	collisionWithEnemy();
    }

    private void resetSpeeds() {

	List<GameObject> objectList = getHandler().getObjects();
	for (int i = 0; i < objectList.size(); ++i) {
	    if (objectList.get(i).getId() == ID.BASIC_ENEMY || objectList.get(i).getId() == ID.DESTROY_ENEMY) {

		changeSpeeds(i, -SPEED_DIVIDER, -SPEED_DIVIDER);
	    }
	}

    }

    @Override
    public void handleCollision()
    {
	List<GameObject> objectList = getHandler().getObjects();
	for (int i = 0; i < objectList.size(); ++i) {
	    if (objectList.get(i).getId() == ID.BASIC_ENEMY || objectList.get(i).getId() == ID.DESTROY_ENEMY) {

	        changeSpeeds(i, SPEED_DIVIDER, SPEED_DIVIDER);

		this.getHandler().removeObject(this);
	    }
	}
	startTimer();
	Sound.playMusic(SOUND_PATH);
	GameTest.getBoard().setNrPowerUps(GameTest.getBoard().getNrPowerUps() - 1);
    }

    private void changeSpeeds(int i, double changeX, double changeY) {

	List<GameObject> objectList = getHandler().getObjects();
	double velX = objectList.get(i).getVelX();
	double velY = objectList.get(i).getVelY();
	if(objectList.get(i).getVelX() > 0){
	    objectList.get(i).setVelX(velX - changeX);
	}else{
	    objectList.get(i).setVelX(velX + changeX);
	}
	if(objectList.get(i).getVelY() > 0){
	    objectList.get(i).setVelY(velY - changeY);
	}else{
	    objectList.get(i).setVelY(velY + changeY);
	}

    }

    private void startTimer() {
        timePassed = 0;
	runTimer = true;
    }
}
