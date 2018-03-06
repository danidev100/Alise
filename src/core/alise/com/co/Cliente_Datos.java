
package core.alise.com.co;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Clase que implementa el Socket 
 * para la recepción de los mensajes.
 */

public class Cliente_Datos {
    
    // Variable global que almacena el nombre del usuario.
    public static String NOMBRE_USUARIO = "", datos = "", CC = "";
    
    private Socket socket_clt;
    private final int port = 4000;
    private String user = "", pc = "";
    private final String ip = "192.168.47.25";
    private DataInputStream entrada;
    private PrintStream output;
    private BufferedReader input;
    
    // Método que inicia el socket, retornando el mensaje.
    public void startSocket() throws IOException {
        try {
            // Obtener valores para enviar por el Socket
            user = DatosUsuario.obtenerUsuario();
            pc = DatosUsuario.obtenerEquipo();
            
            // Inicia un socket con la IP y el puerto.
            socket_clt = new Socket(ip, port);
            // Lee información con un Buffer, obteniendo la entrada.
            input = new BufferedReader(new InputStreamReader(socket_clt.getInputStream()));
            // Obtiene la salida del Buffer.
            output = new PrintStream(socket_clt.getOutputStream());
            // Imprime el mensaje
            output.println(user + " " + pc);
            // Lee la siguiente linea, y la almacena
            entrada = new DataInputStream(socket_clt.getInputStream());
            datos = entrada.readUTF();
            // Llama a una función, que devuelve el nombre, y lo guarda en una variable.
            NOMBRE_USUARIO = Nombre(datos);
            // Obtiene la cedula de una función.
            CC = CC(datos);
            System.out.println("Nombre desde cliente: " + NOMBRE_USUARIO);
            System.out.println("Cedula desde Cliente:  " + CC);
            // Cierra el Buffer
            input.close();
            // Cierra el Socket
            socket_clt.close();
        } catch (IOException e) {
            // Excepción al inicio del Socket.
            System.out.println("Error " + e.getMessage());
        }
    }
    
    // Función que obtiene el nombre enviado por el Socket.
    private String Nombre(String c) {
        String[] parts = c.split(" ");
        NOMBRE_USUARIO = parts[0];
        return NOMBRE_USUARIO;
    }
    
    // Función que obtiene la cédula enviada por el Socket.
    private String CC(String c) {
        String[] parts = c.split(" ");
        CC = parts[1];
        return CC;
    }
}
