package agh.ics.oop.elements.genotype;

public class MutationFixed extends AbstractMutationBasic {
    public MutationFixed(int minMutations, int maxMutations) {
        super(minMutations, maxMutations);
    }
    public void applyChange(IGenotypeBasic genotype, int geneID) {
        boolean choice = generator.nextBoolean();
        int genotypeLength = genotype.getGenotypeLength();
        if (choice)
            genotype.setGene(geneID, (genotype.getGene(geneID) + 1) % 8);
        else
            genotype.setGene(geneID, (genotype.getGene(geneID) + 7) % 8);
    }
}