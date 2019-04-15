package group0681phase2.gamecenter;

import android.support.annotation.NonNull;

import java.util.NoSuchElementException;
import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Iterable<Tile>, Serializable {

    /**
     * The number of rows.
     */
    static int NUM_ROWS = 4;

    /**
     * The number of rows.
     */
    static int NUM_COLS = 4;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles = new Tile[NUM_ROWS][NUM_COLS];

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    Board(List<Tile> tiles) {
        Iterator<Tile> iter = tiles.iterator();

        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
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
    public BoardIterator iterator() {
        return new BoardIterator();
    }

    private class BoardIterator implements Iterator<Tile> {

        int rowI = 0;
        int colI = 0;

        @Override
        public boolean hasNext() {
            return rowI != Board.NUM_ROWS - 1 || colI != Board.NUM_COLS - 1;
        }

        @Override
        public Tile next() {
            if (hasNext()) {
                Tile result = getTile(rowI, colI);
                if (colI < Board.NUM_COLS - 1) {
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
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        Tile temp = getTile(row2, col2);
        this.tiles[row2][col2] = getTile(row1, col1);
        this.tiles[row1][col1] = temp;


        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Board{ tiles= ");
        for (Tile[] tile1 : tiles) {
            for (Tile tile : tile1) {
                s.append(tile.toString());
                s.append(", ");
            }
        }
        s.delete(s.length() - 2, s.length());
        s.append("}");
        return s.toString();
    }
}