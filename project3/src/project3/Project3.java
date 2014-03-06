
package project3;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlos
 */
public class Project3 {

    private static DirectedGraph<Viaje,Arista> Grafo;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Grafo = new DirectedSparseMultigraph<>();
        try {
            fillGraph();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Project3.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("--------------------CIUDADES--------------------");
        System.out.println("1. Tegucigalpa\n2. Miami\n3. Cancun\n4. Atlanta\n5. New York\n6. Baltimore\n7. San Pedro Sula\n8. El Salvador");
        System.out.println("9. Roatán\n10. Managua\n11. Ciudad de México\n12. Panamá\n13. Bogotá\n14. Lima\n15. Philadelphia\n16. Los Angeles");
        System.out.println("17. Seattle\n18. Dallas\n19. Madrid\n20. Honolulu\n21. Berlin\n22. Tokyo\n23. Napoli\n24. Amsterdam");
    }
    
    private static void fillGraph() throws FileNotFoundException{
            File Archivo = new File("./Data/Trips.txt");
            Scanner E = new Scanner(Archivo);
            while (E.hasNext()) {
                String[] NewLine = E.nextLine().split(";");
                Viaje R1 = new Viaje(NewLine[0],"Test");
                Viaje R2 = new Viaje(NewLine[1],"Test");
                
                Viaje Vertex1 = checkNode(R1);
                Viaje Vertex2 = checkNode(R2);
                
                if (Vertex1 == null){
                    Grafo.addVertex(R1);
                    Vertex1 = R1;
                }                 
                if (Vertex2 == null){
                    Grafo.addVertex(R2);
                    Vertex2 = R2;
                }
                
                Arista Edge = new Arista(Grafo.getEdgeCount() + 1,Double.parseDouble(NewLine[2]));                                
                
                Grafo.addEdge(Edge, Vertex1, Vertex2);
            }

    }
    
    private static Viaje checkNode(Viaje R){
        if (!Grafo.containsVertex(R))
            return null;
        Viaje[] arrayViaje = (Viaje[]) Grafo.getVertices().toArray();
        for (int i = 0; i< arrayViaje.length ;i++){
            if (arrayViaje[i].equals(R))
                return arrayViaje[i];
        }
        return null;
    }
    
    
}
