package agh.ics.oop.gui;

import agh.ics.oop.data.Settings;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Scanner;

// Odpowiada za wyświetlanie UI i wyboru parametrów symulacji
public class SettingsPanel {
    private final App mainApp;
    private final VBox root;
    private Settings settings;
    // Inputy zgrupowane po nazwach (czytelniejsze od grupowania indeksami 0, 1, ...)
    private final HashMap<String, TextField> inputs;
    private final HashMap<String, ToggleGroup> radioInputs;
    // Parametry okna z ustawieniami
    private final double lowerFontSize = 14;
    private final double higherFontSize = 18;
    private final double inputWidth = 160;
    private final double inputHeight = 20;
    private final double inputLineHeight = 25;

    private void addTextInput(String text) {
        HBox inputLine = new HBox();
        inputLine.setPrefHeight(inputLineHeight);
        Label label = new Label(text + ":");
        label.setFont(Font.font("Verdana", FontWeight.BOLD, lowerFontSize));
        TextField input = new TextField();
        input.setPrefWidth(inputWidth);
        input.setPrefHeight(inputHeight);
        inputLine.getChildren().addAll(label, input);
        inputLine.setAlignment(Pos.CENTER);
        inputLine.setSpacing(400 - label.getPrefWidth() - inputWidth);
        root.getChildren().add(inputLine);
        inputs.put(text, input);
    }
    private void addRadioInput(String text, String... values) {
        HBox inputLine = new HBox();
        inputLine.setPrefHeight(inputLineHeight);
        Label label = new Label(text + ":");
        label.setFont(Font.font("Verdana", FontWeight.BOLD, lowerFontSize));
        inputLine.getChildren().add(label);
        ToggleGroup group = new ToggleGroup();
        for (String value : values) {
            RadioButton radio = new RadioButton(value);
            radio.setToggleGroup(group);
            inputLine.getChildren().add(radio);
        }
        group.selectToggle(group.getToggles().get(0));
        inputLine.setAlignment(Pos.CENTER);
        inputLine.setSpacing((400 - label.getPrefWidth() - inputWidth) / values.length);
        root.getChildren().add(inputLine);
        radioInputs.put(text, group);
    }
    private void addLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Verdana", FontWeight.BOLD, higherFontSize));
        root.getChildren().add(label);
    }
    private void createSaveButton() {
        Button button = new Button("Run");
        button.setPrefWidth(0.8*inputWidth);
        button.setPrefHeight(2*inputHeight);
        button.setFont(Font.font("Verdana", FontWeight.MEDIUM, higherFontSize));
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveSettings();
            }
        });
        root.getChildren().add(button);
    }
    private void createFileButton() {
        Button button = new Button("Select configuration");
        button.setPrefWidth(2*inputWidth);
        button.setPrefHeight(2*inputHeight);
        button.setFont(Font.font("Verdana", FontWeight.MEDIUM, higherFontSize));
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser file = new FileChooser();
                file.setTitle("Open File");
                File fileChosen = file.showOpenDialog(new Stage());
                try {
                    if (fileChosen != null)
                        parseFile(fileChosen);
                }
                catch (FileNotFoundException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        });
        root.getChildren().add(button);
    }
    private void parseFile(File file) throws FileNotFoundException {
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String key = "";
            String value = "";
            char letter = line.charAt(0);
            int i = 1;
            while (letter != ':' && i < line.length()) {
                key += letter;
                letter = line.charAt(i);
                i += 1;
            }
            if (letter == '\n')
                return;
            while (i < line.length()) {
                letter = line.charAt(i);
                if (letter >= '0' && letter <= '9')
                    value += letter;
                i += 1;
            }
            setInputValue(key, value);
        }
        reader.close();
    }
    private String getInputValue(String inputName) throws InvalidParameterException {
        if (inputs.get(inputName) != null) {
            return inputs.get(inputName).getText();
        }
        if (radioInputs.get(inputName) != null){
            RadioButton selected = (RadioButton) radioInputs.get(inputName).getSelectedToggle();
            return selected.getText();
        }
        throw new InvalidParameterException(inputName + " is not defined as an input");
    }
    private void setInputValue(String inputName, String value) throws  InvalidParameterException {
        TextField input = inputs.get(inputName);
        if (input == null)
            throw new InvalidParameterException(inputName + " is not defined as an input");
        input.setText(value);
    }
    private boolean validateInput(String value, String inputName) throws IllegalArgumentException {
        if (value.length() == 0)
            return false;
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) < '0' || value.charAt(i) > '9')
                throw new IllegalArgumentException(value + " is not a valid value for input: " + inputName);
        }
        return true;
    }
    private boolean saveSettings() {
        try {
            for (String key : inputs.keySet()) {
                if (!validateInput(getInputValue(key), key))
                    return false;
            }
            settings = new Settings(Double.parseDouble(getInputValue("Map height")), Double.parseDouble(getInputValue("Map width")),
                    getInputValue("Map type"), Integer.parseInt(getInputValue("Grass starting number")),
                    Integer.parseInt(getInputValue("Grass respawn per day")),
                    Integer.parseInt(getInputValue("Animals starting number")), Integer.parseInt(getInputValue("Animals starting energy")),
                    Integer.parseInt(getInputValue("Energy per grass")), Integer.parseInt(getInputValue("Reproduction energy requirements")),
                    Integer.parseInt(getInputValue("Reproduction energy cost")),
                    Integer.parseInt(getInputValue("Energy loss per day")),
                    Integer.parseInt(getInputValue("Minimal mutations number")),
                    Integer.parseInt(getInputValue("Maximal mutations number")), getInputValue("Mutation type"),
                    Integer.parseInt(getInputValue("Genome length")), getInputValue("Animals behavior"),
                    Integer.parseInt(getInputValue("Simulation refresh time (ms)")));
        }
        catch (InvalidParameterException exception) {
            System.out.println(exception.getMessage());
            System.exit(-204);
        }
        catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
        mainApp.createSimulation(settings);
        return true;
    }

    public SettingsPanel(App app) {
        this.mainApp = app;
        inputs = new HashMap<>();
        radioInputs = new HashMap<>();

        root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(7);
        addLabel("Map properties");
        addTextInput("Map height");
        addTextInput("Map width");
        addRadioInput("Map type", "Globe", "Hell portal");
        addLabel("Spawning properties");
        addTextInput("Grass starting number");
        addTextInput("Grass respawn per day");
        addTextInput("Animals starting number");
        addLabel("Energy properties");
        addTextInput("Animals starting energy");
        addTextInput("Energy per grass");
        addTextInput("Reproduction energy requirements");
        addTextInput("Reproduction energy cost");
        addTextInput("Energy loss per day");
        addLabel("Mutations properties");
        addTextInput("Minimal mutations number");
        addTextInput("Maximal mutations number");
        addRadioInput("Mutation type", "Full randomness", "Slight correction");
        addTextInput("Genome length");
        addRadioInput("Animals behavior", "Full predestination", "A bit of madness");
        addLabel("Others");
        addTextInput("Simulation refresh time (ms)");
        createFileButton();
        createSaveButton();
    }

    public VBox getRoot() {
        return root;
    }
}
