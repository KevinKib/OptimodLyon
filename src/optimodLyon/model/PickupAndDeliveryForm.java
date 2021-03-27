package optimodLyon.model;

public class PickupAndDeliveryForm {

    private Segment pickupFirstWay;
    private Segment pickupSecondWay;
    private int pDuration;

    private Segment deliveryFirstWay;
    private Segment deliverySecondWay;
    private int dDuration;

    public Segment getPickupFirstWay() {
        return pickupFirstWay;
    }

    public void setPickupFirstWay(Segment pickupFirstWay) {
        this.pickupFirstWay = pickupFirstWay;
    }

    public Segment getPickupSecondWay() {
        return pickupSecondWay;
    }

    public void setPickupSecondWay(Segment pickupSecondWay) {
        this.pickupSecondWay = pickupSecondWay;
    }

    public Segment getDeliveryFirstWay() {
        return deliveryFirstWay;
    }

    public Segment getDeliverySecondWay() {
        return deliverySecondWay;
    }

    public void setDeliverySecondWay(Segment deliverySecondWay) {
        this.deliverySecondWay = deliverySecondWay;
    }

    public PickupAndDeliveryForm()
    {
        this.dDuration = 0;
        this.pDuration = 0;

        this.pickupFirstWay = null;
        this.deliveryFirstWay = null;

        this.pickupSecondWay = null;
        this.deliverySecondWay = null;
    }

    public int getPickupDuration() {
        return pDuration;
    }

    public void setPickupDuration(int pDuration) {
        this.pDuration = pDuration;
    }

    public void setDeliveryFirstWay(Segment deliveryFirstWay) {
        this.deliveryFirstWay = deliveryFirstWay;
    }

    public int getDeliveryDuration() {
        return dDuration;
    }

    public void setDeliveryDuration(int dDuration) {
        this.dDuration = dDuration;
    }

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
                ", pDuration=" + pDuration +
                ", dVoie1='" + deliveryFirstWay + '\'' +
                ", dVoie2='" + deliverySecondWay + '\'' +
                ", dDuration=" + dDuration +
                '}';
    }


}
