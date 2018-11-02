package project.io;

import project.Game;
import project.mechanics.Board;
import project.mechanics.GameState;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static project.mechanics.GameState.LEVELS_UNLOCKED;

/**
 * Created by Nils Broman
 * This class handles all the keyboard input to the game.
 */
public class KeyActionListener extends KeyAdapter{
    private static final int RIGHT_KEY = 39;
    private static final int LEFT_KEY = 37;
    private static final int ENTER_KEY = 10; // Play
    private static final int ESC_KEY = 27;
    private static final int SPACE_KEY = 32; // Check levels unlocked
    private static final int DOWN_KEY = 40;
    private static final int UP_KEY = 38;

    private Board board = Game.getBoard();

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == ESC_KEY) {
            System.exit(0);
        }

        switch(e.getKeyCode()){
            case DOWN_KEY:
                board.getPacMan().setLastDown();
                break;
            case UP_KEY:
                board.getPacMan().setLastUp();
                break;
            case LEFT_KEY:
                board.getPacMan().setLastLeft();
                break;
            case RIGHT_KEY:
                board.getPacMan().setLastRight();
                break;
            case ENTER_KEY:
                if(board.getState() == GameState.END_SCREEN) {
                    board.resetGameValues();
                }
                if(board.getState() != GameState.PAUSE_SCREEN){
		    board.setState(GameState.PLAY);
		}
                break;
	    case SPACE_KEY:
	        if(board.getState() == LEVELS_UNLOCKED){
	            board.setState(GameState.PLAY);
                }else{
                    board.setState(LEVELS_UNLOCKED);
                }
	        break;
            default:
        }
    }

}
