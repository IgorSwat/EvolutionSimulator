package agh.ics.oop.map;

import agh.ics.oop.Vector2d;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

// Gnerator "Zalesione równiki"
public class ForestedEquatorsGenerator implements IGrassGenerator {
    private final Random generator = new Random();
    private final int mapWidth;
    private final int mapHeight;
    private final int preferredRangeMin;
    private final int preferredRangeMax;
    private final TreeMap<Integer, Vector2d> freePreferredSquares = new TreeMap<>();
    private final TreeMap<Integer, Vector2d> freeOtherSquares = new TreeMap<>();

    public ForestedEquatorsGenerator(int mapWidth, int mapHeight) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        int centerID = mapWidth * mapHeight / 2;
        int range = mapWidth * mapHeight / 10;
        this.preferredRangeMin = centerID - range;
        this.preferredRangeMax = centerID + range;
        for (int i = 0; i < mapHeight * mapWidth; i++) {
            Vector2d currentSquare = new Vector2d(i % mapWidth, i / mapWidth);
            if (i >= centerID - range && i <= centerID + range)
                freePreferredSquares.put(currentSquare.getSquareID(mapWidth), currentSquare);
            else
                freeOtherSquares.put(currentSquare.getSquareID(mapWidth), currentSquare);
        }
    }

    // Generuje wolne pole na mapie wg ustalonego zbioru pól (preferowane lub inne)
    // Generacja odbywa się z wykorzystaniem zbalansowanego drzewa w postaci TreeMap,
    // wylosowywane jest dowolne pole mapy a następnie dobierane w czasie O(log nm) wolne pole danej kategorii
    // przy użyciu lowerEntry i upperEntry (w przypadku gdy wylosowane pole jest już wykorzystywane)
    // Generacja ma pewną wadę - wyraźnie bardziej podoba jej się dolna część mapy, więc nie jest to do końca generacja losowa,
    // ale brakuje mi już czasu żeby to rozwinąć ciekawszym algorytmem / poprawić obecny.
    private Vector2d generateSquare(TreeMap<Integer, Vector2d> container, int lowerBound, int higherBound) {
        int generatedID = generator.nextInt(lowerBound, higherBound + 1);
        Vector2d result = container.get(generatedID);
        if (result != null)
            return result;
        Map.Entry<Integer, Vector2d> found = container.lowerEntry(generatedID);
        if (found != null) {
            result = found.getValue();
            return result;
        }
        found = container.higherEntry(generatedID);
        if (found != null) {
            result = found.getValue();
            return result;
        }
        return null;
    }

    public Vector2d generateGrassPosition() {
        int choice = generator.nextInt(100);
        if (choice < 80 && freePreferredSquares.size() > 0)
            return generateSquare(freePreferredSquares, preferredRangeMin, preferredRangeMax);
        else
            return generateSquare(freeOtherSquares, 0, mapHeight * mapWidth - 1);
    }

    public void changeSquareState(Vector2d position) {
        int id = position.getSquareID(mapWidth);
        if (freePreferredSquares.get(id) != null)
            freePreferredSquares.remove(id);
        else if (freeOtherSquares.get(id) != null)
            freeOtherSquares.remove(id);
        else {
            if (id >= preferredRangeMin && id <= preferredRangeMax)
                freePreferredSquares.put(id, position);
            else
                freeOtherSquares.put(id, position);
        }
    }
}
