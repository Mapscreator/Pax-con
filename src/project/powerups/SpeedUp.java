package project.powerups;

import project.GameTest;
import project.Sound;
import project.mechanics.Board;
import project.mechanics.ID;
import project.mechanics.ObjHandler;

/**
 * This class handles the power up that speeds up Pacman.
 */

public class SpeedUp extends PowerUp
{
    private final static int SPEED_INCREASE = 5;
    private static boolean runTimer = false;
    private static int timePassed = 0;
    private final static int DURATION = 500;
    private final static String SOUND_PATH = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\Sounds\\oh-my-god-1.wav";

    public SpeedUp(final int x, final int y, final int size, final ID id,
		   final ObjHandler handler)
    {
	super(x, y, size, id, handler);
    }

    public void tick(){

	if(runTimer){
	    if(DURATION <= timePassed){
		Board.getPacMan().setSpeed(Board.getPacMan().getStartSpeed());
		timePassed = 0;
		runTimer = false;
	    }
	    timePassed++;
	}

        collisionWithEnemy();
    }

    @Override
    public void handleCollision() {
	Board.getPacMan().changeSpeed(SPEED_INCREASE);
	this.getHandler().removeObject(this);
	Sound.playMusic(SOUND_PATH);
	GameTest.getBoard().changeNrOfPowerUps(-1);
	startTimer();
    }

    private void startTimer(){
        timePassed = 0;
        runTimer = true;
    }
}
