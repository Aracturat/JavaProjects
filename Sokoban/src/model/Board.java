package model;

import java.util.Observable;
import java.util.Set;
import java.util.TreeMap;

/**
 * Board is main class in game.
 * It is Model in MVC.
 *
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
*/
public class Board extends Observable {

    private LevelFactory levelFactory = new LevelFactory();

    private Scores scores;

    private TreeMap<String, String> allLevel;

    private Level currentLevel = null;

    private String nameOfCurrentLevel;

    private int steps = 0;

    private String about = new String("This is Sokoban. Dozmorov Nikolay. 2013");

    private String help = new String("Help. \n Sokoban also is called Pusher.");

    private boolean isEnd = false;

    public Board() {
        allLevel = levelFactory.createAllLevel("levels.properties");
        scores = ScoresTool.getScoresFromFile("scores.properties");
    }

    public Set<String> getAllLevelAsString() {
        return allLevel.keySet();
    }

    public Set<String> getAllLevel() {
        return this.allLevel.keySet();
    }

    public String getAbout() {
        return this.about;
    }

    public String getHelp() {
        return this.help;
    }

    public Scores getScores() {
        return this.scores;
    }

    public void reset() {
        currentLevel = null;
        steps = 0;
    }

    public void restart() {
        if (null != currentLevel) {
            String levelName = currentLevel.getName();
            currentLevel = levelFactory.createLevel(allLevel.get(levelName));
            currentLevel.setName(levelName);
            nameOfCurrentLevel = levelName;
            steps = 0;
        }
    }

    public void move(int x, int y, int deltaX, int deltaY) {

        if ((currentLevel.getNumberOfLine() > y-1) && (currentLevel.getNumberOfColumn() > x-1)) {

            BlockType boxType = currentLevel.getTypeOfBlock(x+deltaX, y-deltaY);

            if (boxType == BlockType.BOX) {
                if ((currentLevel.getNumberOfLine() > y-2) && (currentLevel.getNumberOfColumn() > x-2)) {
                    BlockType nextBoxType = currentLevel.getTypeOfBlock(x+2*deltaX, y-2*deltaY);
                    if  ((nextBoxType == BlockType.SPECIAL) ||
                            (nextBoxType == BlockType.SPACE)) {
                        currentLevel.setTypeOfBlock(BlockType.BOX, x+2*deltaX, y-2*deltaY);
                        currentLevel.setTypeOfBlock(BlockType.PUSHER, x+deltaX, y-deltaY);
                        currentLevel.setTypeOfBlock(BlockType.SPACE, x, y);
                        currentLevel.setAICoord(x+deltaX,y-deltaY);
                        steps++;
                    }
                }
            } else if ((boxType == BlockType.SPECIAL) || (boxType == BlockType.SPACE)) {
                currentLevel.setTypeOfBlock(BlockType.PUSHER, x+deltaX, y-deltaY);
                currentLevel.setTypeOfBlock(BlockType.SPACE, x, y);
                currentLevel.setAICoord(x+deltaX,y-deltaY);
                steps++;
            }
        }

        int countOfBox = 0;
        for (Point point : currentLevel.getSpecialPlace()) {
            BlockType type = currentLevel.getTypeOfBlock(point.getX(), point.getY());
            if (false == ((type == BlockType.BOX) || (type == BlockType.PUSHER))) {
                currentLevel.setTypeOfBlock(BlockType.SPECIAL, point.getX(), point.getY());
            } else if (type == BlockType.BOX) {
                countOfBox++;
            }
        }

        if (countOfBox == currentLevel.getSpecialPlace().length) {
            finish();
        }

        this.setChanged();
        this.notifyObservers();

    }

    public void startGame(String levelName) {
        currentLevel = levelFactory.createLevel(allLevel.get(levelName));
        currentLevel.setName(levelName);
        nameOfCurrentLevel = levelName;
        steps = 0;
    }

    public int getSizeX() {
        if (null == currentLevel) {
            return 0;
        }
        return this.currentLevel.getNumberOfColumn();
    }

    public int getSizeY() {
        if (null == currentLevel) {
            return 0;
        }
        return this.currentLevel.getNumberOfLine();
    }

    public Level getCurrentLevel() {
        return this.currentLevel;
    }

    public String getScore(String levelName) {
        Score score = scores.getScore(levelName);
        if (null == score) {
            return new String(" Not record ");
        } else {
            return score.toString();
        }

    }

    public void finish() {
        isEnd = true;
        nameOfCurrentLevel = currentLevel.getName();
    }

    public int getSteps() {
        return steps;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void endGame() {
        this.steps = 0;
        this.currentLevel = null;
        this.isEnd = false;
    }

    public String getNameOfCurrentLevel() {
        return nameOfCurrentLevel;
    }
}

