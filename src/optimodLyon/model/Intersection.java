package optimodLyon.model;

/**
 * Classe qui permet de représenter une intersection sur une carte.
 * @author Dorian TERBAH
 * @since 1.0
 */
public class Intersection
{
    /**
     * La coordonnée x de l'intersection
     */
    public final float x;

    /**
     * La coordonnée y de l'intersection
     */
    public final float y;

    /**
     * L'id de l'intersection
     */
    private long id;

    /**
     * Constructeur de la classe Intersection
     * @param x La coordonnée x de l'intersection
     * @param y La coordonnée y de l'intersection
     * @param id L'id de l'intersection
     */
    public Intersection(float x, float y, long id)
    {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    /**
     * @return L'id de l'intersection
     */
    public long getId()
    {
        return this.id;
    }

    /**
     * Récupère la longitude de l'intersection
     * @return la longitude
     */
    public float getLongitude() {
        return x;
    }

    /**
     * Récupère la latitude de l'intersection
     * @return la latitude
     */
    public float getLatitude() {
        return y;
    }
}
