package ar.edu.unlu.zombi;

import java.util.ArrayList;

import javax.swing.*;
import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.cliente.Cliente;
import ar.edu.unlu.zombi.controlador.Controlador;
import ar.edu.unlu.zombi.interfaces.IVista;
import ar.edu.unlu.zombi.vista.administradores.AdministradorVistaConsola;
import ar.edu.unlu.zombi.vista.administradores.AdministradorVistaUI;

public class AppCliente {
	private static final String IP_SERVIDOR = "127.0.0.1";
    private static final Integer PUERTO_SERVIDOR = 9999;
    private static final String IP_CLIENTE = "127.0.0.1";

    public static void main (String[] args) throws Exception {
    	
    	/*ArrayList<String> ips = Util.getIpDisponibles();
    	String ipCliente = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la que escuchara peticiones el cliente",
                "IP del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ips.toArray(),
                null
        );*/
    	
        String puertoCliente = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el cual escuchar peticiones del cliente",
                "Puerto del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                9998
        );
        
        
    	/*String ipServidor = (String) JOptionPane.showInputDialog(
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
                "Seleccione el puerto en el que corre el servidor",
                "Puerto del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                9998
        );*/
    	
    	String ipCliente = "127.0.0.1";
    	String ipServidor = "127.0.0.1";
    	String puertoServidor= "9999";
    	//String puertoCliente = "9998";
    	
    String[] opciones = {"Vista Grafica","Consola"};

    int seleccion = JOptionPane.showOptionDialog(
            null,
            "Seleccione el tipo de vista que desea:",
            "Vista de la aplicacion",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
            );
        //creo un nuevo controlador
        Controlador controlador = new Controlador();

        //creo una nueva vista
        IVista vista;
        if(seleccion == 0){
            vista = new AdministradorVistaUI();
        }else{
            vista = new AdministradorVistaConsola();
        }
        

        vista.setControlador(controlador);
        controlador.setVista(vista);

        Cliente c = new Cliente(
                IP_CLIENTE,
                Integer.parseInt(puertoCliente),
                IP_SERVIDOR,
                PUERTO_SERVIDOR
        );

        try{
            c.iniciar(controlador);
            System.out.println("Cliente Iniciado");
            //vista.mostrarPanelMenuPrincipal();
            vista.iniciar();
        }catch (RMIMVCException e) {
            e.printStackTrace();
            return;
        }
        
    }
}
