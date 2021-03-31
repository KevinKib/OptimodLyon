package optimodLyon.model.circuit;

import optimodLyon.model.Node;
import optimodLyon.model.Waypoint;

import java.util.List;

public class Graph {
    public List<Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    private List<Node> nodes;
    private List<Edge> edges;
    private Node firstNode;

    public Graph(List<Node> nodes, List<Edge> edges, Node firstNode) {
        this.nodes = nodes;
        this.edges = edges;
        this.firstNode = firstNode;
    }

    public void addNode(Node node){
        if(this.nodes.isEmpty()) {
            this.firstNode = node;
        }
        this.nodes.add(node);
    }

    /**
     * Retourne un edge dont le premier node est égal au node passé en paramètre
     * @param node
     * @return le edge correspondant
     */
    public Edge getEdgeByFirstNode(Node node) {
        for (Edge edge: getEdges()) {
            if(edge.getFirst().equals(node)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Retourne un edge dont le second node est égal au node passé en paramètre
     * @param node
     * @return le edge correspondant
     */
    public Edge getEdgeBySecondNode(Node node) {
        for (Edge edge: getEdges()) {
            if(edge.getSecond().equals(node)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Retourne un edge dont les node sont égaux aux nodes passés en paramètres
     * @param first First node link to the edge
     * @param second Second node link to the edge
     * @return le edge correspondant
     */
    public Edge getEdgeByNodes(Node first, Node second) {
        for (Edge edge: getEdges()) {
            if(edge.getFirst().equals(first) && edge.getSecond().equals(second)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Supprime un edge du graphe
     * @param edge, le edge à supprimer
     */
    public void removeEdge(Edge edge){
        this.getEdges().remove(edge);
    }

    /**
     * Ajoute un edge du graphe
     * @param edge, le edge à ajouter
     */
    public void addEdge(Edge edge) {
        this.getEdges().add(edge);
    }

    /**
     * Retourne le premier noeud du graphe
     * @return le premier noeud
     */
    public Node getFirstNode() {
        return firstNode;
    }
}
