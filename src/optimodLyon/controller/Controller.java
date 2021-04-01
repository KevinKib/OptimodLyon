package optimodLyon.controller;

import optimodLyon.IHM.MapView;
import optimodLyon.model.*;
import optimodLyon.model.circuit.CircuitManager;

import javax.swing.*;
import java.awt.*;

import java.util.*;
import java.util.List;

import static optimodLyon.model.CityMap.CityMapCoordinates;
/**
 * Classe qui fait le lien entre les données de la fenetre et les interactions possibles avec.
 * @author Dorian TERBAH
 * @since 1.0
 */
public class Controller
{
    /**
     * Map de la ville courante
     */
    private CityMap cityMap;

    /**
     * Inventaire de pickup-delivery
     */
    private DeliveryPlan deliveryPlan;

    /**
     * Informations utiles pour l'affichage de la carte
     */
    private CityMapCoordinates cityMapCoordinates;

    public CircuitManager getCircuitManager() {
        return circuitManager;
    }

    /**
     * CircuitManager
     */
    private CircuitManager circuitManager;

    private Set<JComponent> observedViews;

    private List<RequestCoordinates> requestCoordinates;

    /**
     * Constructeur par défaut du controleur
     */
    public Controller()
    {
        this.cityMap = null;
        this.deliveryPlan = null;
        this.cityMapCoordinates = null;
        this.circuitManager = null;
        observedViews = new HashSet<>();

        this.requestCoordinates = new ArrayList<>();
    }

    private void updateObservedView(){
        for (JComponent view : this.observedViews){
            view.repaint();
        }
    }

    public void registerObservedView(JComponent view){
        this.observedViews.add(view);
    }

    /**
     * Met à jour la map de la ville
     * @param cityMap La nouvelle carte
     */
    public void setCityMap(CityMap cityMap)
    {
        this.cityMap = cityMap;
        if (circuitManager != null){
            this.circuitManager.setCityMap(cityMap);
        }
        else {
            this.circuitManager = new CircuitManager(cityMap);
        }
    }

    /**
     * Met à jour de l'inventaire
     * @param deliveryPlan Le nouvel inventaire
     */
    public void setDeliveryPlan(DeliveryPlan deliveryPlan)
    {
        this.deliveryPlan = deliveryPlan;
        this.requestCoordinates.clear();

        if (this.deliveryPlan != null)
        {
            // Creation des requestCoordinates
            for (Request r : this.deliveryPlan.getRequests())
            {
                this.addRequestCoordinates(r);
            }
        }
    }

    private void addRequestCoordinates(final Request request)
    {
        Point deliveryPoint = this.cityMapCoordinates.normalizeIntersection(request.getDelivery().getIntersection());
        Point pickupPoint = this.cityMapCoordinates.normalizeIntersection(request.getPickup().getIntersection());

        Rectangle deliveryBounds = new Rectangle(deliveryPoint.x - (MapView.IMAGE_WIDTH / 2), deliveryPoint.y - MapView.IMAGE_HEIGHT, MapView.IMAGE_WIDTH, MapView.IMAGE_HEIGHT);
        Rectangle pickupBounds = new Rectangle(pickupPoint.x - (MapView.IMAGE_WIDTH / 2), pickupPoint.y - MapView.IMAGE_HEIGHT, MapView.IMAGE_WIDTH, MapView.IMAGE_HEIGHT);

        this.requestCoordinates.add(new RequestCoordinates(request, deliveryBounds, pickupBounds));
    }

    /**
     * Permet de récupérer une requete sur la map
     * @param mouseX La coordonnée X de la souris
     * @param mouseY La coordonnée Y de la souris
     * @return Une Pair qui stocke la requete et un booleen signifiant si l'image cliquée correspond au delivery
     */
    public Map.Entry<Request, Boolean> getRequest(int mouseX, int mouseY)
    {
        for (RequestCoordinates requestCoordinate : this.requestCoordinates)
        {
            Rectangle deliveryBounds = requestCoordinate.getDeliveryBounds();

            if (deliveryBounds.contains(mouseX, mouseY))
            {
                return Map.entry(requestCoordinate.getRequest(), true);
            }

            Rectangle pickupBounds = requestCoordinate.getPickupBounds();

            if (pickupBounds.contains(mouseX, mouseY))
            {
                return Map.entry(requestCoordinate.getRequest(), false);
            }
        }

        return null;
    }

    /**
     * Met à jour le circuitManager
     * @param circuitManager Le nouveau circuitManager
     */
    public void setCircuitManager(CircuitManager circuitManager)
    {
        this.circuitManager = circuitManager;
    }

    /**
     * @return La map de la ville
     */
    public CityMap getCityMap()
    {
        return this.cityMap;
    }

    /**
     * @return L'inventaire de pickup-delivery
     */
    public DeliveryPlan getDeliveryPlan()
    {
        return this.deliveryPlan;
    }

    /**
     * Met à jour le CityMapCoordinates. Attention : la CityMap ne doit jamais
     * être nulle à l'appel de cette méthode.
     * @param mapComponentDimension La dimension du composant qui contient la carte
     */
    public void setCityMapCoordinates(Dimension mapComponentDimension)
    {
        if (this.cityMap != null)
        {
            this.cityMapCoordinates = new CityMapCoordinates(mapComponentDimension, this.cityMap.getIntersections());
        }
    }

    /**
     *
     */
    public void addRequest(PickupAndDeliveryForm pickupAndDeliveryForm){

        Intersection pickupIntersection = this.cityMap.getCommonIntersection(pickupAndDeliveryForm.getPickupFirstWay(), pickupAndDeliveryForm.getPickupSecondWay());
        Intersection deliveryIntersection = this.cityMap.getCommonIntersection(pickupAndDeliveryForm.getDeliveryFirstWay(), pickupAndDeliveryForm.getDeliverySecondWay());

        Request r = new Request(pickupAndDeliveryForm.getDeliveryDuration(), pickupAndDeliveryForm.getPickupDuration(), deliveryIntersection, pickupIntersection);
        this.deliveryPlan.addRequest(r);
        this.addRequestCoordinates(r);

        this.updateObservedView();
    }

    /**
     * @return Le CityMapCoordinates courant
     */
    public CityMapCoordinates getCityMapCoordinates()
    {
        return this.cityMapCoordinates;
    }

    /**
     *
     * @param
     */
    public void computeCircuit(DeliveryPlan plan, int cycleNumber)
    {
        this.circuitManager.computeSolution(plan, cycleNumber);
    }
}
