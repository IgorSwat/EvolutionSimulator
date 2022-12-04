package agh.ics.oop;
import java.util.ArrayList;
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
    public boolean canMoveTo(Vector2d position) {return animals.get(position) == null;}
    public boolean isOccupied(Vector2d position)
    {
        return objectAt(position) != null;
    }
    public boolean place(Animal animal)
    {
        if (!canMoveTo(animal.getPosition()))
            throw new IllegalArgumentException(animal.getPosition() + " is not a legal square for animal");
        animals.put(animal.getPosition(), animal);
        return true;
    }
    public Object objectAt(Vector2d position)
    {
        return animals.get(position);
    }
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition)
    {
        Animal animal = animals.get(oldPosition);
        animals.remove(oldPosition, animal);
        animals.put(newPosition, animal);
    }
    public String toString()
    {
        return visualization.draw(getLeftCorner(), getRightCorner());
    }
}
