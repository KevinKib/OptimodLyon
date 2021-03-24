package optimodLyon.IHM;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import optimodLyon.controller.Controller;
import optimodLyon.controller.ihm.MapViewResizeController;
import optimodLyon.model.*;

import static optimodLyon.model.CityMap.CityMapCoordinates;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * Composant qui va contenir la carte.
 * @author Dorian TERBAH
 * @since 1.0
 */
public class MapView extends JComponent
{
    /**
     * Logo de localisation d'une livraison
     */
    private BufferedImage deliveryLocalisationlogo;

    /**
     * Logo de localisation d'une recherche de livraison
     */
    private BufferedImage pickupLocalisationlogo;

    /**
     * Chemin d'accés à l'image de localisation d'une livraison
     */
    private static final String DELIVERY_LOCALISATION_PATH = "./rsc/image/delivery-localisation.png";

    /**
     * Chemin d'accés à l'image de localisation d'une recherche de livraison
     */
    private static final String PICKUP_LOCALISATION_PATH = "./rsc/image/pickup-localisation.png";

    private static Color[] COLORS = new Color[]
            {
                Color.red, Color.green, Color.blue, Color.cyan, Color.magenta, Color.yellow,
                Color.pink, Color.orange, Color.darkGray, new Color(0xE55674), new Color(0x0F4E95),
                new Color(0xF68C66), new Color(0x1F5530), new Color(0x756BA6),
                new Color(0xD0CF90), new Color(0x77CA9A), new Color(0xDD5084)
            };

    /**
     * Controle le redimensionnement du composant
     */
    private MapViewResizeController mapViewResizeController;

    /**
     * Controleur général qui contient les données de l'application
     */
    private final Controller controller;

    /**
     * Constructeur par défaut de la classe MapView
     * @param controller Le controleur général
     */
    public MapView(Controller controller)
    {
        super();
        this.deliveryLocalisationlogo = null;
        this.pickupLocalisationlogo = null;

        this.controller = controller;

        try
        {
            this.deliveryLocalisationlogo = ImageIO.read(new File(DELIVERY_LOCALISATION_PATH));
            this.pickupLocalisationlogo = ImageIO.read(new File(PICKUP_LOCALISATION_PATH));
        }
        catch (Exception e)
        {
            System.err.println(e);
        }

        this.mapViewResizeController = new MapViewResizeController(controller);
        this.addComponentListener(this.mapViewResizeController);
    }

    /**
     * @return La dimension du composant
     */
    public Dimension getDimension()
    {
        return new Dimension(this.getWidth(), this.getHeight());
    }

    @Override
    public void paintComponent(Graphics q)
    {
        super.paintComponent(q);
        Graphics p = q.create();

        p.setColor(Color.LIGHT_GRAY);
        p.drawRect(0, 0, this.getWidth(), this.getHeight());

        p.setColor(Color.BLACK);

        CityMapCoordinates cityMapCoordinates = this.controller.getCityMapCoordinates();

        if (cityMapCoordinates != null)
        {
            CityMap cityMap = this.controller.getCityMap();
            // Affichage de la carte
            List<Segment> segments = cityMap.getSegments();

            for (Segment segment : segments)
            {
                final Intersection origin = segment.getOrigin();
                final Intersection destination = segment.getDestination();

                Point originPoint = cityMapCoordinates.normalizeIntersection(origin);
                Point destinationPoint = cityMapCoordinates.normalizeIntersection(destination);

                p.drawLine(originPoint.x, originPoint.y, destinationPoint.x, destinationPoint.y);
            }

            DeliveryPlan deliveryPlan = this.controller.getDeliveryPlan();
            // Affichage des pickup-delivery
            if (deliveryPlan != null)
            {
                List<Request> requests = deliveryPlan.getRequests();
                for (int i = 0; i < requests.size(); ++i)
                {
                    Color color = null;
                    if (i >= COLORS.length)
                    {
                        int r = (int)(Math.random() * 256);
                        int g = (int)(Math.random() * 256);
                        int b = (int)(Math.random() * 256);
                        color = new Color(r, g, b);
                    }
                    else
                    {
                        color = COLORS[i];
                    }

                    Request request = requests.get(i);

                    Point deliveryPoint = cityMapCoordinates.normalizeIntersection(request.getDelivery().getIntersection());
                    Point pickupPoint = cityMapCoordinates.normalizeIntersection(request.getPickup().getIntersection());

                    // Affichage de la delivery
                    BufferedImage image = this.resize(this.deliveryLocalisationlogo, 30, 30);
                    image = this.dye(image, color);
                    p.drawImage(image, deliveryPoint.x - 15, deliveryPoint.y - 30, null);

                    // Affichage du pickup
                    image = this.resize(this.pickupLocalisationlogo, 30, 30);
                    image = this.dye(image, color);
                    p.drawImage(image, pickupPoint.x - 15, pickupPoint.y - 30, null);
                }
            }
        }
    }

    private BufferedImage resize(BufferedImage img, int newW, int newH)
    {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    private BufferedImage dye(BufferedImage image, Color color)
    {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage dyed = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dyed.createGraphics();
        g.drawImage(image, 0,0, null);
        g.setComposite(AlphaComposite.SrcAtop);
        g.setColor(color);
        g.fillRect(0,0,w,h);
        g.dispose();
        return dyed;
    }
}