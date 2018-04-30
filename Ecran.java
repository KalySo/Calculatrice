package calco;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.TextField;


public class Ecran extends Parent {
    
    private final DoubleProperty value1 = new SimpleDoubleProperty();
    //l'écran de la calculatrice
   TextField Ecran() {
    final TextField screen = new TextField();
    screen.setStyle("-fx-background-color: white; -fx-font-family: algerian;");
    screen.setAlignment(Pos.CENTER_RIGHT);
    screen.setMinSize(50, 80);
    screen.setOpacity(0.9);
    screen.textProperty().bind(Bindings.format("%.0f", value1));
    //screen.setEditable(false);
    return screen;
  } 
   
}
