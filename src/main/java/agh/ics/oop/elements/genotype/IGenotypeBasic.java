package agh.ics.oop.elements.genotype;

public interface IGenotypeBasic {
    void setGene(int geneID, int value);

    int getGene(int geneID);

    int proceedNextGene();

    int getCurrentGene();

    int getGenotypeLength();
}
