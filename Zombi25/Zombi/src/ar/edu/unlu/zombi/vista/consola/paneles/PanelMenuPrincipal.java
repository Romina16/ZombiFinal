package ar.edu.unlu.zombi.vista.consola.paneles;

import javax.swing.JPanel;

import ar.edu.unlu.zombi.interfaces.IPanel;
import ar.edu.unlu.zombi.interfaces.IVista;
import ar.edu.unlu.zombi.vista.consola.JFramePrincipal;

public class PanelMenuPrincipal extends JPanel implements IPanel {

	private static final long serialVersionUID = 1L;
	private IVista administradorVista;
    private JFramePrincipal frame;
    private boolean hayPartidaPersistida;

	public PanelMenuPrincipal(IVista administradorVista,JFramePrincipal frame) {
	        this.administradorVista = administradorVista;
	        this.frame = frame;
	    }

	    private void obtenerDatosPanel(){ hayPartidaPersistida = administradorVista.hayPartidaPersistida();}

	    private void inicializarAccionEnter(){
	        frame.setEnterAction(e ->{
	            String raw = frame.getInputText();
	            String input = raw == null ? "" : raw.trim();

	            if(input.isEmpty()){
	                administradorVista.mostrarMensajeError("Se debe ingresar una opcion");
	            }else{
	                frame.appendLine(">"+input);
	                try {
	                    int opcion = Integer.parseInt(input);
	                    if (!hayPartidaPersistida) {
	                        switch (opcion) {
	                            case 1 -> administradorVista.IniciarPartida();
	                            case 0 -> administradorVista.SalirJuego();
	                            default -> {
	                                administradorVista.mostrarMensajeError("Opcion invalida. Elegi 1 o 0");
	                            }
	                        }
	                    }else{//caso de que hay una partida persistida
	                        switch (opcion){
	                            case 1 -> administradorVista.IniciarPartida();
	                            case 2 -> administradorVista.continuarPartidaPersistida();
	                            case 0 -> administradorVista.SalirJuego();
	                            default -> {
	                                administradorVista.mostrarMensajeError("Opcion invalida. Elegi 1, 2 o 0");
	                            }
	                        }
	                    }
	                } catch (NumberFormatException ex){
	                    administradorVista.mostrarMensajeError("Formato invalido. Elegi un numero");}
	            }

	            frame.clearInput();
	        });
	    }


	    public void obtenerPanel(){
	        frame.appendLine("----ZOMBI----");
	        frame.appendLine("-------------");
	        frame.appendLine("----Menu-----");
	        frame.appendLine("1)Iniciar Juego");
	        if(hayPartidaPersistida){
	            frame.appendLine("2)Continuar partida");
	        }
	        frame.appendLine("0)Salir Juego");
	        frame.appendLine("-------------");
	        frame.appendLine("Elija una opcion y presione Enter");
	    }


	    @Override
	    public void mostrarPanel() {
	        obtenerDatosPanel();
	        inicializarAccionEnter();
	        obtenerPanel();
	        frame.clearInput();
	        frame.setEditabledInput(true);
	    }

	    @Override
	    public void mostrarMensajeError(String mensaje) {frame.appendLine(mensaje); }
	}

