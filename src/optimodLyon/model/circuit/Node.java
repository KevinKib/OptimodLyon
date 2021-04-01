package optimodLyon.model.circuit;

import optimodLyon.model.Intersection;

/**
 * Classe qui représente un noeud d'un graphe
 * @author Yolann GAUVIN
 * @since 1.0
 */
public class Node {

    /**
     * L'intersection sur la carte représentée par le noeud
     */
    private Intersection intersection;

    /**
     * Constructeur d'un noeud
     * @param intersection, l'intersection représentée par le noeud
     */
    public Node(Intersection intersection) {
        this.intersection = intersection;
    }

    /**
     * Retourne l'intersection représentée par le noeud
     * @return Intersection, l'intersection
     */
    public Intersection getIntersection() {
        return intersection;
    }

    /**
     * Met à jour l'intersection représentée par le noeud
     * @param intersection, la nouvelle intersection
     */
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
        if (this.intersection == null) {
            return node.intersection == null;
        }
        return this.intersection.equals(node.intersection);
    }

}
