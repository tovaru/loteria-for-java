/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

import PiezasDeLoteria.Jugador;
import PiezasDeLoteria.Tarjeta;

/**
 *
 * @author zelda
 */
public class ElegirTarjeta extends PanelDeMenu {

    private Jugador jugador;
    private Tarjeta tarjeta;
    /**
     * Creates new form ElegirTarjeta
     * @param jugador
     */
    public ElegirTarjeta(Jugador jugador) {
        initComponents();
        this.jugador = jugador;
        botonGenerarActionPerformed(null);
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

        botonGenerar = new javax.swing.JButton();
        tarjetaGrafica1 = new piezazgraficas.TarjetaGrafica();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        botonGenerar.setBackground(new java.awt.Color(255, 51, 0));
        botonGenerar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        botonGenerar.setText("Generar nueva tarjeta");
        botonGenerar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        botonGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGenerarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 40;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        add(botonGenerar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        add(tarjetaGrafica1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void botonGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGenerarActionPerformed
        tarjeta = new Tarjeta();
        tarjetaGrafica1.setTarjeta(tarjeta);
        tarjetaGrafica1.actualizar();
    }//GEN-LAST:event_botonGenerarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonGenerar;
    private piezazgraficas.TarjetaGrafica tarjetaGrafica1;
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean ejecutar() {
        jugador.setTarjeta(tarjeta);
        return true;
    }
}
