package calco;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Touche extends Parent {

    void clavier(String[][] fonctions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    // définition des opérateurs
    private enum Op { NOOP, ADD, SUBTRACT, MULTIPLY, DIVIDE }

    private Op curOp   = Op.NOOP;
    private Op stackOp = Op.NOOP;
    
    
     final String fonctions[][] = {
         { "tan" , "sin", "cos", "ln", "%"},
         {"xy", "x2", "xn", "1/n", "n!"},
         {"(", ")", "pi", "arctan", "arcsin"},
          {"7","8","9","C","/"},
         {"4","5","6","DEL","*"},
         {"1","2","3","ON","+"},
         {"+-","0",".","=", "-"}
    };
     
   
     // valeur contenu dans l'ecran
    private final DoubleProperty value1 = new SimpleDoubleProperty();
    private final DoubleProperty value2 = new SimpleDoubleProperty();
    
    // fonction pour créer  un boutton
   private Button Touche(String l){
        Button btn = new Button (l);
        btn.setMaxSize(70, 50);
        btn.setStyle(" -fx-font-size:10 ");
       if (l.matches("[0-9]") || ".".equals(l)){
           // methodes pour les touches numériques
        makeNumericButton(l, btn);}
       else{
       final ObjectProperty<Touche.Op> triggerOp = (ObjectProperty<Touche.Op>) determineOperand(l);
       if((triggerOp.get() != Touche.Op.NOOP)){
      //Operateur(btn, triggerOp);
        btn.setTextFill(Color.BLACK);
      }
       else if ("C".equals(l)) {
        Effacer(btn);
       } else if ("=".equals(l)) {
        // Equal(btn);
      }
      
     }
        return btn;
    }
           


 //le clavier de la calculatrice
   TilePane Clavier(String tab[][]){
   TilePane buttons = new TilePane();
    buttons.setVgap(4);
    buttons.setHgap(4); 
        for (String r[] : tab ){
            for(String j: r){
               buttons.getChildren().add(Touche(j));
                
            }
            
        }
        return buttons;
    
}   
    
       // pour identifier les opérateurs 
    private ObjectProperty<Touche.Op> determineOperand(String s) {
    final ObjectProperty<Touche.Op> triggerOp = new SimpleObjectProperty<>(Touche.Op.NOOP);
    switch (s) {
      case "+": triggerOp.set(Touche.Op.ADD);      break;
      case "-": triggerOp.set(Touche.Op.SUBTRACT); break;
      case "*": triggerOp.set(Touche.Op.MULTIPLY); break;
      case "/": triggerOp.set(Touche.Op.DIVIDE);   break;
    }
    return triggerOp;
  }
    // pour la touche "C"
    private void Effacer(Button button) {
    button.setStyle("-fx-base: mistyrose;");
    button.setOnAction((ActionEvent actionEvent) -> {
        value1.set(0);
    });
  }
    
    // définition de la fonction pour les chifres numériques
    private void makeNumericButton(String s, Button button) {
    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
         if (curOp == Touche.Op.NOOP){ 
          double val;
          val= value1.get()*10;
          val= val + Integer.parseInt(s);
        value1.set(val);
         }else {
          value2.set(value1.get());
          value1.set(Integer.parseInt(s));
          stackOp = curOp;
          curOp = Touche.Op.NOOP;         }
      }
          
    });
   } 
    
    // fonction pour les opérateur
    private void Operateur(Button button, final ObjectProperty<Touche.Op> triggerOp) {
    button.setStyle("-fx-base: lightgray;");
    button.setOnAction((ActionEvent actionEvent) -> {
        curOp = triggerOp.get();
    });
  }
    
    
     private void Equal(Button btn)
     { 
        btn.setStyle("-fx-base: ghostwhite;");
        btn.setOnAction( (ActionEvent action) ->
         {
         switch(stackOp){
             case ADD: value1.set(value1.get() + value2.get()); break;
             case SUBTRACT: value1.set(value1.get() - value2.get()); break;
             case MULTIPLY: value1.set(value2.get() * value1.get()); break;
             case DIVIDE: value1.set(value2.get()/value1.get()); break;
         }
         });
  }
     
     
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
   

