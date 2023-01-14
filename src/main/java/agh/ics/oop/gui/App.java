package agh.ics.oop.gui;

import agh.ics.oop.data.Settings;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;

import java.util.ArrayList;

public class App extends Application {
    private ArrayList<Simulation> simulations = new ArrayList<Simulation>();

    public void start(Stage primaryStage) {

        SettingsPanel settings = new SettingsPanel(this);
        Scene mainScene = new Scene(settings.getRoot(), 800, 800);
        primaryStage.setTitle("Settings panel");
        primaryStage.setScene(mainScene);
        primaryStage.show();

    }

    public void createSimulation(Settings settings) {
        simulations.add(new Simulation(settings));
    }
}
