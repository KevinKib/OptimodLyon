package optimodLyon.model.circuit;

import optimodLyon.model.Waypoint;

import java.util.List;

public class Graph {

    private List<Waypoint> waypoints;
    private List<Edge> edges;

    public List<Waypoint> getWaypoints() {
        return waypoints;
    }
    public List<Edge> getEdges() {
        return edges;
    }

    /*public void setWaypoints(List<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }*/



    public Graph(List<Waypoint> waypointList, List<Edge> edgeList) {
        this.waypoints = waypointList;
        this.edges = edgeList;
    }
}
