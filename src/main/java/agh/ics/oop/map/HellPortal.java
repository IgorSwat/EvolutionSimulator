package agh.ics.oop.map;

import agh.ics.oop.Vector2d;
import agh.ics.oop.elements.Animal;
import agh.ics.oop.elements.AnimalParameters;
import agh.ics.oop.gui.GridHandler;
import agh.ics.oop.simulation.SimulationEngine;

import java.util.Random;

public class HellPortal extends RectangularMap {
    private final int energyReduction;

    public HellPortal(SimulationEngine engineObserver, int width, int height, IGrassGenerator generator,
                      int startingGrass, int grassEnergy, int grassRespawn, int energyReduction) {
        super(engineObserver, width, height, generator, startingGrass, grassEnergy, grassRespawn);
        this.energyReduction = energyReduction;
    }

    public AnimalParameters calculateAnimalState(Animal animal, Vector2d offset) {
        Vector2d positionTranslated = animal.getPosition().add(offset);
        if (positionTranslated.x < 0 || positionTranslated.x >= getWidth() || positionTranslated.y < 0 || positionTranslated.y >= getHeight()) {
            Random generator = new Random();
            positionTranslated = new Vector2d(generator.nextInt(getWidth()), generator.nextInt(getHeight()));
            return new AnimalParameters(positionTranslated, animal.getDirection(), animal.getEnergy() - energyReduction);
        }
        return new AnimalParameters(positionTranslated, animal.getDirection(), animal.getEnergy());
    }
}
