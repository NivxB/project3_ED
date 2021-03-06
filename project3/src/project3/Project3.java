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
import java.util.LinkedList;
import java.util.List;
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
    private static DirectedGraph<Viaje, Arista> RutaIda;
    private static DirectedGraph<Viaje, Arista> RutaVuelta;
    private static ArrayList<Double> Trip;
    private static ArrayList<Viaje> Ruta;
    private static Viaje FINAL;
    private static final double DMAX = Double.NaN;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner E = new Scanner(System.in);
        Trip = new ArrayList<>();
        Ruta = new ArrayList<>();
        ArrayList<Integer> Destinos = new ArrayList<>();
        Grafo = new DirectedSparseMultigraph<>();
        try {
            fillGraph();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Project3.class.getName()).log(Level.SEVERE, null, ex);
        }
        paintGraph(Grafo);
        //fillGraph()
        //System.out.println(Grafo.get

        System.out.println(Grafo.getVertices());

        System.out.println("--------------------CIUDADES--------------------");
        System.out.println("1. Tegucigalpa\n2. Miami\n3. Cancun\n4. Atlanta\n5. New York\n6. Baltimore\n7. San Pedro Sula\n8. El Salvador");
        System.out.println("9. Roatán\n10. Managua\n11. Ciudad de México\n12. Panamá\n13. Bogotá\n14. Lima\n15. Philadelphia\n16. Los Angeles");
        System.out.println("17. Seattle\n18. Dallas\n19. Madrid\n20. Honolulu\n21. Berlin\n22. Tokyo\n23. Napoli\n24. Amsterdam\n25. Wichita");

        System.out.println("Ingrese ciudad de origen: ");
        int C1 = E.nextInt();
        System.out.println("Ingrese de Destino: ");
        int C2 = (E.nextInt());
        do {
            Destinos.add(C2);
            System.out.println("Ingrese de Destino: (0 o 26 para salir)");
            C2 = (E.nextInt());
        } while (C2 > 0 && C2 < 25);

        Viaje Origen = getViaje(getViajeID(C1));
        Viaje[] Desti = new Viaje[Destinos.size()];
        for (int i = 0; i < Desti.length; i++) {
            Desti[i] = getViaje(getViajeID(Destinos.get(i)));
        }
        //Origen.setVisited(true);
        getShortestPath(Origen, Desti);
        if (SumaTrip() != 0) {
            System.out.println("Corto Ida: " + SumaTrip());
            System.out.println(Ruta.size() + " Ruta");
            System.out.println(Trip.size() + " Trip");
            RutaIda = new DirectedSparseMultigraph<>();
//            Ruta.add(Origen);
  //          Trip.add(Grafo.findEdge(Origen, Ruta.get(Ruta.size() - 1)).getPeso());
            fillGraph(RutaIda, Ruta, Trip);
            ResetVisitWeight(Origen);
            FINAL.setVisited(true);
            getShortestPath(FINAL, Origen);
            System.out.println("Corto Vuelta: " + SumaTrip());

            System.out.println(Ruta.size() + " Ruta");
            System.out.println(Trip.size() + " Trip");
            RutaVuelta = new DirectedSparseMultigraph<>();

        //    Ruta.add(FINAL);
         //   Trip.add(Grafo.findEdge(FINAL, Ruta.get(Ruta.size() - 1)).getPeso());
            fillGraph(RutaVuelta, Ruta, Trip);

            paintGraph(RutaIda);
            paintGraph(RutaVuelta);
        } else {
            System.out.println("No puede llegar a esa ciudad");
        }


    }

    private static void fillGraph(DirectedGraph G, ArrayList<Viaje> V, ArrayList<Double> E) {
        //G = new DirectedSparseMultigraph<>();
        for (int i = V.size() - 1; i >= 1; i--) {
            // G.addVertex(V.get(i));
            Viaje V1 = V.get(i);
            Viaje V2 = V.get(i - 1);
            G.addVertex(V1);
            G.addVertex(V2);
            G.addEdge(new Arista(G.getEdgeCount() + 1, E.get(i)), V1, V2);
        }
    }

    private static String getViajeID(int R) {
        switch (R) {
            case 1:
                return "TGU";
            case 2:
                return "MIA";
            case 3:
                return "CUN";
            case 4:
                return "ATL";
            case 5:
                return "JFK";
            case 6:
                return "BWI";
            case 7:
                return "SAP";
            case 8:
                return "SAL";
            case 9:
                return "RTB";
            case 10:
                return "MGA";
            case 11:
                return "MEX";
            case 12:
                return "PTY";
            case 13:
                return "BOG";
            case 14:
                return "LIM";
            case 15:
                return "PHL";
            case 16:
                return "LAX";
            case 17:
                return "SEA";
            case 18:
                return "DFW";
            case 19:
                return "MAD";
            case 20:
                return "HNL";
            case 21:
                return "BER";
            case 22:
                return "HND";
            case 23:
                return "NAP";
            case 24:
                return "AMS";
            case 25:
                return "SPS";
            default:
                return "NULL";


        }
    }

    private static Viaje getViaje(String X) {
        Viaje[] R = Grafo.getVertices().toArray(new Viaje[0]);

        for (int i = 0; i < R.length; i++) {
            if (R[i].getID().equalsIgnoreCase(X)) {
                return R[i];
            }
        }

        return null;
    }

    private static void getShortestPath(Viaje Start, Viaje... Destiny) {
        //System.out.println("Enter getShortestPath");

        Viaje Back = null;
        int i = -1;
        while (!CheckVisited(Destiny, Start)) {
            ResetVisitWeight(Start);
            i++;
            //  System.out.println("Enter While CheckVisited");
            if (i >= Destiny.length) {
                break;
            }
            Viaje StartNode = Destiny[i];
            FINAL = StartNode;
            while (!StartNode.equals(Start)) {
                //    System.out.println("Enter While !StartNode");

                Arista[] InEdges = Grafo.getInEdges(StartNode).toArray(new Arista[0]);
                // System.out.println("InEdges");
                if (InEdges.length == 0) {
                    StartNode.setVisited(true);
                    //System.out.println(StartNode);
                    System.out.println("Back");
                    //Ruta.remove(Ruta.size() - 1);
                    System.out.println(Ruta.get(Ruta.size() - 1) + " RUTA BACK");
                    System.out.println(Trip.get(Trip.size() - 1) + " TRIP BACK");
                    Trip.remove(Trip.size() - 1);
                    StartNode = Ruta.get(Ruta.size() - 1);
                    //System.out.println(StartNode);
                    StartNode.setVisited(false);

                }
                while (true) {
                    if (StartNode.isVisited()) {
                        break;
                    }

                    StartNode.setVisited(true);
                    System.out.println(StartNode.getID() + " ANTES");
                    if (Ruta.size() >= 1) {
                        if (!StartNode.equals(Ruta.get(Ruta.size() - 1))) {
                            Ruta.add(StartNode);
                        }
                    } else {
                        Ruta.add(StartNode);
                    }


                    StartNode = getShortestNode(ArraytoArrayList(InEdges), Destiny, Start);


                    if ((StartNode) == null) {
                        System.out.println("Back");
                        System.out.println(Ruta.get(Ruta.size() - 1) + " RUTA BACK");
                        System.out.println(Trip.get(Trip.size() - 1) + " TRIP BACK");
                        Ruta.remove(Ruta.size() - 1);
                        Trip.remove(Trip.size() - 1);

                        StartNode = Ruta.get(Ruta.size() - 1);
                        StartNode.setVisited(false);

                        //System.out.println(StartNode + " ASD");
                        InEdges = Grafo.getInEdges(StartNode).toArray(new Arista[0]);
                        //System.out.println("H");
                        //InEdges = (Arista[]) removeElements(InEdges,InEdges[getShortestNodeInt(ArraytoArrayList(InEdges), Destiny, Start)]);
                    }
                    System.out.println(StartNode.getID() + " Despues");
                    if (StartNode != null) {
                        if (StartNode.equals(Start)) {
                            Start.setVisited(true);
                        }
                        break;
                    }

                }
                if (StartNode == null) {
                    break;
                }
            }

        }
    }

    private static double SumaTrip() {
        double retVal = 0;

        for (int i = 0; i < Trip.size(); i++) {
            System.out.println(Trip.get(i));
            retVal += Trip.get(i);
        }

        return retVal;
    }

    private static void ResetVisitWeight(Viaje Start) {
        for (int i = 0; i < Grafo.getVertices().size(); i++) {
            Grafo.getVertices().toArray(new Viaje[0])[i].setVisited(false);
        }
        Start.setVisited(false);
        while (!Trip.isEmpty()) {
            Trip.remove(0);
        }
        while (!Ruta.isEmpty()) {
            Ruta.remove(0);
        }
    }

    private static ArrayList<Arista> ArraytoArrayList(Arista[] A) {
        ArrayList<Arista> retVal = new ArrayList<>();

        for (int i = 0; i < A.length; i++) {
            retVal.add(A[i]);
        }

        return retVal;
    }

    private static void Matrix() {
        double[][] GrafoM = new double[Grafo.getVertexCount()][Grafo.getVertexCount()];
        for (int i = 0; i < Grafo.getVertexCount(); i++) {
            for (int j = 0; j < Grafo.getVertexCount(); j++) {
                Arista fill = Grafo.findEdge(Grafo.getVertices().toArray(new Viaje[0])[i], Grafo.getVertices().toArray(new Viaje[0])[j]);
                if (fill == null) {
                    GrafoM[i][j] = DMAX;
                } else {
                    GrafoM[i][j] = fill.getPeso();
                }
            }
        }
    }

    private static boolean CheckVisited(Viaje[] Destiny, Viaje Start) {
        if (!Start.isVisited()) {
            return false;
        }

        for (int i = 0; i < Destiny.length; i++) {
            if (!Destiny[i].isVisited()) {
                return false;
            }
        }

        return true;
    }

    private static Viaje getShortestNode(ArrayList<Arista> SendEdges, Viaje[] TravelTo, Viaje Start) {

        //System.out.println("Enter getShortestNode");

        ArrayList<Viaje> sendNode = new ArrayList<>();
        for (int i = 0; i < SendEdges.size(); i++) {

            // System.out.println("Enter For fill sendNode");
            sendNode.add(Grafo.getSource(SendEdges.get(i)));
            if (sendNode.get(i).equals(Start)) {
                Trip.add(SendEdges.get(i).getPeso());
                SendEdges.remove(i);
                return sendNode.get(i);
            }
        }

        for (int i = 0; i < TravelTo.length; i++) {

            //System.out.println("Enter for TravelTo");

            //if (i == Actual) {
            //    continue;
            //}
            for (int j = 0; j < sendNode.size(); j++) {

                // System.out.println("Enter For sendNode");

                if (TravelTo[i].equals(sendNode.get(j)) && !TravelTo[i].isVisited()) {
                    Trip.add(SendEdges.get(j).getPeso());
                    // System.out.println(sendNode.get(j).getID());
                    // System.out.println("Peso " + SendEdges.get(j).getPeso());
                    SendEdges.remove(j);
                    return sendNode.get(j);
                }

            }




        }



        double weigth = 99999999;
        //SendEdges.get(0).getPeso();
        int Position = -1;
        for (int i = 0; i < SendEdges.size(); i++) {
            if (weigth > SendEdges.get(i).getPeso() && !sendNode.get(i).isVisited()) {
                //System.out.println(sendNode.get(i).getID());
                weigth = SendEdges.get(i).getPeso();
                Position = i;
            }
        }

        if (weigth != 99999999) {
            Trip.add(weigth);
        } else {
            // Trip.add(0.0);
        }
        // System.out.println(sendNode.get(Position).getID());
        // System.out.println("Peso " + weigth);
        if (Position == -1) {
            return null;
        }
        SendEdges.remove(Position);
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

    private static void paintGraph(DirectedGraph X) {
        //SimpleGraphView2 sgv = new SimpleGraphView2(); // This builds the graph
// Layout<V, E>, BasicVisualizationServer<V,E>
        Layout<Viaje, Arista> layout = new FRLayout(X);
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
