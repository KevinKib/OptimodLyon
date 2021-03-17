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

    /**
     * Durée de la livraison
     */
    private int deliveryDuration;

    /**
     * Durée du pickup
     */
    private int pickupDuration;

    /**
     * Adresse de livraison
     */
    private int deliveryAddress;

    /**
     * Adresse de récupération de la livraison
     */
    private int pickupAddress;

    public Request(int id, int deliveryDuration, int pickupDuration, int deliveryAddress, int pickupAddress)
    {
        this.id = id;

        this.deliveryDuration = deliveryDuration;
        this.pickupDuration = pickupDuration;

        this.deliveryAddress = deliveryAddress;
        this.pickupAddress = pickupAddress;
    }

    /**
     * @return L'id de la requête
     */
    public int getId()
    {
        return id;
    }

    /**
     * @return La durée de livraison
     */
    public int getDeliveryDuration()
    {
        return deliveryDuration;
    }

    /**
     * @return La durée de récupération de la livraison
     */
    public int getPickupDuration()
    {
        return pickupDuration;
    }

    /**
     * @return L'id de l'intersection pour la livraison
     */
    public int getDeliveryAddress()
    {
        return deliveryAddress;
    }

    /**
     * Met à jour l'adresse de livraison
     * @param deliveryAddress L'id de l'adresse de livraison
     */
    public void setDeliveryAddress(int deliveryAddress)
    {
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * Met à jour l'adresse de récupération de la livraison
     * @param pickupAddress L'id de l'adresse de récupération de livraison
     */
    public void setPickupAddress(int pickupAddress)
    {
        this.pickupAddress = pickupAddress;
    }

    /**
     * @return L'id de l'intersection pour la récupération de la livraison
     */
    public int getPickupAddress()
    {
        return pickupAddress;
    }

    /**
     * @return Un id de requête
     */
    public static int nextRequestId()
    {
        return REQUEST_ID++;
    }
}
