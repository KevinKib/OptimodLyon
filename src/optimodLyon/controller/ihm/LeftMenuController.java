package optimodLyon.controller.ihm;

import optimodLyon.IHM.LeftMenuView;
import optimodLyon.IHM.NavigationView;
import optimodLyon.IHM.PickupDeliveryDialogView;
import optimodLyon.model.Segment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class LeftMenuController implements ActionListener {

    /**
     * Référence vers la vue controlée par ce controleur
     */
    private final LeftMenuView view;

    private List<Segment> listVoie1;
    private final String[] listVoie2 = new String[]{"La doua", "Gaston Berger", "La rotonde", "La joconde"};



    /**
     * Constructeur de la classe NavigationController
     * @param view La Navigationview associée au controleur
     * @param buttons Les boutons qui seront controlés par ce controleur
     */
    public LeftMenuController(LeftMenuView view, List<JButton> buttons)
    {
        this.view = view;
        listVoie1 = new ArrayList<>();
        for (JButton button : buttons)
        {
            button.addActionListener(this);
        }
        this.initArray();
    }

    private void initArray(){
        Segment s1 = new Segment();
        s1.setName("La doua");
        Segment s2 = new Segment();
        s1.setName("Gaston Berger");
        Segment s3 = new Segment();
        s1.setName("Rotonde");
        Segment s4 = new Segment();
        s1.setName("Joconde");
        this.listVoie1.add(s1);
        this.listVoie1.add(s2);
        this.listVoie1.add(s3);
        this.listVoie1.add(s4);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        System.out.println("Renter");
        if(actionCommand.equals(LeftMenuView.ADD_PICKUP_AND_DELIVERY_POINTS)){
            System.out.println("Renter");
            PickupDeliveryDialogView pickupDeliveryDialogView = new PickupDeliveryDialogView(this.view.getWindow(), listVoie1);
            pickupDeliveryDialogView.setVisible(true);
        } else if(actionCommand.equals(LeftMenuView.MODIFY_ITINERARY_ORDER)) {

        }
    }
}
