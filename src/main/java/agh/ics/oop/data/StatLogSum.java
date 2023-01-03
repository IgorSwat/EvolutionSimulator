package agh.ics.oop.data;

// Obiekt statystyczny dynamicznie obliczający liczebność (sumę) danych elementów
public class StatLogSum implements IStatLog {
    protected int sum;

    public StatLogSum() {this.sum = 0;}
    public StatLogSum(int initialSum) {
        this.sum = initialSum;
    }

    public void registerStatChange(Object change) {
        if (!(change instanceof Integer))
            throw new IllegalArgumentException("Illegal type used: " + change.getClass() + " (required Integer)");
        Integer value = (Integer) change;
        sum += value;
    }
    public String getStatValue() {
        return Integer.toString(sum);
    }
    public void clearParameters() {sum = 0;}
}
