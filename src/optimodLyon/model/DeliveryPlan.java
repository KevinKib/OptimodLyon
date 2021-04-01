package optimodLyon.model;

import java.util.List;
import java.util.ArrayList;
import java.sql.Time;
import java.util.NoSuchElementException;

/**
 * Classe représentant un inventaire de demandes de pickup-delivery.
 * @author Dorian TERBAH
 * @since 1.0
 */
public class DeliveryPlan
{
    /**
     * Liste des requêtes associées à l'inventaire
     */
    private List<Request> requests;

    /**
     * Noeud correspondant au dépot
     */
    private Warehouse warehouse;

    /**
     * Date de départ de la livraison
     */
    private Time departureTime;

    /**
     * Constructeur de la classe DeliveryPlan
     * @param requests La liste des requêtes de l'inventaire
     * @param w L'emplacement de l'entrepôt
     * @param departureTime L'heure de départ
     */
    public DeliveryPlan(final List<Request> requests, Warehouse w, Time departureTime)
    {
        this.requests = new ArrayList<Request>();
        this.requests.addAll(requests);
        this.warehouse = w;
        this.departureTime = departureTime;
    }

    /**
     * @return L'id de l'intersection correspondant au depot
     */
    public long getDepotAddressId()
    {
        return this.warehouse.getIntersection().getId();
    }

    /**
     * @return Le temps de départ pour l'inventaire de pickup-delivery
     */
    public Time getDepartureTime()
    {
        return this.departureTime;
    }

    /**
     * @return Le dépot associé au plan de livraison.
     */
    public Warehouse getWarehouse()
    {
        return this.warehouse;
    }

    /**
     * @return La liste des requêtes
     */
    public List<Request> getRequests()
    {
        return this.requests;
    }

    /**
     * Ajoute une request dans l'inventaire de demandes de pickup-delivery
     * @param request La nouvelle requête à ajouter
     */
    public void addRequest(Request request)
    {
        this.requests.add(request);
    }
}
