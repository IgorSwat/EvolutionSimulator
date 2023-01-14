package agh.ics.oop.data;

import java.util.*;

// Obiekt statystyczny do dynamicznego obliczania najpopularniejszego genotypu
public class StatLogGenotype implements IStatLog {
    private final HashMap<String, Integer> genotypeWithCount = new HashMap<>();

    public void registerStatChange(Object change) {
        if (!(change instanceof String))
            throw new IllegalArgumentException("Illegal type used: " + change.getClass() + " (required String)");
        String genotype = (String) change;
        if (genotype.length() > 12)
            System.out.println("Error in StatLog");
        int prevValue = 0;
        if (genotypeWithCount.get(genotype) != null) {
            prevValue = genotypeWithCount.get(genotype);
            genotypeWithCount.remove(genotype);
        }
        int nextValue = prevValue + 1;
        genotypeWithCount.put(genotype, nextValue);
    }

    // Zwraca najpopularniejszy genotyp
    public String getStatValue() {
        String result = "-";
        int maxCount = 0;
        Set<Map.Entry<String, Integer>> genotypesMap = genotypeWithCount.entrySet();
        for (Map.Entry<String, Integer> entry : genotypesMap) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                result = entry.getKey();
            }
        }
        return result;
    }

    public void clearParameters() {
        genotypeWithCount.clear();
    }
}
