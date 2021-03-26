package optimodLyon.IHM;

import optimodLyon.controller.ihm.NavigationController;

import javax.swing.*;
import java.awt.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Vue qui va contenir les boutons de chargement de fichiers XML
 * @author Dorian TERBAH
 * @since 1.0
 */
public class NavigationView extends JPanel
{
    /**
     * Enumeration pour connaitre quels boutons sont activés dans la NavigationView
     */
    public enum NavigationViewState
    {
        UNDEFINED, MAP_LOADED, DELIVERY_PLAN_LOADED, CIRCUIT_CALCULATED
    }

    public enum FileType
    {
        MAP, DELIVERY_PLAN
    }

    /**
     * Etat de la vue
     */
    private NavigationViewState state;

    /**
     * Composant qui va contenir le titre de l'application
     */
    private JLabel appNameLabel;

    /**
     * Bouton qui va permettre de charger la carte
     */
    private JButton loadMapButton;

    /**
     * Bouton qui va permettre de charger l'inventaire
     */
    private JButton loadProgramButton;

    /**
     * Bouton qui permet de calculer le circuit de pickup-delivery
     */
    private JButton calculateCircuitButton;

    /**
     * Label du bouton pour charger une map
     */
    private static final String LOAD_MAP_LABEL = "Charger une carte";

    /**
     * Label du bouton pour charger un programme
     */
    private static final String LOAD_PROGRAM_LABEL = "Charger un programme de Pickup & Delivery";

    /**
     * Label du bouton pour calculer le circuit
     */
    private static final String CALCULATE_CIRCUIT_LABEL = "Calculer la tournée de Pickup & Delivery";

    /** Constantes pour les actions command **/

    /**
     * Constante pour identifier le bouton de chargement carte
     */
    public static final String LOAD_MAP_ACTION_COMMAND = "LOAD_MAP_ACTION_COMMAND";

    /**
     * Constante pour identifier le bouton de chargement d'un programme
     */
    public static final String LOAD_PROGRAM_ACTION_COMMAND = "LOAD_PROGRAM_ACTION_COMMAND";

    /**
     * Constante pour identifier le bouton pour calculer le circuit
     */
    public static final String CALCULATE_CIRCUIT_ACTION_COMMAND = "CALCULTAE_CIRCUIT_ACTION_COMMAND";

    /**
     * Référence vers la fenetre de l'application
     */
    private final OptimodFrame window;

    /**
     * Constructeur de la classe NavigationView
     * @param window La fenetre de l'application
     */
    public NavigationView(final OptimodFrame window)
    {
        super(new GridLayout(1, 4, 1, 0));
        this.window = window;

        this.setBackground(Color.BLUE);

        // Name label
        this.appNameLabel = new JLabel();
        this.appNameLabel.setText(OptimodFrame.APP_NAME);
        this.appNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.appNameLabel.setVerticalTextPosition(SwingConstants.CENTER);
        this.appNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        this.appNameLabel.setForeground(Color.WHITE);

        // Map button
        this.loadMapButton = buildButton(LOAD_MAP_LABEL);
        this.loadMapButton.setActionCommand(LOAD_MAP_ACTION_COMMAND);

        // Program button
        this.loadProgramButton = buildButton(LOAD_PROGRAM_LABEL);
        this.loadProgramButton.setActionCommand(LOAD_PROGRAM_ACTION_COMMAND);

        // Tour Button
        this.calculateCircuitButton = buildButton(CALCULATE_CIRCUIT_LABEL);
        this.calculateCircuitButton.setActionCommand(CALCULATE_CIRCUIT_ACTION_COMMAND);

        // Add component to the bar panel
        this.add(this.appNameLabel);
        this.add(this.loadMapButton);
        this.add(this.loadProgramButton);
        this.add(this.calculateCircuitButton);

        this.loadProgramButton.setEnabled(false);
        this.calculateCircuitButton.setEnabled(false);

        this.setState(NavigationViewState.UNDEFINED);

        // création du controleur
        List<JButton> buttons = new ArrayList<>();
        buttons.add(this.loadMapButton);
        buttons.add(this.calculateCircuitButton);
        buttons.add(this.loadProgramButton);

        NavigationController controller = new NavigationController(this, buttons);
    }

    /**
     * Permet de changer l'état actuel de la vue
     * @param newState Le nouvel état
     */
    public void setState(NavigationViewState newState)
    {
        this.state = newState;
        this.updateView();
    }

    /**
     * Permet de demander à la fenetre principale de charger un fichier
     * @param fileType Le type du fichier
     * @param filename le nom du fichier à charger
     * @return true si le fichier a bien été chargé, sinon false
     */
    public boolean sendFileToWindow(FileType fileType, String filename)
    {
        if (fileType.equals(FileType.MAP))
        {
            return this.window.loadCityMap(filename);
        }
        else if (fileType.equals(FileType.DELIVERY_PLAN))
        {
            return this.window.loadDeliveryPlan(filename);
        }

        return false;
    }

    public boolean notifyWindowToLoadCircuit(int cycleNumber)
    {
        return this.window.loadCircuit(cycleNumber);
    }

    /**
     * Permet de mettre à jour la vue en fonction de son nouvel état
     */
    private void updateView()
    {
        if (this.state.equals(NavigationViewState.MAP_LOADED))
        {
            this.loadProgramButton.setEnabled(true);
        }
        else if (this.state.equals(NavigationViewState.DELIVERY_PLAN_LOADED))
        {
            this.calculateCircuitButton.setEnabled(true);
        }
    }

    private JButton buildButton(String text) {
        JButton button = new JButton();
        button.setText(
                String.format("<html><body style=\"text-align: center;  text-justify: inter-word;\">%s</body></html>",
                        text)
        );
        button.setBackground(null);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setBorderPainted(false);
        return button;
    }
}
