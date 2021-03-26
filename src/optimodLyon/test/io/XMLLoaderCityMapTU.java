package optimodLyon.test.io;

import optimodLyon.io.XMLLoader;
import optimodLyon.model.CityMap;
import optimodLyon.model.Intersection;
import optimodLyon.model.Segment;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Classe de test qui va tester le parsing d'un fichier de carte
 */
public class XMLLoaderCityMapTU
{
    private static final String MAP_FILES_PATH = "./rsc/test/io/mapfiles";

    /**
     * Méthode qui test le cas où le fichier donné en paramètre n'existe pas
     */
    @Test
    public void testNotFileFoundException()
    {
        CityMap map;
        // Fichier qui n'existe pas
        try
        {
            map = XMLLoader.loadMap(new File(MAP_FILES_PATH, "unexisting.xml").getAbsolutePath());
            fail("FileNotFoundException expected");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            assertTrue(e instanceof FileNotFoundException);
        }
    }

    /**
     * Méthode qui teste si le chemin renvoie bien à un fichier
     */
    @Test
    public void testFileAsFolder()
    {
        CityMap map;
        // Fichier qui existe mais qui est un répertoire
        try
        {
            map = XMLLoader.loadMap(new File(MAP_FILES_PATH).getAbsolutePath());
            fail("IO Exception expected");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            assertTrue(e instanceof IOException);
        }
    }

    /**
     * Méthode qui teste tous les cas d'erreurs pour le noeud intersection
     */
    @Test
    public void testIntersectionNode()
    {
        CityMap map;
        String path = new File(MAP_FILES_PATH, "map1.xml").getAbsolutePath();
        String message = "";

        // Pas de noeud intersection
        try
        {
            map = XMLLoader.loadMap(path);
            fail(String.format("Error expected : Moins de trois intersections dans le fichier %s", path));
        }
        catch (Exception e)
        {
            message = e.getMessage();
            System.out.println(message);
            assertEquals(String.format("Moins de trois intersections dans le fichier %s", path), message);
        }

        path = new File(MAP_FILES_PATH, "map2.xml").getAbsolutePath();
        // Attribut id manquant
        try
        {
            map = XMLLoader.loadMap(path);
            fail(String.format("Error expected : L'attribut id est manquant pour une intersection dans le fichier %s", path));
        }
        catch (Exception e)
        {
            message = e.getMessage();
            System.out.println(message);
            assertEquals(String.format("L'attribut id est manquant pour une intersection dans le fichier %s", path), message);
        }

        path = new File(MAP_FILES_PATH, "map3.xml").getAbsolutePath();
        // Attribut longitude manquant
        try
        {
            map = XMLLoader.loadMap(path);
            fail(String.format("Error expected : L'attribut longitude est manquant pour une intersection dans le fichier %s", path));
        }
        catch (Exception e)
        {
            message = e.getMessage();
            System.out.println(message);
            assertEquals(String.format("L'attribut longitude est manquant pour une intersection dans le fichier %s", path), message);
        }

        path = new File(MAP_FILES_PATH, "map4.xml").getAbsolutePath();
        // Attribut latitude manquant
        try
        {
            map = XMLLoader.loadMap(path);
            fail(String.format("Error expected : L'attribut latitude est manquant pour une intersection dans le fichier %s", path));
        }
        catch (Exception e)
        {
            message = e.getMessage();
            System.out.println(message);
            assertEquals(String.format("L'attribut latitude est manquant pour une intersection dans le fichier %s", path), message);
        }
    }

    /**
     * Méthode qui test tous les cas d'erreurs pour un segment
     */
    @Test
    public void testSegment()
    {
        CityMap map;
        String path = new File(MAP_FILES_PATH, "map5.xml").getAbsolutePath();
        String message = "";

        // Moins de deux segments
        try
        {
            map = XMLLoader.loadMap(path);
            fail(String.format("Error expected : Moins de deux segments dans le fichier %s", path));
        }
        catch (Exception e)
        {
            message = e.getMessage();
            System.out.println(message);
            assertEquals(String.format("Moins de deux segments dans le fichier %s", path), message);
        }

        path = new File(MAP_FILES_PATH, "map6.xml").getAbsolutePath();
        // Attribut destination manquant
        try
        {
            map = XMLLoader.loadMap(path);
            fail(String.format("Error expected : L'attribut destination est manquant pour un segment dans le fichier %s", path));
        }
        catch (Exception e)
        {
            message = e.getMessage();
            System.out.println(message);
            assertEquals(String.format("L'attribut destination est manquant pour un segment dans le fichier %s", path), message);
        }

        path = new File(MAP_FILES_PATH, "map7.xml").getAbsolutePath();
        // Attribut origin manquant
        try
        {
            map = XMLLoader.loadMap(path);
            fail(String.format("Error expected : L'attribut origin est manquant pour un segment dans le fichier %s", path));
        }
        catch (Exception e)
        {
            message = e.getMessage();
            System.out.println(message);
            assertEquals(String.format("L'attribut origin est manquant pour un segment dans le fichier %s", path), message);
        }

        path = new File(MAP_FILES_PATH, "map8.xml").getAbsolutePath();
        // Attribut length manquant
        try
        {
            map = XMLLoader.loadMap(path);
            fail(String.format("Error expected : L'attribut length est manquant pour un segment dans le fichier %s", path));
        }
        catch (Exception e)
        {
            message = e.getMessage();
            System.out.println(message);
            assertEquals(String.format("L'attribut length est manquant pour un segment dans le fichier %s", path), message);
        }

        path = new File(MAP_FILES_PATH, "map9.xml").getAbsolutePath();
        // Attribut name manquant
        try
        {
            map = XMLLoader.loadMap(path);
            fail(String.format("Error expected : L'attribut name est manquant pour un segment dans le fichier %s", path));
        }
        catch (Exception e)
        {
            message = e.getMessage();
            System.out.println(message);
            assertEquals(String.format("L'attribut name est manquant pour un segment dans le fichier %s", path), message);
        }
    }

    /**
     * Méthode qui vérifie que le fichier livré est chargé entièrement
     */
    @Test
    public void testCityMap()
    {
        CityMap map;
        String path = new File(MAP_FILES_PATH, "map.xml").getAbsolutePath();
        String message = "";

        try
        {
            map = XMLLoader.loadMap(path);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail("Le fichier est correct donc il n'est pas censé y avoir d'exception");
        }
    }

    /**
     * Méthode qui teste les bonnes informations d'un deliveryPlan
     */
    @Test
    public void testCityMapInformations()
    {
        CityMap map;
        String path = new File(MAP_FILES_PATH, "map10.xml").getAbsolutePath();
        String message = "";

        try
        {
            map = XMLLoader.loadMap(path);

            // vérification du nombre d'intersections dans l'inventaire
            List<Intersection> intersections = map.getIntersections();
            assertEquals(3, intersections.size());

            // vérification des informations des intersections
            Intersection intersection = map.getIntersectionById(365087270);
            assertNotNull(intersection);
            assertEquals(365087270, intersection.getId());
            assertEquals(45.751892f, intersection.getLatitude(), 0);
            assertEquals(4.883404f, intersection.getLongitude(), 0);

            intersection = map.getIntersectionById(26317396);
            assertNotNull(intersection);
            assertEquals(26317396, intersection.getId());
            assertEquals(45.75356f, intersection.getLatitude(), 0);
            assertEquals(4.883734f, intersection.getLongitude(), 0);

            intersection = map.getIntersectionById(325772567);
            assertNotNull(intersection);
            assertEquals(325772567, intersection.getId());
            assertEquals(45.751896f, intersection.getLatitude(), 0);
            assertEquals(4.8833594f, intersection.getLongitude(), 0);

            // vérification du nombre de segments dans l'inventaire
            List<Segment> segments = map.getSegments();
            assertEquals(2, segments.size());

            // vérification des informations des segments
            Segment segment = segments.get(0);

            if(segment.getDestination().getId() == 26317396) {
                assertEquals(325772567, segment.getOrigin().getId());
                assertEquals(187.21068f, segment.getLength(), 0);
                assertEquals("Rue Saint-Isidore", segment.getName());
            }
            else if (segment.getDestination().getId() == 365087270) {
                assertEquals(325772567, segment.getOrigin().getId());
                assertEquals(3.491316f, segment.getLength(), 0);
                assertEquals("", segment.getName());
            }
            else {
                fail("Les seuls segments qu'il existe sont ceux dans les if.");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail("Le fichier est correct donc il n'est pas censé y avoir d'exception");
        }
    }
}