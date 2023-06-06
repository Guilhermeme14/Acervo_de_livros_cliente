package Principal;

import javax.swing.*;

public class Principal {

    public static void main (String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClienteGUI().setVisible(true);
            }
        });
    }
    
}
