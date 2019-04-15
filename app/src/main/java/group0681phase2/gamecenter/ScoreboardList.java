package group0681phase2.gamecenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * The scoreboard list class.
 */
public class ScoreboardList extends AppCompatActivity {
    /**
     * A listview.
     */
    ListView scoresLV;

    /**
     * A list of scores.
     */
    ArrayList<String> scores;

    /**
     * An instantiation of the scoreboard class.
     */
    Scoreboard scoreboard = new Scoreboard();

    private String gameName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard_list);
        addMainMenuButtonListener();

        Intent intent = getIntent();

        gameName = intent.getStringExtra("gamename");

        if (gameName == null) {
            gameName = "";
        }

        scores = scoreboard.getAllScoresByScore(gameName);

        //From https://stackoverflow.com/questions/5070830/populating-a-listview-using-an-arraylist
        scoresLV = (ListView) findViewById(R.id.threeScores);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1, scores);

        scoresLV.setAdapter(arrayAdapter);
    }

    /**
     * Activate the main menu button.
     */
    private void addMainMenuButtonListener() {
        Button backgroundButton = findViewById(R.id.backButton);
        backgroundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMenu();
            }
        });
    }

    /**
     * Helper method to switch to the main menu.
     */
    private void switchToMenu() {
        Intent tmp = new Intent(this, StartingActivity.class);
        tmp.putExtra("username", GameSelectActivity.username);
        tmp.putExtra("gamename", this.gameName);
        startActivity(tmp);
    }
}
