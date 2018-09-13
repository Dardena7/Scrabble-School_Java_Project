/*
 * ANC3 - Projet d'analyse et de conception - EPFC 2015-2016
 * Groupe 3 - Paulo Pereira de Macedo, Alexis Dardenne
 */
package view;

import model.*;
import control.GameManager;
import java.util.Observable;
import java.util.Observer;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Paulo
 */
public class GameView extends Stage implements Observer {
    
    GameManager gm;
    private final Board board;
    private UsableLetters usableLetters;
    
    private final Scene scene;
    private final BorderPane root = new BorderPane();
    private final ScrabbleCanvas canvas;
    private final HBox btBox = new HBox();
    private final Button btDuplicate = new Button("Duplicate");
    private final Button btValidate = new Button("Validate");
    private final Button btChange = new Button("Change Letters");

    
    public GameView(GameManager gm){
        this.gm = gm;
        board = gm.getBoard();
        setUsableLetters(gm.getUsableLetters());
        
        canvas = new ScrabbleCanvas(board, usableLetters, gm);
        layout();
        handlers();
        
        scene = new Scene(root);
        this.setScene(scene);
        this.setTitle("Scrabble Java");
        this.show();
        this.setResizable(false);
        //refreshCanvas();
    }
    
    private void layout(){
        btBox.setAlignment(Pos.TOP_CENTER);
        btBox.setPadding(new Insets(10, 10, 10, 10));
        HBox HBoxLeft = new HBox();
        HBoxLeft.setSpacing(20);
        HBoxLeft.setPadding(new Insets(0, 250, 0, 0));
        HBoxLeft.getChildren().add(btDuplicate);
        HBoxLeft.getChildren().add(btValidate);
        HBox HBoxRight = new HBox();
        HBoxRight.setPadding(new Insets(0, 200, 0, 0));
        HBoxRight.getChildren().add(btChange);
        btBox.getChildren().add(HBoxLeft);
        btBox.getChildren().add(HBoxRight);
        root.setCenter(canvas);
        root.setBottom(btBox);
    }
   
    
    private void handlers(){
        btDuplicate.setOnAction((ActionEvent e) -> {
            GameView gameView = new GameView(gm);
            gameView.update(usableLetters, e);
        });
        
        btValidate.setOnAction((ActionEvent e) -> {
            gm.validateMove();
        });
        
        btChange.setOnAction((ActionEvent e)->{
            gm.changeLetters();
        });
    }
    
    private void setUsableLetters(UsableLetters usableLetters){
        this.usableLetters = usableLetters;
        usableLetters.addObserver(this);
        subscribeShapes();
    }
    
    public void subscribeShapes(){
        for(LetterShape ls : usableLetters.getList())
            ls.addObserver(this);
    }
    
    public static void showAlert(String text){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid play");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();   
    }
    
    @Override
    public void update(Observable o, Object o1) {
        if(o == usableLetters)
            subscribeShapes();
        refreshCanvas();
    }
    
    public void refreshCanvas(){
        canvas.paint();
    }
}
