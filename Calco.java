package calco;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Calco extends Application {
    
   private final DoubleProperty value1 = new SimpleDoubleProperty();
    private final DoubleProperty value2 = new SimpleDoubleProperty();
    
    
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
   
   /* private final String tab[]= {"tan","sin","cos","ln","xy","x2","xn","1/n","n!","arctan","arcsin","pi"};
    private final String operateur[]={"+","*","-","/"};  */
    @Override
    public void start(Stage primaryStage) {
         //touches
     final TilePane buttons = Clavier(fonctions);
     //StackPane root= new StackPane();
    // Touche touches= new Touche();
    // Ecran ecran= new Ecran();
     //touches.Clavier(fonctions);
     //root.getChildren().add(touches.Clavier(fonctions));
     //root.getChildren().add(touches.Ecran());
     //Scene scene = new Scene(root);
     
     
    
       
        //Ecran
       TextField ecran = Ecran();

    
        
        
        primaryStage.setResizable(false);
        primaryStage.setTitle("Calculatrice");
       primaryStage.setScene(new Scene(Forme(ecran, buttons)));
       //primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
   
   // l'écran de la calculatrice
   private TextField Ecran() {
    final TextField screen = new TextField();
    screen.setStyle("-fx-background-color: white; -fx-font-family: algerian;");
    screen.setAlignment(Pos.CENTER_RIGHT);
    screen.setMinSize(50, 80);
    screen.setOpacity(0.9);
    screen.textProperty().bind(Bindings.format("%.0f", value1));
    //screen.setEditable(false);
    return screen;
  } 
   // faire un boutton
    private Button Touche(String l){
        Button btn = new Button (l);
        btn.setMaxSize(70, 50);
        btn.setStyle(" -fx-font-size:10 ");
       if (l.matches("[0-9]") || ".".equals(l)){
      makeNumericButton(l, btn);}
       else{
       final ObjectProperty<Op> triggerOp = determineOperand(l);
       if((triggerOp.get() != Op.NOOP)){
      Operateur(btn, triggerOp);
        btn.setTextFill(Color.BLACK);
      }
       else if ("C".equals(l)) {
        Effacer(btn);
       } else if ("=".equals(l)) {
         Equal(btn);
      }
      
     }
        return btn;
    }
              
    
        
    private ObjectProperty<Op> determineOperand(String s) {
    final ObjectProperty<Op> triggerOp = new SimpleObjectProperty<>(Op.NOOP);
    switch (s) {
      case "+": triggerOp.set(Op.ADD);      break;
      case "-": triggerOp.set(Op.SUBTRACT); break;
      case "*": triggerOp.set(Op.MULTIPLY); break;
      case "/": triggerOp.set(Op.DIVIDE);   break;
    }
    return triggerOp;
  }
    
    
    private void Operateur(Button button, final ObjectProperty<Op> triggerOp) {
    button.setStyle("-fx-base: lightgray;");
    button.setOnAction((ActionEvent actionEvent) -> {
        curOp = triggerOp.get();
    });
  }
   private void Effacer(Button button) {
    button.setStyle("-fx-base: mistyrose;");
    button.setOnAction((ActionEvent actionEvent) -> {
        value1.set(0);
    });
  }
   
   
    private void makeNumericButton(String s, Button button) {
    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
         if (curOp == Op.NOOP){ 
          double val;
          val= value1.get()*10;
          val= val + Integer.parseInt(s);
        value1.set(val);
         }else {
          value2.set(value1.get());
          value1.set(Integer.parseInt(s));
          stackOp = curOp;
          curOp = Op.NOOP;         }
      }
          
    });
  }
   
   //le clavier de la calculatrice
   private TilePane Clavier(String tab[][]){
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
   
   private boolean cherche(String tab[], String s)
   {
       boolean trouve = false;
       for (String i : tab){
           if (i.equals(s)){trouve = true;}      
       }
       return trouve;
   }  
    //tutorialpoints pour les astuces sur les différents outils en java fx
     private VBox Forme(TextField ecran, TilePane buttons) {
    final VBox forme = new VBox(20);
    forme.setAlignment(Pos.CENTER);
    forme.setStyle(" -fx-padding: 20; -fx-background:DARKGRAY; -fx-font-family: arial black; -fx-font-size: 20;");
    forme.getChildren().setAll(ecran, buttons);
    return forme;
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
     
          
     

}

