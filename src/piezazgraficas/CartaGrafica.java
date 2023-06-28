package piezazgraficas;

import PiezasDeLoteria.Nombres;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Una carta que muesta una imagen
 *
 * @author Ulises Tovar
 */
public class CartaGrafica extends JLabel {

    /**
     * La dirección de la imagen de la carta
     */
    private File imagenDir;
    private File marcadorDir;
    private boolean marcada;
    private final GlassPane glass;

    public CartaGrafica() {
        imagenDir = new File("");
        marcadorDir = new File(ImagenesDeMarcadores.obtenerImagen(Marcadores.Frijol));
        marcada = false;
        glass = new GlassPane();
        setPreferredSize(new Dimension(50, 80));
        setSize(getPreferredSize());
        setReajuste();
    }

    public CartaGrafica(Nombres personaje) {
        imagenDir = ImagenesDeCartas.asignarImagen(personaje);
        marcadorDir = new File(ImagenesDeMarcadores.obtenerImagen(Marcadores.Frijol));
        marcada = false;
        glass = new GlassPane();
    }

    public CartaGrafica(String dir) {
        imagenDir = new File(dir);
        marcadorDir = new File(ImagenesDeMarcadores.obtenerImagen(Marcadores.Frijol));
        marcada = false;
        glass = new GlassPane();
    }

    public File getImagen() {
        return imagenDir;
    }

    public File getMarcadorDir() {
        return marcadorDir;
    }

    public boolean isMarcada() {
        return marcada;
    }

    public void setImagen(Nombres personaje) {
        imagenDir = ImagenesDeCartas.asignarImagen(personaje);
    }

    public void setImagenDir(File imagenDir) {
        this.imagenDir = imagenDir;
    }

    public void setMarcadorDir(String marcadorDir) {
        this.marcadorDir = new File(marcadorDir);

        ImageIcon imagen = new ImageIcon(marcadorDir);
        glass.setIcono(imagen);
    }

    public void setMarcador(Marcadores marcador) {
        marcadorDir = new File(ImagenesDeMarcadores.obtenerImagen(marcador));

        ImageIcon imagen = new ImageIcon(marcadorDir.getPath());
        glass.setIcono(imagen);
    }

    public void setMarcada(boolean marcada) {
        this.marcada = marcada;

        if (marcada) {
            glass.setVisible(true);
        } else {
            glass.setVisible(false);
        }
    }

    public void ajustar() {
        ImageIcon ii = new ImageIcon(imagenDir.getPath());
        ImageIcon nuevoIcono = reescalarImagen(ii.getImage(), getWidth(), getHeight());

        setIcon(nuevoIcono);

        ajustarGlassPane();
        if (marcada) {
            glass.setVisible(true);
        } else {
            glass.setVisible(false);
        }
    }

    private ImageIcon reescalarImagen(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        ImageIcon icono = new ImageIcon(resizedImg);

        return icono;
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        ajustarGlassPane();
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        ajustarGlassPane();
    }

    private void ajustarGlassPane() {
        glass.setSize(this.getSize());
        this.add(glass);
        ImageIcon imagen = new ImageIcon(marcadorDir.getPath());
        glass.setIcono(imagen);

        glass.setVisible(false);
    }

    public void ajustarResizeRatio(Component contenedor) {
        int ancho = contenedor.getWidth();
        int altura = contenedor.getHeight();
        int size = Math.min(ancho, altura);
        this.setPreferredSize(new Dimension(ancho, altura));
        contenedor.revalidate();
    }

    private void setReajuste() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent ce) {
                super.componentResized(ce);
                if (getParent().isVisible()) {
                    ajustar();
                }
            }
        });
    }
}
