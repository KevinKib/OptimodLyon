package optimodLyon.model;

import java.awt.*;

/**
 * Classe qui représente les coordonnées d'une requête
 * @author Dorian TERBAH
 * @since 1.0
 */
public class RequestCoordinates {

    /**
     * La requête associée
     */
    private Request request;

    /**
     * Rectangle associé à la delivery de la requête
     */
    private Rectangle deliveryBounds;

    /**
     * Rectangle associé à la delivery de la requête
     */
    private Rectangle pickupBounds;

    /**
     * Constructeur de la classe
     * @param request La requete
     */
    public RequestCoordinates(Request request, Rectangle deliveryBounds, Rectangle pickupBounds)
    {
        this.request = request;
        this.deliveryBounds = deliveryBounds;
        this.pickupBounds = pickupBounds;
    }

    public Request getRequest() {
        return request;
    }

    public Rectangle getDeliveryBounds() {
        return deliveryBounds;
    }

    public Rectangle getPickupBounds() {
        return pickupBounds;
    }
}
