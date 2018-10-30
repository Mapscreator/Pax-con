package project.mechanics;

import project.GameTest;
import project.characters.GameObject;

/**
 * Created by Nils Broman.
 * This class is used to detect collisions between different game objects and
 * collision with the environment.
 */
public class DefaultCollisionHandler implements CollisionHandler
{

    private Board board = GameTest.getBoard();

    public int defaultCollisionHandler(GameObject obj) {

	int x = obj.getX() / Board.getSquareSize();
	int y = obj.getY() / Board.getSquareSize();
	int offset = (Board.getSquareSize() - 1);

	int nrPixelsOff = 5;

	for (int i = obj.getX(); i < obj.getX() + obj.getSize(); ++i) {
	    for (int j = obj.getY(); j < obj.getY() + obj.getSize(); ++j) {

		if (board.getSquareType((i + nrPixelsOff) / Board.getSquareSize(), j / Board.getSquareSize()) !=
		    SquareType.EMPTY) {
		    System.out.println("Right hit");
		    destroyWall(obj, (i + nrPixelsOff) / Board.getSquareSize(), j / Board.getSquareSize());
		    return 0;
		}
		if (board.getSquareType((i - nrPixelsOff) / Board.getSquareSize(), j / Board.getSquareSize()) !=
		    SquareType.EMPTY) {
		    System.out.println("Left hit");
		    destroyWall(obj, (i - nrPixelsOff) / Board.getSquareSize(), j / Board.getSquareSize());
		    return 1;
		}
		if (board.getSquareType((i) / Board.getSquareSize(), (j + nrPixelsOff) / Board.getSquareSize()) !=
		    SquareType.EMPTY) {
		    System.out.println("Down hit");
		    destroyWall(obj, (i) / Board.getSquareSize(), (j + nrPixelsOff) / Board.getSquareSize());
		    return 2;
		}
		if (board.getSquareType((i) / Board.getSquareSize(), (j - nrPixelsOff) / Board.getSquareSize()) !=
		    SquareType.EMPTY) {
		    System.out.println("Up hit");
		    destroyWall(obj, (i) / Board.getSquareSize(), (j - nrPixelsOff) / Board.getSquareSize());
		    return 3;
		}

		if (board.getSquareType(i / Board.getSquareSize(), j / Board.getSquareSize()) == SquareType.DONE) {
		    if (obj.getId() == ID.BASIC_ENEMY) {
			board.removeEnemy(obj);
		    }
		}

	    }
	}

	if (board.getSquareType(x + 1, y) == SquareType.DONE && board.getSquareType(x - 1, y) == SquareType.DONE &&
	    board.getSquareType(x, y + 1) == SquareType.DONE && board.getSquareType(x, y - 1) == SquareType.DONE) {
	    board.removeEnemy(obj);
	}

	return -1;
    }


    private int bounceOnWalls(int x, int y){
        if(x/Board.getSquareSize() == 1){
            return 0;
        }
        else if(x/Board.getSquareSize() == board.getWidth()-2){
            return 1;
        }
        else if(y/Board.getSquareSize() == board.getHeight()-2){
            return 2;
        }
        else{
            return 3;
        }
    }

    private int bounceNew(int i, int j, GameObject obj) {

	int sqSize = Board.getSquareSize();

	if ((board.getSquareType(i + 1, j) == SquareType.DONE || board.getSquareType(i + 1, j) == SquareType.TRAIL)) {
	    if (board.getSquareType(i, j - 1) == SquareType.EMPTY ||
		board.getSquareType(i, j + 1) == SquareType.EMPTY) {
		return 0;
	    }else{
	        return 2;
	    }
	}
	else if ((board.getSquareType(i - 1, j) == SquareType.DONE || board.getSquareType(i - 1, j) == SquareType.TRAIL)) {
	    if (board.getSquareType(i, j - 1) == SquareType.EMPTY ||
		board.getSquareType(i, j + 1) == SquareType.EMPTY) {
		return 1;
	    }else{
	        return 2;
	    }
	}else{
	    return 2;
	}
	/*
	if(board.getSquareType(i, j +1) == project.mechanics.SquareType.DONE || board.getSquareType(i, j +1) == project.mechanics.SquareType.TRAIL){
	    if(board.getSquareType(i - 1, j - 1) == project.mechanics.SquareType.EMPTY || board.getSquareType(i +1, j -1) == project.mechanics.SquareType.EMPTY){
	        return 2;
	    }
	}
	if(board.getSquareType(i, j -1) == project.mechanics.SquareType.DONE || board.getSquareType(i, j-1) == project.mechanics.SquareType.TRAIL){

	}
	*/
	    //if(board.getSquareType(obj.getX()/sqSize, (obj.getY() - sqSize)/sqSize) == project.mechanics.SquareType.DONE){
	    //System.out.println("No hit");
	}

    private int bounceOnTrail(int i, int j, GameObject obj){
        for(int x = (i/Board.getSquareSize())-1; x <= (i/Board.getSquareSize())+1 ; ++x){
            for(int y = (j/Board.getSquareSize())-1; y <= (j/Board.getSquareSize())+1; ++y){

                if(board.getSquareType(x, y) == SquareType.DONE){

                    int objX = obj.getX() % Board.getSquareSize();
                    int objY = obj.getY() % Board.getSquareSize();

		    System.out.println(" X : " + objX);
		    System.out.println(" Y : " + objY);

                    if(board.getSquareType(objX + 1, objY) == SquareType.DONE){
                            destroyWall(obj, objX + 1, objY);
                            //System.out.println("right hit?!");
                            return 0;
                    }
                    else if(board.getSquareType(objX -1, objY) == SquareType.DONE){
                        //if(x == i/20 +1 && (y == j/20-1 || y == j/20)){ // to many or?! :o
                            destroyWall(obj, objX-1, objY);
                            //System.out.println("left hit?");
                            return 1;
                        //}
                    }
                    else if(board.getSquareType(objX, objY + 1) == SquareType.DONE){
                        destroyWall(obj, objX, objY + 1);
                        return 2; // down
                    }
                    else{ //  if(y == (i/20)+1 && x == (j/20) || x == (j/20)-1 || x == (j/20)+1)
                        destroyWall(obj, objX, objY - 1); // fixa denna!!! :o :o :O :O
                        return 3; //up
                    }
                }
            }
        }
        return -1;
    }

    private void destroyWall(GameObject obj, int x, int y){
        if(obj.getId() == ID.DESTROY_ENEMY){
            if(board.getSquareType(x, y) != SquareType.HARD_FRAME){
		board.setSquareType(x, y, SquareType.EMPTY);
  		board.setPercentage();
	    }
        }
    }

    public SquareType steppingOnSquareType(GameObject obj){
        for(int i = obj.getX(); i < obj.getX() + obj.getSize() ; ++i){
            for(int j = obj.getY(); j < obj.getY() + obj.getSize(); ++j){
		return board.getSquareType(i/Board.getSquareSize(), j/Board.getSquareSize());
            }
        }
        return null;
    }


    @Override public boolean hasCollision() {
	return false;
    }
}
