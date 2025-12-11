package ar.edu.unlu.zombi;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.servidor.Servidor;
import ar.edu.unlu.zombi.modelo.Modelo;

public class AppServidor {
	private static final String IP_SERVIDOR = "127.0.0.1";
    private static final Integer PUERTO_SERVIDOR = 9999;

    public static void main(String[] args){
    	/*ArrayList<String> ips = Util.getIpDisponibles();
    	String ip = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la que escuchara peticiones el servidor",
                "IP del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ips.toArray(),
                null
        );
    	
    	String puertoServidor = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en la que escuchara peticiones el servidor",
                "Puerto del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                9999
        );*/
    	
       // Servidor servidor = new Servidor(ip,Integer.parseInt(portCliente));
    	
    	String ip = IP_SERVIDOR;
    	Integer puertoServidor = PUERTO_SERVIDOR;
    	
    	Modelo modelo = new Modelo();
        Servidor servidor = new Servidor(ip,puertoServidor);
        
        
        try{
            servidor.iniciar(modelo);
        } catch (RemoteException e){
            e.printStackTrace();
        } catch (RMIMVCException e){
            e.printStackTrace();
        }
    }
}
