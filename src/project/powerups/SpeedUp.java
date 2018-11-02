package project.powerups;

import project.Game;
import project.io.Sound;
import project.mechanics.Board;
import project.mechanics.Type;
import project.mechanics.GameObjectHandler;

/**
 * This class handles the power up that speeds up Pacman.
 */

public class SpeedUp extends PowerUp
{
    private final static int SPEED_INCREASE = 5;
    private boolean runTimer = false;
    private int timePassed = 0;
    private final static int DURATION = 500;
    private final static String SOUND_PATH = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\Sounds\\oh-my-god-1.wav";
    private Board board = Game.getBoard();

    public SpeedUp(final int x, final int y, final int size, final Type type,
		   final GameObjectHandler handler)
    {
	super(x, y, size, type, handler);
    }

    @Override
    public void tick(){

	if(runTimer){
	    if(DURATION <= timePassed){
		board.getPacMan().setSpeed(board.getPacMan().getStartSpeed());
		timePassed = 0;
		runTimer = false;
	    }
	    timePassed++;
	}

    }


    public void reactToPacManCollision() {
	board.getPacMan().changeSpeed(SPEED_INCREASE);
	this.getHandler().removeObject(this);
	Sound.playMusic(SOUND_PATH);
	Game.getBoard().changeNrOfPowerUps(-1);
	startTimer();
    }


    private void startTimer(){
        timePassed = 0;
        runTimer = true;
    }
}
