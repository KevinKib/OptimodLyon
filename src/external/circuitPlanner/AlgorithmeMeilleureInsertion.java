package external.circuitPlanner;

import jdk.swing.interop.SwingInterOpUtils;
import optimodLyon.model.Delivery;
import optimodLyon.model.Node;
import optimodLyon.model.Warehouse;
import optimodLyon.model.circuit.Edge;
import optimodLyon.model.circuit.Graph;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AlgorithmeMeilleureInsertion extends AlgorithmeVoyageurCommerce{

    public AlgorithmeMeilleureInsertion() {
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

            System.out.println();
            System.out.println("Current path : ");
            for (Node selectedNode : selectedNodes) {
                System.out.println(selectedNode);
            }

            System.out.println("Valid nodes :");
            for (Node validNode : validNodes) {
                System.out.println(validNode);
            }

            // Selection step
            // Given a sub-tour, find node r not in the sub-tour closest to any node j in the sub-tour;
            // i.e. with minimal crj
            Map.Entry<Node, Float> bestNodeR = new AbstractMap.SimpleEntry<Node, Float>(null, Float.MAX_VALUE);

            for (Node nodeJ : selectedNodes) {
                Map.Entry<Node, Float> nodeR = this.getClosestNode(g, nodeJ);

                if (nodeR.getValue() < bestNodeR.getValue()) {
                    bestNodeR = nodeR;
                }
            }

            System.out.println("Chosen node :");
            System.out.println(bestNodeR.getKey());

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

                System.out.println(i + " : " + distance);

                if (distance <= bestDistance) {
                    bestDistance = distance;
                    bestI = i;
                }
            }
            System.out.println("BEST I : "+bestI);
            this.addNodeToSelected(bestNodeR.getKey(), bestI);
        }

        this.displayResult(g);

        Graph result = new Graph(selectedNodes, g.getEdges(), g.getFirstNode());
        System.out.println(result.getLength());
        System.out.println(g.getLength());

        this.checkResultValidity(result);

        return result;
    }

}
