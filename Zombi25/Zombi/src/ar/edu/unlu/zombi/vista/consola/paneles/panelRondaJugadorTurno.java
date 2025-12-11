package ar.edu.unlu.zombi.vista.consola.paneles;

import java.util.List;

import javax.swing.JPanel;

import ar.edu.unlu.zombi.interfaces.IPanel;
import ar.edu.unlu.zombi.interfaces.IVista;
import ar.edu.unlu.zombi.modelo.DTO.CartaDTO;
import ar.edu.unlu.zombi.vista.consola.JFramePrincipal;

public class panelRondaJugadorTurno extends JPanel implements IPanel{

	private static final long serialVersionUID = 1L;
	private IVista administradorVista;
	private JFramePrincipal frame;
	private String nombreJugadorActual;
	private List<CartaDTO> mazoParejas;//mazo parejas
	private List<CartaDTO> mazoJugador;
	private int cantidadCartasJugadorDerecha;
	
	public panelRondaJugadorTurno(IVista administradorVista,JFramePrincipal frame) {
		this.administradorVista = administradorVista;
		this.frame = frame;
	}
	
	private void obtenerDatosPanel() {
		nombreJugadorActual = administradorVista.obtenerNombreJugadorActual();
		mazoParejas = administradorVista.obtenerUltimasDosCartasMazoParejas();
		mazoJugador = administradorVista.obtenerMazoJugador();
		cantidadCartasJugadorDerecha = administradorVista.ObtenerCantidadCartasJugadorDerecha();
	}
	
	private void inicializarAccionEnter() {
		frame.setEnterAction(e -> {
			String raw = frame.getInputText();
            String input = raw == null ? "" : raw.trim();
            
            if (input.isEmpty()) {
                administradorVista.mostrarMensajeError("Debes ingresar una carta del jugador de la derecha");
            } else {
            	frame.appendLine("> " + input);
            	try {
            		int opcion = Integer.parseInt(input);
            		if(opcion == 0) {
            			administradorVista.persistirPartida();
            		}
            		if ((opcion>= 1) && (opcion <= cantidadCartasJugadorDerecha)) {
            			administradorVista.obtenerDatosCargaRondaJugadorTurno(input);
            		}else {
            			administradorVista.mostrarMensajeError("Opcion invalida. Elige entre 1 y "+ cantidadCartasJugadorDerecha);
            		}
            		
            	}catch(NumberFormatException ex){
					administradorVista.mostrarMensajeError("Formato invalido: ingresa un numero");
				}
            }
            frame.clearInput();
		});
	}
	
	
	private void obtenerPanel() {
		frame.appendLine("--- TURNO JUGADOR ---");
		frame.appendLine("");
		frame.appendLine("Turno del jugador " + nombreJugadorActual);
		frame.appendLine("");
		frame.appendLine("");
		if(!mazoParejas.isEmpty()) {
			frame.appendLine("Mazo parejas: " + mazoParejas.get(0).toString() + ", " + mazoParejas.get(1).toString() + "");
		}else {
			frame.appendLine("Mazo Parejas: no hay cartas");
		}
		frame.appendLine("");
		frame.appendLine("");
		frame.appendLine("Cartas Jugador: " + mazoJugador);
		frame.appendLine("");
		frame.appendLine("Ingrese un numero de la carta a obtener del jugador de la derecha:( Entre 1 y "+cantidadCartasJugadorDerecha+")");
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
	public void mostrarMensajeError(String mensaje) {
		frame.appendLine(mensaje);
	}
	
	
	
}
