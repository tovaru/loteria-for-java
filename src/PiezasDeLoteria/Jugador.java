package PiezasDeLoteria;

import java.util.ArrayList;
import loteria.Movimiento;
import loteria.MovimientoEvent;
import loteria.MovimientoListener;
import piezazgraficas.Marcadores;

/**
 *
 * @author Ulises Tovar
 */
public class Jugador implements MovimientoEvent {

    private String nombre;
    private Tarjeta tarjeta = new Tarjeta();
    private boolean anunciandoVictoria;
    private double dinero;
    private Marcadores marcadorParaTarjeta = Marcadores.Frijolito;
    private int victorias;
    private boolean penalizado;

    private final ArrayList<MovimientoListener> listeners = new ArrayList<>();

    public Jugador() {
        nombre = "Mexicano";
    }

    public Jugador(String nombre) {
        this.nombre = nombre;
    }

    public void setAnunciandoVictoria(boolean condicion) {
        anunciandoVictoria = condicion;
        //Sólo enviar este movimiento si es "true"
        if (condicion) {
            Movimiento m = new Movimiento(nombre, Movimiento.LOTERIA, 0);
            lanzarMovimiento(m);
        }
    }

    public boolean isAnunciandoVictoria() {
        return anunciandoVictoria;
    }

    public String getNombre() {
        return nombre;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setDinero(double dinero) {
        this.dinero = dinero;
    }

    public double getDinero() {
        return dinero;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public void setMarcadorParaTarjeta(Marcadores marcadorParaTarjeta) {
        this.marcadorParaTarjeta = marcadorParaTarjeta;
    }

    public Marcadores getMarcadorParaTarjeta() {
        return marcadorParaTarjeta;
    }

    public void sumarVictoria() {
        victorias++;
    }

    public int getVictorias() {
        return victorias;
    }

    /**
     * @return the penalizado
     */
    public boolean isPenalizado() {
        return penalizado;
    }

    public boolean marcarCarta(int posicion) {
        boolean add = tarjeta.marcarCarta(posicion);
        if (add) {
            //Crear este movimiento y avisar a los oyentes
            Movimiento m = new Movimiento(nombre, Movimiento.MARCAR_CARTA, posicion);
            lanzarMovimiento(m);
        }
        return add;
    }

    public boolean desmarcarCarta(int posicion) {
        boolean add = tarjeta.desmarcarCarta(posicion);
        if (add) {
            //Crear este movimiento y avisar a los oyentes
            Movimiento m = new Movimiento(nombre, Movimiento.DESMARCAR_CARTA, posicion);
            lanzarMovimiento(m);
        }
        return add;
    }

    /**
     * @param penalizado the penalizado to set
     */
    public void setPenalizado(boolean penalizado) {
        this.penalizado = penalizado;
    }

    @Override
    public boolean addMovimientoListener(MovimientoListener listener) {
        return listeners.add(listener);
    }

    @Override
    public boolean removeMovimientoListener(MovimientoListener listener) {
        return listeners.add(listener);
    }

    @Override
    public void lanzarMovimiento(Movimiento m) {
        listeners.forEach((listener) -> {
            listener.movimientoRealizado(m);
        });
    }

    @Override
    public void removelAllListeners() {
        listeners.clear();
    }

    
}
