/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loteria;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Ulises Tovar
 */
public class TestFiles {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File f = new File("archivo_de_prueba.txt");
        
        try {
            try (FileOutputStream fOut = new FileOutputStream(f, true)) {
                if (!f.exists()) {
                    f.createNewFile();
                }
                String linea = "Hola, he creado un archivo.";
                fOut.write(linea.getBytes());
                fOut.flush();
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        
    }
    
}
