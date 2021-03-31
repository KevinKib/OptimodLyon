package optimodLyon.model;

public class Pickup extends Waypoint {

    private Request request;

    public Pickup(Intersection intersection, int duration, Request request) {
        super(intersection, duration);
        this.request = request;
    }

    public Request getRequest() {
        return this.request;
    }
}
