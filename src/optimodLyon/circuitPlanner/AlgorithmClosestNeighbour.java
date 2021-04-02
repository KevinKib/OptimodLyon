package optimodLyon.circuitPlanner;

import optimodLyon.model.circuit.Node;
import optimodLyon.model.Warehouse;
import optimodLyon.model.circuit.Graph;

import java.util.Map;

/**
 * Algorithme ayant pour objectif de résoudre le problème du voyageur de commerce via la méthode du
 * plus proche voisin.
 * Non utilisé car moins efficace que le 2Opt.
 */
public class AlgorithmClosestNeighbour extends AlgorithmTravellingSalesman {

    /**
     * Constructeur.
     */
    public AlgorithmClosestNeighbour() {
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

        // Find node r such that cir is minimal and form sub-tour i-r-i.
        Map.Entry<Node, Float> entry = this.getClosestNode(g, warehouse);
        this.addNodeToSelected(entry.getKey());

        while (selectedNodes.size() != g.getNodes().size()) {

            System.out.println("Valid nodes :");
            for (Node validNode : validNodes) {
                System.out.println(validNode);
            }

            // Select closest node
            Node nodeJ = selectedNodes.get(selectedNodes.size()-1);
            Map.Entry<Node, Float> nodeR = this.getClosestNode(g, nodeJ);

            // Insert closest node
            this.addNodeToSelected(nodeR.getKey());
        }

        this.displayResult(g);

        Graph result = new Graph(selectedNodes, g.getEdges(), g.getFirstNode());
        System.out.println(result.getLength());
        System.out.println(g.getLength());

        this.checkResultValidity(result, true);

        return result;
    }

}
