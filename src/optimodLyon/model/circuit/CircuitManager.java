package optimodLyon.model.circuit;

import external.circuitPlanner.CircuitPlanner1;
import optimodLyon.model.*;

import java.util.List;

/**
 *
 */
public class CircuitManager {}

    private Circuit circuit;

    private CityMap cityMap;

    public CircuitManager(Circuit graph, CityMap cityMap) {
        this.circuit = graph;
        this.cityMap = cityMap;
    }

    public Circuit getGraph() {
        return circuit;
    }

    public CityMap getCityMap() {
        return cityMap;
    }

    public void addRequest(Request request, Waypoint beforePickup, Waypoint beforeDelivery){
        Delivery delivery = request.getDelivery();
        Pickup pickup = request.getPickup();

        List<Waypoint> waypoints = this.circuit.getWaypoints();
        waypoints.add(pickup);
        waypoints.add(delivery);

        Edge pickupSplitEdge = this.circuit.getEdgeByFirstWaypoint(beforePickup);
        Waypoint destinationPickup = pickupSplitEdge.getSecond();

        this.circuit.removeEdge(pickupSplitEdge);
        this.circuit.addEdge(createEdge(beforePickup, pickup));
        this.circuit.addEdge(createEdge(pickup, destinationPickup);

        Edge deliverySplitEdge = this.circuit.getEdgeByFirstWaypoint(beforeDelivery);
        Waypoint destinationDelivery = deliverySplitEdge.getSecond();

        this.circuit.removeEdge(deliverySplitEdge);
        this.circuit.addEdge(createEdge(beforeDelivery, delivery));
        this.circuit.addEdge(createEdge(delivery, destinationDelivery));
    }

    public void deleteRequest(Request request){
        Delivery delivery = request.getDelivery();
        Pickup pickup = request.getPickup();

        List<Waypoint> waypoints = this.circuit.getWaypoints();
        waypoints.remove(pickup);
        waypoints.remove(delivery);

        Edge pickupGroupEdge1 = this.circuit.getEdgeBySecondWaypoint(pickup);
        Waypoint originPickup = pickupGroupEdge1.getFirst();
        Edge pickupGroupEdge2 = this.circuit.getEdgeByFirstWaypoint(pickup);
        Waypoint destinationPickup = pickupGroupEdge2.getSecond();

        this.circuit.removeEdge(pickupGroupEdge1);
        this.circuit.removeEdge(pickupGroupEdge2);
        this.circuit.addEdge(createEdge(originPickup, destinationPickup);

        Edge deliveryGroupEdge1 = this.circuit.getEdgeBySecondWaypoint(delivery);
        Waypoint originDelivery = deliveryGroupEdge1.getFirst();
        Edge deliveryGroupEdge2 = this.circuit.getEdgeByFirstWaypoint(delivery);
        Waypoint destinationDelivery = deliveryGroupEdge2.getSecond();

        this.circuit.removeEdge(deliveryGroupEdge1);
        this.circuit.removeEdge(deliveryGroupEdge2);
        this.circuit.addEdge(createEdge(originDelivery, destinationDelivery);
    }

    public void updateRequest(Request oldRequest, Request newRequest){
        Delivery delivery = oldRequest.getDelivery();
        Pickup pickup = oldRequest.getPickup();


    }

    public void updateRequest(Waypoint waypoint, int position){

    }

    public List<List<Segment>> getSolution(){
        return null;
    }

    public int getSolutionCost(){
        return 0;
    }

    public float getDistance(List<Segment> segments){
        float distance = 0f;
        for (Segment segment : segments) {
            distance += segment.getLength();
        }
        return distance;
    }

    private List<Segment> graphToSegmentList(){
        return null;
    }


    private Edge createEdge(Waypoint first, Waypoint second){
        List<Segment> path = CircuitPlanner1.getShortestPath(this.cityMap, first, second);
        int distance = this.getDistance(path);
        Edge edge = new Edge(path, distance, first, second);
        return edge;
    }

}
