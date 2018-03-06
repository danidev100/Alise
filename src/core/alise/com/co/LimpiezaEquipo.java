
package core.alise.com.co;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;

/**
 *  Clase que Ejecuta Limpieza del equipo por medio de Hilos.
 */

public class LimpiezaEquipo extends Thread {
    
    // Se sobreescribe el Método de Ejecución de hilos run()
    @Override
    public void run() {
        
        // Scripts para limpiar.
        Politicas();
        prueba("Politicas OK");
        LimpiarNavegadores();
        prueba("Navegadores OK");
        FinalizarProcesos();
        prueba("Procesos OK");
        Temporales();
        prueba("Temporales OK");
    }
    
    
    // Imprime mensaje
    private void prueba(String msg) {
        System.out.println(msg);
    }
    
    // Script temporales.
    private void Temporales() {
        try {
            // Almacenar el comando en un String
            String cmdTemp = "C:\\Windows\\System32\\cmd.exe /C rmdir %tmp% /S /Q & rmdir C:\\\\*.tmp & "
                    + "rmdir /s /q C:\\Windows\\Temp\\ & regedit /S \\\\192.168.47.25\\C$\\Users\\csanchep\\Desktop\\sageset_64.reg & CLEANMGR /sagerun:64";
            // Ejecutar el proceso
            Process p = Runtime.getRuntime().exec(cmdTemp);
            // Obtiene el resultado del Script
            p.getOutputStream().close();
            // Crea un String para mostrar la salida del comando
            String salida;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                while ((salida = br.readLine()) != null) {
                    System.out.println(salida);
                }
            }
        } catch (IOException e) {
            e.getMessage();
            JOptionPane.showMessageDialog(null, "Error en Temporales.");
        }
    }
    
    // Script Politicas.
    private void Politicas() {
        try {
            String cmdPoliticas = "C:\\Windows\\System32\\cmd.exe /C gpupdate /force";
            // Intentar ejecutar el proceso
            Process p = Runtime.getRuntime().exec(cmdPoliticas);
            // Obtiene el resultado del Script
            p.getOutputStream().close();
            // Crea un String para mostrar la salida del comando
            String salida;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                while ((salida = br.readLine()) != null) {
                    System.out.println(salida);
                }
            }
        } catch (IOException e) {
            e.getMessage();
            JOptionPane.showMessageDialog(null, "Error en Politicas.");
        }
    }
    
    // Script Limpieza Navegadores.
    private void LimpiarNavegadores() {
        try {
            String cmdClean = "C:\\Windows\\System32\\cmd.exe /C "
                    + "TASKKILL /F /IM chrome.exe & TASKKILL /F /IM firefox.exe "
                    + "& TASKKILL /F /IM MicrosoftEdge.exe "
                    + "& TASKKILL /F /IM iexplore.exe & "
                    + "del /S /F /Q \"%LOCALAPPDATA%\\Google\\Chrome\\User Data\\History*\" & "
                    + "del /S /F /Q \"%APPDATA%\\Mozilla\\Firefox\\Profiles\\places.sqlite*\" & "
                    + "RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 4351 && exit";
            // Intentar ejecutar el proceso
            Process p = Runtime.getRuntime().exec(cmdClean);
            // Obtiene el resultado del Script
            p.getOutputStream().close();
            // Crea un String para mostrar la salida del comando
            String salida;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                while ((salida = br.readLine()) != null) {
                    System.out.println(salida);
                }
            }
        } catch (IOException e) {
            e.getMessage();
            JOptionPane.showMessageDialog(null, "Error Limpieza Navegador.");
        }
    }
    
    // Script procesos.
    private void FinalizarProcesos() {
        try {
            String cmdProcess = "C:\\Windows\\System32\\cmd.exe /C "
                    + "TASKKILL /F /FI \"STATUS eq NOT RESPONDING\"";
            // Intentar ejecutar el proceso
            Process p = Runtime.getRuntime().exec(cmdProcess);
            // Obtiene el resultado del Script
            p.getOutputStream().close();
            // Crea un String para mostrar la salida del comando
            String salida;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                while ((salida = br.readLine()) != null) {
                    System.out.println(salida);
                }
            }
        } catch (IOException e) {
            e.getMessage();
            JOptionPane.showMessageDialog(null, "Error finalizar procesos.");
        }
    }
}
