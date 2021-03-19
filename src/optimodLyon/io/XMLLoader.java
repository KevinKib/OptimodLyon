package optimodLyon.io;

import optimodLyon.model.DeliveryPlan;
import optimodLyon.model.Request;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

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
     * Charge un deliveryPlan contenu dans un fichier
     * @param path Le chemin du fichier
     * @return Le deliveryPlan si le contenu du fichier est correcte, sinon false
     * @throws MalformedXMLException si le fichier XML contient des erreurs
     * @throws FileNotFoundException si le fichier donné en paramètre n'existe pas
     * @throws SecurityException si le fichier n'est pas accessible en écriture
     * @throws IOException si le fichier est un répertoire
     */
    public static DeliveryPlan loadDeliveryPlan(final String path) throws MalformedXMLException, FileNotFoundException, SecurityException, IOException
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

                Request request = new Request(Request.nextRequestId(), deliveryDuration, pickupDuration, deliveryAddress, pickupAddress);
                requests.add(request);
            }
        }

            int depotAddress = Integer.parseInt(depotAddressAttribute);
            deliveryPlan = new DeliveryPlan(requests, departureTime, depotAddress);
        return deliveryPlan;
    }
}
