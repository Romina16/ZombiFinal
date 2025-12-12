package ar.edu.unlu.zombi.vista.administradores;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.JPanel;

import ar.edu.unlu.zombi.interfaces.IControlador;
import ar.edu.unlu.zombi.interfaces.IPanel;
import ar.edu.unlu.zombi.interfaces.IVista;
import ar.edu.unlu.zombi.modelo.DTO.CartaDTO;
import ar.edu.unlu.zombi.modelo.DTO.JugadorDTO;
import ar.edu.unlu.zombi.vista.grafica.JFramePrincipal;
import ar.edu.unlu.zombi.vista.grafica.paneles.JPanelCargaNombreJugador;
import ar.edu.unlu.zombi.vista.grafica.paneles.JPanelDefinirCantidadJugadores;
import ar.edu.unlu.zombi.vista.grafica.paneles.JPanelEsperaJugadores;
import ar.edu.unlu.zombi.vista.grafica.paneles.JPanelFinalRonda;
import ar.edu.unlu.zombi.vista.grafica.paneles.JPanelMenuPrincipal;
import ar.edu.unlu.zombi.vista.grafica.paneles.JPanelNombreJugadoresPartidaPersistida;
import ar.edu.unlu.zombi.vista.grafica.paneles.JPanelNombresJugadoresCargados;
import ar.edu.unlu.zombi.vista.grafica.paneles.JPanelPartidaPersistida;
import ar.edu.unlu.zombi.vista.grafica.paneles.JPanelRondaJugadorObservador;
import ar.edu.unlu.zombi.vista.grafica.paneles.JPanelRondaJugadorTurno;

public class AdministradorVistaUI implements IVista {
	private final Map<String, Object> panels = new LinkedHashMap<>();
	private IControlador controlador;
    private JFramePrincipal framePrincipal;
    private IPanel panelActual;
    private JPanelMenuPrincipal panelMenuPrincipal;
    private JPanelDefinirCantidadJugadores panelDefinirCantidadJugadores;
    private JPanelEsperaJugadores PanelEsperaJugadores;
    private JPanelCargaNombreJugador PanelCargaNombreJugador;
    private JPanelNombresJugadoresCargados PanelNombresJugadoresCargados;
    private JPanelRondaJugadorTurno PanelRondaJugadorTurno;
    private JPanelRondaJugadorObservador PanelRondaJugadorObservador;
    private JPanelFinalRonda PanelFinalRonda;
    private JPanelPartidaPersistida PanelPartidaPersistida;
    private JPanelNombreJugadoresPartidaPersistida PanelNombreJugadoresPartidaPersistida;
	
    public AdministradorVistaUI() {
    	this.framePrincipal = new JFramePrincipal();
    	
    	panelMenuPrincipal = new JPanelMenuPrincipal(this,framePrincipal);
    	panelDefinirCantidadJugadores = new JPanelDefinirCantidadJugadores(this,framePrincipal);
    	PanelEsperaJugadores	= new JPanelEsperaJugadores(this,framePrincipal);
    	PanelCargaNombreJugador = new JPanelCargaNombreJugador(this,framePrincipal);
    	PanelNombresJugadoresCargados = new JPanelNombresJugadoresCargados(this,framePrincipal);
    	PanelRondaJugadorTurno = new JPanelRondaJugadorTurno(this,framePrincipal);
    	PanelRondaJugadorObservador = new JPanelRondaJugadorObservador(this,framePrincipal);
    	PanelFinalRonda = new JPanelFinalRonda(this,framePrincipal);
    	PanelPartidaPersistida = new JPanelPartidaPersistida(this,framePrincipal);
    	PanelNombreJugadoresPartidaPersistida= new JPanelNombreJugadoresPartidaPersistida(this,framePrincipal);
    	
    	addPanel("Menu Principal",panelMenuPrincipal);
    	addPanel("Definir Cantidad de jugadores",panelDefinirCantidadJugadores);
    	addPanel("Espera Jugadores",PanelEsperaJugadores);
    	addPanel("Carga Nombre de Jugador",PanelCargaNombreJugador);
    	addPanel("Nombres Jugadores Cargados",PanelNombresJugadoresCargados);
    	addPanel("Jugador Turno",PanelRondaJugadorTurno);
    	addPanel("Jugador Observador",PanelRondaJugadorObservador);
    	addPanel("Final de ronda",PanelFinalRonda);
    	/*addPanel("Partida Persistida",PanelPartidaPersistida);
    	addPanel("Nombres Jugadores Partida Persistida",PanelNombreJugadoresPartidaPersistida);*/
    	
    	showFrame();
    }
    
    public void iniciar() {
    	this.mostrarPanelCargaNombreJugador();
    }
    
    public void showFrame() { framePrincipal.showFrame();}//mostrar el framPrincipal
    
    public void addPanel(String nombre, Object panel) {panels.put(nombre, panel);}
    
    public void showPanel(String nombre) {
    	System.out.println("lo llama "+ nombre);
    	IPanel panel = (IPanel) panels.get(nombre);
    	panelActual = panel;
    	panel.mostrarPanel();
    }
    
    public void mostrarPanelCargaNombreJugador1() {
		showPanel("Carga Nombre de Jugador");
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
		System.out.println("LLEGO a mostrarPanelDefinirCantidadJugadores");
		showPanel("Definir Cantidad de jugadores");
	}

	@Override
	public boolean hayPartidaPersistida() {
		return controlador.hayPartidaPersistida();
	}

	@Override
	public void mostrarMensajeError(String mensaje) {
		panelActual.mostrarMensajeError(mensaje);
//        panelActual.mostrarPanel();
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
		showPanel("Carga Nombre de Jugador");
		/*System.out.println("mostrarPanelCargaNombreJugador() VISTA");
		showPanel("Carga Nombre de Jugador");*/
		//JPanelCargaNombreJugador panel = new JPanelCargaNombreJugador(this, framePrincipal);
	    
	    // 2. Mostrar el panel en el JFramePrincipal, dándole un nombre único.
	    //framePrincipal.showPanel(panel, "CARGA_NOMBRE");
	    
	    // 3. Llamar al método de inicialización del panel después de ser añadido
	    // (Asegúrate que el panel es IPanel y que esta llamada sucede)
	    //((IPanel)panel).mostrarPanel();
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


