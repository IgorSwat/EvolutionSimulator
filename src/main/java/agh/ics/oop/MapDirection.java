package agh.ics.oop;

public enum MapDirection
{
    NORTH(0, 1),
    NORTHEAST(1, 1),
    EAST(1, 0),
    SOUTHEAST(1, -1),
    SOUTH(0, -1),
    SOUTHWEST(-1, -1),
    WEST(-1, 0),
    NORTHWEST(-1, 1);
    private final Vector2d vector;
    MapDirection(int x, int y) {vector = new Vector2d(x, y);}
    public String toString()
    {
        return switch (this) {
            case NORTH -> "Północ";
            case NORTHEAST -> "Północny wschód";
            case SOUTH -> "Południe";
            case SOUTHEAST -> "Południowy wschód";
            case EAST -> "Wschód";
            case SOUTHWEST -> "Południowy zachód";
            case WEST -> "Zachód";
            case NORTHWEST -> "Północny zachód";
        };
    }
    public MapDirection next() {return MapDirection.values()[(ordinal() + 1) % 8];}
    public MapDirection previous() {return MapDirection.values()[(ordinal() + 7) % 8];}
    public MapDirection opposite() {
        return MapDirection.values()[(ordinal() + 4) % 8];
    }
    public Vector2d toUnitVector() {return vector;}
    public static MapDirection getDirection(int dirNumber) {
        return MapDirection.values()[dirNumber % 8];
    }
}
