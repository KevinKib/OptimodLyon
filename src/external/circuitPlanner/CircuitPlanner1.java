package external.circuitPlanner;

import optimodLyon.model.Node;
import optimodLyon.model.Segment;
import optimodLyon.model.Waypoint;
import optimodLyon.model.circuit.Edge;
import optimodLyon.model.circuit.Graph;

import java.util.*;


public class CircuitPlanner1 extends AbstractCircuitPlanner{

    //TODO Need to test the method
    public List<Segment> getShortestPath(Graph cityMapGraph, Waypoint pointA, Waypoint pointB){
        System.out.println("start to compute shortest path");
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
                System.out.println("found shortest path, ready to convert it into segment list");
                long startTime = System.nanoTime();
                List<Segment> segments = cityMapGraph.getPath(path);
                long endTime = System.nanoTime();

                long duration = (endTime - startTime);
                System.out.println("duration");
                return segments;
            }
            List<Node> nodes = cityMapGraph.getConnectionsFromNode(next.getCurrent());
            nodes.forEach(connection -> {
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
        return null;
    }

    public List<List<Segment>> searchSolution(Graph circuit, int cycleNumber){
        List<List<Segment>> pathForAllCycle = new ArrayList<>();
        List<Segment> pathForOneCycle = new ArrayList<>();
        for (int i=0; i<cycleNumber; i++){
            for(Edge edge : circuit.getEdges()){
                pathForOneCycle = edge.getPath();
            }
            pathForAllCycle.add(pathForOneCycle);
        }
        return pathForAllCycle;
    }

}
