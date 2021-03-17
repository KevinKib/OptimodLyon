package optimodLyon.model;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * Classe qui représente une carte XML.
 * @author Dorian TERBAH
 * @since 1.0
 */
public class CityMap
{
    /**
     * Map qui match deux ids d'intersections avec un segment
     */
    private Map<String, Segment> segmentsByIntersectionId;

    /**
     * Map qui match un id d'intersection avec l'intersection correspondante
     */
    private Map<Integer, Intersection> intersections;

    /**
     * Caractère qui séparent deux ids d'intersection pour la génération des hashcode d'intersections
     */
    private static final String HASH_CODE_SEPARATOR = "-";

    /**
     * Constructeur de la classe CityMap
     * @param segments La liste de tous les segments qui composent la carte
     * @param intersections Map des injtersections composant la carte
     */
    public CityMap(List<Segment> segments, Map<Integer, Intersection> intersections)
    {
        this.intersections = intersections;
        this.segmentsByIntersectionId = new HashMap<String, Segment>();

        for (Segment segment : segments)
        {

        }
    }

    /**
     * Créer une clé à partir de deux ids d'intersection
     * @param intersectionId1 L'id de la première intersection
     * @param intersectionId2 L'id de la deuxième intersection
     * @return La clé générée
     */
    public String generateHashCode(int intersectionId1, int intersectionId2)
    {
        return String.format("%s%s%s", intersectionId1, HASH_CODE_SEPARATOR, intersectionId2);
    }
}
