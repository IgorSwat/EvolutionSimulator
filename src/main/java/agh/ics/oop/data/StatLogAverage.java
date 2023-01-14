package agh.ics.oop.data;

// Obiekt statystyczny do dynamicznego liczenia średniej
public class StatLogAverage extends StatLogSum {
    private int quantity = 0;

    public StatLogAverage() {
        super();
    }

    public StatLogAverage(int initialSum) {
        super(initialSum);
    }

    public void registerStatChange(Object change) {
        super.registerStatChange(change);
        Integer value = (Integer) change;
        if (value <= 0) {
            if (value == -sum)
                quantity = 0;
            else
                quantity -= 1;
        } else
            quantity += 1;
    }

    public String getStatValue() {
        if (quantity > 0)
            return Integer.toString(sum / quantity);
        else
            return "0";
    }

    public void clearParameters() {
        super.clearParameters();
        quantity = 0;
    }
}
