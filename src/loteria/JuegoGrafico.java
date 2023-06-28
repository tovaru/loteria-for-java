package loteria;

import GUI.PanelDeJugador;
import piezazgraficas.CartaGrafica;
import GUI.PanelVictoria;
import GUI.Tablero;
import PiezasDeLoteria.Carta;
import PiezasDeLoteria.Jugador;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import piezazgraficas.GlassPane;

/**
 *
 * @author Ulises Tovar
 */
public class JuegoGrafico extends Juego {

    private final Tablero interfaz;
    private static final int OTROS_JUGADORES_POR_PANEL = 4;
    private int posAct_otrosJugadores = 0;
    private Timer timerBarra;
    private static final int FREC_ACTUALIZACION_BARRA = 500;
    private PanelVictoria panelVictoria;

    public JuegoGrafico(Jugador jugador) {
        super(jugador);
        interfaz = new Tablero();
        coloresPorJugador = new ArrayList<>();

        prepararInterfaz();
        prepararDelayInicioRonda();
    }

    private void prepararInterfaz() {
        graficarJugador();
        ajustarBarraDeProgreso();
        ajustarBotones();
        ajustarPanelVictoria();
    }

    @Override
    public void iniciarRonda() {
        super.iniciarRonda();
        timerBarra.start();
    }

    public void setVisible(boolean condicion) {
        EventQueue.invokeLater(() -> {
            interfaz.setVisible(condicion);
        });
    }

    @Override
    public void agregarJugador(Jugador jugador) {
        super.agregarJugador(jugador);
        asignarColorACadaJugador();
        System.out.println(jugador.getTarjeta().getCarta(0));
    }

    @Override
    public void quitarJugador(Jugador jugador) {
        super.quitarJugador(jugador);
        asignarJugadoresABotones(0);
    }

    @Override
    protected void lanzarCartaGriton() {
        super.lanzarCartaGriton();
        Carta ultimaCarta = cartasLanzadas.get(cartasLanzadas.size() - 1);
        interfaz.cartaGrafica_Griton.setImagen(ultimaCarta.getId());
        interfaz.cartaGrafica_Griton.ajustar();
        interfaz.labelNombreCarta.setText(ultimaCarta.getNombre());

        interfaz.textAreaDescripcionCarta.setText(ultimaCarta.getDescripcion());
        segundosTranscurridos = 0;
        timerBarra.stop();
        timerBarra.start();
    }

    private void graficarJugador() {
        interfaz.tarjetaGrafica1.setTarjeta(jugadorPrincipal.getTarjeta());
        interfaz.tarjetaGrafica1.actualizar();
        interfaz.labelNombre.setText(jugadorPrincipal.getNombre());

    }

    private void asignarColorACadaJugador() {
        otrosJugadores.forEach((jugador) -> {
            asignarColor(jugador);
        });
    }

    private void asignarColor(Jugador jugador) {
        Color c = generarColorAleatorio();
        ColorYJugador cj = new ColorYJugador(jugador.getNombre(), c);
        coloresPorJugador.add(cj);
        asignarJugadoresABotones(0);
    }

    private Color generarColorAleatorio() {
        Random random = new Random();

        //Generar sólo colores vivos
        float r = (float) (random.nextFloat() / 2f + 0.5);
        float g = (float) (random.nextFloat() / 2f + 0.5);
        float b = (float) (random.nextFloat() / 2f + 0.5);

        return new Color(r, g, b);
    }

    private void asignarJugadoresABotones(int posicionInicio) {
        for (int i = 0; i < OTROS_JUGADORES_POR_PANEL; i++) {
            int posicion = posicionInicio + i;
            if (posicion < otrosJugadores.size()) {
                if (!otrosJugadores.get(posicion).getNombre().equals(this.jugadorPrincipal.getNombre())) {
                    ByJ[i].jugador = otrosJugadores.get(posicion);
                }
            } else {
                ByJ[i].jugador = null;
            }
        }

        //Actualizar estado de cada boton de selección
        //para los otros jugadores
        //Botón 1 (arriba a la izquierda)
        if (ByJ[0].jugador != null) {
            interfaz.botonOtro_1.setText(ByJ[0].jugador.getNombre());
            interfaz.botonOtro_1.setBackground(Color.blue);
//            for (ColorYJugador cJ : coloresPorJugador) {
//                if (cJ.nombreDelJugador.equals(ByJ[0].jugador.getNombre())) {
//                    interfaz.botonOtro_1.setBackground(Color.blue);
//                    //interfaz.botonOtro_1.setBackground(cJ.color);
//                } else {
//                    interfaz.botonOtro_1.setBackground(Color.gray);
//                }
//            }
        } else {
            interfaz.botonOtro_1.setText("");
            interfaz.botonOtro_1.setBackground(Color.gray);
        }

        //Botón 2 (arriba a la derecha)
        if (ByJ[1].jugador != null) {
            interfaz.botonOtro_2.setText(ByJ[1].jugador.getNombre());
            interfaz.botonOtro_2.setBackground(Color.red);
//            for (ColorYJugador colorYJugador : coloresPorJugador) {
//                if (colorYJugador.nombreDelJugador.equals(ByJ[1].jugador.getNombre())) {
//                    interfaz.botonOtro_2.setBackground(Color.red);
//                    //interfaz.botonOtro_2.setBackground(colorYJugador.color);
//                } else {
//                    interfaz.botonOtro_2.setBackground(colorYJugador.color);
//                }
//            }
        } else {
            interfaz.botonOtro_2.setText("");
            interfaz.botonOtro_2.setBackground(Color.gray);
        }

        //Botón 3 (abajo a la izquierda)
        if (ByJ[2].jugador != null) {
            interfaz.botonOtro_3.setText(ByJ[2].jugador.getNombre());
            interfaz.botonOtro_3.setBackground(Color.green);
//            for (ColorYJugador colorYJugador : coloresPorJugador) {
//                if (colorYJugador.nombreDelJugador.equals(ByJ[2].jugador.getNombre())) {
//                    interfaz.botonOtro_3.setBackground(Color.green);
//                    //interfaz.botonOtro_3.setBackground(colorYJugador.color);
//                } else {
//                    interfaz.botonOtro_3.setBackground(Color.gray);
//                }
//            }
        } else {
            interfaz.botonOtro_3.setText("");
            interfaz.botonOtro_3.setBackground(Color.gray);
        }

        //Botón 4 (abajo a la derecha)
        if (ByJ[3].jugador != null) {
            interfaz.botonOtro_4.setText(ByJ[3].jugador.getNombre());
            interfaz.botonOtro_4.setBackground(Color.magenta);
//            for (ColorYJugador colorYJugador : coloresPorJugador) {
//                if (colorYJugador.nombreDelJugador.equals(ByJ[3].jugador.getNombre())) {
//                    interfaz.botonOtro_4.setBackground(Color.magenta);
//                    //interfaz.botonOtro_4.setBackground(colorYJugador.color);
//                } else {
//                    interfaz.botonOtro_4.setBackground(Color.gray);
//                }
//            }
        } else {
            interfaz.botonOtro_4.setText("");
            interfaz.botonOtro_4.setBackground(Color.gray);
        }
    }
    private int segundosTranscurridos;

    private void ajustarBarraDeProgreso() {
        int intervalos = tiempoEsperaGriton / FREC_ACTUALIZACION_BARRA;
        //Para permitir que la barra llegue al final, se vuelve 1 segundo más corta
        interfaz.barraDeProgreso.setMaximum(intervalos - 1);

        timerBarra = new Timer(FREC_ACTUALIZACION_BARRA, (ae) -> {
            interfaz.barraDeProgreso.setValue(segundosTranscurridos++);
        });

        timerBarra.setInitialDelay(0);
        timerBarra.setRepeats(true);
    }

    @Override
    public void setTiempoEsperaGriton(int tiempoEsperaGriton) {
        super.setTiempoEsperaGriton(tiempoEsperaGriton);
        ajustarBarraDeProgreso();
    }

    private void ajustarBotones() {
        ajustarBotonLoteria();
        ajustarMarcacionDeTarjeta();
        ajustarBotonesOtrosJugadores();
        ajustarBotonesNavJugadores();
    }

    @Override
    protected void penalizar(Jugador jugador) {
        super.penalizar(jugador);

        //Volver gris el botón de lotería para indicar penalización
        Color colorNormal = interfaz.botonLoteria.getBackground();
        String textoNormal = interfaz.botonLoteria.getText();
        interfaz.botonLoteria.setBackground(Color.gray);

        //Frecuencia de actualización de 1 segundo
        timerContador.addActionListener((ActionEvent ae) -> {
            interfaz.botonLoteria.setText("" + tiempoPenalizado);
            if (tiempoPenalizado <= 0) {
                interfaz.botonLoteria.setBackground(colorNormal);
                interfaz.botonLoteria.setText(textoNormal);
            }
        });
    }

    private void ajustarBotonLoteria() {
        interfaz.botonLoteria.addActionListener((ae) -> {
            //El jugador no tiene que estar penalizado
            if (!jugadorPrincipal.isPenalizado()) {
                jugadorPrincipal.setAnunciandoVictoria(true);
                revisarVictoria(jugadorPrincipal);
            }
        });
    }

    @Override
    public boolean revisarVictoria(Jugador jugador) {
        boolean gano = super.revisarVictoria(jugador);
        if (jugador.getNombre().equals(jugadorPrincipal.getNombre())) {
            if (!gano && jugador.isPenalizado()) {
                penalizar(jugador);
            }
        }
        return gano;
    }

    private void ajustarMarcacionDeTarjeta() {
        ArrayList<CartaGrafica> cartas = interfaz.tarjetaGrafica1.getCartasGraficas();

        //Definir imagen de los marcadores
        cartas.forEach((carta) -> {
            carta.setMarcador(jugadorPrincipal.getMarcadorParaTarjeta());
        });

        cartas.forEach((carta) -> {
            carta.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    int i = cartas.indexOf(carta);
                    carta.setMarcada(!carta.isMarcada());
                    if (carta.isMarcada()) {
                        jugadorPrincipal.marcarCarta(i);
                    } else {
                        jugadorPrincipal.desmarcarCarta(i);
                    }
                    interfaz.labelMarcadas.setText("" + jugadorPrincipal.getTarjeta().getCantidadDeCartasMarcadas());
                }

            });
        });
    }

    GlassPane glass;

    private void ajustarPanelVictoria() {
        //Crear panel de victoria y agregarlo a la interfaz
        panelVictoria = new PanelVictoria();
        interfaz.jLayeredPane.add(panelVictoria, JLayeredPane.MODAL_LAYER);
        panelVictoria.setVisible(false);

        //Crear un GlassPane, un panel que permitirá retener los clics cuando 
        //se requiera
        glass = new GlassPane();
        glass.addMouseListener(new MouseAdapter() {
            //No hay ninguna acción que vaya a realizar el GlassPane
            //más que retener la entrada
        });
        interfaz.setGlassPane(glass);

        //Prerarar texto preliminar de la ventana de victoria, es decir
        //cuando primeramente no hay un ganador
        panelVictoria.iconoFinRonda.setVisible(false);
        panelVictoria.labelMensajeDeVictoria1.setText("El juego empezará en...");
        panelVictoria.labelNombreGanador.setText("");
    }

    @Override
    protected void terminarRonda(Jugador ganador) {
        super.terminarRonda(ganador);

        if (timerBarra.isRunning()) {
            timerBarra.stop();
        }

        System.out.println("Victoria para " + ganador.getNombre());
        panelVictoria.iconoFinRonda.setVisible(true);
        panelVictoria.labelMensajeDeVictoria1.setText("La victoria "
                + "de esta ronda es para:");
        panelVictoria.labelNombreGanador.setText(ganador.getNombre());
    }

    private void prepararDelayInicioRonda() {
        iniciadorRonda.addActionListener((ae) -> {
            if (segRestantes > 0) {
                panelVictoria.labelTiempo.setText("" + segRestantes);
            } else {
                mostrarVictoria(false);
            }
        });
    }

    private BotonYJugador[] ByJ;
    private final ArrayList<ColorYJugador> coloresPorJugador;

    private void ajustarBotonesOtrosJugadores() {
        ByJ = new BotonYJugador[OTROS_JUGADORES_POR_PANEL];
        ByJ[0] = new BotonYJugador(interfaz.botonOtro_1);
        ByJ[0].boton.addActionListener((ae) -> {
            mostrarJugador(ByJ[0].jugador);
        });
        ByJ[1] = new BotonYJugador(interfaz.botonOtro_2);
        ByJ[1].boton.addActionListener((ae) -> {
            mostrarJugador(ByJ[1].jugador);
        });
        ByJ[2] = new BotonYJugador(interfaz.botonOtro_3);
        ByJ[2].boton.addActionListener((ae) -> {
            mostrarJugador(ByJ[2].jugador);
        });
        ByJ[3] = new BotonYJugador(interfaz.botonOtro_4);
        ByJ[3].boton.addActionListener((ae) -> {
            mostrarJugador(ByJ[3].jugador);
        });

    }

    private void mostrarVictoria(boolean condicion) {
        glass.setVisible(condicion);
        panelVictoria.setVisible(condicion);
    }

    private void mostrarJugador(Jugador jugador) {
        System.out.println(jugador.getTarjeta().getCarta(0));

        PanelDeJugador panel = new PanelDeJugador();
        panel.getLabelNombre().setText(jugador.getNombre());
        panel.getLabelDinero().setText("" + jugador.getDinero());
        panel.getTarjetaGrafica1().setTarjeta(jugador.getTarjeta());
        panel.getTarjetaGrafica1().getCartasGraficas().forEach((cartasGrafica) -> {
            cartasGrafica.setMarcador(jugador.getMarcadorParaTarjeta());
        });
        jugador.getTarjeta().getPosicionesMarcadas().forEach((pos) -> {
            panel.getTarjetaGrafica1().marcarCarta(pos, true);
        });
        panel.getTarjetaGrafica1().actualizar();

        //Empezar a escuchar los movimientos del jugador
        MovimientoListener listener = (Movimiento m) -> {
            switch (m.getMovimiento()) {
                case Movimiento.DESMARCAR_CARTA:
                    panel.getTarjetaGrafica1().marcarCarta(m.getPosicion(), false);
                    panel.getLabelMarcadas().setText(""
                            + jugador.getTarjeta().getCantidadDeCartasMarcadas());
                    panel.getTarjetaGrafica1().actualizar();
                    break;
                case Movimiento.MARCAR_CARTA:
                    panel.getTarjetaGrafica1().marcarCarta(m.getPosicion(), true);
                    panel.getLabelMarcadas().setText(""
                            + jugador.getTarjeta().getCantidadDeCartasMarcadas());
                    panel.getTarjetaGrafica1().actualizar();
                    break;
            }
        };
        jugador.addMovimientoListener(listener);

        //Este hilo se bloqueará hasta que el usuario  cierre la ventana
        JOptionPane.showMessageDialog(interfaz, panel);
        //Cuando se cierra, eliminar el listener
        jugador.removeMovimientoListener(listener);
    }

    @Override
    public void jugar() {

        mostrarVictoria(true);
        super.jugar();
    }

    private void ajustarBotonesNavJugadores() {
        interfaz.botonNavOtros_Der.addActionListener((ae) -> {
            posAct_otrosJugadores += OTROS_JUGADORES_POR_PANEL;
            System.out.println(posAct_otrosJugadores);
            if (posAct_otrosJugadores > otrosJugadores.size()) {
                posAct_otrosJugadores -= OTROS_JUGADORES_POR_PANEL;
            }
            asignarJugadoresABotones(posAct_otrosJugadores);
        });
        interfaz.botonNavOtros_Izq.addActionListener((ae) -> {
            System.out.println(posAct_otrosJugadores);
            posAct_otrosJugadores -= OTROS_JUGADORES_POR_PANEL;
            if (posAct_otrosJugadores < 0) {
                posAct_otrosJugadores += OTROS_JUGADORES_POR_PANEL;
            }
            asignarJugadoresABotones(posAct_otrosJugadores);
        });
    }

    /**
     * Una clase que relaciona un jugador con un boton. * Dicho botón es el que
     * utiliza la interfaz para mostrar la información de un jugador en
     * específico.
     */
    private class BotonYJugador {

        Jugador jugador;
        JButton boton;

        public BotonYJugador(JButton boton) {
            this.boton = boton;
        }
    }

    /*
     * Una clase que relaciona un color con un jugador.
     */
    private class ColorYJugador {

        String nombreDelJugador;
        Color color;

        public ColorYJugador(String nombreDelJugador, Color color) {
            this.nombreDelJugador = nombreDelJugador;
            this.color = color;
        }
    }

    public Tablero getInterfaz() {
        return interfaz;
    }

    @Override
    protected void marcarBot(int pos) {
        super.marcarBot(pos);
        boolean contains = jugadorPrincipal.getTarjeta().getPosicionesMarcadas().contains(pos);
        interfaz.tarjetaGrafica1.getCartasGraficas().get(pos).setMarcada(contains);
    }

}
