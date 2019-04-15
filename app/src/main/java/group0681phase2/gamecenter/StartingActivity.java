package group0681phase2.gamecenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AbstractGameActivity {


    public static final String SLIDING_TILES_GAME = "Sliding Tiles";
    public static final String TIC_TAC_TOE_GAME = "Tic Tac Toe";

    private String currentGame = "";

    /**
     * The username of the user logged into the game.
     * For Guest, username is empty.
     */
    private String username;

    /**
     * Indicates if the default background for the game should be used.
     */
    private boolean useDefaultBackground;

    /**
     * Activity Request Code for selecting game background.
     */
    private static int RESULT_SET_BACKGROUND = 2;

    /**
     * The amount of moves that can be undone at the start of a new game.
     */
    static int undoCapacity;

    /**
     * Text that shows how many moves the payer can undo.
     */
    TextView screenUndoVariable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_starting_);
        addStartButtonListener();
        addLoadButtonListener();
        addSaveButtonListener();

        addIncreaseUndoButtonListener();
        addDecreaseUndoButtonListener();
        addReturnToGameSelectionListener();
        screenUndoVariable = findViewById(R.id.textView);
        undoCapacity = 3;
        updateUndoCapacity();

        this.useDefaultBackground = true;

        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");

        currentGame = intent.getStringExtra("gamename");
        if (currentGame == null) {
            currentGame = SLIDING_TILES_GAME;
        }

        Button backgroundButton = findViewById(R.id.BackgroundButton);

        if (currentGame.equals(SLIDING_TILES_GAME)) {
            backgroundButton.setVisibility(View.VISIBLE);
            addBackgroundButtonListener();
            boardManager = new BoardManager();
        } else if (currentGame.equals(TIC_TAC_TOE_GAME)) {
            backgroundButton.setVisibility(View.INVISIBLE);
            boardManager = new TicBoardManager();
        }
        setWelcomeMessage();
        saveToFile(getTempFileName(username, currentGame));
    }

    /**
     * Set the welcome message.
     */
    private void setWelcomeMessage() {
        TextView welcomeMessage = findViewById(R.id.tvWelcomeMsg);
        String message = " Welcome";

        if (this.currentGame.equals(StartingActivity.SLIDING_TILES_GAME)) {
            message = getString(R.string.SlidingTilesTitle);
        } else if (this.currentGame.equals(StartingActivity.TIC_TAC_TOE_GAME)) {
            message = getString(R.string.Tic_Title);
        }
        welcomeMessage.setText(message);
        welcomeMessage.setGravity(Gravity.CENTER_HORIZONTAL);

    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSelection();
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile(getSaveFileName(username, currentGame));
                saveToFile(getTempFileName(username, currentGame));
                makeToastLoadedText();
                switchToGame();
            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile(getSaveFileName(username, currentGame));
                saveToFile(getTempFileName(username, currentGame));
                makeToastSavedText();
            }
        });
    }

    /**
     * Activate the Background button.
     */
    private void addBackgroundButtonListener() {
        Button backgroundButton = findViewById(R.id.BackgroundButton);
        backgroundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBackground();
            }
        });
    }

    /**
     * Switch to the LoadBackgroundActivity view to load a custom background for the game.
     */
    private void selectBackground() {
        Intent intent = new Intent(this, LoadBackgroundActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("useDefault", useDefaultBackground);
        startActivityForResult(intent, RESULT_SET_BACKGROUND);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_SET_BACKGROUND) {
            if (resultCode == RESULT_FIRST_USER) {
                this.useDefaultBackground = true;
            } else if (resultCode == (RESULT_FIRST_USER + 1)) {
                this.useDefaultBackground = false;
            }
        }
    }

    /**
     * Activate the increase undo button.
     */
    private void addIncreaseUndoButtonListener() {
        Button increaseUndoButton = findViewById(R.id.IncreaseUndoButton);
        increaseUndoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undoCapacity++;
                updateUndoCapacity();
            }
        });
    }

    /**
     * Activate the decrease undo button.
     */
    private void addDecreaseUndoButtonListener() {
        Button decreaseUndoButton = findViewById(R.id.DecreaseUndoButton);
        decreaseUndoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (undoCapacity > 0) {
                    undoCapacity--;
                    updateUndoCapacity();
                }
            }
        });
    }

    /**
     * Activate the return to game selection button.
     */
    private void addReturnToGameSelectionListener() {
        Button returnToGameLaunchCentreButton = findViewById(R.id.BackButton);
        returnToGameLaunchCentreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGameSelection();
            }
        });
    }

    /**
     * Helper method to switch to the game selection screen.
     */
    private void switchToGameSelection() {
        Intent tmp = new Intent(this, GameSelectActivity.class);
        tmp.putExtra("username", GameSelectActivity.username);
        startActivity(tmp);
    }

    /**
     * Update the undo capacity message.
     */
    private void updateUndoCapacity() {
        String moves = "Undoable Moves: " + undoCapacity;
        screenUndoVariable.setText(moves);
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile(getTempFileName(username, currentGame));
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        if (currentGame.equals(SLIDING_TILES_GAME)) {
            Intent tmp = new Intent(this, GameActivity.class);
            tmp.putExtra("username", username);
            tmp.putExtra("useDefault", useDefaultBackground);
            saveToFile(getTempFileName(username, currentGame));
            startActivity(tmp);
        } else if (currentGame.equals(TIC_TAC_TOE_GAME)) {
            Intent tmp = new Intent(this, TicGameActivity.class);
            tmp.putExtra("username", username);
            saveToFile(getTempFileName(username, currentGame));
            startActivity(tmp);
        }
    }

    /**
     * Switch to the Selection view to select board size.
     */
    private void switchToSelection() {
        Intent tmp = new Intent(this, SizeSelectionActivity.class);
        tmp.putExtra("username", username);
        tmp.putExtra("gamename", currentGame);
        tmp.putExtra("useDefault", useDefaultBackground);
        startActivity(tmp);
    }


    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                if (currentGame.equals(SLIDING_TILES_GAME)) {
                    boardManager = (BoardManager) input.readObject();
                } else if (currentGame.equals(TIC_TAC_TOE_GAME)) {
                    boardManager = (TicBoardManager) input.readObject();
                }

                boardManager.setUndoCapacity(undoCapacity);
                boardManager.syncSize();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

}