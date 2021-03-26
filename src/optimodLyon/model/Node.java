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
}
