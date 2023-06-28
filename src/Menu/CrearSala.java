/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

import conectividad.ClienteGrafico;
import conectividad.ServidorLoteria;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Ulises Tovar
 */
public class CrearSala extends PanelDeMenu {

    private ServidorLoteria servidor;
    private final ClienteGrafico cliente;

    /**
     * Creates new form CrearSala
     *
     * @param cliente
     */
    public CrearSala(ClienteGrafico cliente) {
        initComponents();
        this.cliente = cliente;
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

        jLabel1 = new javax.swing.JLabel();
        field_puerto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Puerto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(field_puerto, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Ingresa el puerto de conexión que usará tu sala");
        jLabel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jLabel2, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField field_puerto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean ejecutar() {
        String numero = field_puerto.getText();
        //Tratar de crear un servidor y conectar el cliente.
        try {
            int puerto = Integer.parseInt(numero);
            crearServidor(puerto);
            cliente.conectar("localhost", puerto);
            //Poner el cliente en modo servidor
            cliente.setModoServidor(servidor);
            //Agregar la ventana del cliente como el siguiente menú
            padre.agregarMenu(cliente.getVentana());
            return true;
        } catch (NumberFormatException ex) {
            return false;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No fue posible crear la sala"
                    + "\n" + ex.getMessage());
            return false;
        }
    }

    private void crearServidor(int puerto) throws IOException {
        servidor = new ServidorLoteria(puerto);
        Thread hilo = new Thread(() -> {
            servidor.servir();
        });
        hilo.start();
    }
}