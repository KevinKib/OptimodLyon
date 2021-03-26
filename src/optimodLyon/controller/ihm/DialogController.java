package optimodLyon.controller.ihm;

import optimodLyon.IHM.PickupDeliveryDialogView;
import optimodLyon.model.PickupAndDeliveryForm;
import optimodLyon.model.Segment;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class DialogController implements ItemListener, ActionListener {

    private JComboBox<Segment> pVoie1;
    private JComboBox<Segment> pVoie2;
    private JComboBox<Segment> dVoie1;
    private JComboBox<Segment> dVoie2;

    private PickupDeliveryDialogView pickupDeliveyDialogView;

    PickupAndDeliveryForm pickupAndDeliveryForm;


    public DialogController(PickupDeliveryDialogView pickupDeliveryDialogView, JComboBox pVoie1, JComboBox pVoie2, JComboBox dVoie1, JComboBox dVoie2, JButton okButton, JButton cancelButton){
        this.pVoie1 = pVoie1;
        this.pVoie2 = pVoie2;
        this.dVoie1 = dVoie1;
        this.dVoie2 = dVoie2;

        this.pickupDeliveyDialogView = pickupDeliveryDialogView;

        this.pVoie1.addItemListener(this);
        this.pVoie2.addItemListener(this);
        this.dVoie1.addItemListener(this);
        this.dVoie2.addItemListener(this);

        pickupAndDeliveryForm = new PickupAndDeliveryForm();

        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

    }



    @Override
    public void itemStateChanged(ItemEvent e) {
        Object o = e.getSource();
        if(o instanceof JComboBox){
            JComboBox<Segment> comboBox = (JComboBox<Segment>) o;
            if(comboBox.equals(this.pVoie1) && this.pVoie1.getSelectedIndex()!=0){
                List<Segment> intersectionSegment = this.pickupDeliveyDialogView.getController().getCityMap().getIntersectionSegments((Segment) comboBox.getSelectedItem());
                for(Segment s : intersectionSegment){
                    this.pVoie2.addItem(s);
                }
                this.pVoie2.setEnabled(true);
                this.pickupAndDeliveryForm.setpVoie1((Segment) comboBox.getSelectedItem());
            } else if(comboBox.equals(this.pVoie2) && this.pVoie2.getSelectedIndex()!=0){
                this.pickupAndDeliveryForm.setpVoie2((Segment) comboBox.getSelectedItem());
            } else if(comboBox.equals(this.dVoie1) && this.dVoie1.getSelectedIndex()!=0){
                this.dVoie2.setEnabled(true);
                this.pickupAndDeliveryForm.setdVoie1((Segment) comboBox.getSelectedItem());
            } else if(comboBox.equals(this.dVoie2) && this.pVoie2.getSelectedIndex()!=0){
                this.pickupAndDeliveryForm.setpVoie2((Segment) comboBox.getSelectedItem());
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if(actionCommand.equals(PickupDeliveryDialogView.ACTION_OK)){
            System.out.println(this.pickupAndDeliveryForm);
        } else if(actionCommand.equals(PickupDeliveryDialogView.ACTION_CANCEL)) {
            this.pickupDeliveyDialogView.dispose();
        }
    }
}
