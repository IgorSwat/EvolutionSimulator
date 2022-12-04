package agh.ics.oop;

public class Grass implements IMapElement
{
    private final Vector2d position;
    public Grass(Vector2d position)
    {
        this.position = position;
    }
    public Vector2d getPosition()
    {
        return position;
    }
    public String toString()
    {
        return "*";
    }
    public String getTextureLoc() {
        return "C:/Programowanie/Java - laby/oolab/src/main/resources/grass.jpg";
    }
}
