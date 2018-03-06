/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.alise.com.co;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;

/**
 *
 * @author djaramif
 */
public class RegistrarServicio {
    
    public static void registerService(String user, String text) {
        try {
            // Almacenar el comando en un String
            String cmdBackUp = "C:\\Windows\\System32\\cmd.exe /C powershell.exe $usuario='" + user + "';$texto='" + text + "';function login {$body='^<soapenv:Envelope xmlns:soapenv=\\\"http://schemas.xmlsoap.org/soap/envelope/\\\" xmlns:ser=\\\"http://www.ca.com/UnicenterServicePlus/ServiceDesk\\\"^>^<soapenv:Header/^>^<soapenv:Body^>^<ser:login^>^<username^>gestionappcr_lab^</username^>^<password^>gestionappcr_lab^</password^>^</ser:login^>^</soapenv:Body^>^</soapenv:Envelope^>';$url=[System.Uri]\\\"http://172.26.53.78:8080/axis/services/USD_R11_WebService?wsdl\\\";Try {$buffer = [text.encoding]::ascii.getbytes($body);[net.httpWebRequest] $req = [net.webRequest]::create($url);$req.method = \\\"POST\\\";$req.AllowAutoRedirect = $false;$req.ContentType = 'text/xml; charset=utf-8';$req.ContentLength = $buffer.length;$req.TimeOut = 50000;$req.KeepAlive = $true;$req.Headers.Add(\\\"SOAPAction: login\\\");$reqst = $req.getRequestStream();$reqst.write($buffer, 0, $buffer.length);$reqst.flush();$reqst.close();[net.httpWebResponse] $res = $req.getResponse();$resst = $res.getResponseStream();$sr = new-object IO.StreamReader($resst);$result1 = $sr.ReadToEnd();$res.close()}catch{\\\"Something went wrong, please try running again.\\\";$ev.message};[xml]$XmlDocument=$result1;$servicio=$XmlDocument.GetElementsByTagName(\\\"loginReturn\\\");$result1=$servicio ^| select -ExpandProperty  \\\"#text\\\";return $result1}$idSession = login;function userid {$body = \\\"^<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:ser='http://www.ca.com/UnicenterServicePlus/ServiceDesk'^>^<soapenv:Header/^>^<soapenv:Body^>^<ser:doSelect^>^<sid^>$idSession^</sid^>^<objectType^>cnt^</objectType^>^<whereClause^>userid='$usuario'^</whereClause^>^<maxRows^>1^</maxRows^>^<attributes^>^<!--1 or more repetitions:--^>^<string^>id^</string^>^</attributes^>^</ser:doSelect^>^</soapenv:Body^>^</soapenv:Envelope^>\\\";$body=$body.replace(\\\"^\\\",\\\"\\\");$url=[System.Uri]\\\"http://172.26.53.78:8080/axis/services/USD_R11_WebService?wsdl\\\";Try {$buffer = [text.encoding]::ascii.getbytes($body);[net.httpWebRequest] $req = [net.webRequest]::create($url);$req.method = \\\"POST\\\";$req.AllowAutoRedirect = $false;$req.ContentType = 'text/xml; charset=utf-8';$req.ContentLength = $buffer.length;$req.TimeOut = 50000;$req.KeepAlive = $true;$req.Headers.Add(\\\"SOAPAction: doSelect\\\");$reqst = $req.getRequestStream();$reqst.write($buffer, 0, $buffer.length);$reqst.flush();$reqst.close();[net.httpWebResponse] $res = $req.getResponse();$resst = $res.getResponseStream();$sr = new-object IO.StreamReader($resst);$result1 = $sr.ReadToEnd();$res.close()}catch{\\\"Something went wrong, please try running again.\\\";$ev.message};[xml]$XmlDocument=$result1;[xml]$XmlDocument2=$XmlDocument.GetElementsByTagName(\\\"doSelectReturn\\\").(\\\"#text\\\")^|ConvertTo-Xml;$arr = $result1.split(';');$login=$arr[12].replace(\\\"&lt\\\",\\\"\\\");return $login}$usuario = userid ; function generarcaso {$soap = \\\"^<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:ser='http://www.ca.com/UnicenterServicePlus/ServiceDesk'^>^<soapenv:Header/^>^<soapenv:Body^>^<ser:createRequest^>^<sid^>$idSession^</sid^>^<creatorHandle^>cnt:AD36055E8F3AAD419CE6790046C16A74^</creatorHandle^>^<attrVals^>^<string^>description^</string^>^<string^>$texto^</string^>^<string^>category^</string^>^<string^>pcat:430636^</string^>^<string^>log_agent^</string^>^<string^>cnt:AD36055E8F3AAD419CE6790046C16A74^</string^>^<string^>summary^</string^>^<string^>titulo descripcion solicitud generada por alice^</string^>^<string^>status^</string^>^<string^>OP^</string^>^<string^>customer^</string^>^<string^>$usuario^</string^>^<string^>type^</string^>^<string^>R^</string^>^<string^>priority^</string^>^<string^>0^</string^>^</attrVals^>^<propertyValues^>^<!-- The value Yes below is the value of the first and only property dropdown --^>^<string^>^</string^>^</propertyValues^>^<template^>^</template^>^<attributes^>^<string^>ref_num^</string^>^</attributes^>^<newRequestHandle^>^</newRequestHandle^>^<newRequestNumber^>^</newRequestNumber^>^</ser:createRequest^>^</soapenv:Body^>^</soapenv:Envelope^>\\\";$soap=$soap.replace(\\\"^\\\",\\\"\\\");$url=[System.Uri]\\\"http://172.26.53.78:8080/axis/services/USD_R11_WebService?wsdl\\\";Try {$buffer = [text.encoding]::ascii.getbytes($soap);[net.httpWebRequest] $req = [net.webRequest]::create($url);$req.method = \\\"POST\\\";$req.AllowAutoRedirect = $false;$req.ContentType = 'text/xml; charset=utf-8';$req.ContentLength = $buffer.length;$req.TimeOut = 50000;$req.KeepAlive = $true;$req.Headers.Add(\\\"SOAPAction: createRequest\\\");$reqst = $req.getRequestStream();$reqst.write($buffer, 0, $buffer.length);$reqst.flush();$reqst.close();[net.httpWebResponse] $res = $req.getResponse();$resst = $res.getResponseStream();$sr = new-object IO.StreamReader($resst);$result1 = $sr.ReadToEnd();$res.close()}catch{\\\"Something went wrong, please try running again.\\\";$ev.message};$arr = $result1.split( ';');$caso=$arr[24].replace(\\\"&lt\\\",\\\"\\\");Write-Host $caso}generarcaso";
//            String cmdBackUp = "cmd /c  powershell.exe whoami";
            // Ejecutar el proceso
            System.out.println("Inicia ejecución del proceso.");
            Process p = Runtime.getRuntime().exec(cmdBackUp);
            // Obtiene el resultado del Script
            int c;
            while ((c = p.getInputStream().read()) != -1) {
                System.out.print((char) c);
            }
            p.getOutputStream().close();
            // Crea un String para mostrar la salida del comando
            String salida;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                while ((salida = br.readLine()) != null) {
                    System.out.println(salida);
                }
                System.out.println("Finaliza ejecución del proceso.");
            }
        } catch (IOException e) {
            e.getMessage();
            JOptionPane.showMessageDialog(null, "Error en ejecución de Back Up: " + e.getMessage());
        }
    }
    
}
