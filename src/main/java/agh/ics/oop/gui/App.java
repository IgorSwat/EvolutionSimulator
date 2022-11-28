package agh.ics.oop.gui;
import java.lang.*;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;

public class App extends Application
{
    private AbstractWorldMap map;
    private IEngine engine;
    private void loadLabel(GridPane grid, String caption, Vector2d indices)
    {
        Label label = new Label(caption);
        GridPane.setHalignment(label, HPos.CENTER);
        grid.add(label, indices.x, indices.y, 1, 1);
    }
    private void loadGrid(GridPane grid)
    {
        Vector2d leftCorner = map.getLeftCorner();
        Vector2d rightCorner = map.getRightCorner();
        Vector2d mixedCorner = new Vector2d(leftCorner.x, rightCorner.y);
        int width = rightCorner.x - leftCorner.x + 1;
        int height = rightCorner.y - leftCorner.y + 1;
        loadLabel(grid, "y/x", new Vector2d(0, 0));
        grid.getColumnConstraints().add(new ColumnConstraints(30));
        grid.getRowConstraints().add(new RowConstraints(30));
        for (int i = 0; i < width; i++)
        {
            loadLabel(grid, Integer.toString(leftCorner.x + i), new Vector2d(i + 1, 0));
            grid.getColumnConstraints().add(new ColumnConstraints(30));
        }
        for (int j = 0; j < height; j++)
        {
            loadLabel(grid, Integer.toString(rightCorner.y - j), new Vector2d(0, j + 1));
            grid.getRowConstraints().add(new RowConstraints(30));
        }
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                Object object = map.objectAt(mixedCorner.add(new Vector2d(i, -j)));
                Vector2d id = new Vector2d(i + 1, j + 1);
                if (object == null)
                    loadLabel(grid, "", id);
                else
                    loadLabel(grid, object.toString(), id);
            }
        }
    }
    public void start(Stage primaryStage)
    {

        GridPane grid = new GridPane();
        loadGrid(grid);
        grid.setGridLinesVisible(true);

        Scene scene = new Scene(grid, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void init()
    {
        MoveDirection[] directions = OptionsParser.parse(getParameters().getRaw().toArray(new String[0]));
        map = new GrassField(10);
        Vector2d[] positions = { new Vector2d(9,2), new Vector2d(4,8) };
        engine = new SimulationEngine(directions, map, positions);
        engine.run();
    }
}
