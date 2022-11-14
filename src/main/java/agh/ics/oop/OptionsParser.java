package agh.ics.oop;

public class OptionsParser
{
    static MoveDirection[] parse(String[] args)
    {
        MoveDirection[] tmp = new MoveDirection[args.length];
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
                    last_id -= 1;
            }
            last_id += 1;
        }
        MoveDirection[] dirs = new MoveDirection[last_id];
        System.arraycopy(tmp, 0, dirs, 0, last_id);
        return dirs;
    }
}
