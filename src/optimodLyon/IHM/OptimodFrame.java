package optimodLyon.IHM;

import optimodLyon.controller.Controller;
import optimodLyon.io.XMLLoader;
import optimodLyon.model.CityMap;
import optimodLyon.model.DeliveryPlan;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
/**
 * Main class of the application Optimod'Lyon.
 * Contains the map, and the main functionalities of the app.
 */
public class OptimodFrame extends JFrame {

    /**
     * Composant qui va contenir toutes les différentes vues
     */
    private JPanel mainPanel;

    /**
     * Largeyr de la fenetre
     */
    private static final int FRAME_WIDTH = 1200;

    /**
     * Hauteur de la fenetre
     */
    private static final int FRAME_HEIGHT = 800;

    /**
     * Vue centrale de la fenetre (avec la carte)
     */
    private MapView mapView;

    /**
     * Vue au top de la fenetre (Panel en haut qui contient les boutons pour charger les fichiers XML)
     */
    private JPanel navigationView;

    /**
     * Vue du menu à gauche
     */
    private JPanel leftMenuView;

    /**
     * Nom de l'application
     */
    public static final String APP_NAME = "Optimod'Lyon";

    private JLabel mapPlaceholder;

    private JPanel leftPanel;
    private JButton addPDButton;
    private JButton modifyOrderButton;
    private JLabel legendPlaceholder;

    public OptimodFrame(String name) {
        super();

        // Frame construction
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Navigation view
        this.navigationView = new NavigationView(this);
        mainPanel.add(this.navigationView, BorderLayout.NORTH);

        // Center Panel
        this.mapView = new MapView();
        mainPanel.add(this.mapView, BorderLayout.CENTER);

        // Left Menu
        this.leftMenuView = new LeftMenuView(this);
        //buildLeftPanel();
        mainPanel.add(this.leftMenuView, BorderLayout.WEST);

        // Set main panel
        this.setContentPane(mainPanel);
    }

    /**
     * Demande au controleur de la fenetre de charger le fichier de city map
     * @param filename Le fichier contenant les informations de la map
     * @return true si le fichier a bien été chargé, sinon false
     */
    boolean loadCityMap(String filename)
    {
        try
        {
            CityMap map = XMLLoader.loadMap(filename);
            Controller.getInstance().setCityMap(map);
            Controller.getInstance().setDeliveryPlan(null);
            Controller.getInstance().getCircuitManager().setSolution(null);
            Controller.getInstance().setCityMapCoordinates(this.mapView.getDimension());
            this.mapView.repaint();
        }
        catch (Exception e)
        {
            String message = String.format("Le fichier de carte n'a pas été correctement chargé\n%s", e.getMessage());
            JOptionPane.showMessageDialog(null, message,"Erreur de chargement de la carte",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Demande au controleur de la fenetre de charger un fichier de delivery plan
     * @param filename le fichier contenant les informations du delivery plan
     * @return true si le fichier a bien été chargé, sinon false
     */
    boolean loadDeliveryPlan(String filename)
    {
        try
        {
            DeliveryPlan plan = XMLLoader.loadDeliveryPlan(Controller.getInstance().getCityMap(), filename);
            Controller.getInstance().setDeliveryPlan(plan);
            Controller.getInstance().getCircuitManager().setSolution(null);
            this.mapView.repaint();
        }
        catch (Exception e)
        {
            String message = String.format("Le fichier d'inventaire n'a pas été correctement chargé\n%s", e.getMessage());
            JOptionPane.showMessageDialog(null, message,"Erreur de chargement de l'inventaire",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Permet de charger un circuit
     * @param cycleNumber Le nombre de cyclistes
     * @return true si le circuit a bien été chargé, sinon false
     */
    boolean loadCircuit(int cycleNumber)
    {
        try
        {
            DeliveryPlan plan = Controller.getInstance().getDeliveryPlan();
            Controller.getInstance().computeCircuit(plan, cycleNumber);
            this.mapView.repaint();
        }
        catch (Exception e)
        {
            System.err.println(e);
            return false;
        }
        return true;
    }
}
