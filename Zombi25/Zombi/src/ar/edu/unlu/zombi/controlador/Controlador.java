package ar.edu.unlu.zombi.controlador;

import java.rmi.RemoteException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import ar.edu.unlu.zombi.interfaces.IControlador;
import ar.edu.unlu.zombi.interfaces.IModelo;
import ar.edu.unlu.zombi.interfaces.IVista;
import ar.edu.unlu.zombi.modelo.DTO.CartaDTO;
import ar.edu.unlu.zombi.modelo.DTO.JugadorDTO;
import ar.edu.unlu.zombi.modelo.entidades.Carta;
import ar.edu.unlu.zombi.modelo.entidades.Jugador;
import ar.edu.unlu.zombi.modelo.enumerados.EventoGeneral;

public class Controlador implements IControlador, IControladorRemoto{
	private IModelo modelo;
	private IVista vista;
	private Jugador jugadorAsignado;
	
	public Controlador() {}
	
	@Override
	public void actualizar(IObservableRemoto arg0, Object objeto) throws RemoteException {
		EventoGeneral evento = (EventoGeneral) objeto;
		
		switch(evento) {
		case  MOSTRAR_PANTALLA_NOMBRES_JUGADORES_CARGADOS:
			mostrarPanelNombresJugadoresCargados();
			break;
		case MOSTRAR_PANTALLA_RONDA_JUGADORES:
			mostrarPanelRondaJugador();
			break;
		case CONTINUAR_SIGUIENTE_TURNO_RONDA:
			mostrarPanelRondaJugador();
			break;
		case FINAL_RONDA:
			mostrarPanelFinalRonda();
			break;
		case MOSTRAR_PANTALLA_MENU_PRINCIPAL:
			mostrarPanelMenuPrincipal();
			break;
		case PARTIDA_PERSISTIDA:
			mostrarPanelPartidaPersistida();
			break;
		case MOSTRAR_PANTALLA_NOMBRES_JUGADORES_PARTIDA_RECUPERADA:
			//if(this.jugadorAsignado == null) {
				mostrarPanelNombresJugadoresPartidaPersistida();
			//}
			
			break;
			///
		case ERROR_LIMITE_MINIMO_JUGADORES:
			vista.mostrarMensajeError("La cantidad de jugadores debe ser mayor a " + obtenerCantidadMinimaJugadores());
			break;
		case ERROR_LIMITE_MAXIMO_JUGADORES:
			vista.mostrarMensajeError("La cantidad de jugadores debe ser menor a " + obtenerCantidadMaximaJugadores());
			break;
		case MOSTRAR_PANTALLA_CARGA_NOMBRE_JUGADOR:
			//vista.mostrarPanelCargaNombreJugador();
			mostrarPanelCargaNombreJugador();
			break;
		case ERROR_VALIDACION_NOMBRE_JUGADOR:
			vista.mostrarMensajeError("El nombre ingresado no es valido");
			break;
		case MOSTRAR_PANTALLA_ESPERA_JUGADORES:
			vista.mostrarPanelEsperaJugadores();
			break;
		case JUGADOR_ADMIN_AGREGADO:
			vista.mostrarPanelDefinirCantidadJugadores();;//revisar
		default:
			break;
		}
	}

	@Override
	public <T extends IObservableRemoto> void setModeloRemoto(T modeloRemoto) throws RemoteException {
		this.modelo = (IModelo) modeloRemoto;
	}

	@Override
	public void setVista(IVista vista) {
		this.vista = vista;	
	}
	
	public void setJugadorAsignado(Jugador jugador) {
		this.jugadorAsignado = jugador;
	}

	@Override
	public int obtenerCantidadMinimaJugadores() {
		try {
			
			return modelo.obtenerCantidadMinimaJugadores();
			
		}catch(RemoteException e) {
			vista.mostrarMensajeError("Error: Remote Exception "+e.getMessage());
			return -1;
		}		
	}

	@Override
	public int obtenerCantidadMaximaJugadores() {
		try {
			
			return modelo.obtenerCantidadMaximaJugadores();
			
		}catch(RemoteException e) {
			vista.mostrarMensajeError("Error: Remote Exception "+e.getMessage());
			return -1;
		}
		
	}
	
	@Override
	public void mostrarPanelCargaNombreJugador() {
		vista.mostrarPanelCargaNombreJugador();
	}
	
	@Override
	public void obtenerDatosCargaNombreJugador(String nombreJugador) {
		try {
			if (nombreJugador == null || nombreJugador.isBlank() ) {
				vista.mostrarMensajeError("El nombre no puede estar vacio");
				return;
			}		
			/*if(modelo.sePuedeAgregarJugadorAdmin()) {
				Jugador jugadorAdmin= modelo.agregarJugadorAdmin(this, nombreJugador);
				setJugadorAsignado(jugadorAdmin);
			//}*/
			
			//if(modelo.sePuedenIngresarJugadores()) {
				Jugador jugador = modelo.agregarNuevoJugador(this, nombreJugador.trim().toLowerCase());
				setJugadorAsignado(jugador);
			//}
					//System.out.println("jugador asignado: "+jugadorAsignado.getNombre());
		}catch(RemoteException e) {
			vista.mostrarMensajeError("Error Remote Exception "+e.getMessage());
		}
	}
	
	@Override
	public void mostrarPanelDefinirCantidadJugadores() {
		vista.mostrarPanelDefinirCantidadJugadores();
	}

	@Override
	public void obtenerDatosCargaCantidadJugadores(String cantidadJugadores) {
		//System.out.println("carga cantidad de jugador CONTROLADOR");
		try {
			////System.out.println("carga cantidad de jugador CONTROLADOR EN EL try");
			if (cantidadJugadores == null || cantidadJugadores.isBlank()) {
				vista.mostrarMensajeError("La cantidad de jugadores no puede ser vacia");
				return; 
			}
			
			int cantidadJugadoresInteger = Integer.parseInt(cantidadJugadores.trim());
			
			 modelo.definirCantidadJugadoresMaximo(this, cantidadJugadoresInteger);
		
		}catch(NumberFormatException e) {
			vista.mostrarMensajeError("Numero ingresado invalida");
		}catch(RemoteException e) {
			vista.mostrarMensajeError("Error Remote Exception "+e.getMessage());
		}
		
	}
	
	@Override
	public void mostrarPanelMenuPrincipal() {
		vista.mostrarPanelMenuPrincipal();
	}
		
	@Override
	public void mostrarPanelEsperaJugadores() {
		vista.mostrarPanelEsperaJugadores();
	}
		
	@Override
	public void iniciarPartida() {
		try {
			
			if(!modelo.sePuedenIngresarJugadores()) {
				vista.mostrarMensajeError("No se puede unir a la partida, ya hay una en curso");
				return;
			}
			
			modelo.iniciarPartida(this);
			
		} catch(RemoteException e) {
			vista.mostrarMensajeError("Error: Remote Exception "+e.getMessage());
		}
		
	}
	
	@Override
	public void mostrarPanelNombresJugadoresCargados() {
		vista.mostrarPanelNombresJugadoresCargados();
	}

	@Override
	public List<String> obtenerNombresJugadores() {
		try {
			
			return modelo.obtenerNombresJugadores();
			
		} catch(RemoteException e) {
			vista.mostrarMensajeError("Error: Remote Exception " + e.getMessage());
			return List.of();
		}
	}

	@Override
	public void iniciarRonda() {
		try {		
			
			modelo.iniciarRonda(this);
			
		} catch(RemoteException e) {
			vista.mostrarMensajeError("Error: Remote Exception " + e.getMessage());
		}
	}

	@Override
	public void mostrarPanelRondaJugador() {
		try {
			
			if(modelo.obtenerJugadorActualId().equals(jugadorAsignado.getID())) {
				vista.mostrarPanelRondaJugadorTurno();
				return;
			}
			
			vista.mostrarPanelRondaJugadorObservador();
			
		} catch(RemoteException e) {
			vista.mostrarMensajeError("Error: Remote Exception " + e.getMessage());
		}
	}

	@Override
	public String obtenerNombreJugadorActual() {
		try {
			
			return modelo.obtenerNombreJugadorActual();
			
		} catch(RemoteException e) {
			vista.mostrarMensajeError("Error: Remote Exception " + e.getMessage());
			return "";
		}
	}

	@Override
	public List<CartaDTO> obtenerUltimasDosCartasMazoParejas() {
		try {
			
			return modelo.obtenerUltimasDosCartasMazoParejas()
					.stream()
					.map(carta -> new CartaDTO(
							carta.getId(), 
							carta.getTipo(), 
							carta.getNumero()))
					.toList();
			
		} catch(RemoteException e) {
			vista.mostrarMensajeError("Error: Remote Exception " + e.getMessage());
			return List.of();
		}
	}
	
	@Override
	public List<CartaDTO> obtenerMazoJugador() {
		try {
			
			List<Carta> cartasJugador =  modelo.obtenerMazoJugador(jugadorAsignado.getID());
			
			return cartasJugador
					.stream()
					.map(carta -> new CartaDTO(
							carta.getId(), 
							carta.getTipo(), 
							carta.getNumero()))
					.toList();
			
		} catch(NoSuchElementException e) {
			vista.mostrarMensajeError("Error: No se encontro el usuario solicitado");
			return List.of();
		} catch(RemoteException e) {
			vista.mostrarMensajeError("Error: Remote Exception " + e.getMessage());
			return List.of();
		}
	}

	@Override
	public int obtenerCantidadCartasJugadoresDerecha() {
		try {
			
			return modelo.ObtenerCantidadCartasJugadorDerecha();
			
		} catch(RemoteException e) {
			vista.mostrarMensajeError("Error: Remote Exception " + e.getMessage());
			return -1;
		}
	}
	
	@Override
	public void obtenerDatosCargaRondaJugadorTurno(String indiceCartaJugadorDerecha) {
		try {
			
			if(indiceCartaJugadorDerecha == null || indiceCartaJugadorDerecha.isBlank()) {
				vista.mostrarMensajeError("El indice de la carta elegida no puede ser vacia");
				return;
			}
			int indice = Integer.parseInt(indiceCartaJugadorDerecha.trim());
			modelo.tomarCartaJugadorDerecha(this, indice);	
			
		} catch (NumberFormatException e) {
			vista.mostrarMensajeError("Numero ingresado invalido");	
		} catch(RemoteException e) {
			vista.mostrarMensajeError("Error: Remote Exception " + e.getMessage());
		}
	}

	@Override
	public void mostrarPanelFinalRonda() {
		vista.mostrarPanelFinalRonda();
	}

	@Override
	public String obtenerNombreJugadorPerdedor() {
		try {
			
			return modelo.obtenerNombreJugadorPerdedor();
			
		}catch(RemoteException e) {
			vista.mostrarMensajeError("Error: Remote Exception " + e.getMessage());
		}
		return "";
	}

	@Override
	public void volverAlMenuPrincipal() {
		try {
			modelo.finalizarJuego(this);
		}catch(RemoteException e) {
			vista.mostrarMensajeError("Error: Remote Exception " + e.getMessage());
		}
	}
	
	@Override
	public void PersistirPartida() {
		try {
    		this.modelo.persistirPartida();
		} catch(RemoteException e) {
			vista.mostrarMensajeError("Error: Remote Exception " + e.getMessage());
		}
	}
	
	@Override
	public void continuarPartidaPersistida() {
		try {
			modelo.continuarPartidaPersistida(this);
		} catch(RemoteException e) {
			vista.mostrarMensajeError("Error: Remote Exception " + e.getMessage());
		}
		
	}

	@Override
	public boolean hayPartidaPersistida() {
		try {
    		return modelo.hayPartidaPersistida();
		} catch(RemoteException e) {
			vista.mostrarMensajeError("Error: Remote Exception " + e.getMessage());
			return false;
		}
	}

	@Override
	public List<JugadorDTO> obtenerJugadoresPartidaPersistida() {
		try {
    		return modelo.obtenerJugadores()
    				.stream()
    				.map(jugador -> new JugadorDTO(
    						jugador.getID(), 
    						jugador.getNombre()))
    				.toList();
    		
		} catch(RemoteException e) {
			vista.mostrarMensajeError("Error: Remote Exception " + e.getMessage());
			return List.of();
		}
	}

	@Override
	public void obtenerDatosCargaJugadorPartidaPersistida(UUID id) {
		try {
			
			if(id == null) {
				vista.mostrarMensajeError("El identificador no puede estar vacio");
				return;
			}			
			Jugador jugador = modelo.reasignarJugadoresPartidaPersistida(this, id);
			this.setJugadorAsignado(jugador);
		} catch(RemoteException e) {
			vista.mostrarMensajeError("Error: Remote Exception " + e.getMessage());
		}
	}


	@Override
	public void mostrarPanelNombresJugadoresPartidaPersistida() {
		System.out.println("controlador|vista.mostrarPanelNombresJugadoresPartidaPersistida()");
		vista.mostrarPanelNombresJugadoresPartidaPersistida();
	}


	@Override
	public void mostrarPanelPartidaPersistida() {
		vista.mostrarPanelPartidaPersistida();
	}

	

}
