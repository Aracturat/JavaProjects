package model;

/**
 * This class store one score.
 *
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
*/
public class Score {

    private String playerName;

    private int numberOfStep;

    public Score(String playerName, int numberOfStep) {
        this.playerName = playerName;
        this.numberOfStep = numberOfStep;
    }

    public String toString() {
       return ( " " + playerName + " | " + String.valueOf(numberOfStep) + " ");
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public int getNumberOfStep() {
        return this.numberOfStep;
    }

}
