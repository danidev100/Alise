/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.alise.com.co;

import com.sun.awt.AWTUtilities;
import core.alise.com.co.BackUpCuentaUsuario;
import core.alise.com.co.RegistrarServicio;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import netscape.javascript.JSObject;

/**
 *
 * @author yosoriod
 */
public class WebBrowser_old {

    private Scene scene;
    private MyBrowser myBrowser;
    private JFrame frame;
    private JFXPanel fxPanel;
    private Label labelFromJavascript;
    private boolean isActive;
    private JavaApp bridge;
    private String conversation;
    private JFrame parent;

    public void main() {
        //launch(args);
        SwingUtilities.invokeLater(() -> {
            conversation = "";
            System.gc();
            initAndShowGUI();
        });

    }

    public class JavaApp {
        
        public void backup() {
            // Ejecuta Back Up haciendo referencia al botón de PHP
            System.out.println(System.getenv("userprofile"));
            if (System.getProperty("os.name").equals("Windows 7")) {
                System.out.println("Sistema Windows 7");
                // Realiza Back Up de diferentes ubicaciones para W7
                BackUpCuentaUsuario.ejecutarBackUp("\\\"C:\\Users\\%username%\\test\\\"", "\\\"C:\\Users\\%username%\\\"", "test");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"D:\\\"", "\\\"D:\\\"", "Disco_D");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"" + System.getenv("userprofile") + "\\Desktop\\\"", "\\\"" + System.getenv("userprofile"), "Desktop");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"" + System.getenv("userprofile") + "\\Favorites\\\"", "\\\"" + System.getenv("userprofile"), "Favorites");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"C:\\Users\\DefaultAppData\\Roaming\\Microsoft\\Firmas\\\"", "\\\"C:\\Users\\DefaultAppData\\Roaming\\Microsoft\\", "Firmas");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"C:\\Users\\DefaultAppData\\Roaming\\Microsoft\\Outlook\\\"", "\\\"C:\\Users\\DefaultAppData\\Roaming\\Microsoft\\", "Outlook");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"C:\\Users\\DefaultAppData\\Roaming\\Microsoft\\Sticky Notes\\\"", "\\\"C:\\Users\\DefaultAppData\\Roaming\\Microsoft\\", "Sticky Notes");
                RegistrarServicio.registerService("juanrame", "El equipo: " + System.getenv("computername") + " ha realizado Back Up de su información personal");
            } else if (System.getProperty("os.name").equals("Windows 10")) {
                // Realiza Back Up para distintas ubicaciones en W10
                System.out.println("Realizando Back Up...");
                BackUpCuentaUsuario.ejecutarBackUp("\\\"C:\\Users\\%username%\\test\\\"", "\\\"C:\\Users\\%username%\\\"", "test");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"" + System.getenv("userprofile") + "\\test\\\"", "\\\"C:\\Users\\%username%\\\"", "Test");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"" + System.getenv("userprofile") + "\\test_2\\\"", "\\\"C:\\Users\\%username%\\\"", "TestNumero2");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"" + System.getenv("userprofile") + "\\Documents\\\"", "\\\"C:\\Users\\%username%\\\"", "Documents");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"" + System.getenv("userprofile") + "\\Desktop\\\"", "\\\"C:\\Users\\%username%\\\"", "Desktop");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"D:\\Users\\" + System.getenv("username") + "\\Favorites\\\"", "\\\"D:\\Users\\" + System.getenv("username"), "Favorites");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"D:\\Users\\Default\\AppData\\Roaming\\Microsoft\\Firmas\\\"", "\\\"D:\\Users\\Default\\AppData\\Roaming\\Microsoft\\", "Firmas");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"D:\\Users\\Default\\AppData\\Roaming\\Microsoft\\Outlook\\\"", "\\\"D:\\Users\\Default\\AppData\\Roaming\\Microsoft\\", "Outlook");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"D:\\Users\\Default\\AppData\\Roaming\\Microsoft\\Sticky Notes\\\"", "\\\"D:\\Users\\Default\\AppData\\Roaming\\Microsoft\\", "Sticky Notes");
                RegistrarServicio.registerService("juanrame", "El equipo: " + System.getenv("computername") + " ha realizado Back Up de su información personal");
            }
        }
        
        public void getText() {
            // Obtener el texto ingresado por el usuario
            JSObject window = (JSObject) myBrowser.getWebEngine().executeScript("window");
            String text = window.call("functionReturn").toString();
            System.out.println("Texto de PHP: " + text);
            RegistrarServicio.registerService("juanrame", text);
        }
        
        public void clean() {
            // Ejecuta limpieza haciendo referencia al botón de PHP.
//            System.out.println("Entra");
//            LimpiezaEquipo le = new LimpiezaEquipo();
//            RegistrarServicio.registerService("juanrame", "Se ha realizado mantenimiento en el equipo: " + System.getenv("computername"));
            
//            le.start();
        }

        public void initConversation() {
            System.out.println("Conversacion: " + conversation);
            JSObject window = (JSObject) myBrowser.getWebEngine().executeScript("window");
            window.call("inputSubmit");
        }

        public void validateConversation() {
            JSObject window = (JSObject) myBrowser.getWebEngine().executeScript("window");

            conversation = ((int) window.getMember("conversation_id")) + "";
            System.out.println("Conversacion: " + conversation);

            if (!conversation.isEmpty()) {
                window.call("start");
            }
        }
    }

    private void initAndShowGUI() {
        // This method is invoked on the EDT thread
        //Instacia del Frame principal
        bridge = new JavaApp();
        isActive = true;
        frame = new JFrame("Alise");
        //Instancia del FXPanel
        fxPanel = new JFXPanel();
        //Eliminando Bordes del Panel
        frame.setUndecorated(true);
        frame.add(fxPanel);
        //Dimensionando Frame
        frame.setSize(540, 450);
        //Exponiendo Frame
        frame.setVisible(true);
        //Definiendo Default Action
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Agregando Borde Personalizado
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, java.awt.Color.GRAY));

        //Situando en pantalla
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - frame.getWidth();
        int y = (int) rect.getMaxY() - frame.getHeight();
        frame.setLocation(x, y - 50);

        //Configurar bordes redondos.
        Shape forma = new RoundRectangle2D.Double(0, 0, frame.getBounds().width, frame.getBounds().height, 30, 30);
        AWTUtilities.setWindowShape(frame, forma);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //Inicializar Panel FX
                initFX(fxPanel);
            }
        });
    }

    private void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        //Creando Scene
        Scene scene = createScene();
        fxPanel.setScene(scene);
        //Exponiendo Scene
        fxPanel.setVisible(true);
    }

    private class MyBrowser extends Region {

        private HBox toolbar;
        private VBox toolbox;

        private final WebView webView = new WebView();
        private WebEngine webEngine = webView.getEngine();

        JavaApp bridge = new JavaApp();

        /**
         * Activa o desactiva los controles del chat
         *
         * @param path Direccion URL en la que se encuentra ubicado
         *
         */
        private void activateChat(String path) {
            String[] split = path.split("/");
            if (split[(split.length - 1)].compareTo("chatbox.html") == 0) {
                toolbox.setVisible(true);
                toolbar.setVisible(true);
            } else {
                toolbox.setVisible(false);
                toolbar.setVisible(false);
            }
            if (split[(split.length - 1)].compareTo("chatinit.html") == 0) {
                bridge.initConversation();
            }
        }

        /**
         * Constructor de clase
         */
        public WebEngine getWebEngine() {
            return webEngine;
        }

        public MyBrowser() {
            //URL Inicial del explorador
            final String startURL = "http://10.10.7.70:8080/Alice";
            final String trainerURL = "http://10.10.7.70:8080/Alice/html/train.html";
            //Cargar pagina
            webEngine.load(startURL);
            //Listener para capturar los cambios del explorador
            webEngine.getLoadWorker().stateProperty().addListener((ObservableValue<? extends Worker.State> ov, Worker.State oldState, Worker.State newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    JSObject window = (JSObject) webEngine.executeScript("window");
                    window.setMember("app", bridge);
                    window.setMember("conversation", conversation);
                    activateChat(webEngine.getLocation());
                }
            });

            JSObject window = (JSObject) webEngine.executeScript("window");
            window.setMember("app", bridge);
            window.setMember("conversation", conversation);

            //Definicion del textField para el mensaje del chat
            final TextField textField = new TextField();
            textField.setPromptText("Ingrese su conversación, presione Enter o Enviar para confirmar el mensaje.");
            //Definicion de Listener cuando se presione una tecla
            textField.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() {
                /**
                 * Captura la tecla presionada, valida qe sea haya presionado
                 * "Enter" de ser así envia el mensaje
                 *
                 * @param t
                 */
                @Override
                public void handle(javafx.scene.input.KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        webEngine.executeScript("submitInput('" + textField.getText() + "');");
                        textField.setText("");
                    }
                }
            }
            );
            //Definicion del botón Enter
            Button buttonEnter = new Button("Enter");
            //Definicion del Listener del botón Enter
            buttonEnter.setOnAction(new EventHandler<ActionEvent>() {
                /**
                 * Ejecuta el Script de Javascript para enviar el mensaje
                 *
                 * @param arg0 Argumentos del evento
                 */
                @Override
                public void handle(ActionEvent arg0) {
                    if (!textField.getText().isEmpty()) {
                        webEngine.executeScript("submitInput('" + textField.getText() + "');");
                        textField.setText("");
                    }
                }
            });

            Button buttonLog = new Button("Log");
            //Definicion del Listener del botón Enter
            buttonLog.setOnAction(new EventHandler<ActionEvent>() {
                /**
                 * Ejecuta el Script de Javascript para enviar el mensaje
                 *
                 * @param arg0 Argumentos del evento
                 */
                @Override
                public void handle(ActionEvent arg0) {
                    NewJDialog_old J = new NewJDialog_old(parent, true);

                    J.setModal(true);
                    J.setLocationRelativeTo(parent);
                    J.setVisible(isActive);

                    if (J.isValido()) {
                        JOptionPane.showConfirmDialog(parent, "Las credenciales fueron cargadas satisfactoriamente "
                                + "¿Desea ingresar al modulo de entrenamiento?",
                                "Logeo exitoso", JOptionPane.YES_NO_OPTION,
                                JOptionPane.INFORMATION_MESSAGE);
                        webEngine.load(trainerURL);
                    } else {
                        JOptionPane.showMessageDialog(parent, "No se han podido "
                                + "verificar sus credenciales", "Logeo fallido",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });

            //Definicion de la Toolbar
            toolbar = new HBox();
            toolbar.setPadding(new Insets(15, 10, 15, 10));
            toolbar.setSpacing(10);
            toolbar.setStyle("-fx-background-color: #336699");
            toolbar.getChildren().addAll(textField, buttonEnter, buttonLog);

            //Definicion del Toolbox
            toolbox = new VBox();
            labelFromJavascript = new Label();
            toolbox.getChildren().addAll(toolbar);
            labelFromJavascript.setText("Wait");

            //Agrengando componentes al Panel Root
            toolbar.setVisible(false);
            toolbox.setVisible(false);
            getChildren().add(toolbox);
            getChildren().add(webView);

        }

        //Dimensiona los componentes
        @Override
        protected void layoutChildren() {
            double w = getWidth();
            double h = getHeight();
            double toolboxHeight = toolbox.prefHeight(w);
            layoutInArea(webView, 0, 0, w, h - toolboxHeight, 0, HPos.CENTER, VPos.CENTER);
            layoutInArea(toolbox, 0, h - toolboxHeight, w, toolboxHeight, 0, HPos.CENTER, VPos.CENTER);
        }

    }

    /**
     * Toma la escena desde el Browser y la devuelve
     *
     * @return Scene
     */
    private Scene createScene() {
        myBrowser = new MyBrowser();
        scene = new Scene(myBrowser, 640, 480);
        return (scene);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        frame.setVisible(isActive);
        this.isActive = isActive;
    }

    public void setParent(JFrame parent) {
        this.parent = parent;
    }

}
