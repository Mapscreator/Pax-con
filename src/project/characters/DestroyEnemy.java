package project.characters;

import project.GameTest;
import project.mechanics.Board;
import project.mechanics.DefaultCollisionHandler;
import project.mechanics.ID;
import project.mechanics.ObjHandler;

/**
 * Created by Nils Broman.
 * This class is used to handle the specific GameObject DestroyEnemy
 */
public class DestroyEnemy extends GameObject
{

    public DestroyEnemy(int x, int y, double velX, double velY, int size, ID id, ObjHandler handler){super(x, y, velX, velY, size, id, handler);}

    @Override
    public void tick() {
        setX((int) (getX() + getVelX()));
        setY((int) (getY() + getVelY()));
        bounceOnCollision();

        int squareSize = GameTest.getBoard().getSquareSize();

	setX(clamp(getX(), squareSize, squareSize*GameTest.getBoard().getWidth() - GameTest.getBoard().getSquareSize()*2));
	setY(clamp(getY(), squareSize, squareSize*GameTest.getBoard().getHeight() - GameTest.getBoard().getSquareSize()*2));

    }

    private void bounceOnCollision(){

        DefaultCollisionHandler h = new DefaultCollisionHandler();

        switch(h.hasCollision(this)){
            case 0:
                //right or left hit
		velX *= (-1);
                break;
            case 1:
                //down or up hit
		velY *= (-1);
                break;
	    case 2:
	        // collision with corner
	        velX *= (-1);
	        velY *= (-1);
		break;
	    default:
                // no collision
        }
    }

    public void handleCollision() {
	Board.getPacMan().resetPacMan();
	GameTest.getBoard().makeTrailSquaresEmpty();
	GameTest.getBoard().deleteTrail();
	GameTest.getBoard().loseOneLife();
    }
}
