package optimodLyon.IHM;

import optimodLyon.controller.ihm.LeftMenuController;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Composant graphique qui donne les fonctionnalité d'ajout d'un point de pickup & delivery et de la modification de l'ordre dans l'inventaire.
 * Il affiche également la légende.
 *
 * @author Dorian TERBAH
 * @since 1.0
 */
public class LeftMenuView extends JPanel {

    /**
     * Référence vers la fenetre de l'application
     */
    private final OptimodFrame window;

    /**
     * Constante pour identifier le bouton de d'ajout d'un point de pickup and delivery
     */
    public static final String ADD_PICKUP_AND_DELIVERY_POINTS = "ADD_PICKUP_AND_DELIVERY_POINTS";

    /**
     * Constante pour identifier le bouton de d'ajout de changement de l'ordre de livraison
     */
    public static final String MODIFY_ITINERARY_ORDER = "MODIFY_ITINERARY_ORDER";

    /**
     * Bouton qui permet d'ajouter un point de Pickup & Delivery
     */
    private JButton addPDButton;

    /**
     * Bouton qui permet de modifier l'ordre des requêtes dans l'inventaire
     */
    private JButton modifyOrderButton;

    /**
     * Composant graphique qui permet d'afficher la légende de la carte
     */
    private JPanel legendPanel;

    /**
     * Constructeur de la classe LeftMenuView
     * @param window La référence de la fenêtre de l'application
     */
    public LeftMenuView (final OptimodFrame window) {
        super(new GridLayout(3,1));
        this.window = window;

        // Bouton d'ajout du couple pickup and delivery
        addPDButton = new JButton();
        addPDButton.setText("Ajouter un point de Pickup & Delivery");
        addPDButton.setActionCommand(ADD_PICKUP_AND_DELIVERY_POINTS);

        // Bouton de modification de l'ordre de passage du livreur
        modifyOrderButton = new JButton();
        modifyOrderButton.setText("Modifier l'ordre");
        modifyOrderButton.setActionCommand(MODIFY_ITINERARY_ORDER);

        // Légende
        legendPanel = new LegendView(this.window);

        this.add(addPDButton);
        this.add(modifyOrderButton);
        this.add(legendPanel);

        // création du controleur
        List<JButton> buttons = new ArrayList<>();
        buttons.add(this.addPDButton);
        buttons.add(this.modifyOrderButton);

        LeftMenuController leftMenuController = new LeftMenuController(this, buttons, window.getController());
    }

    /**
     * @return La référence de la fenêtre de l'application
     */
    public OptimodFrame getWindow() {
        return window;
    }
}
