package optimodLyon.IHM;

import javax.swing.*;
import java.awt.*;

/**
 * Main class of the application Optimod'Lyon.
 * Contains the map, and the main functionalities of the app.
 */
public class OptimodFrame extends JFrame {
    private JPanel mainPanel;

    private JPanel buttonBarPanel;
    private JLabel appNameLabel;
    private JButton mapButton;
    private JButton programButton;
    private JButton tourButton;

    private JPanel centerPanel;
    private JLabel mapPlaceholder;

    private JPanel leftPanel;
    private JButton addPDButton;
    private JButton modifyOrderButton;
    private JLabel legendPlaceholder;

    public OptimodFrame(String name) {
        super();
        // Frame construction
        this.setSize(new Dimension(900, 640));

        // Main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Button bar
        buildButtonBar();
        mainPanel.add(buttonBarPanel, BorderLayout.NORTH);

        // Center Panel
        buildCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Left Panel
        buildLeftPanel();
        mainPanel.add(leftPanel, BorderLayout.WEST);

        // Set main panel
        this.setContentPane(mainPanel);

        // Default operations
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void buildButtonBar() {
        // Init bar pannel
        buttonBarPanel = new JPanel();
        buttonBarPanel.setLayout(new GridLayout(1, 4, 1, 0));
        buttonBarPanel.setBackground(Color.BLUE);

        // Name label
        appNameLabel = new JLabel();
        appNameLabel.setText("Optimod'Lyon");
        appNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        appNameLabel.setVerticalTextPosition(SwingConstants.CENTER);
        appNameLabel.setFont(new Font("Arial", Font.BOLD, 28));
        appNameLabel.setForeground(Color.WHITE);

        // Map button
        mapButton = buildButton("Charger une carte");

        // Program button
        programButton = buildButton("Charger un programme de Pickup & Delivery");

        // Tour Button
        tourButton = buildButton("Calculer la tournée de Pickup & Delivery");

        // Add component to the bar panel
        buttonBarPanel.add(appNameLabel);
        buttonBarPanel.add(mapButton);
        buttonBarPanel.add(programButton);
        buttonBarPanel.add(tourButton);
    }

    private JButton buildButton(String text) {
        JButton button = new JButton();
        button.setText(
                String.format("<html><body style=\"text-align: center;  text-justify: inter-word;\">%s</body></html>",
                        text)
        );
        button.setBackground(null);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBorderPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return button;
    }

    private void buildCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        mapPlaceholder = new JLabel();
        mapPlaceholder.setText("Map");
        mapPlaceholder.setHorizontalAlignment(SwingConstants.CENTER);
        mapPlaceholder.setVerticalTextPosition(SwingConstants.CENTER);
        mapPlaceholder.setBackground(Color.GRAY);

        centerPanel.add(mapPlaceholder, BorderLayout.CENTER);
    }

    private void buildLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());

        addPDButton = new JButton();
        addPDButton.setText("Ajouter un P&D");
        modifyOrderButton = new JButton();
        modifyOrderButton.setText("Modifier l'ordre");
        legendPlaceholder = new JLabel();
        legendPlaceholder.setText("Légende");
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
