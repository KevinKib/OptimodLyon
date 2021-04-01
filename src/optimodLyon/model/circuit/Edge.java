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

    /**
     * Le premier noeud lié au Edge
     */
    private Node first;

    /**
     * Le second noeud lié au Edge
     */
    private Node second;

    /**
     * La liste des segments représentés par le Edge
     */
    private List<Segment> path;

    /**
     * La distance séparant la première intersection du premier segment à la dernière intersection du dernier Segment
     */
    private final float length;

    /**
     * Constructeur d'un Edge
     * @param path, la liste des segments représentés par le Edge
     * @param length, la distance représentée par le Edge
     * @param first, le premier noeud lié au Edge
     * @param second, le second noeud lié au Edge
     */
    public Edge(List<Segment> path, float length, Node first, Node second) {
        this.path = path;
        this.length = length;
        this.first = first;
        this.second = second;
    }

    /**
     * Retourne le premier noeud lié au Edge
     * @return Node, le noeud correspondant
     */
    public Node getFirst() {
        return first;
    }

    /**
     * Change le premier noeud lié au Edge
     * @param first, le noeud remplaçant
     */
    public void setFirst(Node first) {
        this.first = first;
    }

    /**
     * Retourne le second noeud lié au Edge
     * @return Node, le noeud correspondant
     */
    public Node getSecond() {
        return second;
    }

    /**
     * Change le second noeud lié au Edge
     * @param second, le noeud remplaçant
     */
    public void setSecond(Node second) {
        this.second = second;
    }

    /**
     * Retourne la liste des segments représentés par le Edge
     * @return List<Segment>, la liste des segments
     */
    public List<Segment> getPath() {
        return path;
    }

    /**
     * Retourne la distance représentée par le Edge
     * @return float, la distance
     */
    public float getLength() {
        return length;
    }

}
