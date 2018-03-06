package core.alise.com.co;

import view.alise.com.co.Alice;
import java.io.IOException;

/**
 * Clase que permite inicializar la Interfaz.
 */

public class chatClt {

    // Variable del contexto.
    private static Alice alice;
    
    public static void main(String[] args) throws IOException {
        
        // Iniciación de la Interfaz
        alice = new Alice();
        alice.setVisible(true);
    }
}