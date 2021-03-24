package optimodLyon.model.circuit;

import external.circuitPlanner.CircuitPlanner1;
import optimodLyon.model.Segment;
import optimodLyon.model.Waypoint;

import java.util.List;

public class Circuit extends Graph {
    public Circuit(List<Waypoint> waypointList, List<Edge> edgeList) {
        super(waypointList, edgeList);
    }

    public Edge getEdgeByFirstWaypoint(Waypoint waypoint) {
        for (Edge edge: getEdges()) {
            if(edge.getFirst().equals(waypoint)) {
                return edge;
            }
        }
        return null;
    }

    public Edge getEdgeBySecondWaypoint(Waypoint waypoint) {
        for (Edge edge: getEdges()) {
            if(edge.getSecond().equals(waypoint)) {
                return edge;
            }
        }
        return null;
    }

    public void removeEdge(Edge edge){
        this.getEdges().remove(edge);
    }

    public void addEdge(Edge edge) {
        this.getEdges().add(edge);
    }


}
