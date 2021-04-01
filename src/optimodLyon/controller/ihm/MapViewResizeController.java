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
    /**
     * Le controleur qui contient les données de l'application
     */
    private final Controller controller;

    /**
     * Constructeur par défaut
     * @param controller La contrôleur principal de l'application
     */
    public MapViewResizeController(Controller controller)
    {
        super();
        this.controller = controller;
    }

    @Override
    public void componentResized(ComponentEvent event)
    {
        Component component = event.getComponent();
        if (component instanceof MapView)
        {
            MapView mapView = (MapView) component;
            this.controller.setCityMapCoordinates(mapView.getDimension());
            mapView.repaint();
        }
    }
}
