import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
/**
 * Description of the class: CLase que representa un viaje con su respectivo cargamento.
 *
 * @author Carlos Gonzalez Diaz
 * @author Jorge Jiménez Navas
 * @version     1.0
 */
public class Porte {
    private boolean[][] huecos;
    private String id;
    private Nave nave;
    private PuertoEspacial origen;
    private int muelleOrigen;
    private Fecha salida;
    private PuertoEspacial destino;
    private int muelleDestino;
    private Fecha llegada;
    private double precio;
    private ListaEnvios listaEnvios;

    /**
     * TODO: Completa el constructo de la clase
     * 
     * @param id Identificador.
     * @param nave Nave encargada de realizar el transporte.
     * @param origen Puerto espacial del que saldrá el envío.
     * @param muelleOrigen Muelle del que partirá la nave con el envío.
     * @param salida Fecha de salida del envío.
     * @param destino Puerto al aue se envía el envío.
     * @param muelleDestino Muelle al que arribará la nave.
     * @param llegada Fecha estimada de llegada.
     * @param precio Coste del envío.
     */
    public Porte(String id, Nave nave, PuertoEspacial origen, int muelleOrigen, Fecha salida, PuertoEspacial destino, int muelleDestino, Fecha llegada, double precio) {
        this.id = id;
        this.nave = nave;
        this.origen = origen;
        this.muelleOrigen = muelleOrigen;
        this.salida = salida;
        this.destino = destino;
        this.muelleDestino = muelleDestino;
        this.llegada = llegada;
        this.precio = precio;
        huecos = new boolean [nave.getFilas()][nave.getColumnas()];
        listaEnvios = new ListaEnvios(nave.getColumnas()* nave.getFilas());
    }
    public String getID() {
        return id;
    }
    public Nave getNave(){
        return nave;
    }
    public PuertoEspacial getOrigen() {
        return origen;
    }
    public int getMuelleOrigen() {
        return muelleOrigen;
    }
    public Fecha getSalida(){
        return salida;
    }
    public PuertoEspacial getDestino() {
        return destino;
    }
    public int getMuelleDestino() {
        return muelleDestino;
    }
    public Fecha getLlegada() {
        return llegada;
    }
    public double getPrecio() {
        return precio;
    }
    // TODO: Devuelve el número de huecos libres que hay en el porte
    public int numHuecosLibres() {
        int libres = 0;
        for(int i = 0; i < huecos.length; i++){
            for (int j = 0; j < huecos[i].length; j++){
                if(!huecoOcupado(i,j)){
                    libres++;
                }
            }
        }
        return libres;
    }
    // TODO: ¿Están llenos todos los huecos?
    public boolean porteLleno() {
        return numHuecosLibres() == 0;
    }
    // TODO: ¿Está ocupado el hueco consultado?
    public boolean huecoOcupado(int fila, int columna) {return huecos[fila][columna];}
    public Envio buscarEnvio(String localizador) {
        return listaEnvios.buscarEnvio(localizador);
    }
    /**
     * TODO: Devuelve el objeto Envio que corresponde con una fila o columna,
     * @param fila Posición del envío.
     * @param columna Posición del envío.
     * @return el objeto Envio que corresponde, o null si está libre o se excede en el límite de fila y columna
     */
    public Envio buscarEnvio(int fila, int columna) {return listaEnvios.buscarEnvio(this.getID(), fila, columna);}
    /**
     * TODO: Método que Si está desocupado el hueco que indica el envio, lo pone ocupado y devuelve true,
     *  si no devuelve false
     * @param envio Envío que se pretende añadir.
     * @return True si lo puede incluir y False si no.
     */
    public boolean ocuparHueco(Envio envio) {
        boolean resul = false;
        if(!this.huecoOcupado(envio.getFila(), envio.getColumna())){
            huecos[envio.getFila()-1][envio.getColumna()-1] = true;
            resul = listaEnvios.insertarEnvio(envio);
        }
        return resul;
    }
    /**
     * TODO: A través del localizador del envio, se desocupará el hueco
     * @param localizador Identificador para encontrar el hueco que se quiere despejar.
     * @return True si lo consigue eliminar y False en caso contrario.
     */
    public boolean desocuparHueco(String localizador) {
        boolean resul = false;
        if(huecos[listaEnvios.buscarEnvio(localizador).getFila()][listaEnvios.buscarEnvio(localizador).getColumna()]){
            huecos[listaEnvios.buscarEnvio(localizador).getFila()][listaEnvios.buscarEnvio(localizador).getColumna()] = false;
            resul = listaEnvios.eliminarEnvio(localizador);
        }
        return resul;
    }
    /**
     * TODO: Devuelve una cadena con información completa del porte
     * @return ejemplo del formato -> "Porte PM0066 de Gaia Galactic Terminal(GGT) M5 (01/01/2023 08:15:00) a
     *  Cidonia(CID) M1 (01/01/2024 11:00:05) en Planet Express One(EP-245732X) por 13424,56 SSD, huecos libres: 10"
     */
    public String toString() {return "Porte " + this.getID() + " de " + this.getOrigen().toStringSimple() + this.getMuelleOrigen() + " " + this.getSalida().toString() + " a " + this.getDestino().toStringSimple() + " en Planet Expres One " + " (" + this.getID() + ") " + " por " + this.getPrecio() + " SSD, huecor libres:  " + this.numHuecosLibres();}
    /**
     * TODO: Devuelve una cadena con información abreviada del vuelo
     * @return ejemplo del formato -> "Porte PM0066 de GGT M5 (01/01/2023 08:15:00) a CID M1 (01/01/2024 11:00:05)"
     */
    public String toStringSimple() {return "Porte " + this.getID() + " de " + this.getOrigen().getCodigo() + " " + this.getMuelleOrigen() + " (" + this.getSalida().toString() + ") a " + this.getDestino().getCodigo() + " " + getMuelleDestino() + " (" + this.getLlegada().toString() + ")";}
    /**
     * TODO: Devuelve true si el código origen, destino y fecha son los mismos que el porte
     * @param codigoOrigen Código del puerto de origen.
     * @param codigoDestino Código del puerto de destino.
     * @param fecha Fecha de salida.
     * @return True si coinciden todos los prámetros y False si falla alguno.
     */
    public boolean coincide(String codigoOrigen, String codigoDestino, Fecha fecha) {
        return this.getOrigen().getCodigo().equals(codigoOrigen) && this.getDestino().getCodigo().equals(codigoDestino) && this.getSalida().coincide(fecha);
    }
    /**
     * TODO: Muestra la matriz de huecos del porte, ejemplo:
     *        A  B  C
     *      1[ ][ ][ ]
     *      2[X][X][X]
     *      3[ ][ ][ ]
     *      4[ ][X][ ]
     *     10[ ][ ][ ]
     */
    public void imprimirMatrizHuecos() {
        for(int i = 0; i < huecos.length; i++){
            System.out.print(i);
            for(int j = 0; j < huecos[i].length; j++){
                System.out.print("[");
                if(huecoOcupado(i, j)){
                    System.out.print("X");
                }else System.out.print(" ");
                System.out.print("]");
            }
            System.out.println();
        }
    }
    /**
     * TODO: Devuelve true si ha podido escribir en un fichero la lista de envíos del porte, siguiendo las indicaciones
     *  del enunciado
     * @param fichero Archivo en el que se quieren escribir los datos.
     * @return True si escribe correctamente, False si falla.
     */
    public boolean generarListaEnvios(String fichero) {
        boolean resultado;
        PrintWriter pw;
        Envio envio;
        try {
            pw = new PrintWriter(fichero);
            pw.println("--------------------------------------------------------------");
            pw.println("-------------- Lista de envíos del porte " + getID() + " --------------");
            pw.println("--------------------------------------------------------------");

            for (int i = 0; i < listaEnvios.getOcupacion(); i++) {
                for (int j = 0; j < listaEnvios.getOcupacion(); j++) {
                    if(listaEnvios.buscarEnvio(getID(), i, j) != null) {
                        pw.print(i+1);
                        pw.print((char)(j + 'A') + " ");
                        pw.print(listaEnvios.buscarEnvio(getID(), i, j).getCliente().toString());
                        pw.println();
                    } else {
                        pw.print(i+1);
                        pw.print((char)(j + 'A'));
                        pw.println();
                    }
                }
            }
            pw.close();
            resultado = true;
        } catch (FileNotFoundException e) {
            resultado = false;
            System.out.println("No se encontró el fichero " + fichero);
        } catch (Exception e){
            resultado = false;
            System.out.println("Error al generar la lista de envíos en el archivo " + fichero);
        }
        return resultado;
    }
    /**
     * TODO: Genera un ID de porte. Este consistirá en una cadena de 6 caracteres, de los cuales los dos primeros
     *  serán PM y los 4 siguientes serán números aleatorios.
     *  NOTA: Usar el objeto rand pasado como argumento para la parte aleatoria.
     * @param rand Objeto con funcionalidades para generar números aleatorios.
     * @return ejemplo -> "PM0123"
     */
    public static String generarID(Random rand) {
        return ("PM" + rand.nextInt(0, 9) + rand.nextInt(0, 9) + rand.nextInt(0, 9) + rand.nextInt(0, 9));
    }
    /**
     * TODO: Crea y devuelve un objeto Porte de los datos que selecciona el usuario de puertos espaciales
     *  y naves y la restricción de que no puede estar repetido el identificador, siguiendo las indicaciones
     *  del enunciado.
     *  La función solicita repetidamente los parametros hasta que sean correctos
     * @param teclado Objeto Scanner para pedir datos al usuario.
     * @param rand Objeto aleatorio con el que se consiguen códigos de porte.
     * @param puertosEspaciales Lista de puertos espaciales disponibles.
     * @param naves Lista de naves.
     * @param portes Lista de portes.
     * @return Porte con los datos adecuados. Null si se cancela.
     */
    public static Porte altaPorte(Scanner teclado, Random rand, ListaPuertosEspaciales puertosEspaciales, ListaNaves naves, ListaPortes portes) {
        Porte porte = null;
        String id;
        PuertoEspacial origen;
        int muelleOrigen = -1;
        PuertoEspacial destino = null;
        int muelleDestino = -1;
        Nave nave = null;
        Fecha salida = null;
        Fecha llegada = null;
        double precio = -1;
        do {
            id = generarID(rand);
        } while (portes.buscarPorte(id) != null);
        origen = puertosEspaciales.seleccionarPuertoEspacial(teclado, "Ingrese el código del puerto de origen: ");
        if(origen != null) {
            muelleOrigen = Utilidades.leerNumero(teclado, "Ingrese el muelle de origen (1 - " + origen.getMuelles() + "): ", 1, origen.getMuelles());
            if(muelleOrigen != -1) {
                destino = puertosEspaciales.seleccionarPuertoEspacial(teclado, "Ingrese el código del puerto de destino: ");
                if(destino != null) {
                    muelleDestino = Utilidades.leerNumero(teclado, "Ingrese terminal de de destino (1 - " + destino.getMuelles() + "): ", 1, destino.getMuelles());
                    if(muelleDestino != -1) {
                        naves.mostrarNaves();
                        nave = naves.seleccionarNave(teclado, "Ingrese matrícula de la nave: ", origen.distancia(destino));
                        if(nave != null) {
                            salida = Utilidades.leerFechaHora(teclado, "Introduzca la fecha de salida: ");
                            if(salida != null) {
                                do {
                                    llegada = Utilidades.leerFechaHora(teclado, "Introduzaca la fecha de llegada: ");
                                } while (llegada == null || llegada.anterior(salida)) ;
                                if(llegada != null) {
                                    precio = Utilidades.leerNumero(teclado, "Ingrese precio: ", 0.0, 999999999.99);
                                }
                            }
                        }
                    }
                }
            }
        }
        if(origen != null && muelleOrigen != -1 && destino != null && muelleDestino != -1 && llegada != null && salida != null && precio >= 0) {
            porte = new Porte(id, nave, origen, muelleOrigen, salida, destino, muelleDestino, llegada, precio);
            System.out.println("Porte " + porte.getID() + " creado con éxito.");
        }
        return porte;
    }
}