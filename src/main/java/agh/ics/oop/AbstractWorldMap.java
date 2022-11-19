package agh.ics.oop;
import java.util.Map;
import java.util.HashMap;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver
{
    private final MapVisualizer visualization;
    protected final Map<Vector2d, Animal> animals;
    public AbstractWorldMap()
    {
        visualization = new MapVisualizer(this);
        animals = new HashMap<>();
    }
    public abstract Vector2d getRightCorner();
    public abstract Vector2d getLeftCorner();
    public abstract boolean canMoveTo(Vector2d position);
    public boolean isOccupied(Vector2d position)
    {
        return animals.get(position) != null;
    }
    public boolean place(Animal animal)
    {
        if (!canMoveTo(animal.getPosition()))
            return false;
        animals.put(animal.getPosition(), animal);
        return true;
    }
    public Object objectAt(Vector2d position)
    {
        return animals.get(position);
    }
    public boolean positionChanged(Vector2d oldPosition, Vector2d newPosition)
    {
        Animal animal = animals.get(oldPosition);
        if (animal != null)
        {
            animals.remove(oldPosition, animal);
            animals.put(newPosition, animal);
            return true;
        }
        return false;
    }
    public String toString()
    {
        return visualization.draw(getLeftCorner(), getRightCorner());
    }
}
