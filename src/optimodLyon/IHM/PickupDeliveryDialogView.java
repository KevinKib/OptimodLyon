package optimodLyon.IHM;
import optimodLyon.controller.Controller;
import optimodLyon.controller.ihm.DialogController;
import optimodLyon.model.Segment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;
import java.awt.*;

public class PickupDeliveryDialogView extends JDialog {

    public static final String ACTION_OK = "ACTION_OK";
    public static final String ACTION_CANCEL = "ACTION_CANCEL";

    private JButton okButton;
    private JButton cancelButton;

    private JComboBox pVoie1;
    private JComboBox pVoie2;
    private JComboBox dVoie1;
    private JComboBox dVoie2;

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

        DialogController dialogController = new DialogController(this, pVoie1, pVoie2, dVoie1, dVoie2, okButton, cancelButton);

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

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        JPanel voie1 = buildComboBox(voies, "Voie n°1", true);
        JPanel voie2 = buildComboBox(new ArrayList<>(), "Voie n°2", false);
        if(name.equals("Pickup")){
            this.pVoie1 = (JComboBox) voie1.getComponent(1);
            this.pVoie2 = (JComboBox) voie2.getComponent(1);
        } else {
            this.dVoie1 = (JComboBox) voie1.getComponent(1);
            this.dVoie2 = (JComboBox) voie2.getComponent(1);
        }

        JPanel dureePickup = buildInput("Durée de récupération (en seconde)");

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
        JTextField input = new JTextField();
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
}
