package agh.ics.oop.map;

import agh.ics.oop.Vector2d;

public interface IGrassGenerator {
    Vector2d generateGrassPosition();
    void changeSquareState(Vector2d position);
}
