
package core.alise.com.co;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class EnvioMensaje extends Thread {
    
    /**
     * Clase que envia mensajes desde el servidor al cliente.
     */
    
    // Variables para varios contextos.
    private static int x, y;
    
    @Override
    public void run() {
        // Crea el díalogo para el mensaje
        dialogoMensaje();
    }

    // Getters y Setters.
    public static int getX() {
        return x;
    }

    public static int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    // Método encargado de mostrar el mensaje.
    private void dialogoMensaje() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }

        // Instancia un dialogo para mostrar.
        final JDialog dialog = new JDialog();

        // Configuración del dialogo.
        JOptionPane op = new JOptionPane("<html><body><p style='width: 150px;'>"+
                Server.getMsg()+"</p></body></html>", JOptionPane.INFORMATION_MESSAGE,
                JOptionPane.CLOSED_OPTION);
        op.addPropertyChangeListener((PropertyChangeEvent evt) -> {
            String name1 = evt.getPropertyName();
            if ("value".equals(name1)) {
                dialog.dispose();
            }
        });

        // Configuración de estilos y posición del dialogo.
        dialog.setUndecorated(true);
        dialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.GRAY));
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(null);
        dialog.add(op);
        dialog.pack();
        
        // Ubicación del dialogo en la parte inferior derecha.
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        dialog.setLocation(getX(), getY());
        dialog.setVisible(true);
    }
}
