package optimodLyon.model;

/**
 * Classe représentant un point de récupération d'un colis
 *
 * @author Yolann Gauvin
 * @since 1.0
 */
public class Pickup extends Waypoint {

    /**
     * La requête associée au point de récupération d'un colis
     */
    private Request request;

    /**
     * Constructeur de la classe Pickup
     * @param intersection L'intersection qui définit l'emplacement du point de récupération du colis
     * @param duration La durée de récupération
     * @param request lLa requête associée au point de récupération d'un coils
     */
    public Pickup(Intersection intersection, int duration, Request request) {
        super(intersection, duration);
        this.request = request;
    }

    /**
     * @return La requête associée au point de récupération d'un colis
     */
    public Request getRequest() {
        return this.request;
    }
}
