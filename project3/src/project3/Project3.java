package project3;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author carlos
 */
public class Project3 {

    private static DirectedGraph<Viaje, Arista> Grafo;

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
        paintGraph();
        System.out.println("--------------------CIUDADES--------------------");
        System.out.println("1. Tegucigalpa\n2. Miami\n3. Cancun\n4. Atlanta\n5. New York\n6. Baltimore\n7. San Pedro Sula\n8. El Salvador");
        System.out.println("9. Roatán\n10. Managua\n11. Ciudad de México\n12. Panamá\n13. Bogotá\n14. Lima\n15. Philadelphia\n16. Los Angeles");
        System.out.println("17. Seattle\n18. Dallas\n19. Madrid\n20. Honolulu\n21. Berlin\n22. Tokyo\n23. Napoli\n24. Amsterdam");
    }

    private static void fillGraph() throws FileNotFoundException {
        File Archivo = new File("./Data/Trips.txt");
        Scanner E = new Scanner(Archivo);
        while (E.hasNext()) {
            String[] NewLine = E.nextLine().split(";");
            Viaje R1 = new Viaje(NewLine[0], "Test");
            Viaje R2 = new Viaje(NewLine[1], "Test");

            Viaje Vertex1 = checkNode(R1);
            Viaje Vertex2 = checkNode(R2);

            if (Vertex1 == null) {
                Grafo.addVertex(R1);
                Vertex1 = R1;
            }
            if (Vertex2 == null) {
                Grafo.addVertex(R2);
                Vertex2 = R2;
            }

            Arista Edge = new Arista(Grafo.getEdgeCount() + 1, Double.parseDouble(NewLine[2]));

            Grafo.addEdge(Edge, Vertex1, Vertex2);
        }

    }

    private static void paintGraph() {
        //SimpleGraphView2 sgv = new SimpleGraphView2(); // This builds the graph
// Layout<V, E>, BasicVisualizationServer<V,E>
        Layout<Viaje, Arista> layout = new CircleLayout(Grafo);
        layout.setSize(new Dimension(650, 650));
        BasicVisualizationServer<Viaje, Arista> vv =
                new BasicVisualizationServer<>(layout);
        vv.setPreferredSize(new Dimension(700, 700));
// Setup up a new vertex to paint transformer...
        Transformer<Viaje, Paint> vertexPaint = new Transformer<Viaje, Paint>() {
            @Override
            public Paint transform(Viaje i) {
                return Color.GREEN;
            }
        };
// Set up a new stroke Transformer for the edges
        float dash[] = {10.0f};
        final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
        Transformer<Arista, Stroke> edgeStrokeTransformer =
                new Transformer<Arista, Stroke>() {
                    public Stroke transform(Arista s) {
                        return edgeStroke;
                    }
                };
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
        JFrame frame = new JFrame("Simple Graph View 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }

    private static Viaje checkNode(Viaje R) {
        Viaje[] arrayViaje = Grafo.getVertices().toArray(new Viaje[0]);
        for (int i = 0; i < arrayViaje.length; i++) {
            if (arrayViaje[i].equals(R)) {
                return arrayViaje[i];
            }
        }
        return null;
    }
}
