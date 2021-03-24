package optimodLyon.model.circuit;

import external.circuitPlanner.CircuitPlanner1;
import optimodLyon.model.*;
import java.util.List;



/**
 *
 */
public class CircuitManager
{
    public Circuit getGraph() {
        return circuit;
    }

    public CityMap getCityMap() {
        return cityMap;
    }

    private Circuit circuit;
    private CityMap cityMap;

    public CircuitManager(Circuit graph, CityMap cityMap) {
        this.circuit = graph;
        this.cityMap = cityMap;
    }

    public void addRequest(Request request, int beforePickup, int beforeDelivery){
        Delivery delivery = request.getDelivery();
        Pickup pickup = request.getPickup();

        List<Waypoint> waypoints = this.circuit.getwaypoints();
        waypoints.add(pickup);
        waypoints.add(delivery);

        List<Edge> edges = this.circuit.getedges();
        List<Segment> path = CircuitPlanner1.getShortestPath(this.cityMap, pickup, delivery);
        int distance = this.getDistance(path);
        Edge newEdge = new Edge(path, distance, pickup, delivery);
        edges.add(newEdge);
        waypoints.add(delivery);
        waypoints.add(pickup);
        this.circuit.setEdges(edges);
        this.circuit.setWaypoints(waypoints);
    }
    public List<Segment> deleteRequest(Request request){
        return null;
    }
    public List<Segment> updateRequest(Request oldRequest, Request newRequest){
        return null;
    }
    public List<List<Segment>> getSolution(){
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
