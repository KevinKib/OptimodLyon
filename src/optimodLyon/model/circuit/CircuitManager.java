package optimodLyon.model.circuit;

import external.circuitPlanner.AbstractCircuitPlanner;
import external.circuitPlanner.CircuitPlanner1;
import optimodLyon.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Classe gérant un circuit
 * @author Fanny ROUVEL
 * @since 1.0
 */
public class CircuitManager {

    /**
     * Le circuit à gérer, sous la forme d'un graphe
     */
    private Circuit circuit;

    /**
     * La carte sur laquelle le circuit se base
     */
    private CityMap cityMap;

    /**
     * Le circuit planner utilisé pour créer mettre en place le circuit
     */
    private AbstractCircuitPlanner circuitPlanner;

    public CircuitManager(CityMap cityMap) {
        this.circuitPlanner = new CircuitPlanner1();
        this.cityMap = cityMap;
    }

    /**
     * Retourne le graph du circuit courant
     * @return Circuit, le graphe représentant le circuit courant s'il existe, null sinon
     */
    public Circuit getGraph() {
        return circuit;
    }

    /**
     * Retourne la carte sur laquelle se base le circuit courant
     * @return CityMap, la carte si elle existe, null sinon
     */
    public CityMap getCityMap() {
        return cityMap;
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

            List<Waypoint> waypoints = this.circuit.getWaypoints();
            waypoints.add(pickup);
            waypoints.add(delivery);

            Edge pickupSplitEdge = this.circuit.getEdgeByFirstWaypoint(beforePickup);
            Waypoint destinationPickup = pickupSplitEdge.getSecond();

            this.circuit.removeEdge(pickupSplitEdge);
            this.circuit.addEdge(createEdge(beforePickup, pickup));
            this.circuit.addEdge(createEdge(pickup, destinationPickup));

            Edge deliverySplitEdge = this.circuit.getEdgeByFirstWaypoint(beforeDelivery);
            Waypoint destinationDelivery = deliverySplitEdge.getSecond();

            this.circuit.removeEdge(deliverySplitEdge);
            this.circuit.addEdge(createEdge(beforeDelivery, delivery));
            this.circuit.addEdge(createEdge(delivery, destinationDelivery));
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

            List<Waypoint> waypoints = this.circuit.getWaypoints();
            waypoints.remove(pickup);
            waypoints.remove(delivery);

            Edge pickupGroupEdge1 = this.circuit.getEdgeBySecondWaypoint(pickup);
            Waypoint originPickup = pickupGroupEdge1.getFirst();
            Edge pickupGroupEdge2 = this.circuit.getEdgeByFirstWaypoint(pickup);
            Waypoint destinationPickup = pickupGroupEdge2.getSecond();

            this.circuit.removeEdge(pickupGroupEdge1);
            this.circuit.removeEdge(pickupGroupEdge2);
            this.circuit.addEdge(createEdge(originPickup, destinationPickup));

            Edge deliveryGroupEdge1 = this.circuit.getEdgeBySecondWaypoint(delivery);
            Waypoint originDelivery = deliveryGroupEdge1.getFirst();
            Edge deliveryGroupEdge2 = this.circuit.getEdgeByFirstWaypoint(delivery);
            Waypoint destinationDelivery = deliveryGroupEdge2.getSecond();

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
            Edge edge = this.circuit.getEdgeBySecondWaypoint(waypoint);
            this.circuit.removeEdge(edge);
            this.circuit.addEdge(this.createEdge(edge.getFirst(), waypoint));

            edge = this.circuit.getEdgeByFirstWaypoint(waypoint);
            this.circuit.removeEdge(edge);
            this.circuit.addEdge(this.createEdge(waypoint, edge.getSecond()));
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

        Warehouse warehouse = this.circuit.getWaypoints().stream()
                .filter(waypoint -> waypoint instanceof Warehouse)
                .map(waypoint -> (Warehouse)waypoint)
                .findAny()
                .orElseThrow(NoSuchElementException::new);

        Edge currentEdge = this.circuit.getEdgeByFirstWaypoint(warehouse);
        segments.addAll(currentEdge.getPath());
        Waypoint destination = currentEdge.getSecond();

        while (!(destination instanceof Warehouse)) {
            currentEdge = this.circuit.getEdgeByFirstWaypoint(destination);
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
        List<Segment> path = this.circuitPlanner.getShortestPath(this.cityMap, first, second);
        float distance = this.getDistance(path);
        Edge edge = new Edge(path, distance, first, second);
        return edge;
    }

}
