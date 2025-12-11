package ar.edu.unlu.zombi.modelo.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class Jugador implements Serializable{
	private static final long serialVersionUID = 1L;
	private UUID id;
    private String nombre;
    private List<Carta> mano;
    private boolean esActivo;
    private boolean esAdmin;

    public Jugador(String nombre) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.mano = new ArrayList<Carta>();
        this.esActivo = true;
        this.esAdmin = false;
    }

    public String getNombre() { return this.nombre;}

    public List<Carta> getMazo() {return this.mano;}

    public boolean getEsActivo() {return this.esActivo;}
    
    public boolean getEsAdmin() {return this.esAdmin;}
    
    public UUID getID() {return this.id;}

    public void setEsActivo(boolean esActivo) {this.esActivo = esActivo;}
    
    public void setEsAdmin(boolean esAdmin) {this.esAdmin = esAdmin;}
    //agregar carta
    public void agregarCarta(Carta carta) {
        this.mano.add(carta);
    }
    //metodos sobrecargados
    //descartar carta de una posicion. Se usa cuando te la saca un jugador
    public Carta quitarCarta(int posicion) {
        return mano.remove(posicion);
    }
    //descartar carta.
    public void quitarCarta(Carta carta) {
        mano.remove(carta);
    }

    public boolean soloQuedaComodinEnMazo() {
        return (this.mano.size() == 1) && (this.mano.getFirst().esComodin());
    }

    //Descarte, decuelve lista de cartas parejas
    public List<Carta> descartar() {
        if (mano.isEmpty()) {
            return Collections.emptyList();
        }

        List<Carta> manoAuxiliar = new ArrayList<Carta>(mano);//copia de mazo
        manoAuxiliar.sort(Comparator.comparingInt(Carta::getNumero));//ordeno la lista menor a mayor

        List<Carta> parejas = new ArrayList<Carta>();//lista de cartas parejas a retornar

        int i = 0;
        while (i < manoAuxiliar.size() - 1) {
            Carta actual = manoAuxiliar.get(i);
            if (actual.esComodin()) {
                i++;
                continue;
            }
            Carta siguiente = manoAuxiliar.get(i + 1);
            //si no es Comodin y la carta siguiente tiene el mismo numero
            if (!siguiente.esComodin() && actual.getNumero().equals(siguiente.getNumero())) {
                parejas.add(actual);
                parejas.add(siguiente);//las sumo a la lista de parejas
                i += 2;// incremento i para que no tome la posicion de 'siguiente'
            } else {
                i++;//si es comodin o no tienen el mismo numero sigo
            }
        }
        //descarto todas las cartas parejas de mi mano
        mano.removeAll(parejas);
        //devuelvo las parejas que me descarte
        return parejas;
    }
    
    //recibir sus cartas al inicio de la partida
    public void recibirMano(List<Carta> cartas) {
    	if(!cartas.isEmpty()) {
    		this.mano.addAll(cartas);
    	}
    }
    
    public String MazoToString() {
    	StringBuilder sb = new StringBuilder();
        sb.append("--- Contenido del Mazo ---\n");
        
        for (int i = 0; i < mano.size(); i++) {
            // Usa el toString() de la clase Carta.
            sb.append(" [").append(i + 1).append("] ");
            sb.append(mano.get(i).valorCarta());
            		//.getNumero()+ " " + mano.get(i).getTipo() ); 
            sb.append("\n"); // Salto de línea para cada carta
        }

        sb.append("--------------------------\n");
        sb.append("Total de cartas: ").append(mano.size());
        
        return sb.toString();
    }
    
    public void descartarMano() {
    	this.mano.clear();
    }
}
