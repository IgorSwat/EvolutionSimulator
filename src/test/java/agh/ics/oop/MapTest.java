package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static  org.junit.jupiter.api.Assertions.*;

public class MapTest
{
    @Test
    void test1()
    {
        String[] args = {"f", "b", "r", "f", "l", "b", "b", "l"};
        MoveDirection[] dirs = OptionsParser.parse(args);
        IWorldMap map = new RectangularMap(6, 6);
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(4, 4)};
        IEngine engine = new SimulationEngine(dirs, map, positions);
        engine.run();

        IWorldMap correct = new RectangularMap(6, 6);
        correct.place(new Animal(correct, new Vector2d(2, 2), MapDirection.NORTH));
        correct.place(new Animal(correct, new Vector2d(4, 3), MapDirection.WEST));

        assertEquals(map.toString(), correct.toString());
    }
    @Test
    void test2()
    {
        String[] args = {"f", "r", "f", "b", "r", "b", "f", "f", "f", "l", "r", "f"};
        MoveDirection[] dirs = OptionsParser.parse(args);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = {new Vector2d(3, 2), new Vector2d(6, 4)};
        IEngine engine = new SimulationEngine(dirs, map, positions);
        engine.run();

        IWorldMap correct = new RectangularMap(10, 5);
        correct.place(new Animal(correct, new Vector2d(4, 4), MapDirection.SOUTH));
        correct.place(new Animal(correct, new Vector2d(5, 4), MapDirection.NORTH));

        assertEquals(map.toString(), correct.toString());
    }
    @Test
    void test3()
    {
        String[] args = {"f", "l", "l", "l", "f", "f", "b", "r", "f", "f", "f", "l", "b", "f", "f"};
        MoveDirection[] dirs = OptionsParser.parse(args);
        IWorldMap map = new RectangularMap(4, 4);
        Vector2d[] positions = {new Vector2d(0, 0), new Vector2d(1, 1),  new Vector2d(3, 3)};
        IEngine engine = new SimulationEngine(dirs, map, positions);
        engine.run();

        IWorldMap correct = new RectangularMap(4, 4);
        correct.place(new Animal(correct, new Vector2d(1, 1), MapDirection.WEST));
        correct.place(new Animal(correct, new Vector2d(1, 2), MapDirection.NORTH));
        correct.place(new Animal(correct, new Vector2d(1, 3), MapDirection.SOUTH));

        assertEquals(map.toString(), correct.toString());
    }
}
