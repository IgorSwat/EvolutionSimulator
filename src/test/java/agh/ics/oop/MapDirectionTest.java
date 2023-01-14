package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapDirectionTest {
    @Test
    void methodsTest() {
        assertEquals(MapDirection.NORTH.next(), MapDirection.NORTHEAST);
        assertEquals(MapDirection.NORTHWEST.next(), MapDirection.NORTH);
        assertEquals(MapDirection.SOUTHWEST.previous(), MapDirection.SOUTH);
        assertEquals(MapDirection.NORTHWEST.opposite(), MapDirection.SOUTHEAST);
        assertEquals(MapDirection.EAST.opposite(), MapDirection.WEST);
        assertEquals(MapDirection.getDirection(3), MapDirection.SOUTHEAST);
        assertEquals(MapDirection.getDirection(0), MapDirection.NORTH);
        assertEquals(MapDirection.getDirection(6), MapDirection.WEST);
    }
}
