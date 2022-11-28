package agh.ics.oop;
import java.util.ArrayList;
import java.util.List;

public class Animal implements IMapElement
{
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;
    private final IWorldMap map;
    private final List<IPositionChangeObserver> observers = new ArrayList<>();

    public Animal(IWorldMap map)
    {
        position = new Vector2d(2, 2);
        this.map = map;
    }
    public Animal(IWorldMap map, Vector2d initialPosition)
    {
        position = initialPosition;
        this.map = map;
    }
    public Animal(IWorldMap map, Vector2d initialPosition, MapDirection initialOrientation)
    {
        this(map, initialPosition);
        orientation = initialOrientation;
    }
    public void addObserver(IPositionChangeObserver observer)
    {
        observers.add(observer);
    }
    public boolean removeObserver(IPositionChangeObserver observer)
    {
        return observers.remove(observer);
    }
    private void positionChanged(Vector2d newPosition)
    {
        for (IPositionChangeObserver observer : observers)
        {
            observer.positionChanged(position, newPosition);
        }
    }
    public String toString()
    {
        return switch (orientation) {
            case NORTH -> "N";
            case SOUTH -> "S";
            case EAST -> "E";
            case WEST -> "W";
        };
    }
    public boolean isAt(Vector2d pos) {return position.equals(pos);}
    public Vector2d getPosition() {return position;}
    public void move(MoveDirection direction)
    {
        Vector2d new_pos;
        switch (direction)
        {
            case RIGHT:
                orientation = orientation.next();
                break;
            case LEFT:
                orientation = orientation.previous();
                break;
            case FORWARD:
                new_pos = position.add(orientation.toUnitVector());
                if (map.canMoveTo(new_pos))
                {
                    positionChanged(new_pos);
                    position = new_pos;
                }
                break;
            case BACKWARD:
                new_pos = position.subtract(orientation.toUnitVector());
                if (map.canMoveTo(new_pos))
                {
                    positionChanged(new_pos);
                    position = new_pos;
                }
                break;
        }
    }
}