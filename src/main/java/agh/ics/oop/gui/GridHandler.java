package agh.ics.oop.gui;

import agh.ics.oop.AbstractWorldMap;
import agh.ics.oop.IMapElement;
import agh.ics.oop.Vector2d;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GridHandler
{
    private final GridPane grid;
    private final List<GuiElementBox> elements;
    private final double squareSize = 50;
    private int currentXRange = -1;
    private int currentYRange = -1;
    private boolean linesVisibility = false;

    public GridHandler()
    {
        grid = new GridPane();
        elements = new ArrayList<>();
    }
    private void loadNode(Node node, Vector2d indices)
    {
        GridPane.setHalignment(node, HPos.CENTER);
        grid.add(node, indices.x, indices.y, 1, 1);
    }
    private void loadGridConstraints(int columnDiff, int rowDiff) {
        if (columnDiff >= 0)
        {
            for (int i = 0; i < columnDiff; i++)
                grid.getColumnConstraints().add(new ColumnConstraints(squareSize));
        }
        else
        {
            for (int i = 0; i > columnDiff; i--)
                grid.getColumnConstraints().remove(grid.getColumnConstraints().size() - 1);
        }
        if (rowDiff >= 0)
        {
            for (int j = 0; j < rowDiff; j++)
                grid.getRowConstraints().add(new RowConstraints(squareSize));
        }
        else
        {
            for (int i = 0; i > rowDiff; i--)
                grid.getRowConstraints().remove(grid.getRowConstraints().size() - 1);
        }
    }
    public void loadGridContent(AbstractWorldMap map)
    {
        Vector2d leftCorner = map.getLeftCorner();
        Vector2d rightCorner = map.getRightCorner();
        Vector2d mixedCorner = new Vector2d(leftCorner.x, rightCorner.y);
        int width = rightCorner.x - leftCorner.x + 1;
        int height = rightCorner.y - leftCorner.y + 1;
        loadGridConstraints(width - currentXRange, height - currentYRange);
        currentXRange = width;
        currentYRange = height;
        loadNode(new Label("y/x"), new Vector2d(0, 0));
        for (int i = 0; i < width; i++) {
            loadNode(new Label(Integer.toString(leftCorner.x + i)), new Vector2d(i + 1, 0));
        }
        for (int j = 0; j < height; j++) {
            loadNode(new Label(Integer.toString(rightCorner.y - j)), new Vector2d(0, j + 1));
        }
        int countElemments = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Object object = map.objectAt(mixedCorner.add(new Vector2d(i, -j)));
                Vector2d id = new Vector2d(i + 1, j + 1);
                if (object == null)
                    loadNode(new Label(""), id);
                else {
                    try {
                        GuiElementBox element;
                        if (countElemments < elements.size())
                        {
                            element = elements.get(countElemments);
                            element.reloadContent((IMapElement) object);
                        }
                        else
                        {
                            element = new GuiElementBox((IMapElement) object);
                            elements.add(element);
                        }
                        countElemments += 1;
                        grid.add(element.getVisualization(), i + 1, j + 1, 1, 1);
                    } catch (FileNotFoundException exception) {
                        System.out.println(exception.getMessage());
                        System.exit(-9);
                    }
                }
            }
        }

    }
    public void changeLinesVisibility()
    {
        linesVisibility = !linesVisibility;
        grid.setGridLinesVisible(linesVisibility);
    }
    public void clearGrid() {grid.getChildren().clear();}
    public GridPane getGrid() {return this.grid;}
}
