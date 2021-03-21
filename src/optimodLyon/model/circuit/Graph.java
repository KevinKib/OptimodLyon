package optimodLyon.model.circuit;

import optimodLyon.model.Waypoint;

import java.util.List;

public class Graph {
    public List<Waypoint> getwaypoints() {
        return waypoints;
    }

    public List<Edge> getedges() {
        return edges;
    }

    public void setWaypoints(List<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    private List<Waypoint> waypoints;
    private List<Edge> edges;

    public Graph(List<Waypoint> waypointList, List<Edge> edgeList) {
        this.waypoints = waypointList;
        this.edges = edgeList;
    }
}
