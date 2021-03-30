package external.circuitPlanner;

import optimodLyon.model.Node;
import optimodLyon.model.Warehouse;
import optimodLyon.model.circuit.Graph;

import java.util.AbstractMap;
import java.util.Map;

public class AlgorithmePlusProcheVoisin extends AlgorithmeVoyageurCommerce{

    public AlgorithmePlusProcheVoisin() {
        super();
    }

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

        this.checkResultValidity(result);

        return result;
    }

}
