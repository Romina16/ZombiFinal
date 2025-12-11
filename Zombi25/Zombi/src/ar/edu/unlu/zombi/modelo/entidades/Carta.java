package ar.edu.unlu.zombi.modelo.entidades;

import java.io.Serializable;
import java.util.UUID;

import ar.edu.unlu.zombi.modelo.enumerados.TipoCarta;

public class Carta implements Serializable{
	private static final long serialVersionUID = 1L;
	private UUID id;
    private TipoCarta tipo;
    private Integer numero;

    public Carta(TipoCarta tipoCarta, Integer numero) {
        this.tipo = tipoCarta;
        this.numero = numero;
    }

    public TipoCarta getTipoCarta() {
        return tipo;
    }
    
    public UUID getId() {
		return this.id;
	}

	public TipoCarta getTipo() {
		return tipo;
	}
    
    public Integer getNumero() {
        return numero;
    }

    public String valorCarta() {
        return(this.tipo == null) ? ("Comodin") : ("Numero "+ this.numero.toString() + " Tipo: " + this.tipo.toString());
    }

    public boolean esComodin() {
        return (this.tipo == TipoCarta.COMODIN);
    }

    public boolean esPareja(Carta otraCarta) {
        return (this.numero == otraCarta.numero);
    }

}
