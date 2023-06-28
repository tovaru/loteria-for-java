package piezazgraficas;

import PiezasDeLoteria.Nombres;
import java.io.File;
import loteria.Loteria;

/**
 *
 * @author Ulises Tovar
 */
public class ImagenesDeCartas {

    public static File asignarImagen(Nombres personaje) {
        File archivo;
        switch (personaje) {
            case ElGallo:
                archivo = new File("src\\imagenes\\1.png");
                break;
            case ElDiablito:
                archivo = new File("src\\imagenes\\2.png");
                break;
            case LaDama:
                archivo = new File("src\\imagenes\\3.png");
                break;
            case ElCatrin:
                archivo = new File("src\\imagenes\\4.png");
                break;
            case ElParaguas:
                archivo = new File("src\\imagenes\\5.png");
                break;
            case LaSirena:
                archivo = new File("src\\imagenes\\6.png");
                break;
            case LaEscalera:
                archivo = new File("src\\imagenes\\7.png");
                break;
            case LaBotella:
                archivo = new File("src\\imagenes\\8.png");
                break;
            case ElBarril:
                archivo = new File("src\\imagenes\\9.png");
                break;
            case ElArbol:
                archivo = new File("src\\imagenes\\10.png");
                break;
            case ElMelon:
                archivo = new File("src\\imagenes\\11.png");
                break;
            case ElValiente:
                archivo = new File("src\\imagenes\\12.png");
                break;
            case ElGorrito:
                archivo = new File("src\\imagenes\\13.png");
                break;
            case LaMuerte:
                archivo = new File("src\\imagenes\\14.png");
                break;
            case LaPera:
                archivo = new File("src\\imagenes\\15.png");
                break;
            case LaBandera:
                archivo = new File("src\\imagenes\\16.png");
                break;
            case ElBandolon:
                archivo = new File("src\\imagenes\\17.png");
                break;
            case ElVioloncello:
                archivo = new File("src\\imagenes\\18.png");
                break;
            case LaGarza:
                archivo = new File("src\\imagenes\\19.png");
                break;
            case ElPajaro:
                archivo = new File("src\\imagenes\\20.png");
                break;
            case LaMano:
                archivo = new File("src\\imagenes\\21.png");
                break;
            case LaBota:
                archivo = new File("src\\imagenes\\22.png");
                break;
            case LaLuna:
                archivo = new File("src\\imagenes\\23.png");
                break;
            case ElCotorro:
                archivo = new File("src\\imagenes\\24.png");
                break;
            case ElBorracho:
                archivo = new File("src\\imagenes\\25.png");
                break;
            case ElNegrito:
                archivo = new File("src\\imagenes\\26.png");
                break;
            case ElCorazon:
                archivo = new File("src\\imagenes\\27.png");
                break;
            case LaSandia:
                archivo = new File("src\\imagenes\\28.png");
                break;
            case ElTambor:
                archivo = new File("src\\imagenes\\29.png");
                break;
            case ElCamaron:
                archivo = new File("src\\imagenes\\30.png");
                break;
            case LasJaras:
                archivo = new File("src\\imagenes\\31.png");
                break;
            case ElMusico:
                archivo = new File("src\\imagenes\\32.png");
                break;
            case LaAraña:
                archivo = new File("src\\imagenes\\33.png");
                break;
            case ElSoldado:
                archivo = new File("src\\imagenes\\34.png");
                break;
            case LaEstrella:
                archivo = new File("src\\imagenes\\35.png");
                break;
            case ElCazo:
                archivo = new File("src\\imagenes\\36.png");
                break;
            case ElMundo:
                archivo = new File("src\\imagenes\\37.png");
                break;
            case ElApache:
                archivo = new File("src\\imagenes\\38.png");
                break;
            case ElNopal:
                archivo = new File("src\\imagenes\\39.png");
                break;
            case ElAlacran:
                archivo = new File("src\\imagenes\\40.png");
                break;
            case LaRosa:
                archivo = new File("src\\imagenes\\41.png");
                break;
            case LaCalavera:
                archivo = new File("src\\imagenes\\42.png");
                break;
            case LaCampana:
                archivo = new File("src\\imagenes\\43.png");
                break;
            case ElCantarito:
                archivo = new File("src\\imagenes\\44.png");
                break;
            case ElVenado:
                archivo = new File("src\\imagenes\\45.png");
                break;
            case ElSol:
                archivo = new File("src\\imagenes\\46.png");
                break;
            case LaCorona:
                archivo = new File("src\\imagenes\\47.png");
                break;
            case LaChalupa:
                archivo = new File("src\\imagenes\\48.png");
                break;
            case ElPino:
                archivo = new File("src\\imagenes\\49.png");
                break;
            case ElPescado:
                archivo = new File("src\\imagenes\\50.png");
                break;
            case LaPalma:
                archivo = new File("src\\imagenes\\51.png");
                break;
            case LaMaceta:
                archivo = new File("src\\imagenes\\52.png");
                break;
            case ElArpa:
                archivo = new File("src\\imagenes\\53.png");
                break;
            case LaRana:
                archivo = new File("src\\imagenes\\54.png");
                break;
            default:
                archivo = new File("src\\imagenes\\SinImagen.jpg");
        }
        return archivo;
    }

}
