package project.characters;

import project.GameTest;
import project.mechanics.Board;
import project.mechanics.DefaultCollisionHandler;
import project.mechanics.ID;
import project.mechanics.ObjHandler;
import project.mechanics.SquareType;

/**
 * Created by Nils.
 */
public class PacMan extends GameObject
{
    private final static int START_SPEED = 5;
    private int speed = START_SPEED;
    private boolean lastRight = false, lastLeft = false, lastUp = false, lastDown = false;

    private final static int START_LIVES = 3;
    private int lives = START_LIVES;
    private int time = 0;

    private final static String RIGHT_IMG = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\img\\right.png",
				LEFT_IMG = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\img\\left.png",
				UP_IMG = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\img\\up.png",
				DOWN_IMG = "C:\\Users\\Admin\\IdeaProjects\\Pax-con\\img\\down.png";
    private static String img = RIGHT_IMG;


    public PacMan(int x, int y, double velX, double velY, int size, ID id, ObjHandler handler) {
        super(x, y, velX, velY, size, id, handler);
    }

    @Override public void handleCollision() {

    }

    public void resetPacMan(){
	setX(Board.getSquareSize());
	setY(Board.getSquareSize());
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

        /* Flytta denna hantering till SpeedUp */
	if(START_SPEED != speed){
	    time++;
            if(time > 300){
                speed = START_SPEED;
                time = 0;
	    }
	}

        setX(clamp(getX(), Board.getSquareSize(), Board.getSquareSize()*GameTest.getBoard().getWidth() - Board.getSquareSize()*2));
        setY(clamp(getY(), Board.getSquareSize(), Board.getSquareSize()*GameTest.getBoard().getHeight() - Board.getSquareSize()*2));

        collision();
        collisionWithBoard();
    }

    private void collision(){
        for(int i = 0; i < getHandler().getObjects().size(); ++i){
            GameObject tempObj = getHandler().getObjects().get(i);
	    if(getBounds().intersects(tempObj.getBounds())) {
	        if (tempObj.getId() != ID.PACMAN) {
		    tempObj.handleCollision();
		}
	    }
        }
    }

    private void collisionWithBoard(){
        DefaultCollisionHandler h = new DefaultCollisionHandler();

        if(h.steppingOnSquareType(this) == SquareType.EMPTY ){//|| h.steppingOnSquareType(this) == project.mechanics.SquareType.RED_TRAIL){
            GameTest.getBoard().addToTrail(getX()/Board.getSquareSize(), getY()/Board.getSquareSize());
            GameTest.getBoard().setSquareType(getX()/Board.getSquareSize(), getY()/Board.getSquareSize(), SquareType.LAST_TRAIL);
        }
        else if(h.steppingOnSquareType(this) == SquareType.HARD_FRAME || h.steppingOnSquareType(this) == SquareType.DONE){
            GameTest.getBoard().lockTrail();
        }
        else if(h.steppingOnSquareType(this) == SquareType.TRAIL){
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

    private static int clamp(int compareValue, int min, int max){
        if(compareValue >= max){return max;}
        else if(compareValue <= min){return min;}
        return compareValue;
    }

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
}
