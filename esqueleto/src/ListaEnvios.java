import java.io.*;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author Jorge Jimenez Navas
 * @author Carlos Gonzalez Diaz
 * @version     1.0
 */
public class ListaEnvios {
    private Envio[] envios;
    /**
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad capacidad del array de envíos.
     */
    public ListaEnvios(int capacidad) {
        this.envios = new Envio[capacidad];
    }
    // TODO: Devuelve el número de envíos que hay en la lista
    public int getOcupacion() {
        int ocucpacion = 0;
        for (int i = 0; i < envios.length; i++) {
            if (envios[i] != null) {
                ocucpacion++;
            }
        }
        return ocucpacion;
    }

    // TODO: ¿Está llena la lista de envíos?
    public boolean estaLlena() {
        return this.getOcupacion() == envios.length;
    }
	//TODO: Devuelve el envio dado un indice
    public Envio getEnvio(int i) {
        return envios[i];
    }

    /**
     * TODO: insertamos un nuevo envío en la lista
     * @param envio envio que queremos insertar en la lista.
     * @return true en caso de que se añada correctamente, false en caso de lista llena o error.
     */
    public boolean insertarEnvio(Envio envio) {
        int posicion = 0;
        while ((envios[posicion] != null) && posicion < envios.length){
            posicion ++;
        }
        envios [posicion] = envio;
        return envios [posicion] == envio;
    }

    /**
     * TODO: Buscamos el envio a partir del localizador pasado como parámetro
     * @param localizador localizador del envío que buscamos.
     * @return el envío que encontramos o null si no existe.
     */
    public Envio buscarEnvio(String localizador) {
        int i = 0;
        boolean encontrado = false;
        while ((i < envios.length) && !encontrado){
            if (envios [i].getLocalizador().equals(localizador)){
                encontrado = true;
            }
            i++;
        }
        return envios[i];
    }

    /**
     * TODO: Buscamos el envio a partir del idPorte, fila y columna pasados como parámetros
     * @param idPorte el id del porte del envío que buscamos.
     * @param fila fila en la que se encuentra el envío que buscamos.
     * @param columna columna en la que se encuentra el envío que buscamos.
     * @return el envío que encontramos o null si no existe.
     */
    public Envio buscarEnvio(String idPorte, int fila, int columna) {
        int x = 0;
        boolean encontrado1 = false;
        boolean encontrado2 = false;
        while ((fila < envios.length) && !encontrado1){
                if (envios [x].getPorte().toString().equals(idPorte)){
                    encontrado1 = true;
                }
                x++;
                if (encontrado1){
                    while ((fila < envios [x].getFila() && columna < envios [x].getColumna()) && !encontrado2){
                        if (envios [x].getPorte().buscarEnvio(fila, columna) != null){
                            encontrado2 = true;
                        }
                    }
                }
            }
            return envios[x];
        }

    /**
     * TODO: Eliminamos un envio a través del localizador pasado por parámetro
     * @param localizador localizador del envío que queremos eliminar.
     * @return True si se ha borrado correctamente, false en cualquier otro caso.
     */
    public boolean eliminarEnvio (String localizador) {
        int i = 0;
        boolean encontrado = false;
        while ((i < envios.length) && !encontrado){
            if (envios[i].getLocalizador().equals(localizador)){
                encontrado = true;
            }
            i++;
        }
        if (encontrado) {
            envios[i] = envios[i + 1];
            return true;
        } else {
            return false;
        }
    }

    /**
     * TODO: Muestra por pantalla los Envios de la lista, con el formato que aparece
     * en el enunciado
     */
    public void listarEnvios() {
        int i = 0;
        while (envios[i] != null){
            System.out.println(envios[i].toString());
            i++;
        }
    }

    /**
     * TODO: Permite seleccionar un Envio existente a partir de su localizador, usando el mensaje pasado como argumento
     *  para la solicitud y siguiendo el orden y los textos mostrados en el enunciado.
     *  La función solicita repetidamente hasta que se introduzca un localizador correcto
     * @param teclado entrada principal por teclado.
     * @param mensaje salida principal que se muestra por pantalla.
     * @return devuelve el envío seleccionado en caso de que exista.
     */
    public Envio seleccionarEnvio(Scanner teclado, String mensaje) {
        Envio envio = null;
        do {
            System.out.println(mensaje);
            envio = buscarEnvio(teclado.nextLine());
        } while (envio.getLocalizador().equals(buscarEnvio(teclado.nextLine())));
        return envio;
    }

    /**
     * TODO: Añade los Envios al final de un fichero CSV, SIN SOBREESCRIBIR la información
     * @param fichero fichero en el que escribimos la informacion.
     * @return fichero con los envíos añadidos.
     */
    public boolean aniadirEnviosCsv(String fichero) {
        PrintWriter pw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(fichero, true);
            pw = new PrintWriter(fw);
            for (int i = 0; i < envios.length; i++){
                listarEnvios();
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
                }catch (IOException e){
                    return false;
                }
            }
        }
    }

    /**
     * TODO: Lee los Envios del fichero CSV y los añade a las listas de sus respectivos Portes y Clientes
     * @param ficheroEnvios lista de envíos del fichero CSV.
     * @param portes lista de portes.
     * @param clientes lista de clientes.
     */
    public static void leerEnviosCsv(String ficheroEnvios, ListaPortes portes, ListaClientes clientes) {
        Scanner sc = null;
        Porte porte;
        Envio envio;
        Cliente cliente;
        String linea ="";
        try {
            sc = new Scanner(ficheroEnvios);
            while (sc.hasNextLine()) {
                linea = sc.nextLine();
                cliente = clientes.buscarClienteEmail(linea.split(";")[2]);
                porte = portes.buscarPorte(linea.split(";")[1]);
                envio = new Envio(linea.split(";")[0], porte, cliente, Integer.parseInt(linea.split(";")[3]), Integer.parseInt(linea.split(";")[4]), Double.parseDouble(linea.split(";")[5]));
                cliente.aniadirEnvio(envio);
                porte.ocuparHueco(envio);
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("No se ha encontrado el fichero de envíos" + e.getMessage());
        }
        }
    }

