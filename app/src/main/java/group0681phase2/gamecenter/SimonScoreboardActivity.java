package group0681phase2.gamecenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The scoreboard for Simon.
 */
public class SimonScoreboardActivity extends AppCompatActivity {

    /**
     * The Simon game.
     */
    SimonGame game = new SimonGame();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_scoreboard);
        TextView scoreText = findViewById(R.id.scoreboardScore);
        TextView scoreboardLabel = findViewById(R.id.scoreboardLabel);
        scoreText.setText(String.format("%s", SimonGameActivity.score));
        String complexity;
        if (ComplexityActivity.complexity.get(0) == 1) {
            complexity = "Slow";
        } else if (ComplexityActivity.complexity.get(1) == 1) {
            complexity = "Normal";
        } else if (ComplexityActivity.complexity.get(2) == 1) {
            complexity = "Fast";
        } else {
            complexity = "Insane";
        }
        String scoreboardLabelText = complexity + " Scoreboard";
        scoreboardLabel.setText(scoreboardLabelText);
        ArrayList<String> scores = game.getScores();
        ListView scoresLV = findViewById(R.id.simonScoreList);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1, scores);

        scoresLV.setAdapter(arrayAdapter);
        addMainMenuButtonListener();

        SimonGameActivity.score = 0;
    }

    /**
     * Activate the main menu button.
     */
    private void addMainMenuButtonListener() {
        Button backgroundButton = findViewById(R.id.backToSimonMain);
        backgroundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMenu();
            }
        });
    }

    /**
     * Switch to the main menu for Simon.
     */
    private void switchToMenu() {
        Intent tmp = new Intent(this, SimonMenuActivity.class);
        tmp.putExtra("username", GameSelectActivity.username);
        startActivity(tmp);
    }

}
