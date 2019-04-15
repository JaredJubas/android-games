package group0681phase2.gamecenter;

import android.widget.Button;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Hashtable;

import static org.junit.Assert.*;

/**
 * Unit Tests for various classes.
 */
public class MiscUnitTest {

    //Abstract Board Manager Tests
    @Test
    public void boardManagerGetTotalTime() {
        BoardManager bm = new BoardManager();
        assertTrue(bm.getTotalTime() >= 0.0);
    }

    @Test
    public void customAdapterGetItemId() {
        ArrayList<Button> a = new ArrayList<>();
        CustomAdapter ca = new CustomAdapter(a, 4, 4);
        assertEquals(3, ca.getItemId(3));
    }

    @Test
    public void movementControllerGetAndSetBoardManager() {
        BoardManager bm = new BoardManager();
        MovementController mc = new MovementController();
        mc.setBoardManager(bm);
        assertEquals(bm, mc.getBoardManager());
    }

    @Test
    public void gameScoreGetUserName() {
        GameScore gs = new GameScore("User", "SlidingTiles", 73.3);
        assertEquals("User", gs.getUserName());
    }

    @Test
    public void gameScoreGetNullUserName() {
        GameScore gs = new GameScore(null, "SlidingTiles", 73.3);
        assertEquals("Guest", gs.getUserName());
    }

    @Test
    public void gameScoreGetGameName() {
        GameScore gs = new GameScore("User", "SlidingTiles", 73.3);
        assertEquals("SlidingTiles", gs.getGameName());
    }

    @Test
    public void gameScoreGetTotalTime() {
        GameScore gs = new GameScore(null, "SlidingTiles", 73.3);
        assertEquals(73.3, gs.getTotalTime(), 0.01);
    }

    @Test
    public void gameScoreGetGameSize() {
        GameScore gs = new GameScore("User", "SlidingTiles", 73.3, 3);
        assertEquals(3, gs.getGameSize());
    }

    @Test
    public void gameScoreToString() {
        GameScore gs =  new GameScore("User", "SlidingTiles", 73.3, 3);
        assertEquals("73.3 sec: User - SlidingTiles (3 x 3)", gs.toString());
    }

    @Test
    public void scoreboardGetAllScoresByScore() {
        Scoreboard sb = new Scoreboard();
        Hashtable<String, ArrayList<String>> scoreTable = new Hashtable<String, ArrayList<String>>();
        GameScore gs = new GameScore("User", "SlidingTiles", 73.3);
        ArrayList<String> result = sb.getAllScoresByScore(gs.getGameName());
        assertTrue(result.isEmpty());
    }
}