package optimodLyon.model;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

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
     * Id de l'intersection du depôt
     */
    private int depotIntersectionId;

    /**
     * Date de départ
     */
    private LocalDateTime departureTime;

    public DeliveryPlan(final List<Request> requests, final LocalDateTime departureTime, int depotIntersectionId)
    {
        this.requests = new ArrayList<Request>();
        this.requests.addAll(requests);

        this.departureTime = departureTime;
        this.depotIntersectionId = depotIntersectionId;
    }

    /**
     * @return L'id de l'intersection correspondant au depot
     */
    public int getDepotAddressId()
    {
        return this.depotIntersectionId;
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
