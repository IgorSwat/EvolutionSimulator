package agh.ics.oop.elements;

import agh.ics.oop.MapDirection;
import agh.ics.oop.Vector2d;

public record AnimalParameters(
        Vector2d position,
        MapDirection orientation,
        int energy
) {}
