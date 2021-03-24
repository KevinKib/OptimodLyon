package optimodLyon;

import optimodLyon.IHM.OptimodFrame;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.lang.reflect.InvocationTargetException;

public class MainTest {
    private OptimodFrame mainFrame;

    public void run() {
        mainFrame = new OptimodFrame("Optimod'Lyon");
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        // Apply look n'feel
        UIManager.setLookAndFeel(new NimbusLookAndFeel());

        // Run Optimod'Lyon App
        Runnable app = new OptimodLyonApp();
        try {
            SwingUtilities.invokeAndWait(app);
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
