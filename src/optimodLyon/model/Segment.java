package optimodLyon.model;

/**
 * Classe qui représente un segment de rue dans une carte.
 * @author Dorian TERBAH
 * @since 1.0
 */
public class Segment
{
    /**
     * Id de l'intersection destination du segment
     */
    private long destinationId;

    /**
     * Id de l'intersection origine du segment
     */
    private long originId;

    /**
     * Nom du segment
     */
    private String name;

    /**
     * Longueur du segment
     */
    private float length;

    /**
     * Constructeur de la classe Segment
     * @param destinationId L'id de l'intersection destination
     * @param originId L'id de l'intersection origine
     * @param name Le nom du segment
     * @param length La longueur du segment
     */
    public Segment(long destinationId, long originId, final String name, float length)
    {
        this.destinationId = destinationId;
        this.originId = originId;
        this.name = new String(name);
        this.length = length;
    }

    /**
     * @return L'id de l'intersection destination
     */
    public long getDestinationId()
    {
        return this.destinationId;
    }

    /**
     * @return La longueur du segment
     */
    public float getLength()
    {
        return length;
    }

    /**
     * @return Le nom du segment
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return L'id de l'intersection origine
     */
    public long getOriginId()
    {
        return this.originId;
    }
}
