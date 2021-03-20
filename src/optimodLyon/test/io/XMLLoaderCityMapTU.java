package optimodLyon.test.io;

import optimodLyon.io.XMLLoader;
import optimodLyon.model.CityMap;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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
     * Méthode qui teste les bonnes informations d'un deliveryPlan
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
        }
    }
}