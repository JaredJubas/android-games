package group0681phase2.gamecenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * The Simon game.
 */
class SimonGame {

    /**
     * The pattern of the colors.
     */
    public ArrayList<Integer> pattern = new ArrayList<>();

    /**
     * The list for the slow scoreboard.
     */
    private static ArrayList<String> slowScoreboard = new ArrayList<>();

    /**
     * The list for the normal scoreboard.
     */
    private static ArrayList<String> normalScoreboard = new ArrayList<>();

    /**
     * The list for the fast scoreboard.
     */
    private static ArrayList<String> fastScoreboard = new ArrayList<>();

    /**
     * The list for the insane scoreboard.
     */
    private static ArrayList<String> insaneScoreboard = new ArrayList<>();

    /**
     * Add another color to the pattern.
     */
    public void nextButton() {
        Random rand = new Random();
        int next = rand.nextInt(4);
        pattern.add(next);
    }

    /**
     * Get the pattern.
     *
     * @return the pattern
     */
    public ArrayList<Integer> getPattern() {
        return this.pattern;
    }

    /**
     * Get the scores.
     *
     * @return the scores
     */
    public ArrayList<String> getScores() {
        ArrayList<String> selectedList;
        String user;
        if (GameSelectActivity.username.equals("")) {
            user = "Guest";
        } else {
            user = GameSelectActivity.username;
        }
        int score = SimonGameActivity.score;

        if (ComplexityActivity.complexity.get(0) == 1) {
            selectedList = slowScoreboard;
        } else if (ComplexityActivity.complexity.get(1) == 1) {
            selectedList = normalScoreboard;
        } else if (ComplexityActivity.complexity.get(2) == 1) {
            selectedList = fastScoreboard;
        } else {
            selectedList = insaneScoreboard;
        }
        selectedList.add(score + "\t\t\t\t" + user);
        Collections.sort(selectedList);
        Collections.reverse(selectedList);
        return selectedList;
    }
}