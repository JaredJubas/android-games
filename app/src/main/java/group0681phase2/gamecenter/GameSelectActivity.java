package group0681phase2.gamecenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * The screen to select the different games.
 */
public class GameSelectActivity extends AppCompatActivity {

    /**
     * The username of the player.
     */
    public static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_select);
        addSlidingTilesButtonListener();
        addSimonButtonListener();
        addTicTacToeButtonListener();
        TextView welcomeMessage = findViewById(R.id.tvWelcomeMsg);
        addReturnToGameLaunchCentreListener();

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        if (username != null && !username.equals("")) {
            String message = "Welcome " + username + "!";
            welcomeMessage.setText(message);
        } else {
            String message = "Welcome guest!";
            welcomeMessage.setText(message);
            username = "";
        }
    }

    /**
     * Activate the main menu button.
     */
    private void addSlidingTilesButtonListener() {
        Button slidingTilesButton = findViewById(R.id.slidingTilesButton);
        slidingTilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSlidingTiles();
            }
        });
    }

    /**
     * Helper method to switch the activity to the main menu for SlidingTiles.
     */
    private void switchToSlidingTiles() {
        Intent tmp = new Intent(this, StartingActivity.class);
        tmp.putExtra("username", GameSelectActivity.username);
        tmp.putExtra("gamename", StartingActivity.SLIDING_TILES_GAME);
        startActivity(tmp);
    }

    /**
     * Activate the Simon button.
     */
    private void addSimonButtonListener() {
        Button simonButton = findViewById(R.id.simonButton);
        simonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSimon();
            }
        });
    }

    /**
     * Helper method to switch the activity to the main menu for Simon.
     */
    private void switchToSimon() {
        Intent tmp = new Intent(this, SimonMenuActivity.class);
        startActivity(tmp);
    }

    /**
     * Activate the Tic Tac Toe button.
     */
    private void addTicTacToeButtonListener() {
        Button ticTacToeButton = findViewById(R.id.ticTacToeButton);
        ticTacToeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToTicTacToe();
            }
        });
    }

    /**
     * Helper method to switch the activity to the main menu for Tic Tac Toe.
     */
    private void switchToTicTacToe() {
        Intent tmp = new Intent(this, StartingActivity.class);
        tmp.putExtra("username", GameSelectActivity.username);
        tmp.putExtra("gamename", StartingActivity.TIC_TAC_TOE_GAME);
        startActivity(tmp);
    }

    /**
     * Activate the return to game launch centre button.
     */
    private void addReturnToGameLaunchCentreListener() {
        Button returnToGameLaunchCentreButton = findViewById(R.id.backButton);
        returnToGameLaunchCentreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGameLaunchCentre();
            }
        });
    }

    /**
     * Helper method to switch to the game launch centre.
     */
    private void switchToGameLaunchCentre() {
        Intent tmp = new Intent(this, GameLaunchCentre.class);
        startActivity(tmp);
    }
}
