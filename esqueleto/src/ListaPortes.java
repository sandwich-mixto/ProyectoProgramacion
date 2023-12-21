import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
/**
 * Description of the class
 * @author Carlos Gonzalez DIaz.
 * @author Jorge Jiménez Navas
 * @version     1.0
 */
public class ListaPortes {
    private Porte[] portes;
    /**
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     * @param capacidad Capacidad máxima de la lista de portes.
     */
    public ListaPortes(int capacidad) {
        this.portes = new Porte[capacidad];
    }
    // TODO: Devuelve el número de portes que hay en la lista
    public int getOcupacion() {
        int ocupacion = 0;
        for(int i = 0; i < portes.length; i++){
            if(portes[i] != null){
                ocupacion++;
            }
        } return ocupacion;
    }
    // TODO: ¿Está llena la lista?
    public boolean estaLlena() {return this.getOcupacion() == this.portes.length;}
	//TODO: devuelve un porte dado un indice
    public Porte getPorte(int i) {
        return portes[i];
    }
    /**
     * TODO: Devuelve true si puede insertar el porte
     * @param porte Porte a insertar.
     * @return false en caso de estar llena la lista o de error
     */
    public boolean insertarPorte(Porte porte) {
        boolean encontrado = false;
        int i = 0;
        while (i < portes.length && !encontrado){
            if(portes[i] == null){
                portes[i] = porte;
                encontrado = true;
            } else i++;
        }
        return encontrado;
    }
    /**
     * TODO: Devuelve el objeto Porte que tenga el identificador igual al parámetro id
     * @param id Identificador del porte que se quiere obtener.
     * @return el objeto Porte que encontramos o null si no existe
     */
    public Porte buscarPorte(String id) {
        Porte porte = null;
        int i = 0;
        while (i < portes.length && porte == null){
            if(this.getPorte(i) != null && this.getPorte(i).getID().equals(id)){
                porte = this.getPorte(i);
            }
            i++;
        }
        return porte;
    }
    /**
     * TODO: Devuelve un nuevo objeto ListaPortes conteniendo los Portes que vayan de un puerto espacial a otro
     *  en una determinada fecha
     * @param codigoOrigen Código del puerto de partida.
     * @param codigoDestino Código del puerto de llegada.
     * @param fecha Fecha de salida.
     * @return ListaPortes con los portes que cumplan los requisitos.
     */
    public ListaPortes buscarPortes(String codigoOrigen, String codigoDestino, Fecha fecha) {
        ListaPortes listaPortes = new ListaPortes(portes.length);
        for(int i = 0; i < portes.length; i++){
            if(this.getPorte(i) != null && this.getPorte(i).coincide(codigoOrigen, codigoDestino, fecha)){
                listaPortes.insertarPorte(this.getPorte(i));
            }
        }
        return listaPortes;
    }
    /**
     * TODO: Muestra por pantalla los Portes siguiendo el formato de los ejemplos del enunciado
     */
    public void listarPortes() {
        for(int i = 0; i < portes.length; i++){
            if(this.getPorte(i) != null){
                System.out.println(this.getPorte(i).toString());
            }
        }
    }
    /**
     * TODO: Permite seleccionar un Porte existente a partir de su ID, usando el mensaje pasado como argumento para
     *  la solicitud y siguiendo el orden y los textos mostrados en el enunciado, y usando la cadena cancelar para
     *  salir devolviendo null.
     *  La función solicita repetidamente hasta que se introduzca un ID correcto
     * @param teclado Objeto Scanner para pedir datos al usuario.
     * @param mensaje Mensaje mostrado en pantalla para informar al cliente sobre los datos requeridos.
     * @param cancelar Palabra clave para cortar la ejecución del método.
     * @return Porte.
     */
    public Porte seleccionarPorte(Scanner teclado, String mensaje, String cancelar) {
        listarPortes();
        Porte porte = null;
        String cadena;
        do {
            cadena = Utilidades.leerCadena(teclado, mensaje);
        }while (!cadena.equals(cancelar) && buscarPorte(cadena) == null);
        if(!cadena.equals(cancelar)){
            porte = buscarPorte(cadena);
        }
        return porte;
    }
    /**
     * TODO: Ha de escribir la lista de Portes en la ruta y nombre del fichero pasado como parámetro.
     *  Si existe el fichero, SE SOBREESCRIBE, si no existe se crea.
     * @param fichero Archivo en el que se guardarán los portes.
     * @return True si consigue escribir, false si falla.
     */
    public boolean escribirPortesCsv(String fichero) {
        try {
            PrintWriter pw = new PrintWriter(fichero);
            Porte porte;
            for(int i = 0; i < portes.length; i++){
                porte = this.getPorte(i);
                if(porte != null){
                    pw.println(porte.getID() + ";" + porte.getNave().getMatricula() + ";" + porte.getOrigen().getCodigo() + ";" + porte.getMuelleOrigen() + ";" + porte.getSalida().toString() + ";" + porte.getDestino().getCodigo() + ";" + porte.getMuelleDestino() + ";" + porte.getLlegada().toString() + ";" + porte.getPrecio());
                }
            }
            pw.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }
    /**
     * TODO: Genera una lista de Portes a partir del fichero CSV, usando los límites especificados como argumentos para
     *  la capacidad de la lista
     * @param fichero Archivo que se leerá.
     * @param capacidad Número máximo de portes que puede albergar el sistema.
     * @param puertosEspaciales ListaPuertosEspaciales con los puertos espaciales existentes.
     * @param naves ListaNaves con las naves disponibles.
     * @return ListaPortes con los portes leídos o null si da error.
     */
    public static ListaPortes leerPortesCsv(String fichero, int capacidad, ListaPuertosEspaciales puertosEspaciales, ListaNaves naves) {
        ListaPortes listaPortes = new ListaPortes(capacidad);
        Porte porte;
        String[] linea;
        try {
            int i = 0;
            Scanner sc = new Scanner(fichero);
            while (sc.hasNextLine() && i < capacidad){
                i++;
                linea = sc.nextLine().split(";");
                porte = new Porte(linea[0], naves.buscarNave(linea[1]), puertosEspaciales.buscarPuertoEspacial(linea[2]), Integer.parseInt(linea[3]), Fecha.fromString(linea[4]), puertosEspaciales.buscarPuertoEspacial(linea[5]), Integer.parseInt(linea[6]), Fecha.fromString(linea[7]), Double.parseDouble(linea[8]));
                listaPortes.insertarPorte(porte);
            }
            sc.close();
        } catch (Exception e) {
            listaPortes = new ListaPortes(capacidad);
        }
        return listaPortes;
    }
}