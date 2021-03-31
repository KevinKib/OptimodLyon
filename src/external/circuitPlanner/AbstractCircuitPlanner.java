package external.circuitPlanner;

import optimodLyon.model.Segment;
import optimodLyon.model.Waypoint;
import optimodLyon.model.circuit.Graph;

import java.util.List;

public abstract class AbstractCircuitPlanner {

    public abstract List<List<Segment>> searchSolution(Graph circuit, int cycleNumber);

    public abstract List<Segment> getShortestPath(Graph cityMapGraph, Waypoint pointA, Waypoint pointB);
}
