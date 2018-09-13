/*
 * ANC3 - Projet d'analyse et de conception - EPFC 2015-2016
 * Groupe 3 - Paulo Pereira de Macedo, Alexis Dardenne
 */
package model;

import java.util.Comparator;

/**
 *
 * @author Paulo
 */
    public class Coordinates implements Comparator<Coordinates> {

        private int x;
        private int y;
        
        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public int getX() {
            return this.x;
        }
        public int getY() {
            return this.y;
        }
        
        public void setX(int x) {
            this.x = x;
        }
        public void setY(int y) {
            this.y = y;
        }
        
        @Override
        public boolean equals(Object o) {
            if(o instanceof Coordinates) {
                Coordinates test = (Coordinates)o;
                return test.getX() == this.x && test.getY()== this.y;
            } else {
                return false;
            }
        }
        
        public int compareTo(Coordinates c){
            int resY = this.y - c.y;
            if(resY == 0)
                return this.x - c.x;
            else
                return resY;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 53 * hash + this.x;
            hash = 53 * hash + this.y;
            return hash;
        }
        
        @Override
        public String toString() {
            return "("+x+","+y+")";
        }

        @Override
        public int compare(Coordinates t, Coordinates t1) {
            return t.compareTo(t1);
        }
    }
