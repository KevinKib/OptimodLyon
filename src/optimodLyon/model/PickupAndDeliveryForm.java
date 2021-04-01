package optimodLyon.model;

/**
 * Modèle utilisé lors de l'ajout d'un point de Pickup & Delivery
 *
 * @author Oumar Diakhaby
 * @since 1.0
 */
public class PickupAndDeliveryForm {

    /**
     * Le segment qui définit la première rue définissant le point de pickup
     */
    private Segment pickupFirstWay;

    /**
     * Le segment qui définit la deyuxième rue définissant le point de pickup
     */
    private Segment pickupSecondWay;

    /**
     * la durée de récupération d'un colis
     */
    private int pickupDuration;

    /**
     * Le segment qui définit la première rue définissant le point de delivery
     */
    private Segment deliveryFirstWay;

    /**
     * Le segment qui définit la deuxième rue définissant le point de delivery
     */
    private Segment deliverySecondWay;

    /**
     * La durée de livraison
     */
    private int deliveryDuration;

    /**
     * Constructeur par défaut de la classe PickupAndDeliveryForm
     */
    public PickupAndDeliveryForm()
    {
        this.deliveryDuration = 0;
        this.pickupDuration = 0;

        this.pickupFirstWay = null;
        this.deliveryFirstWay = null;

        this.pickupSecondWay = null;
        this.deliverySecondWay = null;
    }

    /**
     * @return Le segment qui définit la première rue définissant le point de pickup
     */
    public Segment getPickupFirstWay() {
        return pickupFirstWay;
    }

    /**
     * Met à jour le segment qui définit la première rue définissant le point de pickup
     * @param pickupFirstWay Le nouveau segment
     */
    public void setPickupFirstWay(Segment pickupFirstWay) {
        this.pickupFirstWay = pickupFirstWay;
    }

    /**
     * @return Le segment qui définit la deyuxième rue définissant le point de pickup
     */
    public Segment getPickupSecondWay() {
        return pickupSecondWay;
    }

    /**
     * Met à jour le segment qui définit la deyuxième rue définissant le point de pickup
     * @param pickupSecondWay Le nouveau segment
     */
    public void setPickupSecondWay(Segment pickupSecondWay) {
        this.pickupSecondWay = pickupSecondWay;
    }

    /**
     * @return Le segment qui définit la première rue définissant le point de delivery
     */
    public Segment getDeliveryFirstWay() {
        return deliveryFirstWay;
    }

    /**
     * @return Le segment qui définit la deuxième rue définissant le point de delivery
     */
    public Segment getDeliverySecondWay() {
        return deliverySecondWay;
    }

    /**
     * Met à jour le segment qui définit la deuxième rue définissant le point de delivery
     * @param deliverySecondWay Le nouveau segment
     */
    public void setDeliverySecondWay(Segment deliverySecondWay) {
        this.deliverySecondWay = deliverySecondWay;
    }

    /**
     * @return La durée de récupération du colis
     */
    public int getPickupDuration() {
        return pickupDuration;
    }

    /**
     * Met à jour la durée de récupération du colis
     * @param pDuration La nouvelle durée
     */
    public void setPickupDuration(int pDuration) {
        this.pickupDuration = pDuration;
    }

    /**
     * Met à jour le segment qui définit la première rue définissant le point de delivery
     * @param deliveryFirstWay Le nouveau segment
     */
    public void setDeliveryFirstWay(Segment deliveryFirstWay) {
        this.deliveryFirstWay = deliveryFirstWay;
    }

    /**
     * @return La durée de livraison
     */
    public int getDeliveryDuration() {
        return deliveryDuration;
    }

    /**
     * Met à jour la durée de livraison
     * @param dDuration La nouvelle durée
     */
    public void setDeliveryDuration(int dDuration) {
        this.deliveryDuration = dDuration;
    }

    /**
     * @return true si le formulare est valide (toutes les informations nécessaire pour créer un nouveau point de Pickup & Delivery
     * sont définis), sinon false
     */
    public boolean isValid()
    {
        return this.deliveryFirstWay != null
                && this.deliverySecondWay != null
                && this.pickupFirstWay != null
                && this.pickupSecondWay != null;
    }

    @Override
    public String toString() {
        return "PickupAndDeliveryForm{" +
                "pVoie1='" + pickupFirstWay + '\'' +
                ", pVoie2='" + pickupSecondWay + '\'' +
                ", pDuration=" + pickupDuration +
                ", dVoie1='" + deliveryFirstWay + '\'' +
                ", dVoie2='" + deliverySecondWay + '\'' +
                ", dDuration=" + deliveryDuration +
                '}';
    }
}
