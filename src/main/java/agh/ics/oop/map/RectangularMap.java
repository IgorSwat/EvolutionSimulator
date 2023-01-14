package agh.ics.oop.map;

import agh.ics.oop.elements.*;
import agh.ics.oop.Vector2d;
import agh.ics.oop.gui.GridHandler;
import agh.ics.oop.simulation.SimulationEngine;
import javafx.application.Platform;

import java.util.HashMap;
import java.util.TreeSet;

// Abstrakcyjna klasa mapy. Warianty mapy różnią się jedynie sposobem obliczania nowych parametrów dla poruszających się zwierząt
// Zwierzęta przechowywane są w TreeSetach, które to z kolei przypisane są do pól poprzez HashMapę. Gwarantuje to logarytmiczny czas
// wyłaniania najsilniejszego zwierzaka na danym polu, jak i wydajne usuwanie / dodawanie zwierząt.
// Mam nadzieję że nazwy metod są wystarczająco samo-opisujące.
public abstract class RectangularMap implements IPositionChangeObserver, IWorldMap {
    private final SimulationEngine engineObserver;
    private GridHandler grid;
    private final Vector2d bottomLeft = new Vector2d(0, 0);
    private final Vector2d topRight;
    private final HashMap<Vector2d, TreeSet<Animal>> animals = new HashMap<>();
    private final AnimalComparator comparator = new AnimalComparator();
    private final HashMap<Vector2d, Grass> grass = new HashMap<>();
    private final IGrassGenerator grassGenerator;
    private final int grassEnergy;
    private final int grassRespawn;

    RectangularMap(SimulationEngine engineObserver, int width, int height,
                   IGrassGenerator generator, int startingGrass, int grassEnergy, int grassRespawn) {
        this.engineObserver = engineObserver;
        topRight = new Vector2d(width - 1, height - 1);
        this.grassGenerator = generator;
        this.grassRespawn = grassRespawn;
        this.grassEnergy = grassEnergy;
        generateGrass(startingGrass);
    }

    private void updateVisualisation(IMapElement element, Vector2d oldPosition) { // niebezpiecznie wchodzi w obszar GUI
        if (grid != null) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    grid.positionChanged(element, oldPosition);
                }
            });
        }
    }

    private void generateGrass(int grassMissing) {
        int range = grassMissing;
        if (grass.size() + grassMissing > 8 * getWidth() * getHeight() / 10)
            range = Math.min(8 * getWidth() * getHeight() / 10 - grassMissing, 0);
        for (int i = 0; i < range; i++) {
            Vector2d grassPosition = grassGenerator.generateGrassPosition();
            grassGenerator.changeSquareState(grassPosition);
            Grass newGrass = new Grass(grassPosition);
            grass.put(grassPosition, newGrass);
            updateVisualisation(newGrass, null);
            if (animals.get(grassPosition) == null || animals.get(grassPosition).size() == 0)
                engineObserver.updateStatLog("Free squares", -1);
        }
        engineObserver.updateStatLog("Grass", range);
    }

    public void setVisualiser(GridHandler grid) {
        this.grid = grid;
    }

    public boolean place(Animal animal) {
        Vector2d position = animal.getPosition();
        if (position.follows(bottomLeft) && position.precedes(topRight)) {
            if (animals.get(position) == null) {
                TreeSet<Animal> animalSet = new TreeSet<>(comparator);
                animals.put(position, animalSet);
                animalSet.add(animal);
                engineObserver.updateStatLog("Free squares", -1);
            } else {
                if (animals.get(position).size() == 0)
                    engineObserver.updateStatLog("Free squares", -1);
                animals.get(position).add(animal);
            }
            animal.addObserver(this);
            engineObserver.updateStatLog("Average animal energy", animal.getEnergy());
        } else
            throw new IllegalArgumentException(position.toString() + " position does not belongs to the map");
        return true;
    }

    public boolean remove(Animal animal) {
        TreeSet<Animal> container = animals.get(animal.getPosition());
        if (container == null || !container.contains(animal)) {
            return false;
        }
        container.remove(animal);
        if (container.size() == 0 && grass.get(animal.getPosition()) == null)
            engineObserver.updateStatLog("Free squares", 1);
        updateVisualisation(null, animal.getPosition());
        return true;
    }

    public boolean isOccupied(Vector2d position) {
        TreeSet<Animal> selectedAnimals = getAnimals(position);
        if (selectedAnimals == null || selectedAnimals.size() == 0)
            return grass.get(position) != null;
        return true;
    }

    public TreeSet<Animal> getAnimals(Vector2d position) {
        return animals.get(position);  // dehermetyzacja
    }

    public Object objectAt(Vector2d position) {
        TreeSet<Animal> animalsAt = getAnimals(position);
        if (animalsAt != null && animalsAt.size() > 0)
            return animalsAt.last();
        return grass.get(position);
    }

    public AnimalParameters calculateAnimalState(Animal animal, Vector2d offset) {
        return new AnimalParameters(animal.getPosition().add(offset), animal.getDirection(), animal.getEnergy());
    }

    public void respawnGrass() {
        generateGrass(grassRespawn);
    }

    public void consumeGrass() {
        for (Vector2d position : animals.keySet()) {
            TreeSet<Animal> animalsContainer = animals.get(position);
            if (animalsContainer != null && animalsContainer.size() > 0 && grass.get(position) != null) {
                Animal strongestAnimal = animalsContainer.last();
                strongestAnimal.applyEnergy(grassEnergy);
                grass.remove(position);
                grassGenerator.changeSquareState(position);
                animalStateChanged(strongestAnimal, strongestAnimal.getPosition());
                engineObserver.updateStatLog("Grass", -1);
            }
        }
    }

    public void positionChanged(IMapElement element, Vector2d oldPosition) {
        Animal animal = (Animal) element;
        animals.get(oldPosition).remove(animal);
        Vector2d currentPosition = element.getPosition();
        animals.computeIfAbsent(currentPosition, k -> new TreeSet<>(comparator));
        animals.get(currentPosition).add(animal);
        updateVisualisation(element, oldPosition);
        if (grass.get(oldPosition) == null && animals.get(oldPosition).size() == 0
                && (animals.get(currentPosition).size() > 1 || grass.get(currentPosition) != null))
            engineObserver.updateStatLog("Free squares", 1);
        else if ((grass.get(oldPosition) != null || animals.get(oldPosition).size() > 0)
                && animals.get(currentPosition).size() == 1 && grass.get(currentPosition) == null)
            engineObserver.updateStatLog("Free squares", -1);
    }

    public void animalStateChanged(Animal animal, Vector2d position) {
        animals.get(position).remove(animal);
        animals.get(position).add(animal);
    }

    public int getWidth() {
        return topRight.x + 1;
    }

    public int getHeight() {
        return topRight.y + 1;
    }
}