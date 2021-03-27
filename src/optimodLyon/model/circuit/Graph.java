package optimodLyon.model.circuit;

import optimodLyon.model.Node;
import optimodLyon.model.Segment;

import java.util.ArrayList;
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
     * @param node
     * @return le edge correspondant
     */
    //TODO repenser ces fonctions que ne marchent pas si il y a plusieurs edges sur 1 node
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
    //TODO repenser ces fonctions que ne marchent pas si il y a plusieurs edges sur 1 node
    public Edge getEdgeBySecondNode(Node node) {
        for (Edge edge: getEdges()) {
            if(edge.getSecond().equals(node)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Retourne un edge dont les nodes en extrémités correpondent à ceux en paramètre
     * @param start, end
     * @return le edge correspondant
     */
    public Edge getEdgeByNodes(Node start, Node end) {
        for (Edge edge: this.edges) {
            if(edge.getFirst().equals(start) && edge.getSecond().equals(end)) {
                return edge;
            }
            else if (edge.getFirst().equals(end) && edge.getSecond().equals(start)){
                return edge;
            }
        }
        return null;
    }


    /**
     * Retourne un node dont l'id de l'intersection est égal à l'id passé en paramètre
     * @param id
     * @return le node correspondant
     */
    public Node getNodeByIntersectionID(int id) {
        for (Node node: this.nodes) {
            if(node.getIntersection().getId() == id) {
                return node;
            }
        }
        return null;
    }

    /**
     * Retourne la liste des nodes directement connectés au node passé en paramètre
     * @param node
     * @return la liste de nodes correspondant
     */
    public List<Node> getConnectionsFromNode(Node node) {
        List<Node> connections = new ArrayList<>();
        for (Edge edge: this.edges) {
            if(edge.getFirst().equals(node)) {
                connections.add(edge.getSecond());
            }
            else if(edge.getSecond().equals(node)){
                connections.add(edge.getFirst());
            }
        }
        return connections;
    }

    /**
     * Retourne le coût estimé pour naviguer entre deux node.
     * @param start, end
     * @return le coût estimé
     */
    public float computeCost(Node start, Node end) {
        float y1 = start.getIntersection().latitude;
        float y2 = end.getIntersection().latitude;
        float x1 = start.getIntersection().longitude;
        float x2 = end.getIntersection().longitude;

        return (float) Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    /**
     * Retourne le coût estimé pour naviguer entre deux node.
     * @param nodes
     * @return le chemin sous forme de la liste des segments liant les noeuds.
     */
    public List<Segment> getPath(List<Node> nodes) {
        List<Segment> segments = new ArrayList<>();
        System.out.println("nodes");
        for (int i = 0; i < nodes.size()-1; i++) {
            Edge edge = this.getEdgeByNodes(nodes.get(i), nodes.get(i+1));
            segments.addAll(edge.getPath());
            System.out.println(i);
        }
        return segments;
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
