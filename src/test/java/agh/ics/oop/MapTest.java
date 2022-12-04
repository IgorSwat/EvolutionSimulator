package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static  org.junit.jupiter.api.Assertions.*;

public class MapTest
{
    // RectangularMap tests
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
    // ------------------------------
    // GrassField tests
    @Test
    void test4()
    {
        String[] args = {"f", "b", "r", "f", "l", "b", "b", "l"};
        MoveDirection[] dirs = OptionsParser.parse(args);
        IWorldMap map = new GrassField(6);
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(4, 4)};
        IEngine engine = new SimulationEngine(dirs, map, positions);
        engine.run();

        assertFalse(map.canMoveTo(new Vector2d(2,2)));
        assertFalse(map.canMoveTo(new Vector2d(4,3)));
        assertTrue(map.canMoveTo(new Vector2d(4,2)));
        assertTrue(map.canMoveTo(new Vector2d(10,10)));
        try {
            map.place(new Animal(map, new Vector2d(4, 3)));
        }
        catch (Exception exception) {
            assertEquals(exception.getMessage(), "(4,3) is not a legal square for animal");
        }
        assertTrue(map.place(new Animal(map, new Vector2d(16, 11))));
        assertFalse(map.isOccupied(new Vector2d(9, 9)));
    }
    @Test
    void test5()
    {
        String[] args = {"r", "b", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f"};
        MoveDirection[] dirs = OptionsParser.parse(args);
        IWorldMap map = new GrassField(3);
        Vector2d[] positions = {new Vector2d(1, 6), new Vector2d(4, 4)};
        IEngine engine = new SimulationEngine(dirs, map, positions);
        engine.run();

        assertFalse(map.canMoveTo(new Vector2d(6,6)));
        assertFalse(map.canMoveTo(new Vector2d(4,7)));
        assertTrue(map.canMoveTo(new Vector2d(4,2)));
        assertTrue(map.canMoveTo(new Vector2d(100,100)));
        try {
            map.place(new Animal(map, new Vector2d(6, 6)));
        }
        catch (Exception exception) {
            assertEquals(exception.getMessage(), "(6,6) is not a legal square for animal");
        }
        assertTrue(map.place(new Animal(map, new Vector2d(0, 0))));
        assertFalse(map.isOccupied(new Vector2d(-3, -5)));
    }
    @Test
    void test6()    // Test dynamicznej detekcji rogów GrassField
    {
        String[] args1 = {"r", "r", "f", "r"};
        String[] args2 = {"b", "f", "b", "f"};
        MoveDirection[] dirs1 = OptionsParser.parse(args1);
        MoveDirection[] dirs2 = OptionsParser.parse(args2);
        AbstractWorldMap map = new GrassField(3);   // Do celu testu użyte zostaje AbstractWorldMap
        Vector2d[] positions = {new Vector2d(-2, -3), new Vector2d(8, 10)};
        SimulationEngine engine = new SimulationEngine(dirs1, map, positions);
        engine.run();

        assertEquals(map.getLeftCorner(), new Vector2d(-1, -3));
        assertEquals(map.getRightCorner(), new Vector2d(8, 10));

        engine.changeMovementSet(dirs2);
        engine.run();

        assertEquals(map.getLeftCorner(), new Vector2d(-3, -3));
        assertEquals(map.getRightCorner(), new Vector2d(8, 8));
    }
}
