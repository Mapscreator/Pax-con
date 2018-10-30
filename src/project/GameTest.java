package project;

import project.graphics.GameFrame;
import project.mechanics.Board;

/**
 * Created by Nils Broman.
 */
public final class GameTest {

    private static Board board = null;
    private final static int BOARD_WIDTH = 50, BOARD_HEIGHT = 50;

    private GameTest() {
    }

    public static void main(String[] args) {
        board = new Board(BOARD_WIDTH, BOARD_HEIGHT);
        GameFrame frame = new GameFrame(board);
        frame.addKeyListener(new KeyActionListener());
        frame.addMouseListener(new MouseActionListener());
        frame.pack();
        frame.setVisible(true);

        board.updateBoard();
    }

    public static Board getBoard() {
	return board;
    }
}
