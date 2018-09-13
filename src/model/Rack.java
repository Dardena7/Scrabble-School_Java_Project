/*
 * ANC3 - Projet d'analyse et de conception - EPFC 2015-2016
 * Groupe 3 - Paulo Pereira de Macedo, Alexis Dardenne
 */
package model;

/**
 *
 * @author Alex
 */
public class Rack extends LetterContainer {
    
    @Override
    public String toString(){
        int i = 0;
        String result = "Rack : ";
        while(i<size()) {
            result += getLetter(i).getCharacter() + " ";
            ++i;
        }
        return result;
    }
}
