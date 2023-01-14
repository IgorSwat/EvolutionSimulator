package agh.ics.oop.elements.genotype;

public class GenotypeCrazy extends GenotypeClassic { // nie lepiej, żeby te dwie klasy miały wspólnego przodka?

    public GenotypeCrazy(int n) {
        super(n);
    }

    public GenotypeCrazy(int[] genes) {
        super(genes);
    }

    public GenotypeCrazy(IGenotypeBasic dominant, IGenotypeBasic other, int dominantValue, int otherValue,
                         boolean side, IMutationBasic mutation) {
        super(dominant, other, dominantValue, otherValue, side, mutation);
    }

    void setNextGene() {
        int choice = generator.nextInt(100);
        if (choice < 80)
            super.setNextGene();
        else
            currentGene = generator.nextInt(getGenotypeLength());
    }
}
