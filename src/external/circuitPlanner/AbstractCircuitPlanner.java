package external.circuitPlanner;

import optimodLyon.model.*;
import optimodLyon.model.circuit.Graph;

import java.util.List;
import java.util.Map;

public abstract class AbstractCircuitPlanner {

    public abstract List<List<Segment>> searchSolution(CityMap map, DeliveryPlan plan, int cycleNumber);

    public abstract List<Segment> getShortestPath(Graph cityMapGraph, Waypoint pointA, Waypoint pointB);
}
