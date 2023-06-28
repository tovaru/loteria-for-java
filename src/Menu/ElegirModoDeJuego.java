package Menu;

import conectividad.ClienteGrafico;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 *
 * @author Ulises Tovar
 */
public class ElegirModoDeJuego extends Menu.PanelDeMenu {

    private final CrearSala crearSala;
    private final UnirseASala unirseSala;

    /**
     * Creates new form ElegirModoDeJuego
     *
     * @param cliente el cliente que se conectará
     */
    public ElegirModoDeJuego(ClienteGrafico cliente) {
        initComponents();
        crearSala = new CrearSala(cliente);
        unirseSala = new UnirseASala(cliente);
        ajustarOcultajeBotones();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        botonCrearSala = new javax.swing.JButton();
        botonUnirseASala = new javax.swing.JButton();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        botonCrearSala.setBackground(new java.awt.Color(204, 51, 0));
        botonCrearSala.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        botonCrearSala.setForeground(new java.awt.Color(255, 255, 255));
        botonCrearSala.setText("Crear una sala");
        botonCrearSala.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        botonCrearSala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCrearSalaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 40;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 30, 0);
        add(botonCrearSala, gridBagConstraints);

        botonUnirseASala.setBackground(new java.awt.Color(153, 153, 0));
        botonUnirseASala.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        botonUnirseASala.setForeground(new java.awt.Color(0, 0, 0));
        botonUnirseASala.setText("Unirse a una sala");
        botonUnirseASala.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        botonUnirseASala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonUnirseASalaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 40;
        add(botonUnirseASala, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void botonCrearSalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCrearSalaActionPerformed
        //El siguiente menú será la creación de una sala
        padre.agregarMenu(crearSala);
        //Avanzar al siguiente
        padre.botonOK.doClick();
    }//GEN-LAST:event_botonCrearSalaActionPerformed

    private void botonUnirseASalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonUnirseASalaActionPerformed
        //El siguiente menú será unirse a una sala
        padre.agregarMenu(unirseSala);
        //Avanzar al siguiente
        padre.botonOK.doClick();
    }//GEN-LAST:event_botonUnirseASalaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCrearSala;
    private javax.swing.JButton botonUnirseASala;
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean ejecutar() {
        return true;
    }

    private void ajustarOcultajeBotones() {
        //Cuando este menú se muestre, el botón OK se ocultará.
        //Cuando este menú se oculte, el botón OK se mostrará.
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent ce) {
                //Ocultar y desactivar el avance de menú por la tecla enter
                padre.botonOK.setVisible(false);
                padre.getRootPane().setDefaultButton(null);
                //Quitar los paneles si están agregados
                //Esto ocurrirá cuando ya se hayaa accedido antes a este menú
                if (crearSala.padre != null) {
                    padre.quitarMenu(crearSala);
                }
                if (unirseSala.padre != null) {
                    padre.quitarMenu(unirseSala);
                }
            }

            @Override
            public void componentHidden(ComponentEvent ce) {
                padre.botonOK.setVisible(true);
                padre.botonOK.getRootPane().setDefaultButton(padre.botonOK);
            }
        });
    }
}
