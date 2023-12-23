import java.io.*;
import java.util.Scanner;

/**
 * Description of the class: Lista que guarda una serie de envíos.
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
        Envio envio = null;
        while ((i < envios.length) && !encontrado){
            if (envios[i] != null && envios[i].getLocalizador().equals(localizador)){
                encontrado = true;
                envio = envios[i];
            } else i++;
        }
        return envio;
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
        boolean encontrado = false;
        Envio resultado = null;
        while(x < envios.length && !encontrado){
            if(envios[x] != null && envios[x].getPorte().getID().equals(idPorte) && envios[x].getFila() == fila && envios[x].getColumna() == columna){
                encontrado = true;
                resultado = envios[x];
            } else x++;
        }
        return resultado;
        }

    /**
     * TODO: Eliminamos un envio a través del localizador pasado por parámetro
     * @param localizador localizador del envío que queremos eliminar.
     * @return True si se ha borrado correctamente, false en cualquier otro caso.
     */
    public boolean eliminarEnvio (String localizador) {
        int i = 0;
        boolean encontrado = false;
        while (i < envios.length && !encontrado){
            if (envios[i] != null && envios[i].getLocalizador().equals(localizador)){
                encontrado = true;
                envios[i] = null;
            } else i++;
        }
        return encontrado;
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
        String cadena;
        do {
            cadena = Utilidades.leerCadena(teclado, mensaje);
            if(!cadena.equals("CANCELAR")) {
                envio = buscarEnvio(cadena);
            }
        } while (!cadena.equals("CANCELAR") && envio == null);
        return envio;
    }

    /**
     * TODO: Añade los Envios al final de un fichero CSV, SIN SOBREESCRIBIR la información
     * @param fichero fichero en el que escribimos la informacion.
     * @return fichero con los envíos añadidos.
     */
    public boolean aniadirEnviosCsv(String fichero) {
        boolean resultado;
        PrintWriter salida;
        Envio envio;
        try {
            salida = new PrintWriter(fichero);
            for(int i = 0; i < envios.length; i++){
                envio = envios[i];
                if(envio != null){
                    salida.println(envio.getLocalizador() + ";" + envio.getPorte().getID() + ";" + envio.getCliente().getEmail() + ";" + envio.getFila() + ";" + envio.getColumna() + ";" + envio.getPrecio());
                }
            }
            salida.close();
            resultado = true;
        } catch (FileNotFoundException e){
            resultado = false;
            System.out.println("No se encontró el fichero " + fichero);
        } catch (Exception e) {
            resultado = false;
            System.out.println("Error en la escritura de " + fichero);
        }
        return resultado;
    }

    /**
     * TODO: Lee los Envios del fichero CSV y los añade a las listas de sus respectivos Portes y Clientes
     * @param ficheroEnvios lista de envíos del fichero CSV.
     * @param portes lista de portes.
     * @param clientes lista de clientes.
     */
    public static void leerEnviosCsv(String ficheroEnvios, ListaPortes portes, ListaClientes clientes) {
        Scanner entrada;
        Porte porte;
        Envio envio;
        Cliente cliente;
        String linea;
        String [] argumentos;
        try {
            entrada = new Scanner(new File(ficheroEnvios));
            while (entrada.hasNextLine() && !portes.estaLlena() && !clientes.estaLlena()) {
                linea = entrada.nextLine();
                argumentos = linea.split(";");
                cliente = clientes.buscarClienteEmail(argumentos[2]);
                porte = portes.buscarPorte(argumentos[1]);
                envio = new Envio(argumentos[0], porte, cliente, Integer.parseInt(argumentos[3]), Integer.parseInt(argumentos[4]), Double.parseDouble(argumentos[5]));
                cliente.aniadirEnvio(envio);
                porte.ocuparHueco(envio);
            }
            entrada.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado el fichero " + ficheroEnvios);
        } catch (Exception e){
            System.out.println("Error en la lectura de " + ficheroEnvios);
        }
    }
}

