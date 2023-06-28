package loteria;

import PiezasDeLoteria.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

/**
 *
 * @author Ulises Tovar
 */
public class Juego implements RondaEvent {

    protected Jugador jugadorPrincipal;
    protected ArrayList<Jugador> otrosJugadores;
    private final ArrayList<RondaListener> rondaListeners;

    protected Mazo baraja;
    protected ArrayList<Carta> cartasLanzadas;
    protected static int tiempoEsperaGriton = 1000;
    private static final int TIEMPO_DE_PENALIZACION = 10000;
    protected Timer timer;

    protected int rondas;
    protected static final int LIMITE_RONDAS = 2;

    protected Thread revisorDeVictorias;
    protected boolean jugando;

    protected static final int DELAY_OTRA_RONDA = 5000;

    public Juego(Jugador jugador) {
        jugadorPrincipal = jugador;
        otrosJugadores = new ArrayList<>();
        baraja = new Mazo();
        cartasLanzadas = new ArrayList<>();
        rondaListeners = new ArrayList<>();
        prepararGriton();
        prepararDelay();
    }

    private void prepararDelay() {
        iniciadorRonda = new Timer(1000, (ActionEvent ae) -> {
            if (segRestantes > 0) {
                segRestantes--;
            } else {
                iniciarRonda();
                iniciadorRonda.stop();
            }
        });
        iniciadorRonda.setInitialDelay(0);
    }

    public void jugar() {
        //Usar un delay para el inicio de la próxima ronda
        segRestantes = DELAY_OTRA_RONDA / 1000;
        iniciadorRonda.start();
    }

    protected void iniciarRonda() {
        jugando = true;
        rondas++;

        //Reiniciar gritón
        baraja.reiniciar();
        baraja.barajar();
        cartasLanzadas.clear();
        if (timer.isRunning()) {
            timer.stop();
        }
        timer.start();

        /////////////////////////////////////
        if (jugadorPrincipal.getNombre().contains("BOT")) {
            iniciarMovimientosAleatorios();
        }
    }

    protected Timer iniciadorRonda;
    protected int segRestantes;

    private void prepararGriton() {
        timer = new Timer(tiempoEsperaGriton, (ae) -> {
            if (baraja.isEmpty()) {
                timer.stop();
            } else {
                lanzarCartaGriton();
            }
        });

        timer.setRepeats(true);
    }

    protected void lanzarCartaGriton() {
        cartasLanzadas.add(baraja.lanzarCarta());
    }

    protected boolean comprobarVictoria(Tarjeta tarjeta) {
        //Comprobar si al menos a terminado de marcar todas sus cartas.
        if (tarjeta.getCantidadDeCartasMarcadas() != Tarjeta.CANTIDAD_DE_CARTAS) {
            return false;
        }

        //Obtener todas las cartas marcadas de la tarjeta
        ArrayList<Carta> cartasMarcadas = new ArrayList<>();
        for (Integer posicionMarcada : tarjeta.getPosicionesMarcadas()) {
            Carta cartaMarcada = tarjeta.getCarta(posicionMarcada);
            cartasMarcadas.add(cartaMarcada);
        }

        boolean coincidio;
        //Posicionarse en cada una de las cartas marcadas.
        for (Carta cartaMarcada : cartasMarcadas) {
            coincidio = false;

            //Comprobar la actual carta marcada con 
            //cada una de las cartas lanzadas.
            for (Carta cartaLanzada : cartasLanzadas) {

                //Ante la primera coincidencia, ya no es necesario
                //revisar las demás
                if (cartaMarcada.getId() == cartaLanzada.getId()) {
                    coincidio = true;
                    break;
                }
            }

            //Si la carta marcada actual no coincidió con ninguna
            //de las lanzadas, entonces el jugador no ha ganado.
            if (!coincidio) {
                return false;
            }
        }

        //Si se pasan todas las comprobaciones, entonces hay victoria.
        return true;
    }

    protected void terminarRonda(Jugador jugador) {
        jugando = false;
        //Si el gritón no se ha detenido, forzarlo a hacerlo
        if (timer.isRunning()) {
            timer.stop();
        }

        jugador.sumarVictoria();

        //Notificar que terminó la ronda
        avisarFinRonda();
        registrarVictoriaArchivo(jugador);
    }

    public Mazo getBaraja() {
        return baraja;
    }

    public void setOtrosJugadores(ArrayList<Jugador> otrosJugadores) {
        this.otrosJugadores = otrosJugadores;
    }

    public void agregarJugador(Jugador jugador) {
        if (!jugando) {
            otrosJugadores.add(jugador);
        }
    }

    public void quitarJugador(Jugador jugador) {
        otrosJugadores.remove(jugador);
    }

    public boolean revisarVictoria(Jugador jugador) {
        boolean gano = comprobarVictoria(jugador.getTarjeta());
        if (gano && !jugador.isPenalizado()) {
            terminarRonda(jugador);
        } else {
            jugador.setPenalizado(true);
        }
        jugador.setAnunciandoVictoria(false);
        return gano;
    }

    public Jugador getJugadorPrincipal() {
        return jugadorPrincipal;
    }

    public boolean isJugando() {
        return jugando;
    }

    public ArrayList<Jugador> getOtrosJugadores() {
        return otrosJugadores;
    }

    public int getRondas() {
        return rondas;
    }

    @Override
    public void avisarFinRonda() {
        rondaListeners.forEach((rondaListener) -> {
            rondaListener.rondaTerminada();
        });
    }

    @Override
    public boolean addRondaListener(RondaListener r) {
        return rondaListeners.add(r);
    }

    @Override
    public boolean removeRondaListener(RondaListener r) {
        return rondaListeners.remove(r);
    }

    @Override
    public void removeAllRondaListeners() {
        rondaListeners.clear();
    }

    public void setTiempoEsperaGriton(int tiempoEsperaGriton) {
        Juego.tiempoEsperaGriton = tiempoEsperaGriton;
        prepararGriton();
    }

    Timer moves;

    private void iniciarMovimientosAleatorios() {
        String nombre = jugadorPrincipal.getNombre();
        String replace = nombre.replace("BOT ", "");
        int espera;
        try {
            espera = Integer.parseInt(replace);
        } catch (NumberFormatException ex) {
            espera = 500;
        }
        Random r = new Random();
        moves = new Timer(espera, (ae) -> {
            if (jugando) {
                int pos = r.nextInt(Tarjeta.CANTIDAD_DE_CARTAS);
                marcarBot(pos);
            } else {
                moves.stop();
            }
        });
        moves.start();
    }

    protected void marcarBot(int pos) {
        boolean contains = jugadorPrincipal.getTarjeta().getPosicionesMarcadas().contains(pos);
        if (contains) {
            jugadorPrincipal.desmarcarCarta(pos);
        } else {
            jugadorPrincipal.marcarCarta(pos);
        }
    }

    protected Timer timerContador;
    protected int tiempoPenalizado;

    protected void penalizar(Jugador jugador) {
        tiempoPenalizado = TIEMPO_DE_PENALIZACION / 1000;
        //Frecuencia de actualización de 1 segundo
        timerContador = new Timer(1000, (ActionEvent ae) -> {
            tiempoPenalizado--;
            if (tiempoPenalizado < 0) {
                jugador.setPenalizado(false);
                System.out.println("Detenido");
                timerContador.stop();
            }
        });

        timerContador.setRepeats(true);
        timerContador.setInitialDelay(0);

        //El timerContador empieza la cuenta regresiva para devolver el control
        //al jugador.
        timerContador.start();
    }

    private void registrarVictoriaArchivo(Jugador ganador) {
        File f = new File("Victorias_por_ronda.txt");

        try {
            try (FileOutputStream fOut = new FileOutputStream(f, true)) {
                if (!f.exists()) {
                    f.createNewFile();
                }
                String linea = "Ganador de la ronda " + rondas
                        + ": " + ganador.getNombre();
                fOut.write(linea.getBytes());
                fOut.flush();
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }

    }
}
