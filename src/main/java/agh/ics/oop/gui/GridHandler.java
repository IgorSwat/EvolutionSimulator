package agh.ics.oop.gui;

import agh.ics.oop.elements.IMapElement;
import agh.ics.oop.Vector2d;
import agh.ics.oop.map.IPositionChangeObserver;
import agh.ics.oop.map.IWorldMap;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.HashMap;

// Klasa obsługująca graficzną reprezentację świata symulacji
public class GridHandler implements IPositionChangeObserver
{
    private final IWorldMap map;
    private final HashMap<Vector2d, Node> elements = new HashMap<>();
    private final GridPane grid = new GridPane();
    private final double squareWidth;
    private final double squareHeight;

    public GridHandler(IWorldMap map, double gridWidth, double gridHeight)
    {
        this.grid.setPrefWidth(gridWidth);
        this.grid.setPrefHeight(gridHeight);
        this.grid.backgroundProperty().set(new Background(new BackgroundFill(Color.color(0.65, 1.0, 0.4),
                                                            CornerRadii.EMPTY, Insets.EMPTY)));
        this.map = map;
        // Ustawia GridHandlera jako obserwatora (wizualizację) mapy
        this.map.setVisualiser(this);
        this.squareHeight = 750.0 / map.getHeight();
        this.squareWidth = 750.0 / map.getWidth();
        loadGridConstraints(map.getWidth(), map.getHeight());

    }
    private void loadGridConstraints(int columnDiff, int rowDiff) {
        for (int i = 0; i < columnDiff; i++)
            grid.getColumnConstraints().add(new ColumnConstraints(squareWidth));
        for (int j = 0; j < rowDiff; j++)
            grid.getRowConstraints().add(new RowConstraints(squareHeight));
    }
    public void loadGridContent(String genotypeCheck)
    {
        int width = map.getWidth();
        int height = map.getHeight();
        Vector2d leftCorner = new Vector2d(0, 0);
        Vector2d rightCorner = new Vector2d(width - 1, height - 1);
        Vector2d mixedCorner = new Vector2d(leftCorner.x, rightCorner.y);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Vector2d position = mixedCorner.add(new Vector2d(i, -j));
                Object object = map.objectAt(position);
                if (object != null) {
                    GuiElementBox element = new GuiElementBox((IMapElement) object, genotypeCheck, squareWidth, squareHeight);
                    grid.add(element.getVisualization(), i, j, 1, 1);
                    elements.put(position, element.getVisualization());
                }
            }
        }
    }
    // Wywowływana za każdym razem gdy zwierzę lub trawa zmieniają swoją pozycję w sposób znaczący dla widoku świata
    public void positionChanged(IMapElement element, Vector2d oldPosition) {
        Vector2d mixedCorner = new Vector2d(0, map.getHeight() - 1);
        if (oldPosition != null) {
            grid.getChildren().remove(elements.get(oldPosition));
            elements.remove(oldPosition);
            Object object = map.objectAt(oldPosition);
            if (object != null) {
                GuiElementBox guiElement = new GuiElementBox((IMapElement) object, "", squareWidth, squareHeight);
                grid.add(guiElement.getVisualization(), oldPosition.x - mixedCorner.x, mixedCorner.y - oldPosition.y);
                elements.put(oldPosition, guiElement.getVisualization());
            }
        }
        if (element != null) {
            Vector2d newPosition = element.getPosition();
            Object object = map.objectAt(newPosition);
            GuiElementBox guiElement = new GuiElementBox((IMapElement) object, "", squareWidth, squareHeight);
            if (elements.get(newPosition) != null) {
                grid.getChildren().remove(elements.get(newPosition));
                elements.remove(newPosition);
            }
            grid.add(guiElement.getVisualization(), newPosition.x - mixedCorner.x, mixedCorner.y - newPosition.y);
            elements.put(newPosition, guiElement.getVisualization());
        }
    }
    public void clearGrid() {grid.getChildren().clear();}
    public GridPane getGrid() {return this.grid;}
}
