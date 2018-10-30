package project.characters;

import project.GameTest;
import project.mechanics.Board;
import project.mechanics.DefaultCollisionHandler;
import project.mechanics.ID;
import project.mechanics.ObjHandler;
import project.mechanics.SquareType;

/**
 * Created by Nils Broman
 * This class is used to handle the specific project.characters.GameObject project.characters.DestroyEnemy
 */
public class DestroyEnemy extends GameObject
{

    public DestroyEnemy(int x, int y, double velX, double velY, int size, ID id, ObjHandler handler){super(x, y, velX, velY, size, id, handler);}

    @Override
    public void tick() {
        setX((int) (getX() + getVelX()));
        setY((int) (getY() + getVelY()));
        bounce();
    }

    private void bounce(){

        DefaultCollisionHandler h = new DefaultCollisionHandler();

        switch(h.defaultCollisionHandler(this)){
            case 0:
                //right hit
		setVelX(getVelX() *(-1));
                break;
            case 1:
                //left hit
		setVelX(getVelX() * (-1));
                break;
            case 2:
                //down hit
		setVelY(getVelY() * (-1));
                break;
            case 3:
                //up hit
		setVelY(getVelY() * (-1));
                break;
            default:
                // no collision
        }

        if(h.steppingOnSquareType(this) == SquareType.TRAIL){
            GameTest.getBoard().setSquareType(getX() / Board.getSquareSize(), getY() / Board.getSquareSize(), SquareType.RED_TRAIL);
        }
    }

    public void handleCollision() {
	GameTest.getBoard().getPacMan().resetPacMan();
	GameTest.getBoard().makeTrailSquaresEmpty();
	GameTest.getBoard().deleteTrail();
    }
}
