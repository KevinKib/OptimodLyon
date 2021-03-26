package external.circuitPlanner;

import optimodLyon.model.circuit.Graph;

public abstract class AlgorithmeVoyageurCommerce {

    /**
     * Méthode qui prend en entrée un graphe complet contenant toutes les villes et leur chemin pour accéder à toutes
     * les autres villes du graphe, et retourne une solution au problème du voyageur de commerce pour aller d'une ville
     * à l'autre.
     * @param g Graphe complet.
     * @return Chemin le plus court.
     */
    public abstract Graph calculate(Graph g);

}
