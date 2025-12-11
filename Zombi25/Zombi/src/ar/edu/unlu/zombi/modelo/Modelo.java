package ar.edu.unlu.zombi.modelo;

import java.util.List;
import java.util.NoSuchElementException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.UUID;

import ar.edu.unlu.zombi.interfaces.IModelo;
import ar.edu.unlu.zombi.modelo.entidades.Carta;
import ar.edu.unlu.zombi.modelo.entidades.Jugador;
import ar.edu.unlu.zombi.modelo.entidades.Mazo;
import ar.edu.unlu.zombi.modelo.enumerados.EstadoJuego;
import ar.edu.unlu.zombi.modelo.enumerados.EventoGeneral;
import ar.edu.unlu.zombi.modelo.serializacion.AdministradorSerializacion;
import ar.edu.unlu.rmimvc.observer.IObservadorRemoto;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;


public class Modelo extends ObservableRemoto implements IModelo,Serializable{
	private static final long serialVersionUID = 1L;
	private static final int MINIMO_JUGADORES = 2;
	private static final int MAXIMO_JUGADORES = 4;
		
	private int cantidadJugadoresActuales = -1;
	private List<Jugador> jugadores;
	
	private Mazo mazo;
	private Stack<Carta> mazoParejas;
	private int posicionJugadorActual = 0;
	
	private int jugadoresEnEspera = 0;
	
	private AdministradorSerializacion administradorSerializacion;
	private boolean isPartidaRecuperada;
	private List<Jugador> jugadoresAReasignar;
	
	//manejar estado del juego
	private EstadoJuego estadoJuego;
	
	public Modelo() {
		this.jugadores = new ArrayList<Jugador>();
		this.administradorSerializacion = new AdministradorSerializacion();
		this.estadoJuego = EstadoJuego.INICIO;
	}
	
	public boolean sePuedenIngresarJugadores() throws RemoteException {
		return this.estadoJuego.equals(EstadoJuego.ESPERA_DE_JUGADORES);
	}
	
	@Override
	public List<Jugador> obtenerJugadores() throws RemoteException {
		return this.jugadores;
	}

	@Override
	public int obtenerPosicionJugadorActual() throws RemoteException {
		return this.posicionJugadorActual;
	}

	@Override
	public Stack<Carta> obtenerMazoParejas() throws RemoteException {
		return this.mazoParejas;
	}

	@Override
	public int obtenerCantidadMinimaJugadores() throws RemoteException {
		return Modelo.MINIMO_JUGADORES;
	}

	@Override
	public int obtenerCantidadMaximaJugadores() throws RemoteException {
		return Modelo.MAXIMO_JUGADORES;
	}

	@Override
	public boolean esCantidadJugadoresDefinida() throws RemoteException {
		return !(cantidadJugadoresActuales ==-1);
	}

	@Override
	public UUID obtenerJugadorActualId() throws RemoteException {
		return jugadores.get(posicionJugadorActual).getID();
	}
	
	private Jugador obtenerJugadorActual() {
		return jugadores.get(posicionJugadorActual);
	}
	
	private boolean validarNombreJugador(String nombreNuevoJugador) {
		if(nombreNuevoJugador == null || nombreNuevoJugador.isBlank()) {
			return false;
		}
		
		return jugadores
		        .stream()//la lista de jugadores se cvuelve un flujo
		        .map(jugador -> jugador.getNombre().trim().toLowerCase())
		        .noneMatch(nombre -> nombre.equals(nombreNuevoJugador));// si machea devuelve FALSE
	}
	
	//Controla la cantidad de jugadores, si es la correcta arranca el juego
	//este metodo lo uso cuando el jugador admin esta esperando a jugadores
	@Override
	public Jugador agregarNuevoJugador(IObservadorRemoto observadorRemoto, String nombreNuevoJugador) throws RemoteException {
			if(this.jugadores.size() == 0) {
				if (!this.validarNombreJugador(nombreNuevoJugador)) {
					observadorRemoto.actualizar(null, EventoGeneral.ERROR_VALIDACION_NOMBRE_JUGADOR);
					return null;
			}
			Jugador jugadorAdmin = new Jugador(nombreNuevoJugador);
			jugadorAdmin.setEsAdmin(true);//añado a jugador que es admin
			jugadores.add(jugadorAdmin);
			this.estadoJuego = EstadoJuego.ESPERA_DE_JUGADORES;
			observadorRemoto.actualizar(null, EventoGeneral.JUGADOR_ADMIN_AGREGADO);
			
			return jugadorAdmin;
		}
		
		if (this.jugadores.size() == Modelo.MAXIMO_JUGADORES) {
			observadorRemoto.actualizar(null, EventoGeneral.ERROR_LIMITE_MAXIMO_JUGADORES);
			return null;
		}
		
		if (!this.validarNombreJugador(nombreNuevoJugador)) {
			observadorRemoto.actualizar(null, EventoGeneral.ERROR_VALIDACION_NOMBRE_JUGADOR);
			return null;
		}
		
		Jugador jugador = new Jugador(nombreNuevoJugador);
		jugadores.add(jugador);

		//si llego aca es porque el jugador que se agrego no es admin
		observadorRemoto.actualizar(null, EventoGeneral.MOSTRAR_PANTALLA_MENU_PRINCIPAL);
		return jugador;
	}

	@Override
	public void definirCantidadJugadoresMaximo(IObservadorRemoto observadorRemoto, int cantidadJugadores) throws RemoteException {
		if(cantidadJugadores < MINIMO_JUGADORES) {
			observadorRemoto.actualizar(null, EventoGeneral.ERROR_LIMITE_MINIMO_JUGADORES);
			return;
		}
		
		if(cantidadJugadores > MAXIMO_JUGADORES){
			observadorRemoto.actualizar(null, EventoGeneral.ERROR_LIMITE_MAXIMO_JUGADORES);
			return;
		}
		
		this.cantidadJugadoresActuales = cantidadJugadores;
		this.estadoJuego = EstadoJuego.ESPERA_DE_JUGADORES;
		observadorRemoto.actualizar(null,EventoGeneral.MOSTRAR_PANTALLA_MENU_PRINCIPAL);
	}
				
	@Override
	public void iniciarPartida(IObservadorRemoto observadorRemoto) throws RemoteException {
		if((jugadoresEnEspera + 1) < cantidadJugadoresActuales) {
			jugadoresEnEspera ++;
			observadorRemoto.actualizar(null, EventoGeneral.MOSTRAR_PANTALLA_ESPERA_JUGADORES);
			return;
		}
		jugadoresEnEspera = 0;
		
		this.estadoJuego = EstadoJuego.EN_CURSO;
		notificarObservadores(EventoGeneral.MOSTRAR_PANTALLA_NOMBRES_JUGADORES_CARGADOS);
	}
	
	private void repartirCartas() {
		while(!this.mazo.esVacio()) {
			for (Jugador jugador :jugadores) {
				if(this.mazo.esVacio()) {
					break;
				}
				jugador.agregarCarta(mazo.getCartaTope());
			}
		}
	}
		
	private void descarteinicialJugadores() {
		for(Jugador jugador : jugadores) {
			mazoParejas.addAll(jugador.descartar());
		}
	}
	
	@Override
	public void iniciarRonda(IObservadorRemoto observadorRemoto) throws RemoteException {
		if((jugadoresEnEspera+1) < cantidadJugadoresActuales) {
			jugadoresEnEspera++;
			observadorRemoto.actualizar(null, EventoGeneral.MOSTRAR_PANTALLA_ESPERA_JUGADORES);
			return;
		}
		setearActivosJugadores();
		eliminarCartasJugadores();
		jugadoresEnEspera = 0;
		
		mazo = new Mazo();
		mazoParejas = new Stack<>();
		repartirCartas();
		descarteinicialJugadores();
		
		notificarObservadores(EventoGeneral.MOSTRAR_PANTALLA_RONDA_JUGADORES);
	}
	private void setearActivosJugadores() {
		for(Jugador jugador : jugadores) {
			jugador.setEsActivo(true);
		} 
	}
	private void eliminarCartasJugadores() {
		for(Jugador jugador : jugadores) {
			jugador.descartarMano();
		} 
	}
	
	@Override
	public List<String> obtenerNombresJugadores() throws RemoteException {
		return jugadores
		        .stream()//la lista de jugadores se cvuelve un flujo
		        .map(jugador -> jugador.getNombre())
				.toList();
	}

	@Override
	public String obtenerNombreJugadorActual() throws RemoteException {
		return obtenerJugadorActual().getNombre();
	}

	@Override
	public List<Carta> obtenerUltimasDosCartasMazoParejas() throws RemoteException {
		if (mazoParejas.size() >= 2) {
			Carta ultimaCarta = mazoParejas.getLast();
			Carta penultimaCarta = mazoParejas.get(mazoParejas.size()-2);
			return List.of(ultimaCarta,penultimaCarta);
		}
		return List.of();
	}
	
	private Jugador obtenerJugador(UUID id) {
		return jugadores.stream()
				.filter(jugador -> jugador.getID().equals(id))
				.findFirst()
				.orElseThrow(() ->
					new NoSuchElementException("No existe jugador con id " + id)
				);			
	}
	
	@Override
	public List<Carta> obtenerMazoJugador(UUID id) throws RemoteException {
		return obtenerJugador(id).getMazo();
	}
	
	//Cantidad
	private Integer posicionJugadorDerecha() {
		Integer posicionJugadorDerecha = ((posicionJugadorActual - 1) != -1)? (posicionJugadorActual - 1): (cantidadJugadoresActuales - 1);
		
		while(!jugadores.get(posicionJugadorDerecha).getEsActivo()) {
			posicionJugadorDerecha = ((posicionJugadorDerecha - 1) != -1)? (posicionJugadorActual - 1): (cantidadJugadoresActuales - 1);
		}
		
		return posicionJugadorDerecha;
	}
	
	//OBTENER JUGADOR DERECHA
	private Jugador obtenerJugadorDerecha() {
		return jugadores.get(posicionJugadorDerecha());
	}
	
	@Override
	public int ObtenerCantidadCartasJugadorDerecha() throws RemoteException {
		return obtenerJugadorDerecha().getMazo().size();
	}
	
	//QUITAR CARTA A JUGADOR DERECHA 
	private int siguientePosicionJugadorActivo(int posicionActual) {
		int siguientePosicion = (posicionActual + 1) % cantidadJugadoresActuales;
		
		while (!jugadores.get(siguientePosicion).getEsActivo()) {
			siguientePosicion = ((siguientePosicion + 1) % cantidadJugadoresActuales);
		} 
		
		return siguientePosicion;
	}
	
	private Boolean hayMasDeUnJugadorActivo() {
		long activos = jugadores.stream()
				.filter(Jugador::getEsActivo)
				.count();
		return activos > 1;
	}
	
	
	@Override
	public void tomarCartaJugadorDerecha(IObservadorRemoto observadorRemoto, int indiceCartaJugadorDerecha) throws RemoteException {
		//System.out.println("TOMAR CARTA JUGADOR DERECHA INICIO TURNO DE:" +obtenerJugadorActual().getNombre());
		//System.out.println("A1)obtenerJugadorDerecha NOMBRE:"+ obtenerJugadorDerecha().getNombre());
		//System.out.println("A2)obtenerJugadorDerecha isEmpty:"+ obtenerJugadorDerecha().getMazo().isEmpty());
		Jugador jugadorDerecha = obtenerJugadorDerecha();
		Carta cartaAQuitar = jugadorDerecha.getMazo().get(indiceCartaJugadorDerecha-1);
		jugadorDerecha.quitarCarta(cartaAQuitar);//jugador de la derecha se deshace de la carta
		//siguientePosicionJugadorActivo
		//System.out.println("B1)obtenerJugadorDerecha NOMBRE:"+ obtenerJugadorDerecha().getNombre());
		//System.out.println("B2)obtenerJugadorDerecha isEmpty:"+ obtenerJugadorDerecha().getMazo().isEmpty()+"MAZO"+obtenerJugadorDerecha().MazoToString());
		//*****************************************************************************************************************************
		//System.out.println("PREGUNTO SI DEBO SETEAR JUGADOR DE LA DERECHA A FALSE");
		if(jugadorDerecha.getMazo().isEmpty() == true) {
			jugadorDerecha.setEsActivo(false);//Jugador DERECHA se queda sin cartas y sale
			//System.out.println("SETEO JUGADOR DE LA DERECHA A FALSE");
			////System.out.println("JUGADOR DERECHA SETEO EN FALSE A:"+obtenerJugadorDerecha().getNombre()+" " +obtenerJugadorDerecha().getEsActivo());
		}
		//System.out.println("YA PREGUNTE");
		obtenerJugadorActual().agregarCarta(cartaAQuitar);//jugador actual se queda con la carta
		mazoParejas.addAll(obtenerJugadorActual().descartar());// descartar puede retornar null o las 2 cartas descartadas
		
		//System.out.println("obtenerJugadorActual().getMazo().isEmpty() " + obtenerJugadorActual().getNombre() + " " + obtenerJugadorActual().MazoToString());
		if(obtenerJugadorActual().getMazo().isEmpty()== true) { //GANA JUGADOR ACTUAL
			obtenerJugadorActual().setEsActivo(false);
			//System.out.println("JUGADOR ACTUAL SETEO EN FALSE A:"+obtenerJugadorActual().getNombre()+" " +obtenerJugadorActual().getEsActivo());
		}
		
		if(hayMasDeUnJugadorActivo()) {//busco si quedan mas jugadores en juego para no terminar la ronda
			//System.out.println("HAY MAS DE UN JUGADOR ACTIVO |||||posicionJugadorActual"+posicionJugadorActual);
			this.posicionJugadorActual = siguientePosicionJugadorActivo(posicionJugadorActual);
			//System.out.println("TRAS siguientePosicionJugadorActivo||||| posicionJugadorActual"+posicionJugadorActual);
			notificarObservadores(EventoGeneral.CONTINUAR_SIGUIENTE_TURNO_RONDA);
			return;
		}	
		
		this.estadoJuego = EstadoJuego.ESPERA_DE_JUGADORES;
		notificarObservadores(EventoGeneral.FINAL_RONDA); //SI LLEGO ACA ES PORQUE Termino la ronda
	}
	//FINAL DE RONDA
	
	//PERDEDOR
	@Override
	public String obtenerNombreJugadorPerdedor() throws RemoteException {
		Jugador ultimoJugadorActivo = jugadores.stream()
				.filter(Jugador::getEsActivo)
				.findFirst()
				.orElseThrow(() ->
				new NoSuchElementException("No hay jugadores activos"));
		
		return ultimoJugadorActivo.getNombre();
	}
	
	private void resetearJuego() throws RemoteException {
		//this.cantidadJugadoresActuales = -1;
		//this.jugadores.clear();
		this.mazoParejas.clear();
		this.posicionJugadorActual = 0;
		this.jugadoresEnEspera = 0;
		if(hayPartidaPersistida()) {
			this.administradorSerializacion.eliminarPartida();
		}				
	}
	
	@Override
	public void finalizarJuego(IObservadorRemoto observadorRemoto) throws RemoteException {
		if ((jugadoresEnEspera ++) < cantidadJugadoresActuales) {//REVANCHA
			jugadoresEnEspera ++;
			observadorRemoto.actualizar(null, EventoGeneral.MOSTRAR_PANTALLA_ESPERA_JUGADORES);
			return;
		}
		jugadoresEnEspera = 0;//FIN DEL JUEGO IR A MENU PRINCIPAL
		resetearJuego();
		notificarObservadores(EventoGeneral.MOSTRAR_PANTALLA_MENU_PRINCIPAL);
	}
	
	//SERIALIZACION
	
	@Override
	public boolean hayPartidaPersistida() throws RemoteException {
		return this.administradorSerializacion.hayPartidaGuardada();
	}

	@Override
	public void persistirPartida() throws RemoteException {
		if(this.administradorSerializacion.persistirPartida(this)) {
			notificarObservadores(EventoGeneral.PARTIDA_PERSISTIDA);
		}else {
			notificarObservadores(EventoGeneral.ERROR_PARTIDA_PERSISTIDA);
		}
	}
	
	private void recuperarPartida() throws RemoteException {
		IModelo partidaRecuperada = this.administradorSerializacion.recuperarPartida();
	
		if (partidaRecuperada == null) {
			notificarObservadores(EventoGeneral.ERROR_PARTIDA_RECUPERADA);
			return;
		}
		this.jugadores = partidaRecuperada.obtenerJugadores();
		this.jugadoresAReasignar =this.jugadores;
		this.cantidadJugadoresActuales = this.jugadores.size();
		this.posicionJugadorActual = partidaRecuperada.obtenerPosicionJugadorActual();
		this.jugadoresEnEspera = 0;
		mazoParejas = partidaRecuperada.obtenerMazoParejas();
	}
	
	@Override
	public void continuarPartidaPersistida(IObservadorRemoto observadorRemoto) throws RemoteException {
		if (!isPartidaRecuperada) {
			recuperarPartida();
		}
		isPartidaRecuperada = true;
		
		if((jugadoresEnEspera++)< cantidadJugadoresActuales) {
			jugadoresEnEspera++;
			observadorRemoto.actualizar(null, EventoGeneral.MOSTRAR_PANTALLA_ESPERA_JUGADORES);
			return;
		}
		jugadoresEnEspera = 0;
		notificarObservadores(EventoGeneral.MOSTRAR_PANTALLA_RONDA_JUGADORES);

	}

	@Override
	public void reasignarJugadoresPartidaPersistida(IObservadorRemoto observadorRemoto,UUID id) throws RemoteException {
		Jugador jugadorAEliminar = jugadores.stream()
				.filter(jugador -> jugador.getID().equals(id))
				.findFirst()
				.orElse(null);
		
		jugadoresAReasignar.remove(jugadorAEliminar);
		
		if (!jugadoresAReasignar.isEmpty()) {
			notificarObservadores(EventoGeneral.MOSTRAR_PANTALLA_NOMBRES_JUGADORES_PARTIDA_RECUPERADA);
			observadorRemoto.actualizar(null, EventoGeneral.MOSTRAR_PANTALLA_CARGA_NOMBRE_JUGADOR);
		}
		
		notificarObservadores(EventoGeneral.MOSTRAR_PANTALLA_RONDA_JUGADORES);
	}

	@Override
	public List<Jugador> obtenerJugadoresPartidaPersistida() throws RemoteException {
		return this.jugadoresAReasignar;
	}




	
}
