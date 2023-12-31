import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
/**
 * Description of the class: Lista para organizar las naves que posee la empresa.
 *
 * @author Carlos Gonzalez Diaz
 * @author Jorge Jiménez Navas
 * @version     1.0
 */
public class ListaNaves {
    private Nave[] naves;
    /**
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     * @param capacidad Tamaño máximo de la flota.
     */
    public ListaNaves(int capacidad) {
        this.naves = new Nave[capacidad];
    }
    // TODO: Devuelve el número de naves que hay en la lista
    public int getOcupacion() {
        int total = 0;
        for(int i = 0; i < naves.length; i++){
            if(naves[i] != null){
                total++;
            }
        }
        return total;
    }
    // TODO: ¿Está llena la lista de naves?
    public boolean estaLlena() {
        return this.getOcupacion() == naves.length;
    }
	// TODO: Devuelve nave dado un indice
    public Nave getNave(int posicion) {return naves[posicion];}
    /**
     * TODO: insertamos una nueva nave en la lista
     * @param nave Nave que se quiere añadir.
     * @return true en caso de que se añada correctamente, false en caso de lista llena o error
     */
    public boolean insertarNave(Nave nave) {
        boolean resul = false;
        int posicion = 0;
        while (naves[posicion] != null && posicion < naves.length){
            posicion++;
        }
        if(posicion < naves.length){
            naves[posicion] = nave;
            resul = (naves[posicion] == nave);
        }
        return resul;
    }
    /**
     * TODO: Buscamos la nave a partir de la matricula pasada como parámetro
     * @param matricula Matrícula de la nave que se quiere obtener.
     * @return Primera nave con esa matrícula o null si no existe.
     */
    public Nave buscarNave(String matricula) {
        int i = 0;
        boolean encontrado = false;
        Nave resul = null;
        if(matricula != null && !matricula.equals("CANCELAR")){
            while(i < naves.length && !encontrado){
                if(naves[i] != null && naves[i].getMatricula().equals(matricula)){
                    encontrado = true;
                } else i++;
            }
            if(encontrado){
                resul = naves[i];
            }
        }
        return resul;
    }
    // TODO: Muestra por pantalla las naves de la lista con el formato indicado en el enunciado
    public void mostrarNaves() {
        for(int i = 0; i < naves.length; i++){
            if(naves[i] != null){
                System.out.println(this.getNave(i).toString());
            }
        }
    }
    /**
     * TODO: Permite seleccionar una nave existente a partir de su matrícula, y comprueba si dispone de un alcance
     *  mayor o igual que el pasado como argumento, usando el mensaje pasado como argumento para la solicitud y
     *  siguiendo el orden y los textos mostrados en el enunciado.
     *  La función solicita repetidamente la matrícula de la nave hasta que se introduzca una con alcance suficiente
     * @param teclado Objeto Scanner para leer la entrada.
     * @param mensaje Texto impreso por pantalla con instrucciones para el usuario.
     * @param alcance Alcance mínimo que debe tener la nave seleccionada.
     * @return Nave con suficiente alcance a partir de la matrícula o null.
     */
    public Nave seleccionarNave(Scanner teclado, String mensaje, double alcance) {
        Nave nave;
        String cadena;
        do{
            cadena = Utilidades.leerCadena(teclado, mensaje);
            nave = buscarNave(cadena);
        } while(!cadena.equals("CANCELAR") && nave != null && nave.getAlcance() < alcance);
        return nave;
    }
    /**
     * TODO: Genera un fichero CSV con la lista de Naves, SOBREESCRIBIÉNDOLO
     * @param nombre Nombre del fichero en el que se quiere escribir.
     * @return True si consigue escribir correctamente.
     */
    public boolean escribirNavesCsv(String nombre) {
        PrintWriter pw;
        boolean resul = true;
        try {
            pw = new PrintWriter(nombre);
            for(int i = 0; i < naves.length; i++){
                if(naves[i] != null){
                    pw.println(naves[i].getMarca() + ";" + naves[i].getModelo() + ";" + naves[i].getMatricula() + ";" + naves[i].getFilas() + ";" + naves[i].getColumnas() + ";" + naves[i].getAlcance());
                }
            }
            pw.close();
        } catch (Exception e) {
            resul = false;
        }
        return resul;
    }
    /**
     * TODO: Genera una lista de naves a partir del fichero CSV, usando el argumento como capacidad máxima de la lista
     * @param fichero Fichero del que se quieren leer los datos de las naves.
     * @param capacidad Cantidad máxima de naves que guardará la lista.
     * @return ListaNaves con las naves leidas del fichero o null.
     */
    public static ListaNaves leerNavesCsv(String fichero, int capacidad) {
        ListaNaves listaNaves = new ListaNaves(capacidad);
        Scanner entrada;
        String linea;
        Nave nave;
        try {
            entrada = new Scanner(new File(fichero));
            while (entrada.hasNextLine() && !listaNaves.estaLlena()){
                linea = entrada.nextLine();
                nave = new Nave(linea.split(";")[0], linea.split(";")[1], linea.split(";")[2], Integer.parseInt(linea.split(";")[3]), Integer.parseInt(linea.split(";")[4]), Double.parseDouble(linea.split(";")[5]));
                listaNaves.insertarNave(nave);
            }
            entrada.close();
        }catch (FileNotFoundException e){
            listaNaves = new ListaNaves(capacidad);
            System.out.println("Fichero " + fichero  +" no encontrado. ");
        }
        catch (Exception e) {
            listaNaves = new ListaNaves(capacidad);
            System.out.println("Error en la lectura de " + fichero);
        }
        return listaNaves;
    }
}