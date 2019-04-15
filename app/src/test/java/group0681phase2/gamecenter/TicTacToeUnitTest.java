package group0681phase2.gamecenter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TicTacToeUnitTest {

    //Tic Tile Tests
    @Test
    public void ticTileSecondaryConstructor() {
        TicTile tile = new TicTile("X", 0);
        assertEquals(0, tile.getBackground());
    }

    @Test
    public void ticTileGetPlayer() {
        TicTile tile = new TicTile("PLAYER1");
        assertEquals("PLAYER1", tile.getPlayer());
    }

    @Test
    public void ticTileSetPlayer() {
        TicTile tile = new TicTile("PLAYER1");
        tile.setPlayer("PLAYER2");
        assertEquals("PLAYER2", tile.getPlayer());
    }

    @Test
    public void ticTileIsEmpty() {
        TicTile tile = new TicTile("");
        assertTrue(tile.isEmpty());
    }

    @Test
    public void ticTileEquals() {
        TicTile tile1 = new TicTile("X");
        TicTile tile2 = new TicTile("X");
        assertEquals(tile1, tile2);
    }

    @Test
    public void ticTileNotEquals() {
        TicTile tile1 = new TicTile("X");
        Tile tile2 = new Tile(1);
        assertNotEquals(tile1, tile2);
    }

    @Test
    public void ticTileSetBackground() {
        int background = R.drawable.tile_x;
        TicTile tile = new TicTile("O");
        tile.setBackground("X");
        assertEquals(background, tile.getBackground());
    }

    @Test
    public void ticTileToString() {
        TicTile tile = new TicTile("X");
        assertEquals("Tile: X", tile.toString());
    }

    //Tic Board Tests
    @Test
    public void ticBoardSetSize() {
        TicBoard.setSize(4);
        assertEquals(4, TicBoard.NUM_ROWS);
        assertEquals(4, TicBoard.NUM_COLS);
    }

    @Test
    public void ticBoardNumTiles() {
        List<TicTile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != 9; tileNum++) {
            tiles.add(new TicTile(""));
        }
        TicBoard board = new TicBoard(tiles);
        assertEquals(9, board.numTiles());
    }

    @Test
    public void ticBoardGetTile() {
        List<TicTile> tiles = new ArrayList<>();
        TicBoard.setSize(3);
        for (int tileNum = 0; tileNum != 9; tileNum++) {
            tiles.add(new TicTile(""));
        }
        TicBoard board = new TicBoard(tiles);
        assertEquals(tiles.get(0), board.getTile(0, 0));
    }

    @Test
    public void ticBoardUpdateTilesSizeThree() {
        TicBoard.setSize(3);
        List<TicTile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != 9; tileNum++) {
            tiles.add(new TicTile(TicTile.EMPTY));
        }
        TicBoard board = new TicBoard(tiles);
        board.updateTiles(1, 1, TicTile.PLAYER1);
        assertEquals(TicTile.PLAYER1, board.getTile(1, 1).getPlayer());
    }

    @Test
    public void ticBoardUpdateTilesSizeFour() {
        TicBoard.setSize(4);
        List<TicTile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != 16; tileNum++) {
            tiles.add(new TicTile(TicTile.EMPTY));
        }
        TicBoard board = new TicBoard(tiles);
        board.updateTiles(2, 2, TicTile.PLAYER2);
        assertEquals(TicTile.PLAYER2, board.getTile(2, 2).getPlayer());
    }

    @Test
    public void ticBoardUpdateTilesSizeFive() {
        TicBoard.setSize(5);
        List<TicTile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != 25; tileNum++) {
            tiles.add(new TicTile(TicTile.EMPTY));
        }
        TicBoard board = new TicBoard(tiles);
        board.updateTiles(1, 2, TicTile.PLAYER1);
        assertEquals(TicTile.PLAYER1, board.getTile(1, 2).getPlayer());
    }

    @Test
    public void ticBoardRowComplete() {
        TicBoard.setSize(3);
        List<TicTile> tiles = new ArrayList<>();
        for (int i = 0; i != 3; i++) {
            tiles.add(new TicTile("X"));
        }
        for (int i = 3; i != 9; i++) {
            tiles.add(new TicTile(""));
        }
        TicBoard board = new TicBoard(tiles);
        assertTrue(board.rowCompleted());
    }

    @Test
    public void ticBoardColComplete() {
        TicBoard.setSize(3);
        List<TicTile> tiles = new ArrayList<>();
        for (int i = 0; i != 3; i++) {
            tiles.add(new TicTile("X"));
            tiles.add(new TicTile(""));
            tiles.add(new TicTile(""));
        }
        TicBoard board = new TicBoard(tiles);
        assertTrue(board.colCompleted());
    }

    @Test
    public void ticBoardDiagComplete() {
        TicBoard.setSize(3);
        List<TicTile> tiles = new ArrayList<>();
        tiles.add(new TicTile("X"));
        tiles.add(new TicTile(""));
        tiles.add(new TicTile(""));

        tiles.add(new TicTile(""));
        tiles.add(new TicTile("X"));
        tiles.add(new TicTile(""));

        tiles.add(new TicTile(""));
        tiles.add(new TicTile(""));
        tiles.add(new TicTile("X"));
        TicBoard board = new TicBoard(tiles);
        assertTrue(board.diagonalCompleted());
    }

    @Test
    public void ticBoardIsFull() {
        List<TicTile> tiles = new ArrayList<>();
        for (int i = 0; i != 9; i++) {
            tiles.add(new TicTile("X"));
        }
        TicBoard board = new TicBoard(tiles);
        assertTrue(board.isFull());
    }

    @Test
    public void ticBoardIsNotFull() {
        List<TicTile> tiles = new ArrayList<>();
        for (int i = 0; i != 8; i++) {
            tiles.add(new TicTile("X"));
        }
        tiles.add(new TicTile(""));
        TicBoard board = new TicBoard(tiles);
        assertTrue(!board.isFull());
    }

    @Test
    public void ticBoardGetEmptyPositions() {
        List<TicTile> tiles = new ArrayList<>();
        List<Integer> vals = new ArrayList<>();
        for (int tileNum = 0; tileNum != 9; tileNum++) {
            tiles.add(new TicTile(""));
            vals.add(tileNum);
        }
        TicBoard board = new TicBoard(tiles);
        List<Integer> results = board.getEmptyPositions();
        boolean eq = true;
        for (int i = 0; i < results.size(); i++) {
            if (! vals.get(i).equals(results.get(i))) {
                eq = false;
            }
        }
        assertTrue(eq);
    }

    @Test
    public void ticBoardToString() {
        TicBoard.setSize(3);
        List<TicTile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != 9; tileNum++) {
            tiles.add(new TicTile(TicTile.EMPTY));
        }
        TicBoard board = new TicBoard(tiles);
        board.updateTiles(1, 1, TicTile.PLAYER1);
        board.updateTiles(2, 1, TicTile.PLAYER2);
        board.updateTiles(0, 1, TicTile.PLAYER1);
        board.updateTiles(2, 0, TicTile.PLAYER2);
        assertEquals("Board{ tiles= Tile: , Tile: X, Tile: , Tile: , Tile: X, Tile: , Tile: O, Tile: O, Tile: }", board.toString());
    }

    //Tic Board Manager Tests
    @Test
    public void ticBoardManagerIsValidTap() {
        TicBoardManager bm = new TicBoardManager();
        assertTrue(bm.isValidTap(4));
    }

    @Test
    public void ticBoardManagerTouchMove() {
        TicBoardManager bm = new TicBoardManager();
        bm.touchMove(4);
        assertTrue(!bm.isValidTap(4));
    }

    @Test
    public void ticBoardManagerPuzzleSolved() {
        TicBoardManager bm = new TicBoardManager();
        for (int i = 0; i < 9; i++) {
            bm.touchMove(i);
        }
        assertTrue(bm.puzzleSolved());
    }

    @Test
    public void ticBoardManagerIsWinner() {
        TicBoardManager bm = new TicBoardManager();
        for (int i = 0; i < 9; i++) {
            bm.touchMove(i);
        }
        assertTrue(bm.isWinner());
    }
}
