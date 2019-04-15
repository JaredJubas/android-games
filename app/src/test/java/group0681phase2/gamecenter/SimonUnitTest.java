package group0681phase2.gamecenter;

import android.widget.TextView;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SimonUnitTest {
    @Test
    public void testGetPatternSize() {
        SimonGame game = new SimonGame();
        game.nextButton();
        game.nextButton();
        game.nextButton();
        assertEquals(3, game.getPattern().size());
    }

    @Test
    public void testValuesNextButton() {
        SimonGame game = new SimonGame();
        boolean valuesCorrect = true;
        game.nextButton();
        game.nextButton();
        game.nextButton();

        for (int i : game.getPattern()) {
            if (i < 0 || i > 3) {
                valuesCorrect = false;
            }
        }
        assertTrue(valuesCorrect);
    }

    @Test
    public void testComplexityActivitySetSpeed() {
        ComplexityActivity ca = new ComplexityActivity();
        ca.changeSpeed(500);
        assertEquals(500, ComplexityActivity.speed);
    }

    @Test
    public void testAddPointToScore() {
        SimonGameActivity game = new SimonGameActivity();
        SimonGameActivity.score = 2;
        game.addPoint();
        assertEquals(3, SimonGameActivity.score);
    }

    @Test
    public void testUpdateSpeed() {
        SimonGameActivity game = new SimonGameActivity();
        ComplexityActivity.speed = 500;
        game.updateSpeed();
        assertEquals(500, game.updatedSpeed);
    }

    @Test
    public void testUpdateDuration() {
        SimonGameActivity game = new SimonGameActivity();
        ComplexityActivity.duration = 200;
        game.updateDuration();
        assertEquals(200, game.updatedDuration);
    }

    @Test
    public void testCheckDoneWhenNotDone(){
        SimonGameActivity game = new SimonGameActivity();
        SimonGame sg = new SimonGame();
        sg.nextButton();
        sg.nextButton();
        sg.nextButton();
        game.patternIndex = 1;
        game.checkDone();
        assertEquals(2, game.patternIndex);
    }
}
