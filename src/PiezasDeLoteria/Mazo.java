package PiezasDeLoteria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Ulises Tovar
 */
public class Mazo {

    private final ArrayList<Carta> cartas;
    private long semilla;

    public Mazo() {
        cartas = new ArrayList<>();
        semilla = 0;
        crearCartas();
    }

    private void crearCartas() {
        for (Nombres n : Nombres.values()) {
            Carta cartaNueva = new Carta(n);
            cartas.add(cartaNueva);
        }
    }

    public void barajar() {
        if (semilla != 0) {
            Collections.shuffle(cartas, new Random(semilla));
        } else {
            Collections.shuffle(cartas);
        }
    }

    public int calcularCartasRestantes() {
        return cartas.size();
    }

    public Carta lanzarCarta() {
        return cartas.remove(cartas.size() - 1);
    }

    @Override
    public String toString() {
        StringBuilder lista = new StringBuilder();

        int contador = 1;
        for (Carta carta : cartas) {
            lista.append(contador++).append(". ");
            lista.append(carta);
            lista.append("\n\n");
        }

        return lista.toString();
    }

    public void reiniciar() {
        cartas.clear();
        crearCartas();
    }

    public boolean isEmpty() {
        return cartas.isEmpty();
    }

    public void setSemilla(long semilla) {
        this.semilla = semilla;
    }
}
