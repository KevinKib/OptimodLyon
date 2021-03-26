package optimodLyon.model.circuit;

import optimodLyon.model.Node;
import optimodLyon.model.Segment;

import java.util.List;

/**
 * Classe qui représente une arrète d'un graphe
 * @author Yolann GAUVIN
 * @since 1.0
 */
public class Edge {

    private List<Segment> path;
    private float length;
    private Node first;
    private Node second;

    public Edge() {

    }

    public Edge(List<Segment> path, float length, Node first, Node second) {
        this.path = path;
        this.length = length;
        this.first = first;
        this.second = second;
    }

    public Node getFirst() {
        return first;
    }

    public Node getSecond() {
        return second;
    }

    public List<Segment> getPath() {
        return path;
    }

    public float getLength() {
        return length;
    }
}
