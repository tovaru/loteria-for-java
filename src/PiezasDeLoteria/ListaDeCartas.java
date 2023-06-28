package PiezasDeLoteria;


/**
 * La clase que sirve de base de datos para los personajes. Contiene
 * descripciones y nombres.
 *
 * @author Ulises Tovar
 */
public class ListaDeCartas {

    public static String[] crearPersonaje(Nombres personaje) {
        String[] atributos = new String[2];

        //Se crean los atributos de una carta de acuerdo al nombre elegido
        //por el enum del método
        switch (personaje) {
            case ElGallo:
                atributos[0] = "El gallo";
                atributos[1] = "El que le cantó a San Pedro.";
                break;
            case ElDiablito:
                atributos[0] = "El diablito";
                atributos[1] = "Pórtate bien, cuatito, si no te lleva el coloradito.";
                break;
            case LaDama:
                atributos[0] = "La dama";
                atributos[1] = "Puliendo el paso, por toda la calle real.";
                break;
            case ElCatrin:
                atributos[0] = "El catrin";
                atributos[1] = "Don Ferruco en la alameda, su bastón quería tirar."
                        + "\nInspirado en la personalidad de Don Memo (ampliamente"
                        + "\nconocido por su elegancia y caballerosidad).";
                break;
            case ElParaguas:
                atributos[0] = "El paraguas";
                atributos[1] = "Para el sol y para el agua";
                break;
            case LaSirena:
                atributos[0] = "La sirena";
                atributos[1] = "Medio cuerpo de señora se divisa en altamar.";
                break;
            case LaEscalera:
                atributos[0] = "La escalera";
                atributos[1] = "Súbeme paso a pasito, no quieras pegar brinquitos.";
                break;
            case LaBotella:
                atributos[0] = "La botella";
                atributos[1] = "La herramienta del borracho.";
                break;
            case ElBarril:
                atributos[0] = "El barril";
                atributos[1] = "Tanto bebió el albañil, que quedó como barril.";
                break;
            case ElArbol:
                atributos[0] = "El árbol";
                atributos[1] = "El que a buen árbol se arrima buena sombra le cobija.";
                break;
            case ElMelon:
                atributos[0] = "El melón";
                atributos[1] = "Me lo das o me lo quitas.";
                break;
            case ElValiente:
                atributos[0] = "El valiente";
                atributos[1] = "¿Por que le corres, cobarde, trayendo tan buen puñal?";
                break;
            case ElGorrito:
                atributos[0] = "El gorrito";
                atributos[1] = "El gorrito que me ponen\n"
                        + "Ponle su gorrito al nene, no se nos vaya a resfriar. ";
                break;
            case LaMuerte:
                atributos[0] = "La Muerte";
                atributos[1] = "La muerte tilica y flaca.";
                break;
            case LaPera:
                atributos[0] = "La pera";
                atributos[1] = "El que me espera desespera";
                break;
            case LaBandera:
                atributos[0] = "La bandera";
                atributos[1] = "Verde, blanco y colorado, la bandera del soldado.";
                break;
            case ElBandolon:
                atributos[0] = "El bandolón";
                atributos[1] = "Tocando su bandolón, está el mariachi Simón.";
                break;
            case ElVioloncello:
                atributos[0] = "El violoncello";
                atributos[1] = "Creciendo se fue hasta el cielo, "
                        + "\ny como no fue violín, tuvo que ser violoncello.";
                break;
            case LaGarza:
                atributos[0] = "La garza";
                atributos[1] = "Al otro lado del río tengo mi banco de arena,"
                        + "\ndonde se sienta mi chata pico de garza morena.";
                break;
            case ElPajaro:
                atributos[0] = "El pájaro";
                atributos[1] = "Tú me traes a puros brincos, como pájaro en la rama.";
                break;
            case LaMano:
                atributos[0] = "La mano";
                atributos[1] = "La mano de un criminal.";
                break;
            case LaBota:
                atributos[0] = "La bota";
                atributos[1] = "Una bota igual que la otra.";
                break;
            case LaLuna:
                atributos[0] = "La luna";
                atributos[1] = "El farol de los enamorados.";
                break;
            case ElCotorro:
                atributos[0] = "El cotorro";
                atributos[1] = "Cotorro cotorro saca la pata, y empiézame a platicar.";
            case ElBorracho:
                atributos[0] = "El borracho";
                atributos[1] = "¡Ah! qué borracho tan necio, ya no lo puedo aguantar.";
                break;
            case ElNegrito:
                atributos[0] = "El negrito";
                atributos[1] = "El que se comió el azúcar.";
                break;
            case ElCorazon:
                atributos[0] = "El corazón";
                atributos[1] = "No me extrañes, corazón, que regresó en el camión.";
                break;
            case LaSandia:
                atributos[0] = "La sandía";
                atributos[1] = "A barriga que Juan tenía, era empacho de sandía.";
                break;
            case ElTambor:
                atributos[0] = "El tambor";
                atributos[1] = "No te arrugues, cuero viejo, que te quiero pa'tambor.";
                break;
            case ElCamaron:
                atributos[0] = "El camarón";
                atributos[1] = "Camarón que se duerme, se lo lleva la corriente.";
                break;
            case LasJaras:
                atributos[0] = "Las jaras";
                atributos[1] = "Las jaras del indio Adán, donde pegan, dan. ";
                break;
            case ElMusico:
                atributos[0] = "El músico";
                atributos[1] = "El músico trompa de hule, ya no me quiere tocar.";
                break;
            case LaAraña:
                atributos[0] = "La araña";
                atributos[1] = "Atarántamela a palos, no me la dejes llegar.";
                break;
            case ElSoldado:
                atributos[0] = "El soldado";
                atributos[1] = "Uno, dos y tres el soldado p'al cuartel.";
                break;
            case LaEstrella:
                atributos[0] = "La estrella";
                atributos[1] = "La guía de los marineros.";
                break;
            case ElCazo:
                atributos[0] = "El cazo";
                atributos[1] = "El caso que te hago es poco.";
                break;
            case ElMundo:
                atributos[0] = "El mundo";
                atributos[1] = "Este mundo es una bola, y nosotros un balón.";
                break;
            case ElApache:
                atributos[0] = "El apache";
                atributos[1] = "¡Ah, Chihuahua! cuánto apache con pantalón y huarache.";
                break;
            case ElNopal:
                atributos[0] = "El nopal";
                atributos[1] = "Al nopal lo van a ver, nomás cuando tiene tunas.";
                break;
            case ElAlacran:
                atributos[0] = "El alacrán";
                atributos[1] = "El que con la cola pica, le dan una paliza.";
                break;
            case LaRosa:
                atributos[0] = "La rosa";
                atributos[1] = "Rosita, Rosaura, ven que te quiero ahora.";
                break;
            case LaCalavera:
                atributos[0] = "La calavera";
                atributos[1] = "Al pasar por el panteón, me encontré un calaverón.";
                break;
            case LaCampana:
                atributos[0] = "La campana";
                atributos[1] = "Tú con la campana y yo con tu hermana.";
                break;
            case ElCantarito:
                atributos[0] = "El cantarito";
                atributos[1] = "Tanto va el cántaro al agua, que se quiebra"
                        + "\ny te moja las enaguas.";
                break;
            case ElVenado:
                atributos[0] = "El venado";
                atributos[1] = "Saltando va buscando, pero no ve nada.";
                break;
            case ElSol:
                atributos[0] = "El sol";
                atributos[1] = "Solo solo te quedaste, de cobija de los pobres.";
                break;
            case LaCorona:
                atributos[0] = "La corona";
                atributos[1] = "El sombrero de los reyes.";
                break;
            case LaChalupa:
                atributos[0] = "La chalupa";
                atributos[1] = "Rema rema va Lupita, sentada en su chalupita.";
                break;
            case ElPino:
                atributos[0] = "El pino";
                atributos[1] = "Fresco y oloroso, en todo tiempo hermoso.";
                break;
            case ElPescado:
                atributos[0] = "El pescado";
                atributos[1] = "El que por la boca muere, aunque mudo fuere.";
                break;
            case LaPalma:
                atributos[0] = "La palma";
                atributos[1] = "Palmero, sube a la palma y bájame un coco real.";
                break;
            case LaMaceta:
                atributos[0] = "La maceta";
                atributos[1] = "El que nace pa'maceta, no sale del corredor.";
                break;
            case ElArpa:
                atributos[0] = "El arpa";
                atributos[1] = "Arpa vieja de mi suegra, ya no sirves pa'tocar.";
                break;
            case LaRana:
                atributos[0] = "La rana";
                atributos[1] = "Al ver a la verde rana, qué brinco pegó tu hermana.";
                break;
            default:
                atributos[0] = "Nadie";
                atributos[1] = "No es nadie";
        }

        return atributos;
    }

}
