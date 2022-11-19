package agh.ics.oop;
import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine
{
    private IWorldMap map;
    private List<Animal> animals;
    private MoveDirection[] moves;
    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] starting_positions)
    {
        this.map = map;
        this.moves = moves;
        animals = new ArrayList<>();
        for (Vector2d x : starting_positions)
        {
            Animal current = new Animal(this.map, x);
            if (this.map.place(current))
            {
                current.addObserver((IPositionChangeObserver) map);
                animals.add(current);
            }
        }
    }
    public void changeMovementSet(MoveDirection[] moves)
    {
        this.moves = moves;
    }
    public void run()
    {
        int count_animals = animals.size();
        for (int i = 0; i < moves.length; i++)
            animals.get(i % count_animals).move(moves[i]);
    }
}
