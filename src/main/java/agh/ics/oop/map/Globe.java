package agh.ics.oop.map;

import agh.ics.oop.Vector2d;
import agh.ics.oop.elements.Animal;
import agh.ics.oop.elements.AnimalParameters;
import agh.ics.oop.simulation.SimulationEngine;

public class Globe extends RectangularMap {
    public Globe(SimulationEngine engineObserver, int width, int height, IGrassGenerator generator,
                 int startingGrass, int grassEnergy, int grassRespawn) {
        super(engineObserver, width, height, generator, startingGrass, grassEnergy, grassRespawn);
    }

    public AnimalParameters calculateAnimalState(Animal animal, Vector2d offset) {
        Vector2d positionTranslated = animal.getPosition().add(offset);
        if (positionTranslated.y >= getHeight() || positionTranslated.y < 0)
            return new AnimalParameters(animal.getPosition(), animal.getDirection().opposite(), animal.getEnergy());
        if (positionTranslated.x < 0)
            positionTranslated = new Vector2d(getWidth() - 1, positionTranslated.y);
        else if (positionTranslated.x >= getWidth())
            positionTranslated = new Vector2d(0, positionTranslated.y);
        return new AnimalParameters(positionTranslated, animal.getDirection(), animal.getEnergy());
    }
}

