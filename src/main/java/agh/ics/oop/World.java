package agh.ics.oop;
import static java.lang.System.out;

public class World
{
    public static void main(String[] args)
    {
        MoveDirection[] directions = OptionsParser.parse(args);
        IInteractiveMap map = new GrassField(10);
        Vector2d[] positions = { new Vector2d(1,1), new Vector2d(14,2) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        out.println(map);
    }
}
