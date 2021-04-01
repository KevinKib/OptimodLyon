package optimodLyon.model;

/**
 * Classe représentant un point de passage
 * @author Fanny ROUVEL
 * @since 1.0
 */
public abstract class Waypoint extends Node{
    /**
     * La durée associée au waypoint
     */
    private int duration;

    /**
     * Constructeur de la classe Wayppoint
     * @param intersection L'emplacement du waypoint
     * @param duration La durée associée au waypoint
     */
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

    /**
     * Mets à jour la durée du point d'arrêt
     * @param duration La nouvelle durée
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }
}