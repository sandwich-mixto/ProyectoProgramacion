import javax.imageio.IIOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Description of the class
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
     * @param capacidad
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
        boolean estaLlena = false;
        if (getOcupacion() == lista.length){
            estaLlena = true;
        }
        return estaLlena;
    }
	// TODO: Devuelve un puerto espacial dado un indice
    public PuertoEspacial getPuertoEspacial(int i) {
        PuertoEspacial puerto = null;
        for (i = 0; i < lista.length; i++){
            if (lista [i] != null){
                puerto = lista [i];
            }
        }
        return puerto;
    }

    /**
     * TODO: insertamos un Puerto espacial nuevo en la lista
     * @param puertoEspacial
     * @return true en caso de que se añada correctamente, false en caso de lista llena o error
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
     * @param codigo
     * @return Puerto espacial que encontramos o null si no existe
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
     * @param teclado
     * @param mensaje
     * @return
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
     * @param nombre
     * @return
     */
    public boolean escribirPuertosEspacialesCsv(String nombre) {
        PrintWriter pw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(nombre, false);
            pw = new PrintWriter(fw);
            for (int i = 0; i < lista.length; i++){
                pw.printf("%s;%s;%01.3f;%01.01f;%01.01f;%d", lista[i].getNombre(),lista[i].getCodigo(),lista[i].getRadio(),lista[i].getAzimut(),lista[i].getPolar(),lista[i].getMuelles());
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (pw != null){
                pw.close();
            }
            if (fw != null){
                try {
                    fw.close();
                } catch (IIOException e){
                    return false;
                } catch (IOException e) {
                    return false;
                }
            }
        }
    }


    /**
     * TODO: Genera una lista de PuertosEspaciales a partir del fichero CSV, usando el argumento como capacidad máxima
     *  de la lista
     * @param fichero
     * @param capacidad
     * @return
     */
    public static ListaPuertosEspaciales leerPuertosEspacialesCsv(String fichero, int capacidad) {
        ListaPuertosEspaciales listaPuertosEspaciales = new ListaPuertosEspaciales(capacidad);
        Scanner sc = null;
        FileReader fr = null;
        int i = 0;
        String linea = "";
        PuertoEspacial puertoEspacial;
        try {
            fr = new FileReader(fichero);
            sc = new Scanner(fr);
            sc.useDelimiter("; | \n");
            while (i < capacidad && sc.hasNext()){
                puertoEspacial = new PuertoEspacial(sc.next(), sc.next(), sc.nextDouble(), sc.nextDouble(), sc.nextDouble(), sc.nextInt());
                listaPuertosEspaciales.insertarPuertoEspacial(puertoEspacial);
            }
        } catch (Exception e) {
            return null;
        } finally {
            if (sc != null){
                sc.close();
            }
            if (fr != null){
                try {
                    fr.close();
                } catch (IOException e){
                    return null;
                }
            }
        }
        return listaPuertosEspaciales;
    }
}
