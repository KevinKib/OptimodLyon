package optimodLyon.model.circuit;

import optimodLyon.model.Waypoint;

import java.util.List;

public class Circuit extends Graph {
    public Circuit(List<Waypoint> waypointList, List<Edge> edgeList) {
        super(waypointList, edgeList);
    }

    public Edge getEdgeByWaypoint(Waypoint waypoint){
        return null;
    }
}
