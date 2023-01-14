package agh.ics.oop.gui;

import agh.ics.oop.data.Settings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.HashMap;

// Klasa reprezentująca lewą część GUI (panel ze statystykami + przycisk stopowania symulacji)
public class StatsPanel {
    private final Simulation simulation;
    private final VBox panel;
    private final VBox upperPart;
    private final HashMap<String, Text> statistics = new HashMap<>();
    private Button button;
    private final double panelWidth;
    private final double lineHeight = 70.0;
    private final double fontSize = 16.0;

    public StatsPanel(Simulation simulation, Settings settings, double panelWidth, double panelHeight) {
        this.panelWidth = panelWidth;
        this.simulation = simulation;
        this.panel = new VBox();
        upperPart = new VBox();
        panel.setPrefWidth(panelWidth);
        panel.setPrefHeight(panelHeight);
        upperPart.setPrefWidth(panelWidth);
        upperPart.setPrefHeight(9 * panelHeight / 10);
        addStatsLine("Animals", Integer.toString(settings.getAnimalsStarting()));
        addStatsLine("Grass", Integer.toString(settings.getGrassStarting()));
        addStatsLine("Free squares", "0");
        addStatsLine("Top genotype", "-");
        addStatsLine("Average animal energy", "-");
        addStatsLine("Average life length", "-");
        panel.getChildren().add(upperPart);
        createButton();
    }

    private void addStatsLine(String title, String initialValue) {
        HBox line = new HBox();
        line.setMinWidth(0.9 * panelWidth);
        line.setPrefHeight(lineHeight);
        Label titleLabel = new Label(title + ":");
        titleLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 0.8 * fontSize));
        line.getChildren().add(titleLabel);
        Text statField = new Text();
        statField.setFont(Font.font("Verdana", FontWeight.BOLD, 1.5 * fontSize));
        statField.setText(initialValue);
        statField.setTextAlignment(TextAlignment.RIGHT);
        line.getChildren().add(statField);
        statistics.put(title, statField);
        line.setAlignment(Pos.CENTER);
        line.setSpacing(fontSize);
        upperPart.getChildren().add(line);
    }

    private void createButton() {
        HBox line = new HBox();
        line.setMinWidth(0.9 * panelWidth);
        line.setAlignment(Pos.CENTER);
        button = new Button();
        button.setText("Simulation off");
        button.setAlignment(Pos.BOTTOM_CENTER);
        button.setFont(Font.font("Verdana", FontWeight.MEDIUM, 1.5 * fontSize));
        line.getChildren().add(button);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                simulation.changeSimulationState();
            }
        });
        panel.getChildren().add(line);
    }

    public void updateStat(String statName, String value) {
        Text textField = statistics.get(statName);
        if (textField == null)
            throw new IllegalArgumentException(statName + " is not a valid statistics name");
        textField.setText(value);
    }

    public String getStatValue(String statName) {
        Text textField = statistics.get(statName);
        if (textField == null)
            throw new IllegalArgumentException(statName + " is not a valid statistics name");
        return textField.getText();
    }

    public void setButtonText(String text) {
        button.setText(text);
    }

    public VBox getRoot() {
        return panel;
    }
}
