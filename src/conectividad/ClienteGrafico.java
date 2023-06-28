package conectividad;

import Menu.SalaVentana;
import PiezasDeLoteria.Jugador;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import loteria.JuegoGrafico;
import loteria.RondaListener;

/**
 *
 * @author Ulises Tovar
 */
public class ClienteGrafico extends ClienteLoteria implements RondaListener {

    private final SalaVentana ventana;
    private ServidorLoteria servidor;
    private final ArrayList<JLabel> labelsNombres;
    private int limiteRondas;

    public ClienteGrafico(JuegoGrafico juego) {
        super(juego);
        labelsNombres = new ArrayList<>();
        ventana = new SalaVentana();
        ajustarBotones();
        ajustarCerradoDeCliente();
    }

    private void ajustarBotones() {
        ajustarBotonIniciar();
        ajustarBotonSalir();
    }

    /**
     * Este método recibe el objeto de un servidor para poder ajustar las
     * opciones de juego, en caso de estar creando una sala. De otra forma,
     * envía "null" para desactivarlo (si es que ya se activó antes).
     *
     * @param servidor que controlará el usuario de esta sala
     */
    public void setModoServidor(ServidorLoteria servidor) {
        this.servidor = servidor;
        ventana.getPanelServidor().setVisible(servidor != null);
    }

    public SalaVentana getVentana() {
        return ventana;
    }

    @Override
    protected void agregarJugador(Jugador jugador) {
        super.agregarJugador(jugador);
        agregarLabel(jugador.getNombre());
    }

    private void agregarLabel(String nombre) {
        //Crear un label que muestre el nombre del jugador
        JLabel label = new JLabel(nombre);
        label.setFont(new java.awt.Font("Consolas", 1, 24));
        labelsNombres.add(label);
        ventana.getPanelConectados().add(label);
        ventana.getPanelConectados().revalidate();
    }

    @Override
    protected synchronized void desconectarJugador(Jugador jugador) {
        super.desconectarJugador(jugador);
        //Buscar el label que pertenezca al jugador desconectado
        EventQueue.invokeLater(() -> {
            JLabel[] array = new JLabel[labelsNombres.size()];
            labelsNombres.toArray(array);
            for (JLabel conectado : array) {
                if (conectado.getText().equals(jugador.getNombre())) {
                    labelsNombres.remove(conectado);
                    ventana.getPanelConectados().remove(conectado);
                    ((JuegoGrafico) juego).getInterfaz().panelOtrosJugadores.revalidate();
                }
            }
            ventana.repaint();
            ventana.revalidate();
        });
    }

    @Override
    protected void desconectar() throws IOException {
        super.desconectar();
        //Cerrar (ocultar) la ventana del juego
        ((JuegoGrafico) juego).getInterfaz().setVisible(false);
        //Hacer que el menú de la sala regrese al menú de conexión para una nueva sala
        ventana.getPadre().getBotonAtras().doClick();
        System.exit(0);
    }

    @Override
    protected void iniciarJuego() {
        super.iniciarJuego();
        ventana.avanzar();
        System.out.println("Ventana visible ahora");
    }

    private void ajustarCerradoDeCliente() {
//        ventana.addAncestorListener(new AncestorListener() {
//            @Override
//            public void ancestorAdded(AncestorEvent ae) {
//                ventana.getPadre().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//                ventana.getPadre().addWindowListener(new WindowAdapter() {
//                    @Override
//                    public void windowClosing(WindowEvent we) {
//                        super.windowClosing(we);
//                        if (tratarDeSalir()) {
//                            salir();
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void ancestorRemoved(AncestorEvent ae) {
//
//            }
//
//            @Override
//            public void ancestorMoved(AncestorEvent ae) {
//
//            }
//        });
        ((JuegoGrafico) juego).getInterfaz().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ((JuegoGrafico) juego).getInterfaz().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (tratarDeSalir()) {
                    salir();
                }
            }
        });
        ventana.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent ce) {
                super.componentHidden(ce);
                ventana.getPadre().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }

            @Override
            public void componentShown(ComponentEvent ce) {
                ventana.getPadre().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                ventana.getPadre().addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent we) {
                        super.windowClosing(we);
                        if (tratarDeSalir()) {
                            salir();
                        }
                    }
                });
            }

        });
    }

    private boolean tratarDeSalir() {
        int continuar = JOptionPane.showConfirmDialog(ventana,
                "¿Seguro que deseas salir del juego?",
                "Saliendo del juego",
                JOptionPane.YES_NO_OPTION);
        return continuar == JOptionPane.YES_OPTION;
    }

    private void salir() {
        if (servidor != null) {
            servidor.detener();
        } else {
            try {
                solicitarDesconexion();
            } catch (IOException ex) {
                mostrarError("Error al intentar salir (botón)");
                ex.printStackTrace(System.err);
            }
        }
    }

    @Override
    public void rondaTerminada() {
        if (juego.getRondas() <= limiteRondas) {
            servidor.iniciarRonda();
        }
    }

    private void ajustarBotonIniciar() {
        ventana.getBotonIniciar().addActionListener((ae) -> {
            String numero = ventana.getField_rondas().getText();
            String numero2 = ventana.getField_rapidez().getText();
            try {
                int rondas = Integer.parseInt(numero);
                int velocidad = Integer.parseInt(numero2) * 1000;
                int continuar = JOptionPane.showConfirmDialog(ventana, "¿Deseas iniciar el juego ya?", "Iniciar el juego", JOptionPane.YES_NO_OPTION);
                if (continuar == JOptionPane.YES_OPTION) {
                    System.out.println("Rondas puestas: " + rondas);
                    juego.addRondaListener(this);
                    servidor.setVelocidadGriton(velocidad);
                    servidor.iniciarRonda();
                    servidor.iniciarRonda();
                    limiteRondas = rondas;
                }
            } catch (NumberFormatException ex) {
            }
        });
    }

    private void ajustarBotonSalir() {
        ActionListener[] actionListeners = ventana.getBotonSalir().getActionListeners();
        for (ActionListener actionListener : actionListeners) {
            ventana.getBotonSalir().removeActionListener(actionListener);
        }
        ventana.getBotonSalir().addActionListener((ae) -> {
            if (tratarDeSalir()) {
                salir();
                ventana.getPadre().getBotonAtras().doClick();
            }
        });

    }
}
