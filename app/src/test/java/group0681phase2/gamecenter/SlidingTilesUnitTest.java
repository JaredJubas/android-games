package group0681phase2.gamecenter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit Tests pertaining to Sliding Tiles classes.
 */
public class SlidingTilesUnitTest {

    //Tile Tests
    @Test
    public void tileGetBackground() {
        Tile tile = new Tile(0);
        assertEquals(R.drawable.tile_1, tile.getBackground());
    }

    @Test
    public void tileSecondaryConstructor() {
        Tile tile = new Tile(0, 1);
        assertEquals(0, tile.getId());
    }

    @Test
    public void tileToString() {
        Tile tile = new Tile(0);
        assertEquals("Tile: 1", tile.toString());
    }

    @Test
    public void tileCompareToEquals() {
        Tile tile1 = new Tile(0);
        assertEquals(0, tile1.compareTo(tile1));
    }

    @Test
    public void tileCompareToGreater() {
        Tile tile1 = new Tile(0);
        Tile tile2 = new Tile(1);
        assertTrue(tile1.compareTo(tile2) > 0);
    }

    //Board Tests
    @Test
    public void boardSetSize() {
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != 16; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        Board board = new Board(tiles);
        board.setSize(3);
        assertEquals(3, board.NUM_ROWS);
    }

    @Test
    public void boardGetTile() {
        List<Tile> tiles = new ArrayList<>();
        Tile tile1 = new Tile(0);
        tiles.add(tile1);
        for (int tileNum = 1; tileNum != 25; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        Board board = new Board(tiles);
        assertEquals(tile1, board.getTile(0, 0));
    }

    @Test
    public void boardTestSwapTiles(){
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != 16; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        Board board = new Board(tiles);
        Tile tile = board.getTile(0, 0);
        board.swapTiles(0,0,0,1);
        assertEquals(tile, board.getTile(0, 1));
    }

    @Test
    public void boardTestNumTiles() {
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != 16; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        Board board = new Board(tiles);
        board.setSize(4);
        assertEquals(16, board.numTiles());
    }

    @Test
    public void boardToString() {
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != 16; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        Board board = new Board(tiles);
        String s = "Board{ tiles= Tile: 1, Tile: 2, Tile: 3, Tile: 4, Tile: 5, Tile: 6, Tile: 7, " +
                "Tile: 8, Tile: 9, Tile: 10, Tile: 11, Tile: 12, Tile: 13, Tile: 14, Tile: 15, " +
                "Tile: 16}";
        assertEquals(s, board.toString());
    }

    //Board Manager Tests
    @Test
    public void boardManagerSizeTests() {
        BoardManager bm = new BoardManager();
        bm.syncSize();
        assertEquals(4, bm.getSize());
    }

    @Test
    public void boardManagerGetInversesInOrder() {
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != 9; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        assertEquals(0, BoardManager.getInverses(tiles));
    }

    @Test
    public void boardManagerGetInversesReversed() {
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 8; tileNum >= 0; tileNum--) {
            tiles.add(new Tile(tileNum));
        }
        assertEquals(7+6+5+4+3+2+1, BoardManager.getInverses(tiles));
    }

    @Test
    public void boardManagerGetInversesRandom() {
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(4));
        tiles.add(new Tile(0));
        tiles.add(new Tile(7));
        tiles.add(new Tile(8));
        tiles.add(new Tile(1));
        tiles.add(new Tile(3));
        tiles.add(new Tile(2));
        tiles.add(new Tile(5));
        tiles.add(new Tile(6));
        assertEquals(4+5+1, BoardManager.getInverses(tiles));
    }

    @Test
    public void boardManagerBlankOnOddRowOddRow() {
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != 16; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        assertTrue(BoardManager.blankOnOddRow(tiles));
    }

    @Test
    public void boardManagerBlankOnOddRowEvenRow() {
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 15; tileNum >= 0; tileNum--) {
            tiles.add(new Tile(tileNum));
        }
        assertTrue(!BoardManager.blankOnOddRow(tiles));
    }

    @Test
    public void boardManagerGetAndSetUndoCapacity() {
        BoardManager bm = new BoardManager();
        bm.setUndoCapacity(4);
        assertEquals(4, bm.getUndoCapacity());
    }

    @Test
    public void boardManagerTouchMove() {
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != 16; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        Board.setSize(4);
        Board board = new Board(tiles);
        BoardManager bm = new BoardManager();
        bm.setBoard(board);
        bm.touchMove(14);
        assertFalse(bm.isValidTap(14));
    }

    @Test
    public void boardManagerUndoMove() {
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != 16; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        Board.setSize(4);
        Board board = new Board(tiles);
        BoardManager bm = new BoardManager();
        bm.setBoard(board);
        bm.touchMove(14);
        bm.setUndoCapacity(2);
        bm.undo();
        assertTrue(bm.isValidTap(14));
    }

    @Test
    public void boardManagerIsValidTap() {
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != 16; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        Board board = new Board(tiles);
        BoardManager bm = new BoardManager();
        bm.setBoard(board);
        assertTrue(bm.isValidTap(14));
    }

    @Test
    public void boardManagerPuzzleSolved() {
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != 16; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        Board board = new Board(tiles);
        BoardManager bm = new BoardManager();
        bm.setBoard(board);
        assertTrue(bm.puzzleSolved());
    }

    @Test
    public void boardManagerIsWinner() {
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != 16; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        Board board = new Board(tiles);
        BoardManager bm = new BoardManager();
        bm.setBoard(board);
        assertTrue(bm.isWinner());
    }

    @Test
    public void boardManagerGetBoard() {
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != 16; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        Board board = new Board(tiles);
        BoardManager bm = new BoardManager();
        bm.setBoard(board);
        assertEquals(board, bm.getBoard());
    }

    @Test
    public void loadBackgroundActivityUpdateDisplay() {
        LoadBackgroundActivity lba = new LoadBackgroundActivity();
    }
}