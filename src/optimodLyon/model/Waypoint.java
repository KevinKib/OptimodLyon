package optimodLyon.model;

/**
 * Classe représentant un point de passage
 * @author Fanny ROUVEL
 * @since 1.0
 */
abstract public class Waypoint {

    private Intersection intersection;
    private int duration;

    public Waypoint(Intersection intersection, int duration) {
        this.intersection = intersection;
        this.duration = duration;
    }

    /**
     * Retourne l'intersection sur laquelle se trouve le waypoint
     * @return Intersection
     */
    public Intersection getIntersection() {
        return intersection;
    }

    /**
     * Retourne la durée d'arrêt du waypoint
     * @return int, la durée d'arrêt
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Met à jour un waypoint
     * @param intersection, l'intersection où est situé le waypoint
     * @param duration, la durée d'arrêt du waypoint
     */
    public void updateWaypoint(Intersection intersection, int duration)
    {
        this.intersection = intersection;
        this.duration = duration;
    }

}
