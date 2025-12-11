package ar.edu.unlu.zombi.vista.consola.paneles;

import java.util.List;

import javax.swing.JPanel;

import ar.edu.unlu.zombi.interfaces.IPanel;
import ar.edu.unlu.zombi.interfaces.IVista;
import ar.edu.unlu.zombi.vista.consola.JFramePrincipal;

public class panelNombresJugadoresCargados extends JPanel implements IPanel {

	private static final long serialVersionUID = 1L;
	private IVista administradorVista;
	private JFramePrincipal frame;
	private List<String> nombresJugadores;
	
	public panelNombresJugadoresCargados(IVista administradorVista,JFramePrincipal frame) {
		this.administradorVista = administradorVista;
		this.frame = frame;  
	}
	
	private void obtenerDatosPanel() {
    	nombresJugadores = administradorVista.obtenerNombresJugadores();
    }
		
    private void inicializarAccionEnter() {
        frame.setEnterAction(e -> {  
            administradorVista.iniciarRonda(); // en vez de iniciar otro
        });
    }
        
    private void obtenerPanel() {
    	frame.appendLine("=== JUGADORES CARGADOS ===");
        frame.appendLine("");
        frame.appendLine("");
        for(String nombreJugador: nombresJugadores) {
        	frame.appendLine("Jugador: " + nombreJugador);
        }
        frame.appendLine("");
        frame.appendLine("");
		frame.appendLine("Presione Enter para comenzar la partida:");
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
