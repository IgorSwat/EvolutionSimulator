package agh.ics.oop;
import java.util.ArrayList;
import java.util.List;

public class RectangularMap extends AbstractWorldMap
{
    private static final Vector2d left_corner = new Vector2d(0, 0);
    private final Vector2d right_corner;
    public RectangularMap(int width, int height)
    {
        right_corner = new Vector2d(width - 1, height - 1);
    }
    public Vector2d getLeftCorner() {return left_corner;}
    public Vector2d getRightCorner() {return right_corner;}
    public boolean canMoveTo(Vector2d position)
    {
        return (position.follows(left_corner) && position.precedes(right_corner) && !super.isOccupied(position));
    }
}
