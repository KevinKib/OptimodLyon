package optimodLyon.test.model;

import optimodLyon.model.*;
import optimodLyon.model.circuit.CircuitManager;
import optimodLyon.model.circuit.Edge;
import optimodLyon.model.circuit.Graph;
import optimodLyon.model.circuit.NoWarehouseException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class CircuitManagerTU {

    CircuitManager circuitManager;

    @Before
    public void init() {
        circuitManager = new CircuitManager(new CityMap(new ArrayList<>(), new HashMap<>()));
    }

    @Test
    public void addRequestOnCircuitTest()
    {
        Warehouse warehouse = new Warehouse(null, 0);
        Waypoint pickup1 = new Pickup(null, 2);
        Waypoint delivery1 = new Delivery(null, 2);

        List<Node> waypoints = new ArrayList<>();
        waypoints.add(warehouse);
        waypoints.add(pickup1);
        waypoints.add(delivery1);

        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(null, 0f, warehouse, pickup1));
        edges.add(new Edge(null, 0f, pickup1, delivery1));
        edges.add(new Edge(null, 0f, delivery1, warehouse));

        Graph circuit = new Graph(waypoints, edges, warehouse);
        circuitManager.setCircuit(circuit);

        Pickup pickup2 = new Pickup(null, 1);
        Delivery delivery2 = new Delivery(null, 1);

        Request request = new Request(pickup2, delivery2);
        circuitManager.addRequest(request, pickup1, delivery1);

        List<Waypoint> newWaypointList = new ArrayList<>();
        newWaypointList.add(warehouse);
        newWaypointList.add(pickup1);
        newWaypointList.add(pickup2);
        newWaypointList.add(delivery1);
        newWaypointList.add(delivery2);
        newWaypointList.add(warehouse);

        Graph newGraph = circuitManager.getGraph();
        this.assertOrder(newGraph, newWaypointList);
    }

    @Test
    public void deleteRequestFromCircuitTest()
    {
        Warehouse warehouse = new Warehouse(null, 0);
        Pickup pickup1 = new Pickup(null, 2);
        Delivery delivery1 = new Delivery(null, 2);
        Pickup pickup2 = new Pickup(null, 1);
        Delivery delivery2 = new Delivery(null, 1);

        Request request = new Request(pickup1, delivery1);

        List<Node> waypoints = new ArrayList<>();
        waypoints.add(warehouse);
        waypoints.add(pickup1);
        waypoints.add(delivery1);
        waypoints.add(pickup2);
        waypoints.add(delivery2);

        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(null, 0f, warehouse, pickup1));
        edges.add(new Edge(null, 0f, pickup1, delivery1));
        edges.add(new Edge(null, 0f, delivery1, pickup2));
        edges.add(new Edge(null, 0f, pickup2, delivery2));
        edges.add(new Edge(null, 0f, delivery2, warehouse));

        Graph circuit = new Graph(waypoints, edges, warehouse);
        circuitManager.setCircuit(circuit);
        circuitManager.deleteRequest(request);

        List<Waypoint> newWaypointList = new ArrayList<>();
        newWaypointList.add(warehouse);
        newWaypointList.add(pickup2);
        newWaypointList.add(delivery2);
        newWaypointList.add(warehouse);

        Graph newGraph = circuitManager.getGraph();
        this.assertOrder(newGraph, newWaypointList);
    }

    @Test
    public void changeCircuitOrderTest()
    {
        Warehouse warehouse = new Warehouse(null, 0);
        Waypoint pickup1 = new Pickup(null, 2);
        Waypoint delivery1 = new Delivery(null, 2);
        Waypoint pickup2 = new Pickup(null, 1);
        Waypoint delivery2 = new Delivery(null, 1);

        List<Node> waypoints = new ArrayList<>();
        waypoints.add(warehouse);
        waypoints.add(pickup1);
        waypoints.add(delivery1);
        waypoints.add(pickup2);
        waypoints.add(delivery2);

        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(null, 0f, warehouse, pickup1));
        edges.add(new Edge(null, 0f, pickup1, delivery1));
        edges.add(new Edge(null, 0f, delivery1, pickup2));
        edges.add(new Edge(null, 0f, pickup2, delivery2));
        edges.add(new Edge(null, 0f, delivery2, warehouse));

        Graph circuit = new Graph(waypoints, edges, warehouse);
        circuitManager.setCircuit(circuit);

        List<Waypoint> oldWaypointList = new ArrayList<>();
        oldWaypointList.add(warehouse);
        oldWaypointList.add(pickup1);
        oldWaypointList.add(delivery1);
        oldWaypointList.add(pickup2);
        oldWaypointList.add(delivery2);
        oldWaypointList.add(warehouse);

        List<Waypoint> newWaypointList = new ArrayList<>();
        newWaypointList.add(warehouse);
        newWaypointList.add(pickup1);
        newWaypointList.add(pickup2);
        newWaypointList.add(delivery1);
        newWaypointList.add(delivery2);
        newWaypointList.add(warehouse);

        try {
            circuitManager.updateRequest(oldWaypointList, newWaypointList);
        } catch (NoWarehouseException e) {
            e.printStackTrace();
            fail();
        }

        Graph newGraph = circuitManager.getGraph();
        this.assertOrder(newGraph, newWaypointList);

    }

    private void assertOrder(Graph graph, List<Waypoint> orderedList){
        Node node = graph.getFirstNode();

        for(int i = 0; i < orderedList.size(); ++i) {
            assertEquals(node, orderedList.get(i));
            Edge edge = graph.getEdgeByFirstNode(node);
            node = edge.getSecond();
        }
    }
}
