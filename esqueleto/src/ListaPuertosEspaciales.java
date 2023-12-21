import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
/**
 * Description of the class: Lista que contiene los puertos espaciales.
 *
 * @author Jorge Jiménez Navas
 * @author Carlos Gonzalez Diaz
 * @version     1.0
 */
public class ListaPuertosEspaciales {
    private PuertoEspacial[] lista;
    /**
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad Número máximo de puertos que se aceptarán.
     */
    public ListaPuertosEspaciales(int capacidad) {
        this.lista = new PuertoEspacial[capacidad];

    }
    // TODO: Devuelve el número de puertos espaciales que hay en la lista
    public int getOcupacion() {
        int ocupacion = 0;
        for (int i = 0; i < lista.length; i++){
            if (lista[i] != null){
                ocupacion++;
            }
        }
        return ocupacion;
    }
    // TODO: ¿Está llena la lista?
    public boolean estaLlena() {
        return getOcupacion() == lista.length;
    }
	// TODO: Devuelve un puerto espacial dado un indice
    public PuertoEspacial getPuertoEspacial(int i) {
        return lista[i];
    }
    /**
     * TODO: insertamos un Puerto espacial nuevo en la lista
     * @param puertoEspacial Puerto espacial que se desea añadir.
     * @return true en caso de que se añada correctamente, false en caso de lista llena o error.
     */
    public boolean insertarPuertoEspacial(PuertoEspacial puertoEspacial) {
        boolean res = false;
        if (!estaLlena()){
            lista[getOcupacion()] = puertoEspacial;
            res = true;
        }
        return res;
    }

    /**
     * TODO: Buscamos un Puerto espacial a partir del codigo pasado como parámetro
     * @param codigo Designador del puerto espacial que se quiere obtener.
     * @return PuertoEspacial que buscamos o null si no existe.
     */
    public PuertoEspacial buscarPuertoEspacial(String codigo) {
        PuertoEspacial res = null;
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < lista.length){
            encontrado = lista [i].getCodigo().equals(codigo);
            i++;
        }
        if (encontrado){
            res = lista [i - 1];
        }
        return res;
    }

    /**
     * TODO: Permite seleccionar un puerto espacial existente a partir de su código, usando el mensaje pasado como
     *  argumento para la solicitud y siguiendo el orden y los textos mostrados en el enunciado.
     *  La función solicita repetidamente el código hasta que se introduzca uno correcto
     * @param teclado Objeto Scanner para pedir datos al usuario.
     * @param mensaje Mensaje que se mostrará por pantalla al pedir datos.
     * @return PuertoEspacial buscado con el código.
     */
    public PuertoEspacial seleccionarPuertoEspacial(Scanner teclado, String mensaje) {
        PuertoEspacial puertoEspacial = null;
        boolean encontrado = false;
        String codigo = "";
        while (!encontrado){
            System.out.println(mensaje);
            codigo = teclado.next();
            puertoEspacial = buscarPuertoEspacial(codigo);
            encontrado = puertoEspacial != null;
            if (!encontrado){
                System.out.println("Código de puerto no encontrado");
            }
        }
        return puertoEspacial;
    }
    /**
     * TODO: Genera un fichero CSV con la lista de puertos espaciales, SOBREESCRIBIENDOLO
     * @param nombre Nombre del fichero donde se guardarán los datos.
     * @return True si funciona correctamente. False si falla.
     */
    public boolean escribirPuertosEspacialesCsv(String nombre) {
        PrintWriter pw = null;
        FileWriter fw = null;
        boolean salir = false;
        try {
            fw = new FileWriter(nombre, false);
            pw = new PrintWriter(fw);
            for (int i = 0; i < lista.length; i++){
                pw.printf("%s;%s;%01.3f;%01.01f;%01.01f;%d", lista[i].getNombre(),lista[i].getCodigo(),lista[i].getRadio(),lista[i].getAzimut(),lista[i].getPolar(),lista[i].getMuelles());
            }
            pw.close();
            fw.close();
        } catch (Exception e) {
            salir = false;
        }
        return salir;
    }
    /**
     * TODO: Genera una lista de PuertosEspaciales a partir del fichero CSV, usando el argumento como capacidad máxima
     *  de la lista
     * @param fichero Fichero del que se obtendrán los datos.
     * @param capacidad Longitud de la lista para no añadir más puertos de los permitidos.
     * @return ListaPuertosEspaciales creada con los datos del fichero.
     */
    public static ListaPuertosEspaciales leerPuertosEspacialesCsv(String fichero, int capacidad) {
        ListaPuertosEspaciales listaPuertosEspaciales = new ListaPuertosEspaciales(capacidad);
        PuertoEspacial puertoEspacial;
        String [] linea;
        try {
            Scanner sc = new Scanner(fichero);
            while (listaPuertosEspaciales.getOcupacion() < capacidad && sc.hasNext()){
                linea = sc.nextLine().split(";");
                puertoEspacial = new PuertoEspacial(sc.next(), sc.next(), sc.nextDouble(), sc.nextDouble(), sc.nextDouble(), sc.nextInt());
                listaPuertosEspaciales.insertarPuertoEspacial(puertoEspacial);
            }
            sc.close();
        } catch (Exception e) {
            listaPuertosEspaciales = new ListaPuertosEspaciales(capacidad);
        }
        return listaPuertosEspaciales;
    }
}