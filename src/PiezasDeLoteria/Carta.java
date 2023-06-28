package PiezasDeLoteria;

import java.io.Serializable;

/**
 *
 * @author Ulises Tovar
 */
public class Carta implements Serializable {
    
    /**
     * Un atributo de tipo enum que funciona como identificador.
     */
    private final Nombres id;
    
    /**
     * El nombre de la carta
     */
    private final String nombre;
    
    /**
     * La descripción de la carta.
     */
    private final String descripcion;
    
    /**
     * El constructor de la carta.
     * @param personaje objeto tipo enum que indica el personaje que se
     * creará en la carta.
     */
    public Carta(Nombres personaje) {
        String[] atributos = ListaDeCartas.crearPersonaje(personaje);
        id = personaje;
        nombre = atributos[0];
        descripcion = atributos[1];
    }

    /**
     *
     * @return un String con el nombre del personaje de la carta.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * 
     * @return un String con la descripción del personaje de la carta.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * 
     * @return el enum que identifica el personaje de la carta.
     */
    public Nombres getId() {
        return id;
    }
    
    @Override
    public String toString(){
        return nombre + ":\n" + descripcion;
    }
}
