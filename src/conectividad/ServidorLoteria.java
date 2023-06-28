package conectividad;

import loteria.Movimiento;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Ulises Tovar
 */
public class ServidorLoteria {

    private final ServerSocket server;
    private final ArrayList<ClienteListener> listeners;

    public ServidorLoteria(int puerto) throws IOException {
        server = new ServerSocket(puerto);
        listeners = new ArrayList<>();
    }

    /**
     * Empezar a recibir jugadores
     *
     */
    public void servir() {
        while (!server.isClosed()) {
            //Esperar un cliente
            Socket cliente;
            try {
                cliente = server.accept();
                LoteriaHandler handler = new LoteriaHandler(cliente, this);
                listeners.add(handler);
                handler.start();
            } catch (IOException ex) {
                mostrarError("No fue posible conectar un cliente.");
                mostrarError(ex.getMessage());
            }
        }
    }

    public void iniciarRonda() {
        //Obtener y colocar una nueva semilla para la baraja
        long semilla = System.nanoTime();
        listeners.forEach((listener) -> {
            listener.iniciarRonda(semilla);
        });
    }
    
    public void setVelocidadGriton(int segundos) {
        listeners.forEach((listener) -> {
            listener.setSegundosEsperaGriton(segundos);
        });
    }

    private void setJuego(boolean condicion) {
        listeners.forEach((listener) -> {
            listener.setJugando(condicion);
        });
    }

    public void detener() {
        try {
            setJuego(false);
            server.close();
            System.out.println("Servidor cerrado");
        } catch (IOException ex) {
            mostrarError("No fue posible cerrar el servidor");
            mostrarError(ex.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        System.err.println(mensaje);
    }

    protected void retransmitirMovimiento(Movimiento m) {
        System.out.println("Servidor M: se recibió un movimiento de " + m.getNombre());

        listeners.forEach((listener) -> {
            listener.enviarMovimiento(m);
        });
        System.out.println("Se envió el movimiento de " + m.getNombre()
                + " a " + listeners.size() + " escuchadores");
    }

    public ServerSocket getServer() {
        return server;
    }

    
}
