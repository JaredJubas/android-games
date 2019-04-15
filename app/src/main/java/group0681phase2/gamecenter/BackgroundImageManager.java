package group0681phase2.gamecenter;

import android.graphics.Bitmap;

public class BackgroundImageManager {

    /**
     * The image.
     */
    private Bitmap image;

    /**
     * The width of the tile.
     */
    private int tileWidth;

    /**
     * The height of the tile.
     */
    private int tileHeight;

    /**
     * The complexity of the game.
     */
    private int complexity;

    /**
     * A new background image manager.
     *
     * @param bmp            the image
     * @param gameComplexity the complexity of the game
     */
    BackgroundImageManager(Bitmap bmp, int gameComplexity) {
        this.image = bmp;
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        this.complexity = gameComplexity;
        if (this.complexity > 0) {
            this.tileWidth = imageWidth / this.complexity;
            this.tileHeight = imageHeight / this.complexity;
        }
    }

    /**
     * Create a tile from the image
     *
     * @param backgroundId the tile id
     * @return the tile
     */
    public Bitmap getTileImage(int backgroundId) {

        int row = backgroundId / this.complexity;
        int col = backgroundId % this.complexity;

        int x = col * tileWidth;
        int y = row * tileHeight;

        return Bitmap.createBitmap(image, x, y, tileWidth, tileHeight);
    }
}