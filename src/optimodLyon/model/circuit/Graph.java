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

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    private List<Node> nodes;
    private List<Edge> edges;

    public Graph(List<Node> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public void addNode(Node node){
        this.nodes.add(node);
    }

    /**
     * Retourne un edge dont le premier node est égal au node passé en paramètre
     * @param Node
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
     * @param Node
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
     * Supprime un edge du graph
     * @param edge, le edge à supprimer
     */
    public void removeEdge(Edge edge){
        this.getEdges().remove(edge);
    }

    /**
     * Ajoute un edge du graph
     * @param edge, le edge à ajouter
     */
    public void addEdge(Edge edge) {
        this.getEdges().add(edge);
    }
}
