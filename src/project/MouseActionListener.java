package project;

import project.graphics.GameComponent;
import project.graphics.GameFrame;
import project.mechanics.Board;
import project.mechanics.GameState;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Nils Broman.
 * This class handles all mouse-action buttons in the game.
 */
public class MouseActionListener extends MouseAdapter{

    private Board board = GameTest.getBoard();
    private static GameComponent comp = GameFrame.getGameComponent();

    /**
     * Coordinates for the buttons on the end screen.
     */

    private static final int PLAY_AGAIN_X1 = 200, PLAY_AGAIN_X2 = 600, PLAY_AGAIN_Y1 = 600, PLAY_AGAIN_Y2 = 700;

    /**
     * Coordinates for the buttons on the start screen.
     */

    private static final int PLAY_X1 = 300, PLAY_X2 = 700, PLAY_Y1 = 600, PLAY_Y2 = 700;
    private static final int LEVELS_X1 = 300, LEVELS_X2 = 700, LEVELS_Y1 = 840, LEVELS_Y2 = 940;

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

        if(board.getState() == GameState.START_SCREEN){
            addStartScreenActions(e);
        }
        if(board.getState() == GameState.END_SCREEN){
            addEndScreenActions(e);
        }
    }

    private void addEndScreenActions(final MouseEvent e) {
        if(isBetween(e.getX(), PLAY_AGAIN_X1, PLAY_AGAIN_X2) && isBetween(e.getY(), PLAY_AGAIN_Y1, PLAY_AGAIN_Y2)){
            board.resetGameValues();
            board.setState(GameState.PLAY);
        }
    }

    private void addStartScreenActions(MouseEvent e){

        if(isBetween(e.getX(), PLAY_X1, PLAY_X2) && isBetween(e.getY(), PLAY_Y1, PLAY_Y2)){ // Play button
            board.setState(GameState.PLAY);
        }
        if(isBetween(e.getX(), LEVELS_X1, LEVELS_X2) && isBetween(e.getY(), LEVELS_Y1, LEVELS_Y2)){ // project.mechanics.Level unlocked
            board.setState(GameState.LEVELS_UNLOCKED);
            comp.boardChanged();
        }
    }

    private boolean isBetween(int x, int low, int high){
        return x > low && x < high;
    }
}
