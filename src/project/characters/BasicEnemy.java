package project.characters;

import project.GameTest;
import project.mechanics.Board;
import project.mechanics.DefaultCollisionHandler;
import project.mechanics.ID;
import project.mechanics.ObjHandler;
import project.mechanics.SquareType;

/**
 * Created by Nils Broman
 * This class is used to handle the specific project.characters.GameObject project.characters.BasicEnemy.
 */
public class BasicEnemy extends GameObject
{

    private int timer = 0;
    private boolean startTimer = false;

    DefaultCollisionHandler h = new DefaultCollisionHandler();


    public BasicEnemy(int x, int y, double velX, double velY, int size, ID enemy, ObjHandler handler){
        super(x, y, velX, velY, size, enemy, handler);
    }

    @Override
    public void tick() {

        if(Board.getEnemyStartVelX() != getVelX() && Board.getEnemyStartVelY() != getVelY()){ // if speed changed
	    startTimer = true;
        }

        /*
	final int endTime = 350;
	if(timer == endTime){
            startTimer = false;
	    timer = 0;
	    if(getVelX() > 0){
		setVelX(getVelX() -Board.getEnemyStartVelX());
	    }else{
		setVelX(Board.getEnemyStartVelX());
	    }
	    if(getVelY() > 0){
		getVelY() = Board.getEnemyStartVelY();
	    }else{
		getVelY() = -Board.getEnemyStartVelY();
	    }
	}
	*/


        if(startTimer){
	    timer++;
	}

	setX((int) (getX() + getVelX()));
	setY((int) (getY() + getVelX()));

        bounce();

	setX(clamp(getX(), Board.getSquareSize(), Board.getSquareSize()*GameTest.getBoard().getWidth() - Board.getSquareSize()*2)); // magic
	setY(clamp(getY(), Board.getSquareSize(), Board.getSquareSize()*GameTest.getBoard().getHeight() - Board.getSquareSize()*2));

    }

    private void bounce(){

        switch(h.defaultCollisionHandler(this)){
            case 0:
            case 1:
                //left of right hit
		System.out.println("velx = " + getVelX());
		setVelX(getVelX() * (-1));
		System.out.printf("after VelX = " + getVelX());
		break;
            case 2:
            case 3:
                //up or down hit
		System.out.println("vely = " + getVelY());
		setVelY(getVelY() * (-1));
		System.out.println("after VelY = " + getVelY());
		break;
            default:
                //no collision;
        }

        if(h.steppingOnSquareType(this) == SquareType.TRAIL){
            GameTest.getBoard().setSquareType(getX() / Board.getSquareSize(), getY() / Board.getSquareSize(), SquareType.RED_TRAIL);
        }
    }

    private static int clamp(int compareValue, int min, int max){
	if(compareValue >= max){return max;}
	else if(compareValue <= min){return min;}
	return compareValue;
    }

    public void handleCollision() {
	GameTest.getBoard().getPacMan().resetPacMan();
	GameTest.getBoard().makeTrailSquaresEmpty();
	GameTest.getBoard().deleteTrail();
    }
}
