package optimodLyon.circuitPlanner;

import optimodLyon.model.circuit.Node;
import optimodLyon.model.Warehouse;
import optimodLyon.model.circuit.Graph;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Algorithme résolvant le problème du voyageur de commerce grâce à la méthode d'optimisation locale 2-Opt.
 * Utilise l'algorithme de résolution aléatoire.
 */
public class Algorithm2Opt extends AlgorithmTravellingSalesman {

    public Algorithm2Opt() {
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

        AlgorithmTravellingSalesman algoDepart = new AlgorithmRandom();
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
