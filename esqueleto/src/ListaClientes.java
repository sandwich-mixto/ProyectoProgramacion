import java.io.*;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author Jorge Jimenez Navas
 * @author Carlos Gonzalez Diaz
 * @version     1.0
 */
public class ListaClientes {
    private Cliente[] clientes;

    /**
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad
     */
    public ListaClientes(int capacidad) {
        this.clientes = new Cliente[capacidad];
    }
    // TODO: Devuelve el número de clientes que hay en la lista de clientes
    public int getOcupacion() {
        int ocupacion = 0;
        for (int i = 0; i < clientes.length; i++){
            if (clientes[i] != null){
                ocupacion++;
            }
        }
        return ocupacion;
    }
    // TODO: ¿Está llena la lista de clientes?
    public boolean estaLlena() {
        return getOcupacion() == clientes.length;
    }
	// TODO: Devuelve el cliente dada el indice
    public Cliente getCliente(int i) {
        return clientes[i];
    }
    // TODO: Inserta el cliente en la lista de clientes
    public boolean insertarCliente(Cliente cliente) {
        int posicion = 0;
        while ((clientes [posicion] != null) && posicion < clientes.length){
            posicion ++;
        }
        clientes [posicion] = cliente;
        return clientes [posicion] == cliente;
    }
    // TODO: Devuelve el cliente que coincida con el email, o null en caso de no encontrarlo
    public Cliente buscarClienteEmail(String email) {
        boolean encontrado = false;
        int i = 0;
        while ((clientes[i] != null) && !encontrado){
            if (clientes [i].getEmail().equals(email)) {
                encontrado = true;
                i++;
            }
        }
        return clientes [i - 1];
    }

    /**
     * TODO: Método para seleccionar un Cliente existente a partir de su email, usando el mensaje pasado como argumento
     *  para la solicitud y, siguiendo el orden y los textos mostrados en el enunciado.
     *  La función debe solicitar repetidamente hasta que se introduzca un email correcto
     * @param teclado
     * @param mensaje
     * @return
     */
    public Cliente seleccionarCliente(Scanner teclado, String mensaje) {
        Cliente cliente = null;
        do {
            System.out.println(mensaje);
            cliente = buscarClienteEmail(teclado.nextLine());
        } while (cliente.getEmail().equals(buscarClienteEmail(teclado.nextLine())));
        return cliente;
    }

    /**
     * TODO: Método para guardar la lista de clientes en un fichero .csv, sobreescribiendo la información del mismo
     *  fichero
     * @param fichero
     * @return
     */
    public boolean escribirClientesCsv(String fichero) {
        PrintWriter pw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(fichero, false);
            pw = new PrintWriter(fw);
            for (int i = 0; i < clientes.length; i++){
                pw.println(clientes[i].getNombre() + ";" + clientes[i].getApellidos() + ";" + clientes[i].getEmail());
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    return false;
                }
            }
        }
    }

    /**
     * TODO: Genera una lista de Clientes a partir del fichero CSV, usando los límites especificados como argumentos
     *  para la capacidad de la lista y el número de billetes máximo por pasajero
     * @param fichero
     * @param capacidad
     * @param maxEnviosPorCliente
     * @return lista de clientes
     */
    public static ListaClientes leerClientesCsv(String fichero, int capacidad, int maxEnviosPorCliente) {
        ListaClientes listaClientes = new ListaClientes(capacidad);
        Scanner sc = null;
        FileReader fr = null;
        int i = 0;
        String linea = "";
        Cliente cliente;
        try {
            fr = new FileReader(fichero);
            sc = new Scanner(fr);
            sc.useDelimiter("; | \n");
            while (i < capacidad && sc.hasNext()){
                cliente = new Cliente(sc.next(), sc.next(), sc.next(), sc.nextInt());
                listaClientes.insertarCliente(cliente);
            }

        } catch (FileNotFoundException e) {
            return null;
        } finally {
            if (sc != null){
                sc.close();
            }
            if (fr != null){
                try{
                    fr.close();
                } catch (IOException e ){
                    return null;
                }
            }
        }
        return listaClientes;
    }
}