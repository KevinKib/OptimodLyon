package optimodLyon.controller;

import optimodLyon.model.CityMap;
import optimodLyon.model.DeliveryPlan;
import optimodLyon.model.circuit.CircuitManager;

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
     * CircuitManager
     */
    private CircuitManager circuitManager;

    /**
     * Constructeur par défaut du controleur
     */
    public Controller()
    {
        this.cityMap = null;
        this.deliveryPlan = null;
        this.circuitManager = null;
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
     *
     * @param
     */
    public void computeCircuit(DeliveryPlan plan, int cycleNumber)
    {
        this.circuitManager.getSolution(plan, cycleNumber);
    }
}
