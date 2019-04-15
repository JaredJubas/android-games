package group0681phase2.gamecenter;


import java.io.Serializable;


public abstract class AbstractBoardManager implements Serializable {

    /**
     * The start time of the clock.
     */
    private double startTime;
    /**
     * The end time of the clock.
     */
    private double endTime;
    /**
     * The difference between start and end time.
     */
    private double totalTime;

    protected AbstractBoardManager() {
        this.startTime = System.currentTimeMillis();
    }

    protected void setEndTime() {
        this.endTime = System.currentTimeMillis();
        this.totalTime = (this.endTime - this.startTime) / 1000;
    }

    public double getTotalTime() {
        return this.totalTime;
    }

    abstract public boolean puzzleSolved();

    abstract public boolean isValidTap(int position);

    abstract public void touchMove(int position);

    abstract public void setUndoCapacity(int i);

    abstract public void syncSize();

    abstract public boolean isWinner();


}