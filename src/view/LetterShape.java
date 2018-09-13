/*
 * ANC3 - Projet d'analyse et de conception - EPFC 2015-2016
 * Groupe 3 - Paulo Pereira de Macedo, Alexis Dardenne
 */
package view;

import java.util.Observable;
import model.Letter;

/**
 *
 * @author Alex
 */
public class LetterShape extends Observable {
    private int posX;
    private int posY;
    private final int size;
    private final Letter letter;
    
    public LetterShape(int x, int y, Letter l, int s){
        setPos(x, y);
        letter = l;
        size = s;
    }
    
    public int getX(){
        return posX;
    }
    
    public int getY(){
        return posY;
    }
    
    public int getSize(){
        return size;
    }
    
    public Letter getLetter(){
        return letter;
    }
    
    public final void setPos(int x, int y){
        posX = x;
        posY = y;
        setChanged();
        notifyObservers();
    }
}
