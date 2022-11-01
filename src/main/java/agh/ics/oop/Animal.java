package agh.ics.oop;

public class Animal
{
    private static final IWorldMap default_map = new RectangularMap(5, 5);
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;
    private final IWorldMap map;
    public Animal()
    {
        position = new Vector2d(2, 2);
        map = default_map;
    }
    public Animal(IWorldMap map)
    {
        position = new Vector2d(2, 2);
        this.map = map;
    }
    public Animal(IWorldMap map, Vector2d initialPosition)
    {
        position = initialPosition;
        this.map = map;
    }
    public Animal(IWorldMap map, Vector2d initialPosition, MapDirection initialOrientation)
    {
        this(map, initialPosition);
        orientation = initialOrientation;
    }
    public String toString()
    {
        return switch (orientation) {
            case NORTH -> "N";
            case SOUTH -> "S";
            case EAST -> "E";
            case WEST -> "W";
        };
    }
    public boolean isAt(Vector2d pos) {return position.equals(pos);}
    public Vector2d getPosition() {return position;}
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
                if (map.canMoveTo(new_pos))
                    position = new_pos;
                break;
            case BACKWARD:
                new_pos = position.subtract(orientation.toUnitVector());
                if (map.canMoveTo(new_pos))
                    position = new_pos;
                break;
        }
    }
}

class OptionsParser
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