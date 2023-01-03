package agh.ics.oop.elements;
import agh.ics.oop.MapDirection;
import agh.ics.oop.Vector2d;
import agh.ics.oop.elements.genotype.IGenotypeBasic;
import agh.ics.oop.map.IPositionChangeObserver;
import agh.ics.oop.map.IWorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal implements IMapElement
{
    private final IWorldMap map;
    private final IGenotypeBasic genotype;
    // Kontener na 3 parametry zwierzaków, ułatwia implementację metody calculateAnimalState() dla map
    private AnimalParameters parameters;
    private int age = 0;
    private int children = 0;
    private final int maxEnergy;
    // Unikalny numer ID zwierzaka przypisywany w trakcie wykonania symulacji, pozwala rozróżniać 2 zwierzaki o tych samych parametrach
    private final long animalID;
    private final List<IPositionChangeObserver> observers = new ArrayList<>();

    public Animal(IWorldMap map, IGenotypeBasic genotype, int maxEnergy, long animalID) {
        this.animalID = animalID;
        this.map = map;
        this.genotype = genotype;
        this.maxEnergy = maxEnergy;
        Random generator = new Random();
        Vector2d customPosition = new Vector2d(generator.nextInt(this.map.getWidth()), generator.nextInt(this.map.getHeight()));
        this.parameters = new AnimalParameters(customPosition, MapDirection.getDirection(genotype.getGene(0)), maxEnergy);
    }
    public Animal(IWorldMap map, IGenotypeBasic genotype, AnimalParameters parameters, int maxEnergy, long animalID)
    {
        this.animalID = animalID;
        this.map = map;
        this.genotype = genotype;
        this.parameters = parameters;
        this.maxEnergy = maxEnergy;
    }
    public void addObserver(IPositionChangeObserver observer)
    {
        observers.add(observer);
    }
    public boolean removeObserver(IPositionChangeObserver observer)
    {
        return observers.remove(observer);
    }
    private void positionChanged(Vector2d oldPosition)
    {
        for (IPositionChangeObserver observer : observers)
            observer.positionChanged(this, oldPosition);
    }
    public void move() {
        // Obraca zwierzę (wskazuje nową orientację na podstawie aktualnego genu)
        MapDirection newOrientation = MapDirection.getDirection(genotype.proceedNextGene());
        Vector2d oldPosition = getPosition();
        parameters = new AnimalParameters(oldPosition, newOrientation, getEnergy());
        // Mapa oblicza i zwraca nowe parametry zwierzaka po wykonaniu ruchu
        parameters = map.calculateAnimalState(this, newOrientation.toUnitVector());
        map.animalStateChanged(this, oldPosition);
        if (!oldPosition.equals(getPosition()))
            positionChanged(oldPosition);
    }
    public boolean isAt(Vector2d pos) {return parameters.position().equals(pos);}
    public void updateAge() {age += 1; map.animalStateChanged(this, getPosition());}
    public void updateChildren() {children += 1; map.animalStateChanged(this, getPosition());}
    public void applyEnergy(int energyBoost) {
        energyBoost = energyBoost + getEnergy();
        if (energyBoost > maxEnergy)
            energyBoost = maxEnergy;
        parameters = new AnimalParameters(getPosition(), getDirection(), energyBoost);
        map.animalStateChanged(this, getPosition());
    }
    public Vector2d getPosition() {
        return parameters.position();
    }
    public MapDirection getDirection() {
        return parameters.orientation();
    }
    public int getEnergy() {
        return parameters.energy();
    }
    public int getAge() {
        return age;
    }
    public int getChildren() {
        return children;
    }
    // Zwraca współczynnik zdrowia zwierzaka (liczba z zakresu 0-1, do ustalenia koloru graficznej reprezentacji zwierzaka)
    public double getHealthStatus() {return (double) getEnergy() / maxEnergy;}
    public IGenotypeBasic getGenotype() {
        return genotype;
    }
    public long getID() {return animalID;}
}