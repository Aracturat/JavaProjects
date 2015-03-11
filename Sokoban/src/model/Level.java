package model;

import java.util.Vector;

/**
 * This class store information about level.
 *
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
*/
public class Level implements Cloneable {

    private Vector< Vector<BlockType> > field;

    private int numberOfLine;

    private int numberOfColumn;

    private Point coordAI;

    private Point coordSpecial[];

    private String name;

    public Level(Vector< Vector<BlockType> > field, Point coordSpecial[], Point coord) {
        this.field = field;
        this.numberOfColumn = field.get(0).size();
        this.numberOfLine = field.size();
        this.coordAI = coord;
        this.coordSpecial = coordSpecial;
    }


    public Point[] getSpecialPlace() {
        return this.coordSpecial;
    }

    public Point getCoordAI() {
        return coordAI;
    }

    public void setAICoord(int x, int y) {
        this.coordAI = new Point(x, y);
    }

    public int getNumberOfLine() {
        return numberOfLine;
    }

    public int getNumberOfColumn() {
        return numberOfColumn;
    }

    public BlockType getTypeOfBlock(int x, int y) {
        if ((x <= numberOfColumn) && (x >= 0) &&
            (y <= numberOfLine) && (y >= 0)) {
            return field.get(y).get(x);
        }
        return BlockType.ERROR;
    }

    public void setTypeOfBlock(BlockType type, int x, int y) {
        if ((x <= numberOfColumn) && (x >= 0) &&
                (y <= numberOfLine) && (y >= 0)) {
            field.get(y).set(x, type);
        }
    }
    public boolean isMovable(int x, int y) {
        if ((x <= numberOfColumn) && (x >= 0) &&
            (y <= numberOfLine) && (y >= 0)) {
            BlockType type = field.get(y).get(x);
            if (type == BlockType.BOX) {
                return true;
            }
        }
        return false;
    }

    public boolean isSpace(int x, int y) {
        if ((x <= numberOfColumn) && (x >= 0) &&
            (y <= numberOfLine) && (y >= 0)) {
            BlockType type = field.get(y).get(x);
            if ((type == BlockType.SPACE) || (type == BlockType.SPECIAL)) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
