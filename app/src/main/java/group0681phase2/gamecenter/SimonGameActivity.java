package group0681phase2.gamecenter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The main class for Simon game.
 */
public class SimonGameActivity extends AppCompatActivity {

    /**
     * The Simon game.
     */
    SimonGame simonGame = new SimonGame();

    /**
     * The animation for the tiles.
     */
    Animation animation = new AlphaAnimation(1, 0);

    /**
     * The green button.
     */
    Button greenButton;

    /**
     * The red button.
     */
    Button redButton;

    /**
     * The yellow button.
     */
    Button yellowButton;

    /**
     * The blue button.
     */
    Button blueButton;

    /**
     * The score text.
     */
    TextView scoreText;

    /**
     * The index in the pattern for the current color.
     */
    int patternIndex = 0;

    /**
     * The speed to be used in the game.
     */
    int updatedSpeed = 1000;

    /**
     * The duration each animation lasts.
     */
    int updatedDuration = 800;

    /**
     * The score.
     */
    public static int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_game);
        greenButton = findViewById(R.id.greenButton);
        redButton = findViewById(R.id.redButton);
        yellowButton = findViewById(R.id.yellowButton);
        blueButton = findViewById(R.id.blueButton);
        scoreText = findViewById(R.id.scoreText);
        scoreText.setText("0");
        addGreenButtonListener();
        addRedButtonListener();
        addYellowButtonListener();
        addBlueButtonListener();
        addStartButton();

        //Based off: https://stackoverflow.com/questions/4852281/android-how-can-i-make-a-button-flash
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatMode(Animation.REVERSE);
        nullifyButtons();
        score = 0;
    }

    /**
     * Activate the start button.
     */
    private void addStartButton() {
        final Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
                resetButtons();
                startButton.setVisibility(View.INVISIBLE);
            }
        });
    }

    /**
     * Start the game.
     */
    private void startGame() {
        simonGame.nextButton();
        updateSpeed();
        updateDuration();
        flashPattern();
    }

    /**
     * Update the speed.
     */
    public void updateSpeed() {
        updatedSpeed = ComplexityActivity.speed;
    }

    /**
     * Update the duration.
     */
    public void updateDuration() {
        updatedDuration = ComplexityActivity.duration;
    }

    /**
     * Activate the green button.
     */
    private void addGreenButtonListener() {
        final Button greenButton = findViewById(R.id.greenButton);
        greenButton.setBackgroundColor(Color.GREEN);
        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCorrect(0)) {
                    flashButton(greenButton);
                    checkDone();
                } else {
                    switchToScoreboard();
                }
            }
        });
    }

    /**
     * Activate the red button.
     */
    private void addRedButtonListener() {
        final Button redButton = findViewById(R.id.redButton);
        redButton.setBackgroundColor(Color.RED);
        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCorrect(1)) {
                    flashButton(redButton);
                    checkDone();
                } else {
                    switchToScoreboard();
                }
            }
        });
    }

    /**
     * Activate the yellow button.
     */
    private void addYellowButtonListener() {
        final Button yellowButton = findViewById(R.id.yellowButton);
        yellowButton.setBackgroundColor(Color.YELLOW);
        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCorrect(2)) {
                    flashButton(yellowButton);
                    checkDone();
                } else {
                    switchToScoreboard();
                }
            }
        });
    }

    /**
     * Activate the blue button.
     */
    private void addBlueButtonListener() {
        final Button blueButton = findViewById(R.id.blueButton);
        blueButton.setBackgroundColor(Color.BLUE);
        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCorrect(3)) {
                    flashButton(blueButton);
                    checkDone();
                } else {
                    switchToScoreboard();
                }
            }
        });
    }

    /**
     * Flash the pattern.
     */
    public void flashPattern() {
        nullifyButtons();
        final ArrayList<Integer> pattern = simonGame.getPattern();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (pattern.get(patternIndex) == 0) {
                    greenButton.startAnimation(animation);
                    checkDonePattern();
                } else if (pattern.get(patternIndex) == 1) {
                    redButton.startAnimation(animation);
                    checkDonePattern();
                } else if (pattern.get(patternIndex) == 2) {
                    yellowButton.startAnimation(animation);
                    checkDonePattern();
                } else if (pattern.get(patternIndex) == 3) {
                    blueButton.startAnimation(animation);
                    checkDonePattern();
                }
            }
        };
        timer.schedule(task, updatedSpeed);
    }

    /**
     * Check if Simon is done flashing the pattern.
     */
    public void checkDonePattern() {
        ArrayList<Integer> pattern = simonGame.getPattern();
        if (patternIndex == pattern.size() - 1) {
            patternIndex = 0;
            resetButtons();
        } else {
            patternIndex++;
            flashPattern();
        }
    }

    /**
     * Nullify the buttons so that they can't be pressed.
     */
    private void nullifyButtons() {
        greenButton.setOnClickListener(null);
        redButton.setOnClickListener(null);
        yellowButton.setOnClickListener(null);
        blueButton.setOnClickListener(null);
    }

    /**
     * Reset the buttons.
     */
    public void resetButtons() {
        addGreenButtonListener();
        addRedButtonListener();
        addYellowButtonListener();
        addBlueButtonListener();
    }

    /**
     * Flash the button.
     *
     * @param button the button to be flashed
     */
    public void flashButton(Button button) {
        animation.setDuration(updatedDuration);
        button.startAnimation(animation);
    }

    /**
     * Check if the color is correct.
     *
     * @param colourNumber the number for the color to be checked
     * @return if the color is correct
     */
    public boolean checkCorrect(int colourNumber) {
        ArrayList<Integer> pattern = simonGame.getPattern();
        return pattern.get(patternIndex) == colourNumber;
    }

    /**
     * Check if the player is done repeating the pattern.
     */
    public void checkDone() {
        ArrayList<Integer> pattern = simonGame.getPattern();
        if (patternIndex == pattern.size() - 1) {
            patternIndex = 0;
            addPoint();
            scoreText.setText(String.format("%s", score));
            startGame();
        } else {
            patternIndex++;
        }
    }

    /**
     * Add a point to the score.
     */
    public void addPoint() {
        score++;
    }

    /**
     * Switch to the scoreboard.
     */
    private void switchToScoreboard() {
        Intent tmp = new Intent(this, SimonScoreboardActivity.class);
        startActivity(tmp);
    }
}