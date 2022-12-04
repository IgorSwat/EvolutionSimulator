package agh.ics.oop;
import java.util.TreeMap;

// Data structure to provide O(log n) complexity of dynamic determination of map corners
public class MapBoundary
{
    private final BoundaryContainer horizontalContainer;
    private final BoundaryContainer verticalContainer;
    public MapBoundary()
    {
        horizontalContainer = new BoundaryContainer();
        verticalContainer = new BoundaryContainer();
    }
    public void loadObject(IMapElement element)
    {
        Vector2d position = element.getPosition();
        horizontalContainer.add(position.x);
        verticalContainer.add(position.y);
    }
    public void reloadObject(IMapElement element, Vector2d newPosition)
    {
        Vector2d oldPosition = element.getPosition();
        if (newPosition.x != oldPosition.x)
        {
            horizontalContainer.subtract(oldPosition.x);
            horizontalContainer.add(newPosition.x);
        }
        if (newPosition.y != oldPosition.y)
        {
            verticalContainer.subtract(oldPosition.y);
            verticalContainer.add(newPosition.y);
        }
    }
    public void forgetObject(IMapElement element)
    {
        Vector2d position = element.getPosition();
        horizontalContainer.subtract(position.x);
        verticalContainer.subtract(position.y);
    }
    public Vector2d getTopCorner()
    {
        int x = horizontalContainer.getMaxBorder();
        int y = verticalContainer.getMaxBorder();
        return new Vector2d(x, y);
    }
    public Vector2d getBottomCorner()
    {
        int x = horizontalContainer.getMinBorder();
        int y = verticalContainer.getMinBorder();
        return new Vector2d(x, y);
    }
}

class BoundaryContainer
{
    private final TreeMap<Integer, Integer> borderValues;
    public BoundaryContainer() {borderValues = new TreeMap<>();}
    public void add(int v)
    {
        Integer oldValue = borderValues.get(v);
        if (oldValue == null)
            borderValues.put(v, 1);
        else
        {
            borderValues.remove(v);
            borderValues.put(v, oldValue + 1);
        }
    }
    public void subtract(int v)
    {
        Integer oldValue = borderValues.get(v);
        if (oldValue != null)
        {
            borderValues.remove(v);
            if (oldValue > 1)
                borderValues.put(v, oldValue - 1);
        }
    }
    public int getMaxBorder()
    {
        return borderValues.lastKey();
    }
    public int getMinBorder()
    {
        return borderValues.firstKey();
    }
}