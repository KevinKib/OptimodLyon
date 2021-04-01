package optimodLyon.circuitPlanner;

import optimodLyon.model.Segment;
import optimodLyon.model.Waypoint;
import optimodLyon.model.circuit.Graph;

import java.util.List;

/**
 * Classe représentant un algorithme permettant de générer des circuits optimaux
 * @author Yolann GAUVIN
 * @since 1.0
 */
public abstract class AbstractCircuitPlanner {

    /**
     * Lance la recherche de circuits optimaux
     * @param circuit, le graphe représentant un circuit non optimal
     * @param cycleNumber, le nombre de cyclistes disponibles
     * @return List<List<Segment>>, la liste de liste de segment représentant les circuits générés
     */
    public abstract List<List<Segment>> searchSolution(Graph circuit, int cycleNumber);

    /**
     * Calcule les segments à emprunter pour aller du point d'arrêt A au point d'arrêt B le plus rapidement
     * @param cityMapGraph, le graphe représentant la carte de la ville
     * @param pointA, le premier point d'arrêt
     * @param pointB, le deuxième point d'arrêt
     * @return List<Segment>, la liste des segments à emprunter
     */
    public abstract List<Segment> getShortestPath(Graph cityMapGraph, Waypoint pointA, Waypoint pointB);
}
