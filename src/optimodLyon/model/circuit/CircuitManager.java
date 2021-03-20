package optimodLyon.model.circuit;

import optimodLyon.model.CityMap;
import optimodLyon.model.Request;
import optimodLyon.model.Segment;

import java.util.List;

/**
 *
 */
public class CircuitManager
{
    public Circuit getGraph() {
        return graph;
    }

    public CityMap getCityMap() {
        return cityMap;
    }

    private Circuit graph;
    private CityMap cityMap;

    public CircuitManager(Circuit graph, CityMap cityMap) {
        this.graph = graph;
        this.cityMap = cityMap;
    }

    public List<Segment> addRequest(Request request, int beforePickup, int beforeDelivery){
        return null;
    }
    public List<Segment> deleteRequest(Request request){
        return null;
    }
    public List<Segment> updateRequest(Request oldRequest, Request newRequest){
        return null;
    }
    public List<List<Segment>> getSolution(){
        return null;
    }
    public int getSolutionCost(){
        return 0;
    }
    public int getDistance(List<Segment> segments){
        return 0;
    }
    private List<Segment> graphToSegmentList(){
        return null;
    }

}
