package PiezasDeLoteria;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Un conjunto de 16 cartas aleatorias
 *
 * @author Ulises Tovar
 */
public class Tarjeta implements Serializable {

    private final ArrayList<Carta> cartas;
    //private int[] posicionesMarcadas;
    private final ArrayList<Integer> posicionesMarcadas;
    public static final int CANTIDAD_DE_CARTAS = 16;

    public Tarjeta() {
        cartas = new ArrayList<>();
        posicionesMarcadas = new ArrayList<>();
        asignarCartas();
    }

    /**
     * Selecciona aleatoriamente 16 de entre las 54 cartas disponibles
     */
    private void asignarCartas() {

        //El mazo desde el que se irán tomando cartas
        Mazo mazo = new Mazo();
        mazo.barajar();

        Carta cartaAleatoria;

        for (int i = 0; i < CANTIDAD_DE_CARTAS; i++) {
            //La clase es tomada y luego eliminada del mazo
            cartaAleatoria = mazo.lanzarCarta();

            cartas.add(cartaAleatoria);
        }
    }

    /**
     * Marca la posición de una carta.
     *
     * @param carta la carta a marcar
     * @return un booleano que indica si se pudo marcar la carta
     */
    public boolean marcarCarta(Carta carta) {
        int posicion = cartas.indexOf(carta);
        if (posicion == -1) {
            return false;
        } else {
            return marcarCarta(posicion);
        }
    }

    public boolean marcarCarta(int posicion) {
        for (Integer posicionMarcada : posicionesMarcadas) {
            if (posicionMarcada == posicion) {
                return false;
            }
        }
        return posicionesMarcadas.add(posicion);
    }

    public Carta getCarta(int posicion) {
        return cartas.get(posicion);
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

    public ArrayList<Integer> getPosicionesMarcadas() {
        return posicionesMarcadas;
    }

    public int getCantidadDeCartasMarcadas() {
        return posicionesMarcadas.size();
    }

    public boolean desmarcarCarta(Carta carta) {
        int posicion = cartas.indexOf(carta);
        return desmarcarCarta(posicion);
    }

    public boolean desmarcarCarta(int posicion) {
        for (Integer posicionMarcada : posicionesMarcadas) {
            if (posicionMarcada == posicion) {
                posicionesMarcadas.remove(posicionMarcada);
                return true;
            }
        }
        return false;
    }
}
