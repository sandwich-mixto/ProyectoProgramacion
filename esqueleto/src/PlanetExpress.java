import java.util.Random;
import java.util.Scanner;
/**
 * Clase principal de Planet Express App, la práctica de Taller de Programación
 * @author      Taller de Progamación
 * @version     1.0
 */
public class PlanetExpress {
    private final int maxPortes;
    private final int maxNaves;
    private final int maxClientes;
    private final int maxEnviosPorCliente;
    private ListaPuertosEspaciales listaPuertosEspaciales;
    private final int maxPuertosEspaciales;
    private ListaNaves listaNaves;
    private ListaClientes listaClientes;
    private ListaPortes listaPortes;
    /**
     * TODO: Rellene el constructor de la clase
     * @param maxPuertosEspaciales Máximo número de puertos espaciales que tendrá la lista de puertos espaciales de PlanetExpress App.
     * @param maxNaves Máximo número de naves que tendrá la lista de naves de PlanetExpress App.
     * @param maxPortes Máximo número de portes que tendrá la lista de portes de PlanetExpress App.
     * @param maxClientes Máximo número de clientes que tendrá la lista de clientes de PlanetExpress App.
     * @param maxEnviosPorCliente Máximo número de envíos por cliente.
     */
    public PlanetExpress(int maxPuertosEspaciales, int maxNaves, int maxPortes, int maxClientes, int maxEnviosPorCliente) {
        this.maxPuertosEspaciales = maxPuertosEspaciales;
        this.maxNaves = maxNaves;
        this.maxPortes = maxPortes;
        this.maxClientes = maxClientes;
        this.maxEnviosPorCliente = maxEnviosPorCliente;
    }
    /**
     * TODO: Metodo para leer los datos de los ficheros específicados en el enunciado y los agrega a
     *  la información de PlanetExpress (listaPuertosEspaciales, listaNaves, listaPortes, listaClientes)
     * @param ficheroPuertos Fichero del que se leerá los puertos espaciales.
     * @param ficheroNaves Archivo dedicado a las naves registradas.
     * @param ficheroPortes Fichero con los portes programados.
     * @param ficheroClientes Fichero con usuarios.
     * @param ficheroEnvios Fichero con los envíos contratados por cada cliente.
     */
    public void cargarDatos(String ficheroPuertos, String ficheroNaves, String ficheroPortes, String ficheroClientes, String ficheroEnvios) {
        this.listaPuertosEspaciales = ListaPuertosEspaciales.leerPuertosEspacialesCsv(ficheroPuertos, maxPuertosEspaciales);
        this.listaNaves = ListaNaves.leerNavesCsv(ficheroNaves, maxNaves);
        this.listaPortes = ListaPortes.leerPortesCsv(ficheroPortes, maxPortes, listaPuertosEspaciales, listaNaves);
        this.listaClientes = ListaClientes.leerClientesCsv(ficheroClientes, maxClientes, maxEnviosPorCliente);
        ListaEnvios.leerEnviosCsv(ficheroEnvios, listaPortes, listaClientes);
    }
    /**
     * TODO: Metodo para almacenar los datos de PlanetExpress en los ficheros .csv especificados
     *  en el enunciado de la práctica
     * @param ficheroPuertos Fichero para guardar puertos.
     * @param ficheroNaves Fichero para guardar naves.
     * @param ficheroPortes Fichero para guardar portes.
     * @param ficheroClientes Fichero para guardar clientes.
     * @param ficheroEnvios Fichero para guardar envíos.
     */
    public void guardarDatos(String ficheroPuertos, String ficheroNaves, String ficheroPortes, String ficheroClientes, String ficheroEnvios) {
        listaPuertosEspaciales.escribirPuertosEspacialesCsv(ficheroPuertos);
        listaNaves.escribirNavesCsv(ficheroNaves);
        listaPortes.escribirPortesCsv(ficheroPortes);
        listaClientes.escribirClientesCsv(ficheroClientes);
        for(int i = 0; i < listaPortes.getOcupacion(); i++){
            listaPortes.getPorte(i).generarListaEnvios(ficheroEnvios);
        }
    }
    public boolean maxPortesAlcanzado() {
        return listaPortes.estaLlena();
    }
    public boolean insertarPorte (Porte porte) {
        return listaPortes.insertarPorte(porte);
    }
    public boolean maxClientesAlcanzado() {
        return listaClientes.estaLlena();
    }
    public boolean insertarCliente (Cliente cliente) {
        return listaClientes.insertarCliente(cliente);
    }
    /**
     * TODO: Metodo para buscar los portes especificados tal y como aparece en el enunciado de la práctica,
     *  Devuelve una lista de los portes entre dos puertos espaciales con una fecha de salida solicitados por teclado
     *  al usuario en el orden y con los textos establecidos (tomar como referencia los ejemplos de ejecución en el
     *  enunciado de la prática)
     * @param teclado Objeto Scanner para leer la entrada.
     * @return ListaPortes con los portes que coinciden.
     */
    public ListaPortes buscarPorte(Scanner teclado) {
        ListaPortes resul = null;
        String codigoOrigen, codigoDestino = null;
        Fecha fecha = null;
        codigoOrigen = Utilidades.leerCadena(teclado, "Ingrese código del puerto de origen: ");
        if(codigoOrigen != null){
            codigoDestino = Utilidades.leerCadena(teclado, "Ingrese código del puerto de destino: ");
            if(codigoDestino != null){
                fecha = Utilidades.leerFecha(teclado, "Fecha de salida: ");
            }
        }
        if (fecha != null && codigoDestino != null && codigoOrigen != null) {
            resul = listaPortes.buscarPortes(codigoOrigen, codigoDestino, fecha);
        }
        return resul;
    }
    /**
     * TODO: Metodo para contratar un envio tal y como se indica en el enunciado de la práctica. Se contrata un envio para un porte
     *  especificado, pidiendo por teclado los datos necesarios al usuario en el orden y con los textos (tomar como referencia los
     *  ejemplos de ejecución en el enunciado de la prática)
     * @param teclado Objeto Scanner para pedir datos al usuario.
     * @param rand Objeto aleatorio.
     * @param porte Porte al que se quiere añadir el envío.
     */
    public void contratarEnvio(Scanner teclado, Random rand, Porte porte) {
        Cliente cliente;
        if (porte != null) {
                cliente = listaClientes.seleccionarCliente(teclado, "Email del cliente: ");
            if(cliente != null && !cliente.maxEnviosAlcanzado()){
                porte.ocuparHueco(Envio.altaEnvio(teclado, rand, porte, cliente));
            }
        }
    }
    /**
     * TODO Metodo statico con la interfaz del menú de entrada a la App.
     * Tiene que coincidir con las trazas de ejecución que se muestran en el enunciado
     * @param teclado Objeto Scanner para pedir datos al usuario.
     * @return Opción seleccionada.
     */
    public static int menu(Scanner teclado) {
        System.out.println("1. Alta de Porte\n2. Alta de Cliente\n3. Buscar Porte\n4. Mostrar envíos de un cliente\n5. Generar lista de envíos\n0. Salir");
        return Utilidades.leerNumero(teclado, "Seleccione opción: ", 0, 5);
    }
    /**
     * TODO: Método Main que carga los datos de los ficheros CSV pasados por argumento (consola) en PlanetExpress,
     *  llama iterativamente al menú y realiza la opción especificada hasta que se indique la opción Salir. Al finalizar
     *  guarda los datos de PlanetExpress en los mismos ficheros CSV.
     * @param args argumentos de la línea de comandos, recibe **10 argumentos** estrictamente en el siguiente orden:
     * 1. Número máximo de puertos espaciales que tendrá la lista de puertos espaciales de PlanetExpress App.
     * 2. Número máximo de naves que tendrá la lista de naves de PlanetExpress App.
     * 3. Número máximo de portes que tendrá la lita de portes de PlanetExpress App.
     * 4. Número máximo de clientes que tendrá la lista de clientes de PlanetExpress App.
     * 5. Número máximo de envíos por pasajero.
     * 6. Nombre del fichero CSV que contiene la lista de puertos espaciales de PlanetExpress App (tanto para lectura como escritura).
     * 7. Nombre del fichero CSV que contiene la lista de naves de PlanetExpress App (tanto para lectura como escritura).
     * 8. Nombre del fichero CSV que contiene la lista de portes de PlanetExpress App (tanto para lectura como escritura).
     * 9. Nombre del fichero CSV que contiene la lista de clientes de PlanetExpress App (tanto para lectura como escritura).
     * 10. Nombre del fichero CSV que contiene los envíos adquiridos en PlanetExpress App (tanto para lectura como escritura).
     * En el caso de que no se reciban exactamente estos argumentos, el programa mostrará el siguiente mensaje
     * y concluirá la ejecución del mismo: `Número de argumentos incorrecto`.
     */
    public static void main(String[] args) {
        char letra = ' ';
        String email, porte, fichero;
        Cliente cliente;
        Envio envio;
        ListaPortes coincidentes;
        if (args.length != 10) {
            System.out.println("Número de argumentos incorrecto");
            return;
        }
        int opcion;
        Scanner teclado = new Scanner(System.in);
        Random rand = new Random();
        PlanetExpress planetExpress = new PlanetExpress(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
        planetExpress.cargarDatos(args[5], args[6], args[7], args[8], args[9]);
        do {
            opcion = menu(teclado);
            switch (opcion) {
                case 1:     // TODO: Alta de Porte
                    if(!planetExpress.maxPortesAlcanzado()) {
                        planetExpress.insertarPorte(Porte.altaPorte(teclado, rand, planetExpress.listaPuertosEspaciales, planetExpress.listaNaves, planetExpress.listaPortes));
                    } else System.out.println("No se pueden dar de alta más vuelos. ");
                    break;
                case 2:     // TODO: Alta de Cliente
                    if(!planetExpress.maxClientesAlcanzado()){
                        planetExpress.insertarCliente(Cliente.altaCliente(teclado, planetExpress.listaClientes, planetExpress.maxEnviosPorCliente));
                    } else{
                        System.out.println("No se pueden añadir más usuarios. ");
                    }
                    break;
                case 3:     // TODO: Buscar Porte
                    Porte porteSeleccionado;
                    coincidentes =  planetExpress.buscarPorte(teclado);
                    do{
                        letra = Utilidades.leerLetra(teclado, "¿Comprar billete para un nuevo pasajero (n), o para uno ya existente (e)?", 'e', 'n');
                        switch (letra){
                            case 'n':
                                cliente = null;
                                if(!planetExpress.maxClientesAlcanzado()){
                                    cliente = Cliente.altaCliente(teclado, planetExpress.listaClientes, planetExpress.maxEnviosPorCliente);
                                    planetExpress.insertarCliente(cliente);
                                } else{
                                    System.out.println("No se pueden añadir más usuarios. ");
                                }
                            case 'e':
                                if(coincidentes != null ) {
                                    do {
                                        porteSeleccionado = planetExpress.listaPortes.seleccionarPorte(teclado, "Seleccione porte: ", "CANCELAR");
                                    } while (porteSeleccionado != null && !porteSeleccionado.porteLleno());
                                    planetExpress.contratarEnvio(teclado, rand, porteSeleccionado);
                                } else{
                                    System.out.println("Ningún porte reúne los requisitos de búsqueda. ");
                                }
                                break;
                            default:
                                System.out.println("El valor de entrada debe ser 'n' o 'e'. ");
                        }
                    } while (letra != 'e' && letra != 'n');
                    break;
                case 4:     // TODO: Listado de envíos de un cliente
                    do {
                        email = Utilidades.leerCadena(teclado, "Email del cliente: ");
                    }while (!email.equals("CANCELAR") && !Cliente.correctoEmail(email) && planetExpress.listaClientes.buscarClienteEmail(email) != null);
                    if (!email.equals("CANCELAR")){
                        cliente = planetExpress.listaClientes.buscarClienteEmail(email);
                        cliente.listarEnvios();
                        envio = cliente.seleccionarEnvio(teclado, "Seleccione un envío. ");
                        if(envio != null){
                            do {
                                letra = Utilidades.leerLetra(teclado, "¿Cancelar envío (c), o generar factura (f)?: ", 'c', 'f');
                                if (letra == 'c') {
                                    cliente.cancelarEnvio(envio.getLocalizador());
                                    cliente.buscarEnvio(envio.getLocalizador()).cancelar();
                                } else if (letra == 'f') {
                                    do {
                                        fichero = Utilidades.leerCadena(teclado, "Nombre del fichero: ");
                                    } while (!fichero.equals("CANCELAR") && !cliente.buscarEnvio(envio.getLocalizador()).generarFactura(fichero));
                                } else {
                                    System.out.println("La entrada debe ser 'c' o 'f'. ");
                                }
                            }while (letra != 'c' && letra != 'f');
                        }
                    }
                    break;
                case 5:     // TODO: Lista de envíos de un porte
                    planetExpress.listaPortes.listarPortes();
                    do {
                        porte = Utilidades.leerCadena(teclado, "Seleccione un porte: ");
                    }while (!porte.equals("CANCELAR") && planetExpress.listaPortes.buscarPorte(porte) != null);
                    if(!porte.equals("CANCELAR")){
                        do {
                            fichero = Utilidades.leerCadena(teclado, "Nombre del fichero: ");
                        }while (!fichero.equals("CANCELAR") && !planetExpress.listaPortes.buscarPorte(porte).generarListaEnvios(fichero));
                    }
                    break;
            }
        } while (opcion != 0);
    }
}