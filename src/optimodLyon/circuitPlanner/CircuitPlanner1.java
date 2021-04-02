package optimodLyon.circuitPlanner;

import optimodLyon.model.circuit.Node;
import optimodLyon.model.Segment;
import optimodLyon.model.Waypoint;
import optimodLyon.model.circuit.Graph;

import java.util.*;

/**
 * Classe qui permet de spécifier un planificateur de circuit en précisant les algorithmes à utiliser.
 * Etends la classe AbstractCircuitPlanner.
 * @author Yolann GAUVIN
 * @since 1.0
 */
public class CircuitPlanner1 extends AbstractCircuitPlanner{

    /**
     * Algorithme d'ordonnancement du planificateur de circuit.
     */
    private AlgorithmTravellingSalesman voyageurCommerce;

    /**
     * Constructeur par défaut d'un planificateur de circuit.
     */
    public CircuitPlanner1() {
        this.voyageurCommerce = new Algorithm2Opt();
    }

    /**
     * Spécifie la méthode abstraite getShortestPath de la classe AbstractCircuitPlanner en implémentant l'algorithme
     * de résolution A*.
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
     * Spécifie la méthode abstraite searchSolution de la classe AbstractCircuitPlanner en utilisant l'algorithme
     * d'ordonnancement instancié dans le constructeur par défaut du planificateur de circuit.
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
