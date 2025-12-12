package ar.edu.unlu.zombi.interfaces;

import java.util.List;
import java.util.Stack;
import java.util.UUID;
import java.rmi.RemoteException;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import ar.edu.unlu.rmimvc.observer.IObservadorRemoto;
import ar.edu.unlu.zombi.modelo.entidades.*;

public interface IModelo extends IObservableRemoto {
	
	boolean sePuedenIngresarJugadores() throws RemoteException;
	List<Jugador> obtenerJugadores() throws RemoteException;
	int obtenerPosicionJugadorActual() throws RemoteException;
	Stack<Carta> obtenerMazoParejas() throws RemoteException;
	int obtenerCantidadMinimaJugadores() throws RemoteException;
	int obtenerCantidadMaximaJugadores() throws RemoteException;
	boolean esCantidadJugadoresDefinida() throws RemoteException;
	UUID obtenerJugadorActualId() throws RemoteException;
	Jugador agregarNuevoJugador(IObservadorRemoto observadorRemoto, String nombreNuevoJugador) throws RemoteException;
	void definirCantidadJugadoresMaximo(IObservadorRemoto observadorRemoto, int cantidadJugadores) throws RemoteException;
	void iniciarPartida(IObservadorRemoto observadorRemoto) throws RemoteException;
	void iniciarRonda(IObservadorRemoto observadorRemoto) throws RemoteException;
	List<String> obtenerNombresJugadores() throws RemoteException;
	String obtenerNombreJugadorActual() throws RemoteException;
	List<Carta> obtenerUltimasDosCartasMazoParejas() throws RemoteException;
	List<Carta> obtenerMazoJugador(UUID id) throws RemoteException;
	int ObtenerCantidadCartasJugadorDerecha() throws RemoteException;
	void tomarCartaJugadorDerecha(IObservadorRemoto observadorRemoto, int indiceCartaJugadorDerecha) throws RemoteException;
	String obtenerNombreJugadorPerdedor() throws RemoteException;
	void finalizarJuego(IObservadorRemoto observadorRemoto) throws RemoteException;
	boolean hayPartidaPersistida() throws RemoteException;
	void persistirPartida() throws RemoteException;
	void continuarPartidaPersistida(IObservadorRemoto observadorRemoto) throws RemoteException;
	List<Jugador> obtenerJugadoresPartidaPersistida() throws RemoteException;
	Jugador reasignarJugadoresPartidaPersistida(IObservadorRemoto observadorRemoto, UUID id) throws RemoteException;
	
}
