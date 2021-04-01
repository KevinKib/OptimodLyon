package optimodLyon.model.circuit;

import optimodLyon.circuitPlanner.AbstractCircuitPlanner;
import optimodLyon.circuitPlanner.CircuitPlanner1;
import optimodLyon.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe gérant un circuit
 * @author Fanny ROUVEL && Yolann GAUVIN
 * @since 1.0
 */
public class CircuitManager
{
    /**
     * Le circuit à gérer, sous la forme d'un graphe
     */
    private Graph circuit;

    /**
     * La carte sur laquelle le circuit se base
     */
    private CityMap cityMap;

    /**
     * Le graph représentant la carte
     */
    private Graph cityMapGraph;

    /**
     * L'algorithme utilisé pour calculer les circuits
     */
    private AbstractCircuitPlanner circuitPlanner;

    /**
     * La solution de l'algorithme : les circuits générés sous forme de suite de segments
     */
    private List<List<Segment>> solution;

    /**
     * Constructeur de circuit manager
     * @param cityMap, la map sur laquelle se base le circuit
     */
    public CircuitManager(CityMap cityMap) {
        this.cityMap = cityMap;
        this.cityMapGraph = this.createCityMapGraph();
        this.circuitPlanner = new CircuitPlanner1();
    }

    /**
     * Retourne la carte sur laquelle se base le circuit courant
     * @return CityMap, la carte si elle existe, null sinon
     */
    public CityMap getCityMap() {
        return cityMap;
    }

    /**
     * Met à jour la carte utilisée et crée le graphe qui la représente
     * @param cityMap, la carte
     */
    public void setCityMap(CityMap cityMap) {
        this.cityMap = cityMap;
        this.cityMapGraph = this.createCityMapGraph();
    }

    /**
     * Retourne le graphe du circuit courant
     * @return Circuit, le graphe représentant le circuit courant s'il existe, null sinon
     */
    public Graph getCircuit() {
        return circuit;
    }

    /**
     * Met à jour le graphe du circuit
     * @param circuit, le graphe
     */
    public void setCircuit(Graph circuit) {
        this.circuit = circuit;
    }

    /**
     * Crée un graphe représentant la carte CityMap
     * @return le graphe crée
     */
    public Graph createCityMapGraph(){
        List<Segment> segments = this.cityMap.getSegments();
        List<Node> nodes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        Node firstNode = null;

        for (int i = 0; i<segments.size(); ++i) {
            Segment segment = segments.get(i);
            Node origin = new Node(segment.getOrigin());
            Node destination = new Node(segment.getDestination());
            List<Segment> path = Arrays.asList(segment);
            Edge edge = new Edge(path, segment.getLength(), origin, destination);
            edges.add(edge);
            nodes.add(origin);
            nodes.add(destination);

            if(i == 0) {
                firstNode = origin;
            }
        }
        return new Graph(nodes, edges, firstNode);
    }

    /**
     * Crée un simple graphe des waypoints à partir d'un DeliveryPlan
     * @param plan, le DeliveryPlan
     * @return le graphe crée
     */
    public Graph createCircuit(DeliveryPlan plan){
        List<Request> requests = plan.getRequests();
        List<Node> waypoints = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        waypoints.add(plan.getWarehouse());

        //For each waypoint in the requests of the plan, (either Delivery or Pickup): append them to the circuit waypoint
        //And computes the edge between
        for (Request request: requests) {
            Delivery delivery = request.getDelivery();
            Pickup pickup = request.getPickup();

            waypoints.add(delivery);
            for (int i=0; i<waypoints.size()-1; i++){
                edges.add(this.createEdge((Waypoint) waypoints.get(i), delivery));
            }

            waypoints.add(pickup);
            for (int i=0; i<waypoints.size()-1; i++){
                edges.add(this.createEdge((Waypoint) waypoints.get(i), pickup));
            }
        }
        return new Graph(waypoints, edges, plan.getWarehouse());
    }

    /**
     * Retourne les circuits générés par l'algorithme sous forme de liste de liste de segments
     * @return la solution de l'algorithme
     */
    public List<List<Segment>> getSolution() {
        return solution;
    }

    /**
     * Met à jour les circuits
     * @param solution, la solution sous forme de liste de liste de segments
     */
    public void setSolution(List<List<Segment>> solution) {
        this.solution = solution;
    }

    /**
     * Calcule les circuits
     * @param plan, le DeliveryPlan contenant les points de pickup-delivery à relier par les circuits
     * @param cycleNumber, le nombre de cyclistes disponibles c'est-à-dire le nombre de circuit à créer
     */
    public void computeSolution(DeliveryPlan plan, int cycleNumber){
        //The first step is to create the graph corresponding to the delivery plan, which is the circuit.
        this.circuit = this.createCircuit(plan);
        System.out.println("Circuit has been built. Searching for solution...");
        //Then get the solution which is the ordered circuit. Taking in account the number of cycle.
        this.solution = this.circuitPlanner.searchSolution(this.circuit, cycleNumber);
    }

    /**
     * Retourne la distance totale minimale pour parcourir tous les segments
     * @return float, la distance
     */
    public float getDistance(List<Segment> segments){
        float distance = 0f;
        for (Segment segment : segments) {
            distance += segment.getLength();
        }
        return distance;
    }

    /**
     * Crée un Edge à partir de deux Waypoint
     * @param first, le premier Waypoint à relier
     * @param second, le deuxième Waypoint à relier
     * @return Edge, le Edge crée
     */
    private Edge createEdge(Waypoint first, Waypoint second){
        List<Segment> path = this.circuitPlanner.getShortestPath(this.cityMapGraph, first, second);
        float distance = this.getDistance(path);
        Edge edge = new Edge(path, distance, first, second);
        return edge;
    }
}