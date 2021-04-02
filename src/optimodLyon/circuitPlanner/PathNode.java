package optimodLyon.circuitPlanner;

import optimodLyon.model.circuit.Node;


/**
 * Classe permettant de représenter une liste chainée de noeuds associé à un coût réel et estimé
 * Utilisé par l'algorithme A*.
 * @author Yolann GAUVIN
 * @since 1.0
 */
class PathNode implements Comparable<PathNode>{

    /**
     * Le noeud courant sur la chaine.
     */
    private final Node current;

    /**
     * Le noeud précédant le noeud courant sur la chaine.
     */
    private Node previous;

    /**
     * Le coût réel associé au chemin menant jusqu'au noeud courant de la chaine.
     */
    private double pathCost;

    /**
     * Le coût estimé permettant d'atteindre la fin de la chaine à partir du noeud courant de la chaine.
     */
    private double estimatedCost;

    /**
     * Retourne le noeud courant sur la chaine .
     * @return Node, le noeud courant sur la chaine.
     */
    public Node getCurrent() {
        return current;
    }

    /**
     * Retourne le noeud précédant le noeud courant sur la chaine.
     * @return Node, le noeud précédant le noeud courant sur la chaine.
     */
    public Node getPrevious() {
        return previous;
    }

    /**
     * Retourne le coût estimé permettant d'atteindre la fin de la chaine à partir du noeud courant de la chaine.
     * @return Double, le coût estimé.
     */
    public double getPathCost() {
        return pathCost;
    }

    /**
     * Met à jour le noeud précédant le noeud courant sur la chaine.
     * @param previous, le noeud.
     */
    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    /**
     * Met à jour le coût réel permettant d'atteindre le noeud courant.
     * @param pathCost, le coût réel.
     */
    public void setPathCost(double pathCost) {
        this.pathCost = pathCost;
    }

    /**
     * Met à jour le coût estimé permettant d'atteindre la  fin de la chaine à partir du noeud courant de la chaine.
     * @param estimatedCost, le coût estimé.
     */
    public void setEstimatedCost(double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    /**
     * Constructeur par défaut d'un path node.
     * @param current, le noeud courrant.
     */
    PathNode(Node current) {
        this(current, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    /**
     * Constructeur d'un path node donnant des informations sur le path node.
     * @param current, la noeud courrant
     * @param previous, la noeud précédant le noeud courant
     * @param pathScore, le coût réel permettant d'atteindre le noeud courrant
     * @param estimatedScore, le coût estimé permettant d'atteindre la fin de la chaine.
     */
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