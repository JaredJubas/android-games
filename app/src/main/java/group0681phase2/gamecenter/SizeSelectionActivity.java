package group0681phase2.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * The activity for selecting board size for the sliding puzzle tile game.
 */
public class SizeSelectionActivity extends AbstractGameActivity {

    /**
     * The username of the user logged into the game.
     * For Guest, username is empty.
     */
    private String username;

    private String currentGame;

    /**
     * Indicates if the default background for the game should be used.
     */
    private boolean useDefaultBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        this.username = intent.getStringExtra("username");
        this.currentGame = intent.getStringExtra("gamename");
        this.useDefaultBackground = intent.getBooleanExtra("useDefault", true);
        setContentView(R.layout.activity_sizeselection_);
        addFourButtonListener();
        addThreeButtonListener();
        addFiveButtonListener();
        addReturnToMenuButton();

    }

    /**
     * Activate the 4*4 button.
     */
    private void addFourButtonListener() {
        Button fourButton = findViewById(R.id.SizeFourButton);
        fourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame(4);
            }
        });
    }

    /**
     * Activate the 3*3 button.
     */
    private void addThreeButtonListener() {
        Button threeButton = findViewById(R.id.SizeThreeButton);
        threeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame(3);
            }
        });
    }

    /**
     * Activate the 5*5 button.
     */
    private void addFiveButtonListener() {
        Button fiveButton = findViewById(R.id.SizeFiveButton);
        fiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame(5);
            }
        });
    }

    private void addReturnToMenuButton() {
        Button returnToMainMenuButton = findViewById(R.id.returnToMenuButton);
        returnToMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMainMenu();
            }
        });
    }

    private void switchToMainMenu() {
        Intent tmp = new Intent(this, StartingActivity.class);
        tmp.putExtra("username", GameSelectActivity.username);
        tmp.putExtra("gamename", this.currentGame);
        startActivity(tmp);
    }


    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame(int size) {
        if (currentGame.equals(StartingActivity.SLIDING_TILES_GAME)) {
            Board.setSize(size);
            boardManager = new BoardManager();
            boardManager.setUndoCapacity(StartingActivity.undoCapacity);

            Intent tmp = new Intent(this, GameActivity.class);
            tmp.putExtra("username", this.username);
            tmp.putExtra("useDefault", this.useDefaultBackground);
            saveToFile(getTempFileName(this.username, currentGame));
            startActivity(tmp);
            finish();
        } else if (currentGame.equals(StartingActivity.TIC_TAC_TOE_GAME)) {
            TicBoard.setSize(size);
            boardManager = new TicBoardManager();
            boardManager.setUndoCapacity(StartingActivity.undoCapacity);

            Intent tmp = new Intent(this, PlayerSelectionActivity.class);
            tmp.putExtra("username", username);
            saveToFile(getTempFileName(this.username, currentGame));
            startActivity(tmp);
            finish();
        }
    }


}