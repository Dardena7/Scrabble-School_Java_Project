/*
 * ANC3 - Projet d'analyse et de conception - EPFC 2015-2016
 * Groupe 3 - Paulo Pereira de Macedo, Alexis Dardenne
 */
package model;

/**
 *
 * @author Paulo
 */
public class WordTripleCase extends Case {
    
    public WordTripleCase(Letter l, int x, int y) {
        this.setLetter(l);
        this.setCoordinates(new Coordinates(x,y));
    }

    @Override
    public int getMultiplier() {
        return 3;
    }
    
}
