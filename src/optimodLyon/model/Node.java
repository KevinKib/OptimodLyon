package optimodLyon.model;

public class Node {
    private Intersection intersection;

    public Node(Intersection intersection) {
        this.intersection = intersection;
    }

    public Intersection getIntersection() {
        return intersection;
    }
    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }

    // Overriding equals() to compare two Nodes
    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }
        // typecast o to Node so that we can compare intersections
        Node node = (Node) o;
        return this.intersection.equals(node.intersection);
    }

}
