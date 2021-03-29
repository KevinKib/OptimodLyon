package optimodLyon.io;

import optimodLyon.model.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Classe qui va charger à partir de fichiers XML la carte et les courses.
 * @author Dorian TERBAH
 * @since 1.0
 */
public class XMLLoader
{
    /**
     * Tag XML pour le tag "planningRequest"
     */
    private static final String PLANNING_REQUEST_XML_TAG = "planningRequest";

    /**
     * Tag XML pour le tag "depot"
     */
    private static final String DEPOT_XML_TAG = "depot";

    /**
     * Tag XML pour le tag "request"
     */
    private static final String REQUEST_XML_TAG = "request";

    /**
     *
     * Tag XML pour l'attribut "address"
     */
    private static final String ADDRESS_XML_ATTRIBUTE = "address";

    /**
     * Tag XML pour l'attribut "departureTime"
     */
    private static final String DEPARTURE_TIME_XML_ADDRESS = "departureTime";

    /**
     * Tag XML pour l'attribut "pickupAddress"
     */
    private static final String PICKUP_ADDRESS_XML_ATTRIBUTE = "pickupAddress";

    /**
     * Tag XML pour l'attribut "deliveryAddress"
     */
    private static final String DELIVERY_ADDRESS_XML_ATTRIBUTE = "deliveryAddress";

    /**
     * Tag XML pour l'attribut "pickupDuration"
     */
    private static final String PICKUP_DURATION_XML_ATTRIBUTE = "pickupDuration";

    /**
     * Tag XML pour l'attribut "deliveryDuration"
     */
    private static final String DELIVERY_DURATION_XML_ATTRIBUTE = "deliveryDuration";

    /**
     * Tag XML pour le tag "map"
     */
    private static final String MAP_XML_TAG = "map";

    /**
     * Tag XML pour le tag "intersection"
     */
    private static final String INTERSECTION_XML_TAG = "intersection";

    /**
     *
     * Tag XML pour l'attribut "id"
     */
    private static final String ID_XML_ATTRIBUTE = "id";

    /**
     *
     * Tag XML pour l'attribut "latitude"
     */
    private static final String LATITUDE_XML_ATTRIBUTE = "latitude";

    /**
     *
     * Tag XML pour l'attribut "longitude"
     */
    private static final String LONGITUDE_XML_ATTRIBUTE = "longitude";

    /**
     * Tag XML pour le tag "segment"
     */
    private static final String SEGMENT_XML_TAG = "segment";

    /**
     *
     * Tag XML pour l'attribut "destination"
     */
    private static final String DESTINATION_XML_ATTRIBUTE = "destination";

    /**
     *
     * Tag XML pour l'attribut "origin"
     */
    private static final String ORIGIN_XML_ATTRIBUTE = "origin";

    /**
     *
     * Tag XML pour l'attribut "length"
     */
    private static final String LENGTH_XML_ATTRIBUTE = "length";

    /**
     *
     * Tag XML pour l'attribut "name"
     */
    private static final String NAME_XML_ATTRIBUTE = "name";

    /**
     * Charge une carte contenu dans un fichier
     * @param path Le chemin du fichier
     * @return La CityMap si le contenu du fichier est correcte, sinon null
     * @throws MalformedXMLException si le fichier XML contient des erreurs
     * @throws FileNotFoundException si le fichier donné en paramètre n'existe pas
     * @throws SecurityException si le fichier n'est pas accessible en écriture
     * @throws IOException si le fichier est un répertoire
     */
    public static CityMap loadMap(final String path) throws MalformedXMLException, FileNotFoundException, SecurityException, IOException {
        CityMap map = null;

        File file = new File(path);
        if (!file.exists())
        {
            throw new FileNotFoundException(String.format("Le fichier %s n'existe pas", path));
        }

        if (!file.canRead())
        {
            throw new SecurityException(String.format("le fichier %s n'a pas d'accés en écriture", path));
        }

        if (file.isDirectory())
        {
            throw new IOException(String.format("Le fichier %s est un répertoire et non un fichier", path));
        }

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        documentFactory.setIgnoringComments(true);
        documentFactory.setIgnoringElementContentWhitespace(true);
        documentFactory.setValidating(false);

        DocumentBuilder builder = null;
        Document document = null;
        try {
            builder = documentFactory.newDocumentBuilder();
            document = builder.parse(file);
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }

        Node mapNode = document.getElementsByTagName(MAP_XML_TAG).item(0);

        Element mapElement = (Element) mapNode;

        Map<Long, Intersection> intersections = new HashMap<>();

        NodeList intersectionNodeList = mapElement.getElementsByTagName(INTERSECTION_XML_TAG);
        if(intersectionNodeList.getLength() < 3) {
            throw new MalformedXMLException(String.format("Moins de trois intersections dans le fichier %s", path));
        }

        for (int i = 0; i < intersectionNodeList.getLength(); ++i) {
            Element currentIntersectionElement = (Element) intersectionNodeList.item(i);

            String idAttribute = currentIntersectionElement.getAttribute(ID_XML_ATTRIBUTE);
            if (idAttribute.isEmpty())
            {
                throw new MalformedXMLException(String.format("L'attribut %s est manquant pour une intersection dans le fichier %s", ID_XML_ATTRIBUTE, path));
            }

            String latitudeAttribute = currentIntersectionElement.getAttribute(LATITUDE_XML_ATTRIBUTE);
            if (latitudeAttribute.isEmpty())
            {
                throw new MalformedXMLException(String.format("L'attribut %s est manquant pour une intersection dans le fichier %s", LATITUDE_XML_ATTRIBUTE, path));
            }

            String longitudeAttribute = currentIntersectionElement.getAttribute(LONGITUDE_XML_ATTRIBUTE);
            if (longitudeAttribute.isEmpty())
            {
                throw new MalformedXMLException(String.format("L'attribut %s est manquant pour une intersection dans le fichier %s", LONGITUDE_XML_ATTRIBUTE, path));
            }

            // informations de l'intersection courante
            long id = Long.parseLong(idAttribute);
            float longitude = Float.parseFloat(longitudeAttribute);
            float latitude = Float.parseFloat(latitudeAttribute);

            Intersection intersection = new Intersection(longitude, latitude, id);
            intersections.put(id, intersection);
        }

        List<Segment> segments = new ArrayList<Segment>();

        NodeList segmentNodeList = mapElement.getElementsByTagName(SEGMENT_XML_TAG);
        if(segmentNodeList.getLength() < 2) {
            throw new MalformedXMLException(String.format("Moins de deux segments dans le fichier %s", path));
        }

        for (int i = 0; i < segmentNodeList.getLength(); ++i) {
            Element currentSegmentElement = (Element) segmentNodeList.item(i);

            String destinationAttribute = currentSegmentElement.getAttribute(DESTINATION_XML_ATTRIBUTE);
            if (destinationAttribute.isEmpty()) {
                throw new MalformedXMLException(String.format("L'attribut %s est manquant pour un segment dans le fichier %s", DESTINATION_XML_ATTRIBUTE, path));
            }

            String originAttribute = currentSegmentElement.getAttribute(ORIGIN_XML_ATTRIBUTE);
            if (originAttribute.isEmpty()) {
                throw new MalformedXMLException(String.format("L'attribut %s est manquant pour un segment dans le fichier %s", ORIGIN_XML_ATTRIBUTE, path));
            }

            String lengthAttribute = currentSegmentElement.getAttribute(LENGTH_XML_ATTRIBUTE);
            if (lengthAttribute.isEmpty()) {
                throw new MalformedXMLException(String.format("L'attribut %s est manquant pour un segment dans le fichier %s", LENGTH_XML_ATTRIBUTE, path));
            }

            if (!currentSegmentElement.hasAttribute(NAME_XML_ATTRIBUTE)) {
                throw new MalformedXMLException(String.format("L'attribut %s est manquant pour un segment dans le fichier %s", NAME_XML_ATTRIBUTE, path));
            }
            String nameAttribute = currentSegmentElement.getAttribute(NAME_XML_ATTRIBUTE);


            long destinationId = Long.parseLong(destinationAttribute);
            long originId = Long.parseLong(originAttribute);
            float length = Float.parseFloat(lengthAttribute);

            Segment segment = new Segment(intersections.get(destinationId), intersections.get(originId), nameAttribute, length);
            segments.add(segment);
        }

        map = new CityMap(segments, intersections);
        return map;
    }


    /**
     * Charge un deliveryPlan contenu dans un fichier
     * @param path Le chemin du fichier
     * @param map La map de la ville
     * @return Le deliveryPlan si le contenu du fichier est correcte, sinon false
     * @throws MalformedXMLException si le fichier XML contient des erreurs
     * @throws FileNotFoundException si le fichier donné en paramètre n'existe pas
     * @throws SecurityException si le fichier n'est pas accessible en écriture
     * @throws IOException si le fichier est un répertoire
     */
    public static DeliveryPlan loadDeliveryPlan(final CityMap map, final String path) throws MalformedXMLException, FileNotFoundException, SecurityException, IOException
    {
        DeliveryPlan deliveryPlan = null;

        File file = new File(path);
        if (!file.exists())
        {
            throw new FileNotFoundException(String.format("Le fichier %s n'existe pas", path));
        }

        if (!file.canRead())
        {
            throw new SecurityException(String.format("le fichier %s n'a pas d'accés en écriture", path));
        }

        if (file.isDirectory())
        {
            throw new IOException(String.format("Le fichier %s est un répertoire et non un fichier", path));
        }

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        documentFactory.setIgnoringComments(true);
        documentFactory.setIgnoringElementContentWhitespace(true);
        documentFactory.setValidating(false);
        DocumentBuilder builder = null;
        Document document = null;

        try
        {
            builder = documentFactory.newDocumentBuilder();
            document = builder.parse(file);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        if (document == null)
        {
            throw new MalformedXMLException(String.format("Le fichier %s n'a pas pu être parser correctement", path));
        }

        Node planningRequestNode = document.getElementsByTagName(PLANNING_REQUEST_XML_TAG).item(0);

        // récupération des informations du dépôt
        Node depotNode = document.getElementsByTagName(DEPOT_XML_TAG).item(0);

        if (depotNode == null)
        {
            throw new MalformedXMLException(String.format("Les informations concernant le dépôt sont manquantes dans le fichier %s", path));
        }

        // récupération de l'attribut de départ du dépôt
        String departureTimeAttribute = ((Element) depotNode).getAttribute(DEPARTURE_TIME_XML_ADDRESS);
        if (departureTimeAttribute.isEmpty())
        {
            throw new MalformedXMLException(String.format("L'attribut de la date de départ n'est pas présent dans le fichier %s", path));
        }

        Time departureTime = Time.valueOf(departureTimeAttribute);

        String depotAddressAttribute = ((Element) depotNode).getAttribute(ADDRESS_XML_ATTRIBUTE);
        if (depotAddressAttribute.isEmpty())
        {
            throw new MalformedXMLException(String.format("L'attribut %s est manquant dans les informations du dépôt dans le fichier %s", ADDRESS_XML_ATTRIBUTE, path));
        }

        List<Request> requests = new ArrayList<Request>();

        if (planningRequestNode.getNodeType() == Node.ELEMENT_NODE)
        {
            Element planningRequestElement = (Element) planningRequestNode;
            NodeList requestsNodeList = planningRequestElement.getElementsByTagName(REQUEST_XML_TAG);

            for (int i = 0; i < requestsNodeList.getLength(); ++i)
            {
                Element currentRequestElement = (Element) requestsNodeList.item(i);
                String pickupAddressAttribute = currentRequestElement.getAttribute(PICKUP_ADDRESS_XML_ATTRIBUTE);
                if (pickupAddressAttribute.isEmpty())
                {
                    throw new MalformedXMLException(String.format("L'attribut %s est manquant pour une requête dans le fichier %s", PICKUP_ADDRESS_XML_ATTRIBUTE, path));
                }

                String deliveryAddressAttribute = currentRequestElement.getAttribute(DELIVERY_ADDRESS_XML_ATTRIBUTE);
                if (deliveryAddressAttribute.isEmpty())
                {
                    throw new MalformedXMLException(String.format("L'attribut %s est manquant pour une requête dans le fichier %s", DELIVERY_ADDRESS_XML_ATTRIBUTE, path));
                }

                String pickupDurationAttribute = currentRequestElement.getAttribute(PICKUP_DURATION_XML_ATTRIBUTE);
                if (pickupDurationAttribute.isEmpty())
                {
                    throw new MalformedXMLException(String.format("L'attribut %s est manquant pour une requête dans le fichier %s", PICKUP_DURATION_XML_ATTRIBUTE, path));
                }

                String deliveryDurationAttribute = currentRequestElement.getAttribute(DELIVERY_DURATION_XML_ATTRIBUTE);
                if (deliveryDurationAttribute.isEmpty())
                {
                    throw new MalformedXMLException(String.format("L'attribut %s est manquant pour une requête dans le fichier %s", DELIVERY_DURATION_XML_ATTRIBUTE, path));
                }

                // informations de la requête courante
                int deliveryDuration = Integer.parseInt(deliveryDurationAttribute);
                int pickupDuration = Integer.parseInt(pickupDurationAttribute);
                long deliveryAddress = Long.parseLong(deliveryAddressAttribute);
                long pickupAddress = Long.parseLong(pickupAddressAttribute);

                Request request = new Request(deliveryDuration, pickupDuration, map.getIntersectionById(deliveryAddress), map.getIntersectionById(pickupAddress));
                requests.add(request);
            }
        }

        int depotAddress = Integer.parseInt(depotAddressAttribute);
        Warehouse w = new Warehouse(map.getIntersectionById(depotAddress));
        deliveryPlan = new DeliveryPlan(requests, w, departureTime);
        return deliveryPlan;
    }
}
