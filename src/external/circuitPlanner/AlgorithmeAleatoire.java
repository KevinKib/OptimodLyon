package external.circuitPlanner;

import optimodLyon.model.Node;
import optimodLyon.model.Pickup;
import optimodLyon.model.circuit.Graph;

import java.util.Random;

/**
 * Algorithme ayant pour objectif de résoudre le problème du voyageur de commerce via un choix aléatoire des noeuds.
 */
public class AlgorithmeAleatoire extends AlgorithmeVoyageurCommerce {

    public AlgorithmeAleatoire() {
        super();
    }

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
