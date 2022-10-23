package agh.ics.oop;
import static java.lang.System.out;

public class World
{
    static Animal zwierzak = new Animal();
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
