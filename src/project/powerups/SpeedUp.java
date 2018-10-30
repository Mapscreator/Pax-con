package project.powerups;

import project.GameTest;
import project.Sound;
import project.mechanics.ID;
import project.mechanics.ObjHandler;

/**
 * This class handles the power up that speeds up Pacman.
 */

public class SpeedUp extends PowerUp
{
    private final static int SPEED_INCREASE = 5;
    private final static String SOUND_PATH = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\Sounds\\oh-my-god-1.wav";

    public SpeedUp(final int x, final int y, final double velX, final double velY, final int size, final ID id,
		   final ObjHandler handler)
    {
	super(x, y, velX, velY, size, id, handler);
    }

    @Override
    public void handleCollision() {
	GameTest.getBoard().getPacMan().changeSpeed(SPEED_INCREASE);
	this.getHandler().removeObject(this);
	Sound.playMusic(SOUND_PATH);
	GameTest.getBoard().changeNrOfPowerUps(-1);
    }
}
