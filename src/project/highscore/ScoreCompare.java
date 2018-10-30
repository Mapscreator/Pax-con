package project.highscore;

import java.util.Comparator;

/**
 * Created by Nils Broman.
 * This class is used to compare scores to the high score list.
 */
public class ScoreCompare implements Comparator<HighScore>
{
    public int compare(HighScore high1, HighScore high2) {
            if (high1.getScore() > high2.getScore()) {
                return -1;
            } else {
                return 1;
            }
        }
}
