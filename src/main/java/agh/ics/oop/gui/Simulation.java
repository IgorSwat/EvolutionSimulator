package agh.ics.oop.gui;

import agh.ics.oop.data.IStatLog;
import agh.ics.oop.data.Settings;
import agh.ics.oop.data.StatLogGenotype;
import agh.ics.oop.data.StatLogSum;
import agh.ics.oop.simulation.SimulationEngine;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.HashMap;
import java.util.Map;

// Klasa reprezentująca ogólną obsługę pojedynczej symulacji i jej GUI
public class Simulation {
    private final SimulationEngine engine;
    private Thread engineThread;
    private final StatsPanel statsPanel;
    private final GridHandler grid;
    private HBox container = new HBox();
    // Simulation variables
    private boolean simulationState = true;

    public Simulation(Settings settings) {
        container.setPrefHeight(800);
        container.setPrefWidth(1200);
        statsPanel = new StatsPanel(this, settings, 400, 800);
        engine = new SimulationEngine(this, settings);
        grid = new GridHandler(engine.getUsedMap(), 800, 800);
        grid.getGrid().setAlignment(Pos.CENTER);
        grid.loadGridContent("");
        container.getChildren().add(statsPanel.getRoot());
        container.getChildren().add(grid.getGrid());
        Scene simulationScene = new Scene(container, 1200, 800);
        Stage simulationStage = new Stage();
        simulationStage.setTitle("Evolution Simulator");
        simulationStage.setScene(simulationScene);
        simulationStage.show();
        engineThread = new Thread(engine);
        engineThread.start();
        simulationStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                engine.switchOff();
            }
        });
    }
    public void refreshScene() {
        statsPanel.setButtonText("Simulation on");
        grid.clearGrid();
        grid.loadGridContent(statsPanel.getStatValue("Top genotype"));
    }
    // Stopuje / wznawia symulację
    public void changeSimulationState() {
        if (simulationState) {
            try {
                engine.switchOff();
                engineThread.join();
            }
            catch (InterruptedException exception) {
                System.out.println(exception.getMessage());
                System.exit(-101);
            }
        }
        else {
            engine.switchOn();
            statsPanel.setButtonText("Simulation off");
            grid.clearGrid();
            grid.loadGridContent("");
            engineThread = new Thread(engine);
            engineThread.start();
        }
        simulationState = !simulationState;
    }
    // Aktualizacja statystyk wyświetlanych w GUI
    public void loadStatistics() {
        HashMap<String, IStatLog> logs = engine.getStats();
        for (String key : logs.keySet()) {
            statsPanel.updateStat(key, logs.get(key).getStatValue());
        }
    }
}
