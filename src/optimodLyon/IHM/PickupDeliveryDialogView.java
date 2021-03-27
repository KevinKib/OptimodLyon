package optimodLyon.IHM;
import optimodLyon.ListUtils;
import optimodLyon.controller.Controller;
import optimodLyon.controller.ihm.DialogController;
import optimodLyon.model.Segment;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;

public class PickupDeliveryDialogView extends JDialog {

    public static final String ACTION_OK = "ACTION_OK";
    public static final String ACTION_CANCEL = "ACTION_CANCEL";

    private JButton okButton;
    private JButton cancelButton;

    private JComboBox pickupFirstWay;
    private JComboBox pickupSecondWay;
    private JComboBox deliveryFirstWay;
    private JComboBox deliverySecondWay;

    private JSpinner deliveryDurationSpinner;

    private JSpinner pickupDurationSpinner;

    private Controller controller;

    public PickupDeliveryDialogView(final JFrame frame, Controller controller){
        super(frame, "Ajout d'un couple Pickup&Delivery", Dialog.ModalityType.DOCUMENT_MODAL);
        this.controller = controller;
        this.setBounds(132, 132, 500, 500);

        Container dialogContainer = this.getContentPane();

        dialogContainer.setLayout(new BorderLayout());

        JPanel pickupPanel = buidPanelForm(controller.getCityMap().getSegments(), "Pickup");
        JPanel deliveryPanel = buidPanelForm(controller.getCityMap().getSegments(), "Delivery");
        dialogContainer.add(pickupPanel, BorderLayout.NORTH);
        dialogContainer.add(deliveryPanel, BorderLayout.CENTER);

        okButton = new JButton("Valider");
        okButton.setActionCommand(ACTION_OK);
        cancelButton = new JButton("Annuler");
        cancelButton.setActionCommand(ACTION_CANCEL);

        JPanel footerButtonPanel = new JPanel(new FlowLayout());
        footerButtonPanel.add(cancelButton);
        footerButtonPanel.add(okButton);

        DialogController dialogController = new DialogController(this, okButton, cancelButton);

        dialogContainer.add(footerButtonPanel, BorderLayout.SOUTH);
    }

    public Controller getController() {
        return controller;
    }



    private JPanel buidPanelForm(List<Segment> voies, String name){
        JLabel pickupLabel = new JLabel(name);
        pickupLabel.setFont(new Font(pickupLabel.getFont().getName(), Font.BOLD, 18));

        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);


        JPanel dureePickup = buildInput("Durée de récupération (en seconde)");
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        JPanel voie1 = buildComboBox(ListUtils.removeDuplicatesSegment(voies), "Voie n°1", true);
        JPanel voie2 = buildComboBox(new ArrayList<>(), "Voie n°2", false);
        if(name.equals("Pickup")){
            this.pickupFirstWay = (JComboBox) voie1.getComponent(1);
            this.pickupSecondWay = (JComboBox) voie2.getComponent(1);

            this.pickupDurationSpinner = (JSpinner) dureePickup.getComponent(1);
        } else {
            this.deliveryFirstWay = (JComboBox) voie1.getComponent(1);
            this.deliverySecondWay = (JComboBox) voie2.getComponent(1);

            this.deliveryDurationSpinner = (JSpinner) dureePickup.getComponent(1);
        }


        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BorderLayout());
        labelPanel.add(pickupLabel, BorderLayout.LINE_START);
        panel1.add(labelPanel);
        panel1.add(separator);

        JPanel formPickupPanel = new JPanel();
        formPickupPanel.setLayout(new BoxLayout(formPickupPanel, BoxLayout.Y_AXIS));
        formPickupPanel.add(voie1);
        formPickupPanel.add(voie2);
        formPickupPanel.add(dureePickup);

        panel1.add(formPickupPanel);
        return panel1;
    }

    private JPanel buildInput(final String inputLabelText){
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2,1));
        inputPanel.setBounds(10,10,200, 30);
        JSpinner input = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        JLabel inputLabel = new JLabel(inputLabelText);
        inputPanel.add(inputLabel, BorderLayout.NORTH);
        inputPanel.add(input, BorderLayout.SOUTH);

        return inputPanel;
    }

    private JPanel buildComboBox(List<Segment> options, final String comboTextLabel, boolean enabled){
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2,1));
        inputPanel.setBounds(10,10,200, 30);
        JComboBox<Segment> combo = new JComboBox<>();
        combo.addItem(new Segment("Selectionner une voie..."));
        for(Segment s : options){
            if(!s.getName().equals("")){
                combo.addItem(s);
            }
        }
        combo.setEnabled(enabled);
        JLabel inputLabel = new JLabel(comboTextLabel);
        inputPanel.add(inputLabel, BorderLayout.NORTH);
        inputPanel.add(combo, BorderLayout.SOUTH);

        return inputPanel;
    }

    // GETTERS

    public JSpinner getDeliveryDurationSpinner()
    {
        return this.deliveryDurationSpinner;
    }

    public JSpinner getPickupDurationSpinner()
    {
        return this.pickupDurationSpinner;
    }

    public JComboBox<Segment> getPickupFirstWay() {
        return pickupFirstWay;
    }

    public JComboBox<Segment> getPickupSecondWay() {
        return pickupSecondWay;
    }

    public JComboBox<Segment> getDeliveryFirstWay() {
        return deliveryFirstWay;
    }

    public JComboBox<Segment> getDeliverySecondWay() {
        return deliverySecondWay;
    }
}
