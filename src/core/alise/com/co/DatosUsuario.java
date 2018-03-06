
package core.alise.com.co;

/**
 * Obtener usuario y equipo de Windows.
 */

public class DatosUsuario {
    
    // Variables encargadas de acceder a valores de otras clases.
    private static String pc = "", user = "";
    
    // Obtener nombre de Equipo
    public static String obtenerEquipo() {
        pc = System.getenv("computername");
        return pc;
    }
    
    // Obtener usuario logueado.
    public static String obtenerUsuario() {
        user = System.getProperty("user.name");
        return user;
    }
}
