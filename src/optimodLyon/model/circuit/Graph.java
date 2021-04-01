package optimodLyon.model.circuit;

import optimodLyon.model.Node;
import optimodLyon.model.Segment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe qui représente un graphe composé de noeuds (Node) reliés par des arcs (Edge)
 * @author Yolann GAUVIN
 * @since 1.0
 */
public class Graph {

    /**
     * La liste des noeuds contenus dans le graphe
     */
    private List<Node> nodes;

    /**
     * La liste des arcs contenus dans le graphe
     */
    private List<Edge> edges;

    /**
     * Le premier noeud du graphe
     */
    private Node firstNode;

    /**
     * Constructeur d'un graphe
     * @param nodes, la liste des noeuds du graphe
     * @param edges, la liste des arcs du graphe
     * @param firstNode, le premier noeud du graphe
     */
    public Graph(List<Node> nodes, List<Edge> edges, Node firstNode) {
        this.nodes = nodes;
        this.edges = edges;
        this.firstNode = firstNode;
    }

    /**
     * Retourne la liste des noeuds du graphe
     * @return List<Node>, les noeuds du graphe
     */
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * Met à jour les noeuds du graphe
     * @param nodes, les nouveaux noeuds du graphe
     */
    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    /**
     * Ajoute un noeud au graphe
     * @param node, le noeud à ajouter
     */
    public void addNode(Node node){
        if(this.nodes.isEmpty()) {
            this.firstNode = node;
        }
        this.nodes.add(node);
    }

    /**
     * Retourne la liste des arcs du graphe
     * @return List<Edge>, les arcs du graphe
     */
    public List<Edge> getEdges() {
        return edges;
    }

    /**
     * Retourne le premier noeud du graphe
     * @return Node, le premier noeud
     */
    public Node getFirstNode() {
        return firstNode;
    }

    /**
     * Récupère le chemin sous forme de liste de segment à partir d'une liste de noeuds.
     * @param nodes
     * @return List<Segment>, le chemin sous forme de la liste des segments liant les noeuds.
     */
    public List<Segment> getPath(List<Node> nodes) {
        List<Segment> segments = new ArrayList<>();
        for (int i = 0; i < nodes.size()-1; i++) {
            Edge edge = this.getEdgeByNodes(nodes.get(i), nodes.get(i+1));
            segments.addAll(edge.getPath());
        }
        return segments;
    }

    /**
     * Calcule la longueur du chemin du graphe.
     * @return int, la longueur du chemin.
     */
    public int getLength() {
        int length = 0;
        for(int j=0; j<nodes.size()-1;j++) {
            length += this.getEdgeByNodes(nodes.get(j), nodes.get(j+1)).getLength();
        }

        length += this.getEdgeByNodes(nodes.get(nodes.size()-1), nodes.get(0)).getLength();
        return length;
    }

    /**
     * Réordonne la liste de segments passée en paramètre
     * @param segments, la liste à réordonner
     */
    private void orderPath(List<Segment> segments){
        for (int i = 0; i < segments.size()-1; i++) {
            Segment segment = segments.get(i);
            Segment nextSegment = segments.get(i+1);
            if (!segment.getDestination().equals(nextSegment.getOrigin())) {
                if (segment.getDestination().equals(nextSegment.getDestination())) {
                    nextSegment.revertDirection();
                } else if (segment.getOrigin().equals(nextSegment.getOrigin())) {
                    segment.revertDirection();
                } else if (segment.getOrigin().equals(nextSegment.getDestination())) {
                    segment.revertDirection();
                    nextSegment.revertDirection();
                } else {
                    System.out.println("ISSUE");
                }
            }
            /*
            else{
                if(i==segments.size()-2){

                }
            }*/
        }
    }



    /**
     * Retourne la liste des noeuds directement connectés au noeud passé en paramètre
     * @param node, le noeud dont on recherche les voisins
     * @return la liste de noeuds correspondants
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
     * Retourne le coût estimé pour naviguer entre deux noeuds.
     * @param start, end
     * @return float, le coût estimé
     */
    public float computeCost(Node start, Node end) {
        float y1 = start.getIntersection().latitude;
        float y2 = end.getIntersection().latitude;
        float x1 = start.getIntersection().longitude;
        float x2 = end.getIntersection().longitude;

        return (float) Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    /**
     * Retourne un edge dont les noeuds en extrémités correpondent à ceux en paramètre
     * Réordonne l'edge si le sens initial n'est pas bon.
     * @param first, le premier noeud lié au Edge
     * @param second le second noeud lié au Edge
     * @param ordered Vrai si l'on souhaite réordonner le segment, faux sinon
     * @return Edge, le edge correspondant
     */
    public Edge getEdgeByNodes(Node first, Node second, boolean ordered) {
        for (Edge edge: this.edges) {
            if(edge.getFirst().equals(first) && edge.getSecond().equals(second)) {
                if (ordered) {
                    List<Segment> segments = edge.getPath();
                    this.orderPath(segments);
                }
                return edge;
            }
            else if (edge.getFirst().equals(second) && edge.getSecond().equals(first)){
                if (ordered) {
                    List<Segment> segments = edge.getPath();
                    Collections.reverse(segments);
                    this.orderPath(segments);
                    Node f = edge.getFirst();
                    edge.setFirst(edge.getSecond());
                    edge.setSecond(f);
                }
                return edge;
            }
        }
        return null;
    }

    /**
     * Retourne un edge dont les noeuds en extrémités correpondent à ceux en paramètre.
     * Permet d'avoir false par défault pour le paramètre ordered.
     * @param first le premier noeud lié au Edge
     * @param second le second noeud lié au Edge
     * @return le edge correspondant
     */
    public Edge getEdgeByNodes(Node first, Node second) {
        return this.getEdgeByNodes(first, second, false);
    }
}
