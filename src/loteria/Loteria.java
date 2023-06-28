package loteria;

import Menu.ElegirMarcador;
import Menu.ElegirModoDeJuego;
import Menu.ElegirTarjeta;
import Menu.IngresarNombre;
import Menu.MenuInicio;
import PiezasDeLoteria.Jugador;
import conectividad.ClienteGrafico;
import javax.swing.JOptionPane;

/**
 *
 * @author Ulises Tovar
 */
public class Loteria {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Crear menú de inicio
        MenuInicio menu = new MenuInicio();

        //Permitir que el usuario personalice el jugador
        Jugador jugador = crearJugador(menu);

        //Crear el juego con el jugador recién creado
        JuegoGrafico juego = new JuegoGrafico(jugador);

        //Crear un cliente de conexión
        ClienteGrafico cliente = new ClienteGrafico(juego);

        //Permitir que el usuario decida cómo conectar el cliente
        ElegirModoDeJuego emj = new ElegirModoDeJuego(cliente);
        menu.agregarMenu(emj);
        try {
            menu.ejecutar();
        } catch (InterruptedException ex) {
            System.err.println("La creación del jugador fue interrumpida.");
            JOptionPane.showMessageDialog(menu, "La creación del jugador fue interrumpida.");
        }

        //Ocultar el menú una vez se ha terminado de ejecutar
        menu.setVisible(false);
        juego.setVisible(true);
    }

    private static Jugador crearJugador(MenuInicio menu) {
        Jugador jugador = new Jugador();

        IngresarNombre in = new IngresarNombre(jugador);
        ElegirMarcador em = new ElegirMarcador(jugador);
        ElegirTarjeta et = new ElegirTarjeta(jugador);

        menu.agregarMenu(in);
        menu.agregarMenu(em);
        menu.agregarMenu(et);

        //Esperar a que se termine de ejecutar el menú de inicio
        try {
            menu.ejecutar();
        } catch (InterruptedException ex) {
            System.err.println("La creación del jugador fue interrumpida.");
            JOptionPane.showMessageDialog(menu, "La creación del jugador fue interrumpida.");
        }

        return jugador;
    }

}
