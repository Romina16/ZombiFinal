package ar.edu.unlu.zombi.vista.consola.paneles;

import java.util.List;

import javax.swing.JPanel;

import ar.edu.unlu.zombi.interfaces.IPanel;
import ar.edu.unlu.zombi.interfaces.IVista;
import ar.edu.unlu.zombi.modelo.DTO.CartaDTO;
import ar.edu.unlu.zombi.vista.consola.JFramePrincipal;

public class panelRondaJugadorObservador extends JPanel implements IPanel{

	private static final long serialVersionUID = 1L;
	private IVista administradorVista;
	private JFramePrincipal frame;
	
	private String nombreJugadorActual;
	private List<CartaDTO> mazoParejas;//mazo parejas
	private List<CartaDTO> mazoJugador; 

	public panelRondaJugadorObservador(IVista administradorVista,JFramePrincipal frame) {
		this.administradorVista = administradorVista;
		this.frame = frame;
	}
	
	private void inicializarAccionEnter() {
		frame.setEnterAction(e -> {
			frame.clearInput();
		});
	}
	
	private void obtenerDatosPanel() {
		nombreJugadorActual = administradorVista.obtenerNombreJugadorActual();
		mazoParejas = administradorVista.obtenerUltimasDosCartasMazoParejas();
		mazoJugador = administradorVista.obtenerMazoJugador();
	}
	
	private void obtenerPanel() {
		frame.appendLine("---JUGADOR OBSERVADOR---");
		frame.appendLine("");
		frame.appendLine("Turno del jugador: " + nombreJugadorActual);
		frame.appendLine("");
		frame.appendLine("");
		if(!mazoParejas.isEmpty()) {
			frame.appendLine("Mazo Parejas: "+mazoParejas.get(0).toString()+" , "+ mazoParejas.get(1).toString() + " ");
		}else {
			frame.appendLine("Mazo Parejas: no hay cartas");
		}
		frame.appendLine("");
		frame.appendLine("");
		frame.appendLine("Cartas Jugador: "+ mazoJugador);
		frame.appendLine("");
		frame.appendLine("Espere su turno para jugar");
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
	public void mostrarMensajeError(String mensaje) {frame.appendLine(mensaje);}

}
