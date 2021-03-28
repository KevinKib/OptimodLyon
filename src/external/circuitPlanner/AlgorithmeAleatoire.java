package external.circuitPlanner;

import optimodLyon.model.Delivery;
import optimodLyon.model.Node;
import optimodLyon.model.Pickup;
import optimodLyon.model.Warehouse;
import optimodLyon.model.circuit.Graph;

import java.util.ArrayList;

/**
 * Algorithme ayant pour objectif de résoudre le problème du voyageur de commerce via un choix aléatoire des noeuds.
 */
public class AlgorithmeAleatoire extends AlgorithmeVoyageurCommerce {

    public Graph calculate(Graph g) {
        Graph result = new Graph(g.getNodes(), g.getEdges());
        ArrayList<Node> validNodes = new ArrayList<>();

        

        for (Node n : g.getNodes()) {
            System.out.println(n.getClass());
            if (n instanceof Pickup) {

            }
            else if (n instanceof Delivery) {

            }
            else if (n instanceof Warehouse) {

            }
        }

        System.out.println(result.getNodes().size());

        return g;
    }

}
