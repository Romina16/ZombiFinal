package ar.edu.unlu.zombi.vista.administradores;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ar.edu.unlu.zombi.interfaces.IControlador;
import ar.edu.unlu.zombi.interfaces.IPanel;
import ar.edu.unlu.zombi.interfaces.IVista;
import ar.edu.unlu.zombi.modelo.DTO.CartaDTO;
import ar.edu.unlu.zombi.modelo.DTO.JugadorDTO;
import ar.edu.unlu.zombi.vista.consola.JFramePrincipal;
import ar.edu.unlu.zombi.vista.consola.paneles.PanelMenuPrincipal;
import ar.edu.unlu.zombi.vista.consola.paneles.panelCargaNombreJugador;
import ar.edu.unlu.zombi.vista.consola.paneles.panelDefinirCantidadJugadores;
import ar.edu.unlu.zombi.vista.consola.paneles.panelEsperaJugadores;
import ar.edu.unlu.zombi.vista.consola.paneles.panelFinalRonda;
import ar.edu.unlu.zombi.vista.consola.paneles.panelNombreJugadoresPartidaPersistida;
import ar.edu.unlu.zombi.vista.consola.paneles.panelNombresJugadoresCargados;
import ar.edu.unlu.zombi.vista.consola.paneles.panelPartidaPersistida;
import ar.edu.unlu.zombi.vista.consola.paneles.panelRondaJugadorObservador;
import ar.edu.unlu.zombi.vista.consola.paneles.panelRondaJugadorTurno;

public class AdministradorVistaConsola implements IVista {
	private final Map<String, Object> panels = new LinkedHashMap<>();
	private IControlador controlador;
    private JFramePrincipal framePrincipal;
    private IPanel panelActual;
    private PanelMenuPrincipal panelMenuPrincipal;
    private panelDefinirCantidadJugadores PanelDefinirCantidadJugadores;
    private panelEsperaJugadores PanelEsperaJugadores;
    private panelCargaNombreJugador PanelCargaNombreJugador;
    private panelNombresJugadoresCargados PanelNombresJugadoresCargados;
    private panelRondaJugadorTurno PanelRondaJugadorTurno;
    private panelRondaJugadorObservador PanelRondaJugadorObservador;
    private panelFinalRonda PanelFinalRonda;
    private panelPartidaPersistida PanelPartidaPersistida;
    private panelNombreJugadoresPartidaPersistida PanelNombreJugadoresPartidaPersistida;
	
    public AdministradorVistaConsola () {
    	this.framePrincipal = new JFramePrincipal();
    	
    	panelMenuPrincipal = new PanelMenuPrincipal(this,framePrincipal);
    	PanelDefinirCantidadJugadores = new panelDefinirCantidadJugadores(this,framePrincipal);
    	PanelEsperaJugadores	= new panelEsperaJugadores(this,framePrincipal);
    	PanelCargaNombreJugador = new panelCargaNombreJugador(this,framePrincipal);
    	PanelNombresJugadoresCargados = new panelNombresJugadoresCargados(this,framePrincipal);
    	PanelRondaJugadorTurno = new panelRondaJugadorTurno(this,framePrincipal);
    	PanelRondaJugadorObservador = new panelRondaJugadorObservador(this,framePrincipal);
    	PanelFinalRonda = new panelFinalRonda(this,framePrincipal);
    	PanelPartidaPersistida = new panelPartidaPersistida(this,framePrincipal);
    	PanelNombreJugadoresPartidaPersistida= new panelNombreJugadoresPartidaPersistida(this,framePrincipal);
    	
    	addPanel("Menu Principal",panelMenuPrincipal);
    	addPanel("Definir Cantidad de jugadores",PanelDefinirCantidadJugadores);
    	addPanel("Espera Jugadores",PanelEsperaJugadores);
    	addPanel("Carga Nombre de Jugador",PanelCargaNombreJugador);
    	addPanel("Nombres Jugadores Cargados",PanelNombresJugadoresCargados);
    	addPanel("Jugador Turno",PanelRondaJugadorTurno);
    	addPanel("Jugador Observador",PanelRondaJugadorObservador);
    	addPanel("Final de ronda",PanelFinalRonda);
    	addPanel("Partida Persistida",PanelPartidaPersistida);
    	addPanel("Nombres Jugadores Partida Persistida",PanelNombreJugadoresPartidaPersistida);
    	
    	showFrame();
    }
    
    public void iniciar() {
    	this.mostrarPanelCargaNombreJugador();
    }
    
    public void showFrame() { framePrincipal.showFrame();}//mostrar el framPrincipal
    
    public void addPanel(String nombre, Object panel) {panels.put(nombre, panel);}
    
    public void showPanel(String nombre ) {
    	IPanel panel = (IPanel) panels.get(nombre);
    	panelActual = panel;
    	panel.mostrarPanel();
    }
	
	@Override
	public void setControlador(IControlador controlador) {this.controlador = controlador;	}

	@Override
	public void mostrarPanelMenuPrincipal() {
        showPanel("Menu Principal");
	}

	@Override
	public void IniciarPartida() {
		controlador.iniciarPartida();
	}

	@Override
	public void SalirJuego() {
		System.exit(0);
	}

	@Override
	public void mostrarPanelDefinirCantidadJugadores() {
		showPanel("Definir Cantidad de jugadores");
	}

	@Override
	public boolean hayPartidaPersistida() {
		return controlador.hayPartidaPersistida();
	}

	@Override
	public void mostrarMensajeError(String mensaje) {
		panelActual.mostrarMensajeError(mensaje);
        panelActual.mostrarPanel();
	}

	@Override
	public int obtenerCantidadMinimaJugadores() {
		return controlador.obtenerCantidadMinimaJugadores();
	}

	@Override
	public int obtenerCantidadMaximaJugadores() {
		return controlador.obtenerCantidadMaximaJugadores();
	}

	@Override
	public void obtenerDatosCargaCantidadJugadores(String cantidadJugadores) {
		controlador.obtenerDatosCargaCantidadJugadores(cantidadJugadores);
	}

	@Override
	public void mostrarPanelEsperaJugadores() {
		showPanel("Espera Jugadores");
	}

	@Override
	public void mostrarPanelCargaNombreJugador() {
		System.out.println("mostrarPanelCargaNombreJugador() VISTA");
		showPanel("Carga Nombre de Jugador");
	}

	@Override
	public void obtenerDatosCargaNombreJugador(String nombreJugador) {
		controlador.obtenerDatosCargaNombreJugador(nombreJugador);
	}

	@Override
	public void mostrarPanelNombresJugadoresCargados() {
		showPanel("Nombres Jugadores Cargados");
	}

	@Override
	public List<String> obtenerNombresJugadores() {
		return controlador.obtenerNombresJugadores();
	}

	@Override
	public void iniciarRonda() {
		controlador.iniciarRonda();
	}

	@Override
	public void mostrarPanelRondaJugadorTurno() {
		showPanel("Jugador Turno");
	}

	@Override
	public void mostrarPanelRondaJugadorObservador() {
		showPanel("Jugador Observador");
	}

	@Override
	public String obtenerNombreJugadorActual() {
		return controlador.obtenerNombreJugadorActual();
	}

	@Override
	public List<CartaDTO> obtenerUltimasDosCartasMazoParejas() {
		return controlador.obtenerUltimasDosCartasMazoParejas();
	}

	@Override
	public List<CartaDTO> obtenerMazoJugador() {
		return controlador.obtenerMazoJugador();
	}

	@Override
	public int ObtenerCantidadCartasJugadorDerecha() {
		return controlador.obtenerCantidadCartasJugadoresDerecha();
	}

	@Override
	public void obtenerDatosCargaRondaJugadorTurno(String indiceCartJugadorDerecha) {
		controlador.obtenerDatosCargaRondaJugadorTurno(indiceCartJugadorDerecha);
	}

	@Override
	public void mostrarPanelFinalRonda() {
		showPanel("Final de ronda");
	}

	@Override
	public String obtenerNombreJugadorPerdedor() {
		return controlador.obtenerNombreJugadorPerdedor();
	}

	@Override
	public void volverAlMenuPrincipal() {
		controlador.volverAlMenuPrincipal();
	}
	//serializacion
	@Override
	public void persistirPartida() {
		controlador.PersistirPartida();
	}

	@Override
	public void mostrarPanelPartidaPersistida() {
		showPanel("Partida Persistida");
	}

	@Override
	public void continuarPartidaPersistida() {
		controlador.continuarPartidaPersistida();
	}

	@Override
	public void mostrarPanelNombresJugadoresPartidaPersistida() {
		showPanel("Nombres Jugadores Partida Persistida");
	}

	@Override
	public List<JugadorDTO> obtenerJugadoresPartidaPersistida() {
		return controlador.obtenerJugadoresPartidaPersistida();
	}

	@Override
	public void obtenerDatosCargaJugadorPartidaPersistida(UUID id) {
		controlador.obtenerDatosCargaJugadorPartidaPersistida(id);
	}
}
