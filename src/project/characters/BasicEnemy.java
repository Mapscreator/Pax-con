package project.characters;

import project.Game;
import project.mechanics.Board;
import project.mechanics.Type;
import project.mechanics.GameObjectHandler;
import project.mechanics.SquareType;

/**
 * Created by Nils Broman.
 * This class is used to handle the specific GameObject BasicEnemy.
 */
public class BasicEnemy extends Enemy
{
    private Board board = Game.getBoard();

    public BasicEnemy(int x, int y, int velX, int velY, int size, Type enemy, GameObjectHandler handler){
        super(x, y, velX, velY, size, enemy, handler);
    }

    /**
     * This method deletes the Basic Enemy of it's surrounded by blocks that are not empty.
     * @param x not used
     * @param y not used
     */
    @Override public void reactToWallCollision(final int x, final int y) {


	int xEnemy = this.x / Game.getBoard().getSquareSize();
	int yEnemy = this.y / Game.getBoard().getSquareSize();

	if(isSurrounded(xEnemy, yEnemy)) {
		board.removeEnemy(this);
	}
    }

    private boolean isSurrounded(int xEnemy, int yEnemy){
        // Warning, overly complex boolean expression, this needs to be checked since we need to know if the
	// enemy is surrounded
	return xEnemy == 0 && board.getSquareType(xEnemy, yEnemy + 1) != SquareType.EMPTY &&
		   board.getSquareType(xEnemy, yEnemy - 1) != SquareType.EMPTY ||
		   yEnemy == 0 && board.getSquareType(xEnemy + 1, yEnemy) != SquareType.EMPTY &&
		   board.getSquareType(xEnemy - 1, yEnemy) != SquareType.EMPTY ||
		   board.getSquareType(xEnemy + 1, yEnemy) != SquareType.EMPTY &&
		   board.getSquareType(xEnemy - 1, yEnemy) != SquareType.EMPTY &&
		   board.getSquareType(xEnemy, yEnemy + 1) != SquareType.EMPTY &&
		   board.getSquareType(xEnemy, yEnemy - 1) != SquareType.EMPTY;
    }


}
