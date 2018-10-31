package project.characters;

import project.GameTest;
import project.mechanics.Board;
import project.mechanics.DefaultCollisionHandler;
import project.mechanics.ID;
import project.mechanics.ObjHandler;

/**
 * Created by Nils Broman.
 * This class is used to handle the specific GameObject BasicEnemy.
 */
public class BasicEnemy extends GameObject
{

    private DefaultCollisionHandler h = new DefaultCollisionHandler();

    public BasicEnemy(int x, int y, double velX, double velY, int size, ID enemy, ObjHandler handler){
        super(x, y, velX, velY, size, enemy, handler);
    }

    @Override
    public void tick() {

        y += velY;
	x += velX;

        bounceOnCollision();

        int squareSize = GameTest.getBoard().getSquareSize();

	setX(clamp(getX(), squareSize, squareSize*GameTest.getBoard().getWidth() - GameTest.getBoard().getSquareSize()*2));
	setY(clamp(getY(), squareSize, squareSize*GameTest.getBoard().getHeight() - GameTest.getBoard().getSquareSize()*2));
    }

    private void bounceOnCollision(){

        switch(h.hasCollision(this)){
            case 0:
                // left or right hit
                velX *= (-1);
		return;
            case 1:
                //up or down hit
		velY *= (-1);
		return;
	    case 2:
	        // Corner hit
		velX *= (-1);
	        velY *= (-1);
		break;
	    default:
                break;
                //no collision;
        }

    }

    public void handleCollision() {
	Board.getPacMan().resetPacMan();
	GameTest.getBoard().makeTrailSquaresEmpty();
	GameTest.getBoard().deleteTrail();
	GameTest.getBoard().loseOneLife();
    }
}
