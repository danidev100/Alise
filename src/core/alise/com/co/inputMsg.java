package core.alise.com.co;

import java.io.BufferedReader;
import java.io.IOException;


public class inputMsg {
    
    /**
     * Entrada de mensaje.
     */
    
    // Iniciación de las variables Globales.
    private final Server srv;
    private Server socket;
    private static BufferedReader bufferedReader;
    private String msg;

    
    /**
     * Declaración de Getters y Setters.
     * @throws IOException 
     */
    
    // Inicializa Server.
    public inputMsg() throws IOException {
        this.srv = new Server();
    }

    // Permite obtener el mensaje
    public String getMsg() throws IOException {
        socket = new Server();
        msg = bufferedReader.readLine();
        return msg;
    }

    // Setea el valor para msg
    public void setMsg(String msg) {
        this.msg = msg;
    }    

    @Override
    public String toString() {
        return "inputMsg{" + "msg=" + msg + '}';
    }
}
