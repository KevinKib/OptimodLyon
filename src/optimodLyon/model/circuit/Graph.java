package optimodLyon.model.circuit;

import optimodLyon.model.Waypoint;

import java.util.List;

public class Graph {
    public List<Waypoint> getWaypointList() {
        return waypointList;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    private List<Waypoint> waypointList;
    private List<Edge> edgeList;

    public Graph(List<Waypoint> waypointList, List<Edge> edgeList) {
        this.waypointList = waypointList;
        this.edgeList = edgeList;
    }
}
