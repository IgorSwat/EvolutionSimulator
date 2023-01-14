package agh.ics.oop.map;

import agh.ics.oop.elements.Animal;
import agh.ics.oop.Vector2d;
import agh.ics.oop.elements.AnimalParameters;
import agh.ics.oop.elements.IMapElement;
import agh.ics.oop.gui.GridHandler;

import java.util.TreeSet;

public interface IWorldMap {
    boolean place(Animal animal);

    boolean remove(Animal animal);

    boolean isOccupied(Vector2d position);

    TreeSet<Animal> getAnimals(Vector2d position);

    Object objectAt(Vector2d position);

    AnimalParameters calculateAnimalState(Animal animal, Vector2d offset);

    void animalStateChanged(Animal animal, Vector2d position);

    void respawnGrass();

    void consumeGrass();

    int getWidth();

    int getHeight();

    void setVisualiser(GridHandler grid);
}