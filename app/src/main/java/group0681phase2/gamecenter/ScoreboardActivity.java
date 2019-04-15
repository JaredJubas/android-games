package group0681phase2.gamecenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Scoreboard Activity Class
 */
public class ScoreboardActivity extends AppCompatActivity {

    private String currentGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        addMainMenuButtonListener();
        addScoreboardListener();
        TextView time = findViewById(R.id.timer);
        Intent intent = getIntent();
        GameScore score = (GameScore) intent.getSerializableExtra("score");
        double gameTotalTime = 0;
        if (score == null) {
            currentGame = "";
        } else {
            gameTotalTime = score.getTotalTime();
            currentGame = score.getGameName();
        }
        String stringTime = (Double.toString(gameTotalTime) + " sec");
        time.setText(stringTime);
    }

    /**
     * Activate the main menu button.
     */
    private void addMainMenuButtonListener() {
        Button backgroundButton = findViewById(R.id.mainMenuButton);
        backgroundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMenu();
            }
        });
    }

    /**
     * Helper method to switch the activity to the main menu.
     */
    private void switchToMenu() {
        Intent tmp = new Intent(this, GameSelectActivity.class);
        tmp.putExtra("username", GameSelectActivity.username);
        startActivity(tmp);
    }

    /**
     * Activate the scoreboard button.
     */
    private void addScoreboardListener() {
        Button backgroundButton = findViewById(R.id.threeScoreboard);
        backgroundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToScoreboard();
            }
        });
    }

    /**
     * Helper method to switch to the scoreboard.
     */
    private void switchToScoreboard() {
        Intent tmp = new Intent(this, ScoreboardList.class);
        tmp.putExtra("gamename", this.currentGame);
        startActivity(tmp);
    }
}