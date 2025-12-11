package ar.edu.unlu.zombi.vista.administradores;

import java.util.List;
import java.util.UUID;

import ar.edu.unlu.zombi.interfaces.IControlador;
import ar.edu.unlu.zombi.interfaces.IVista;
import ar.edu.unlu.zombi.modelo.DTO.CartaDTO;
import ar.edu.unlu.zombi.modelo.DTO.JugadorDTO;

public class AdministradorVistaUI implements IVista{

	@Override
	public void iniciar() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setControlador(IControlador controlador) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarPanelMenuPrincipal() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void IniciarPartida() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void SalirJuego() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarPanelDefinirCantidadJugadores() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hayPartidaPersistida() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void mostrarMensajeError(String mensaje) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int obtenerCantidadMinimaJugadores() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int obtenerCantidadMaximaJugadores() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void obtenerDatosCargaCantidadJugadores(String cantidadJugadores) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarPanelEsperaJugadores() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarPanelCargaNombreJugador() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void obtenerDatosCargaNombreJugador(String nombreJugador) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarPanelNombresJugadoresCargados() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> obtenerNombresJugadores() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void iniciarRonda() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarPanelRondaJugadorTurno() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarPanelRondaJugadorObservador() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String obtenerNombreJugadorActual() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CartaDTO> obtenerUltimasDosCartasMazoParejas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CartaDTO> obtenerMazoJugador() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int ObtenerCantidadCartasJugadorDerecha() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void obtenerDatosCargaRondaJugadorTurno(String indiceCartJugadorDerecha) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarPanelFinalRonda() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String obtenerNombreJugadorPerdedor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void volverAlMenuPrincipal() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void persistirPartida() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarPanelPartidaPersistida() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void continuarPartidaPersistida() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarPanelNombresJugadoresPartidaPersistida() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<JugadorDTO> obtenerJugadoresPartidaPersistida() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void obtenerDatosCargaJugadorPartidaPersistida(UUID id) {
		// TODO Auto-generated method stub
		
	}


}
