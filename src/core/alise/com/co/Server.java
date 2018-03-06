package core.alise.com.co;

import view.alise.com.co.Chat;
import view.alise.com.co.Alice;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    /**
     * Clase que permite implementar la conexión al Socket
     * y recibir las peticiones de Mensajes.
     */
    
    // Definición de variables.
    private static ServerSocket serverSocket;
    private static Socket socket;
    private BufferedReader bufferedReader;
    private static String msg = "";
    private DataOutputStream outputStream;
    private final int port = 3000;
    private final Alice alice;
    private final String hello = "";
    public static String user = "", givenName = "", pc = "";

    // Constructor de la clase Server
    public Server() throws IOException {
        // Instancia, e inicia el Socket cliente.
//        cliente = new Cliente();
//        cliente.startSocket(hello);
        // Muestra a Alice en primera instancia.
        alice = new Alice();
        alice.setVisible(true);
    }

    // Inicio del Socket.
    public void startSocket() {
        try {
            // Inicia el Socket Servidor.
            serverSocket = new ServerSocket(port);
            socket = new Socket();

            // Nombre del usuario enviado por Socket.
            givenName = Cliente_Datos.NOMBRE_USUARIO;

            while (true) {
                // Acepta la conexión
                socket = serverSocket.accept();
                System.out.println("Conexión aceptada....");
                // Asigna el buffer para leer la información ingresada
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // Lee el texto del buffer
                msg = bufferedReader.readLine();
                // Muestra el mensaje
                System.out.println("El mensage ingresado es = " + msg);
                // Si ventana de Navegador esta activa, mostrar mensaje en un tamaño.
                if (Chat.isVisible1()) {
                    // Localiza el mensaje mas arriba.
                    EnvioMensaje em = new EnvioMensaje();
                    em.setX(1020);
                    em.setY(90);
                    em.start();
                    // En caso que no este visible.
                } else {
                    // Localiza el mensaje mas abajo.
                    EnvioMensaje em = new EnvioMensaje();
                    em.setX(1100);
                    em.setY(450);
                    em.start();
                }
                
                // Instancia el objeto de salida de datos
                outputStream = new DataOutputStream(socket.getOutputStream());
                // Envia una respuesta al mensaje.
                outputStream.writeUTF("Hola " + givenName + ", Como puedo ayudarte?");
                // Cierra el Socket.
                socket.close();
            }
        } catch (IOException ex) {
            // Excepción al inicio del Socket.
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Método para mostrar el Mensaje.
    public static String getMsg() {
        return msg;
    }
}
