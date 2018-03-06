
package core.alise.com.co;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Clase que implementa el Socket 
 * para el envio de mensajes.
 */

public class Cliente {
    
    // Instancia de variables para la clase.
    private Socket socket_clt;
    private final int port = 3000;
    private final String ip = "10.10.7.52";
    private BufferedReader input;
    private PrintStream output;
    
    // Método que inicia el socket, retornando el mensaje.
    public String startSocket(String GUIMsg) throws IOException {
        String msg = null;
        try {
            // Inicia un socket con la IP y el puerto.
            socket_clt = new Socket(ip, port);
            // Lee información con un Buffer, obteniendo la entrada.
            input = new BufferedReader(new InputStreamReader(socket_clt.getInputStream()));
            // Obtiene la salida del Buffer.
            output = new PrintStream(socket_clt.getOutputStream());
            // Imprime el mensaje
            output.println(GUIMsg);
            // Lee la siguiente linea, y la almacena
            msg = input.readLine();
            // Muestra el mensaje.
            System.out.println("msg = " + msg);
            // Cierra el Buffer
            input.close();
            // Cierra el Socket
            socket_clt.close();
        } catch (IOException e) {
            // Excepción al inicio del Socket.
            System.out.println("Error " + e);
        }
        // Retorna mensaje
        return msg;
    }
}
