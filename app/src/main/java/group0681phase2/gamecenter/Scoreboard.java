package group0681phase2.gamecenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;

import java.util.Set;

import android.widget.*;

/**
 * Scoreboard Class
 */
public class Scoreboard implements Serializable {


    /**
     * A Hashtable ocontaining a list of scores by game
     */

    public static Hashtable<String, ArrayList<String>> scoreTable =
            new Hashtable<String, ArrayList<String>>();

    /**
     * Concatenate and order the name, time, and rows
     *
     * @return list of concatenations
     */
    public ArrayList<String> getAllScoresByScore(String gameName) {

        ArrayList<String> gameScores = scoreTable.get(gameName);
        if (gameScores == null) {
            gameScores = new ArrayList<>();
        }

        Collections.sort(gameScores);
        return gameScores;

    }

    public static void addScore(GameScore gameScore) {
        String gameName = gameScore.getGameName();

        ArrayList<String> gameScores = scoreTable.get(gameName);
        if (gameScores == null) {
            gameScores = new ArrayList<>();
        }

        gameScores.add(gameScore.toString());
        scoreTable.put(gameName, gameScores);
    }

}
