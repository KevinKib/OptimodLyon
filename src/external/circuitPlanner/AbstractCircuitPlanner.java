package external.circuitPlanner;

import optimodLyon.model.CityMap;
import optimodLyon.model.DeliveryPlan;
import optimodLyon.model.Intersection;
import optimodLyon.model.Segment;

import java.util.List;
import java.util.Map;

public abstract class AbstractCircuitPlanner {

    public abstract void searchSolution(CityMap map, DeliveryPlan plan, int cyclists);

    public abstract Map.Entry<List<Segment>, Integer> getShortestPath(CityMap map, Intersection a, Intersection b, int cyclists);

}
