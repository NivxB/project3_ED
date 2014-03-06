/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project3;

/**
 *
 * @author Kevin Barahona
 */
public class Arista {
    
    private int ID;
    private double Peso;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getPeso() {
        return Peso;
    }

    public void setPeso(double Peso) {
        this.Peso = Peso;
    }

    public Arista(int ID, double Peso) {
        this.ID = ID;
        this.Peso = Peso;
    }
    
    public Arista(){
        
    }
    
}
