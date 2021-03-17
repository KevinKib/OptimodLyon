package optimodLyon.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

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
     * Date de départ
     */
    private Date departureTime;

    public DeliveryPlan(final List<Request> requests, final Date departureTime)
    {
        this.requests = new ArrayList<Request>();
        this.requests.addAll(requests);

        this.departureTime = departureTime;
    }

    /**
     * Ajoute une request dans l'inventaire de demandes de pickup-delivery
     * @param request La nouvelle requête à ajouter
     */
    public void addRequest(Request request)
    {
        this.requests.add(request);
    }

    /**
     * Remplace l'ancienne requête par la nouvelle
     * @param oldRequest L'ancienne requête
     * @param newRequest La nouvelle requête
     */
    public void updateRequest(Request oldRequest, Request newRequest)
    {
    }

    /**
     * Supprime une requête
     * @param request La requête à supprimer
     */
    public void deleteRequest(Request request)
    {
        this.requests.remove(request);
    }
}
