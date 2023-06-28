
package loteria;

/**
 *
 * @author Ulises Tovar
 */
public interface RondaEvent {
    public boolean addRondaListener(RondaListener r);
    public boolean removeRondaListener(RondaListener r);
    public void removeAllRondaListeners();
    public void avisarFinRonda();
}
