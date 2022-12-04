package agh.ics.oop.gui;

import java.lang.*;
import java.util.regex.Pattern;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.*;

public class App extends Application
{
    private AbstractWorldMap map;
    private SimulationEngine engine;
    // graphics
    private GridHandler grid;

    public void reloadGrid() {
        grid.changeLinesVisibility();
        grid.clearGrid();
        grid.loadGridContent(map);
        grid.changeLinesVisibility();
    }
    public void start(Stage primaryStage)
    {
        this.grid = new GridHandler();
        grid.loadGridContent(map);
        grid.changeLinesVisibility();

        VBox inputContainer = new VBox();

        TextField text = new TextField();
        text.setPrefWidth(200);
        text.setPrefWidth(30);

        Button button = new Button("Start");
        button.setPrefWidth(100);
        button.setPrefHeight(30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (text.getText().length() > 0)
                {
                    MoveDirection[] newDirections = OptionsParser.parse(text.getText().split(" "));
                    engine.changeMovementSet(newDirections);
                    Thread engineThread = new Thread(engine);
                    engineThread.start();
                }
            }
        });
        inputContainer.getChildren().add(text);
        inputContainer.getChildren().add(button);
        inputContainer.setAlignment(Pos.CENTER);

        Scene mainScene = new Scene(grid.getGrid(), 1000, 800);
        Scene inputScene = new Scene(inputContainer, 200, 60);

        primaryStage.setScene(mainScene);
        Stage stage = new Stage();
        stage.setScene(inputScene);
        primaryStage.show();
        stage.show();

    }
    public void init()
    {
        MoveDirection[] directions = OptionsParser.parse(getParameters().getRaw().toArray(new String[0]));
        map = new GrassField(10);
        Vector2d[] positions = { new Vector2d(9,2), new Vector2d(4,8) };
        engine = new SimulationEngine(directions, map, positions);
        engine.setObserver(this);
    }
}
