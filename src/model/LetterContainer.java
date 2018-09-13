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
public abstract class LetterContainer {
    
    private final List<Letter> letterList = new ArrayList<>();
    
    //returns the list of letters contained in the container
    public List<Letter> getLetterList() {
        return letterList;
    }
    
    //returns the number of letters in the container
    public int size() {
        return this.getLetterList().size();
    }
    
    //returns true if the container is empty
    public boolean isEmpty() {
        return this.getLetterList().isEmpty();
    }
    
    //returns true if the container contains a given letter
    public boolean contains(Letter letter) {
        return letterList.contains(letter); 
    }
    
    //returns the letter at index i
    public Letter getLetter(int i) {
        return letterList.get(i);
    }
    
    //add a letter at the end of the list and returns true or false
    public boolean addLetter(Letter letter) {
        return letterList.add(letter);
    }
    
    //removes and return the letter at index i
    protected Letter removeLetter(int i) {
        return letterList.remove(i);
    }
    
    //removes the firt occurence of the given letter
    protected boolean removeLetter(Letter letter) {
        return letterList.remove(letter);
    }
    
    //return the occurence of a letter in a list of letters
    public static int letterOccurence(List<Letter> list, Letter l) {
        int cpt = 0;
        if(list.contains(l)) {
            for(int i=0; i<list.size();i++)
                if(list.get(i).equals(l)) {
                    cpt++;
                }
        }
        return cpt;
    }
}
