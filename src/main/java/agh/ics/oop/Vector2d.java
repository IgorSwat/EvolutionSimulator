package agh.ics.oop;

import agh.ics.oop.map.IWorldMap;

public class Vector2d
{
    public final int x;
    public final int y;
    public Vector2d(int x_, int y_)
    {
        x = x_;
        y = y_;
    }
    public String toString()
    {
        return new String("(" + Integer.toString(x) + "," + Integer.toString(y) + ")");
    }
    public boolean precedes(Vector2d other)
    {
        return (x <= other.x && y <= other.y);
    }
    public boolean equals(Object other)
    {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d vector = (Vector2d) other;
        return (x == vector.x && y == vector.y);
    }
    public int hashCode()
    {
        int hash = 13;
        hash = 31 * hash + x;
        hash = 31 * hash + y;
        return hash;
    }
    public boolean follows(Vector2d other)
    {
        return (x >= other.x && y >= other.y);
    }
    public Vector2d upperRight(Vector2d other)
    {
        return new Vector2d(Math.max(x, other.x), Math.max(y, other.y));
    }
    public Vector2d lowerLeft(Vector2d other)
    {
        return new Vector2d(Math.min(x, other.x), Math.min(y, other.y));
    }
    public Vector2d add(Vector2d other)
    {
        return new Vector2d(x + other.x, y + other.y);
    }
    public Vector2d subtract(Vector2d other)
    {
        return new Vector2d(x - other.x, y - other.y);
    }
    public Vector2d opposite()
    {
        return new Vector2d(-x, -y);
    }
    public int getSquareID(int mapWidth) {
        return this.y * mapWidth + this.x;
    }
}
