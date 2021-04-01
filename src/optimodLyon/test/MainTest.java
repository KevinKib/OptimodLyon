package optimodLyon.test;

import optimodLyon.IHM.OptimodFrame;
import optimodLyon.circuitPlanner.CircuitPlanner1;
import optimodLyon.controller.Controller;
import optimodLyon.io.XMLLoader;

public class MainTest {
    private OptimodFrame mainFrame;

    public void run() {
        mainFrame = new OptimodFrame("Optimod'Lyon");
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        CircuitPlanner1 circuitPlanner1 = new CircuitPlanner1();

        Controller c = Controller.getInstance();
        c.setCityMap(XMLLoader.loadMap("./rsc/test/io/mapfiles/map.xml"));
        c.setDeliveryPlan(XMLLoader.loadDeliveryPlan(c.getCityMap(), "./rsc/test/io/delivery-files/delivery-good-formed2.xml"));

        circuitPlanner1.searchSolution(c.getCircuitManager().createCircuit(c.getDeliveryPlan()), 1);
    }
}
