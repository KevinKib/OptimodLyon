package optimodLyon.model;

/**
 * Classe qui représente un segment de rue dans une carte.
 * @author Dorian TERBAH
 * @since 1.0
 */
public class Segment implements Comparable<Segment>
{
    /**
     * Intersection destination du segment
     */
    private Intersection destination;

    /**
     * Intersection origine du segment
     */
    private Intersection origin;

    /**
     * Nom du segment
     */
    private String name;

    /**
     * Longueur du segment
     */
    private float length;


    public Segment(String name){
        this.name = name;
    }

    /**
     * Constructeur de la classe Segment
     * @param destination L'intersection destination
     * @param origin L'intersection origine
     * @param name Le nom du segment
     * @param length La longueur du segment
     */
    public Segment(Intersection destination, Intersection origin, final String name, float length)
    {
        this.destination = destination;
        this.origin = origin;
        this.name = name;
        this.length = length;
    }

    /**
     * @return L'intersection destination
     */
    public Intersection getDestination()
    {
        return this.destination;
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
     * Affecte le nom du segment
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return L'intersection origine
     */
    public Intersection getOrigin()
    {
        return this.origin;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Segment){
            Segment s = (Segment) o;
            if(s.getName().trim().equals(this.name.trim())){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public int compareTo(Segment segment) {
        return this.getName().compareTo(segment.getName());
    }
}
