package group0681phase2.gamecenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The complexity options for Simon.
 */
public class ComplexityActivity extends AppCompatActivity {

    /**
     * The speed that the pattern flashes.
     */
    static int speed = 1000;

    /**
     * The duration that the pattern flashes for.
     */
    static int duration = 500;

    /**
     * A list that stores which complexity was selected.
     */
    public static List<Integer> complexity = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complexity);
        addSlowButtonListener();
        addNormalButtonListener();
        addFastButtonListener();
        addInsaneButtonListener();
        addReturnToMainMenuButton();
    }

    /**
     * Activate the slow button.
     */
    public void addSlowButtonListener() {
        Button playButton = findViewById(R.id.slowButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSpeed(1500);
                changeDuration(800);
                complexity = Arrays.asList(1, 0, 0, 0);
                switchToSimon();
            }
        });
    }

    /**
     * Activate the normal button.
     */
    private void addNormalButtonListener() {
        Button playButton = findViewById(R.id.normalButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complexity = Arrays.asList(0, 1, 0, 0);
                switchToSimon();
            }
        });
    }

    /**
     * Activate the fast button.
     */
    private void addFastButtonListener() {
        Button playButton = findViewById(R.id.fastButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSpeed(500);
                changeDuration(300);
                complexity = Arrays.asList(0, 0, 1, 0);
                switchToSimon();
            }
        });
    }

    /**
     * Activate the insane button.
     */
    private void addInsaneButtonListener() {
        Button playButton = findViewById(R.id.insaneButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSpeed(250);
                changeDuration(150);
                complexity = Arrays.asList(0, 0, 0, 1);
                switchToSimon();
            }
        });
    }

    /**
     * Activate the return to main menu button.
     */
    private void addReturnToMainMenuButton() {
        Button returnToMainMenuButton = findViewById(R.id.returnMenu);
        returnToMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMainMenu();
            }
        });
    }

    /**
     * Change the speed that the tiles flash.
     *
     * @param newSpeed the new speed.
     */
    public void changeSpeed(int newSpeed) {
        speed = newSpeed;
    }

    /**
     * Change the duration that the animation lasts.
     *
     * @param newDuration the new duration.
     */
    public void changeDuration(int newDuration) {
        duration = newDuration;
    }

    /**
     * Switch to Simon game.
     */
    private void switchToSimon() {
        Intent tmp = new Intent(this, SimonGameActivity.class);
        startActivity(tmp);
    }

    /**
     * Switch to the main menu.
     */
    private void switchToMainMenu() {
        Intent tmp = new Intent(this, SimonMenuActivity.class);
        tmp.putExtra("username", GameSelectActivity.username);
        startActivity(tmp);
    }
}
