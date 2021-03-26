package optimodLyon.model;

/**
 * Classe qui permet de représenter une intersection sur une carte.
 * @author Dorian TERBAH
 * @since 1.0
 */
public class Intersection {
    /**
     * La longitude de l'intersection (coordonnée x)
     */
    public final float longitude;

    /**
     * La latitude de l'intersection (coordonnée y)
     */
    public final float latitude;

    /**
     * L'id de l'intersection
     */
    private long id;

    /**
     * Constructeur de la classe Intersection
     * @param longitude La coordonnée longitude de l'intersection
     * @param latitude La coordonnée y de l'intersection
     * @param id L'id de l'intersection
     */
    public Intersection(float longitude, float latitude, long id)
    {
        this.longitude = longitude;
        this.latitude = latitude;
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
     * @return Retourne la longitude d'une intersection - autrement dit, la coordonnée x
     */
    public float getLongitude()
    {
        return this.longitude;
    }

    /**
     * @return Retourne la latitude d'une intersection - autrement dit, la coordonnée y
     */
    public float getLatitude()
    {
        return this.latitude;
    }
}