package external.circuitPlanner;

import optimodLyon.model.Node;
import optimodLyon.model.Warehouse;
import optimodLyon.model.circuit.Edge;
import optimodLyon.model.circuit.Graph;

import java.util.ArrayList;

public class AlgorithmeOptimisationLocale extends AlgorithmeVoyageurCommerce{

    public AlgorithmeOptimisationLocale() {
        super();
    }

    public Graph calculate(Graph g) {

        AlgorithmeAleatoire algorithmeAleatoire = new AlgorithmeAleatoire();
        Graph graph = algorithmeAleatoire.calculate(g);

        selectedNodes = new ArrayList<Node>(graph.getNodes());

        boolean amelioration = true;

        while(amelioration) {
            amelioration = false;

            for (Node i : selectedNodes) {
                for (Node j : selectedNodes) {
                    if (i != j && !(i instanceof Warehouse) && !(j instanceof Warehouse)) {
//                        this.swapOpt(i, j);
                    }
                }
            }
        }

        return null;
    }

    private void swapOpt() {

    }

}
