package Menu;

import javax.swing.JPanel;


/**
 *
 * @author Ulises Tovar
 */
public abstract class PanelDeMenu extends JPanel {
    protected MenuInicio padre;
    public abstract boolean ejecutar();

    public MenuInicio getPadre() {
        return padre;
    }
    
}
