package optimodLyon.controller.ihm;

import optimodLyon.IHM.MapView;
import optimodLyon.controller.Controller;

import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Classe qui contrôle le redimensionnement du composant contenant la carte
 * @author Dorian TERBAH
 * @since 1.0
 */
public class MapViewResizeController extends ComponentAdapter
{

    @Override
    public void componentResized(ComponentEvent event)
    {
        Component component = event.getComponent();
        if (component instanceof MapView)
        {
            MapView mapView = (MapView) component;
            Controller.getInstance().setCityMapCoordinates(mapView.getDimension());
            mapView.repaint();
        }
    }
}
