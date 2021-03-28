package optimodLyon.model.circuit;

import external.circuitPlanner.AbstractCircuitPlanner;
import external.circuitPlanner.CircuitPlanner1;
import optimodLyon.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Classe gérant un circuit
 * @author Fanny ROUVEL && Yolann GAUVIN
 * @since 1.0
 */
public class CircuitManager
{
    /**
     * Retourne le graph du circuit courant
     * @return Circuit, le graphe représentant le circuit courant s'il existe, null sinon
     */
    public Graph getGraph() {
        return circuit;
    }

    /**
     * Retourne la carte sur laquelle se base le circuit courant
     * @return CityMap, la carte si elle existe, null sinon
     */
    public CityMap getCityMap() {
        return cityMap;
    }

    public void setCircuit(Graph circuit) {
        this.circuit = circuit;
    }

    public void setCityMap(CityMap cityMap) {
        this.cityMap = cityMap;
        this.cityMapGraph = this.createCityMapGraph();
    }

    /**
     * Le circuit à gérer, sous la forme d'un graphe
     */
    private Graph circuit;

    /**
     * La carte sur laquelle le circuit se base
     */
    private CityMap cityMap;
    private Graph cityMapGraph;
    private AbstractCircuitPlanner circuitPlanner;

    public List<List<Segment>> getSolution() {
        return solution;
    }

    private List<List<Segment>> solution;

    public Graph getCityMapGraph() {
        return cityMapGraph;
    }

    public void setCityMapGraph(Graph cityMapGraph) {
        this.cityMapGraph = cityMapGraph;
    }



    public CircuitManager(CityMap cityMap) {
        this.cityMap = cityMap;
        this.cityMapGraph = this.createCityMapGraph();
        this.circuitPlanner = new CircuitPlanner1();
    }

    public Graph createCityMapGraph(){
        List<Segment> segments = this.cityMap.getSegments();
        List<Node> nodes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        for (Segment segment: segments) {
            Node origin = new Node(segment.getOrigin());
            Node destination = new Node(segment.getDestination());
            List<Segment> path = Arrays.asList(segment);
            Edge edge = new Edge(path, segment.getLength(), origin, destination);
            edges.add(edge);
            nodes.add(origin);
            nodes.add(destination);
        }
        return new Graph(nodes, edges);
    }

    public Graph createCircuit(DeliveryPlan plan){
        List<Request> requests = plan.getRequests();
        List<Node> waypoints = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

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
        return new Graph(waypoints, edges);
    }

    public void computeSolution(DeliveryPlan plan, int cycleNumber){
        //The first step is to create the graph corresponding to the delivery plan, which is the circuit.
        this.circuit = this.createCircuit(plan);

//        System.out.println(this.cityMapGraph);
//        System.out.println(this.circuit);
//        System.out.println(plan.getRequests());

        this.solution = this.circuitPlanner.searchSolution(this.circuit, cycleNumber);
    }

    /**
     * Ajoute un Pickup-Delivery au circuit courant s'il existe
     * @param request le Pickup-Delivery à ajouter
     * @param beforePickup le waypoint précédent le pickup
     * @param beforeDelivery le waypoint précédent la delivery
     */
    public void addRequest(Request request, Waypoint beforePickup, Waypoint beforeDelivery){
        if(this.circuit != null) {
            Delivery delivery = request.getDelivery();
            Pickup pickup = request.getPickup();

            List<Node> waypoints = this.circuit.getNodes();
            waypoints.add(pickup);
            waypoints.add(delivery);

            Edge pickupSplitEdge = this.circuit.getEdgeByFirstNode(beforePickup);
            Node destinationPickup = pickupSplitEdge.getSecond();

            this.circuit.removeEdge(pickupSplitEdge);
            this.circuit.addEdge(createEdge(beforePickup, pickup));
            this.circuit.addEdge(createEdge(pickup, (Waypoint) destinationPickup));

            Edge deliverySplitEdge = this.circuit.getEdgeByFirstNode(beforeDelivery);
            Node destinationDelivery = deliverySplitEdge.getSecond();

            this.circuit.removeEdge(deliverySplitEdge);
            this.circuit.addEdge(createEdge(beforeDelivery, delivery));
            this.circuit.addEdge(createEdge(delivery, (Waypoint) destinationDelivery));
        }
    }

    /**
     * Supprime un Pickup-Delivery au circuit courant s'il existe
     * @param request le Pickup-Delivery à supprimer
     */
    public void deleteRequest(Request request) {
        if(circuit != null) {
            Delivery delivery = request.getDelivery();
            Pickup pickup = request.getPickup();

            List<Node> waypoints = this.circuit.getNodes();
            waypoints.remove(pickup);
            waypoints.remove(delivery);

            Edge pickupGroupEdge1 = this.circuit.getEdgeBySecondNode(pickup);
            Waypoint originPickup = (Waypoint) pickupGroupEdge1.getFirst();
            Edge pickupGroupEdge2 = this.circuit.getEdgeByFirstNode(pickup);
            Waypoint destinationPickup = (Waypoint) pickupGroupEdge2.getSecond();

            this.circuit.removeEdge(pickupGroupEdge1);
            this.circuit.removeEdge(pickupGroupEdge2);
            this.circuit.addEdge(createEdge(originPickup, destinationPickup));

            Edge deliveryGroupEdge1 = this.circuit.getEdgeBySecondNode(delivery);
            Waypoint originDelivery = (Waypoint) deliveryGroupEdge1.getFirst();
            Edge deliveryGroupEdge2 = this.circuit.getEdgeByFirstNode(delivery);
            Waypoint destinationDelivery = (Waypoint) deliveryGroupEdge2.getSecond();

            this.circuit.removeEdge(deliveryGroupEdge1);
            this.circuit.removeEdge(deliveryGroupEdge2);
            this.circuit.addEdge(createEdge(originDelivery, destinationDelivery));
        }
    }

    /**
     * Met à jour les trajets vers et depuis le waypoint qui à été modifié si le circuit existe
     * @param waypoint le waypoint modifié
     */
    public void updateWaypoint(Waypoint waypoint){
        if(circuit != null) {
            Edge edge = this.circuit.getEdgeBySecondNode(waypoint);
            this.circuit.removeEdge(edge);
            this.circuit.addEdge(this.createEdge((Waypoint) edge.getFirst(), waypoint));

            edge = this.circuit.getEdgeByFirstNode(waypoint);
            this.circuit.removeEdge(edge);
            this.circuit.addEdge(this.createEdge(waypoint, (Waypoint) edge.getSecond()));
        }
    }

    /**
     * Modifie la position d'un waypoint dans le circuit courant
     * @param waypoint le waypoint
     * @param position la nouvelle position du waypoint
     */
    public void updateRequest(Waypoint waypoint, int position){

    }

    /**
     * Retourne la longueur du circuit courant
     * @return float, la longueur du circuit courant
     */
    public float getSolutionCost(){
        return this.getDistance(this.graphToSegmentList());
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
     * Retourne la liste des segments composants le circuit courant
     * @return List<Segment> La liste des segments
     */
    public List<Segment> graphToSegmentList(){
        List<Segment> segments = new ArrayList<>();

        Warehouse warehouse = this.circuit.getNodes().stream()
                .filter(waypoint -> waypoint instanceof Warehouse)
                .map(waypoint -> (Warehouse)waypoint)
                .findAny()
                .orElseThrow(NoSuchElementException::new);

        Edge currentEdge = this.circuit.getEdgeByFirstNode(warehouse);
        segments.addAll(currentEdge.getPath());
        Node destination = currentEdge.getSecond();

        while (!(destination instanceof Warehouse)) {
            currentEdge = this.circuit.getEdgeByFirstNode(destination);
            segments.addAll(currentEdge.getPath());
            destination = currentEdge.getSecond();
        }

        return segments;
    }

    /**
     * Crée un Edge
     * @return Edge, le Edge crée
     */
    private Edge createEdge(Waypoint first, Waypoint second){
        List<Segment> path = this.circuitPlanner.getShortestPath(this.cityMapGraph, first, second);
        float distance = this.getDistance(path);
        Edge edge = new Edge(path, distance, first, second);
        return edge;
    }



}