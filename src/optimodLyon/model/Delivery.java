package optimodLyon.model;

/**
 * Classe qui définit un point de livraison.
 *
 * @author Yolann Gauvin
 * @since 1.0
 */
public class Delivery extends Waypoint{

    /**
     * La requêtre associée au point de livraison
     */
    private Request request;

    /**
     * Constructeur de la classe Delivery
     * @param intersection L'intersection qui définit l'emplacement du point de livraison
     * @param duration La durée de lvraison
     * @param request La requête associée au point de livraison
     */
    public Delivery(Intersection intersection, int duration, Request request) {
        super(intersection, duration);
        this.request = request;
    }

    /**
     * @return La requête associé au point de livraison
     */
    public Request getRequest() {
        return this.request;
    }
}
