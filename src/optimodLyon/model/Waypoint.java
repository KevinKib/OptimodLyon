package optimodLyon.model;

abstract public class Waypoint {
    private Intersection intersection;
    private int duration;

    public Waypoint(Intersection intersection, int duration) {
        this.intersection = intersection;
        this.duration = duration;
    }

    public Intersection getIntersection() {
        return intersection;
    }
    public int getDuration() {
        return duration;
    }
    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
