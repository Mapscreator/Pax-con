package project.characters;

import project.Game;
import project.mechanics.Type;
import project.mechanics.GameObjectHandler;
import project.collisiondetection.PacManCollisionHandler;
import project.mechanics.SquareType;

/**
 * Created by Nils Broman.
 * This class handles the main character PacMan. Such as choosing image, steering and collision with different
 * square types.
 */
public class PacMan extends GameObject
{
    private final static int START_SPEED = 5;
    private int speed;
    private boolean lastRight = false, lastLeft = false, lastUp = false, lastDown = false;

    private final static int START_LIVES = 3;
    private int lives;

    private final static String RIGHT_IMG = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\img\\right.png",
				LEFT_IMG = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\img\\left.png",
				UP_IMG = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\img\\up.png",
				DOWN_IMG = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\img\\down.png";
    private String img = RIGHT_IMG;

    public PacMan(int x, int y, int size, Type type, GameObjectHandler handler) {
        super(x, y, size, type, handler, new PacManCollisionHandler());
	speed = START_SPEED;
	lives = START_LIVES;
    }

    public void resetPacMan(){
	setX(Game.getBoard().getSquareSize());
	setY(Game.getBoard().getSquareSize());
    }

    @Override
    public void tick(){

        if(lastRight){
            img = RIGHT_IMG;
            moveRight();
            setY(correctMove(getY()));
        }else if(lastLeft){
            img = LEFT_IMG;
            moveLeft();
	    setY(correctMove(getY()));
        }else if(lastUp){
            img = UP_IMG;
            moveUp();
	    setX(correctMove(getX()));
        }else if(lastDown){
            img = DOWN_IMG;
            moveDown();
	    setX(correctMove(getX()));
        }

        setX(clamp(getX(), Game.getBoard().getSquareSize(), Game.getBoard().getSquareSize() * Game.getBoard().getWidth() - Game.getBoard().getSquareSize() * 2));
        setY(clamp(getY(), Game.getBoard().getSquareSize(), Game.getBoard().getSquareSize() * Game.getBoard().getHeight() - Game.getBoard().getSquareSize() * 2));
    }

    /**
     * This method calls necessary functions for when PacMan steps on a empty square, eg adding a trail-square.
     * @param x coordinate of empty square
     * @param y coordinate of empty square
     */
    public void steppingOnEmptySquare(final int x, final int y) {
  	Game.getBoard().addToTrail(x, y);
  	Game.getBoard().setSquareType(x, y, SquareType.LAST_TRAIL);
    }

    /**
     * This method calls a function to lock the trail when PacMan enters a "safe" square.
     */
    public void steppingOnHardFrame() {
	Game.getBoard().lockTrail();
    }

    /**
     * This method calls necessary functions for when PacMan steps on it's own trail.
     */
    public void steppingOnTrail(){
	resetPacMan();
	Game.getBoard().loseOneLife();
	Game.getBoard().makeTrailSquaresEmpty();
	Game.getBoard().deleteTrail();
    }

    /**
     * Corrects the move of pacman, making him walk exactly according to the grid and not between squares.
     * @param coordinate is the x or y coordinate that is to be corrected
     * @return new coordinate to Pacman, corrected according to the grid
     */
    private int correctMove(int coordinate) {
	if(coordinate % getSize() != 0){
	    if(coordinate % getSize() > getSize() / 2){
		coordinate = coordinate - (coordinate % getSize()) + getSize();
	    }else{
		coordinate -= (coordinate % getSize());
	    }
	}
        return coordinate;
    }

    private void moveRight(){
	setX(getX() + speed);
    }
    private void moveLeft(){
	setX(getX() - speed);
    }
    private void moveUp(){
	setY(getY() - speed);
    }
    private void moveDown(){
	setY(getY() + speed);
    }

    /**
     * These four methods below are to set booleans of the last pressed key. Depending on which is last pressed, the
     * PacMan character moves in different directions.
     */
    public void setLastRight(){
        lastRight = true;
        lastDown = false;
        lastLeft= false;
        lastUp = false;
    }
    public void setLastLeft(){
        lastRight = false;
        lastDown = false;
        lastLeft = true;
        lastUp = false;
    }
    public void setLastDown(){
        lastRight=false;
        lastDown = true;
        lastLeft= false;
        lastUp = false;
    }
    public void setLastUp(){
        lastRight = false;
        lastDown = false;
        lastLeft = false;
        lastUp = true;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(final int lives) {	// Warning that lives value is always 3. I don't want to change it because
						// the value is supposed to be replaceable
	this.lives = lives;
    }

    public void changeLives(int change) {
        lives += change;
    }

    public void changeSpeed(int change){
        speed += change;
    }

    public String getImg() {
	return img;
    }

    public void setSpeed(final int speed) {
	this.speed = speed;
    }

    public int getStartSpeed() {
	return START_SPEED;
    }

}
