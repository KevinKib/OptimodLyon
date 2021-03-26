package optimodLyon.model.circuit;

import optimodLyon.model.Node;

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
    public void addEdge(Edge edge){
        this.edges.add(edge);
    }
}
