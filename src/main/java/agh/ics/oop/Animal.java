package agh.ics.oop;

import java.util.Arrays;

public class Animal
{
    private MapDirection orientation;
    private Vector2d position;
    Animal() {orientation = MapDirection.NORTH; position = new Vector2d(2, 2);}
    public String toString() {return position.toString() + " " + orientation.toString();}
    public boolean isAt(Vector2d pos) {return position.equals(pos);}
    public void move(MoveDirection direction)
    {
        Vector2d new_pos;
        switch (direction)
        {
            case RIGHT:
                orientation = orientation.next();
                break;
            case LEFT:
                orientation = orientation.previous();
                break;
            case FORWARD:
                new_pos = position.add(orientation.toUnitVector());
                if (new_pos.precedes(new Vector2d(4, 4)))
                    position = new_pos;
                break;
            case BACKWARD:
                new_pos = position.subtract(orientation.toUnitVector());
                if (new_pos.follows(new Vector2d(0, 0)))
                    position = new_pos;
                break;
        }
    }
}

class OptionsParser
{
    // opcjonalnie ArrayList
    static MoveDirection[] parse(String[] args)
    {
        MoveDirection[] tmp = new MoveDirection[args.length];
        int correct = args.length;
        int last_id = 0;
        for (String str : args)
        {
            switch (str)
            {
                case "f":
                case "forward":
                    tmp[last_id] = MoveDirection.FORWARD;
                    break;
                case "b":
                case "backward":
                    tmp[last_id] = MoveDirection.BACKWARD;
                    break;
                case "r":
                case "right":
                    tmp[last_id] = MoveDirection.RIGHT;
                    break;
                case "l":
                case "left":
                    tmp[last_id] = MoveDirection.LEFT;
                    break;
                default:
                    correct -= 1;
                    last_id -= 1;
            }
            last_id += 1;
        }
        MoveDirection[] dirs = new MoveDirection[correct];
        System.arraycopy(tmp, 0, dirs, 0, correct);
        return dirs;
    }
}