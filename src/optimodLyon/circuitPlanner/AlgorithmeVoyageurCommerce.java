package optimodLyon.circuitPlanner;

import optimodLyon.model.circuit.Node;
import optimodLyon.model.Pickup;
import optimodLyon.model.Warehouse;
import optimodLyon.model.circuit.Edge;
import optimodLyon.model.circuit.Graph;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public abstract class AlgorithmeVoyageurCommerce {

    /**
     * All nodes that can safely be selected by random
     */
    protected ArrayList<Node> validNodes;

    /**
     * All nodes that were randomly selected by the algorithm
     */
    protected ArrayList<Node> selectedNodes;

    public AlgorithmeVoyageurCommerce() {
        this.validNodes = new ArrayList<>();
        this.selectedNodes = new ArrayList<>();
    }

    /**
     * Méthode qui prend en entrée un graphe complet contenant toutes les villes et leur chemin pour accéder à toutes
     * les autres villes du graphe, et retourne une solution au problème du voyageur de commerce pour aller d'une ville
     * à l'autre.
     * @param g Graphe complet.
     * @return Chemin le plus court.
     */
    public abstract Graph calculate(Graph g);

    /**
     * Adds a new node to the list of selected nodes.
     * If node is pickup, add that node to the valid (selectable) nodes.
     * @param n Selected node.
     */
    protected void addNodeToSelected(Node n) {
        this.addNodeToSelected(n, this.selectedNodes.size());
    }

    protected void addNodeToSelected(Node n, int index) {
        validNodes.remove(n);
        selectedNodes.add(index, n);

        // If pickup, add delivery as valid node
        if (n instanceof Pickup) {
            // Get associated request, add delivery node in valid nodes list
            Pickup p = (Pickup) n;
            validNodes.add(p.getRequest().getDelivery());
        }
    }

    /**
     * Inits the list of valid nodes with all pickups.
     * @param g Graph.
     */
    protected void initValidNodes(Graph g) {
        for (Node n : g.getNodes()) {
            if (n instanceof Pickup) {
                validNodes.add(n);
            }
        }
    }

    protected Map.Entry<Node, Float> getClosestNode(Graph g, Node searched) {
        float minimumLength = Float.MAX_VALUE;
        Node bestNode = null;

        for (Node potentialClosest : validNodes) {
            if (!selectedNodes.contains(potentialClosest)) {
                Edge e = g.getEdgeByNodes(searched, potentialClosest);
                if (e.getLength() < minimumLength) {
                    bestNode = potentialClosest;
                    minimumLength = e.getLength();
                }
            }
        }

        return new AbstractMap.SimpleEntry<>(bestNode, minimumLength);
    }

    protected Map.Entry<Node, Float> getFarthestNode(Graph g, Node searched) {
        float maximumLength = Float.MIN_VALUE;
        Node bestNode = null;

        for (Node potentialFarthest : validNodes) {
            if (!selectedNodes.contains(potentialFarthest)) {
                Edge e = g.getEdgeByNodes(searched, potentialFarthest);
                if (e.getLength() > maximumLength) {
                    bestNode = potentialFarthest;
                    maximumLength = e.getLength();
                }
            }
        }

        return new AbstractMap.SimpleEntry<>(bestNode, maximumLength);
    }

    protected void displayResult(Graph g) {
        // Display nodes
        for (int i = 0; i < selectedNodes.size(); ++i) {
            int j = i+1;
            if (j == selectedNodes.size()) j = 0;
            String text = selectedNodes.get(i) + " : " + g.getEdgeByNodes(selectedNodes.get(i), selectedNodes.get(j)).getLength();
            System.out.println(text);
        }
    }

    protected boolean checkResultValidity(Graph result, boolean printResult) {
        boolean isValid = true;

        if (!(result.getFirstNode() instanceof Warehouse)) {
            isValid = false;
        }

        for (Node n : result.getNodes()) {
            if (n instanceof Pickup) {
                int pickupIndex = result.getNodes().indexOf(n);
                int deliveryIndex = result.getNodes().indexOf(((Pickup) n).getRequest().getDelivery());
                if (pickupIndex > deliveryIndex) {
                    isValid = false;
                }
            }
        }

        if (printResult) {
            if (!isValid) {
                System.err.println("Invalid result!");
            }
            else {
                System.out.println("The result is valid.");
            }
        }

        return isValid;
    }

    protected void displayPath() {
        System.out.println("Current path : ");
        for (Node selectedNode : selectedNodes) {
            System.out.println(selectedNode);
        }
    }

    protected void displayValidNodes() {
        System.out.println("Valid nodes :");
        for (Node validNode : validNodes) {
            System.out.println(validNode);
        }
    }
}
