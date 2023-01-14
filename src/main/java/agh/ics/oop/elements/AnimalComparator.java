package agh.ics.oop.elements;

import java.util.Comparator;

// Komparator zwierząt wg klucza energia -> wiek -> liczba dzieci -> numer ID (do TreeSet)
public class AnimalComparator implements Comparator<Animal> {
    public int compare(Animal first, Animal second) {
        if (first.getEnergy() != second.getEnergy())
            return first.getEnergy() - second.getEnergy();
        if (first.getAge() != second.getAge())
            return first.getAge() - second.getAge();
        if (first.getChildren() != second.getChildren())
            return first.getChildren() - second.getChildren();
        if (first.getID() > second.getID()) // a czemu tu Pan zrezygnował z odejmowania?
            return 1;
        if (first.getID() < second.getID())
            return -1;
        return 0;
    }
}
