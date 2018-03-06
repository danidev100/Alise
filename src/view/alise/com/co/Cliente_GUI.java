
package view.alise.com.co;

import com.sun.awt.AWTUtilities;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.naming.NamingException;

/**
 * Clase que carga navegador, y Alice.
 */

public final class Cliente_GUI extends javax.swing.JFrame {

    // Variables globales.
    private WebBrowser webBrowser = new WebBrowser();
    private final Chat c = new Chat();
    private static boolean visible = false;

    // Variables usadas para obtener datos de Usuario
    private int x, y;

    // Definición del Constructor Cliente_GUI
    public Cliente_GUI() {
        // Quita bordes, instancia del navegador.
        setUndecorated(true);
        // Agrega los componentes
        initComponents();
        webBrowser.main();
        // Diseño de la ventana.
        ConfiguracionVentana();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblImg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(500, 180));
        setResizable(false);

        lblImg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/b.gif"))); // NOI18N
        lblImg.setToolTipText("");
        lblImg.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblImg.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                lblImgMouseDragged(evt);
            }
        });
        lblImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImgMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblImgMousePressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImg, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImg, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblImgMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImgMousePressed
        // Obtiene valores para las coordenadas.
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_lblImgMousePressed

    private void lblImgMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImgMouseDragged
        // TODO add your handling code here:
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
        c.setLocation(this.getLocation().x + evt.getX() - x - 25, this.getLocation().y + evt.getY() - y + 260);
    }//GEN-LAST:event_lblImgMouseDragged

    private void lblImgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImgMouseClicked
        // Instancia Alice, cierra el navegador, y hace visible a Alice.
        Alice a = new Alice();
        a.setVisible(true);
        webBrowser.setActive(false);
        c.setVisible(false);
        c.setVisible1(false);
        this.dispose();
    }//GEN-LAST:event_lblImgMouseClicked

    /**
     * @param args the command line arguments
     * @throws javax.naming.NamingException
     */
    public static void main(String args[]) throws NamingException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cliente_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
          new Cliente_GUI().setVisible(true);
        });
    }
    
    // Configura diseño de la ventana, e inicialización del navegador.
    private void ConfiguracionVentana() {
        // Activa la transparencia.
        AWTUtilities.setWindowOpaque(this, false);

        // Establece el inicio del Frame en la parte inferior derecha.
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        x = (int) rect.getMaxX() - getWidth();
        y = (int) rect.getMaxY() - getHeight();
        setLocation(x - 80, y - 450);
        setVisible(true);
        setVisible1(true);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel lblImg;
    // End of variables declaration//GEN-END:variables

    // Getters y Setters
    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public static boolean isVisible1() {
        return visible;
    }

    public void setVisible1(boolean visible) {
        Cliente_GUI.visible = visible;
    }
}
