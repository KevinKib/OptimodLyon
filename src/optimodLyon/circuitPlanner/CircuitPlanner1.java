package optimodLyon.circuitPlanner;

import optimodLyon.model.circuit.Node;
import optimodLyon.model.Segment;
import optimodLyon.model.Waypoint;
import optimodLyon.model.circuit.Graph;

import java.util.*;

/**
 * Classe qui a pour objectif de travailler sur les algorithmes pour donner le plus court chemin
 * de livraison pour un cycliste.
 */
public class CircuitPlanner1 extends AbstractCircuitPlanner{

    /**
     * Instance de l'algorithme de résolution du problème du voyageur de commerce.
     */
    private AlgorithmTravellingSalesman voyageurCommerce;

    /**
     * Constructeur.
     */
    public CircuitPlanner1() {
        this.voyageurCommerce = new Algorithm2Opt();
    }

    //TODO Need to test the method
    /**
     * Renvoie le trajet optimal (ou un trajet qui s'en rapproche) d'une ville A à une ville B.
     * @param cityMapGraph Graphe représentant la CityMap.
     * @param pointA Point pickup/delivery/dépot appartenant à la CityMap duquel on veut partir.
     * @param pointB Point pickup/delivery/dépot appartenant à la CityMap auquel on veut arriver.
     * @return Liste de segments constituant le plus court chemin.
     */
    public List<Segment> getShortestPath(Graph cityMapGraph, Waypoint pointA, Waypoint pointB){

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
                List<Node> nodesPath = new ArrayList<>();
                PathNode current = next;
                do {
                    nodesPath.add(0, current.getCurrent());
                    current = visitedNodes.get(current.getPrevious());
                } while (current != null);
                return cityMapGraph.getPath(nodesPath);
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

    /**
     * Retourne le trajet optimal (ou un trajet qui s'en rapproche) d'un ou de plusieurs cyclistes pour effectuer
     * sa/leur livraison.
     * @param circuit Graphe représentant la CityMap.
     * @param cycleNumber Nombre de cyclistes devant effectuer une livraison.
     * @return Liste de segments à parcourir, pour chaque cycliste.
     */
    public List<List<Segment>> searchSolution(Graph circuit, int cycleNumber){
        List<List<Segment>> pathForAllCycles = new ArrayList<>();
        List<Segment> pathForOneCycle = new ArrayList<>();

        Graph result = this.voyageurCommerce.calculate(circuit);
        result.addNode(result.getFirstNode());
        List<Node> nodes = result.getNodes();

        for (int i=0; i<cycleNumber; i++){
            for(int j=0; j<nodes.size()-1;j++){
                pathForOneCycle.addAll(result.getEdgeByNodes(nodes.get(j), nodes.get(j+1), true).getPath());
            }
            pathForAllCycles.add(pathForOneCycle);
        }
        return pathForAllCycles;
    }

}
