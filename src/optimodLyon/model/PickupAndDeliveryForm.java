package optimodLyon.model;

public class PickupAndDeliveryForm {

    private Segment pVoie1;
    private Segment pVoie2;
    private int pDuration;

    private Segment dVoie1;
    private Segment dVoie2;
    private int dDuration;

    public PickupAndDeliveryForm(){

    }

    public Segment getpVoie1() {
        return pVoie1;
    }

    public void setpVoie1(Segment pVoie1) {
        this.pVoie1 = pVoie1;
    }

    public Segment getpVoie2() {
        return pVoie2;
    }

    public void setpVoie2(Segment pVoie2) {
        this.pVoie2 = pVoie2;
    }

    public int getpDuration() {
        return pDuration;
    }

    public void setpDuration(int pDuration) {
        this.pDuration = pDuration;
    }

    public Segment getdVoie1() {
        return dVoie1;
    }

    public void setdVoie1(Segment dVoie1) {
        this.dVoie1 = dVoie1;
    }

    public Segment getdVoie2() {
        return dVoie2;
    }

    public void setdVoie2(Segment dVoie2) {
        this.dVoie2 = dVoie2;
    }

    public int getdDuration() {
        return dDuration;
    }

    public void setdDuration(int dDuration) {
        this.dDuration = dDuration;
    }

    @Override
    public String toString() {
        return "PickupAndDeliveryForm{" +
                "pVoie1='" + pVoie1 + '\'' +
                ", pVoie2='" + pVoie2 + '\'' +
                ", pDuration=" + pDuration +
                ", dVoie1='" + dVoie1 + '\'' +
                ", dVoie2='" + dVoie2 + '\'' +
                ", dDuration=" + dDuration +
                '}';
    }


}
