package optimodLyon.model;

import optimodLyon.ListUtils;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                    .min(Comparator.comparing(Intersection::getLatitude))
                    .orElseThrow(NoSuchElementException::new)
                    .getLatitude();

            this.minLongitude = intersections.stream()
                    .min(Comparator.comparing(Intersection::getLongitude))
                    .orElseThrow(NoSuchElementException::new)
                    .getLongitude();

            this.maxLatitude = intersections.stream()
                    .max(Comparator.comparing(Intersection::getLatitude))
                    .orElseThrow(NoSuchElementException::new)
                    .getLatitude();

            this.maxLongitude = intersections.stream()
                    .max(Comparator.comparing(Intersection::getLongitude))
                    .orElseThrow(NoSuchElementException::new)
                    .getLongitude();
        }

        /**
         * Permet d'avoir un point normalisé sur la carte à partir d'une intersection
         * @param intersection L'intersection à normaliser
         * @return Un point contenant les coordonnées normalisées
         */
        public Point normalizeIntersection(final Intersection intersection)
        {
            int finalX = (int) (((intersection.getLongitude() - this.minLongitude) / (this.maxLongitude - this.minLongitude)) * this.mapComponentDimension.width);
            int finalY = (int) (((intersection.getLatitude() - this.minLatitude) / (this.maxLatitude - this.minLatitude)) * this.mapComponentDimension.height);

            return new Point(finalX, finalY);
        }

        /**
         * Met à jour les dimensions du composant qui prend en charge la carte
         * @param mapComponentDimension Les nouvelles dimension de la carte
         */
        public void setMapComponentDimension(Dimension mapComponentDimension)
        {
            this.mapComponentDimension = mapComponentDimension;
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

            String key = this.generateHashCode(origin, destination);
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
        List<Segment> segments = new ArrayList<>(this.segmentsByIntersectionId.values());
        Collections.sort(segments);
        return Collections.unmodifiableList(segments);
    }

    /**
     * @return Les segments ayant une intersection avec le nom de la rue passé en paramèrtre
     */
    public List<Segment> getIntersectionSegments(final Segment segment){
        List<Segment> segments = this.getSegments();
        List<Segment> finalIntersectionSegments = new ArrayList<>();
        // Recuperer tous les segment portant le même nom que celui passer en paramètre
        List<Segment> allSegmentOfRue = segments.stream()
                .filter(s -> s.getName().equals(segment.getName()))
                .collect(Collectors.toList());

        for(Segment seg : allSegmentOfRue){
            finalIntersectionSegments.addAll(
                    segments.stream()
                            .filter(s -> !s.getName().equals("") && !s.getName().equals(seg.getName()) && (s.getDestination().equals(seg.getOrigin()) || s.getOrigin().equals(seg.getDestination())))
            .collect(Collectors.toList()));
        }

        return ListUtils.removeDuplicatesSegment(finalIntersectionSegments);
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
     * Retourne un segment à partir de deux intersections.
     * @param a La première intersection.
     * @param b La seconde intersection.
     * @return Le segment correspondant aux deux intersections.
     */
    public Segment getSegment(Intersection a, Intersection b) {
        /* Les segments de la map possédant une origine et une destination,
         * on peut parfois avoir un null si on cherche un segment de A a B mais pas de B à A.
         * La méthode getSegment teste les deux cas pour retourner quand même le bon segment si l'utilisateur
         * ne passe pas la bonne intersection en premier. */
        Segment returnedSeg = this.segmentsByIntersectionId.get(generateHashCode(a, b));
        if (returnedSeg == null) {
            returnedSeg = this.segmentsByIntersectionId.get(generateHashCode(b, a));
        }

        return returnedSeg;
    }

    /**
     * Retourne la liste contenant les segments indexés par leur IDs d'intersection.
     * @return La liste concernée.
     */
    public Map<String, Segment> getSegmentsByIntersectionIdList() {
        return segmentsByIntersectionId;
    }

    /**
     * Créer une clé à partir de deux intersections
     * @param a La première intersection
     * @param b La deuxième intersection
     * @return La clé générée
     */
    private String generateHashCode(Intersection a, Intersection b)
    {
        return String.format("%s%s%s", a.getId(), HASH_CODE_SEPARATOR, b.getId());
    }
}
