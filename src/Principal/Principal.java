package Principal;

import javax.swing.SwingUtilities;

public class Principal {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClienteGUI clienteGUI = new ClienteGUI();
            clienteGUI.setVisible(true);
        });
    }
}
