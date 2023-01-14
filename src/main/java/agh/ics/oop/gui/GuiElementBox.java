package agh.ics.oop.gui;

import agh.ics.oop.elements.Animal;
import agh.ics.oop.elements.IMapElement;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

// Klasa reprezentująca pojedynczy element na mapie (zwierzę lub roślinę)
public class GuiElementBox {
    private final VBox container;

    public GuiElementBox(IMapElement element, String genotypeCheck, double squareWidth, double squareHeight) {
        container = new VBox();
        container.setPrefWidth(squareWidth);
        container.setPrefHeight(squareHeight);
        Node node;
        if (element instanceof Animal) {
            Animal animal = (Animal) element;
            Circle circle = new Circle();
            if (animal.getGenotype().toString().equals(genotypeCheck)) {
                circle.setRadius(45 * Math.min(squareHeight, squareWidth) / 100);
                circle.setFill(Color.BLUEVIOLET);
            } else {
                circle.setRadius(4 * Math.min(squareHeight, squareWidth) / 10);
                double status = element.getHealthStatus();
                if (status > 0.9)
                    circle.setFill(Color.color(0.45, 0.2, 0.0));
                else if (status > 0.7)
                    circle.setFill(Color.color(0.7, 0.35, 0.0));
                else if (status > 0.5)
                    circle.setFill(Color.color(0.8, 0.4, 0.0));
                else if (status > 0.2)
                    circle.setFill(Color.color(0.78, 0.55, 0.33));
                else
                    circle.setFill(Color.color(1.0, 0.75, 0.5));
            }
            node = circle;
        } else {
            Rectangle rectangle = new Rectangle();
            rectangle.setWidth(8 * squareWidth / 10);
            rectangle.setHeight(8 * squareHeight / 10);
            rectangle.setFill(Color.color(0.15, 0.65, 0.15));
            node = rectangle;
        }
        container.getChildren().add(node);
    }

    public VBox getVisualization() {
        return container;
    }
}
