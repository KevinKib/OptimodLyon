package external.circuitPlanner;

import optimodLyon.model.CityMap;
import optimodLyon.model.Segment;
import optimodLyon.model.Waypoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CircuitPlanner1 extends AbstractCircuitPlanner {

    public List<Segment> getShortestPath(CityMap cityMap, Waypoint a, Waypoint b){
        //Should call A* to compute the shortest path between the two points
        Map<String, Segment> segments = cityMap.getSegmentsByIntersectionId();
        Map.Entry<String, Segment> entry = segments.entrySet().iterator().next();
        List<Segment> segmentList = new ArrayList<>();
        segmentList.add(entry.getValue());

        return segmentList;
    }
}
