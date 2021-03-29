package external.circuitPlanner;

import optimodLyon.model.Node;
import optimodLyon.model.Segment;
import optimodLyon.model.Waypoint;
import optimodLyon.model.circuit.Edge;
import optimodLyon.model.circuit.Graph;

import java.util.*;


public class CircuitPlanner1 extends AbstractCircuitPlanner{

    private AlgorithmeVoyageurCommerce voyageurCommerce;

    public CircuitPlanner1() {
        this.voyageurCommerce = new AlgorithmeAleatoire();
    }

    //TODO Need to test the method
    public List<Segment> getShortestPath(Graph cityMapGraph, Waypoint pointA, Waypoint pointB){
        System.out.println("start to compute shortest between " + pointA.getIntersection().getId() + " and " + pointB.getIntersection().getId());
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
                List<Segment> segments = cityMapGraph.getPath(path);
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
        List<List<Segment>> pathForAllCycles = new ArrayList<>();
        List<Segment> pathForOneCycle = new ArrayList<>();
        List<Node> nodes = circuit.getNodes();

        for (int i=0; i<cycleNumber; i++){
            for(int j=0; j<nodes.size()-1;j++){
                Edge e = circuit.getEdgeByNodes(nodes.get(j), nodes.get(j+1));

                pathForOneCycle = e.getPath();
                pathForAllCycles.add(pathForOneCycle);
            }
        }

        Graph result = this.voyageurCommerce.calculate(circuit);

        return pathForAllCycles;
    }

}
