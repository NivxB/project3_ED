/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project3;

/**
 *
 * @author Kevin Barahona
 */
public class Viaje {
    private String ID;
    private String Aerolinea;
    private boolean Visited;
    
    

    public Viaje(String ID, String Aerolinea) {
        Visited = false;
        this.ID = ID;
        this.Aerolinea = Aerolinea;
    }

    public Viaje(){
        Visited = false;
    }

    public boolean isVisited() {
        return Visited;
    }

    public void setVisited(boolean Visited) {
        this.Visited = Visited;
    }
    
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAerolinea() {
        return Aerolinea;
    }

    public void setAerolinea(String Aerolinea) {
        this.Aerolinea = Aerolinea;
    }
    
    public boolean equals(Viaje E){
       return E.getID().equals(this.ID);       
    }

    @Override
    public String toString(){
        return ID+"";
    }
       
}
