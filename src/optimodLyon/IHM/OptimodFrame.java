package optimodLyon.IHM;

import optimodLyon.controller.Controller;
import optimodLyon.io.XMLLoader;
import optimodLyon.model.CityMap;
import optimodLyon.model.DeliveryPlan;

import javax.swing.*;
import java.awt.*;

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
     * Etat de la fenetre
     */
    private OptimodFrameState state;

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
     * Le controleur de la fenetre
     */
    private Controller controller;

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

        this.controller = new Controller();

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

        // Left Panel
        buildLeftPanel();
        mainPanel.add(leftPanel, BorderLayout.WEST);

        // Set main panel
        this.setContentPane(mainPanel);
    }

    /**
     * Mets à jour l'état courant de la fenetre
     * @param newState Le nouvel etat
     */
    public void updateState(OptimodFrameState newState)
    {
        this.state = newState;
        this.updateView();
    }

    /**
     * Demande au controleur de la fenetre de charger le fichier de city map
     * @param filename Le fichier contenant les informations de la map
     * @@return true si le fichier a bien été chargé, sinon false
     */
    boolean loadCityMap(String filename)
    {
        try
        {
            CityMap map = XMLLoader.loadMap(filename);
            this.controller.setCityMap(map);
            this.controller.setDeliveryPlan(null);
            this.mapView.updateCityMap(map);
        }
        catch (Exception e)
        {
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
            DeliveryPlan plan = XMLLoader.loadDeliveryPlan(this.controller.getCityMap(), filename);
            this.controller.setDeliveryPlan(plan);
            this.mapView.updateDeliveryPlan(plan);
        }
        catch (Exception e)
        {
            System.err.println(e);
            return false;
        }
        return true;
    }

    private void updateView()
    {
    }

    private void buildLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());

        addPDButton = new JButton();
        addPDButton.setText("Ajouter un point de Pickup & Delivery");
        modifyOrderButton = new JButton();
        modifyOrderButton.setText("Modifier l'ordre");
        legendPlaceholder = new JLabel();
        //legendPlaceholder.setText("Légende");
        leftPanel.setBackground(Color.white);

        GridBagConstraints cons = new GridBagConstraints();
        cons.gridy = 1;
        leftPanel.add(addPDButton, cons);
        cons.gridy++;
        leftPanel.add(modifyOrderButton, cons);
        cons.gridy++;
        cons.gridheight = GridBagConstraints.REMAINDER;
        leftPanel.add(legendPlaceholder, cons);
    }
}
