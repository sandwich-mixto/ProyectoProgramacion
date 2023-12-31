import java.io.*;
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
            if(lista[i] != null && lista[i].getCodigo().equals(codigo)){
                encontrado = true;
                res = lista[i];
            } else i++;
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
        String cadena;
        do{
            cadena = Utilidades.leerCadena(teclado, mensaje);
            puertoEspacial = buscarPuertoEspacial(cadena);
            if(puertoEspacial == null && !cadena.equals("CANCELAR")){
                System.out.println("Puerto espacial no encontrado. ");
            }
        }while (!cadena.equals("CANCELAR") && puertoEspacial == null);
        return puertoEspacial;
    }
    /**
     * TODO: Genera un fichero CSV con la lista de puertos espaciales, SOBREESCRIBIENDOLO
     * @param nombre Nombre del fichero donde se guardarán los datos.
     * @return True si funciona correctamente. False si falla.
     */
    public boolean escribirPuertosEspacialesCsv(String nombre) {
        boolean resultado;
        PrintWriter salida;
        PuertoEspacial puerto;
        try {
            salida = new PrintWriter(new File(nombre));
            for (int i = 0; i < lista.length; i++){
                puerto = getPuertoEspacial(i);
                if(puerto != null) {
                    salida.println(puerto.getNombre() + ";" + puerto.getCodigo() + ";" + puerto.getRadio() + ";" + puerto.getAzimut() + ";" + puerto.getPolar() + ";" + puerto.getMuelles());
                }
            }
            resultado = true;
            salida.close();
        } catch (FileNotFoundException e){
            System.out.println("No se encontró el fichero " + nombre);
            resultado = false;
        } catch (Exception e) {
            System.out.println("Error en la escritura de " + nombre);
            resultado = false;
        }
        return resultado;
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
        Scanner entrada;
        String linea;
        String [] argumentos;
        PuertoEspacial puertoEspacial;
        try {
            entrada = new Scanner(new File(fichero));
            while (!listaPuertosEspaciales.estaLlena() && entrada.hasNext()){
                linea = entrada.nextLine();
                argumentos = linea.split(";");
                puertoEspacial = new PuertoEspacial(argumentos[0], argumentos[1], Double.parseDouble(argumentos[2]), Double.parseDouble(argumentos[3]), Double.parseDouble(argumentos[4]), Integer.parseInt(argumentos[5]));
                listaPuertosEspaciales.insertarPuertoEspacial(puertoEspacial);
            }
            entrada.close();
        }catch (FileNotFoundException e){
            listaPuertosEspaciales = new ListaPuertosEspaciales(capacidad);
            System.out.println("Fichero " + fichero + " no encontrado. ");
        }
        catch (Exception e) {
            listaPuertosEspaciales = new ListaPuertosEspaciales(capacidad);
            System.out.println("Error en la lectura del fichero. ");
        }
        return listaPuertosEspaciales;
    }
}