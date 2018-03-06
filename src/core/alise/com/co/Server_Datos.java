package core.alise.com.co;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server_Datos {

    private static ServerSocket serverSocket;
    private static Socket socket;
    private DataOutputStream salida;
    private String datosRecibidos;
    private static String[] resultado;
    public int port = 4000;

    public void startSocket() {
        try {
            // Inicio de Buffer, Socket server, y cliente.
            BufferedReader bReader;
            serverSocket = new ServerSocket(port);
            socket = new Socket();

            while (true) {
                // Escuchando para cuando se solicite una petición.
                System.out.println("Esperando conexión");
                // Inicia el Socket cliente, aceptando la petición.
                socket = serverSocket.accept();
                System.out.println("Se ha conectado un cliente.");
                // Generación del canal para la entrada y la salida de datos.
                bReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                salida = new DataOutputStream(socket.getOutputStream());
                // Confirma que la conexión se realizo de manera exitosa.
                System.out.println("Confirmando conexión con el cliente.");
                // Envia el mensaje al servidor de que la conexión se realizo exitosamente.
                salida.writeUTF("Conexión exitosa!");
                
                // Recepción de Mensaje desde el Servidor.
                datosRecibidos = bReader.readLine();
                System.out.println(datosRecibidos);
                System.out.println("Se recibio tu mensaje.");
                // Cierra el socket del Servidor
                serverSocket.close();
                // Cierra el socket cliente.
                socket.close();
                System.out.println("Conexión cerrada....");
            }
        } catch (IOException ex) {
            // En caso de no conectarse al servidor por el puerto.
            System.out.println("Error");
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Método que separa la entrada de Datos, para enviar 
    // el nombre, y la cédula desde el servidor.
    public void splitString(String cadena) {
        resultado = cadena.split("\\s");
        for (int x = 0; x < resultado.length; x++) {
            System.out.println(resultado[x]);
        }
    }
    
}
