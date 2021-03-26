package optimodLyon.model.circuit;

import optimodLyon.model.Waypoint;

import java.util.List;

/**
 * Classe représentant un circuit
 * @author Fanny ROUVEL
 * @since 1.0
 */
public class Circuit extends Graph {

    public Circuit(List<Waypoint> waypointList, List<Edge> edgeList) {
        super(waypointList, edgeList);
    }

    /**
     * Retourne un edge dont le premier waypoint est égal au waypoint passé en paramètre
     * @param waypoint
     * @return le edge correspondant
     */
    public Edge getEdgeByFirstWaypoint(Waypoint waypoint) {
        for (Edge edge: getEdges()) {
            if(edge.getFirst().equals(waypoint)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Retourne un edge dont le second waypoint est égal au waypoint passé en paramètre
     * @param waypoint
     * @return le edge correspondant
     */
    public Edge getEdgeBySecondWaypoint(Waypoint waypoint) {
        for (Edge edge: getEdges()) {
            if(edge.getSecond().equals(waypoint)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Supprime un edge du circuit
     * @param edge, le edge à supprimer
     */
    public void removeEdge(Edge edge){
        this.getEdges().remove(edge);
    }

    /**
     * Ajoute un edge du circuit
     * @param edge, le edge à ajoute
     */
    public void addEdge(Edge edge) {
        this.getEdges().add(edge);
    }
}
