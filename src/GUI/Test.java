
package GUI;

import PiezasDeLoteria.Nombres;
import PiezasDeLoteria.Tarjeta;
import javax.swing.Timer;

/**
 *
 * @author zelda
 */
public class Test {

    static int contador = 0;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Tablero t = new Tablero();
        t.setVisible(true);
        t.tarjetaGrafica1.setTarjeta(new Tarjeta());
        t.tarjetaGrafica1.actualizar();
        t.cartaGrafica_Griton.setImagen(Nombres.ElSoldado);
        t.cartaGrafica_Griton.ajustar();
        
        Timer timer = new Timer(200, (ae) -> {
            t.labelRonda.setText("" + ++contador);
        });
        timer.start();
    }
    
}
