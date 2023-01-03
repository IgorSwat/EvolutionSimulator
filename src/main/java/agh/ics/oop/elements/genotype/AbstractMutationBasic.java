package agh.ics.oop.elements.genotype;

import java.util.ArrayList;
import java.util.Random;

public abstract class AbstractMutationBasic implements IMutationBasic {
    protected final Random generator = new Random();
    protected int minMutations;
    protected int maxMutations;

    AbstractMutationBasic(int minMutations, int maxMutations) {
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;
    }

    // Zwraca losową kombinację indeksów genotypu o długości pomiędzy minMutations i maxMutations
    private int[] generatePermutation(int genotypeLength) {
        int genesToChange = generator.nextInt(minMutations, maxMutations + 1);
        if (genesToChange > genotypeLength)
            genesToChange = genotypeLength;
        ArrayList<Integer> availableIDs = new ArrayList<>();
        for (int i = 0; i < genotypeLength; i++)
            availableIDs.add(i);
        java.util.Collections.shuffle(availableIDs);
        int[] generatedIDs = new int[genesToChange];
        for (int i = 0; i < genesToChange; i++)
            generatedIDs[i] = availableIDs.get(i);
        return generatedIDs;
    }
    // Aplikuje zmianę pojedynczego genu (w zależności od implementacji, mniej lub bardziej losową)
    public abstract void applyChange(IGenotypeBasic genotype, int geneID);
    // Aplikuje mutację do genotypu
    public void applyMutation(IGenotypeBasic genotype) {
        int[] genesToChanged = generatePermutation(genotype.getGenotypeLength());
        for (int geneID : genesToChanged)
            applyChange(genotype, geneID);
    }
}
