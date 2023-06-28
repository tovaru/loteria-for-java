package loteria;

import piezazgraficas.CartaGrafica;
import GUI.Tablero_Old;
import piezazgraficas.TarjetaGrafica_Old;
import PiezasDeLoteria.Carta;
import PiezasDeLoteria.Jugador;
import PiezasDeLoteria.Mazo;
import PiezasDeLoteria.Tarjeta;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 *
 * @author Ulises Tovar
 */
public class Juego_old {

    private Tablero_Old interfaz;

    private ArrayList<Jugador> jugadores;
    private Mazo baraja;
    private ArrayList<Carta> cartasLanzadas;

    private int rondas;
    private int subJugadorMostrandose;

    private static final int TIEMPO_ESPERA_GRITON = 3000;
    private Timer timer;

    Thread griton;

    public Juego_old(ArrayList<Jugador> jugadores) {
        interfaz = new Tablero_Old();
        this.jugadores = jugadores;
        baraja = new Mazo();
        cartasLanzadas = new ArrayList<>();
        rondas = 0;
        subJugadorMostrandose = 1;

        prepararInterfaz();
    }

    private void prepararInterfaz() {
        interfaz.setTarjetaPrincipal(jugadores.get(0));
        interfaz.setSubTarjeta(jugadores.get(subJugadorMostrandose));
        interfaz.setMarcadorRonda(rondas);
        interfaz.setMarcadorNumeroDeJugadores(jugadores.size());

        ArrayList<JTextArea> infos;
        infos = new ArrayList<>();

        //Mostrar información de todos los jugadores
        for (Jugador jugador : jugadores) {
            JTextArea info = new JTextArea();
            info.setEditable(false);
            info.setBackground(interfaz.getBackground());
            info.setText(
                    jugador.getNombre() + "\n"
                    + "Cartas Marcadas: " + jugador.getTarjeta().getCantidadDeCartasMarcadas());

            infos.add(info);
            interfaz.getPanelInfoOtrosJugadores().add(info);
        }

        interfaz.setVisible(true);

        ajustarEventos();
    }

    public void iniciarRonda() {
        rondas++;
        baraja.reiniciar();
        baraja.barajar();
        cartasLanzadas.clear();
        reiniciarTarjetaTodosJugadores();

        interfaz.setMarcadorRonda(rondas);

        iniciarGriton();
    }

    private void iniciarGriton() {
        timer = new Timer(TIEMPO_ESPERA_GRITON, (e) -> {
            if (baraja.isEmpty()) {
                timer.stop();
            } else {
                lanzarCartaGriton();
            }
        });

        timer.setRepeats(true);
        lanzarCartaGriton();
        timer.start();
    }

    private void reiniciarTarjetaTodosJugadores() {
        for (Jugador jugador : jugadores) {
            jugador.setTarjeta(new Tarjeta());
        }
    }

    private void lanzarCartaGriton() {
        cartasLanzadas.add(baraja.lanzarCarta());
        int cantidad = cartasLanzadas.size();
        Carta ultimaCarta = cartasLanzadas.get(cantidad - 1);
        interfaz.setCartaGriton(ultimaCarta);
    }

    private void ajustarEventos() {
        //Cambio de tarjeta
        interfaz.getBotonSigTarjeta().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Verificar que no llegaría al tope de la cantidad de jugadores.
                if (subJugadorMostrandose + 1 == jugadores.size()) {
                    //Si es así, que vuelva al primero después del jugador principal.
                    subJugadorMostrandose = 1;
                } else {
                    subJugadorMostrandose++;
                }

                interfaz.setSubTarjeta(jugadores.get(subJugadorMostrandose));
            }

        });
        interfaz.getBotonAntTarjeta().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Verificar que no llegaría a la posicion del jugador principal.
                if (subJugadorMostrandose - 1 == 0) {
                    //Si es así, que vaya hasta la última posición
                    subJugadorMostrandose = jugadores.size() - 1;
                } else {
                    subJugadorMostrandose--;
                }

                interfaz.setSubTarjeta(jugadores.get(subJugadorMostrandose));
            }

        });
        interfaz.getBotonMostrarTarjeta().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //Marcadores de tarjetas
        TarjetaGrafica_Old tarjetaPrincipal = interfaz.getTarjetaPrincipal();
        ArrayList<CartaGrafica> cartasGraficas = tarjetaPrincipal.getCartasGraficas();
        for (CartaGrafica cartaGrafica : cartasGraficas) {
            cartaGrafica.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int posicionActual = cartasGraficas.indexOf(cartaGrafica);

                    if (!cartaGrafica.isMarcada()) {
                        tarjetaPrincipal.marcarCarta(posicionActual, true);
                        jugadores.get(0).getTarjeta().marcarCarta(posicionActual);
                    } else {
                        tarjetaPrincipal.marcarCarta(posicionActual, false);
                        jugadores.get(0).getTarjeta().desmarcarCarta(posicionActual);
                    }

                    interfaz.setMarcadorNumeroCartasMarcadas(jugadores.get(0).getTarjeta().getCantidadDeCartasMarcadas());
                }
            });
        }

        //Anunciar lotería
        interfaz.getBotonLoteria().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean victoria = comprobarVictoria();

                if (victoria) {
                    terminarJuego();
                } else {
                }
            }
        });
    }

    private boolean comprobarVictoria() {
        //Obtener tarjeta del jugador principal.
        Tarjeta tarjeta = jugadores.get(0).getTarjeta();

        //Comprobar si al menos a terminado de marcar todas sus cartas.
        if (tarjeta.getCantidadDeCartasMarcadas() != 16) {
            return false;
        }

        //Obtener todas las cartas marcadas de la tarjeta
        ArrayList<Carta> cartasMarcadas = new ArrayList<>();
        for (Integer posicionMarcada : tarjeta.getPosicionesMarcadas()) {
            Carta cartaMarcada = tarjeta.getCarta(posicionMarcada);
            cartasMarcadas.add(cartaMarcada);
        }

        boolean coincidio = false;
        //Posicionarse en cada una de las cartas marcadas.
        for (Carta cartaMarcada : cartasMarcadas) {
            coincidio = false;

            //Comprobar la actual carta marcada con 
            //cada una de las cartas lanzadas.
            for (Carta cartaLanzada : cartasLanzadas) {

                //Si coincide aunque sea una vez, se detiene el ciclo.
                if (cartaMarcada.getId() == cartaLanzada.getId()) {
                    coincidio = true;
                    break;
                }
            }

            //Si la carta marcada actual no coincidió con ninguna
            //de las lanzadas, entonces el jugador no ha ganados.
            if (!coincidio) {
                return false;
            }
        }

        //Si se pasan todas las comprobaciones, entonces hay victoria.
        return true;
    }

    private void terminarJuego() {
        //Si el gritón no se ha detenido, forzarlo a hacerlo
        if (timer.isRunning()) {
            timer.stop();
        }

        interfaz.terminar();
    }

}
