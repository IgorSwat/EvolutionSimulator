package agh.ics.oop;

import agh.ics.oop.elements.Animal;
import agh.ics.oop.elements.genotype.GenotypeClassic;
import agh.ics.oop.gui.App;
import agh.ics.oop.map.*;
import javafx.application.Application;

import static java.lang.System.exit;
import static java.lang.System.out;

public class World {
    public static void main(String[] args) {
        try {
            Application.launch(App.class, args);
        } catch (IllegalArgumentException exception) {
            out.println(exception.getMessage());
            exit(-4);
        }
    }
}
