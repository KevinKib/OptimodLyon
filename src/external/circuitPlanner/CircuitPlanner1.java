package external.circuitPlanner;

import optimodLyon.model.CityMap;
import optimodLyon.model.Segment;
import optimodLyon.model.Waypoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CircuitPlanner1 {

    public static List<Segment> getShortestPath(CityMap cityMap, Waypoint pointA, Waypoint pointB){
        //Should call A* to compute the shortest path between the two points
        Map<String, Segment> segments = cityMap.getSegmentsByIntersectionIdList();
        Map.Entry<String, Segment> entry = segments.entrySet().iterator().next();
        List<Segment> segmentList = new ArrayList<>();
        segmentList.add(entry.getValue());

        return segmentList;
    }
}
