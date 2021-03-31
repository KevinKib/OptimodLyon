package optimodLyon.model;

public class Delivery extends Waypoint{

    private Request request;

    public Delivery(Intersection intersection, int duration, Request request) {
        super(intersection, duration);
        this.request = request;
    }

    public Request getRequest() {
        return this.request;
    }
}
