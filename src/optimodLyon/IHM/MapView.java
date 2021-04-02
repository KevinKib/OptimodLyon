package optimodLyon.IHM;

import optimodLyon.controller.Controller;
import optimodLyon.controller.ihm.MapViewResizeController;
import optimodLyon.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static optimodLyon.model.CityMap.CityMapCoordinates;

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
     * Logo de localisation d'un dépôt
     */
    private BufferedImage warehouseLocalisationlogo;

    /**
     * Chemin d'accés à l'image de localisation d'une livraison
     */
    private static final String DELIVERY_LOCALISATION_PATH = "/image/delivery-localisation.png";

    /**
     * Chemin d'accés à l'image de localisation d'une recherche de livraison
     */
    private static final String PICKUP_LOCALISATION_PATH = "/image/pickup-localisation.png";

    /**
     * Chemin d'accés à l'image de localisation d'un dépôt
     */
    private static final String WAREHOUSE_LOCALISATION_PATH = "/image/warehouse.png";

    /**
     * Constante qui définit une palette de couleurs pour l'affichage des points de Pickup & Deilevry
     */
    private static Color[] COLORS = new Color[]
            {
                    new Color(0xA31834), new Color(0x006195),
                    new Color(0x02550D), new Color(0xFFC99700), new Color(0x54327D),
                    new Color(0xA14498), new Color(0xCB6702), new Color(0x003165),
                    new Color(0x8E4966), new Color(0x450707), new Color(0x5B805B),
                    new Color(0x47311D), new Color(0x20103E), new Color(0x1E7B76),
                    new Color(0x9d029b), new Color(0x34762F), new Color(0x875300),
                    new Color(0x0B3B3B), new Color(0xd6608f), new Color(0xAF8F76),
                    new Color(0x444478), new Color(0x790057), new Color(0x7D5A5A),
                    new Color(0xF68C66), new Color(0x00581A), new Color(0x756BA6),
                    new Color(0x865D1E), new Color(0x332942), new Color(0x613143),
                    new Color(0xe54d10), new Color(0x2e86c1), new Color(0x239b56)
            };

    /**
     * Controle le redimensionnement du composant
     */
    private MapViewResizeController mapViewResizeController;

    /**
     * Constructeur par défaut de la classe MapView
     */
    public MapView()
    {
        super();
        this.deliveryLocalisationlogo = null;
        this.pickupLocalisationlogo = null;

        Controller.getInstance().registerObservedView(this);

        try
        {
            this.deliveryLocalisationlogo = ImageIO.read(getClass().getResource(DELIVERY_LOCALISATION_PATH));
            this.pickupLocalisationlogo = ImageIO.read(getClass().getResource(PICKUP_LOCALISATION_PATH));
            this.warehouseLocalisationlogo = ImageIO.read(getClass().getResource(WAREHOUSE_LOCALISATION_PATH));
        }
        catch (Exception e)
        {
            System.err.println(e);
        }

        this.mapViewResizeController = new MapViewResizeController();
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

        CityMapCoordinates cityMapCoordinates = Controller.getInstance().getCityMapCoordinates();

        if (cityMapCoordinates != null)
        {
            CityMap cityMap = Controller.getInstance().getCityMap();
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

            DeliveryPlan deliveryPlan = Controller.getInstance().getDeliveryPlan();
            // Affichage des pickup-delivery
            if (deliveryPlan != null)
            {
                Point wareHousePoint = cityMapCoordinates.normalizeIntersection(deliveryPlan.getWarehouse().getIntersection());
                BufferedImage image = this.resize(this.warehouseLocalisationlogo, 20, 20);
                p.drawImage(image, wareHousePoint.x - 10, wareHousePoint.y - 10, null);

                List<Request> requests = deliveryPlan.getRequests();
                List<Waypoint> waypoints = new ArrayList<>();
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

                    waypoints.add(request.getDelivery());
                    waypoints.add(request.getPickup());

                    Point deliveryPoint = cityMapCoordinates.normalizeIntersection(request.getDelivery().getIntersection());
                    Point pickupPoint = cityMapCoordinates.normalizeIntersection(request.getPickup().getIntersection());


                    // Affichage de la delivery
                    image = this.resize(this.deliveryLocalisationlogo, 30, 30);
                    image = this.dye(image, color);
                    p.drawImage(image, deliveryPoint.x - 15, deliveryPoint.y - 30, null);

                    // Affichage du pickup
                    image = this.resize(this.pickupLocalisationlogo, 30, 30);
                    image = this.dye(image, color);
                    p.drawImage(image, pickupPoint.x - 15, pickupPoint.y - 30, null);
                }

                List<List<Segment>> solution = Controller.getInstance().getCircuitManager().getSolution();

                int order = 1;
                List<Intersection> numberedWaypoints = new ArrayList<>();
                if (solution != null) {
                    // Affichage des itinéraires
                    for (List<Segment> cycle: solution){
                        long circuitDuration = deliveryPlan.getDepartureTime().getTime()/1000;
                        for( int i=0; i < cycle.size(); i++){
                            Segment segment = cycle.get(i);
                            circuitDuration += segment.getLength()/5.5;
                            final Intersection origin = segment.getOrigin();
                            final Intersection destination = segment.getDestination();

                            Point originPoint = cityMapCoordinates.normalizeIntersection(origin);
                            Point destinationPoint = cityMapCoordinates.normalizeIntersection(destination);

                            p.setColor(Color.RED);
                            Graphics2D g2 = (Graphics2D) p;
                            g2.setStroke(new BasicStroke(2));

                            if (Math.floorMod(order,2)==0){
                                this.drawArrowLine(g2,originPoint.x, originPoint.y, destinationPoint.x, destinationPoint.y,20,5);
                            }
                            else{
                                g2.drawLine(originPoint.x, originPoint.y, destinationPoint.x, destinationPoint.y);
                            }
                            p.setColor(Color.BLACK);
                            p.setFont(new Font("Arial", Font.BOLD, 15));
                            if(i==0){
                                p.drawString("D-"+new Time(circuitDuration*1000).toString(), originPoint.x-10, originPoint.y-5);
                            }
                            else if(i==cycle.size()-1){
                                p.drawString("A-"+new Time(circuitDuration*1000).toString(), originPoint.x-10, originPoint.y+25);
                            }
                            for (Waypoint waypoint : waypoints) {
                                Intersection intersection = waypoint.getIntersection();
                                if (!numberedWaypoints.contains(intersection) && origin.equals(intersection) ) {
                                    circuitDuration += waypoint.getDuration();
                                    numberedWaypoints.add(intersection);
                                    p.drawString(String.valueOf(new Time(circuitDuration*1000).toString()), originPoint.x-10, originPoint.y+25);
                                }
                            }
                            order +=1;
                        }
                    }
                }
            }
        }
    }
    /**
     * Draw an arrow line between two points.
     * @param g the graphics component.
     * @param x1 x-position of first point.
     * @param y1 y-position of first point.
     * @param x2 x-position of second point.
     * @param y2 y-position of second point.
     * @param d  the width of the arrow.
     * @param h  the height of the arrow.
     */
    private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1; // Direction in x and in y
        double D = Math.sqrt(dx*dx + dy*dy);// The distance between the two points of the line
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.drawLine(x1, y1, x2, y2);
        if (D-d>0){
            g.fillPolygon(xpoints, ypoints, 3);
        }
    }

    /**
     * Fonction qui permet de redimensionner une image
     * @param img L'image à redimensionner
     * @param newW La nouvelle largeur de l'image
     * @param newH La nouvelle hauteur de l'image
     * @return L'image redimensionnée avec la nouvelle largeur et la nouvelle hauteur
     */
    private BufferedImage resize(BufferedImage img, int newW, int newH)
    {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    /**
     * Permet de coloriser une image
     * @param image L'image à coloriser
     * @param color La nouvelle couleur de l'image
     * @return L'image avec la nouvelle couleur
     */
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