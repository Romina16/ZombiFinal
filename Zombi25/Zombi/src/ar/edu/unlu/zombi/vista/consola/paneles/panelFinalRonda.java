package ar.edu.unlu.zombi.vista.consola.paneles;

import javax.swing.JPanel;

import ar.edu.unlu.zombi.interfaces.IPanel;
import ar.edu.unlu.zombi.interfaces.IVista;
import ar.edu.unlu.zombi.vista.consola.JFramePrincipal;

public class panelFinalRonda extends JPanel implements IPanel {

	private static final long serialVersionUID = 1L;
	private IVista administradorVista;
	private JFramePrincipal frame; 
	
	private String nombreJugadorPerdedor;
	
	public panelFinalRonda(IVista administradorVista,JFramePrincipal frame) {
		this.administradorVista = administradorVista;
		this.frame = frame;
	}
	
	private void obtenerDatosPanel() {
		nombreJugadorPerdedor = administradorVista.obtenerNombreJugadorPerdedor();
	}
	 
	private void inicializarAccionEnter() {
		frame.setEnterAction(e -> {
			administradorVista.volverAlMenuPrincipal();
		});
	}

		
	private void obtenerPanel() {
		frame.appendLine("=== FIN DEL JUEGO ===");
		frame.appendLine("");
		frame.appendLine("Nombre del jugador perdedor: "+nombreJugadorPerdedor);
		frame.appendLine("");
		frame.appendLine("Presione Enter para ir al menu principal ");
		
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
