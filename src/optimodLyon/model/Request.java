package optimodLyon.model;

/**
 * Classe qui représente une requête de pickup-delivery.
 * @author Dorian TERBAH
 * @since 1.0
 */
public class Request
{
    /**
     * Id de la prochaine requête
     */
    private static int REQUEST_ID = 0;
    /**
     * Id de la request
     */
    private final int id;

    private Delivery delivery;

    private Pickup pickup;


    public Request(int id, int deliveryDuration, int pickupDuration, Intersection deliveryIntersection,
                   Intersection pickupIntersection)
    {
        this.id = id;
        this.delivery = new Delivery(deliveryIntersection, deliveryDuration);
        this.pickup = new Pickup(pickupIntersection, pickupDuration);
    }

    /**
     * @return L'id de la requête
     */
    public int getId()
    {
        return id;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public Pickup getPickup() {
        return pickup;
    }

    /**
     * @return Un id de requête
     */
    public static int nextRequestId()
    {
        return REQUEST_ID++;
    }
}
