package external.circuitPlanner;

import optimodLyon.model.circuit.Node;
import optimodLyon.model.Warehouse;
import optimodLyon.model.circuit.Graph;

import java.util.ArrayList;
import java.util.Collections;

public class Algorithme2Opt extends AlgorithmeVoyageurCommerce{

    public Algorithme2Opt() {
        super();
    }

    public Graph calculate(Graph g) {

        AlgorithmeVoyageurCommerce algoDepart = new AlgorithmeAleatoire();
        Graph graph = algoDepart.calculate(g);

        selectedNodes = new ArrayList<Node>(graph.getNodes());

        boolean amelioration = true;

        while(amelioration) {
            amelioration = false;

            for (Node i : selectedNodes) {
                for (Node j : selectedNodes) {
                    if (i != j && !(i instanceof Warehouse) && !(j instanceof Warehouse)) {
                        Graph newGraph = this.simpleSwapOpt(graph, i, j);
                        if (newGraph != null && newGraph.getLength() < graph.getLength()) {
                            graph = newGraph;
                            amelioration = true;
                            System.out.println(newGraph.getLength() + " (reverse swap)");
                        }

                        Graph newGraph2 = this.swapOpt(graph, i, j);
                        if (newGraph2 != null && newGraph2.getLength() < graph.getLength()) {
                            graph = newGraph2;
                            amelioration = true;
                            System.out.println(newGraph2.getLength() + " (simple swap)");
                        }
                    }
                }
            }
        }

        System.out.println("2OPT RESULT");
        System.out.println(graph.getLength());

        return graph;
    }

    private Graph swapOpt(Graph g, Node i, Node j) {
        Graph newGraph = new Graph(g.getNodes(), g.getEdges(), g.getFirstNode());
        ArrayList<Node> newNodes = new ArrayList<>(g.getNodes());
        int indexI = newNodes.indexOf(i);
        int indexJ = newNodes.indexOf(j);
        double nbSwaps = (indexJ-indexI)/2.0;

        for(int count = 0; count < nbSwaps; ++count) {
            Collections.swap(newNodes, indexI+count, indexJ-count);
        }

        newGraph.setNodes(newNodes);
        if (!this.checkResultValidity(newGraph, false)) {
            return null;
        };

        return newGraph;
    }

    private Graph simpleSwapOpt(Graph g, Node i, Node j) {
        Graph newGraph = new Graph(g.getNodes(), g.getEdges(), g.getFirstNode());
        ArrayList<Node> newNodes = new ArrayList<>(g.getNodes());
        int indexI = newNodes.indexOf(i);
        int indexJ = newNodes.indexOf(j);

        Collections.swap(newNodes, indexI, indexJ);

        newGraph.setNodes(newNodes);
        if (!this.checkResultValidity(newGraph, false)) {
            return null;
        };

        return newGraph;
    }

}
