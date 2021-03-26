package optimodLyon.model.circuit;

import external.circuitPlanner.CircuitPlanner1;
import optimodLyon.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 *
 */
public class CircuitManager
{
    public Graph getGraph() {
        return circuit;
    }

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

    private Graph circuit;
    private CityMap cityMap;
    private Graph cityMapGraph;

    public Graph getCityMapGraph() {
        return cityMapGraph;
    }

    public void setCityMapGraph(Graph cityMapGraph) {
        this.cityMapGraph = cityMapGraph;
    }



    public CircuitManager(CityMap cityMap) {
        this.cityMap = cityMap;
        this.cityMapGraph = this.createCityMapGraph();
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
            waypoints.add(pickup);
            List<Segment> path = CircuitPlanner1.getShortestPath(this.cityMapGraph, pickup, delivery);
            int distance = this.getDistance(path);
            Edge edge = new Edge(path, distance, pickup, delivery);
            edges.add(edge);
        }
        return new Graph(waypoints, edges);
    }

    public void addRequest(Request request, int beforePickup, int beforeDelivery){
        Delivery delivery = request.getDelivery();
        Pickup pickup = request.getPickup();

        List<Node> waypoints = this.circuit.getNodes();
        waypoints.add(pickup);
        waypoints.add(delivery);

        List<Edge> edges = this.circuit.getEdges();
        List<Segment> path = CircuitPlanner1.getShortestPath(this.cityMapGraph, pickup, delivery);
        int distance = this.getDistance(path);
        Edge newEdge = new Edge(path, distance, pickup, delivery);
        edges.add(newEdge);
        waypoints.add(delivery);
        waypoints.add(pickup);
        this.circuit.setEdges(edges);
        this.circuit.setNodes(waypoints);
    }

    public List<Segment> deleteRequest(Request request){
        return null;
    }
    public List<Segment> updateRequest(Request oldRequest, Request newRequest){
        return null;
    }

    public List<List<Segment>> getSolution(DeliveryPlan plan, int cycleNumber){
        //The first step is to create the graph corresponding to the delivery plan, which is the circuit.
        this.circuit = this.createCircuit(plan);

        System.out.println(this.cityMapGraph);
        System.out.println(this.circuit);

        System.out.println(plan.getRequests());
        //CircuitPlanner1.searchSolution(this.cityMap, plan, cycleNumber);
        return null;
    }

    public int getSolutionCost(){
        return 0;
    }
    public int getDistance(List<Segment> segments){
        return 0;
    }
    private List<Segment> graphToSegmentList(){
        return null;
    }

}
