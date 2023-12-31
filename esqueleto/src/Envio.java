import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
/**
 * Description of the class: Clase equivalente a un envío.
 * Clase que representa un envío con su localizador, porte, cliente, posición y precio.
 * @author Carlos Gonzalez Diaz.
 * @author Jorge Jiménez Navas
 * @version     1.0
 */
public class Envio {
    private String localizador;
    private Porte porte;
    private Cliente cliente;
    private int fila;
    private int columna;
    private double precio;
    /**
     * Constructor of the class
     *
     * @param localizador Identificador único para cada envío.
     * @param porte Vuelo en el que se enviará.
     * @param cliente Usuario que contrató el transporte.
     * @param fila Posición en la bodega de carga en la que se guardará.
     * @param columna Posición en la bodega de carga en la que se guardará.
     * @param precio Cantidad a pagar por el cliente.
     */
    public Envio(String localizador, Porte porte, Cliente cliente, int fila, int columna, double precio) {
        this.localizador = localizador;
        this.porte = porte;
        this.cliente = cliente;
        this.fila = fila;
        this.columna = columna;
        this.precio = precio;
    }
    public String getLocalizador() {
        return localizador;
    }
    public Porte getPorte() {
        return porte;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public int getFila() {
        return fila;
    }
    public int getColumna() {
        return columna;
    }
    // TODO: Ejemplos: "1A" para el hueco con fila 1 y columna 1, "3D" para el hueco con fila 3 y columna 4
    public String getHueco() {
        return (Integer.toString(fila) + (char)('A' + columna));
    }
    public double getPrecio() {
        return precio;
    }
    //TODO: Texto que debe generar: Envío PM1111AAAABBBBC para Porte PM0066 de GGT M5 (01/01/2023 08:15:00) a CID M1 (01/01/2024 11:00:05) en hueco 6C por 13424,56 SSD
    public String toString() {
        return  "Envío " + this.getLocalizador() + " para Porte " + this.getPorte().toString();
    }
    // TODO: Cancela este envío, eliminándolo de la lista de envíos del porte y del cliente correspondiente
    public boolean cancelar() {
        return porte.desocuparHueco(localizador);
    }
    /**
     * TODO: Método para imprimir la información de este envío en un fichero que respecta el formato descrito en el
     *  enunciado
     * @param fichero Archivo en el que se quiere guardar la factura.
     * @return Devuelve la información con el siguiente formato como ejemplo ->
     *     -----------------------------------------------------
     *     --------- Factura del envío PM1111AAAABBBBC ---------
     *     -----------------------------------------------------
     *     Porte: PM0066
     *     Origen: Gaia Galactic Terminal (GGT) M5
     *     Destino: Cidonia (CID) M1
     *     Salida: 01/01/2023 08:15:00
     *     Llegada: 01/01/2024 11:00:05
     *     Cliente: Zapp Brannigan, zapp.brannigan@dop.gov
     *     Hueco: 6C
     *     Precio: 13424,56 SSD
     */
    public boolean generarFactura(String fichero) {
        PrintWriter pw;
        boolean resul;
        try {
            pw = new PrintWriter(fichero);
            pw.println("-----------------------------------------------------");
            pw.println("--------- Factura del envío " + localizador + " ---------");
            pw.println("-----------------------------------------------------");
            pw.println("Porte: " + porte.getID());
            pw.println("Origen: " + porte.getOrigen().toStringSimple());
            pw.println("Destino: " + porte.getDestino().toStringSimple());
            pw.println("Salida: " + porte.getSalida().toString());
            pw.println("LLegada: " + porte.getLlegada().toString());
            pw.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellidos() + ", " + cliente.getEmail());
            pw.println("Hueco: " + this.getHueco());
            pw.println("Precio: " + precio + " SSD");
            pw.close();
            resul =  true;
        } catch (FileNotFoundException e) {
            resul = false;
        }
        return resul;
    }
    /**
     *	TODO: Genera un localizador de envío. Este consistirá en una cadena de 15 caracteres, de los cuales los seis
	 *   primeros será el ID del porte asociado y los 9 siguientes serán letras mayúsculas aleatorias. Ejemplo: PM0123ABCD
     *   NOTA: Usar el objeto rand pasado como argumento para la parte aleatoria.
     * @param rand Objeto con funcionalidades aleatorias.
     * @param idPorte Identificador del porte en el que basará el localizador.
     * @return String localizador.
     */
    public static String generarLocalizador(Random rand, String idPorte) {
        StringBuilder localizador = new StringBuilder(idPorte);
        for(int i = 0; i < 9; i++){
            localizador.append((char)rand.nextInt('A', 'Z'));
        }
        return localizador.toString();
    }
    /**
     * TODO: Método para crear un nuevo envío para un porte y cliente específico, pidiendo por teclado los datos
     *  necesarios al usuario en el orden y con los textos indicados en los ejemplos de ejecución del enunciado
     *  La función solicita repetidamente los parámetros hasta que sean correctos
     * @param teclado Objeto Scanner.
     * @param rand Objeto aleatorio.
     * @param porte Porte al que se quiere añadir el envío.
     * @param cliente Usuario que realiza el envío.
     * @return Envio para el porte y cliente especificados. Null si se cancela la operación.
     */
    public static Envio altaEnvio(Scanner teclado, Random rand, Porte porte, Cliente cliente) {
        Envio envio = null;
        int fila = -1, columna = -1;
        double precio;
        porte.imprimirMatrizHuecos();
        do {
            fila = Utilidades.leerNumero(teclado, "Fila del hueco: ", 1, porte.getNave().getFilas());
            if(fila != -1){
                columna = Utilidades.leerNumero(teclado, "Columna del hueco: ", 1, porte.getNave().getColumnas());
            }
        }while(porte.huecoOcupado(fila, columna) && columna != -1 && fila != -1);
        precio = Utilidades.leerNumero(teclado, "Precio del envío: ", 1.00, 999999999.99);
        if(precio != -1 && columna != -1 && fila != -1){
            String localizador = generarLocalizador(rand, porte.getID());
            envio = new Envio(localizador, porte, cliente, fila-1, columna-1, precio);
            cliente.aniadirEnvio(envio);
            System.out.println("Envío " + localizador + " creado correctamente. ");
        }
        return envio;
    }
}