package external.circuitPlanner;

import optimodLyon.model.Delivery;
import optimodLyon.model.Node;
import optimodLyon.model.Pickup;
import optimodLyon.model.Warehouse;
import optimodLyon.model.circuit.Graph;

import java.util.ArrayList;
import java.util.Random;

/**
 * Algorithme ayant pour objectif de résoudre le problème du voyageur de commerce via un choix aléatoire des noeuds.
 */
public class AlgorithmeAleatoire extends AlgorithmeVoyageurCommerce {

    public Graph calculate(Graph g) {
        // Graph that will be returned at the end.
        Graph result = new Graph(g.getNodes(), g.getEdges(), g.getFirstNode());

        // All nodes that can safely be selected by random
        ArrayList<Node> validNodes = new ArrayList<>();

        // All nodes that were randomly selected by the algorithm
        ArrayList<Node> selectedNodes = new ArrayList<>();

        // Object to randomize stuff
        Random r = new Random();

        // Select warehouse as first node
        selectedNodes.add(g.getFirstNode());

        // Load all valid nodes
        for (Node n : g.getNodes()) {
            if (n instanceof Pickup) {
                validNodes.add(n);
            }
        }

        // Until all nodes have been inserted
        while (selectedNodes.size() != g.getNodes().size()) {
            // Select node among all valid ones, delete node from valid nodes list
            int selectedIndex = r.nextInt(validNodes.size());
            Node selected = validNodes.get(selectedIndex);
            validNodes.remove(selected);
            selectedNodes.add(selected);

            // If pickup, add delivery as valid node
            if (selected instanceof Pickup) {
                // Get associated request, add delivery node in valid nodes list
                Pickup p = (Pickup) selected;
                validNodes.add(p.getRequest().getDelivery());
            }

        }

        // Display nodes
//        for (int i = 0; i < selectedNodes.size(); ++i) {
//            System.out.println(selectedNodes.get(i));
//        }

        // Update the nodes order in the return graph
        result.setNodes(selectedNodes);

        System.out.println(result.getLength());
        System.out.println(g.getLength());

        return result;
    }

}
