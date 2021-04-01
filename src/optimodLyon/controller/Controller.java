package optimodLyon.controller;

import optimodLyon.model.*;
import optimodLyon.model.circuit.CircuitManager;

import javax.swing.JComponent;
import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;

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
        //this.mapView = mapView;
    }

    /**
     * Permet de mettre à jour les composants graphiques observées par le contrôleur
     */
    private void updateObservedView(){
        for (JComponent view : this.observedViews){
            view.repaint();
        }
    }

    /**
     * Permet de renseigner une vue à observer par le contrôleur
     * @param view La vue à observer
     */
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
     * Permet d'ajouter une requête dans l'inventaire
     * @param pickupAndDeliveryForm Le formulaire qui provient de l'ihm pour ajouter un point de Pickup & Delivery
     */
    public void addRequest(PickupAndDeliveryForm pickupAndDeliveryForm){

        Intersection pickupIntersection = this.cityMap.getCommonIntersection(pickupAndDeliveryForm.getPickupFirstWay(), pickupAndDeliveryForm.getPickupSecondWay());
        Intersection deliveryIntersection = this.cityMap.getCommonIntersection(pickupAndDeliveryForm.getDeliveryFirstWay(), pickupAndDeliveryForm.getDeliverySecondWay());

        Request r = new Request(pickupAndDeliveryForm.getDeliveryDuration(), pickupAndDeliveryForm.getPickupDuration(), deliveryIntersection, pickupIntersection);

        this.deliveryPlan.addRequest(r);
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
     * Permet de lancer le calcul des circuits
     * @param plan L'inventaire
     * @param cycleNumber Le nombre de cyclistes
     */
    public void computeCircuit(DeliveryPlan plan, int cycleNumber)
    {
        this.circuitManager.computeSolution(plan, cycleNumber);
    }
}
