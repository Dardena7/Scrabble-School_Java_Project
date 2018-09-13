/*
 * ANC3 - Projet d'analyse et de conception - EPFC 2015-2016
 * Groupe 3 - Paulo Pereira de Macedo, Alexis Dardenne
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 *
 * @author Alex
 */
public class Bag extends LetterContainer {
    
    public Bag() { 
        this.fillBag();   
    }
    
    //returns a random Letter from the bag
    public Letter getRandomLetter() {
        if(!this.isEmpty()) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(size());
            Letter letter = getLetter(randomIndex);
            return letter;
        }
        else
            throw new RuntimeException("The bag is empty");
    }
    
    public List<Letter> exchangeLetters(List<Letter> letters) {
        List<Letter> newLetters = new ArrayList<>();
        int cpt = 0;
        for(Letter l : letters) {
            this.addLetter(l);
            ++cpt;
        }
        for(int i=0;i<cpt;i++){
            newLetters.add(this.getRandomLetter());
        }
        return newLetters;
    }
    
    @Override
    public String toString(){ 
        return "Bag : " + getLetterList().toString();
    }
    
    //fills the bag with appropriate number of each Letter
    private void fillBag() {
        addLetter(new Letter('J'));
        addLetter(new Letter('K'));
        addLetter(new Letter('Q'));
        addLetter(new Letter('W'));
        addLetter(new Letter('X'));
        addLetter(new Letter('Y'));
        addLetter(new Letter('Z'));
        
        for(int i = 0; i < 2; ++i) {
            addLetter(new Letter('B'));
            addLetter(new Letter('C'));
            addLetter(new Letter('F'));
            addLetter(new Letter('G'));
            addLetter(new Letter('H'));
            addLetter(new Letter('P'));
            addLetter(new Letter('V'));
            //addLetter(new Letter('*')); Pas dans cette itération
        }
        
        for(int i = 0; i < 3; ++i) {
            addLetter(new Letter('D'));
            addLetter(new Letter('M'));
        }
        
        for(int i = 0; i < 5; ++i)
            addLetter(new Letter('L'));
        
        for(int i = 0; i < 6; ++i) {
            addLetter(new Letter('N'));
            addLetter(new Letter('O'));
            addLetter(new Letter('R'));
            addLetter(new Letter('S'));
            addLetter(new Letter('T'));
            addLetter(new Letter('U'));
        }
        
        for(int i = 0; i < 8; ++i) {
            addLetter(new Letter('I'));
        }
        
        for(int i = 0; i < 9; ++i)
            addLetter(new Letter('A'));
        
        for(int i = 0; i < 15; ++i)
            addLetter(new Letter('E'));
    }
    
}   
