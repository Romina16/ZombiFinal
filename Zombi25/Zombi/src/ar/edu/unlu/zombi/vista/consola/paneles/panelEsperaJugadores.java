package ar.edu.unlu.zombi.vista.consola.paneles;

import javax.swing.JPanel;

import ar.edu.unlu.zombi.interfaces.IPanel;
import ar.edu.unlu.zombi.interfaces.IVista;
import ar.edu.unlu.zombi.vista.consola.JFramePrincipal;

public class panelEsperaJugadores extends JPanel implements IPanel{

	private static final long serialVersionUID = 1L;
	private IVista administradorVista;
	private JFramePrincipal frame;
	
	public panelEsperaJugadores(IVista administradorVista,JFramePrincipal frame) {
		this.administradorVista = administradorVista;
		this.frame = frame;
	}
	
	private void inicializarAccionEnter() {
        frame.setEnterAction(e -> {
        	frame.clearInput();
        });
    }
	
	private void obtenerPanel() {
    	frame.appendLine("=== ESPERANDO JUGADORES ===");
        frame.appendLine("");
        frame.appendLine("Esperando jugadores...");
        frame.appendLine("");
	}

	@Override
	public void mostrarPanel() {
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
