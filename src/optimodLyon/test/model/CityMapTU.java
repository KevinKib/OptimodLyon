package optimodLyon.test.model;

import optimodLyon.model.CityMap;
import optimodLyon.model.Intersection;
import optimodLyon.model.Segment;
import org.junit.Before;

import java.io.File;
import optimodLyon.io.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Classe de test qui va tester le parsing d'un fichier d'inventaire de pickup-delivery
 */
public class CityMapTU
{
    private static final String DELIVERY_FILES_PATH = "./rsc/test/io/delivery-files";

    private CityMap cityMap;

    @Before
    public void loadCityMap()
    {

        try
        {
            this.cityMap = XMLLoader.loadMap(new File(DELIVERY_FILES_PATH, "map.xml").getAbsolutePath());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Impossible de charger la map");
        }
    }

    @Test
    public void testsIfGetSegmentWorksWithIntersectionsInGoodOrder()
    {
        Segment correctSegment = this.cityMap.getSegments().get(0);
        Intersection origin = this.cityMap.getSegments().get(0).getOrigin();
        Intersection destination = this.cityMap.getSegments().get(0).getDestination();

        Segment seg = this.cityMap.getSegment(origin, destination);

        assertNotNull(seg);
        assertEquals(correctSegment, seg);
    }

    @Test
    public void testsIfGetSegmentWorksWithIntersectionsInWrongOrder()
    {
        Segment correctSegment = this.cityMap.getSegments().get(0);
        Intersection origin = this.cityMap.getSegments().get(0).getOrigin();
        Intersection destination = this.cityMap.getSegments().get(0).getDestination();

        Segment segmentA = this.cityMap.getSegment(origin, destination);
        Segment segmentB = this.cityMap.getSegment(destination, origin);

        assertNotNull(segmentA);
        assertNotNull(segmentB);
        assertEquals(segmentA, segmentB);
        assertEquals(correctSegment, segmentA);

    }

}

