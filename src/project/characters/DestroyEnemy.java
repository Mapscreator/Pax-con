package project.characters;

import project.Game;
import project.mechanics.Board;
import project.mechanics.Type;
import project.mechanics.GameObjectHandler;
import project.mechanics.SquareType;

/**
 * Created by Nils Broman.
 * This class is used to handle the specific GameObject DestroyEnemy
 */
public class DestroyEnemy extends Enemy
{
    public DestroyEnemy(int x, int y, int velX, int velY, int size, Type type, GameObjectHandler handler){
        super(x, y, velX, velY, size, type, handler);
    }

    /**
     * This method deletes the block that the Destroy Enemy just collided with.
     * @param x coordinate of block that is to be destroyed
     * @param y coordinate fo block that is to be destroyed
     */
    @Override public void reactToWallCollision(final int x, final int y) {
	Board board = Game.getBoard();
        if(board.getSquareType(x, y) == SquareType.DONE){
	    board.setSquareType(x, y, SquareType.EMPTY);
	    board.setPercentage();
	}
    }
}
