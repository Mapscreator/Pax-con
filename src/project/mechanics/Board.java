package project.mechanics;

import project.highscore.HighScore;
import project.highscore.HighScoreList;
import project.Sound;
import project.characters.BasicEnemy;
import project.characters.DestroyEnemy;
import project.characters.GameObject;
import project.characters.PacMan;
import project.powerups.SlowDownEnemies;
import project.powerups.SpeedUp;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

/**
 * Created by Nils Broman.
 * This class creates the game board as well as handles all the game mechanics.
 */
public class Board {

    private GameState state = GameState.START_SCREEN;
    private List<BoardListener> boardListeners;
    private static ObjHandler handler = new ObjHandler();

    private int width, height;
    private SquareType[][] squares;
    private final static int SQUARE_SIZE = 20;

    private static PacMan pacMan = null;
    private final static int PACMAN_START_X = 20, PACMAN_START_Y = 20;
    private List<Point> trail = new ArrayList<>();
    private double percentageComplete = 0;

    private final static int NR_LEVELS = 10;
    private static List<LevelsUnlocked> levelsUnlocked = new ArrayList<>();
    private Level level = Level.LEVEL1;

    private int score = 0;
    private final static int LOSE_SCORE_ON_DEATH = 100, DELETE_ENEMY_SCORE = 200;

    private int nrPowerUps = 0, nrDestroyEnemies = 0, nrBasicEnemies = 0;
    private final static double ENEMY_START_VEL_X = 3, ENEMY_START_VEL_Y = 2;
    private final static int POWER_UPS_RNG_SPAWN = 200; // 400
    private static final int MAXIMUM_POWER_UPS = 3;

    private int timePassed = 0;
    private final static int PAUSE_DELAY = 3000;

    private static final String LOSE_ONE_LIFE_SOUND = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\Sounds\\no-4.wav",
	    COMPLETE_LEVEL_SOUND = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\Sounds\\no-4.wav";

    private boolean gameOver = false;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
	squares = new SquareType[width][height];
	boardListeners = new ArrayList<>();

	for (int i = 0; i < width; i++) {
	    for (int j = 0; j < height; j++) {
		if (i == 0 || i == width-1 || j == 0 || j == height-1) {
		    squares[i][j] = SquareType.OUTSIDE;
		}
		else if(i == 1 || i == width-2 || j == 1 || j == height-2){
		    squares[i][j] = SquareType.HARD_FRAME;
		}
		else {
		    squares[i][j] = SquareType.EMPTY;
		}
	    }
	}
	this.notifyListeners();
    }

    private void resetBoard(){
    	clearBoard();
    	deleteAllObjects();
    	updateBoard();
    }

    public void updateBoard() {
	initLevelsUnlocked();
	clockTimer.setCoalesce(true);
	clockTimer.start();
	spawnPacMan();
	spawnEnemies();
    }

    private void clearBoard(){
	for(int i = 0; i < height; ++i){
	    for(int j = 0; j < width; ++j){
		if(getSquareType(i, j) == SquareType.DONE || getSquareType(i, j) == SquareType.RED_TRAIL || getSquareType(i, j) == SquareType.TRAIL){
		    setSquareType(i, j , SquareType.EMPTY);
		}
	    }
	}
    }

    private void deleteAllObjects(){
	while(!handler.getObjects().isEmpty()){
	    handler.getObjects().remove(handler.getObjects().get(0));
	}
    }

    public void addBoardListener(BoardListener bl) {
                this.boardListeners.add(bl);
            }

    private void notifyListeners() {
    	for (BoardListener boardListener : boardListeners) {
    	    boardListener.boardChanged();
    	}
        }

    private final Action doOneStep = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            tick();
        }
    };
    private final Timer clockTimer = new Timer(30, doOneStep);

    /**
     * This tick function is the main game-loop. It handles everything affecting the board while playing the game.
     */

    private void tick() {

        switch(state){
	    case PLAY:
		if (gameOver) {
		    enterHighScore();
		    state = GameState.END_SCREEN;
		}
		checkTrail();
		spawnPowerUps();
		spawnEnemies();
		final int percentToCompleteLevel = 80;
		if(percentageComplete >= percentToCompleteLevel){
		    Sound.playMusic(COMPLETE_LEVEL_SOUND);
		    timePassed = 0;
		    state = GameState.PAUSE_SCREEN;
		}
		if(pacMan.getLives() <= 0){
		    gameOver = true;
		}
		handler.tick();
		notifyListeners();
	        break;
	    case PAUSE_SCREEN:
		pauseBetweenLevels();
		notifyListeners();
		break;
	    case LEVELS_UNLOCKED:
		notifyListeners();
		break;
	    case END_SCREEN:
	        notifyListeners();
	        break;
	    case START_SCREEN:
	        notifyListeners();
	        break;
	    default:
		System.out.println("No game State found");
	}
    }

    /**
     * This function makes the game pause in between levels, as well as clears the board and updates the current level.
     */

    private void pauseBetweenLevels(){
        timePassed += clockTimer.getDelay();
        if(timePassed >= PAUSE_DELAY){
	    percentageComplete = 0;
            resetBoard();
            deleteAllObjects();
	    level = nextLevel();
            updateBoard();
            pacMan.changeLives(+1);
            state = GameState.PLAY;
        }
    }

    public void resetGameValues() {

	resetLevelValues();
	pacMan.setLives(3);
	this.score = 0;
	gameOver = false;
	this.level = Level.LEVEL1;
	resetBoard();

    }

    /* Spawn methods */

    private void spawnPowerUps(){
       Random rng = new Random();

       if(rng.nextInt(POWER_UPS_RNG_SPAWN) == 1){
	   spawnDifferentPowerUps();
       }
    }

    private void spawnDifferentPowerUps() {
	Random rng = new Random();
	int x = rng.nextInt((width * SQUARE_SIZE) - (5 * SQUARE_SIZE)) + (2 * SQUARE_SIZE);
	int y = rng.nextInt((width * SQUARE_SIZE) - (5 * SQUARE_SIZE)) + (2 * SQUARE_SIZE);
	int nextPowerUp = rng.nextInt(2);

	if(nrPowerUps < MAXIMUM_POWER_UPS){
	   if(nextPowerUp == 0){
	       handler.addObject(new SlowDownEnemies(x, y,
						     SQUARE_SIZE * 2, ID.SLOW_DOWN_ENEMIES_POWER_UP, handler));
	   }else if(nextPowerUp == 1){
	       handler.addObject(new SpeedUp(x, y,
					     SQUARE_SIZE * 2, ID.SPEED_POWER_UP, handler));
	   }
	   nrPowerUps++;
	}
    }

    private void spawnEnemies() {
        Random rng = new Random();
        spawnBasic(rng);
        spawnDestroy(rng);
    }

    private void spawnBasic(Random rng){
	while(nrBasicEnemies < level.getBasicEnemy()){
	    int spawnX = rng.nextInt((width * SQUARE_SIZE) - (5 * SQUARE_SIZE)) + (2 * SQUARE_SIZE);
	    int spawnY = rng.nextInt((height * SQUARE_SIZE) - (5 * SQUARE_SIZE)) + (2 * SQUARE_SIZE);

	    if(getSquareType(spawnX/SQUARE_SIZE, spawnY/SQUARE_SIZE) == SquareType.EMPTY){
		BasicEnemy b = new BasicEnemy(spawnX, spawnY,
					   ENEMY_START_VEL_X, ENEMY_START_VEL_Y, SQUARE_SIZE, ID.BASIC_ENEMY, handler);
		handler.addObject(b);
		nrBasicEnemies++;
	    }
        }
    }

    private void spawnDestroy(Random rng){
        while(nrDestroyEnemies < level.getLargeEnemy()){
	    int spawnX = rng.nextInt((width * SQUARE_SIZE )- (6 * SQUARE_SIZE)) + (2 * SQUARE_SIZE);
	    int spawnY = rng.nextInt((height * SQUARE_SIZE) - (6 * SQUARE_SIZE)) + (2 * SQUARE_SIZE);

	    if(getSquareType(spawnX/SQUARE_SIZE, spawnY/SQUARE_SIZE) == SquareType.EMPTY){
		DestroyEnemy d = new DestroyEnemy(spawnX, spawnY,
						  ENEMY_START_VEL_X, ENEMY_START_VEL_Y,
						  SQUARE_SIZE * 2, ID.DESTROY_ENEMY, handler);
		handler.addObject(d);
		nrDestroyEnemies++;
	    }
        }
    }

    private void spawnPacMan(){
	pacMan = new PacMan(PACMAN_START_X, PACMAN_START_Y, 0, 0, SQUARE_SIZE, ID.PACMAN, handler);
        handler.addObject(pacMan);
    }

    /* Level methods */

    private void initLevelsUnlocked(){
	levelsUnlocked.add(LevelsUnlocked.UNLOCKED);
	for (int i = 0; i < NR_LEVELS - 1; i++) {
    	    levelsUnlocked.add(LevelsUnlocked.LOCKED);
    	}
    }

    private void resetLevelValues(){
	nrBasicEnemies = 0;
	nrDestroyEnemies = 0;
	nrPowerUps = 0;
    }

    private Level nextLevel() {

        switch(level) {
	    case LEVEL1:
		levelsUnlocked.set(1, LevelsUnlocked.UNLOCKED);
		resetLevelValues();
		return Level.LEVEL2;
	    case LEVEL2:
		levelsUnlocked.set(2, LevelsUnlocked.UNLOCKED);
		resetLevelValues();
		return Level.LEVEL3;
	    case LEVEL3:
	        levelsUnlocked.set(3, LevelsUnlocked.UNLOCKED);
		resetLevelValues();
	        return Level.LEVEL4;
	    case LEVEL4:
	        levelsUnlocked.set(4, LevelsUnlocked.UNLOCKED);
		resetLevelValues();
	        return Level.LEVEL5;
	    case LEVEL5:
		levelsUnlocked.set(5, LevelsUnlocked.UNLOCKED);
		resetLevelValues();
		return Level.LEVEL6;
	    case LEVEL6:
		levelsUnlocked.set(6, LevelsUnlocked.UNLOCKED);
		resetLevelValues();
		return Level.LEVEL7;
	    case LEVEL7:
		levelsUnlocked.set(7, LevelsUnlocked.UNLOCKED);
		resetLevelValues();
		return Level.LEVEL8;
	    case LEVEL8:
		levelsUnlocked.set(8, LevelsUnlocked.UNLOCKED);
		resetLevelValues();
		return Level.LEVEL9;
	    case LEVEL9:
		levelsUnlocked.set(9, LevelsUnlocked.UNLOCKED);
		resetLevelValues();
		return Level.LEVEL10;
	    default:
		System.out.println("Error, no level?");
		return null;
	}
    }

    /* Trail methods */

    /**
     * This function adds to the PacMan trail as PacMan walks. The two first trail-squares are "immune" to collision with PacMan
     * since otherwise the trail couldn't be painted to follow PacMan.
     * @param x coordinate on where to add the square type.
     * @param y coordinate on where to add the square type.
     */

    public void addToTrail(int x, int y){

        if(trail.size() >= 3){
            int oldX = trail.get(trail.size()-2).x;
            int oldY = trail.get(trail.size()-2).y;
            setSquareType(oldX, oldY, SquareType.TRAIL);
	}
        trail.add(new Point(x, y));

    }

    /**
     * This function locks the PacMan trail in place and make a call to fill one of the spaces divided by the trail.
     * Also updates percentage complete.
     */

    public void lockTrail(){
	for (Point aTrail : trail) {
	    setSquareType(aTrail.x, aTrail.y, SquareType.DONE);
	}
        if(!trail.isEmpty()){
            fillSpaces();
        }
        score += trail.size();
        setPercentage();
        trail.clear();
    }

    public void deleteTrail(){
        trail.clear();
    }

    public void makeTrailSquaresEmpty(){
           for (Point aTrail : trail) {
               setSquareType(aTrail.x, aTrail.y, SquareType.EMPTY);
           }
        }

    /**
     * This function makes the trail go red once a single square on the trail is marked red. It gets called every single tick
     * to finally catch up with PacMan leading to losing a life.
     */

    private void checkTrail(){
	if(!trail.isEmpty()){
	    for (int i = 0; i < trail.size() - 1; ++i) {
		if (getSquareType(trail.get(i).x, trail.get(i).y) == SquareType.RED_TRAIL) {
		    if(getSquareType(trail.get(i + 1).x, trail.get(i + 1).y) == SquareType.TRAIL ||
		    getSquareType(trail.get(i + 1).x, trail.get(i + 1).y) == SquareType.LAST_TRAIL){
			setSquareType(trail.get(i + 1).x, trail.get(i + 1).y, SquareType.RED_TRAIL);
			break;
		    }
		}
	    }
	    checkRedTrailCollision();
	}
    }

    /**
     * This function is checking if the red trail has caught up to PacMan, leading to PacMan losing a life.
     */

    private void checkRedTrailCollision() {
	if(getSquareType(trail.get(trail.size()-1).x, trail.get(trail.size()-1).y) == SquareType.RED_TRAIL){
	    makeTrailSquaresEmpty();
	    trail.clear();
	    loseOneLife();
	}
    }

    /**
     * This function is checking on PacMans trail and deciding where to search for potential areas to fill. If PacMan is moving
     * sideways, this function makes a call to compare the segments above and below PacMan. If PacMan is moving vertically,
     * this function makes a call to compare segments to the left and right of the trail.
     */

    private void fillSpaces(){

	if(!trail.isEmpty()){
	    for (Point aTrail : trail) {
		int x = aTrail.x;
		int y = aTrail.y;
		boolean leftOfTrailEmpty = getSquareType(x - 1, y) == SquareType.EMPTY;
		boolean rightOfTrailEmpty = getSquareType(x + 1, y) == SquareType.EMPTY;
		boolean belowTrailEmpty = getSquareType(x, y - 1) == SquareType.EMPTY;
		boolean aboveTrailEmpty = getSquareType(x, y + 1) == SquareType.EMPTY;

		if (leftOfTrailEmpty && rightOfTrailEmpty) {
		    int firstSpace = checkBiggestArea(x - 1, y);
		    int secondSpace = checkBiggestArea(x + 1, y);
		    if (firstSpace < secondSpace) {
			floodFill(x - 1, y);
		    } else if (firstSpace > secondSpace) {
			floodFill(x + 1, y);
		    }
		} else if (belowTrailEmpty && aboveTrailEmpty) {
		    int firstSpace = checkBiggestArea(x, y - 1);
		    int secondSpace = checkBiggestArea(x, y + 1);
		    if (firstSpace < secondSpace) {
			floodFill(x, y - 1);
		    } else if (firstSpace > secondSpace) {
			floodFill(x, y + 1);
		    }
		}
	    }
	}
    }

    /**
     * This function is implemented according to the algorithm flood fill. It starts in a point and basically fills connected
     * points with, in this case, the square type done.
     * @param x start coordinate
     * @param y start coordinate
     */

    private void floodFill(int x, int y){
        if(getSquareType(x, y) == SquareType.DONE){
            return;
        }
        if(getSquareType(x, y) != SquareType.EMPTY){
            return;
        }
        List<Point> floodFillQueue = new ArrayList<>();
        setSquareType(x, y, SquareType.DONE);
        Point node = new Point(x, y);
        floodFillQueue.add(node);
        while(!floodFillQueue.isEmpty()){
            Point n = floodFillQueue.get(0);
            floodFillQueue.remove(0);
            if(getSquareType(n.x, n.y-1) == SquareType.EMPTY){
                setSquareType(n.x,n.y-1,SquareType.DONE);
                floodFillQueue.add(new Point(n.x, n.y-1));
                score += 2;
            }
            if(getSquareType(n.x, n.y+1) == SquareType.EMPTY){
                setSquareType(n.x,n.y+1,SquareType.DONE);
                floodFillQueue.add(new Point(n.x, n.y+1));
                score += 2;
            }
            if(getSquareType(n.x-1, n.y) == SquareType.EMPTY){
                setSquareType(n.x-1,n.y,SquareType.DONE);
                floodFillQueue.add(new Point(n.x-1, n.y));
                score += 2;
            }
            if(getSquareType(n.x+1, n.y) == SquareType.EMPTY){
                setSquareType(n.x+1,n.y,SquareType.DONE);
                floodFillQueue.add(new Point(n.x+1, n.y));
                score += 2;
            }
        }
    }

    /**
     * This function calulates the number of connected empty squares from a given start point. It does this by using the
     * floodFill algoritm as mentioned above but instead of changing the square types if just counts the number of empty squares
     * @param x start coordinate, from which to count connected 'nodes'
     * @param y start coordinate, from which to count connected 'nodes'
     * @return int, how big the connected area was.
     */

    private int checkBiggestArea(int x, int y){
        int i = 0;
        boolean counting = true;
        if(getSquareType(x, y) == SquareType.DONE){
            counting = false;
        }
        if(getSquareType(x, y) != SquareType.EMPTY){
            counting = false;
        }
        if(counting){
            List<Point> counted = new ArrayList<>();
            List<Point> countingQueue = new ArrayList<>();
            Point startNode = new Point(x, y);
            countingQueue.add(startNode);
            counted.add(startNode);
            while(!countingQueue.isEmpty()){
                Point n = countingQueue.get(0);
                countingQueue.remove(0);
                if(getSquareType(n.x, n.y-1) == SquareType.EMPTY){
                    Point p = new Point(n.x, n.y-1);
                    if(!counted.contains(p)){
                        countingQueue.add(p);
                        counted.add(p);
                        i += 1;
                    }
                }
                if(getSquareType(n.x, n.y+1) == SquareType.EMPTY){
                    Point p = new Point(n.x, n.y+1);
                    if(!counted.contains(p)){
                        countingQueue.add(p);
                        counted.add(p);
                        i += 1;
                    }
                }
                if(getSquareType(n.x-1, n.y) == SquareType.EMPTY){
                    Point p = new Point(n.x-1, n.y);
                    if(!counted.contains(p)){
                        countingQueue.add(p);
                        counted.add(p);
                        i += 1;
                    }
                }
                if(getSquareType(n.x+1, n.y) == SquareType.EMPTY){
                    Point p = new Point(n.x+1, n.y);
                    if(!counted.contains(p)){
                        countingQueue.add(p);
                        counted.add(p);
                        i += 1;
                    }
                }
            }
            return i;
        }else{
            return i;
        }
    }

    /**
     * This function calculates and updates how much percentage of the playing field that is covered with done squares or blue
     * squares.
     */

    void setPercentage(){
        int totalBlocks = 0;
        double blocksDone = 0;
	for(int i = 2; i < height - 2; ++i){
	    for(int j = 2; j < width - 2; ++j){
                if(getSquareType(i, j) == SquareType.DONE){
                    blocksDone ++;
                }
                totalBlocks ++;
            }
        }
        percentageComplete = 100 * blocksDone / totalBlocks;
    }

    public void loseOneLife(){
    	Sound.playMusic(LOSE_ONE_LIFE_SOUND);

	pacMan.changeLives(-1);
	pacMan.resetPacMan();
	if(score >= LOSE_SCORE_ON_DEATH){
	    score -= LOSE_SCORE_ON_DEATH;
    	}
    }

    void removeEnemy(final GameObject obj) {
    	handler.removeObject(obj);
    	score += DELETE_ENEMY_SCORE;
    	if(obj.getId() == ID.BASIC_ENEMY){
	    nrBasicEnemies--;
	}else if(obj.getId() == ID.DESTROY_ENEMY){
    	    nrDestroyEnemies--;
	}
    }

    private void enterHighScore() {

    	String name = JOptionPane.showInputDialog("Please insert name for highscore : ");
    	if (name == null) {
    	    name = "";
    	}
	final HighScore highScore = new HighScore(name, score);

    	HighScoreList highScoreList = HighScoreList.getInstance();
    	highScoreList.addScore(highScore);

    }

    public void changeNrOfPowerUps(final int i) {
        nrPowerUps += i;
    }

    /* Getters and setters */

    public void setSquareType(int x, int y, SquareType st) {
	this.squares[x][y] = st;
	this.notifyListeners();
    }

    public SquareType getSquareType(int x, int y) {
            return this.squares[x][y];
        }

    public ObjHandler getHandler() {
    	return handler;
        }

    public int getWidth() {
        return width;
    }

    public int getHeight() { return height; }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public Level getLevel() {
        return level;
    }

    public static PacMan getPacMan() { return pacMan; }

    public int getScore() {
        return score;
    }

    public double getPercentageComplete() {
        return percentageComplete;
    }

    public int getSquareSize() {
	return SQUARE_SIZE;
    }

    public int getNrPowerUps() {
	return nrPowerUps;
    }

    public void setNrPowerUps(final int nrPowerUps) {
	this.nrPowerUps = nrPowerUps;
    }

    public List<LevelsUnlocked> getLevelsUnlocked() { // del?
	return levelsUnlocked;
    }

}
