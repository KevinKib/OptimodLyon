package optimodLyon.model;

/**
 * Classe qui représente une requête de pickup-delivery.
 * @author Dorian TERBAH
 * @since 1.0
 */
public class Request
{
    /**
     * Le point de livraison
     */
    private Delivery delivery;

    /**
     * Le point de récupération de la livraison
     */
    private Pickup pickup;

    /**
     * Constructeur de la classe Request
     * @param deliveryDuration le durée de livraison
     * @param pickupDuration La durée de récupération du colis
     * @param deliveryIntersection L'emplacement de la livraison
     * @param pickupIntersection L'emplacement de la récupération du colis
     */
    public Request(int deliveryDuration, int pickupDuration, Intersection deliveryIntersection,
                   Intersection pickupIntersection)
    {
        this.delivery = new Delivery(deliveryIntersection, deliveryDuration, this);
        this.pickup = new Pickup(pickupIntersection, pickupDuration, this);
    }

    /**
     * Constructeur de la classe Request
     * @param pickup Le point de pickup
     * @param delivery Le point de delivery
     */
    public Request(Pickup pickup, Delivery delivery)
    {
        this.pickup = pickup;
        this.delivery = delivery;
    }

    /**
     * @return Le point de delivery
     */
    public Delivery getDelivery() {
        return delivery;
    }

    /**
     * @return Le point de pickup
     */
    public Pickup getPickup() {
        return pickup;
    }
}