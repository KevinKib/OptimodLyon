package optimodLyon.model;

/**
 * Classe qui définit l'entrepôt
 *
 * @author Dorian TERBAH
 * @since 1.0
 */
public class Warehouse extends Waypoint{

    /**
     * Constructeur de la classe Warehouse
     * @param intersection L'emplacement de l'entrepôt
     */
    public Warehouse(Intersection intersection) {
        super(intersection, 0);
    }
}
