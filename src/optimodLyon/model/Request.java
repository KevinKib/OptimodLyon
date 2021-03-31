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


    public Request(int deliveryDuration, int pickupDuration, Intersection deliveryIntersection,
                   Intersection pickupIntersection)
    {
        this.delivery = new Delivery(deliveryIntersection, deliveryDuration);
        this.pickup = new Pickup(pickupIntersection, pickupDuration);
    }

    public Request(Pickup pickup, Delivery delivery)
    {
        this.pickup = pickup;
        this.delivery = delivery;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public Pickup getPickup() {
        return pickup;
    }


}
