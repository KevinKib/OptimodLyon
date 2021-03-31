package optimodLyon.controller.ihm;

import optimodLyon.IHM.PickupDeliveryDialogView;
import optimodLyon.model.PickupAndDeliveryForm;
import optimodLyon.model.Segment;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class DialogController implements ItemListener, ActionListener, ChangeListener {

    private JComboBox<Segment> pickupFirstWay;
    private JComboBox<Segment> pickupSecondWay;
    private JComboBox<Segment> deliveryFirstWay;
    private JComboBox<Segment> deliverySecondWay;

    private PickupDeliveryDialogView pickupDeliveyDialogView;

    private JSpinner deliveryDurationSpinner;

    private JSpinner pickupDurationSpinner;

    private JButton validateButton;

    PickupAndDeliveryForm pickupAndDeliveryForm;


    public DialogController(PickupDeliveryDialogView pickupDeliveryDialogView, JButton okButton, JButton cancelButton){
        this.pickupDeliveyDialogView = pickupDeliveryDialogView;

        this.pickupFirstWay = this.pickupDeliveyDialogView.getPickupFirstWay();
        this.pickupSecondWay = this.pickupDeliveyDialogView.getPickupSecondWay();
        this.deliveryFirstWay = this.pickupDeliveyDialogView.getDeliveryFirstWay();
        this.deliverySecondWay = this.pickupDeliveyDialogView.getDeliverySecondWay();

        this.deliveryDurationSpinner = this.pickupDeliveyDialogView.getDeliveryDurationSpinner();
        this.pickupDurationSpinner = this.pickupDeliveyDialogView.getPickupDurationSpinner();

        this.validateButton = okButton;

        this.pickupFirstWay.addItemListener(this);
        this.pickupSecondWay.addItemListener(this);
        this.deliveryFirstWay.addItemListener(this);
        this.deliverySecondWay.addItemListener(this);

        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

        this.pickupDurationSpinner.addChangeListener(this);
        this.deliveryDurationSpinner.addChangeListener(this);

        okButton.setEnabled(false);

        pickupAndDeliveryForm = new PickupAndDeliveryForm();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object o = e.getSource();
        if(o instanceof JComboBox)
        {
            JComboBox<Segment> comboBox = (JComboBox<Segment>) o;

            // Premiere voie pour le pickup
            if(comboBox.equals(this.pickupFirstWay) && this.pickupFirstWay.getSelectedIndex()!=0){
                Segment segmentRef = (Segment) comboBox.getSelectedItem();
                List<Segment> intersectionSegment = this.pickupDeliveyDialogView.getController().getCityMap().getIntersectionSegments(segmentRef);
                this.pickupSecondWay.removeAllItems();
                for(Segment s : intersectionSegment){
                    this.pickupSecondWay.addItem(s);
                }
                this.pickupSecondWay.setEnabled(true);
                this.pickupAndDeliveryForm.setPickupFirstWay((Segment) comboBox.getSelectedItem());
                this.pickupAndDeliveryForm.setPickupSecondWay(intersectionSegment.get(0));

            // Deuxieme voie pour el pickup
            } else if(comboBox.equals(this.pickupSecondWay) && this.pickupSecondWay.getSelectedIndex()!=0){
                this.pickupAndDeliveryForm.setPickupSecondWay((Segment) comboBox.getSelectedItem());

            // Premiere voie pour le delivery
            } else if(comboBox.equals(this.deliveryFirstWay) && this.deliveryFirstWay.getSelectedIndex()!=0){
                Segment segmentRef = (Segment) comboBox.getSelectedItem();
                List<Segment> intersectionSegment = this.pickupDeliveyDialogView.getController().getCityMap().getIntersectionSegments(segmentRef);
                this.deliverySecondWay.removeAllItems();
                for(Segment s : intersectionSegment){
                    this.deliverySecondWay.addItem(s);
                }
                this.deliverySecondWay.setEnabled(true);
                this.pickupAndDeliveryForm.setDeliveryFirstWay((Segment) comboBox.getSelectedItem());
                this.pickupAndDeliveryForm.setDeliverySecondWay(intersectionSegment.get(0));

                // Deuxieme voie pour le delivery
            } else if(comboBox.equals(this.deliverySecondWay) && this.pickupSecondWay.getSelectedIndex()!=0){
                this.pickupAndDeliveryForm.setDeliverySecondWay((Segment) comboBox.getSelectedItem());
            }
        }

        if (this.pickupAndDeliveryForm.isValid())
        {
            this.validateButton.setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if(actionCommand.equals(PickupDeliveryDialogView.ACTION_OK)){
            this.pickupDeliveyDialogView.getController().addRequest(this.pickupAndDeliveryForm);
            this.pickupDeliveyDialogView.dispose();
        } else if(actionCommand.equals(PickupDeliveryDialogView.ACTION_CANCEL)) {
            this.pickupDeliveyDialogView.dispose();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        Object o = e.getSource();
        if (o instanceof JSpinner)
        {
            JSpinner source = (JSpinner) o;
            if (source.equals(this.deliveryDurationSpinner))
            {
                this.pickupAndDeliveryForm.setDeliveryDuration((int) source.getValue());
            }
            else if (source.equals(this.pickupDurationSpinner))
            {
                this.pickupAndDeliveryForm.setPickupDuration((int) source.getValue());
            }
        }
    }
}
