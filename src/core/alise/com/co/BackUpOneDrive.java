
package core.alise.com.co;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Ejecutar Back Up en oneDrive por sistema de archivos.
 */

public class BackUpOneDrive extends Thread {
    
     //Inicialización de variables para ejecución de Scripts.
    private static String givenName = "";
    private static String MSJ_BACK_UP = "";
    
    // Método que ejecuta el hilo.
    @Override
    public void run() {
        do {
            try {
                // Intenta ejecutar el Back Up.
              doBakcUp();
              // Obtiene Nombre de Usuario
              givenName = Cliente_Datos.NOMBRE_USUARIO;
              // Valida si la ruta existe. En ese caso, hace Back Up
              String ruta = System.getenv("userprofile") + "\\OneDrive\\BACK_UP";
              // Asigna ruta donde se encuentra el archivo.
              File f = new File(ruta);
              // Si la ruta existe, realiza Back Up
              if (f.exists()) {
                  mensajeBackUp();
              } else {
                  // Muestra mensaje de error de OneDrive.
                  mensajeBackUpError();
              }
              // Timer para ejecutar Back Up cada día.
              Thread.sleep(86400000);
            } catch (InterruptedException ex) {
                Logger.getLogger(BackUpOneDrive.class.getName()).log(Level.SEVERE, null, ex);
            }
            // Repetira el ciclo hasta que la app Finalice.
        } while (true);
    }
    
    // Proceso que crea Back Up del disco D en OneDrive.
    public final void doBakcUp() {
        try {
            // Almacenar el comando en un String
            String cmdTemp = "C:\\Windows\\System32\\cmd.exe /C ROBOCOPY D:\\Nueva "
                    + "%USERPROFILE%\\OneDrive\\BACK_UP /E /R:0 "
                    + "/MAXAGE:8 /MT:24 > %USERPROFILE%\\xcopy.txt";
            // Intentar ejecutar el proceso
            Process p = Runtime.getRuntime().exec(cmdTemp);
            // Obtiene el resultado del Script
            p.getOutputStream().close();
            // Crea un String para mostrar la salida del comando
            String salida;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                // Mientras hayan datos...
                while ((salida = br.readLine()) != null) {
                    System.out.println(salida);
                }
            }
        } catch (IOException e) {
            // Excepción en la ejecución del proceso.
            JOptionPane.showMessageDialog(null, "No se pudo Configurar el BackUp en su OneDrive");
            Logger.getLogger(BackUpOneDrive.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    //Mensaje que muestra estado de Back Up.
    private void mensajeBackUp() {
        try {
            // Establece el estilo básico del dialogo.
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }

        // Instancia de un nuevo dialogo.
        final JDialog dialog = new JDialog();
        
        // Mensaje de Back Up, y configuración de JDialog.
        MSJ_BACK_UP = "Hola " + givenName + ". Se acaba "
            + "de realizar Back Up de su información en la unidad de OneDrive de "
            + "su cuenta de correo de Office 365." ;
        JOptionPane op = new JOptionPane("<html><body><p style='width: 150px;'>"+MSJ_BACK_UP
                + "</p></body></html>",
                JOptionPane.INFORMATION_MESSAGE, JOptionPane.CLOSED_OPTION);
        op.addPropertyChangeListener((PropertyChangeEvent evt) -> {
            String name1 = evt.getPropertyName();
            if ("value".equals(name1)) {
                dialog.dispose();
            }
        });

        // Configuración del Dialogo para posición, diseño y margen.
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
        dialog.setLocation(1100, 380);
        dialog.setVisible(true);
    }
    
    // Mensaje error por no configurar OneDrive en el equipo.
    private void mensajeBackUpError() {
        try {
            // Establece el estilo básico del dialogo.
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }

        // Instancia de un nuevo dialogo.
        final JDialog dialog = new JDialog();
        
        MSJ_BACK_UP = "Hola " + givenName + ". No se ha detectado una "
                + "cuenta de correo de Office 365 configurada en su equipo.";
        JOptionPane op = new JOptionPane("<html><body><p style='width: 150px;'>" + MSJ_BACK_UP
                + "</p></body></html>",
                JOptionPane.INFORMATION_MESSAGE, JOptionPane.CLOSED_OPTION);
        op.addPropertyChangeListener((PropertyChangeEvent evt) -> {
            String name1 = evt.getPropertyName();
            if ("value".equals(name1)) {
                dialog.dispose();
            }
        });

        // Configuración del Dialogo para posición, diseño y margen.
        dialog.setUndecorated(true);
        dialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.GRAY));
        dialog.setLayout(new BorderLayout());
        dialog.add(op);
        dialog.pack();

        // Ubicación del dialogo en la parte inferior derecha.
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        dialog.setLocation(1100, 380);
        dialog.setVisible(true);
    }
}
