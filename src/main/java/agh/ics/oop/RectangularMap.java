package agh.ics.oop;
import java.util.ArrayList;
import java.util.List;


public class RectangularMap implements IWorldMap
{
    private static final Vector2d left_corner = new Vector2d(0, 0);
    private final Vector2d right_corner;
    private final List<Animal> animals;
    private final MapVisualizer visualization;
    public RectangularMap(int width, int height)
    {
        right_corner = new Vector2d(width - 1, height - 1);
        animals = new ArrayList<>();
        visualization = new MapVisualizer(this);
    }
    public int getWidth() {return right_corner.x + 1;}
    public int getHeight() {return right_corner.y + 1;}
    public boolean isOccupied(Vector2d position)
    {
        for (Animal x : animals)
        {
            if (x.isAt(position))
                return true;
        }
        return false;
    }
    public boolean canMoveTo(Vector2d position)
    {
        return (!isOccupied(position) && position.follows(left_corner) && position.precedes(right_corner));
    }
    public boolean place(Animal animal)
    {
        Vector2d position = animal.getPosition();
        if (isOccupied(position))
            return false;
        for (Animal x : animals)
            if (x == animal)
                return true;
        animals.add(animal);
        return true;
    }
    public Object objectAt(Vector2d position)
    {
        for (Animal x : animals)
        {
            if (x.isAt(position))
                return x;
        }
        return null;
    }
    public String toString()
    {
        return visualization.draw(left_corner, right_corner);
    }
}
