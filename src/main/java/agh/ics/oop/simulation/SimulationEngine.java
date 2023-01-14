package agh.ics.oop.simulation;

import agh.ics.oop.MapDirection;
import agh.ics.oop.data.*;
import agh.ics.oop.elements.Animal;
import agh.ics.oop.elements.AnimalComparator;
import agh.ics.oop.elements.AnimalParameters;
import agh.ics.oop.elements.genotype.*;
import agh.ics.oop.gui.Simulation;
import agh.ics.oop.map.*;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SimulationEngine implements IEngine, Runnable {
    private final Simulation simulationObserver;
    private boolean isRunning = true;
    private final Settings settings;
    private IWorldMap map;
    private final ArrayList<Animal> animals = new ArrayList<>();
    private final int delay;
    private final Random generator = new Random();
    // World parameters
    private int countAnimals = 0;
    // World statistics
    private final HashMap<String, IStatLog> logs = new HashMap<>();

    public SimulationEngine(Simulation simulation, Settings settings) {
        this.settings = settings;
        this.initLogs();
        this.simulationObserver = simulation;
        this.delay = settings.getRefreshTime();
        initMap();
        initAnimals();
    }

    private void initLogs() {
        this.logs.put("Animals", new StatLogSum());
        this.logs.put("Grass", new StatLogSum());
        this.logs.put("Free squares", new StatLogSum((int) settings.getMapHeight() * (int) settings.getMapWidth()));
        this.logs.put("Top genotype", new StatLogGenotype());
        this.logs.put("Average animal energy", new StatLogAverage());
        this.logs.put("Average life length", new StatLogAverage());
    }

    private void initMap() {
        int mapWidth = (int) settings.getMapWidth();
        int mapHeight = (int) settings.getMapHeight();
        IGrassGenerator grassGenerator = new ForestedEquatorsGenerator(mapWidth, mapHeight);
        if (settings.getMapType() == "Globe")
            this.map = new Globe(this, mapWidth, mapHeight, grassGenerator, settings.getGrassStarting(),
                    settings.getEnergyPerGrass(), settings.getGrassRespawn());
        else
            this.map = new HellPortal(this, mapWidth, mapHeight, grassGenerator, settings.getGrassStarting(),
                    settings.getEnergyPerGrass(), settings.getGrassRespawn(), settings.getReproductionCost());
    }

    private void initAnimals() {
        int animalsStarting = settings.getAnimalsStarting();
        int maxEnergy = settings.getStartingEnergy();
        for (int i = 0; i < animalsStarting; i++) {
            IGenotypeBasic genotype;
            if (settings.getAnimalsBehavior().equals("Full predestination"))
                genotype = new GenotypeClassic(settings.getGenomeLength());
            else
                genotype = new GenotypeCrazy(settings.getGenomeLength());
            Animal animal = new Animal(map, genotype, maxEnergy, countAnimals);
            countAnimals += 1;
            if (map.place(animal))
                this.animals.add(animal);
            updateStatLog("Animals", 1);
        }
    }

    private void updateAnimals() {
        ArrayList<Integer> toRemove = new ArrayList<>();
        for (int i = 0; i < animals.size(); i++) {
            Animal animal = animals.get(i);
            animal.applyEnergy(-settings.getEnergyLoss());
            if (animal.getEnergy() <= 0 || animal.getAge() > 300) {
                map.remove(animal);
                toRemove.add(i);
                updateStatLog("Average life length", animal.getAge());
            }
        }
        updateStatLog("Animals", -toRemove.size());
        for (int i = toRemove.size() - 1; i >= 0; i--) {
            animals.remove((int) toRemove.get(i));
        }
        for (Animal animal : animals) {
            animal.move();
        }
    }

    // Funkcja sortuje zwierzaki względem danych w poleceniu parametrów, przez co przejście od końca listy gwarantuje wybór najsilniejszego
    // dostępnego w danym momencie zwierzaka
    private void updateChildren() {
        animals.sort(new AnimalComparator());
        boolean[] used = new boolean[animals.size()];
        int maxRange = animals.size() - 1;
        for (int i = maxRange; i >= 0; i--) {
            int partnerID = -1;
            if (!used[i] && animals.get(i).getEnergy() >= settings.getReproductionEnergy())
                partnerID = findPartnerID(animals.get(i), i);
            if (partnerID != -1) {
                makeChild(animals.get(i), animals.get(partnerID));
                used[i] = true;
                used[partnerID] = true;
            }
        }
    }

    // Funkcja poszukuje najlepszego kandydata na rodzica będącego na tym samym polu co drugi rodzic
    private int findPartnerID(Animal animal, int id) {
        for (int i = id - 1; i >= 0; i--) {
            Animal candidate = animals.get(i);
            if (candidate.getAge() > 0 && candidate.getEnergy() >= settings.getReproductionEnergy()
                    && candidate.getPosition().equals(animal.getPosition()))
                return i;
        }
        return -1;
    }

    private void makeChild(Animal animal1, Animal animal2) {
        IMutationBasic mutation;
        if (settings.getMutationType().equals("Full randomness"))
            mutation = new MutationRandom(settings.getMinMutations(), settings.getMaxMutations());
        else
            mutation = new MutationFixed(settings.getMinMutations(), settings.getMaxMutations());
        boolean side = generator.nextBoolean();
        IGenotypeBasic genotype;
        if (settings.getAnimalsBehavior().equals("Full predestination"))
            genotype = new GenotypeClassic(animal1.getGenotype(), animal2.getGenotype(), animal1.getEnergy(),
                    animal2.getEnergy(), side, mutation);
        else
            genotype = new GenotypeCrazy(animal1.getGenotype(), animal2.getGenotype(), animal1.getEnergy(),
                    animal2.getEnergy(), side, mutation);
        Animal child = new Animal(map, genotype, new AnimalParameters(animal1.getPosition(),
                MapDirection.getDirection(genotype.getGene(0)), 2 * settings.getReproductionCost()),
                settings.getStartingEnergy(), countAnimals);
        countAnimals += 1;
        animal1.applyEnergy(-settings.getReproductionCost());
        animal2.applyEnergy(-settings.getReproductionCost());
        animal1.updateChildren();
        animal2.updateChildren();
        if (map.place(child))
            this.animals.add(child);
        updateStatLog("Animals", 1);
    }

    private void updateSpecialLogs() {
        logs.get("Average animal energy").clearParameters();
        logs.get("Top genotype").clearParameters();
        for (Animal animal : animals) {
            if (animal.getEnergy() > 0 && animal.getAge() <= 300) {
                updateStatLog("Average animal energy", animal.getEnergy());
                updateStatLog("Top genotype", animal.getGenotype().toString());
            }
        }
    }

    public IWorldMap getUsedMap() {
        return map;
    }

    public HashMap<String, IStatLog> getStats() {
        return logs; // dehermetyzacja
    }

    public void switchOff() {
        this.isRunning = false;
    }

    public void switchOn() {
        this.isRunning = true;
    }

    public void updateStatLog(String statName, Object change) {
        IStatLog log = logs.get(statName);
        if (log == null)
            throw new IllegalArgumentException(statName + " is not a valid statistics name");
        log.registerStatChange(change);
    }

    public void run() {
        while (isRunning && animals.size() > 0) {
            try {
                Thread.sleep(delay);
                for (Animal animal : animals)
                    animal.updateAge();
                updateAnimals();
                map.consumeGrass();
                updateChildren();
                map.respawnGrass();
                updateSpecialLogs();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        simulationObserver.loadStatistics();
                    }
                });
            } catch (InterruptedException exception) {
                System.out.println(exception.getMessage());
            }
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                simulationObserver.refreshScene();
            }
        });
    }
}
