package agh.ics.oop;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;

public class GrassField extends AbstractWorldMap
{
    private final Map<Vector2d, Grass> grass;
    private final int grassRange;
    private Vector2d topRight;
    private Vector2d bottomLeft;
    private final MapBoundary border;
    private void updateCorners()
    {
        topRight = border.getTopCorner();
        bottomLeft = border.getBottomCorner();
    }
    private void fixedGrassGeneration() // Finds all avaible squares and then generates one grass randomly
    {
        ArrayList<Vector2d> freeSquares = new ArrayList<>();
        for (int i = 0; i < grassRange; i++)
        {
            for (int j = 0; j < grassRange; j++)
            {
                Vector2d position = new Vector2d(i, j);
                if (!isOccupied(position))
                    freeSquares.add(position);
            }
        }
        if (freeSquares.size() > 0)
        {
            Random generator = new Random();
            int i = generator.nextInt(freeSquares.size());
            Vector2d position = freeSquares.get(i);
            Grass g = new Grass(position);
            grass.put(position, g);
            border.loadObject(g);
            updateCorners();
        }
    }
    private void randomGrassGeneration(int n)
    {
        Random generator = new Random();
        int count = 0;
        while (count < n)
        {
            int i = generator.nextInt(grassRange);
            int j = generator.nextInt(grassRange);
            Vector2d pos = new Vector2d(i, j);
            if (!isOccupied(pos))
            {
                count += 1;
                Grass g = new Grass(pos);
                grass.put(pos, g);
                border.loadObject(g);
            }
        }
        updateCorners();
    }
    public GrassField(int n)
    {
        grassRange = (int)Math.sqrt(10*n) + 1;
        topRight = new Vector2d(0, 0);
        bottomLeft = topRight;
        grass = new HashMap<>();
        border = new MapBoundary();

        randomGrassGeneration(n);
    }
    public Vector2d getLeftCorner() {return bottomLeft;}
    public Vector2d getRightCorner() {return topRight;}
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
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition)
    {
        super.positionChanged(oldPosition, newPosition);
        Animal animal = animals.get(newPosition);
        if (grass.get(newPosition) != null)
        {
            grass.remove(newPosition);
            border.forgetObject(animal);
            if (animals.size() > 85 * grassRange * grassRange / 100)
                fixedGrassGeneration();
            else
                randomGrassGeneration(1);
        }
        else
        {
            border.reloadObject(animal, newPosition);
            updateCorners();
        }
    }
}
