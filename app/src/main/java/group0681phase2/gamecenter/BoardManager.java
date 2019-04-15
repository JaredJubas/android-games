package group0681phase2.gamecenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManager extends AbstractBoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private Board board;
    private Stack<Integer> moveList = new Stack<>();
    private int undoneMoves = 0;
    private int undoCapacity;

    /**
     * The board size of current game.
     */
    private int gameSize;

    /**
     * Return the current board.
     */
    Board getBoard() {
        return board;
    }

    /**
     * Set the Board size to current game size.
     */
    @Override
    public void syncSize() {
        Board.setSize(this.gameSize);
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
    BoardManager() {
        super();
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
        this.gameSize = Board.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        tiles = shuffleValidBoard(tiles);
        this.board = new Board(tiles);
    }

    /*
     * Returns tiles shuffled so that it is solvable.
     *
     * @param tiles unshuffled tiles
     * @return tiles in a shuffled but solvable order.
     */
    private List<Tile> shuffleValidBoard(List<Tile> tiles) {
        Collections.shuffle(tiles);
        int inverses = getInverses(tiles);
        int area = tiles.size();
        if (area != 4) {
            while (inverses % 2 == 1) {
                Collections.shuffle(tiles);
                inverses = getInverses(tiles);
            }
        } else {
            boolean blankOnOddRow = blankOnOddRow(tiles);
            if (blankOnOddRow) {
                while (inverses % 2 == 1) {
                    Collections.shuffle(tiles);
                    inverses = getInverses(tiles);
                }
            } else {
                while (inverses % 2 == 0) {
                    Collections.shuffle(tiles);
                    inverses = getInverses(tiles);
                }
            }
        }
        return tiles;
    }

    /*
     * Sets the board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean puzzleSolved() {
        boolean solved = true;
        int currentNum = 1;
        for (Tile tile : board) {
            if (tile.getId() != currentNum) {
                solved = false;
            }
            currentNum++;
        }
        if (solved) {
            setEndTime();
        }
        return solved;
    }

    /**
     * Return whether the player win the game.
     *
     * @return whether the player win the game.
     */
    @Override
    public boolean isWinner() {
        return puzzleSolved();
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    public boolean isValidTap(int position) {

        int row = position / Board.NUM_COLS;
        int col = position % Board.NUM_COLS;
        int blankId = board.numTiles();
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == Board.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == Board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Undoes the previous move.
     */
    void undo() {
        if (!this.moveList.empty() && this.undoneMoves < this.undoCapacity) {
            this.undoneMoves += 2;
            this.touchMove(moveList.pop());
            moveList.pop();
        }
    }

    /*
     * Sets the undo capacity
     */
    public void setUndoCapacity(int i) {
        this.undoCapacity = i;
    }

    /*
     * Returns this BoardManager's undo capacity.
     */
    int getUndoCapacity() {
        return this.undoCapacity;
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    public void touchMove(int position) {

        int row = position / Board.NUM_ROWS;
        int col = position % Board.NUM_COLS;
        int blankId = board.numTiles();
        if (this.undoneMoves > 0) {
            this.undoneMoves--;
        }

        if (isValidTap(position)) {
            if (row != Board.NUM_ROWS - 1 && board.getTile(row + 1, col).getId() == blankId) {
                board.swapTiles(row + 1, col, row, col);
                moveList.push((row + 1) * (Board.NUM_ROWS) + col);
            } else if (col != Board.NUM_COLS - 1 && board.getTile(row, col + 1).getId() == blankId) {
                board.swapTiles(row, col + 1, row, col);
                moveList.push((row) * (Board.NUM_ROWS) + col + 1);
            } else if (row != 0 && board.getTile(row - 1, col).getId() == blankId) {
                board.swapTiles(row, col, row - 1, col);
                moveList.push((row - 1) * (Board.NUM_ROWS) + col);
            } else if (col != 0 && board.getTile(row, col - 1).getId() == blankId) {
                board.swapTiles(row, col, row, col - 1);
                moveList.push((row) * (Board.NUM_ROWS) + col - 1);
            }
        }
    }

    /*
     * Returns the number of board inverses in a list of tiles.
     * More info on inverses: https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
     *
     * @param tiles A list of tiles corresponding to a board
     * @return The number of inverses present in tiles, excluding the blank tiles
     */
    static int getInverses(List<Tile> tiles) {
        int inverses = 0;
        for (Tile tile : tiles) {
            int tile_id = tile.getId();
            boolean start_counting = false;
            for (Tile tile2 : tiles) {
                if (tile2.getId() == tile_id && tile_id != tiles.size()) {
                    start_counting = true;
                } else if (start_counting && tile2.getId() < tile_id) {
                    inverses++;
                }
            }
        }
        return inverses;
    }

    /*
     *Returns true iff the blank tile is on the odd row counting from the bottom of a 4x4 board
     *with tiles tiles.
     *
     * @param tiles A list of tiles corresponding to a 4x4 board.
     * @return Whether or not the blank tile is on an odd row from the bottom.
     */
    static boolean blankOnOddRow(List<Tile> tiles) {
        boolean isOddRow = false;
        int count = 0;
        for (Tile tile : tiles) {

            if (count == 4) {
                count = 0;
                isOddRow = !isOddRow;
            }
            if (tile.getId() == 16) {
                return isOddRow;
            }
            count++;
        }
        return isOddRow;
    }

}
