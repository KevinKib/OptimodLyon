package optimodLyon.model.circuit;

import optimodLyon.model.Segment;
import optimodLyon.model.Waypoint;

import java.util.List;

/**
 * Classe qui représente une arrète d'un graphe
 * @author Yolann GAUVIN
 * @since 1.0
 */
public class Edge {

    private List<Segment> path;
    private int length;
    private Waypoint first;
    private Waypoint second;

    public Edge() {

    }

    public Edge(List<Segment> path, int length, Waypoint first, Waypoint second) {
        this.path = path;
        this.length = length;
        this.first = first;
        this.second = second;
    }

    public Waypoint getFirst() {
        return first;
    }

    public Waypoint getSecond() {
        return second;
    }

    public List<Segment> getPath() {
        return path;
    }

    public int getLength() {
        return length;
    }
}
