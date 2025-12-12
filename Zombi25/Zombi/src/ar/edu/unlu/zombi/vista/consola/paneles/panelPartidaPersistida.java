package ar.edu.unlu.zombi.vista.consola.paneles;

import javax.swing.JPanel;

import ar.edu.unlu.zombi.interfaces.IPanel;
import ar.edu.unlu.zombi.interfaces.IVista;
import ar.edu.unlu.zombi.vista.consola.JFramePrincipal;

public class PanelPartidaPersistida extends JPanel implements IPanel {

	private static final long serialVersionUID = 1L;

	private IVista administradorVista;
	private JFramePrincipal frame;
	
	private String nombreJugadorPerdedor;
	
	public PanelPartidaPersistida(
			IVista administradorVista, 
			JFramePrincipal frame) {
		this.administradorVista = administradorVista;
		this.frame = frame;    
	}
	
    private void obtenerDatosPanel() {
    	nombreJugadorPerdedor = administradorVista.obtenerNombreJugadorActual();
    }
		
    private void inicializarAccionEnter() {
        frame.setEnterAction(e -> {  
            administradorVista.volverAlMenuPrincipal();
        });
    }
        
    private void obtenerPanel() {
    	frame.appendLine("=== PARTIDA PAUSADA ===");
        frame.appendLine("");
        frame.appendLine("");
        frame.appendLine("El jugador : " + nombreJugadorPerdedor + " pauso la partida");
        frame.appendLine("");
        frame.appendLine("");
		frame.appendLine("Presione Enter para ir al menu principal:");
	}
    
	@Override
    public void mostrarPanel() {
		obtenerDatosPanel();
		inicializarAccionEnter();
        obtenerPanel();
        frame.clearInput();
        frame.setEditabledInput(false);
    }
	
	@Override
	public void mostrarMensajeError(String mensaje) {
		frame.appendLine(mensaje);
	}
    

}
