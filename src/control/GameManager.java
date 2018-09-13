/*
 * ANC3 - Projet d'analyse et de conception - EPFC 2015-2016
 * Groupe 3 - Paulo Pereira de Macedo, Alexis Dardenne
 */ 
package control;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;
import model.*;
import view.GameView;
import view.LetterShape;
import view.ScrabbleCanvas;
import view.UsableLetters;
/**
 *
 * @author Paulo
 */
public class GameManager extends Application {
    
    private final Game game;
    private final UsableLetters usableLetters;
    private Move move;
    private final List<Letter> changeList = new ArrayList();
    
    public static void main (String[] args) {
        launch(args);
    }

    public GameManager() throws FileNotFoundException {
        this.game = new Game();
        this.usableLetters = new UsableLetters();
        this.move = new Move();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new GameView(this);
        playTurn();         
    }
    
    //Si il reste des lettres à jouer, remplit le rack si possible et remplit usableLetters avec les lettres du rack (pour la vue)
    public void playTurn(){
        if(playableLetters()){
            fillRack();
            setUsableLetters();
        }
        else
            System.out.println("Game over!");
    }
    
    public UsableLetters getUsableLetters(){
        return usableLetters;
    }
    
    public void fillRack(){
        game.fillRack();
    }
    
    public Board getBoard(){
        return game.getBoard();
    }
    
    public Rack getRack(){
        return game.getRack();
    }
    
    public Bag getBag(){
        return game.getBag();
    }
    
    //Remplit usableLetters avec les lettres du rack (mise en forme pour la vue)
    public void setUsableLetters(){ 
        usableLetters.setList(getRack());
    }
    
    public boolean playableLetters(){
        return !getBag().isEmpty() || !getRack().isEmpty();
    }
    
    //Vérifie la validité du mot et le joue si Ok
    //Sinon affiche une pop up
    //Ensuite passage au tour suivant (Même situation qu'au début du tour si mot invalide)
    public void validateMove(){
        setMove();
        clearUsableLetters();
        String moveCheck = checkMoveOk();
        if(moveCheck.isEmpty()) {
            System.out.println(getMoveScore());            
            game.updateGameScore(getMoveScore());
            playWord();
            System.out.println("Total : " + game.getGameScore());
        }else {
            GameView.showAlert(moveCheck);
        }
        playTurn();
    }
    
    public void changeLetters(){
        setChangeList();
        clearUsableLetters();
        if(getBag().size() >= changeList.size())
            game.changeLetters(changeList);
        playTurn();
    }
    
    private void clearUsableLetters(){
        usableLetters.getList().clear();
    }
    
    private void playWord(){
        game.placeWord(move);
    }
    
    
    //Trie l'ordre d'apparition des lettres dans word en fonction de leur place sur le plateau
    private void setMove(){
        move.clear();
        putPlayedLettersInMove();
        sortMove();
    }
    
    private void setChangeList(){
        changeList.clear();
        putLettersInChangeList();
    }
    
    //Remplit word avec les lettres jouées et leurs coordonnées dans le model du board (calcul en fonction des coordonnées du canvas)
    private void putPlayedLettersInMove(){
        for(LetterShape ls : usableLetters.getList())
            if(ScrabbleCanvas.onTheBoard(ls.getX(), ls.getY())){
                Coordinates c = new Coordinates(ScrabbleCanvas.getBoardIndexX(ls.getX()), ScrabbleCanvas.getBoardIndexY(ls.getY()));
                Letter l = ls.getLetter();
                move.add(l, c);
            }
    }
    
    private void putLettersInChangeList(){
        for(LetterShape ls : usableLetters.getList())
            if(ScrabbleCanvas.onTheGarbage(ls.getX(), ls.getY())){
                Letter l = ls.getLetter();
                changeList.add(l);
            }
    }
    
    
    //Range les lettres dans l'ordre d'apparation sur le board (de haut en bas et de gauche à droite)
    //Recherche la lettre la plus "haute" dans le board, la supprime de word et la rajoute à sortedWord
    //On répète cette opération jusqu'à ce que word soit vide puis on lui donne sortedWord
    public void sortMove(){
        Move sortedWord = new Move();
        while(move.size() > 0){
            int position = 0;
            Coordinates minCoordinates = move.getCoordinate(0);
            for(int j=1; j<move.size(); ++j){
                Coordinates currentCoordinates = move.getCoordinate(j);
                int compareResult = minCoordinates.compareTo(currentCoordinates);
                if(compareResult > 0){
                    minCoordinates = currentCoordinates;
                    position = j;
                }
            }
            sortedWord.add(move.getLetter(position), minCoordinates);
            move.remove(position);
        }
        move = sortedWord;
        System.out.println(move.toString());
    }
    
    //Vérifie la validité du mot au premier tour et aux tours suivants
    private String checkMoveOk(){
        MoveChecker mc = new MoveChecker(getBoard(), move);
        return mc.checkMove();
    }
    
    //get numerical value of turn's score from Game
    private int getMoveScore() {
        MoveChecker mc = new MoveChecker(getBoard(), move);
        return game.moveScore(mc.getWordsList(), move);
    }
    
    public int getGameScore(){
        return game.getGameScore();
    }
}
