package agh.ics.oop;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class GrassField extends AbstractWorldMap
{
    private final Map<Vector2d, Grass> grass;
    private final int grassCount;
    private Vector2d topRight;
    private Vector2d bottomLeft;
    private final BorderCalculator border;
    private void updateCorners()
    {
        topRight = border.getTopCorner();
        bottomLeft = border.getBottomCorner();
    }
    private void generateGrassFix(int rest) // Fills the grass on the map, when there are too many random iterations
    {
        int max_range = (int)Math.sqrt(10*grassCount) + 1;
        for (int i = 0; i < max_range; i++)
        {
            for (int j = 0; j < max_range; j++)
            {
                if (rest == 0) return;
                Vector2d pos = new Vector2d(i, j);
                if (!isOccupied(pos))
                {
                    rest -= 1;
                    Grass g = new Grass(pos);
                    grass.put(pos, g);
                    border.loadObject(g);
                }
            }
        }
    }
    private void generateGrass(int n)
    {
        Random generator = new Random();
        int count = 0;
        int iterations = 0;
        int max_range = (int)Math.sqrt(10*grassCount) + 1;
        while (iterations < 10*grassCount && count < n)
        {
            iterations += 1;
            int i = generator.nextInt(max_range);
            int j = generator.nextInt(max_range);
            Vector2d pos = new Vector2d(i, j);
            if (!isOccupied(pos))
            {
                count += 1;
                Grass g = new Grass(pos);
                grass.put(pos, g);
                border.loadObject(g);
            }
        }
        // -------- Optional? How likely is it to happen?
        if (count != n)
            generateGrassFix(n - count);
        // --------
        updateCorners();
    }
    public GrassField(int n)
    {
        grassCount = n;
        topRight = new Vector2d(0, 0);
        bottomLeft = topRight;
        grass = new HashMap<>();
        border = new BorderCalculator();

        generateGrass(n);
    }
    public Vector2d getLeftCorner() {return bottomLeft;}
    public Vector2d getRightCorner() {return topRight;}
    public boolean canMoveTo(Vector2d position)
    {
        return animals.get(position) == null;
    }
    public boolean isOccupied(Vector2d position)
    {
        return super.isOccupied(position) || (grass.get(position) != null);
    }
    public boolean place(Animal animal)
    {
        boolean result = super.place(animal);
        if (result)
        {
            border.loadObject(animal);
            updateCorners();
        }
        return result;
    }
    public Object objectAt(Vector2d position)
    {
        Object result = super.objectAt(position);
        if (result != null) return result;
        return grass.get(position);
    }
    public boolean positionChanged(Vector2d oldPosition, Vector2d newPosition)
    {
        if (!super.positionChanged(oldPosition, newPosition))
            return false;
        Animal animal = animals.get(newPosition);
        if (grass.get(newPosition) != null)
        {
            grass.remove(newPosition);
            border.forgetObject(animal);
            generateGrass(1);
        }
        else
        {
            border.reloadObject(animal, newPosition);
            updateCorners();
        }
        return true;
    }
}
