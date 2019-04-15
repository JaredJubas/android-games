package group0681phase2.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/*
 * The screen to select different player.
 */
public class PlayerSelectionActivity extends AppCompatActivity {


    /**
     * The username of the user logged into the game.
     * For Guest, username is empty.
     */
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");
        setContentView(R.layout.activity_playerselection);
        addPlayer1ButtonListener();
        addPlayer2ButtonListener();

    }

    /**
     * Activate the Player1 button.
     */
    private void addPlayer1ButtonListener() {
        Button p1Button = findViewById(R.id.Player1);
        p1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TicBoardManager.humanPlayer = TicTile.PLAYER1;
                switchToTic(false);
            }
        });
    }

    /**
     * Activate the Player2 button.
     */
    private void addPlayer2ButtonListener() {
        Button p2Button = findViewById(R.id.Player2);
        p2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TicBoardManager.humanPlayer = TicTile.PLAYER2;
                switchToTic(true);
            }
        });
    }

    /**
     * Switch to the TicGameActivity view to play the game.
     *
     * @param botStart If true, the bot player will move first
     */
    private void switchToTic(boolean botStart) {
        Intent tmp = new Intent(this, TicGameActivity.class);
        tmp.putExtra("username", this.username);
        tmp.putExtra("botstart", botStart);
        startActivity(tmp);
        finish();
    }


}


