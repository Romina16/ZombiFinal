package ar.edu.unlu.zombi.vista.consola.paneles;

import java.util.List;

import javax.swing.JPanel;

import ar.edu.unlu.zombi.interfaces.IPanel;
import ar.edu.unlu.zombi.interfaces.IVista;
import ar.edu.unlu.zombi.modelo.DTO.JugadorDTO;
import ar.edu.unlu.zombi.vista.consola.JFramePrincipal;

public class PanelNombresJugadoresPartidaPersistida extends JPanel implements IPanel{

	private static final long serialVersionUID = 1L;

	private IVista administradorVista;
	private JFramePrincipal frame;
	
	private List<JugadorDTO> jugadoresPartidaPersistida;
	private int cantidadJugadores;
	
	public PanelNombresJugadoresPartidaPersistida(IVista administradorVista, JFramePrincipal frame) {
		this.administradorVista = administradorVista;
		this.frame = frame;    
	}
	
    private void obtenerDatosPanel() {
    	jugadoresPartidaPersistida = administradorVista.obtenerJugadoresPartidaPersistida();
    	cantidadJugadores = jugadoresPartidaPersistida.size();
    }
		
    private void inicializarAccionEnter() {
    	frame.setEnterAction(e -> {
    		String raw = frame.getInputText();
			String input = raw == null ? "" : raw.trim();
	
			if (input.isEmpty()) {
				administradorVista.mostrarMensajeError("Debes ingresar una opción.");
			} else {
	            
				frame.appendLine("> " + input);
	
				try {
		            	
					int opcion = Integer.parseInt(input);
					
					if((opcion >= 1) && (opcion <= cantidadJugadores)) {
						administradorVista.obtenerDatosCargaJugadorPartidaPersistida(jugadoresPartidaPersistida.get(opcion - 1).getId());
					} else {
						administradorVista.mostrarMensajeError("Opción inválida. Elige entre 1 y " + cantidadJugadores);
					}		
		                
				} catch (NumberFormatException ex) {
					administradorVista.mostrarMensajeError("Formato inválido: ingresa un número.");
				}
	             
			}

		frame.clearInput();
	        
		});
	}
        
    private void obtenerPanel() {
    	int indiceJugador = 1;
    	frame.appendLine("=== NOMBRES JUGADORES PARTIDA GUARDADA ===");
        frame.appendLine("");
        frame.appendLine("");
        for(JugadorDTO jugador: jugadoresPartidaPersistida) {
        	frame.appendLine("Jugador " + indiceJugador + ": " + jugador.getNombre());
        	indiceJugador ++;
        }
        frame.appendLine("");
        frame.appendLine("");
		frame.appendLine("Elija el jugador a recuperar (Entre 1 y " + cantidadJugadores + "):");
	}
    
	@Override
    public void mostrarPanel() {
		System.out.println("mostrarPanel partida jugadores nombre");
		obtenerDatosPanel();
		inicializarAccionEnter();
        obtenerPanel();
        frame.clearInput();
        frame.setEditabledInput(true);
    }

	@Override
	public void mostrarMensajeError(String mensaje) {
		frame.appendLine(mensaje);
	}

}
