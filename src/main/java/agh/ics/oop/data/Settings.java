package agh.ics.oop.data;

// Kontener na dane przekazane przez użytkownika
public class Settings {
    private final double mapHeight;
    private final double mapWidth;
    private final String mapType;
    private final int grassStarting;
    private final int grassRespawn;
    private final int animalsStarting;
    private final int startingEnergy;
    private final int energyPerGrass;
    private final int reproductionEnergy;
    private final int reproductionCost;
    private final int energyLoss;
    private final int minMutations;
    private final int maxMutations;
    private final String mutationType;
    private final int genomeLength;
    private final String animalsBehavior;
    private final int refreshTime;

    public Settings(double height, double width, String mapType, int grassStarting, int grassRespawn,
                     int animalsStarting, int startingEnergy, int energyPerGrass,
                    int reproductionEnergy, int reproductionCost, int energyLoss, int minMutations, int maxMutations,
                    String mutationType, int genomeLength, String animalsBehavior, int refreshTime) {
        this.mapWidth = width;
        this.mapHeight = height;
        this.mapType = mapType;
        this.grassStarting = grassStarting;
        this.grassRespawn = grassRespawn;
        this.animalsStarting = animalsStarting;
        this.startingEnergy = startingEnergy;
        this.energyPerGrass = energyPerGrass;
        this.reproductionEnergy = reproductionEnergy;
        this.reproductionCost = reproductionCost;
        this.energyLoss = energyLoss;
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;
        this.mutationType = mutationType;
        this.genomeLength = genomeLength;
        this.animalsBehavior = animalsBehavior;
        this.refreshTime = refreshTime;
    }

    public double getMapWidth() {return mapWidth;}
    public double getMapHeight() {return mapHeight;}
    public String getMapType() {return mapType;}
    public int getGrassStarting() {return grassStarting;}
    public int getGrassRespawn() {return grassRespawn;}
    public int getAnimalsStarting() {return animalsStarting;}
    public int getStartingEnergy() {return startingEnergy;}
    public int getEnergyPerGrass() {return energyPerGrass;}
    public int getReproductionEnergy() {return reproductionEnergy;}
    public int getReproductionCost() {return reproductionCost;}
    public int getEnergyLoss() {return energyLoss;}
    public int getMinMutations() {return minMutations;}
    public int getMaxMutations() {return maxMutations;}
    public String getMutationType() {return mutationType;}
    public int getGenomeLength() {return genomeLength;}
    public String getAnimalsBehavior() {return animalsBehavior;}
    public int getRefreshTime() {return refreshTime;}
}
