package group0681phase2.gamecenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The Tic-Tac-Toe tiles board.
 */
public class TicBoard extends Observable implements Serializable, Iterable<TicTile> {
    /**
     * The number of rows.
     */
    static int NUM_ROWS = 3;

    /**
     * The number of rows.
     */
    static int NUM_COLS = 3;

    /**
     * The tiles on the board in row-major order.
     */
    private TicTile[][] tiles = new TicTile[NUM_ROWS][NUM_COLS];

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    TicBoard(List<TicTile> tiles) {
        Iterator<TicTile> iter = tiles.iterator();

        for (int row = 0; row != TicBoard.NUM_ROWS; row++) {
            for (int col = 0; col != TicBoard.NUM_COLS; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * Set the size of the board
     *
     * @param size the size of the board
     */
    public static void setSize(int size) {
        NUM_ROWS = size;
        NUM_COLS = size;
    }

    @NonNull
    @Override
    public TicBoardIterator iterator() {
        return new TicBoardIterator();
    }

    private class TicBoardIterator implements Iterator<TicTile> {

        int rowI = 0;
        int colI = 0;

        @Override
        public boolean hasNext() {
            return rowI != TicBoard.NUM_ROWS - 1 || colI != TicBoard.NUM_COLS - 1;
        }

        @Override
        public TicTile next() {
            if (hasNext()) {
                TicTile result = getTile(rowI, colI);
                if (colI < TicBoard.NUM_COLS - 1) {
                    colI++;
                } else {
                    colI = 0;
                    rowI++;
                }
                return result;
            }
            throw new NoSuchElementException("No More Elements");
        }
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    int numTiles() {
        return NUM_ROWS * NUM_COLS;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    TicTile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Update the tile at (row, col) with the given player name
     *
     * @param row    the tile row
     * @param col    the tile column
     * @param player the player name
     */
    void updateTiles(int row, int col, String player) {

        tiles[row][col].setPlayer(player);

        setChanged();
        notifyObservers();
    }


    /**
     * Return whether a row is occupied by a player.
     *
     * @return whether a row is occupied by a player.
     */
    public boolean rowCompleted() {

        for (int i = 0; i < NUM_ROWS; i++) {
            boolean completed = true;
            for (int j = 0; j < NUM_COLS; j++) {
                if (!(tiles[i][j].equals(tiles[i][0])) || (tiles[i][j]).isEmpty()) {
                    completed = false;
                }
            }
            if (completed) {
                return true;
            }

        }
        return false;
    }

    /**
     * Return whether a col is occupied by a player.
     *
     * @return whether a col is occupied by a player.
     */
    public boolean colCompleted() {

        for (int i = 0; i < NUM_COLS; i++) {
            boolean completed = true;
            for (int j = 0; j < NUM_ROWS; j++) {
                if (!(tiles[j][i].equals(tiles[0][i])) || tiles[j][i].isEmpty()) {
                    completed = false;
                }
            }
            if (completed) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return whether one of the diagonals is occupied by a player.
     *
     * @return whether one of the diagonals is occupied by a player.
     */
    public boolean diagonalCompleted() {
        boolean completedLeft = true;
        boolean completedRight = true;
        for (int i = 0; i < NUM_ROWS; i++) {
            if (!(tiles[i][i].equals(tiles[0][0])) || tiles[i][i].isEmpty()) {
                completedLeft = false;

            }
        }
        for (int i = NUM_ROWS - 1; i >= 0; i--) {
            if (!(tiles[NUM_ROWS - 1 - i][i].equals(tiles[0][NUM_COLS - 1])) ||
                    tiles[NUM_ROWS - 1 - i][i].isEmpty()) {
                completedRight = false;

            }
        }

        return completedLeft || completedRight;
    }


    /**
     * Return whether the board is fully occupied.
     *
     * @return whether one of the diagonals is occupied by a player.
     */
    public boolean isFull() {
        for (int row = 0; row != TicBoard.NUM_ROWS; row++) {
            for (int col = 0; col != TicBoard.NUM_COLS; col++) {
                if (tiles[row][col].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Return a list of empty tiles on the board.
     *
     * @return a list of empty tiles on the board.
     */
    public List<Integer> getEmptyPositions() {
        List<Integer> result = new ArrayList<>();
        for (int row = 0; row != TicBoard.NUM_ROWS; row++) {
            for (int col = 0; col != TicBoard.NUM_COLS; col++) {
                if (tiles[row][col].isEmpty()) {
                    int position = col + row * TicBoard.NUM_ROWS;
                    result.add(new Integer(position));
                }
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Board{ tiles= ");
        for (TicTile[] tile1 : tiles) {
            for (TicTile tile : tile1) {
                s.append(tile.toString());
                s.append(", ");
            }
        }
        s.delete(s.length() - 2, s.length());
        s.append("}");
        return s.toString();
    }

}
