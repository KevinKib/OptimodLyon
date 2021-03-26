package external.circuitPlanner;

import optimodLyon.model.*;
import optimodLyon.model.circuit.Graph;

import java.util.*;


public class CircuitPlanner1 extends AbstractCircuitPlanner{

    //TODO Need to test the method
    public List<Segment> getShortestPath(Graph cityMapGraph, Waypoint pointA, Waypoint pointB){
        //Should call A* to compute the shortest path between the two points
        List<Segment> segmentList = new ArrayList<>();

        // The priority queue allow to get the nodes to be visited in order given the result of the compareTo method.
        Queue<PathNode> nodesToBeVisited = new PriorityQueue<>();
        Map<Node, PathNode> visitedNodes = new HashMap<>();

        Waypoint start = pointA;
        Waypoint end = pointB;
        // Initialisation with the starting node.
        PathNode startPathNode = new PathNode(start, null, 0, cityMapGraph.computeCost(start, end));
        nodesToBeVisited.add(startPathNode);
        visitedNodes.put(start, startPathNode);

        // Iterate while we have nodes to visit or we reached the ending node with the lowest cost.
        while (!nodesToBeVisited.isEmpty()) {
            PathNode next = nodesToBeVisited.poll();
            if (next.getCurrent().equals(end)) {
                List<Node> path = new ArrayList<>();
                PathNode current = next;
                do {
                    path.add(0, current.getCurrent());
                    current = visitedNodes.get(current.getPrevious());
                } while (current != null);
                //TODO need to find a way to convert a list of node into a list of Segments.
                return path;
            }
            cityMapGraph.getConnectionsFromNode(next.getCurrent()).forEach(connection -> {
                PathNode nextNode = visitedNodes.getOrDefault(connection, new PathNode(connection));
                visitedNodes.put(connection, nextNode);

                double newCost = next.getPathCost() + cityMapGraph.computeCost(next.getCurrent(), connection);
                if (newCost < nextNode.getPathCost()) {
                    nextNode.setPrevious(next.getCurrent());
                    nextNode.setPathCost(newCost);
                    nextNode.setEstimatedCost(newCost + cityMapGraph.computeCost(connection, end));
                    nodesToBeVisited.add(nextNode);
                }
            });
        }

        return segmentList;
    }

    public List<List<Segment>> searchSolution(CityMap map, DeliveryPlan plan, int cycleNumber){

        int requestNumber = plan.getRequests().toArray().length;
        for (int i=0; i<requestNumber; i++){

        }
        return null;
    }

}
