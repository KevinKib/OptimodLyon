package optimodLyon.model;

public abstract class Waypoint extends Node{
    private int duration;

    public Waypoint(Intersection intersection, int duration) {
        super(intersection);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
