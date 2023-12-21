import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Description of the class
 *
 * @author Jorge Jiménez Navas
 * @author Carlos Gonzalez Díaz
 * @version     1.0
 */
public class PuertoEspacial {
    private String nombre;
    private String codigo;
    private double radio;
    private double azimut;
    private double polar;
    private int numMuelles;

    /**
     * Constructor of the class
     *
     * @param nombre el nombre del puerto espacial.
     * @param codigo el código identificativo otorgado por la Galactic Federation of Spaceport Coordination.
     * @param radio las unidades de distancia desde el sol hasta el punto dado.
     * @param azimut  el ángulo desde el eje positivo x hasta la proyección del punto en el plano xy.
     * @param polar el ángulo desde el eje positivo z hasta el puerto.
     * @param numMuelles número de muelles de carga del puerto espacial.
     */
    public PuertoEspacial(String nombre, String codigo, double radio, double azimut, double polar, int numMuelles) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.radio = radio;
        this.azimut = azimut;
        this.polar = polar;
        this.numMuelles = numMuelles;
    }
    public String getNombre() {
        return nombre;
    }
    public String getCodigo() {
        return codigo;
    }
    public double getRadio() {
        return radio;
    }
    public double getAzimut() {
        return azimut;
    }

    public double getPolar() {
        return polar;
    }
    public int getMuelles() {
        return numMuelles;
    }

    /**
     * TODO: Método para calcular la distancia entre el puerto espacial que recibe el mensaje y el puerto
     *  espacial "destino" siguiendo las ecuaciones del enunciado (Las formulas se encuentran en el enunciado)
     * @param destino destino del envío.
     * @return devuelve la distancia que tiene que recorrer la nave.
     */
    public double distancia(PuertoEspacial destino) {
        // TODO: Para calcular la distancia entre dos Puertos Espaciales, se transforman sus coordenadas esféricas a cartesianas
        //destino
        double x2 = destino.getRadio() * sin(destino.getAzimut()) * cos (destino.getPolar());
        double y2 = destino.getRadio() * sin(destino.getAzimut()) * sin(destino.getPolar());
        double z2 = destino.getRadio() * cos(destino.getAzimut());

        //salida
        double x1 = getRadio() * sin(getAzimut()) * cos (getPolar());
        double y1 = getRadio() * sin(getAzimut()) * sin(getPolar());
        double z1 = getRadio() * cos(getAzimut());


        // TODO: Una vez se tienen las coordenadas cartesianas, basta con calcular la distancia euclídea entre ellas:
        double distancia = Math.sqrt(Math.pow(x2 - x1,2) - Math.pow(y2 - y1,2) - Math.pow(z2 - z1,2));
        return distancia ;
    }

    /**
     * TODO: Método que crea un String con los datos de un puerto espacial con el siguiente formato:
     * @return ejemplo -> "Gaia Galactic Terminal(GGT), en (1.0 90.0 0.0), con 8 muelles" (Radio, Azimut, Polar)
     */
    public String toString() {
        return this.nombre + "(" + this.codigo + "), en (" + this.radio + ", " + this.azimut + ", " + this.polar + "), con " + this.numMuelles + " muelles"  ;
    }

    /**
     * TODO: Método que crea un String con los datos de un aeropuerto con el siguiente formato:
     * @return ejemplo -> "Gaia Galactic Terminal (GGT)"
     */
    public String toStringSimple() {
        return this.nombre + " (" + this.codigo + ")";
    }
}
