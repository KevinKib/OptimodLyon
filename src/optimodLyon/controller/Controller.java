package optimodLyon.controller;

import optimodLyon.model.CityMap;
import optimodLyon.model.DeliveryPlan;
import optimodLyon.model.circuit.CircuitManager;

import java.awt.*;

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

    /**
     * Constructeur par défaut du controleur
     */
    public Controller()
    {
        this.cityMap = null;
        this.deliveryPlan = null;
        this.cityMapCoordinates = null;
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
