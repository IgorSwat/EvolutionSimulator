package agh.ics.oop;

public enum MapDirection
{
    NORTH(0, 1),
    EAST(1, 0),
    SOUTH(0, -1),
    WEST(-1, 0);
    private final Vector2d vector;
    MapDirection(int x, int y) {vector = new Vector2d(x, y);}
    public String toString()
    {
        return switch (this) {
            case NORTH -> "Północ";
            case SOUTH -> "Południe";
            case EAST -> "Wschód";
            case WEST -> "Zachód";
        };
    }
    public MapDirection next() {return MapDirection.values()[(ordinal() + 1) % 4];}
    public MapDirection previous() {return MapDirection.values()[(ordinal() + 3) % 4];}
    public Vector2d toUnitVector() {return vector;}
}
