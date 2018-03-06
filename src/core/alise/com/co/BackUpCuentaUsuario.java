
package core.alise.com.co;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;

/**
 * Clase que realiza Back Up en la cuenta de correo del usuario.
 */

public class BackUpCuentaUsuario {
    
    public static void ejecutarBackUp(String path, String path_short, String dir) {
        try {
            // Almacenar el comando en un String
            String cmdBackUp = "C:\\Windows\\System32\\cmd.exe /C powershell.exe "
                        + "\"Set-Executionpolicy -Scope CurrentUser -ExecutionPolicy UnRestricted;"
                    + "if ((Test-Path -Path C:\\dato) -and (Test-Path -Path C:\\backups) -and (Test-Path -Path C:\\backups\\" + dir + ")) {Write-Host \\\"Ya existen\\\"}else{New-Item -ItemType directory -Path C:\\dato;New-Item -ItemType directory -Path C:\\backups;New-Item -ItemType directory -Path C:\\backups\\" + dir + "}"
                    + "$Nombre=(get-date).ToString(\\\"yyyyMMdd-HHmm\\\");"
                    + "$ruta= -join \\\"c:\\backups\\" + dir + "\\\\\\\"+$Nombre+\\\".csv\\\";"
                    + "Get-ChildItem -Recurse " + path + " -Include *.gif, *.jpg, *.xls, *.xlsx, *.doc, *.docx, *.pdf*, *.wav*, *.ppt, *.pptx, *.csv* |` ForEach-Object {$_ | add-member -name \\\"Owner\\\" -membertype noteproperty -value (get-acl $_.fullname).owner -passthru} | ` Sort-Object fullname | Select FullName,LastWriteTime | Export-Csv -encoding UTF8 -Force -NoTypeInformation $ruta;"
                    + "if ((gci c:\\backups\\" + dir + " | sort LastWriteTime | select -last 2).count -eq 2){"
                    + " $ruta1 = -join \\\"c:\\backups\\" + dir + "\\\\\\\"+(gci c:\\backups\\" + dir + "\\ | sort LastWriteTime | select -last 2| select -index 0);"
                    + " $ruta2 = -join \\\"c:\\backups\\" + dir + "\\\\\\\"+ (gci c:\\backups\\" + dir + "\\ | sort LastWriteTime | select -last 2| select -index 1);"
                    + " $csv1 = Import-Csv -encoding UTF8 $ruta1;"
                    + " $csv2 = import-csv -encoding UTF8 $ruta2;"
                    + " $archivos= -join \\\"c:\\dato\\out.csv\\\";"
                    + " $ruta1;"
                    + " $ruta2;"
                    + " Compare-Object -referenceobject $csv1 -differenceobject $csv2 -Property LastWriteTime,fullname -includeequal -PassThru |  Where-Object{$_.SideIndicator -eq '=>'} | Select-Object  \\\"FullName\\\", \\\"LastWriteTime\\\" | export-csv -encoding UTF8 $archivos -NoTypeInformation}"
                    + "else{"
                    + " $archivos = -join \\\"c:\\backups\\" + dir + "\\\\\\\"+(gci c:\\backups\\" + dir + "\\ | sort LastWriteTime | select -index 0)}"
                    + "$datos =Import-Csv -encoding UTF8 $archivos;"
                    + "function credenciales {"
                    + "$url_sharepoint=\\\"https://suramericana-my.sharepoint.com\\\";"
                    + "$Url_site = \\\"/personal/mpinto_sura_com_co\\\";"
                    + "$url =$url = $url_sharepoint+$Url_site;"
                    + "$cred1 = Import-CliXml -Path 'C:\\dato\\cred.xml';if ((!($cred1)) -or (!($cred1.UserName -like \\\"*@sura.com.co\\\"))){"
                    + "$cred1 = $host.ui.PromptForCredential(\\\"Backup Drive Arus\\\", \\\"Favor ingrese su usuario y contraseña.\\\", \\\"\\\", \\\"NetBiosUserName\\\");"
                    + "$cred1 | Export-CliXml -Path 'C:\\dato\\cred.xml'}"
                    + "if (!($cred1)){exit}"
                    + "Add-Type -Path \\\"%userprofile%\\Microsoft.SharePoint.Client.dll\\\";"
                    + "Add-Type -Path \\\"%userprofile%\\Microsoft.SharePoint.Client.Runtime.dll\\\""
                    + "$clientContext = New-Object Microsoft.SharePoint.Client.ClientContext($url);"
                    + "$cred = New-Object Microsoft.SharePoint.Client.SharePointOnlineCredentials($cred1.username, $cred1.Password);"
                    + "$clientContext.Credentials = $cred;"
                    + "$web = $clientContext.Web;"
                    + "try {"
                    + "$clientContext.ExecuteQuery()}"
                    + " catch {"
                    + " if ($_.Exception){"
                    + "$cred=$false;"
                    + "Remove-Item 'C:\\dato\\cred.xml'}}"
                    + "return $cred}"
                    + "Function subir {"
                    + " $url_sharepoint=\\\"https://suramericana-my.sharepoint.com\\\";"
                    + " $Url_site = \\\"/personal/mpinto_sura_com_co\\\";"
                    + " $library= \\\"Documents\\\";"
                    + " $t1  = get-date;"
                    + " $url =$url = $url_sharepoint+$Url_site;"
                    + " $datos =Import-Csv -encoding UTF8 $archivos;"
                    + " Add-Type -Path \\\"%userprofile%\\Microsoft.SharePoint.Client.dll\\\";"
                    + " Add-Type -Path \\\"%userprofile%\\Microsoft.SharePoint.Client.Runtime.dll\\\";"
                    + " $clientContext = New-Object Microsoft.SharePoint.Client.ClientContext($url);"
                    + " do {"
                    + "     $clientContext.Credentials = credenciales}until ($clientContext.Credentials)"
                    + "     $web = $clientContext.Web;"
                    + "     $clientContext.Load($web);"
                    + "     $clientContext.ExecuteQuery();"
                    + "     ($web.Lists).Count;$mylist = $web.GetList($url+\\\"/\\\"+$library);"
                    + "     $clientContext.Load($mylist);"
                    + "     $clientContext.ExecuteQuery();"
                    + "     $source_folder = " + path_short + ";"
                    + "     $directories= @();"
                    + "     foreach ($file in  split-path($datos).Fullname){"
                    + "         $directories +=$file.replace($source_folder+\\\"\\\\\\\",\\\"\\\")}"
                    + "     foreach ($directory in $directories){"
                    + "         $myfolder = $mylist.RootFolder;"
                    + "         $clientContext.Load($myfolder);"
                    + "         $clientContext.ExecuteQuery();"
                    + "         $myfolder = $myfolder.Folders.Add($directory.split('\\')[0]);"
                    + "         $clientContext.Load($myfolder);"
                    + "         $clientContext.ExecuteQuery();"
                    + "         for ($i = 1; $i -le ($directory.split('\\').Count-1) ; $i++){"
                    + "             $myfolder = $myfolder.folders.Add(($directory.split('\\'))[$i]);"
                    + "             $clientContext.Load($myfolder);"
                    + "             $clientContext.ExecuteQuery()}}"
                    + "     $t1  = get-date;"
                    + "     $i=1;"
                    + "     $count = ($datos| Measure-Object -Property FullName ).Count;"
                    + "     foreach ($file in ($datos).Fullname){"
                    + "         $t01  = get-date;"
                    + "         $url_dest = $url_sharepoint+$Url_site+'/'+$library+($file.Replace($source_folder,'')).Replace('\\','/');"
                    + "         $FileStream = New-Object IO.FileStream($File,[System.IO.FileMode]::Open);"
                    + "         $FileCreationInfo = New-Object Microsoft.SharePoint.Client.FileCreationInformation;"
                    + "         $FileCreationInfo.Overwrite = $true;"
                    + "         $FileCreationInfo.ContentStream = $FileStream;"
                    + "         $FileCreationInfo.URL = $url_dest;"
                    + "         $Upload = $mylist.RootFolder.Files.Add($FileCreationInfo);"
                    + "         $listItem = $upload.ListItemAllFields;"
                    + "         $listItem['Title']=($file).split('.')[0];"
                    + "         Write-Host \\\" Uploading file $i/$count $url_dest\\\";"
                    + "         $listItem.update();"
                    + "         $clientContext.Load($Upload);"
                    + "         $clientContext.RequestTimeout = 1000000000;"
                    + "         $clientContext.ExecuteQuery();"
                    + "         $t02  = get-date;"
                    + "         $speed =\\\"{0:n2}\\\" -f ($file.Length/($t02-$t01).TotalSeconds/1mb);"
                    + "         Write-Host \\\"...................upload speed was \\\" $speed \\\" MB/sec\\\";"
                    + "         $i++}"
                    + "     $t2=get-date;"
                    + "     $size = \\\"{0:n2}\\\" -f (gci -path $source_folder -recurse | measure-object -property length -sum).sum;"
                    + "     Write-Host \\\"Medium upload speed was \\\" $speed \\\" MB/sec\\\"}"
                    + "     subir\"";
            // Ejecutar el proceso
            System.out.println("Inicia ejecución del proceso.");
            Process p = Runtime.getRuntime().exec(cmdBackUp);
            // Obtiene el resultado del Script
            System.out.println("Finaliza ejecución del proceso.");
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
            JOptionPane.showMessageDialog(null, "Error en ejecución de Back Up: " + e.getMessage());
        }
    }
}
