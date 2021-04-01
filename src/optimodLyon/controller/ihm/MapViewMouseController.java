package optimodLyon.controller.ihm;

import optimodLyon.controller.Controller;
import optimodLyon.model.Request;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

/**
 * Controleur de la MapView
 * @author Dorian TERBAH
 * @since 1.0
 */
public class MapViewMouseController extends MouseAdapter
{
    private final Controller controller;

    public MapViewMouseController(final Controller controller)
    {
        this.controller = controller;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        Map.Entry<Request, Boolean> result = this.controller.getRequest(e.getX(), e.getY());

    }
}
