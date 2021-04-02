package external.circuitPlanner;

import optimodLyon.model.Segment;
import optimodLyon.model.Waypoint;
import optimodLyon.model.circuit.Graph;

import java.util.List;

/**
 * Classe abstraite représentant la structure d'un CircuitPlanner, soit une classe qui a pour objectif de travailler
 * sur les algorithmes pour donner le plus court chemin de livraison pour un cycliste.
 */
public abstract class AbstractCircuitPlanner {

    /**
     * Retourne le trajet optimal (ou un trajet qui s'en rapproche) d'un ou de plusieurs cyclistes pour effectuer
     * sa/leur livraison.
     * @param circuit Graphe représentant la CityMap.
     * @param cycleNumber Nombre de cyclistes devant effectuer une livraison.
     * @return Liste de segments à parcourir, pour chaque cycliste.
     */
    public abstract List<List<Segment>> searchSolution(Graph circuit, int cycleNumber);

    /**
     * Renvoie le trajet optimal (ou un trajet qui s'en rapproche) d'une ville A à une ville B.
     * @param cityMapGraph Graphe représentant la CityMap.
     * @param pointA Point pickup/delivery/dépot appartenant à la CityMap duquel on veut partir.
     * @param pointB Point pickup/delivery/dépot appartenant à la CityMap auquel on veut arriver.
     * @return Liste de segments constituant le plus court chemin.
     */
    public abstract List<Segment> getShortestPath(Graph cityMapGraph, Waypoint pointA, Waypoint pointB);
}
