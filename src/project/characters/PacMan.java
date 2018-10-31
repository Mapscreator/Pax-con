package project.characters;

import project.GameTest;
import project.mechanics.DefaultCollisionHandler;
import project.mechanics.ID;
import project.mechanics.ObjHandler;
import project.mechanics.SquareType;

/**
 * Created by Nils Broman.
 * This class handles the main character PacMan. Such as choosing image, steering and collision with different
 * square types.
 */
public class PacMan extends GameObject
{
    private final static int START_SPEED = 5;
    private int speed = START_SPEED;
    private boolean lastRight = false, lastLeft = false, lastUp = false, lastDown = false;

    private final static int START_LIVES = 3;
    private int lives = START_LIVES;

    private final static String RIGHT_IMG = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\img\\right.png",
				LEFT_IMG = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\img\\left.png",
				UP_IMG = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\img\\up.png",
				DOWN_IMG = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\img\\down.png";
    private static String img = RIGHT_IMG;


    public PacMan(int x, int y, double velX, double velY, int size, ID id, ObjHandler handler) {
        super(x, y, velX, velY, size, id, handler);
    }

    public void handleCollision() {
	for(int i = 0; i < getHandler().getObjects().size(); ++i){
	    GameObject tempObj = getHandler().getObjects().get(i);
	    if(getBounds().intersects(tempObj.getBounds())) {
		if (tempObj.getId() != ID.PACMAN) {
		    tempObj.handleCollision();
		}
	    }
	}
    }

    public void resetPacMan(){
	setX(GameTest.getBoard().getSquareSize());
	setY(GameTest.getBoard().getSquareSize());
	setVelX(0);
	setVelY(0);
    }

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


        setX(clamp(getX(), GameTest.getBoard().getSquareSize(), GameTest.getBoard().getSquareSize()*GameTest.getBoard().getWidth() - GameTest.getBoard().getSquareSize()*2));
        setY(clamp(getY(), GameTest.getBoard().getSquareSize(), GameTest.getBoard().getSquareSize()*GameTest.getBoard().getHeight() - GameTest.getBoard().getSquareSize()*2));

        handleCollision();
        collisionWithBoard();
    }

    private void collisionWithBoard(){
        DefaultCollisionHandler h = new DefaultCollisionHandler();

        if(h.steppingOnSquareType(this) == SquareType.EMPTY){
            GameTest.getBoard().addToTrail(getX()/GameTest.getBoard().getSquareSize(), getY()/GameTest.getBoard().getSquareSize());
            GameTest.getBoard().setSquareType(getX()/GameTest.getBoard().getSquareSize(), getY()/GameTest.getBoard().getSquareSize(), SquareType.LAST_TRAIL);
        }
        else if(h.steppingOnSquareType(this) == SquareType.HARD_FRAME || h.steppingOnSquareType(this) == SquareType.DONE){
            GameTest.getBoard().lockTrail();
        }
        else if(h.steppingOnSquareType(this) == SquareType.TRAIL || h.steppingOnSquareType(this) == SquareType.RED_TRAIL){
            resetPacMan();
            GameTest.getBoard().loseOneLife();
            GameTest.getBoard().makeTrailSquaresEmpty();
            GameTest.getBoard().deleteTrail();
        }

    }

    /**
     * Corrects the move of pacman, making him walk exactly according to the grid and not between squares.
     * @param axis is the x or y axis that is to be corrected
     * @return int to Pacman, corrected according to the grid.
     */

    private int correctMove(int axis) {
	if(axis % getSize() != 0){
	    if(axis % getSize() > getSize() / 2){
		axis = axis - (axis % getSize()) + getSize();
	    }else{
		axis -= (axis % getSize());
	    }
	}
        return axis;
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
     * These four functions below are to set booleans of the last pressed key. Depending on which is last pressed, the
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

    /**
     * Warning that lives value os always 3. I don't want to change it because the value is supposed to be replaceable
     * @param lives PacMans lives left
     */

    public void setLives(final int lives) {
	this.lives = lives;
    }

    public void changeLives(int change) {
        lives += change;
    }

    public void changeSpeed(int change){
        speed += change;
    }

    public static String getImg() {
	return img;
    }

    public void setSpeed(final int speed) {
	this.speed = speed;
    }

    public int getStartSpeed() {
	return START_SPEED;
    }
}
