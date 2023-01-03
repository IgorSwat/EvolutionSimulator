package agh.ics.oop.elements.genotype;

public class MutationRandom extends AbstractMutationBasic {
    public MutationRandom(int minMutations, int maxMutations) {
        super(minMutations, maxMutations);
    }
    public void applyChange(IGenotypeBasic genotype, int geneID) {
        int value = generator.nextInt(8);
        int oldValue = genotype.getGene(geneID);
        while (value == oldValue)
            value = generator.nextInt(8);
        genotype.setGene(geneID, value);
    }
}
