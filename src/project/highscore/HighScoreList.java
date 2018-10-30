package project.highscore;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the high score list.
 */


public final class HighScoreList
{

    private static final int NR_OF_ENTRIES = 20;

    private static HighScoreList ourInstance = new HighScoreList();

    public static HighScoreList getInstance() {
	return ourInstance;
    }

    private HighScoreList() {
        this.highscoreList = new ArrayList<>();
    }

    private static final HighScoreList INSTANCE = new HighScoreList(); // del?
    private List<HighScore> highscoreList;

    public void addScore(HighScore highScores) {
        highscoreList.add(highScores);
	highscoreList.sort(new ScoreCompare());
    }

    @Override
    public String toString() {
    StringBuilder stringBuilder = new StringBuilder();

    for (int i = 0; i < highscoreList.size() && i < NR_OF_ENTRIES; ++i) {
        stringBuilder.append(" Name : ");
        stringBuilder.append(highscoreList.get(i).getName());
        stringBuilder.append(" Score : ");
        stringBuilder.append(highscoreList.get(i).getScore());
        stringBuilder.append("\n");
    }

    return stringBuilder.toString();
    }

    public int size(){
        return highscoreList.size();
    }

    public String getString(int i){
        return " Name : " + highscoreList.get(i).getName() + " Score : " + highscoreList.get(i).getScore();
    }

}
