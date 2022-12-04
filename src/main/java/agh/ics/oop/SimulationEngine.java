package agh.ics.oop;
import agh.ics.oop.gui.App;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine, Runnable
{
    private final IWorldMap map;
    private final List<Animal> animals;
    private MoveDirection[] moves;
    private App observer;
    private final int moveDelay = 500;
    public SimulationEngine(IWorldMap map, Vector2d[] starting_positions)
    {
        this.map = map;
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
    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] starting_positions)
    {
        this(map, starting_positions);
        this.moves = moves;
    }
    public void setObserver(App obs) {this.observer = obs;}
    public void changeMovementSet(MoveDirection[] moves)
    {
        this.moves = moves;
    }
    public void run()
    {
        int count_animals = animals.size();
        for (int i = 0; i < moves.length; i++)
        {
            try {
                Thread.sleep(moveDelay);
            }
            catch (InterruptedException exception)
            {
                System.out.println("Move delay interrupted.\n" + exception.getMessage());
            }
            if (animals.get(i % count_animals).move(moves[i]) && observer != null)
            {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        observer.reloadGrid();
                    }
                });
            }
        }
    }
}
