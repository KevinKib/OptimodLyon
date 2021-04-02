package external.circuitPlanner;

import optimodLyon.model.Node;
import optimodLyon.model.circuit.Graph;

import java.util.Random;

/**
 * Algorithme ayant pour objectif de résoudre le problème du voyageur de commerce via un choix aléatoire des noeuds.
 * Utilisé car nécessaire à l'exécution du 2Opt.
 */
public class AlgorithmRandom extends AlgorithmTravellingSalesman {

    public AlgorithmRandom() {
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
        // Graph that will be returned at the end.
        Graph result = new Graph(g.getNodes(), g.getEdges(), g.getFirstNode());

        // Object to randomize stuff
        Random r = new Random();

        // Select warehouse as first node
        selectedNodes.add(g.getFirstNode());

        // Load all valid nodes
        this.initValidNodes(g);

        // Until all nodes have been inserted
        while (selectedNodes.size() != g.getNodes().size()) {
            // Select node among all valid ones, delete node from valid nodes list
            int selectedIndex = r.nextInt(validNodes.size());
            Node selected = validNodes.get(selectedIndex);
            this.addNodeToSelected(selected);
        }

        this.displayResult(g);

        // Update the nodes order in the return graph
        result.setNodes(selectedNodes);

        System.out.println(result.getLength());
        System.out.println(g.getLength());

        this.checkResultValidity(result, true);

        return result;
    }

}
