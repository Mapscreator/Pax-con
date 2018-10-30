package project.highscore;

/**
 * This class handles high scores.
 */

public class HighScore
{
    private String name;
    private int score;

    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    String getName() {
        return name;
    }

    int getScore() { return score; }
}
