/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loteria;

import java.io.Serializable;

/**
 *
 * @author Ulises Tovar
 */
public interface MovimientoEvent extends Serializable {
    public boolean addMovimientoListener(MovimientoListener listener);
    public boolean removeMovimientoListener(MovimientoListener listener);
    public void removelAllListeners();
    public void lanzarMovimiento(Movimiento m);
}
