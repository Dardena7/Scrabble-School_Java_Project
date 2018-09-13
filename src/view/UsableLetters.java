/*
 * ANC3 - Projet d'analyse et de conception - EPFC 2015-2016
 * Groupe 3 - Paulo Pereira de Macedo, Alexis Dardenne
 */
package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import model.Rack;

/**
 *
 * @author Alex
 */
public class UsableLetters extends Observable {
    private final List<LetterShape> list = new ArrayList<>();
    
    public List<LetterShape> getList(){
        return list;
    }
    
    public void setList(Rack r){
        list.clear();
        for(int i=0; i<r.size(); ++i){
            LetterShape ls = new LetterShape(getLetterShapePosX(i), getLetterShapePosY(), r.getLetter(i), ScrabbleCanvas.getCaseSize());
            add(ls);
        }
        setChanged();
        notifyObservers();
    }
    
    private int getLetterShapePosX(int i){
        return ScrabbleCanvas.getRackPlacePosX(i);
    }
    
    private int getLetterShapePosY(){
        return ScrabbleCanvas.getRackPlacePosY();
    }
    
    public void add(LetterShape ls){
        list.add(ls);
    }
    
    public void remove(LetterShape ls){
        list.remove(ls);
    }
}
