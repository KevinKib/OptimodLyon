package external.circuitPlanner;

import optimodLyon.model.CityMap;
import optimodLyon.model.DeliveryPlan;
import optimodLyon.model.Segment;
import optimodLyon.model.Waypoint;
import optimodLyon.model.circuit.Graph;

import java.util.ArrayList;
import java.util.List;


public class CircuitPlanner1 {

    public static List<Segment> getShortestPath(Graph cityMapGraph, Waypoint pointA, Waypoint pointB){
        //Should call A* to compute the shortest path between the two points
        List<Segment> segmentList = new ArrayList<>();

        return segmentList;
    }

    public List<List<Segment>> searchSolution(CityMap map, DeliveryPlan plan, int cycleNumber){

        int requestNumber = plan.getRequests().toArray().length;
        for (int i=0; i<requestNumber; i++){

        }
        return null;
    }
}
