package agh.ics.oop;

public class Animal implements IMapElement {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;
    private final IInteractiveMap map;  // to jest obejście zakazu modyfikacji interfejsu

    public Animal(IInteractiveMap map) {
        this(map, new Vector2D(2, 2), MapDirection.NORTH);
    }

    public Animal(IInteractiveMap map, Vector2d initialPosition) {
        position = initialPosition;
        this.map = map;
    }

    public Animal(IInteractiveMap map, Vector2d initialPosition, MapDirection initialOrientation) {
        this(map, initialPosition);
        orientation = initialOrientation;
    }

    public String toString() {
        return switch (orientation) {
            case NORTH -> "N";
            case SOUTH -> "S";
            case EAST -> "E";
            case WEST -> "W";
        };
    }

    public boolean isAt(Vector2d pos) {
        return position.equals(pos);
    }

    public Vector2d getPosition() {
        return position;
    }

    public void move(MoveDirection direction) {
        Vector2d new_pos;
        switch (direction) {
            case RIGHT:
                orientation = orientation.next();
                break;
            case LEFT:
                orientation = orientation.previous();
                break;
            case FORWARD:
                new_pos = position.add(orientation.toUnitVector());
                if (map.canMoveTo(new_pos)) {
                    map.registerMove(this, new_pos);
                    position = new_pos;
                }
                break;
            case BACKWARD:
                new_pos = position.subtract(orientation.toUnitVector());
                if (map.canMoveTo(new_pos)) {
                    map.registerMove(this, new_pos);
                    position = new_pos;
                }
                break;
        }
    }
}