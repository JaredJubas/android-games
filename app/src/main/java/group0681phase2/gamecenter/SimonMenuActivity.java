package group0681phase2.gamecenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * The main menu for Simon game.
 */
public class SimonMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_menu);
        addSimonPlayButtonListener();
        addSimonInstructionsButtonListener();
        addReturnToGameSelectionButtonListener();
    }

    /**
     * Activate the Simon play button.
     */
    private void addSimonPlayButtonListener() {
        Button playButton = findViewById(R.id.simonPlayButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSimon();
            }
        });
    }

    /**
     * Activate the instructions button.
     */
    private void addSimonInstructionsButtonListener() {
        Button instructionsButton = findViewById(R.id.instructionsButton);
        instructionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToInstructions();
            }
        });
    }

    /**
     * Activate the return to game selection screen button.
     */
    private void addReturnToGameSelectionButtonListener() {
        Button gameSelectionButton = findViewById(R.id.returnGameSelection);
        gameSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGameSelection();
            }
        });
    }

    /**
     * Switch to the Simon complexity screen.
     */
    private void switchToSimon() {
        Intent tmp = new Intent(this, ComplexityActivity.class);
        startActivity(tmp);
    }

    /**
     * Switch to the Simon instructions screen.
     */
    private void switchToInstructions() {
        Intent tmp = new Intent(this, InstructionsActivity.class);
        startActivity(tmp);
    }

    /**
     * Switch to the game selection screen.
     */
    private void switchToGameSelection() {
        Intent tmp = new Intent(this, GameSelectActivity.class);
        tmp.putExtra("username", GameSelectActivity.username);
        startActivity(tmp);
    }
}
