package project.graphics;

import project.characters.GameObject;
import project.characters.PacMan;
import project.Game;
import project.highscore.HighScoreList;
import project.mechanics.Board;
import project.mechanics.BoardListener;
import project.mechanics.GameObjectHandler;
import project.mechanics.LevelsUnlocked;
import project.mechanics.SquareType;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

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

    private final static String START_SCREEN_IMG = "img/startScreen.png";

    /**
     * Variables used to draw the Level-select screen.
     */
    private final static int LEVEL_IMG_X = 140, LEVEL_IMG_Y = 300, LEVEL_IMG_SIZE = 60,
	    		     SPACE_BETWEEN_LEVEL_IMG_X = 120, SPACE_BETWEEN_LEVEL_IMG_Y = 100,
	    		     LEVEL_TEXT_X = 160, LEVEL_TEXT_Y = 350;
    private final static String LEVEL_LOCK_IMG = "img/LevelLock.png";
    private final static String LEVEL_BACKGROUND_IMG = "img/LevelBackgound.png";

    /**
     * Variables used to draw the end game screen, including high score list.
     */
    private final static int HIGH_SCORE_X = 100, HIGH_SCORE_Y = 100, HIGH_SCORE_LIST_Y = 140, SPACE_BETWEEN_HIGH_SCORES = 25,
	    		     PLAY_AGAIN_X = 200, PLAY_AGAIN_Y = 600, PLAY_AGAIN_WIDTH = 400, PLAY_AGAIN_HEIGHT = 100,
	    		     PLAY_AGAIN_TEXT_X = 250, PLAY_AGAIN_TEXT_Y = 650;

    /**
     * All the images used for the GameObject sprites.
     */
    private final static String BASIC_ENEMY_IMG = "img/BASICENEMY.png",
			        DESTROY_ENEMY_IMG = "img/LARGEENEMY.png",
			        SPEED_POWER_UP_IMG = "img/speedPowerUp.png",
			        SLOW_DOWN_POWER_UP = "img/SlowDownPowerUp.png";

    /**
     * All the images used to draw the different squares on the board.
     */
    private final static String BLUE_SQUARE = "img/PAXCONTILE.png",
			        EMPTY_SQUARE = "img/PAXCONTILEE.png",
			        TRAIL_SQUARE = "img/TRAIL.png",
			        RED_TRAIL_SQUARE = "img/REDTRAIL.png";

    /**
     * Variables for the main game state. Includes score, percent complete, level and lives left coordinates.
     */
    private final static int SCORE_X = 100, SCORE_Y = 150, PERCENT_X = 250, PERCENT_Y = 150, LEVEL_X = 550, LEVEL_Y = 150,
	    		     LIVES_LEFT_X = 100, LIVES_LEFT_Y = 200;

    private Map<String, ImageIcon> imageIcons = null;

    GameComponent(Board board) {
        this.board = board;
        this.board.addBoardListener(this);
        this.imageIcons = new HashMap<>();
        imageIcons.put(START_SCREEN_IMG, new ImageIcon(getClass().getResource(START_SCREEN_IMG)));
        imageIcons.put(LEVEL_LOCK_IMG, new ImageIcon(getClass().getResource(LEVEL_LOCK_IMG)));
	imageIcons.put(LEVEL_BACKGROUND_IMG, new ImageIcon(getClass().getResource(LEVEL_BACKGROUND_IMG)));
	imageIcons.put(BASIC_ENEMY_IMG, new ImageIcon(getClass().getResource(BASIC_ENEMY_IMG)));
	imageIcons.put(DESTROY_ENEMY_IMG, new ImageIcon(getClass().getResource(DESTROY_ENEMY_IMG)));
	imageIcons.put(SPEED_POWER_UP_IMG, new ImageIcon(getClass().getResource(SPEED_POWER_UP_IMG)));
	imageIcons.put(SLOW_DOWN_POWER_UP, new ImageIcon(getClass().getResource(SLOW_DOWN_POWER_UP)));
	imageIcons.put(BLUE_SQUARE, new ImageIcon(getClass().getResource(BLUE_SQUARE)));
	imageIcons.put(EMPTY_SQUARE, new ImageIcon(getClass().getResource(EMPTY_SQUARE)));
	imageIcons.put(TRAIL_SQUARE, new ImageIcon(getClass().getResource(TRAIL_SQUARE)));
	imageIcons.put(RED_TRAIL_SQUARE, new ImageIcon(getClass().getResource(RED_TRAIL_SQUARE)));
    }

    public void boardChanged() { this.repaint(); }

    @Override
    public Dimension getPreferredSize() {
        super.getPreferredSize();
        // Size is based on number of blocks.
        return new Dimension(this.board.getWidth() * Game.getBoard().getSquareSize()
			     - BOARD_OFFSET * Game.getBoard().getSquareSize(),
			     this.board.getHeight() * Game.getBoard().getSquareSize()
			     - BOARD_OFFSET * Game.getBoard().getSquareSize());
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
	ImageIcon img = new ImageIcon(getClass().getResource(START_SCREEN_IMG));
	Image startScreen = img.getImage();
	g.drawImage(startScreen, 0, 0,
		    board.getWidth() * Game.getBoard().getSquareSize(),
		    board.getHeight() * Game.getBoard().getSquareSize(),
		    null);
    }

    private void drawLevelsScreen(final Graphics2D draw) {

        List<LevelsUnlocked> levelList = Game.getBoard().getLevelsUnlocked();

        draw.setColor(Color.black);
	draw.fillRect(0, 0, getWidth(), getHeight());

	int rows = 4;
	int columns = 6;
	int index = 0;

	for(int j = 0; j < rows; ++j){
	    for(int i = 0; i < columns; ++i){

		drawElement(LEVEL_BACKGROUND_IMG, draw,
			    LEVEL_IMG_X + i * SPACE_BETWEEN_LEVEL_IMG_X,
			    LEVEL_IMG_Y + j*SPACE_BETWEEN_LEVEL_IMG_Y, LEVEL_IMG_SIZE);

		if(levelList.size() == index){
		    drawElement(LEVEL_LOCK_IMG, draw,
				LEVEL_IMG_X + i * SPACE_BETWEEN_LEVEL_IMG_X,
				LEVEL_IMG_Y + j*SPACE_BETWEEN_LEVEL_IMG_Y, LEVEL_IMG_SIZE);
		}else{
		    if(levelList.get(index) == LevelsUnlocked.UNLOCKED){
			addText(draw, String.valueOf(index + 1),
				LEVEL_TEXT_X + i * SPACE_BETWEEN_LEVEL_IMG_X - Game.getBoard().getSquareSize(),
				LEVEL_TEXT_Y + j*SPACE_BETWEEN_LEVEL_IMG_Y - Game.getBoard().getSquareSize(), NORMAL_FONT_SIZE, YELLOW);
		    }else{
		        drawElement(LEVEL_LOCK_IMG, draw,
				    LEVEL_IMG_X + i * SPACE_BETWEEN_LEVEL_IMG_X,
				    LEVEL_IMG_Y + j*SPACE_BETWEEN_LEVEL_IMG_Y, LEVEL_IMG_SIZE);
		    }
		    index++;
		}
	    }
	}
    }

    private void drawLevelComplete(final Graphics2D draw) {
        int x = board.getWidth() * Game.getBoard().getSquareSize() / 3;
        int y = board.getHeight() * Game.getBoard().getSquareSize() / 2;
        addText(draw, "LEVEL COMPLETE", x, y, HUGE_FONT_SIZE, YELLOW);

    }

    private void drawGameElements(Graphics2D draw){
        GameObjectHandler handler = Game.getBoard().getGameObjectHandler();
        for(int i = 0; i < handler.getObjects().size(); i++) {
            GameObject object = handler.getObjects().get(i);
            switch (object.getType()) {
                case PACMAN:
		    drawPacMan(((PacMan) object).getImg(), draw, object.getX(), object.getY(), object.getSize());
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
        addText(draw, "Lives left: " + board.getPacMan().getLives(), LIVES_LEFT_X, LIVES_LEFT_Y, SMALL_FONT_SIZE, YELLOW);
    }

    private void drawElement(String fileName, Graphics draw, int x, int y, int size){
	Image imgSlow = imageIcons.get(fileName).getImage();
	draw.drawImage(imgSlow,
		       x - Game.getBoard().getSquareSize(),
		       y - Game.getBoard().getSquareSize(),
		       size, size, null);
    }

    private void drawPacMan(String fileName, Graphics draw, int x, int y, int size){
        ImageIcon icon = new ImageIcon(getClass().getResource(fileName));
        Image img = icon.getImage();
	draw.drawImage(img,
		      x -  Game.getBoard().getSquareSize(),
		      y -  Game.getBoard().getSquareSize(),
		      size, size, null);
    }

    private void drawEndScreen(final Graphics2D draw) {

	HighScoreList highScoreList = HighScoreList.getInstance();

	draw.setColor(Color.black);
	draw.fillRect(0, 0, getWidth(), getHeight());
	addText(draw, "HighScore : ", HIGH_SCORE_X, HIGH_SCORE_Y, NORMAL_FONT_SIZE, YELLOW);

	String[] highScores = highScoreList.toString().split("\n");

	for (int i = 0; i < highScores.length; i++) {
	    addText(draw, highScores[i], HIGH_SCORE_X, HIGH_SCORE_LIST_Y + i * SPACE_BETWEEN_HIGH_SCORES, NORMAL_FONT_SIZE, YELLOW);
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
        g2d.fillRect(Game.getBoard().getSquareSize(), Game.getBoard().getSquareSize(), this.getWidth(), this.getHeight());

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
	Image img = imageIcons.get(fileName).getImage();
	g2d.drawImage(img,
		      (x - 1) * Game.getBoard().getSquareSize(),
		      (y - 1) * Game.getBoard().getSquareSize(),
		      Game.getBoard().getSquareSize(), Game.getBoard().getSquareSize(), null);
    }
}
