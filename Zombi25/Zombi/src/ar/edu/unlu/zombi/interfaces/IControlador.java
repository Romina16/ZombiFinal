package ar.edu.unlu.zombi.interfaces;

import java.util.List;
import java.util.UUID;

import ar.edu.unlu.zombi.modelo.DTO.CartaDTO;
import ar.edu.unlu.zombi.modelo.DTO.JugadorDTO;

public interface IControlador {
	
    void setVista(IVista vista);
    int obtenerCantidadMinimaJugadores();
    int obtenerCantidadMaximaJugadores();
    void mostrarPanelCargaNombreJugador();
    void obtenerDatosCargaNombreJugador(String nombreJugador);    
    void mostrarPanelDefinirCantidadJugadores();
    void obtenerDatosCargaCantidadJugadores(String cantidadJugadores);
    void mostrarPanelMenuPrincipal();
    void iniciarPartida();
    void mostrarPanelEsperaJugadores();
    void mostrarPanelNombresJugadoresCargados();
    List<String> obtenerNombresJugadores();
    void iniciarRonda();
    void mostrarPanelRondaJugador();
    String obtenerNombreJugadorActual();
    List<CartaDTO> obtenerUltimasDosCartasMazoParejas();
    List<CartaDTO> obtenerMazoJugador();
    int obtenerCantidadCartasJugadoresDerecha();
    void obtenerDatosCargaRondaJugadorTurno(String indiceCartaJugadorDerecha);
    void mostrarPanelFinalRonda();
    String obtenerNombreJugadorPerdedor();
    void volverAlMenuPrincipal();
    void PersistirPartida();
    void continuarPartidaPersistida();
    void mostrarPanelNombresJugadoresPartidaPersistida();
    void mostrarPanelPartidaPersistida();
    boolean hayPartidaPersistida();
    List<JugadorDTO> obtenerJugadoresPartidaPersistida();
    void obtenerDatosCargaJugadorPartidaPersistida(UUID id);
    
}
