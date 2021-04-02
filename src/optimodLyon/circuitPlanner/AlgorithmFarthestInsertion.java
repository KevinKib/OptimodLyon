package optimodLyon.circuitPlanner;

import optimodLyon.model.Delivery;
import optimodLyon.model.circuit.Node;
import optimodLyon.model.Warehouse;
import optimodLyon.model.circuit.Graph;

import java.util.AbstractMap;
import java.util.Map;

/**
 * Algorithme ayant pour objectif de résoudre le problème du voyageur de commerce via la méthode de la
 * lointaine insertion.
 * Non utilisé car moins efficace que le 2Opt.
 */
public class AlgorithmFarthestInsertion extends AlgorithmTravellingSalesman {

    /**
     * Constructeur.
     */
    public AlgorithmFarthestInsertion() {
        super();
    }

    /**
     * Méthode qui prend en entrée un graphe complet contenant toutes les villes et leur chemin pour accéder à toutes
     * les autres villes du graphe, et retourne une solution au problème du voyageur de commerce pour aller d'une ville
     * à l'autre.
     * @param g Graphe complet.
     * @return Chemin le plus court.
     */
    public Graph calculate(Graph g) {

        this.initValidNodes(g);

        // Start with sub-graph consisting of node i only
        Warehouse warehouse = (Warehouse) g.getFirstNode();
        selectedNodes.add(warehouse);

        // Find node r such that cir is MAXIMAL and form sub-tour i-r-i.
        Map.Entry<Node, Float> entry = this.getFarthestNode(g, warehouse);
        this.addNodeToSelected(entry.getKey());

        while (selectedNodes.size() != g.getNodes().size()) {

            // Selection step
            // Given a sub-tour, find node r not in the sub-tour FARTHEST to any node j in the sub-tour;
            // i.e. with minimal crj
            Map.Entry<Node, Float> bestNodeR = new AbstractMap.SimpleEntry<Node, Float>(null, Float.MIN_VALUE);

            for (Node nodeJ : selectedNodes) {
                Map.Entry<Node, Float> nodeR = this.getFarthestNode(g, nodeJ);

                if (nodeR.getValue() > bestNodeR.getValue()) {
                    bestNodeR = nodeR;
                }
            }

            // Insertion step
            // Find the arc (i, j) in the sub-tour which minimizes cir + crj - cij.
            // Insert r between i and j.
            int startI = 1;
            if (bestNodeR.getKey() instanceof Delivery) {
                startI = selectedNodes.indexOf(((Delivery) bestNodeR.getKey()).getRequest().getPickup())+1;
            }
            int bestI = startI;
            float bestDistance = Float.MAX_VALUE;

            for (int i = startI; i <= selectedNodes.size(); ++i) {
                int j = i;
                if (j >= selectedNodes.size()) {
                    j = 0;
                }
                float distance =
                        g.getEdgeByNodes(selectedNodes.get(i-1), bestNodeR.getKey()).getLength()
                        + g.getEdgeByNodes(bestNodeR.getKey(), selectedNodes.get(j)).getLength()
                        - g.getEdgeByNodes(selectedNodes.get(i-1), selectedNodes.get(j)).getLength();

                if (distance <= bestDistance) {
                    bestDistance = distance;
                    bestI = i;
                }
            }
            this.addNodeToSelected(bestNodeR.getKey(), bestI);
        }

        this.displayResult(g);

        Graph result = new Graph(selectedNodes, g.getEdges(), g.getFirstNode());
        System.out.println(result.getLength());
        System.out.println(g.getLength());

        this.checkResultValidity(result, true);

        return result;
    }

}
