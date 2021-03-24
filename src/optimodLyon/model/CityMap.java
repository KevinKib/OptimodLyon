package optimodLyon.model;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Classe qui représente une carte XML.
 * @author Dorian TERBAH
 * @since 1.0
 */
public class CityMap
{
    /**
     * Classe qui représente les différents coefficients de normalisation des coordonnées
     */
    public static class CityMapCoordinates
    {

        /**
         * Dimension du composant qui va contenir la carte
         */
        private Dimension mapComponentDimension;

        /**
         * Latitude minimale
         */
        private float minLatitude;

        /**
         * Longitude minimale
         */
        private float minLongitude;

        /**
         * Latitude maximale
         */
        private float maxLatitude;

        /**
         * Longitude minimale
         */
        private float maxLongitude;

        /***
         * Constructeur de la classe CityMapCoordinates
         * @param mapComponentDimension La dimension du compoosant contenant la map
         * @param intersections La liste des intersections
         */
        public CityMapCoordinates(Dimension mapComponentDimension, final List<Intersection> intersections)
        {
            this.mapComponentDimension = mapComponentDimension;

            this.minLatitude = intersections.stream()
                    .min(Comparator.comparing(Intersection::getY))
                    .orElseThrow(NoSuchElementException::new)
                    .getY();

            this.minLongitude = intersections.stream()
                    .min(Comparator.comparing(Intersection::getX))
                    .orElseThrow(NoSuchElementException::new)
                    .getX();

            this.maxLatitude = intersections.stream()
                    .max(Comparator.comparing(Intersection::getY))
                    .orElseThrow(NoSuchElementException::new)
                    .getY();

            this.maxLongitude = intersections.stream()
                    .max(Comparator.comparing(Intersection::getX))
                    .orElseThrow(NoSuchElementException::new)
                    .getX();
        }

        /**
         * Permet d'avoir un point normalisé sur la carte à partir d'une intersection
         * @param intersection L'intersection à normaliser
         * @return Un point contenant les coordonnées normalisées
         */
        public Point normalizeIntersection(final Intersection intersection)
        {
            int finalX = (int) (((intersection.getX() - this.minLongitude) / (this.maxLongitude - this.minLongitude)) * this.mapComponentDimension.width);
            int finalY = (int) (((intersection.getY() - this.minLatitude) / (this.maxLatitude - this.minLatitude)) * this.mapComponentDimension.height);

            return new Point(finalX, finalY);
        }
    }

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
     * @param intersections Map des intersections composant la carte
     */
    public CityMap(List<Segment> segments, Map<Long, Intersection> intersections)
    {
        this.intersections = intersections;
        this.segmentsByIntersectionId = new HashMap<String, Segment>();

        for (Segment segment : segments)
        {
            Intersection origin = segment.getOrigin();
            Intersection destination = segment.getDestination();

            String key = this.generateHashCode(origin.getId(), destination.getId());
            this.segmentsByIntersectionId.put(key, segment);
        }
    }

    /**
     * @return Toutes les intersections de la carte dans une liste non modifiable
     */
    public List<Intersection> getIntersections()
    {
        return Collections.unmodifiableList(new ArrayList<Intersection>(this.intersections.values()));
    }

    /**
     * @return Tous les segments de la carte dans une liste non modifiable
     */
    public List<Segment> getSegments()
    {
        return Collections.unmodifiableList(new ArrayList<Segment>(this.segmentsByIntersectionId.values()));
    }

    /**
     * Récupère une intersection à partir de son id
     * @param id L'id de l'intersection
     * @return L'intersection associée à l'id
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
}
