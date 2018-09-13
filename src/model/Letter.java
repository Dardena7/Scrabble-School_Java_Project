/*
 * ANC3 - Projet d'analyse et de conception - EPFC 2015-2016
 * Groupe 3 - Paulo Pereira de Macedo, Alexis Dardenne
 */
package model;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Alex
 */
public class Letter {
    private final char character;
    private final int value;
        
    public Letter(char character){
        this.character = character;
        this.value = letterValues.get(character);
    }
    
    public char getCharacter(){
        return this.character;
    }
    
    public int getValue(){
        return this.value;
    }
    
    @Override
    public String toString() {
        return " " + character + " ";
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof Letter){
            Letter l = (Letter) o;
            return this.character == l.character && this.value == l.value;
        }
        else
            return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.character;
        hash = 23 * hash + this.value;
        return hash;
    }
    
    //map containing all characters and their associated value
    private static final Map<Character,Integer> letterValues = new TreeMap<Character,Integer>() {
        {
            put('A',1);
            put('B',3);
            put('C',3);
            put('D',2);
            put('E',1);
            put('F',4);
            put('G',2);
            put('H',4);
            put('I',1);
            put('J',8);
            put('K',10);
            put('L',1);
            put('M',2);
            put('N',1);
            put('O',1);
            put('P',3);
            put('Q',8);
            put('R',1);
            put('S',1);
            put('T',1);
            put('U',1);
            put('V',4);
            put('W',10);
            put('X',10);
            put('Y',10);
            put('Z',10);
            put('*',0);
        }
        
    };
}
