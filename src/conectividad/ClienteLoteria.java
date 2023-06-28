package conectividad;

import loteria.Movimiento;
import PiezasDeLoteria.Jugador;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import loteria.Juego;

/**
 *
 * @author Ulises Tovar
 */
public class ClienteLoteria {

    private Socket socket;
    protected final Juego juego;
    private ObjectInputStream in;
    private ObjectOutput out;
    private static final int TIMEOUT = 500;

    public ClienteLoteria(Juego juego) {
        this.juego = juego;
    }

    public void conectar(String ip, int puerto) throws IOException {
        //Conectar al servidor
        socket = new Socket(ip, puerto);
        socket.setSoTimeout(TIMEOUT);

        //Prepararse para oír los movimientos de este jugador
        ajustarMovimientoListener();

        //Crear los flujos de entrada y salida para enviar objetos
        crearFlujos();

        //Iniciar a compartir información
        iniciar();
    }

    private void crearFlujos() throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    Comandos comandoRecibido;

    private void iniciar() {
        //Enviar la información de este jugador al servidor
        enviarJugador();

        iniciarEntrada();
    }

    private void iniciarEntrada() {
        Thread hilo = new Thread(() -> {
            int intentosDeConexion = 0;
            while (comandoRecibido != Comandos.terminar) {
                try {

                    //Recibir comando que indica el objeto que se recibe
                    comandoRecibido = (Comandos) in.readObject();
                    intentosDeConexion = 0;
                    
                    Jugador jugador;
                    switch (comandoRecibido) {
                        case envioSeed:
                            System.out.println("Cliente: recibiendo semilla");

                            long seed = (long) in.readObject();
                            juego.getBaraja().setSemilla(seed);

                            break;
                        case iniciarRonda:
                            System.out.println("Cliente: iniciando ronda");
                            iniciarJuego();
                            break;
                        case terminar:
                            //Tan sólo al recibir este comando el ciclo acabará 
                            //y se cerrará el cliente

                            break;
                        case jugadorConectado:
                            //Recibir la información del jugador conectado

                            jugador = (Jugador) in.readObject();
                            agregarJugador(jugador);

                            break;
                        case jugadorDesconectado:

                            jugador = (Jugador) in.readObject();
                            desconectarJugador(jugador);

                            break;
                        case movimiento:

                            Movimiento m = (Movimiento) in.readObject();
                            aplicarMovimiento(m);

                            break;
                        case velocidadGriton:
                            //En segundos
                            Integer velocidad = (Integer) in.readObject();
                            juego.setTiempoEsperaGriton(velocidad);
                        default:
                            mostrarError("Se recibió un comando inesperado");
                    }

                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace(System.err);
                    break;
                } catch (SocketTimeoutException ex) {
                    intentosDeConexion++;
                    if (intentosDeConexion > 500) {
                        comandoRecibido = Comandos.terminar;
                    }
                } catch (IOException ex) {
                    ex.printStackTrace(System.err);
                    mostrarError("Se ha perdido la conexión con el servidor");
                    break;
                }
            }
            try {
                desconectar();
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
            }
        });
        hilo.start();
    }

    protected void mostrarError(String mensaje) {
        System.err.println(mensaje);
    }

    protected void desconectar() throws IOException {
        //Acabar con el hilo de entrada
//        comandoRecibido = Comandos.terminar;
        //Notificar al servidor que este jugador ha acabado su conexión
//        if (!socket.isClosed()) {
//            out.writeObject(Comandos.terminar);
//        }
        //Cerrar flujos
        socket.close();
        out.close();
        in.close();
        mostrarError("Te has desconectado");
    }

    /**
     * Añadir un nuevo jugador al juego local
     *
     * @param jugador
     */
    protected void agregarJugador(Jugador jugador) {
        if (!jugador.getNombre().equals(juego.getJugadorPrincipal().getNombre())) {
            System.out.println("Se conectó " + jugador.getNombre());
            juego.agregarJugador(jugador);
        }
    }

    protected synchronized void desconectarJugador(Jugador jugador) {
        System.out.println("Se desconectó " + jugador.getNombre());
        juego.quitarJugador(jugador);
    }

    protected void iniciarJuego() {
        juego.jugar();
    }

    private void enviarJugador() {
        //Enviar la información de este jugador al servidor
        Jugador jugador = crearCopia(juego.getJugadorPrincipal());
        try {
            out.writeObject(jugador);
            out.flush();
            System.out.println("Jugador enviado");
        } catch (IOException ex) {
            mostrarError("Error al enviar jugador");
            ex.printStackTrace(System.out);
            System.exit(0);
        }
    }

    private void aplicarMovimiento(Movimiento m) {
        //Buscar el jugador al que le pertenece el movimiento
        for (Jugador jugador : juego.getOtrosJugadores()) {
            if (jugador.getNombre().equals(m.getNombre())) {
                //Manejar el tipo el tipo de movimiento
                switch (m.getMovimiento()) {
                    case Movimiento.MARCAR_CARTA:
                        jugador.marcarCarta(m.getPosicion());
                        System.out.println(m.getNombre() + " marcó carta " + m.getPosicion());
                        break;
                    case Movimiento.DESMARCAR_CARTA:
                        jugador.desmarcarCarta(m.getPosicion());
                        System.out.println(m.getNombre() + " desmarcó carta " + m.getPosicion());
                        break;
                    case Movimiento.LOTERIA:
                        System.out.println(jugador.getNombre() + " anunció lotería");
                        juego.revisarVictoria(jugador);
                        break;
                    default:
                        mostrarError("Se recibió un movimiento inesperado"
                                + "de " + m.getNombre());
                }
            }
        }
    }

    private void ajustarMovimientoListener() {
        juego.getJugadorPrincipal().addMovimientoListener((m) -> {
            if (!socket.isClosed()) {
                System.out.println("Enviando movimiento " + m.getMovimiento());
                try {
                    //Enviar el movimiento al servidor
                    out.writeObject(Comandos.movimiento);
                    out.writeObject(m);
                } catch (IOException ex) {
                    mostrarError("Error al enviar movimiento");
                    ex.printStackTrace(System.err);
                    try {
                        socket.close();
                        desconectar();
                    } catch (IOException ex1) {
                        ex1.printStackTrace(System.err);
                    }
                }
            }
        });
    }

    private Jugador crearCopia(Jugador jugador) {
        Jugador j = new Jugador();
        j.setNombre(jugador.getNombre());
        j.setTarjeta(jugador.getTarjeta());
        j.setDinero(j.getDinero());
        j.setPenalizado(jugador.isPenalizado());
        j.setMarcadorParaTarjeta(jugador.getMarcadorParaTarjeta());
        return j;
    }

    protected void solicitarDesconexion() throws IOException {
        if (!socket.isClosed()) {
            out.writeObject(Comandos.terminar);
        }
    }
}
