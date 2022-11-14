package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static  org.junit.jupiter.api.Assertions.*;

public class AnimalTest
{
    @Test
    void movingTest()
    {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(map);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.RIGHT);
        assertTrue(animal.isAt(new Vector2d(2, 4)));
        animal.move(MoveDirection.LEFT);
        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.BACKWARD);
        assertTrue(animal.isAt(new Vector2d(1, 3)));
        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.BACKWARD);
        assertTrue(animal.isAt(new Vector2d(0, 4)));
    }
    @Test
    void parsingTest()
    {
        String[] ex1 = {"f", "r", "l", "b"};
        MoveDirection[] ans1 = {MoveDirection.FORWARD, MoveDirection.RIGHT, MoveDirection.LEFT, MoveDirection.BACKWARD};
        MoveDirection[] res1 = OptionsParser.parse(ex1);
        String[] ex2 = {"right", "backward", "left", "forward"};
        MoveDirection[] ans2 = {MoveDirection.RIGHT, MoveDirection.BACKWARD, MoveDirection.LEFT, MoveDirection.FORWARD};
        MoveDirection[] res2 = OptionsParser.parse(ex2);
        String[] ex3 = {"f", "lala", "none", "backward", "g", "r", "left", "w", "p123", "9182"};
        MoveDirection[] ans3 = {MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.RIGHT, MoveDirection.LEFT};
        MoveDirection[] res3 = OptionsParser.parse(ex3);
        for (int i = 0; i < 4; i++)
        {
            assertEquals(res1[i], ans1[i]);
            assertEquals(res2[i], ans2[i]);
            assertTrue(res3.length == ans3.length);
            assertEquals(res3[i], ans3[i]);
        }
    }
}
