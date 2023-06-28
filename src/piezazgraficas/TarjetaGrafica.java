package piezazgraficas;

import PiezasDeLoteria.Carta;
import PiezasDeLoteria.ListaDeCartas;
import PiezasDeLoteria.Tarjeta;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 *
 * @author Ulises Tovar
 */
public class TarjetaGrafica extends javax.swing.JPanel {

    private Tarjeta tarjeta;
    private final ArrayList<CartaGrafica> cartasGraficas;
    private static final int COLUMANAS = 4;
    private static final int PREF_W = 250;
    private static final int PREF_H = 320;

    public TarjetaGrafica() {
        cartasGraficas = new ArrayList<>();
        setPreferredSize(new Dimension(PREF_W, PREF_H));
        crearCartasGraficas();
    }

    public void actualizar() {

        if (tarjeta != null) {
            int numeroDeIcono = 0;
            for (CartaGrafica icono : cartasGraficas) {
                Carta c = tarjeta.getCarta(numeroDeIcono);

                icono.setImagenDir(ImagenesDeCartas.asignarImagen(c.getId()));
                icono.ajustar();

                numeroDeIcono++;
            }
        }
    }

    private void crearCartasGraficas() {
        setLayout(new GridLayout(4, COLUMANAS));

        //Crear y ajustar cada una de las cartas gráficas
        for (int i = 0; i < Tarjeta.CANTIDAD_DE_CARTAS; i++) {
            CartaGrafica icono = new CartaGrafica();
            icono.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
//            int ancho = PREF_W / COLUMANAS;
//            int altura = PREF_H / (Tarjeta.CANTIDAD_DE_CARTAS / COLUMANAS);
//            icono.setSize(ancho, altura);
            icono.ajustar();

            cartasGraficas.add(icono);
            this.add(icono);
        }
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public void marcarCarta(int posicion, boolean condicion) {
        if (posicion >= 0 || posicion <= Tarjeta.CANTIDAD_DE_CARTAS) {
            CartaGrafica cg = cartasGraficas.get(posicion);
            cg.setMarcada(condicion);
        }
    }

    public ArrayList<CartaGrafica> getCartasGraficas() {
        return cartasGraficas;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }
}
