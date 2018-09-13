/*
 * ANC3 - Projet d'analyse et de conception - EPFC 2015-2016
 * Groupe 3 - Paulo Pereira de Macedo, Alexis Dardenne
 */
package model;

/**
 *
 * @author Paulo
 */
public abstract class Case {
    
    private Letter letter;
    private Coordinates coordinates;
    
    public Letter getLetter() {
        return this.letter;
    }
    public void setLetter(Letter l) {
        this.letter = l;
    }
    
    public Coordinates getCoordinates() {
        return this.coordinates;
    }
    
    public int getX(){
        return coordinates.getX();
    }
    
    public int getY(){
        return coordinates.getY();
    }
    
    public void setCoordinates(Coordinates c) {
        this.coordinates = c;
    }
    public void setCoordinates(int x, int y) {
        this.coordinates.setX(x);
        this.coordinates.setY(y);
    }
    
    
    //returns true if current case has no letter assigned
    public boolean isEmpty() { 
        return this.letter == null;
    }
    
    public abstract int getMultiplier(); //returns the numerical value of the multiplier
}
