package model;

/**
 *  Simple realization of two-dimensional point.
 *
 *  @author Aracturat (Nikolay Dozmorov)
 *  @version 0.1
 */
public class Point {

    /** X coordinate */
    private int x;

    /** Y coordinate */
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this.x = 0;
        this.y = 0;
    }

    public int getX()
    {
        return this.x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return this.y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setCoord(Point point) {
        this.x = point.getX();
        this.y = point.getY();
    }
}