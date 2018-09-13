/*
 * ANC3 - Projet d'analyse et de conception - EPFC 2015-2016
 * Groupe 3 - Paulo Pereira de Macedo, Alexis Dardenne
 */
package model;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Paulo
 */
public class Board {
    
    private Case[][] playBoard; //2D array of Cases
    private boolean isEmpty = true;
    
    public Board() {
        playBoard = new Case[15][15];
        this.fillBoard();
    }
    
    //fills the playBoard with the appropriate cases
    private void fillBoard() { 
        for(int i=0; i<15; i++) {
            for(int j=0; j<15; j++) {
                Coordinates tmp = new Coordinates(i,j);
                if(letterDouble.contains(tmp)) {
                    playBoard[i][j] = new LetterDoubleCase(null,i,j);
                } else if(letterTriple.contains(tmp)) {
                    playBoard[i][j] = new LetterTripleCase(null,i,j);
                } else if(wordDouble.contains(tmp)) {
                    playBoard[i][j] = new WordDoubleCase(null,i,j);
                } else if(wordTriple.contains(tmp)) {
                    playBoard[i][j] = new WordTripleCase(null,i,j);
                } else if(i==j) {
                    playBoard[i][j] = new CentralCase(null,i,j);
                } else {
                    playBoard[i][j] = new OrdinaryCase(null,i,j);
                }
            }
        }
    }
    
    //returns the case at the given coordinates
    public Case getCase(int x, int y) { 
        return playBoard[x][y];
    }
    public Case getCase(Coordinates c) {
        return playBoard[c.getX()][c.getY()];
    }
    
    //places a given Letter on a Case of the Board
    public void placeLetter(Letter e, int x, int y) {
        if(0<=x && x<15 && 0<=y && y<15) {
            if(playBoard[x][y].getLetter()==null) {
                playBoard[x][y].setLetter(e);
                isEmpty = false;
            } else {
                System.out.println("Case already taken");
            }
        } else {
            System.out.println("Invalid coordinates");
        }
        
    }
    
    public boolean isEmpty(){
        return isEmpty;
    }
    
    //lists of special cases coordinates
    private static final List<Coordinates> letterDouble = new ArrayList<Coordinates>() {
        {
            add(new Coordinates(3,0));
            add(new Coordinates(3,14));
            add(new Coordinates(11,0));
            add(new Coordinates(11,14));
            add(new Coordinates(6,2));
            add(new Coordinates(8,2));
            add(new Coordinates(7,3));
            add(new Coordinates(6,6));
            add(new Coordinates(8,8));
            add(new Coordinates(6,8));
            add(new Coordinates(8,6));
            add(new Coordinates(7,11));
            add(new Coordinates(6,12));
            add(new Coordinates(8,12));
            add(new Coordinates(0,3));
            add(new Coordinates(0,11));
            add(new Coordinates(2,6));
            add(new Coordinates(3,7));
            add(new Coordinates(2,8));
            add(new Coordinates(12,6));
            add(new Coordinates(11,7));
            add(new Coordinates(12,8));
            add(new Coordinates(14,3));
            add(new Coordinates(14,11));
            
        }
    };
    private static final List<Coordinates> letterTriple = new ArrayList<Coordinates>() {
        {
            add(new Coordinates(5,1));
            add(new Coordinates(9,1));
            add(new Coordinates(1,5));
            add(new Coordinates(5,5));
            add(new Coordinates(9,5));
            add(new Coordinates(13,5));
            add(new Coordinates(5,9));
            add(new Coordinates(9,9));
            add(new Coordinates(13,9));
            add(new Coordinates(1,9));
            add(new Coordinates(5,13));
            add(new Coordinates(9,13));
        }
    };
    private static final List<Coordinates> wordDouble = new ArrayList<Coordinates>() {
        {
            add(new Coordinates(1,1));
            add(new Coordinates(2,2));
            add(new Coordinates(3,3));
            add(new Coordinates(4,4));
            add(new Coordinates(10,10));
            add(new Coordinates(11,11));
            add(new Coordinates(12,12));
            add(new Coordinates(13,13));
            add(new Coordinates(13,1));
            add(new Coordinates(12,2));
            add(new Coordinates(11,3));
            add(new Coordinates(10,4));
            add(new Coordinates(4,10));
            add(new Coordinates(3,11));
            add(new Coordinates(2,12));
            add(new Coordinates(1,13));
            
            
        }
    };;
    private static final List<Coordinates> wordTriple = new ArrayList<Coordinates>() {
        {
            add(new Coordinates(0,0));
            add(new Coordinates(14,14));
            add(new Coordinates(0,14));
            add(new Coordinates(14,0));
            add(new Coordinates(7,0));
            add(new Coordinates(0,7));
            add(new Coordinates(7,14));
            add(new Coordinates(14,7));
        }
    };
}
