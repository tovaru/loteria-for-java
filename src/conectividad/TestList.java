package conectividad;

import PiezasDeLoteria.Jugador;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author Ulises Tovar
 */
public class TestList implements ListChangeListener<Jugador> {

    public TestList() {

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ObservableList<Jugador> listaO = FXCollections.observableArrayList();
        TestList t = new TestList();
        listaO.addListener(t);
        Jugador j = new Jugador();
        listaO.add(j);
        Jugador j2 = new Jugador();
        j2.sumarVictoria();
        listaO.set(0, j2);
        
    }

    @Override
    public void onChanged(Change<? extends Jugador> c) {
        while (c.next()) {
            if (c.wasAdded()) {
                System.out.println("Agregado");
                System.out.println(c.getAddedSubList().get(0).getVictorias());
            }
            if (c.wasRemoved()) {
                System.out.println("Removido");
            }
        }
    }

}
