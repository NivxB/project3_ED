package project3;

import edu.uci.ics.jung.algorithms.layout.BalloonLayout;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout2;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
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
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
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
    private static double Trip = 0.0;

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
        //paintGraph();
        //System.out.println(Grafo.get

        Viaje Start = Grafo.getVertices().toArray(new Viaje[0])[5];
        Viaje Dest1 = Grafo.getVertices().toArray(new Viaje[0])[1];
        Viaje Dest2 = Grafo.getVertices().toArray(new Viaje[0])[8];

        Start.setVisited(true);

        getShortestPath(Start, Dest1, Dest2);
        System.out.println(Trip);

        System.out.println("--------------------CIUDADES--------------------");
        System.out.println("1. Tegucigalpa\n2. Miami\n3. Cancun\n4. Atlanta\n5. New York\n6. Baltimore\n7. San Pedro Sula\n8. El Salvador");
        System.out.println("9. Roatán\n10. Managua\n11. Ciudad de México\n12. Panamá\n13. Bogotá\n14. Lima\n15. Philadelphia\n16. Los Angeles");
        System.out.println("17. Seattle\n18. Dallas\n19. Madrid\n20. Honolulu\n21. Berlin\n22. Tokyo\n23. Napoli\n24. Amsterdam\n25. Wichita");
    }

    private static void getShortestPath(Viaje Start, Viaje... Destiny) {
        System.out.println("Enter getShortestPath");

        int i = 0;
        while (!CheckVisited(Destiny)) {

            System.out.println("Enter While CheckVisited");

            Viaje StartNode = Destiny[i];
            while (!StartNode.equals(Start)) {

                System.out.println("Enter While !StartNode");

                Arista[] InEdges = Grafo.getInEdges(StartNode).toArray(new Arista[0]);
                if (InEdges.length == 0 || StartNode.isVisited()) {
                    break;
                }
                StartNode.setVisited(true);
                StartNode = getShortestNode(ArraytoArrayList(InEdges), Destiny, i);


            }
            ResetVisitWeight();
            i++;
            
            if (i >= Destiny.length){
                break;
            }
        }
    }

    private static void ResetVisitWeight() {
        for (int i = 0; i < Grafo.getVertices().size(); i++) {
            Grafo.getVertices().toArray(new Viaje[0])[i].setVisited(false);
        }
        Trip = 0.0;
    }

    private static ArrayList<Arista> ArraytoArrayList(Arista[] A) {
        ArrayList<Arista> retVal = new ArrayList<>();

        for (int i = 0; i < A.length; i++) {
            retVal.add(A[i]);
        }

        return retVal;
    }

    private static boolean CheckVisited(Viaje[] Destiny) {
        for (int i = 0; i < Destiny.length; i++) {
            if (!Destiny[i].isVisited()) {
                return false;
            }
        }

        return true;
    }

    private static Viaje getShortestNode(ArrayList<Arista> SendEdges, Viaje[] TravelTo, int Actual) {
        ArrayList<Viaje> sendNode = new ArrayList<>();
        for (int i = 0; i < SendEdges.size(); i++) {
            //REVISAR
            //Creo que te retornaria hacia donde va el Edge y no de donde parte
            //Y se ocupa de donde parte.
            sendNode.add(Grafo.getSource(SendEdges.get(i)));
        }

        for (int i = 0; i < TravelTo.length; i++) {
            if (i == Actual) {
                continue;
            }
            for (int j = 0; j < sendNode.size(); j++) {
                if (TravelTo[i].equals(sendNode.get(j))) {
                    Trip += SendEdges.get(j).getPeso();
                    return sendNode.get(j);
                }
            }


        }

        double weigth = SendEdges.get(0).getPeso();
        int Position = 0;
        for (int i = 1; i < SendEdges.size(); i++) {
            if (weigth > SendEdges.get(i).getPeso()) {
                weigth = SendEdges.get(i).getPeso();
                Position = i;
            }
        }

        Trip += weigth;
        return sendNode.get(Position);

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
        Layout<Viaje, Arista> layout = new FRLayout(Grafo);
        layout.setSize(new Dimension(650, 650));
        BasicVisualizationServer<Viaje, Arista> vv =
                new BasicVisualizationServer<>(layout);
        vv.setPreferredSize(new Dimension(700, 700));
// Setup up a new vertex to paint transformer...
        Transformer<Viaje, Paint> vertexPaint = new Transformer<Viaje, Paint>() {
            @Override
            public Paint transform(Viaje i) {
                if (i.getID().equals("TGU")) {
                    return Color.CYAN;
                } else {
                    return Color.GREEN;
                }
            }
        };
        Transformer<Viaje, Shape> vertexSize = new Transformer<Viaje, Shape>() {
            public Shape transform(Viaje i) {
                Ellipse2D circle = new Ellipse2D.Double(-15, -15, 30, 30);
                // in this case, the vertex is twice as large
                return circle;
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
        vv.getRenderContext().setVertexShapeTransformer(vertexSize);

        vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
        JFrame frame = new JFrame("Ciudades");
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
