package optimodLyon;

import optimodLyon.model.Segment;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Classe qui donne accès à des méthodes manipulant des listes
 * @author Dorian TERBAH
 * @since 1.0
 */
public class ListUtils
{
    /**
     * Permet de supprimer les doublons d'une liste de segments (pproblème avec les streams)
     * @param segments La liste à filtrer
     * @return La liste filtrée
     */
    public static List<Segment> removeDuplicatesSegment(final List<Segment> segments)
    {
        return segments.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(Segment::getName ))), ArrayList::new));
    }
}
