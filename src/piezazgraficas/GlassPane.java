package piezazgraficas;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 *
 * @author Ulises Tovar
 */
public class GlassPane extends JComponent {
    
    private ImageIcon icono;

    public GlassPane() {
        icono = new ImageIcon();
    }

    public void setIcono(ImageIcon icono) {
        this.icono = icono;
        repaint();
    }
    
    public void setIcono(String dir) {
        ImageIcon ii = new ImageIcon(dir);
        icono = ii;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        //Asegurarse de darle un tamaño a la clase 
        //para evitar que la imagen siempre esté vacía
        super.paintComponent(g);
        int x = getWidth() / 2 - (int) (getWidth() * 0.2);
        int y = getHeight() / 2 - (int) (getWidth() * 0.2);
        int ancho = (int) (getWidth() * 0.4);
        //int altura = icono.getIconHeight() * (ancho * 1 / icono.getIconWidth());
        int altura = (int) (getHeight() * 0.4);
        g.drawImage(icono.getImage(), x, y, ancho, altura, this);
    }
    
}
