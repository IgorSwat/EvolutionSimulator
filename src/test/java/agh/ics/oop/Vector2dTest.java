package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
    @Test
    void equalsTest() {
        assertTrue(new Vector2d(1, 1).equals(new Vector2d(1, 1)));
        assertFalse(new Vector2d(17, -13).equals(new Vector2d(-17, -13)));
        assertFalse(new Vector2d(0, 10).equals(new Vector2d(10, 0)));
        assertTrue(new Vector2d(1928, 1928).equals(new Vector2d(1928, 1928)));
    }

    @Test
    void toStringTest() {
        assertEquals(new Vector2d(0, 0).toString(), "(0,0)");
        assertEquals(new Vector2d(-15, -1928).toString(), "(-15,-1928)");
        assertEquals(new Vector2d(10, -2).toString(), "(10,-2)");
    }

    @Test
    void precedesTest() {
        assertFalse(new Vector2d(17, 41).precedes(new Vector2d(17, 36)));
        assertFalse(new Vector2d(10, 16).precedes(new Vector2d(13, 13)));
        assertTrue(new Vector2d(-5, 10).precedes(new Vector2d(3, 12)));
    }

    @Test
    void followsTest() {
        assertTrue(new Vector2d(17, 41).follows(new Vector2d(17, 36)));
        assertFalse(new Vector2d(10, 16).follows(new Vector2d(13, 13)));
        assertTrue(new Vector2d(-5, 10).follows(new Vector2d(-7, 9)));
    }

    @Test
    void upperRightTest() {
        assertEquals(new Vector2d(3, 5).upperRight(new Vector2d(7, 1)), new Vector2d(7, 5));
        assertEquals(new Vector2d(-47, -20).upperRight(new Vector2d(-77, 0)), new Vector2d(-47, 0));
        assertEquals(new Vector2d(0, 0).upperRight(new Vector2d(0, 0)), new Vector2d(0, 0));
    }

    @Test
    void lowerLeftTest() {
        assertEquals(new Vector2d(3, 5).lowerLeft(new Vector2d(7, 1)), new Vector2d(3, 1));
        assertEquals(new Vector2d(-47, -20).lowerLeft(new Vector2d(-77, 0)), new Vector2d(-77, -20));
        assertEquals(new Vector2d(0, 0).lowerLeft(new Vector2d(0, 0)), new Vector2d(0, 0));
    }

    @Test
    void addTest() {
        assertEquals(new Vector2d(3, 7).add(new Vector2d(10, 10)), new Vector2d(13, 17));
        assertEquals(new Vector2d(14, 9).add(new Vector2d(-1, -10)), new Vector2d(13, -1));
        assertEquals(new Vector2d(162, 71).add(new Vector2d(0, 0)), new Vector2d(162, 71));
    }

    @Test
    void subtractTest() {
        assertEquals(new Vector2d(3, 7).subtract(new Vector2d(10, 10)), new Vector2d(-7, -3));
        assertEquals(new Vector2d(14, 9).subtract(new Vector2d(-1, -10)), new Vector2d(15, 19));
        assertEquals(new Vector2d(162, 71).subtract(new Vector2d(0, 0)), new Vector2d(162, 71));
    }

    @Test
    void oppositeTest() {
        assertEquals(new Vector2d(13, 18).opposite(), new Vector2d(-13, -18));
        assertEquals(new Vector2d(-4, 5).opposite(), new Vector2d(4, -5));
        assertEquals(new Vector2d(0, 0).opposite(), new Vector2d(0, 0));
    }
}
