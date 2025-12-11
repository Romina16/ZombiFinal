package ar.edu.unlu.zombi.interfaces;

import java.util.List;
import java.util.UUID;

import ar.edu.unlu.zombi.modelo.DTO.CartaDTO;
import ar.edu.unlu.zombi.modelo.DTO.JugadorDTO;

public interface IVista {
	
	void iniciar();
	void setControlador(IControlador controlador);
    int obtenerCantidadMinimaJugadores();
    int obtenerCantidadMaximaJugadores();
    void mostrarPanelCargaNombreJugador();
    void obtenerDatosCargaNombreJugador(String nombreJugador);
    void mostrarPanelDefinirCantidadJugadores();
    void obtenerDatosCargaCantidadJugadores(String cantidadJugadores);
    void mostrarPanelMenuPrincipal();
    void IniciarPartida();
    void SalirJuego();
    void mostrarPanelEsperaJugadores();
    void mostrarPanelNombresJugadoresCargados();
    List<String> obtenerNombresJugadores();
    void iniciarRonda();
    void mostrarPanelRondaJugadorTurno();
    void mostrarPanelRondaJugadorObservador();
    String obtenerNombreJugadorActual();
    List<CartaDTO> obtenerUltimasDosCartasMazoParejas();
    List<CartaDTO> obtenerMazoJugador();
    int ObtenerCantidadCartasJugadorDerecha();
    void obtenerDatosCargaRondaJugadorTurno(String indiceCartJugadorDerecha);
    void mostrarPanelFinalRonda();
    String obtenerNombreJugadorPerdedor();
    void volverAlMenuPrincipal();
    //Persistencia
    void persistirPartida();
    void mostrarPanelPartidaPersistida();
    boolean hayPartidaPersistida();
    void continuarPartidaPersistida();
    void mostrarPanelNombresJugadoresPartidaPersistida();
    List<JugadorDTO> obtenerJugadoresPartidaPersistida();
    void obtenerDatosCargaJugadorPartidaPersistida(UUID id);
    //
    void mostrarMensajeError(String mensaje);
    
}
