
package core.alise.com.co;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * Clase que permite ejecutar el monitoreo del equipo
 * al inicio, y cada cierto tiempo.
 */

public class monitor extends Thread {

    private final Sigar sigar; // Instancia de Sigar
    private final double max_mem; // Maximo Porcentaje de uso de RAM 
    private final double max_cpu; // Maximo Porcentaje uso de CPU
    private final double max_hdd; // Maximo Porcentaje de uso de disco 
    private final DecimalFormat df = new DecimalFormat("#.00"); //Formato para valores decimales, maximo 2 decimales
    private final Timer main_timer; // Temporizador de verificacion automatica
    private final Timer ram_timer; // Temporizador de verificacion cada minuto
    private final Timer hdd_timer; // Temporizador de verificacion automatica
    private final Timer cpu_timer; // Temporizador de verificacion automatica
    private Calendar time;
    private static String givenName = "";

    private String log;

    public monitor() {
        //Implementacion de Sigar API
        sigar = new Sigar();
        //Porcentajes permitidos
        max_mem = 0.9;
        max_hdd = 0.9;
        max_cpu = 0.9;
        //Inicializacion del timer el valor de la hora es 3600000, 
        //  300000 para cada 5 minutos
        main_timer = new Timer(3600000, main_action);
        //Timer ejecutando verificaciones cada minuto
        ram_timer = new Timer(60000, ram_action);
        hdd_timer = new Timer(60000, hdd_action);
        cpu_timer = new Timer(60000, cpu_action);
    }

    //Metodo para validaciones OPCIONAL
    private String get_time() {
        time = new GregorianCalendar();
        return time.get(Calendar.HOUR_OF_DAY) + ":" + time.get(Calendar.MINUTE) + ":" + time.get(Calendar.SECOND);
    }

    /**
     * Acciones de los timer
     */
    ActionListener main_action = (ActionEvent e) -> {
        System.gc();
        auto_check();
    } /**
     * Accion a realizar cada 3600000 milisegundos (1 hora)
     *
     * @param e Evento realizado
     */ ;

    ActionListener ram_action = new ActionListener() {

        boolean[] ram_stats = {false, false, false}; // Estados de la ram en cada chequeo
        int pos = 0; //Posicion del vector
        String log = ""; //Informacion a mostrar o mandar

        /**
         * Accion a realizar cada 60000 milisegundos (1 minuto)
         *
         * @param e Evento realizado
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Accion RAM Timer");
            log += "\n";
            if (pos < 3) {
                System.out.println("Verificacion de RAM #" + (pos + 1) + " " + get_time());
                log += "Verificacion de RAM # " + (pos + 1) + "\n";
                //Se realiza el chequeo y guarda el resultado
                ram_stats[pos] = check_ram();
                //Almacena un chequeo siguiente
                log += get_ram_usage();
                //Aumenta la posicion del arreglo cpu_stats
                pos++;
                if (pos == 3) {
                    if (validate_stats(ram_stats)) {
                        //Implementar correo o alarma
                        System.out.println("Se presentaron problemas recurrentes en la RAM.\nInforme completo: " + log);
                    } else {
                        System.out.println("El problema en la memoria no fue recurrente");
                    }
                    //Se resetean las variables del action listener
                    ram_stats = new boolean[]{false, false, false};
                    pos = 0;
                    log = "";
                    ram_timer.stop();
                }
            }
        }
    };

    ActionListener hdd_action = new ActionListener() {
        boolean[] hdd_stats = {false, false, false}; // Estados del hdd en cada chequeo
        int pos = 0; //Posicion del vector
        String log = ""; //Informacion a mostrar o mandar

        /**
         * Accion a realizar cada 60000 milisegundos (1 minuto)
         *
         * @param e Evento realizado
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Accion HDD Timer");
            log += "\n";
            if (pos < 3) {
                System.out.println("Verificacion de HDD #" + (pos + 1) + " " + get_time());
                log += "Verificacion de HDD # " + (pos + 1) + "\n";
                //Se realiza el chequeo y guarda el resultado
                hdd_stats[pos] = check_hdd();
                //Almacena un chequeo siguiente
                log += get_hdd_usage();
                //Aumenta la posicion del arreglo cpu_stats
                pos++;
                if (pos == 3) {
                    if (validate_stats(hdd_stats)) {
                        //Implementar correo o alarma
                        System.out.println("Se presentaron problemas recurrentes en el HDD.\nInforme completo: " + log);
                    } else {
                        System.out.println("El problema en el disco no fue recurrente");
                    }
                    //Se resetean las variables del action listener
                    hdd_stats = new boolean[]{false, false, false};
                    pos = 0;
                    log = "";
                    hdd_timer.stop();
                }
            }
        }
    };

    ActionListener cpu_action = new ActionListener() {
        boolean[] cpu_stats = {false, false, false}; // Estados de la CPU en cada chequeo
        int pos = 0; //Posicion del vector
        String log = ""; //Informacion a mostrar o mandar

        /**
         * Accion a realizar cada 60000 milisegundos (1 minuto)
         *
         * @param e Evento realizado
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Accion CPU Timer");
            log += "\n";
            if (pos < 3) {
                System.out.println("Verificacion de CPU #" + (pos + 1) + " " + get_time());
                log += "Verificacion de CPU # " + (pos + 1) + "\n";
                //Se realiza el chequeo y guarda el resultado
                cpu_stats[pos] = check_cpu();
                //Almacena un chequeo siguiente
                log += get_cpu_usage();
                //Aumenta la posicion del arreglo cpu_stats
                pos++;
                if (pos == 3) {
                    if (validate_stats(cpu_stats)) {
                        //Implementar correo o alarma
                        System.out.println("Se presentaron problemas recurrentes en la CPU.\nInforme completo: " + log);
                    } else {
                        System.out.println("El problema en la CPU no fue recurrente");
                    }
                    //Se resetean las variables del action listener
                    cpu_stats = new boolean[]{false, false, false};
                    pos = 0;
                    log = "";
                    hdd_timer.stop();
                }
            }
        }
    };

    /**
     * Valida los 3 estados tomados por los timer,
     *
     * @param stats Estados tomados por el timer
     * @return True si al menos uno de los estados es verdadero, indicando
     * recurrencia en el error
     */
    private boolean validate_stats(boolean[] stats) {
        for (int i = 0; i < 3; i++) {
            if (stats[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Realiza un chequeo de los recursos, activa automaticamente los timers en
     * caso de detectar una anomalia en el funcionamiento de los recursos
     */
    private void auto_check() {
        //Variable para envio de correo electronico
        boolean send = false;
        //Informacion a enviar por correo
        log = "";
        //Validacion de parametros de CPU
        if (check_cpu()) {
            //Indica que se enviaran los datos del error
            send = true;
            //Almacena la informacion del error
            log += "\nSe detectaron anomalias en el funcionamiento de la CPU: \n"
                    + get_cpu_usage();
            //Mensaje o Alerta
            System.out.println("Error en CPU");
            //Se activa el chequeo cada minuto
            cpu_timer.start();
            //Mensaje o Alerta
            System.out.println("Timer CPU Activado " + get_time());
        }
        if (check_hdd()) {
            //Indica que se enviaran los datos del error
            send = true;
            //Almacena la informacion del error
            log += "\nSe detectaron anomalias en el funcionamiento de la HDD: \n"
                    + get_hdd_usage();
            //Mensaje o Alerta
            System.out.println("Error en HDD");
            //Se activa el chequeo cada minuto
            hdd_timer.start();
            //Mensaje o Alerta
            System.out.println("Timer HDD Activado " + get_time());
        }

        if (check_ram()) {
            //Indica que se enviaran los datos del error
            send = true;
            //Almacena la informacion del error
            log += "\nSe detectaron anomalias en el funcionamiento de la RAM: \n"
                    + get_ram_usage();
            //Mensaje o Alerta
            System.out.println("Error en RAM");
            //Se activa el chequeo cada minuto
            ram_timer.start();
            //Mensaje o Alerta
            System.out.println("Timer RAM Activado " + get_time());

        }
        log += "\nLos datos fueron tomados inmediatamente despues de reportarse la anomalia";

        //Envio de correo
        /*if (send) {
            String from, to, subject;
            from = "testmailmonitor@gmail.com";
            to = "sadnessremains@gmail.com";
            subject = "Auto-monitoring";
            mail ml = new mail();

            ml.setUsername("testmailmonitor@gmail.com");
            ml.setPassword("0123456789a");
            ml.send_mail(from, to, subject, log);
        }*/
    }

    /**
     * Verifica el procentaje de memoria RAM libre.
     *
     * @return True, si la memoria libre es menor al limite. False, si la
     * memoria libre esta en niveles aceptables
     */
    public boolean check_ram() {
        try {
            //Variable para pruebas
            boolean ret;
            //Procentaje de memoria en uso
            double used_Mem = sigar.getMem().getUsedPercent();
            //Mensaje o Alerta
            System.out.println("Porcentaje de memoria en uso: " + used_Mem);
            //Retorna condicion
            ret = ((used_Mem / 100) > max_mem);
            return ret;

        } catch (SigarException ex) {
            Logger.getLogger(monitor.class
                    .getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Consulta la información detallada del uso actual de memoria RAM.
     *
     * @return String, detalle de uso de RAM.
     */
    public String get_ram_usage() {
        String ret = "";
        try {
            Mem info = sigar.getMem();
            ret += "Tamaño total de la memoria: " + (info.getTotal() / 1024) + " MBytes\n"
                    + "Memoria en uso: " + (info.getUsed() / 1024) + " MBytes\n"
                    + "Porcentaje en uso: " + df.format(info.getUsedPercent()) + "%\n"
                    + "Memoria libre" + (info.getFree() / 1024) + " MBytes\n"
                    + "Porcentaje libre: " + (df.format(info.getFreePercent())) + "%\n";

        } catch (SigarException ex) {
            Logger.getLogger(monitor.class
                    .getName()).log(Level.SEVERE, null, ex);
            ret += "No se pudo cargar la información de RAM";
        }
        return ret;
    }

    /**
     * Verifica el procentaje de uso del procesador.
     *
     * @return True, si el uso del procesador es mayor al maximo establecido.
     * False, si le uso del procesador esta en niveles aceptables
     */
    public boolean check_cpu() {
        try {
            boolean ret;
            double cpu_usage = sigar.getCpuPerc().getCombined();
            System.out.println("Uso de CPU: " + cpu_usage);
            ret = (cpu_usage > max_cpu);
            return ret;

        } catch (SigarException ex) {
            Logger.getLogger(monitor.class
                    .getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    /**
     * Consulta la información detallada del uso de la CPU.
     *
     * @return String, detalle de uso de CPU.
     */
    public String get_cpu_usage() {

        String ret = "";
        try {
            CpuInfo info = sigar.getCpuInfoList()[0];

            ret += "Vendedor: " + info.getVendor() + "\n"
                    + "Modelo: " + info.getModel() + "\n"
                    + "Nucleos: " + info.getTotalCores() + "\n"
                    + "Reloj: " + info.getMhz() + "\n";

            double usage = sigar.getCpuPerc().getCombined();
            //Sin la redundancia causa error al enviar el mensaje,
            //muestra un caracter desconocido en la parte de % de CPU
            ret += "Procentage de uso: " + (df.format((usage * 100))) + "%\n";

        } catch (SigarException ex) {
            Logger.getLogger(monitor.class
                    .getName()).log(Level.SEVERE, null, ex);
            ret += "No se pudo cargar la información de CPU";
        }
        return ret;
    }

    /**
     * Verifica el procentaje uso del HDD.
     *
     * @return True, si el espacio libre en el HDD es inferior al límite
     * establecido. False si el espacio libre en HDD esta en niveles aceptables.
     */
    public boolean check_hdd() {
        try {
            boolean ret;
            double hdd_usage = sigar.getFileSystemUsage("C:").getUsePercent();
            System.out.println("Uso de Disco: " + hdd_usage);
            ret = hdd_usage > max_hdd;
            return ret;

        } catch (SigarException ex) {
            Logger.getLogger(monitor.class
                    .getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Consulta la información detallada del uso actual del HDD.
     *
     * @return String, detalle de uso de HDD.
     */
    public String get_hdd_usage() {
        String ret = "";
        try {
            FileSystemUsage usage = sigar.getFileSystemUsage("C:");
            ret += "Espacio total: " + (usage.getTotal() / 1024) + " Mbytes\n"
                    + "Espacio en uso: " + (usage.getUsed() / 1024) + " Mbytes\n"
                    + "Espacio libre: " + (usage.getFree() / 1024) + " Mbytes\n"
                    + "Porcentaje de uso: " + (df.format(usage.getUsePercent() * 100)) + "%\n";

        } catch (SigarException ex) {
            Logger.getLogger(monitor.class
                    .getName()).log(Level.SEVERE, null, ex);
            ret += "No se pudo cargar la información de disco duro";
        }
        return ret;
    }

    @Override
    public void run() {
        if (check_cpu() && check_hdd() && check_ram()) {
            String msg = "El equipo esta no listo para iniciar labores.";
            System.out.println("El equipo no esta  listo para iniciar labores.");
        } else {
            // Obtener el nombre del usuario por Socket
            givenName = Cliente_Datos.NOMBRE_USUARIO;
            // Creación de cadena de mensaje.
            // creación de dialogo, configuración, y diseño.
            String msg = "Hola " + givenName + " se realizo un diagnostico "
                    + "de su equipo y este se encuentra en optimas condiciones "
                    + "para que realice su trabajo.";
            final JDialog dialog = new JDialog();

            JOptionPane op = new JOptionPane("<html><body><p style='width: 150px;'>"
                    + msg + "</p></body></html>", JOptionPane.INFORMATION_MESSAGE,
                    JOptionPane.CLOSED_OPTION);
            op.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                String name1 = evt.getPropertyName();
                if ("value".equals(name1)) {
                    dialog.dispose();
                }
            });

            // Configuración de estilos y posición del dialogo.
            dialog.setUndecorated(true);
            dialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.GRAY));
            dialog.setLayout(new BorderLayout());
            dialog.add(op);
            dialog.pack();
            // Ubicación del dialogo en la parte inferior derecha.
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
            Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
            dialog.setLocation(1100, 380);
            dialog.setVisible(true);
        }
        auto_check();
        main_timer.start();
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(chatSrv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
