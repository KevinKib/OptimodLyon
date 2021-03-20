package optimodLyon.model;

import java.util.ArrayList;
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
    private Map<Long, Intersection> intersections;

    /**
     * Caractère qui séparent deux ids d'intersection pour la génération des hashcode d'intersections
     */
    private static final String HASH_CODE_SEPARATOR = "-";

    /**
     * Constructeur de la classe CityMap
     * @param segments La liste de tous les segments qui composent la carte
     * @param intersections Map des injtersections composant la carte
     */
    public CityMap(List<Segment> segments, Map<Long, Intersection> intersections)
    {
        this.intersections = intersections;
        this.segmentsByIntersectionId = new HashMap<>();

        for (Segment segment : segments)
        {
            Intersection origin = segment.getOrigin();
            Intersection destination = segment.getDestination();

            String key = this.generateHashCode(origin.getId(), destination.getId());
            this.segmentsByIntersectionId.put(key, segment);
        }
    }

    /**
     * Récupère une intersection à partir de son id
     * @param id L'id de l'intersection
     * @return L'intersection associée à l'id si elle existe, null sinon
     */
    public Intersection getIntersectionById(long id)
    {
        return this.intersections.get(id);
    }

    /**
     * Créer une clé à partir de deux ids d'intersection
     * @param intersectionId1 L'id de la première intersection
     * @param intersectionId2 L'id de la deuxième intersection
     * @return La clé générée
     */
    public String generateHashCode(long intersectionId1, long intersectionId2)
    {
        return String.format("%s%s%s", intersectionId1, HASH_CODE_SEPARATOR, intersectionId2);
    }

    /**
     * Récupère la liste des intersections de la CityMap
     * @return La liste des intersections
     */
    public List<Intersection> getIntersections() {
        return new ArrayList<>(intersections.values());
    }

    /**
     * Récupère la liste des segments de la CityMap
     * @return La liste des segments
     */
    public List<Segment> getSegments() {
        return new ArrayList<>(segmentsByIntersectionId.values());
    }
}
