
package conectividad;

import loteria.Movimiento;

/**
 *
 * @author Ulises Tovar
 */
public interface ClienteListener {
    public void iniciarRonda(long semilla);
    public void setJugando(boolean condicion);
    public void enviarMovimiento(Movimiento m);
    public void setSegundosEsperaGriton(int velocidad);
}
