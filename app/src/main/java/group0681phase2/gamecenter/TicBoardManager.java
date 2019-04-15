package group0681phase2.gamecenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Manage a Ticboard, including update tiles, checking for a win, and managing taps.
 */
public class TicBoardManager extends AbstractBoardManager implements Serializable {
    /**
     * The board being managed.
     */
    private TicBoard board;

    /**
     * A list of moves done by player
     */
    private Stack<Integer> moveList = new Stack<>();

    /**
     * the number of undone moves
     */
    private int undoneMoves = 0;

    /**
     * The limit of undo
     */
    private int undoCapacity;


    /**
     * The board size of current game.
     */
    private int gameSize;

    /**
     * Player name of current player.
     */
    public static String currentPlayer;

    /**
     * The player picked by user
     */
    public static String humanPlayer = TicTile.PLAYER1;


    /**
     * Return the current board.
     */
    TicBoard getBoard() {
        return board;
    }

    /**
     * Set the Board size to current game size.
     */
    @Override
    public void syncSize() {
        TicBoard.setSize(this.gameSize);
    }

    /**
     * Return the current board size.
     */
    public int getSize() {
        return this.gameSize;
    }

    /**
     * Manage a new shuffled board.
     */
    TicBoardManager() {
        super();
        this.currentPlayer = TicTile.PLAYER1;
        List<TicTile> tiles = new ArrayList<>();
        final int numTiles = TicBoard.NUM_ROWS * TicBoard.NUM_COLS;
        this.gameSize = TicBoard.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new TicTile(TicTile.EMPTY));
        }

        Collections.shuffle(tiles);
        this.board = new TicBoard(tiles);
    }

    /**
     * Return whether the human player is winner.
     *
     * @return whether the human player is winner.
     */
    @Override
    public boolean isWinner() {
        if (puzzleSolved()) {
            if (board.rowCompleted() || board.colCompleted() || board.diagonalCompleted()) {
                return !isBotPlayer();
            }
        }
        return false;
    }

    /**
     * Return whether there is a completed line or the board is full
     *
     * @return whether there is a completed line or the board is full .
     */
    @Override
    public boolean puzzleSolved() {
        boolean solved = board.rowCompleted() || board.colCompleted() || board.diagonalCompleted() ||
                board.isFull();
        if (solved) {
            setEndTime();
        }
        return solved;
    }

    /**
     * Return whether the clicked tile is unoccupied.
     *
     * @param position the tile to check
     * @return whether the tile at position is unoccupied.
     */
    @Override
    public boolean isValidTap(int position) {

        int row = position / TicBoard.NUM_COLS;
        int col = position % TicBoard.NUM_COLS;

        return (board.getTile(row, col).getPlayer().equals(TicTile.EMPTY));
    }

    /**
     * Undoes the previous move.
     */
    void undo() {
        if ((this.moveList.size() > 1) && this.undoneMoves < this.undoCapacity) {
            this.undoneMoves += 1;
            this.setEmpty(moveList.pop());
            this.setEmpty(moveList.pop());
        }
    }

    /**
     * Set the position on the board to be empty
     *
     * @param position the position of the tile need to be modified.
     */
    private void setEmpty(int position) {
        int row = position / TicBoard.NUM_ROWS;
        int col = position % TicBoard.NUM_COLS;
        board.updateTiles(row, col, TicTile.EMPTY);

    }

    /**
     * Set the limit of undo
     *
     * @param i the undo limit.
     */
    public void setUndoCapacity(int i) {
        this.undoCapacity = i;
    }

    /**
     * Process a touch at position in the board, update the tiles as appropriate.
     *
     * @param position the position
     */
    @Override
    public void touchMove(int position) {

        int row = position / TicBoard.NUM_ROWS;
        int col = position % TicBoard.NUM_COLS;
        board.updateTiles(row, col, currentPlayer);
        this.moveList.push(position);
        if (!isBotPlayer() && undoneMoves > 0) {
            this.undoneMoves--;
        }

        switchPlayers();

    }

    /**
     * Switch the current player to the other one.
     */
    private void switchPlayers() {
        // If puzzle is not solved, switch players
        if (!puzzleSolved()) {
            if (currentPlayer.equals(TicTile.PLAYER1)) {
                this.currentPlayer = TicTile.PLAYER2;
            } else {
                this.currentPlayer = TicTile.PLAYER1;

            }
            if (isBotPlayer()) {
                botMove();
            }
        }
    }


    /**
     * Return whether it is the bot's turn to play the game.
     */
    public boolean isBotPlayer() {
        if (this.currentPlayer.equals(this.humanPlayer)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Let the computer player commit a move.
     */
    public void botMove() {
        List<Integer> possibleMoves = board.getEmptyPositions();
        int bound = possibleMoves.size();
        Random random = new Random();
        if (bound > 0) {
            int randomMove = random.nextInt(bound);
            touchMove(possibleMoves.get(randomMove));
        }

    }

}
