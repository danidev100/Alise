
package core.alise.com.co;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.swing.JOptionPane;

/**
 * Punto de inicio de Alise, con Sockets, Interfaz.
 */

public class chatSrv {
    
    // Getters y Setters.
    public static String getUser() {
        return user;
    }

    public static String getPc() {
        return pc;
    }

    public static void setUser(String user) {
        chatSrv.user = user;
    }

    public static String getGivenName() {
        return givenName;
    }
    
    // Definición de instancias para chatSrv.
    private static Server server;
    private static Cliente_Datos cDatos;
    private static final String RUTA_MANTENIMIENTO = System.getenv("USERPROFILE") + "\\prueba.txt";
    private static final LimpiezaEquipo LE = new LimpiezaEquipo();
    private static String user = "", givenName = "", pc = "";
    public static monitor m = new monitor();
    
    // Instancia de Clase que ejecuta el Back Up.
    private static final BackUpOneDrive bk = new BackUpOneDrive();
    
    public static void main(String[] args) throws IOException,
            NamingException, InterruptedException {
        
        // Valores de usuario y pc.
        user = DatosUsuario.obtenerUsuario();
        pc = DatosUsuario.obtenerEquipo();
       
        // Instancia de Puerto para envio de datos
        cDatos = new Cliente_Datos();
        cDatos.startSocket();
        givenName = Cliente_Datos.NOMBRE_USUARIO;
        server = new Server();
        // Ejecuta Back Up.
        bk.start();
        // Validar fecha para realizar mantenimiento de equipo.
        ValidarFechaMantenimiento();
        // Validar fecha para realizar Back Up directamente a la cuenta.
        ValidarFechaBackUp();
        // Monitorea el equipo.
        m.start();
        // Inicia el Socket para estar a la escucha.
        server.startSocket();
    }
    
    // Revisa cada 8 días, si se desea hacer Back Up de información.
    private static void ValidarFechaMantenimiento() {
      // Antes que nada, verifica que el archivo ya exista.
      if (fileExist(RUTA_MANTENIMIENTO)) {
        // Comparar la fecha del archivo, y ejecutar Back Up
        System.out.println("Archivo Mantenimiento existe!");
        if (compareDate(RUTA_MANTENIMIENTO)) {
          // Ya han pasado mas de 8 días. Por tanto, ejecuta el Back Up
          System.out.println("Hora de Mantenimiento...");
          alertClean();
        } else {
          // Aún no han pasado 8 días. Continua el flujo del Programa.
          System.out.println("No han pasado 8 días para mantenimiento..");
        }
      } else {
        // El archivo no se ha creado
        System.out.println("Archivo Mantenimiento no existe. Se creara...");
        // Se registra la hora, y se pregunta si desea realizar mantenimiento.
        registroHora(RUTA_MANTENIMIENTO);
        // Pregunta al usuario si desea realizar la limpieza.
        alertClean();
      }
    }
    
    // Valida la fecha correcta, y ejecuta Back Up.
    private static void ValidarFechaBackUp() {
        
        // Verifica la fecha, segun el día
        if (isFriday()) {
            // Si es viernes, determina el tipo de SO.
            if (System.getProperty("os.name").equals("Windows 7")) {
                System.out.println("Sistema Windows 7");
                // Realiza Back Up de diferentes ubicaciones para W7
                BackUpCuentaUsuario.ejecutarBackUp("\\\"D:\\\"", "\\\"D:\\\"", "Disco_D");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"" + System.getenv("userprofile") + "\\Desktop\\\"");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"" + System.getenv("userprofile") + "\\Favorites\\\"");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"C:\\Users\\DefaultAppData\\Roaming\\Microsoft\\Firmas\\\"");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"C:\\Users\\DefaultAppData\\Roaming\\Microsoft\\Outlook\\\"");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"C:\\Users\\DefaultAppData\\Roaming\\Microsoft\\Sticky Notes\\\"");
            } else if (System.getProperty("os.name").equals("Windows 10")) {
                // Realiza Back Up para distintas ubicaciones en W10
                System.out.println("Realizando Back Up...");
                BackUpCuentaUsuario.ejecutarBackUp("\\\"C:\\Users\\%username%\\test\\\"", "\\\"C:\\Users\\%username%\\\"", "Test");
                BackUpCuentaUsuario.ejecutarBackUp("\\\"C:\\Users\\%username%\\test_2\\\"", "\\\"C:\\Users\\%username%\\\"", "TestNumero2");
                //BackUpCuentaUsuario.ejecutarBackUp("\\\"C:\\Users\\%username%\\Documents\\\"", "\\\"C:\\Users\\%username%\\\"", "Otra");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"C:\\Users\\%username%\\Documents\\\"", "\\\"C:\\Users\\%username%\\\"", "Documents");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"C:\\Users\\%username%\\Desktop\\\"", "\\\"C:\\Users\\%username%\\\"", "Desktop");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"D:\\Users\\" + System.getenv("username") + "\\Favorites\\\"");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"D:\\Users\\Default\\AppData\\Roaming\\Microsoft\\Firmas\\\"");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"D:\\Users\\Default\\AppData\\Roaming\\Microsoft\\Outlook\\\"");
//                BackUpCuentaUsuario.ejecutarBackUp("\\\"D:\\Users\\Default\\AppData\\Roaming\\Microsoft\\Sticky Notes\\\"");
            }
            System.out.println("Ejecuta Back Up");
//            BackUpCuentaUsuario.ejecutarBackUp();
        } else {
            // No es viernes.
            System.out.println("No es viernes");
        }
    }
    
    // Metodo que obtiene hora y dia.
    private static boolean isFriday() {
        
        // Obtiene una fecha actual
        Date d = new Date();
        // Objeto Calendario que permite determinar hora y Dia
        Calendar c = Calendar.getInstance();
        // Establece formato de Fecha
        c.setTime(d);
        // Almacena el dia numericamente.
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        // Guarda la hora.
        int hour = c.get(Calendar.HOUR);
        System.out.println("Hora: " + hour);
        // Verifica si es viernes, a las 12 pm.
        return (dayOfWeek == 5) && (hour == 10);
    }
    
    // Determina si el archivo existe.
    private static boolean fileExist(String ruta) {
        
      // Instancia objeto Archivo
      File f = new File(ruta);
      return f.exists();
    }
    
    // Comparar dos fechas.
    private static boolean compareDate(String ruta) {
        
      // Variable que determina si han pasado 8 días, o no.
      boolean value = false;
      try {
        // Variable que instancia la fecha actual.
        Date d = new Date();
        // Se da formato a la fecha como Dia, Mes, Año.
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        
        // Almacena la fecha actual, y la del archivo, en formato Dia, Mes, Año.
        String dmyActual = sdf.format(d);
        String dmyArchivo = ReadFile(ruta);
        
        // Se parsea String a tipo Date, para validar cantidad de dias
        Date date1 = sdf.parse(dmyArchivo);
        Date date2 = sdf.parse(dmyActual);
        int dias = (int) ((date2.getTime() - date1.getTime()) / 86400000);
        
        // Verifica si han pasado mas de 8 dias, y devuelve el resultado.
        value = dias == 8;
      } catch (ParseException ex) {
        Logger.getLogger(chatSrv.class.getName()).log(Level.SEVERE, null, ex);
      }
      return value;
    }
    
    // Alerta para ejecutar Back Up cada 8 dias.
    private static void alertClean() {
        
      // Da las opciones para el mensaje.
        String[] options = new String[] {"Ahora", "Despues"};
        // Muestra el dialogo con las opciones.
        int response = JOptionPane.showOptionDialog(null, "Desea realizar mantenimiento "
                + "de su equipo ahora?", "Limpieza de Equipo.",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        // Registra la selección del usuario.
        if(response == 0) {
          try {
            System.out.println("Mantenimiento ahora...");
            // En caso de aceptar, ejecuta la limpieza, espera un tiempo, e informa al usuario.
            LE.start();
            Thread.sleep(15000);
            JOptionPane.showMessageDialog(null, "Hola " + Cliente_Datos.NOMBRE_USUARIO + ", el mantenimiento se ha realizado correctamente.",
                    "Mantenimiento",
                    JOptionPane.INFORMATION_MESSAGE);
            // Registra la fecha de ultima ejecución.
            registroHora(RUTA_MANTENIMIENTO);
          } catch (InterruptedException ex) {
            Logger.getLogger(chatSrv.class.getName()).log(Level.SEVERE, null, ex);
          }
        } else {
            // No realiza ninguna acción.
        }
    }
    
    // Genera la fecha actual, en formato DD-MM-AAAA
    private static void registroHora(String ruta) {
        
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        System.out.println(formateador.format(ahora));
        saveInFile(formateador.format(ahora), ruta);
    }
    
    // Guardar el formato de fecha en un archivo externo.
    private static void saveInFile(String d, String ruta) {
        
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(ruta);
            pw = new PrintWriter(fichero);
            pw.print(d);
        } catch (IOException e) {
            e.getMessage();
        } finally {
            try {
                // Si el fichero esta abierto, lo cierra
                if (null != fichero) {
                    fichero.close();
                }
            } catch (IOException e2) {
                e2.getMessage();
            }
        }
    }
    
    // Lectura del archivo, que contiene el formato de fecha.
    private static String ReadFile(String ruta) {
        
      // Variable para almacenar el contenido del archivo.
        String linea = "";
        // Lector del archivo.
        FileReader fr = null;
        try {
            // Intenta leer el archivo en la ruta.
            fr = new FileReader(ruta);
            BufferedReader br = new BufferedReader(fr);
            linea = br.readLine();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(chatSrv.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(chatSrv.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(chatSrv.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return linea;
    }
}
