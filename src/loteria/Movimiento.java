
package loteria;

import java.io.Serializable;

/**
 *
 * @author Ulises Tovar
 */
public class Movimiento implements Serializable {
    
    public static final String MARCAR_CARTA = "Marcar carta";
    public static final String DESMARCAR_CARTA = "Desmarcar carta";
    public static final String LOTERIA = "Loteria!";
    
    private final String nombre;
    private final String movimiento;
    private final int posicion;

    public Movimiento(String nombre, String movimiento, int posicion) {
        this.nombre = nombre;
        this.movimiento = movimiento;
        this.posicion = posicion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public int getPosicion() {
        return posicion;
    }
    
}
