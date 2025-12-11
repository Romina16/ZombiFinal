package ar.edu.unlu.zombi.vista.consola.paneles;

import javax.swing.JPanel;

import ar.edu.unlu.zombi.interfaces.IPanel;
import ar.edu.unlu.zombi.interfaces.IVista;
import ar.edu.unlu.zombi.vista.consola.JFramePrincipal;

public class panelDefinirCantidadJugadores extends JPanel implements IPanel{

	private static final long serialVersionUID = 1L;
	private IVista administradorVista;
	private JFramePrincipal frame;
	
	public panelDefinirCantidadJugadores(IVista administradorVista,JFramePrincipal frame) {
		this.administradorVista = administradorVista;
		this.frame = frame;
	}
	
	private void inicializarAccionEnter() {
		frame.setEnterAction(e ->{
			String raw = frame.getInputText();
			String input = raw ==null ? "" : raw.trim();
			if(input.isEmpty()) {
				administradorVista.mostrarMensajeError("Se debe ingresar un numero");
			}else {
				frame.appendLine(">"+input);
				try {
					int opcion = Integer.parseInt(input);
					if (opcion >= 2 && opcion <= 4) {
						administradorVista.obtenerDatosCargaCantidadJugadores(input);
					}else {
						administradorVista.mostrarMensajeError("Opcion invalida. Debe ser entre 2 y 4");
					}
				}catch(NumberFormatException ex){
					administradorVista.mostrarMensajeError("Debe ingresar un numero valido");
				}
			}
			frame.clearInput();
			});
	}
	
	private void obtenerPanel() {
		frame.appendLine("---CANTIDAD DE JUGADORES---");
		frame.appendLine("");
		frame.appendLine("--Elija la cantidad de jugadores a jugar(entre 2 y 4)--");
	}
	
	
	@Override
	public void mostrarPanel() {
        inicializarAccionEnter();
        obtenerPanel();
        frame.clearInput();
        frame.setEditabledInput(true);
	}

	@Override
	public void mostrarMensajeError(String mensaje) {frame.appendLine(mensaje);
	}

}
