package project.powerups;

import project.GameTest;
import project.Sound;
import project.characters.GameObject;
import project.mechanics.ID;
import project.mechanics.ObjHandler;

import java.util.List;

/**
 * This class handles the power up that slows down the enemies.
 */

public class SlowDownEnemies extends PowerUp
{
    private final static int SPEED_DIVIDER = 2;
    private final static String SOUND_PATH = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\Sounds\\oh-yeah-everything-is-fine.wav";

    public SlowDownEnemies(final int x, final int y, final double velX, final double velY, final int size, final ID id,
			   final ObjHandler handler)
    {
	super(x, y, velX, velY, size, id, handler);
    }

    @Override
    public void handleCollision()
    {
	List<GameObject> objectList = getHandler().getObjects();
	for (int i = 0; i < objectList.size(); ++i) {
	    if (objectList.get(i).getId() == ID.BASIC_ENEMY || objectList.get(i).getId() == ID.DESTROY_ENEMY) {
		double velX = objectList.get(i).getVelX();
		double velY = objectList.get(i).getVelY();

		objectList.get(i).setVelY(velY/SPEED_DIVIDER);
		objectList.get(i).setVelX(velX/SPEED_DIVIDER);

		PowerUp.startSlowTimer();

		this.getHandler().removeObject(this);
	    }
	}
	Sound.playMusic(SOUND_PATH);
	GameTest.getBoard().setNrPowerUps(GameTest.getBoard().getNrPowerUps() - 1);
    }
}
