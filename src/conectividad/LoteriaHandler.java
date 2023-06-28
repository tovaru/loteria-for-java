package conectividad;

import loteria.Movimiento;
import PiezasDeLoteria.Jugador;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author Ulises Tovar
 */
public class LoteriaHandler extends Thread implements ListChangeListener<Jugador>, ClienteListener {

    private static final ObservableList<Jugador> JUGADORES = FXCollections.observableArrayList();
    private Jugador esteJugador;
    private final Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean jugando = true;

    private final ServidorLoteria servidorLoteria;

    public LoteriaHandler(Socket cliente, ServidorLoteria servidor) throws IOException {
        super();
        this.socket = cliente;
        socket.setSoTimeout(1000);
        this.servidorLoteria = servidor;
        crearFlujos();
    }

    @Override
    public void run() {
        //iniciarSalida();
        //Revisar clientes que ya están conectados
        enviarInfoClientesYaConectados();

        //Permitir que esta parte del servidor escuche los 
        //cambios del arreglo de jugadores
        //Es necesario que esto sea después de recibir el jugador, para no
        //terminar enviando la información de este mismo jugador a su cliente
        JUGADORES.addListener(this);

        //Recibir la información de este cliente
        recibirJugador();

        //Iniciar procesos de interacción con el cliente
        iniciarEntrada();
    }

    private void iniciarEntrada() {
        Thread hilo = new Thread(() -> {
            while (jugando) {
                try {
                    //Identificar qué información se va a recibir
                    Comandos comandoRecibido = (Comandos) in.readObject();

                    //Manejar el comando recibido
                    switch (comandoRecibido) {
                        case movimiento:
                            //Recibir cada una de las partes de un movimiento
                            Movimiento m = (Movimiento) in.readObject();
                            //Solicitar al servidor que se lo envíe a todos
                            servidorLoteria.retransmitirMovimiento(m);
                            break;
                        case terminar: {
                            jugando = false;
                        }
                        break;
                    }
                } catch (IOException ex) {
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace(System.err);
                    jugando = false;
                }
            }
            try {
                desconectarCliente();
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
            }
        });
        hilo.start();
    }

    private void crearFlujos() throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    private void mostrarError(String message) {
        System.err.println(this.getName() + ":");
        System.err.println(message);
    }

    private void recibirJugador() {
        try {
            esteJugador = (Jugador) in.readObject();
            System.out.println("Servidor: se conectó " + esteJugador.getNombre());
            JUGADORES.add(esteJugador);
            this.setName("Hilo de cliente " + esteJugador.getNombre());
        } catch (IOException | ClassNotFoundException ex) {
            mostrarError("Error al recibir jugador");
            ex.printStackTrace(System.out);
        }
    }

    private synchronized void enviar(Comandos comando, Object o) throws IOException {
        out.writeObject(comando);
        if (o != null && !servidorLoteria.getServer().isClosed()) {
            out.writeObject(o);
        }
        out.flush();
    }

    protected void desconectarCliente() throws IOException {
        //Avisar al cliente que termine la conexión
        enviar(Comandos.terminar, null);

        JUGADORES.removeListener(this);
        //Eliminar de la lista de jugadores conectados
        //Lo cual escucharán los otros hilos para notificar a sus clientes
        JUGADORES.remove(esteJugador);

        //Liberar hilos de salida y entrada
        jugando = false;

        //Cerrar flujos
        in.close();
        out.close();
        socket.close();
    }

    @Override
    public void onChanged(Change<? extends Jugador> c) {
        while (c.next()) {
            if (c.wasAdded()) {
                c.getAddedSubList().forEach((jugador) -> {
                    System.out.println("Enviando información de " + jugador.getNombre());
                    try {
                        enviar(Comandos.jugadorConectado, jugador);
                    } catch (IOException ex) {
                        ex.printStackTrace(System.out);
                    }

                });
            }
            if (c.wasRemoved()) {
                c.getRemoved().forEach((jugador) -> {
                    try {
                        enviar(Comandos.jugadorDesconectado, jugador);
                    } catch (IOException ex) {
                        ex.printStackTrace(System.err);
                    }
                });
            }
        }
    }

    @Override
    public void iniciarRonda(long semilla) {
        try {
            enviar(Comandos.envioSeed, semilla);
            enviar(Comandos.iniciarRonda, null);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }

    @Override
    public void setJugando(boolean condicion) {
        //Aplicar nuevo valor
        jugando = condicion;
    }

    @Override
    public void enviarMovimiento(Movimiento m) {
        //Sólo enviar el movimiento a este cliente si el movimiento no es el suyo
        //Esto puede ocurrir porque el serivodor retransmitirá un movimiento a
        //todos los jugadores, incluso a quien hizo la petición
        if (!m.getNombre().equals(esteJugador.getNombre())) {
            try {
                enviar(Comandos.movimiento, m);
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
            }
        }
    }

    @Override
    public void setSegundosEsperaGriton(int velocidad) {
        try {
            enviar(Comandos.velocidadGriton, velocidad);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }

    private void enviarInfoClientesYaConectados() {
        if (!JUGADORES.isEmpty()) {
            JUGADORES.forEach((jugador) -> {
                try {
                    enviar(Comandos.jugadorConectado, jugador);
                } catch (IOException ex) {
                    ex.printStackTrace(System.out);
                }
            });
        }
    }

}
