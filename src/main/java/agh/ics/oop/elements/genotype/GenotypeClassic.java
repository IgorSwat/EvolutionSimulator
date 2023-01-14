package agh.ics.oop.elements.genotype;

import java.util.Random;

public class GenotypeClassic implements IGenotypeBasic {
    protected final int[] genes;
    protected int currentGene = 0;
    protected final Random generator = new Random();

    public GenotypeClassic(int length) {
        genes = new int[length];
        for (int i = 0; i < length; i++)
            genes[i] = generator.nextInt(8);
    }

    public GenotypeClassic(int[] genes) {
        int length = genes.length;
        this.genes = new int[length];
        for (int i = 0; i < length; i++)
            this.genes[i] = genes[i];
    }

    // Implementacja mechanizmu "dziedziczenia" genotypu przez dzieci
    public GenotypeClassic(IGenotypeBasic dominant, IGenotypeBasic other, int dominantValue, int otherValue,
                           boolean side, IMutationBasic mutation) {
        // side == false - lewa strona osobnika silniejszego
        // side == true - prawa strona osobnika silniejszego
        int length = dominant.getGenotypeLength();
        genes = new int[length];
        if (!side) {
            int crossPoint = (int) Math.ceil(((double) dominantValue) * length / (dominantValue + otherValue));
            for (int i = 0; i < crossPoint; i++)
                genes[i] = dominant.getGene(i);
            for (int i = crossPoint; i < length; i++)
                genes[i] = other.getGene(i);
        } else {
            int crossPoint = (int) Math.floor(((double) otherValue) * length / (dominantValue + otherValue));
            for (int i = 0; i < crossPoint; i++)
                genes[i] = other.getGene(i);
            for (int i = crossPoint; i < length; i++)
                genes[i] = dominant.getGene(i);
        }
        mutation.applyMutation(this);
    }

    void setNextGene() { // słowo set jest mylące tutaj
        currentGene = (currentGene + 1) % genes.length;
    }

    public void setGene(int geneID, int value) { // czy upublicznienie tej metody jest najlepszym rozwiązaniem?
        genes[geneID] = value;
    }

    public int getGene(int geneID) {
        return genes[geneID];
    }

    public int getGenotypeLength() {
        return genes.length;
    }

    public int getCurrentGene() {
        return getGene(currentGene);
    }

    public int proceedNextGene() {
        int gene = getCurrentGene();
        setNextGene();
        return gene;
    }

    public String toString() {
        String genotypeRepresentation = "";
        for (int i = 0; i < getGenotypeLength(); i++)
            genotypeRepresentation += Integer.toString(getGene(i));
        return genotypeRepresentation;
    }
}
