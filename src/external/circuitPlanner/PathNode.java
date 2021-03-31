package external.circuitPlanner;

import optimodLyon.model.Node;

class PathNode implements Comparable<PathNode>{
    public Node getCurrent() {
        return current;
    }
    public Node getPrevious() {
        return previous;
    }
    public double getPathCost() {
        return pathCost;
    }
    public double getEstimatedCost() {
        return estimatedCost;
    }
    public void setPrevious(Node previous) {
        this.previous = previous;
    }
    public void setPathCost(double pathCost) {
        this.pathCost = pathCost;
    }
    public void setEstimatedCost(double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    private final Node current;
    private Node previous;
    private double pathCost;
    private double estimatedCost;

    PathNode(Node current) {
        this(current, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    PathNode(Node current, Node previous, double pathScore, double estimatedScore) {
        this.current = current;
        this.previous = previous;
        this.pathCost = pathScore;
        this.estimatedCost = estimatedScore;
    }

    @Override
    public int compareTo(PathNode other) {
        if (this.estimatedCost > other.estimatedCost) {
            return 1;
        } else if (this.estimatedCost < other.estimatedCost) {
            return -1;
        } else {
            return 0;
        }
    }
}