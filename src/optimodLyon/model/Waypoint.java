package optimodLyon.model;

/**
 * Classe représentant un point de passage
 * @author Fanny ROUVEL
 * @since 1.0
 */
public abstract class Waypoint extends Node{
    private int duration;

    public Waypoint(Intersection intersection, int duration) {
        super(intersection);
        this.duration = duration;
    }

    /**
     * Retourne la durée d'arrêt du waypoint
     * @return int, la durée d'arrêt
     */
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Met à jour un waypoint
     * @param intersection, l'intersection où est situé le waypoint
     * @param duration, la durée d'arrêt du waypoint
     */
    public void updateWaypoint(Intersection intersection, int duration)
    {
        this.setIntersection(intersection);
        this.duration = duration;
    }

}