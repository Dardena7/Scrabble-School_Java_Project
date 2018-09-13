/*
 * ANC3 - Projet d'analyse et de conception - EPFC 2015-2016
 * Groupe 3 - Paulo Pereira de Macedo, Alexis Dardenne
 */
package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Alex
 */
public class Game extends Observable {
    private final Board board;
    private final Bag bag;
    private final Rack rack;
    private int score;
    private static final Set<String> DICTIONNARY = new TreeSet<>();
    
    public Game() throws FileNotFoundException {
        board = new Board();
        bag = new Bag();
        rack = new Rack();
        score = 0;
        this.fillRack();
        this.initializeDic();
    }
    
    public Board getBoard() {
        return this.board;
    }
    
    public Bag getBag() {
        return this.bag;
    }
    
    public Rack getRack() {
        return this.rack;
    }
    
    public static Set<String> getDictionnary() {
        return Game.DICTIONNARY;
    }
    
    //opens the text file and creates a list of the words it contains
    private void initializeDic() throws FileNotFoundException {
        File f = new File("src/french.dic");
        if(f.exists() && f.canRead()) {
            Scanner in = new Scanner(f);
            while(in.hasNext()) {
                DICTIONNARY.add(in.next());
            }
        }
    }
    
    //places a Letter on a Case of the Board or returns false if rack doesn't contain letter
    public boolean playLetter(Letter e, int x, int y) {
        if(rack.contains(e)) { 
            board.placeLetter(e,x,y);
            rack.removeLetter(e);
            return true;
        } else {
            return false;
        }
        
    }
    public boolean playLetter(Letter e, Coordinates c) {
        if(rack.contains(e)) { 
            board.placeLetter(e,c.getX(),c.getY());
            rack.removeLetter(e);
            return true;
        } else {
            return false;
        }
        
    }
    
    //places the played word on the board
    public void placeWord(Move word){
        for(Entry e : word.getMap().entrySet())
            playLetter((Letter)e.getValue(), (Coordinates)e.getKey());
    }
    
    //places a list of letters on their associated coordinates in the board
    public void placeWord(List<Letter> ll, List<Coordinates> lc){
        if(ll.size() != lc.size())
            throw new RuntimeException("Le nombre de lettres et de coordonnéres ne correspondent pas");
        for(int i=0; i<ll.size(); ++i)
            playLetter(ll.get(i), lc.get(i));
        setChanged();
        notifyObservers();
    }
    
    //fills rack with letters from the bag
    public final void fillRack(){
        Letter letter;
        while(rack.size() < 7 && !getBag().isEmpty()){
            letter = bag.getRandomLetter();
            rack.addLetter(letter);
            bag.removeLetter(letter);
        }
    }  
    
    //exchanges letters between rack and bag
    public void changeLetters(List<Letter> letters){
        if(letters.size() <= bag.size()){
            for(Letter l : letters){
                rack.removeLetter(l);
                Letter randomLetter = bag.getRandomLetter();
                rack.addLetter(randomLetter);
                bag.removeLetter(randomLetter);
            }
            for(Letter l : letters)
                bag.addLetter(l);
        }
    }
    
    //returns numerical value of a played word
    public int wordValue(Word word) {
        int value = 0;
        int multiplier = 1;
        for(Coordinates c : word.getWordMap().keySet()) {
            if(board.getCase(c).isEmpty()) {
                if(board.getCase(c) instanceof LetterDoubleCase || board.getCase(c) instanceof LetterTripleCase ) {
                    value += (board.getCase(c).getMultiplier() * word.getWordMap().get(c).getValue());
                } else if (board.getCase(c) instanceof WordDoubleCase || board.getCase(c) instanceof WordTripleCase || board.getCase(c) instanceof CentralCase) {
                    value += word.getWordMap().get(c).getValue();
                    multiplier *= board.getCase(c).getMultiplier();
                } else {
                    value += word.getWordMap().get(c).getValue();
                }
            } else {
                value += word.getWordMap().get(c).getValue();
            }
        }
        return value * multiplier;
    }
    
    //returns numerical value of the played turn's score
    public int moveScore(List<Word> words, Move m) {
        int score = 0;
        for(Word w : words) {
            score += wordValue(w);
        }
        if(m.size() == 7)
            score += 50;
        return score;
    }
    
    //updates total game scrore
    public void updateGameScore(int i) {
        score += i;
    }
    
    //returns numerical value of game score
    public int getGameScore() {
        return score;
    }
}
