package group0681phase2.gamecenter;

import java.io.Serializable;

/**
 * A Tile in a Tic-Tac-Toe game.
 */
public class TicTile implements Serializable {
    /**
     * The string representation of player 1.
     */
    static final String PLAYER1 = "X";

    /**
     * The string representation of player 2.
     */
    static final String PLAYER2 = "O";

    /**
     * The string representation of an empty tile
     */
    static final String EMPTY = "";

    /**
     * The background id to find the tile image.
     */
    private int background;

    /**
     * The player.
     */
    private String player = EMPTY;

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Return the player who occupy the tile.
     *
     * @return the the player who occupy the tile
     */
    public String getPlayer() {
        return player;
    }

    /**
     * A Tile with player and background. The background may not have a corresponding image.
     *
     * @param player     the player
     * @param background the background
     */
    public TicTile(String player, int background) {
        this.player = player;
        this.background = background;
    }

    /**
     * A tile with a player name; look up and set the player.
     *
     * @param player the player
     */
    public TicTile(String player) {
        setPlayer(player);
    }


    /**
     * Set the player on the tile.
     *
     * @param player the player
     */
    public void setPlayer(String player) {
        this.player = player;
        setBackground(player);
    }

    /**
     * Set the background of the tile according to the player name.
     *
     * @param player the player
     */
    public void setBackground(String player) {
        switch (player) {
            case PLAYER2:
                this.background = R.drawable.tile_o;
                break;
            case PLAYER1:
                this.background = R.drawable.tile_x;
                break;
            case EMPTY:
                this.background = R.drawable.tile_blank;
                break;
            default:
                this.background = R.drawable.tile_blank;
        }
    }


    /**
     * Return whether this tile is equal to other.
     *
     * @param obj the other object
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof TicTile) {
            return ((TicTile) obj).player.equals(this.player);
        } else {
            return false;
        }
    }

    /**
     * Return whether this tile is empty.
     */
    public boolean isEmpty() {
        return this.player.equals(EMPTY);
    }

    @Override
    public String toString() {
        return "Tile: " + this.getPlayer();
    }
}