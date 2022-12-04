package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.Grass;
import agh.ics.oop.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox
{
    private final ImageView image;
    private Label label;
    private final VBox container;
    public GuiElementBox(IMapElement element) throws FileNotFoundException {
        Image img = new Image(new FileInputStream(element.getTextureLoc()));
        image = new ImageView(img);
        image.setFitHeight(20);
        image.setFitWidth(20);

        container = new VBox(image);

        String caption = "";
        if (element instanceof Animal)
            caption = "Z " + element.getPosition().toString();
        else if (element instanceof Grass)
            caption = "Trawa";
        label = new Label(caption);

        container.getChildren().add(label);
        container.setAlignment(Pos.CENTER);
    }
    public void reloadContent(IMapElement element) throws  FileNotFoundException
    {
        Image img = new Image(new FileInputStream(element.getTextureLoc()));
        image.setImage(img);

        container.getChildren().remove(label);
        String caption = "";
        if (element instanceof Animal)
            caption = "Z " + element.getPosition().toString();
        else if (element instanceof Grass)
            caption = "Trawa";
        label = new Label(caption);
        container.getChildren().add(label);
    }
    public VBox getVisualization() {
        return container;
    }
}
