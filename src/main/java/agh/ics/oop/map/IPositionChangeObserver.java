package agh.ics.oop.map;

import agh.ics.oop.elements.Animal;
import agh.ics.oop.Vector2d;
import agh.ics.oop.elements.IMapElement;

public interface IPositionChangeObserver {
    void positionChanged(IMapElement element, Vector2d oldPosition);
}
