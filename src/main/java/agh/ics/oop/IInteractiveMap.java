package agh.ics.oop;

public interface IInteractiveMap extends IWorldMap
{
    void registerMove(Animal animal, Vector2d newPosition);
}
