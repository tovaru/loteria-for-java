
package Menu;

import PiezasDeLoteria.Jugador;

/**
 *
 * @author Ulises Tovar
 */
public class TestMenu {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        MenuInicio mn = new MenuInicio();
        mn.opciones.add(new IngresarNombre(null));
        mn.setVisible(true);
    }
    
}
