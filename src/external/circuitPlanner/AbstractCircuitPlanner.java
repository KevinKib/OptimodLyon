package external.circuitPlanner;

import optimodLyon.model.CityMap;
import optimodLyon.model.Intersection;
import optimodLyon.model.Segment;
import optimodLyon.model.Waypoint;

import java.util.List;

public abstract class AbstractCircuitPlanner {

    public abstract List<Segment> getShortestPath(CityMap cityMap, Waypoint a, Waypoint b);

}
