package project.graphics;

import javax.swing.*;
import java.awt.*;
import project.mechanics.Board;

/**
 * Created by Nils Broman
 * This class handles the Frame of the game.
 */
public class GameFrame extends JFrame{

    private static GameComponent gameComponent = null;

    public GameFrame(Board board) {
        super("Pax-con");

	GameComponent gameComponent = new GameComponent(board);

        this.setLayout(new BorderLayout());
        this.add(gameComponent, BorderLayout.CENTER);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public static GameComponent getGameComponent() {
	return gameComponent;
    }
}
