package agh.ics.oop;
import static java.lang.System.out;

public class World
{
    static Animal zwierzak = new Animal();
    static final Vector2d left_corner = new Vector2d(0, 0);
    static final Vector2d right_corner = new Vector2d(4, 4);
    public static void main(String[] args)
    {
        run(OptionsParser.parse(args));
        out.println(zwierzak);
    }
    static void run(MoveDirection[] dirs)
    {
        for (MoveDirection dir : dirs)
            zwierzak.move(dir);
    }
}
