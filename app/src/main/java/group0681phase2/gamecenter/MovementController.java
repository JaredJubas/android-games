package group0681phase2.gamecenter;

import android.content.Context;
import android.widget.Toast;

/**
 * The movement controller class.
 */
public class MovementController {

    /**
     * An instantiation of board manager.
     */
    private AbstractBoardManager boardManager = null;

    /**
     * A movement controller.
     */
    public MovementController() {
    }

    /**
     * Set the board manager.
     *
     * @param boardManager An instance of board manager.
     */
    public void setBoardManager(AbstractBoardManager boardManager) {
        this.boardManager = boardManager;
    }

    public AbstractBoardManager getBoardManager() {
        return this.boardManager;
    }

    /**
     * Display messages based on the tap position.
     *
     * @param context  A context.
     * @param position Position of the tap.
     * @param display  The display.
     */
    public void processTapMovement(Context context, int position, boolean display) {
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            if (boardManager.puzzleSolved()) {
                if (boardManager.isWinner())
                    Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "GAME OVER", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}

