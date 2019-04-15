package group0681phase2.gamecenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.view.View;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import java.util.Timer;
import java.util.TimerTask;

public class TicGameActivity extends AbstractGameActivity implements Observer {

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    // Save game every set interval controlled by a timer and the gameChanged indicator
    private Timer timer;
    private boolean gameChanged;
    private static long TIMER_INTERVAL = 5000;

    /**
     * The username of the user logged into the game.
     * For Guest, username is empty.
     */
    private String username;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");
        boolean botStart = intent.getBooleanExtra("botstart", false);

        loadFromFile(getTempFileName(username, StartingActivity.TIC_TAC_TOE_GAME));

        createTileButtons(this);

        setContentView(R.layout.activity_main);
        addUndoButtonListener();

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(TicBoard.NUM_COLS);
        gridView.setBoardManager(boardManager);
        ((TicBoardManager) boardManager).getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / TicBoard.NUM_COLS;
                        columnHeight = displayHeight / TicBoard.NUM_ROWS;

                        display();
                    }
                });

        if (botStart) {
            ((TicBoardManager) boardManager).botMove();
        } else {
            TicBoardManager.currentPlayer = TicBoardManager.humanPlayer;
        }

        // Timer to save the game at TIMER_INTERVAL.
        gameChanged = true;
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                if (gameChanged) {
                    saveToFile(getTempFileName(username, StartingActivity.TIC_TAC_TOE_GAME));
                    saveToFile(getSaveFileName(username, StartingActivity.TIC_TAC_TOE_GAME));
                    gameChanged = false;
                }
            }
        }, TIMER_INTERVAL, TIMER_INTERVAL);
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        TicBoard board = ((TicBoardManager) boardManager).getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != TicBoard.NUM_ROWS; row++) {
            for (int col = 0; col != TicBoard.NUM_COLS; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        TicBoard board = ((TicBoardManager) boardManager).getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / TicBoard.NUM_ROWS;
            int col = nextPos % TicBoard.NUM_COLS;

            //int tileId = board.getTile(row, col).getId();
            //boolean blankTile = tileId == Board.NUM_COLS * Board.NUM_ROWS;

            b.setBackgroundResource(board.getTile(row, col).getBackground());

            nextPos++;
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(getTempFileName(username, StartingActivity.TIC_TAC_TOE_GAME));
    }

    /**
     * Stop timer when exiting the game.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
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
                boardManager = (TicBoardManager) input.readObject();
                boardManager.setUndoCapacity(StartingActivity.undoCapacity);
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


    @Override
    public void update(Observable o, Object arg) {
        display();
        gameChanged = true;

        if (boardManager.isWinner()) {
            switchToScoreboard();
        }


    }

    /**
     * Activate the Undo button.
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.undoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TicBoardManager) boardManager).undo();
            }
        });
    }

    /**
     * Helper method to switch to the scoreboard.
     */
    public void switchToScoreboard() {
        Intent tmp = new Intent(this, ScoreboardActivity.class);

        GameScore score = new GameScore(this.username, StartingActivity.TIC_TAC_TOE_GAME,
                boardManager.getTotalTime(), ((TicBoardManager) boardManager).getSize());
        Scoreboard.addScore(score);
        tmp.putExtra("score", score);
        startActivity(tmp);
    }

}