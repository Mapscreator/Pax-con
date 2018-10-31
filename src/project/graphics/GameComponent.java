package project.graphics;

import project.GameTest;
import project.mechanics.Board;
import project.mechanics.BoardListener;
import project.characters.GameObject;
import project.highscore.HighScoreList;
import project.mechanics.LevelsUnlocked;
import project.mechanics.ObjHandler;
import project.characters.PacMan;
import project.mechanics.SquareType;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

import static java.awt.Color.BLUE;
import static java.awt.Color.YELLOW;
import static java.awt.Font.SERIF;

/**
 * Created by Nils Broman.
 * This class handles all the drawing of components.
 */
public class GameComponent extends JComponent implements BoardListener
{
    private Board board;

    private final static int NORMAL_FONT_SIZE = 25, SMALL_FONT_SIZE = 20, HUGE_FONT_SIZE = 50;

    private final static int BOARD_OFFSET = 2; // to not include the squares that's outside the game-zone.

    private final static String START_SCREEN_IMG = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\img\\startScreen.png";

    /**
     * Variables used to draw the Level-select screen.
     */

    private final static int LEVEL_IMG_X = 140, LEVEL_IMG_Y = 300, LEVEL_IMG_SIZE = 60, SPACE_BTWN_LEVEL_IMG_X = 120,
	    SPACE_BETWEEN_LEVEL_IMG_Y = 100, LEVEL_TEXT_X = 160, LEVEL_TEXT_Y = 350;
    private final static String LEVEL_LOCK_IMG = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\img\\LevelLock.png";
    private final static String LEVEL_BACKGROUND_IMG = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\img\\LevelBackgound.png";

    /**
     * Variables used to draw the end game screen, including high score list.
     */

    private final static int HIGHSCORE_X = 100, HIGHSCORE_Y = 100, HIGHSCORE_LIST_Y = 140, SPACE_BTWN_HIGHSCORES = 25,
	    PLAY_AGAIN_X = 200, PLAY_AGAIN_Y = 600, PLAY_AGAIN_WIDTH = 400, PLAY_AGAIN_HEIGHT = 100, PLAY_AGAIN_TEXT_X = 250,
	    PLAY_AGAIN_TEXT_Y = 650;

    /**
     * All the images used for the GameObject sprites.
     */

    private final static String BASIC_ENEMY_IMG = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\img\\BASICENEMY.png",
	    DESTROY_ENEMY_IMG = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\img\\LARGEENEMY.png",
	    SPEED_POWER_UP_IMG = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\img\\speedPowerUp.png",
	    SLOW_DOWN_POWER_UP = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\img\\SlowDownPowerUp.png";

    /**
     * All the images used to draw the different squares on the board.
     */

    private final static String BLUE_SQUARE = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\img\\PAXCONTILE.png",
	    EMPTY_SQUARE = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\img\\PAXCONTILEE.png",
	    TRAIL_SQUARE = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\img\\TRAIL.png",
	    RED_TRAIL_SQUARE = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\img\\REDTRAIL.png";

    /**
     * Variables for the main game state. Includes score, percent complete, level and lives left coordinates.
     */

    private final static int SCORE_X = 100, SCORE_Y = 150, PERCENT_X = 250, PERCENT_Y = 150, LEVEL_X = 550, LEVEL_Y = 150,
	    LIVES_LEFT_X = 100, LIVES_LEFT_Y = 200;


    GameComponent(Board board) {
        this.board = board;
        this.board.addBoardListener(this);
    }

    public void boardChanged() { this.repaint(); }

    @Override
    public Dimension getPreferredSize() {
        super.getPreferredSize();
        // Size is based on number of blocks.
        return new Dimension(this.board.getWidth() * GameTest.getBoard().getSquareSize()
			     - BOARD_OFFSET * GameTest.getBoard().getSquareSize(),
			     this.board.getHeight() * GameTest.getBoard().getSquareSize()
			     - BOARD_OFFSET * GameTest.getBoard().getSquareSize());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;

        switch(board.getState()){
            case PLAY:
                drawSquares(g2d);
                drawGameElements(g2d);
                break;
            case END_SCREEN:
                drawEndScreen(g2d);
                break;
            case PAUSE_SCREEN:
                drawSquares(g2d);
                drawGameElements(g2d);
                drawLevelComplete(g2d);
                break;
            case START_SCREEN:
                drawStartScreen(g2d);
                break;
            case LEVELS_UNLOCKED:
                drawLevelsScreen(g2d);
                break;
            default:
                System.out.printf("No game-state found");
        }
    }

    private void drawStartScreen(Graphics2D g){
	ImageIcon img;
	img = new ImageIcon(START_SCREEN_IMG);
	Image startScreen = img.getImage();
	g.drawImage(startScreen, 0, 0,
		board.getWidth()* GameTest.getBoard().getSquareSize(),
		board.getHeight()* GameTest.getBoard().getSquareSize(),
		null);
    }

    private void drawLevelsScreen(final Graphics2D draw) {

        List<LevelsUnlocked> levelList = GameTest.getBoard().getLevelsUnlocked();

        draw.setColor(Color.black);
	draw.fillRect(0, 0, getWidth(), getHeight());

	int rows = 4;
	int columns = 6;
	int index = 0;

	for(int j = 0; j < rows; ++j){
	    for(int i = 0; i < columns; ++i){

		drawElement(LEVEL_BACKGROUND_IMG, draw,
			    LEVEL_IMG_X + i * SPACE_BTWN_LEVEL_IMG_X,
			    LEVEL_IMG_Y + j*SPACE_BETWEEN_LEVEL_IMG_Y, LEVEL_IMG_SIZE);

		if(levelList.size() == index){
		    drawElement(LEVEL_LOCK_IMG, draw,
				LEVEL_IMG_X + i * SPACE_BTWN_LEVEL_IMG_X,
				LEVEL_IMG_Y + j*SPACE_BETWEEN_LEVEL_IMG_Y, LEVEL_IMG_SIZE);
		}else{
		    if(levelList.get(index) == LevelsUnlocked.UNLOCKED){
			addText(draw, String.valueOf(index + 1),
				LEVEL_TEXT_X + i * SPACE_BTWN_LEVEL_IMG_X - GameTest.getBoard().getSquareSize(),
				LEVEL_TEXT_Y + j*SPACE_BETWEEN_LEVEL_IMG_Y - GameTest.getBoard().getSquareSize(), NORMAL_FONT_SIZE, YELLOW);
		    }else{
		        drawElement(LEVEL_LOCK_IMG, draw,
				    LEVEL_IMG_X + i * SPACE_BTWN_LEVEL_IMG_X,
				    LEVEL_IMG_Y + j*SPACE_BETWEEN_LEVEL_IMG_Y, LEVEL_IMG_SIZE);
		    }
		    index++;
		}
	    }
	}
    }

    private void drawLevelComplete(final Graphics2D draw) {

        int x = board.getWidth()* GameTest.getBoard().getSquareSize() / 3;
        int y = board.getHeight() * GameTest.getBoard().getSquareSize() / 2;
        addText(draw, "LEVEL COMPLETE", x, y, HUGE_FONT_SIZE, YELLOW);

    }

    private void drawGameElements(Graphics2D draw){
        ObjHandler handler = GameTest.getBoard().getHandler();
        for(int i = 0; i < handler.getObjects().size(); i++) {
            GameObject object = handler.getObjects().get(i);
            switch (object.getId()) {
                case PACMAN:
		    drawElement(PacMan.getImg(), draw, object.getX(), object.getY(), object.getSize());
                    break;
                case BASIC_ENEMY:
		    drawElement(BASIC_ENEMY_IMG, draw, object.getX(), object.getY(), object.getSize());
                    break;
                case DESTROY_ENEMY:
		    drawElement(DESTROY_ENEMY_IMG, draw, object.getX(), object.getY(), object.getSize());
                    break;
                case SPEED_POWER_UP:
		    drawElement(SPEED_POWER_UP_IMG, draw, object.getX(), object.getY(), object.getSize());
                    break;
		case SLOW_DOWN_ENEMIES_POWER_UP:
		    drawElement(SLOW_DOWN_POWER_UP, draw, object.getX(), object.getY(), object.getSize());
		    break;
                default:
                    System.out.println("No object found to draw");
            }
        }
        addText(draw, "Score: "+ board.getScore(), SCORE_X, SCORE_Y, SMALL_FONT_SIZE, YELLOW);
        addText(draw, new DecimalFormat("##.#").format(board.getPercentageComplete())
		      + "/100% Complete", PERCENT_X, PERCENT_Y, SMALL_FONT_SIZE, YELLOW);
        addText(draw, String.valueOf(board.getLevel()), LEVEL_X, LEVEL_Y, SMALL_FONT_SIZE, YELLOW);
        addText(draw, "Lives left: " + Board.getPacMan().getLives(), LIVES_LEFT_X, LIVES_LEFT_Y, SMALL_FONT_SIZE, YELLOW);
    }

    private void drawElement(String fileName, Graphics draw, int x, int y, int size){
	ImageIcon img = new ImageIcon(fileName);
	Image imgSlow = img.getImage();
	draw.drawImage(imgSlow,
		       x - GameTest.getBoard().getSquareSize(),
		       y - GameTest.getBoard().getSquareSize(),
		       size, size, null);
    }

    private void drawEndScreen(final Graphics2D draw) {

	HighScoreList highScoreList = HighScoreList.getInstance();

	draw.setColor(Color.black);
	draw.fillRect(0, 0, getWidth(), getHeight());
	addText(draw, "HighScore : ",HIGHSCORE_X, HIGHSCORE_Y, NORMAL_FONT_SIZE, YELLOW);

	String[] highScores = highScoreList.toString().split("\n");

	for (int i = 0; i < highScores.length; i++) {
	    addText(draw, highScores[i], HIGHSCORE_X, HIGHSCORE_LIST_Y + i * SPACE_BTWN_HIGHSCORES, NORMAL_FONT_SIZE, YELLOW);
	}

	draw.setColor(YELLOW);
	draw.fillRect(PLAY_AGAIN_X, PLAY_AGAIN_Y, PLAY_AGAIN_WIDTH, PLAY_AGAIN_HEIGHT);
	addText(draw, "Play again?", PLAY_AGAIN_TEXT_X, PLAY_AGAIN_TEXT_Y, NORMAL_FONT_SIZE, BLUE);

    }

    private void addText(Graphics2D g, String text, int x, int y, int size, Color color){
        Font myFont = new Font(SERIF, Font.BOLD, size);
        g.setFont(myFont);
        g.setColor(color);
        g.drawString(text, x, y);
    }

    private void drawSquares(Graphics2D g2d){
        g2d.setColor(Color.BLACK);
        g2d.fillRect(GameTest.getBoard().getSquareSize(), GameTest.getBoard().getSquareSize(), this.getWidth(), this.getHeight());

        for (int i = 1; i < this.board.getWidth(); i++) {
            // Starts on 1 because of not wanting to draw the squares outside the board
            for (int j = 1; j < this.board.getHeight(); j++) {

                if(board.getSquareType(i, j) == SquareType.DONE || board.getSquareType(i, j) == SquareType.HARD_FRAME){
		    drawSquare(BLUE_SQUARE, g2d, i, j);
                }
                if(board.getSquareType(i, j) == SquareType.EMPTY) {
                    drawSquare(EMPTY_SQUARE, g2d, i, j);
                }
                if(board.getSquareType(i, j) == SquareType.TRAIL || board.getSquareType(i, j) == SquareType.LAST_TRAIL){
		    drawSquare(TRAIL_SQUARE, g2d, i, j);
                }
                if(board.getSquareType(i, j) == SquareType.RED_TRAIL){
                    drawSquare(RED_TRAIL_SQUARE, g2d, i, j);
                }
            }
        }
    }

    private void drawSquare(String fileName, Graphics g2d, int x, int y) {
	ImageIcon icon = new ImageIcon(fileName);
	Image img = icon.getImage();
	g2d.drawImage(img,
		      (x - 1) * GameTest.getBoard().getSquareSize(),
		      (y - 1) * GameTest.getBoard().getSquareSize(),
		      GameTest.getBoard().getSquareSize(),GameTest.getBoard().getSquareSize(), null);
    }
}
