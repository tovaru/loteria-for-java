package piezazgraficas;

public class ImagenesDeMarcadores {

    public static String obtenerImagen(Marcadores marcador) {
        String direccion;

        switch (marcador) {
            case Frijol:
                direccion = "src\\imagenes\\Frijol.png";
                break;
            case Frijolito:
                direccion = "src\\imagenes\\Frijolito.png";
                break;
            case Arrozito:
                direccion = "src\\imagenes\\Arrozito.png";
                break;
            case Monedita:
                direccion = "src\\imagenes\\Monedita.png";
                break;
            case Piedrita:
                direccion = "src\\imagenes\\Piedrita.png";
                break;
            case Waifu:
                direccion = "src\\imagenes\\Monika.png";
                break;
            default:
                direccion = "src\\imagenes\\Frijol.png";
        }

        return direccion;
    }
}
