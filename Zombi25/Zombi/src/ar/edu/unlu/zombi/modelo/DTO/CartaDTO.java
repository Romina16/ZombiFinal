package ar.edu.unlu.zombi.modelo.DTO;

import java.io.Serializable;
import java.util.UUID;

import ar.edu.unlu.zombi.modelo.enumerados.TipoCarta;

//para usar la comunicación de salida del modelo a la vista
//D de solid, inyección de dependencias
public class CartaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private UUID id;
    private TipoCarta tipoCarta;
    private Integer numero;

    public CartaDTO(UUID id,TipoCarta tipo, Integer numero){
        this.id = id;
        this.tipoCarta = tipo;
        this.numero = numero;
    }

    public UUID getId() { return id; }

    public TipoCarta getTipoCarta() { return tipoCarta;}

    public Integer getNumero() { return numero; }

    public String NombreImagenDeLaCarta(){
        return (this.tipoCarta ==TipoCarta.COMODIN) ? ("Zombi") : (this.numero.toString() + this.tipoCarta.toString());
    }
    @Override
    public String toString(){
        if (tipoCarta != TipoCarta.COMODIN){
            return " " + tipoCarta+ " " + numero;
        }else{
            return tipoCarta.toString();
        }
    }
}
