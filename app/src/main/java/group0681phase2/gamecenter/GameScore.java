package group0681phase2.gamecenter;

import java.io.Serializable;

public class GameScore implements Serializable {

    private String userName;
    private String gameName;
    private double totalTime;
    private int gameSize;

    public GameScore(String userName, String gameName, double totalTime) {
        setGameScore(userName, gameName, totalTime, 0);
    }

    public GameScore(String userName, String gameName, double totalTime, int gameSize) {
        setGameScore(userName, gameName, totalTime, gameSize);
    }

    private void setGameScore(String userName, String gameName, double totalTime, int gameSize) {
        if (userName == null || userName.equals("")) {
            this.userName = "Guest";
        } else {
            this.userName = userName;
        }

        this.gameName = gameName;
        this.totalTime = totalTime;
        this.gameSize = gameSize;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getGameName() {
        return this.gameName;
    }

    public double getTotalTime() {
        return this.totalTime;
    }

    public int getGameSize() {
        return this.gameSize;
    }

    @Override
    public String toString() {
        String gameSizeString = "";

        if (this.gameSize > 0) {
            gameSizeString = " (" + Integer.toString(this.gameSize) + " x " +
                    Integer.toString(this.gameSize) + ")";
        }


        return Double.toString(this.totalTime) + " sec: " + this.userName + " - " +
                this.gameName + gameSizeString;

    }
}
