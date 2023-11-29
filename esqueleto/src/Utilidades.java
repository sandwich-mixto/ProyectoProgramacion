import java.util.Scanner;

/**
 * Description of the class
 *
 * @author Jorge Jiménez Navas
 * @author Carlos Gonzalez Diaz
 * @version     1.0
 */
public class Utilidades {

    /**
     * TODO: Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado
     * @param mensaje
     * @param minimo
     * @param maximo
     * @return int numero
     */
    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo) {
        int numero;
        numero = teclado.nextInt();
        while (numero < minimo || numero > maximo) {
            System.out.println(mensaje);
        }
            return numero;
    }

    /**
     * TODO: Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado
     * @param mensaje
     * @param minimo
     * @param maximo
     * @return long numero
     */
    public static long leerNumero(Scanner teclado, String mensaje, long minimo, long maximo) {
        long numero;
        numero = teclado.nextLong();
        while (numero < minimo || numero > maximo) {
            System.out.println(mensaje);
        }
            return numero;
    }

    /**
     * TODO: Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado
     * @param mensaje
     * @param minimo
     * @param maximo
     * @return double numero
     */
    public static double leerNumero(Scanner teclado, String mensaje, double minimo, double maximo) {
        double numero;
        numero = teclado.nextDouble();
        while (numero < minimo || numero > maximo) {
            System.out.println(mensaje);
        }
            return numero;
    }

    /**
     * TODO: TODO: Solicita una letra repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado
     * @param mensaje
     * @param minimo
     * @param maximo
     * @return char letra
     */
    public static char leerLetra(Scanner teclado, String mensaje, char minimo, char maximo) {
        char letra;
        letra = teclado.next().charAt(0);
        while (letra < minimo || letra > maximo){
            System.out.println(mensaje);
        }
        return letra;
    }


    /**
     * TODO: Solicita una fecha repetidamente hasta que se introduzca una correcta
     * @param teclado
     * @param mensaje
     * @return Fecha
     */
    public static Fecha leerFecha(Scanner teclado, String mensaje) {
        System.out.println(mensaje);
        Fecha fecha = null;
        int dia;
        do {
            System.out.print("Ingrese día: ");
            dia = teclado.nextInt();
        } while (dia >= 0 && dia <= Fecha.DIAS_MES);

        int mes;
        do{
            System.out.print("Ingrese mes: ");
            mes = teclado .nextInt();
        } while (mes >= 1 && mes <= Fecha.MESES_ANIO);

        int anio;
        do {
            System.out.print("Ingrese año: ");
            anio = teclado.nextInt();
        } while (anio >= Fecha.PRIMER_ANIO && anio <= Fecha.ULTIMO_ANIO);

        fecha = new Fecha(dia, mes, anio);

        if (Fecha.comprobarFecha(dia, mes, anio) == false){
            fecha = leerFecha(teclado, "Fecha incorrecta.");
        }

        return fecha;
    }


    /**
     * TODO: Solicita una fecha y hora repetidamente hasta que se introduzcan unas correctas
     * @param teclado
     * @param mensaje
     * @return Fecha
     */
    public static Fecha leerFechaHora(Scanner teclado, String mensaje) {
        System.out.println(mensaje);
        Fecha fecha = null;
        int dia;
        do {
            System.out.print("Ingrese día: ");
            dia = teclado.nextInt();
        } while (dia >= 1 && dia <= 31);

        int mes;
        do{
            System.out.print("Ingrese mes: ");
            mes = teclado .nextInt();
        } while (mes >= 1 && mes <= 12);

        int anio;
        do {
            System.out.print("Ingrese año: ");
            anio = teclado.nextInt();
        } while (anio >= Fecha.PRIMER_ANIO && anio <= Fecha.ULTIMO_ANIO);

        int hora;
        do{
            System.out.print("Ingrese hora: ");
            hora = teclado.nextInt();
        } while (hora >= 0 && hora <= Fecha.HORAS_DIA);

        int minuto;
        do {
            System.out.print("Ingrese minuto: ");
            minuto = teclado.nextInt();
        } while (minuto >= 0 && minuto <= Fecha.MINUTOS_HORA);

        int segundo;
        do {
            System.out.print("Ingrese segundo: ");
            segundo = teclado.nextInt();
        } while (segundo >= 0 && segundo <= Fecha.SEGUNDOS_MINUTO);

        fecha = new Fecha(dia, mes, anio, hora, minuto, segundo);
        if ((Fecha.comprobarFecha(dia, mes, anio) == false) || (Fecha.comprobarHora(hora, minuto, segundo) == false)){
            fecha = leerFechaHora(teclado, "Fecha incorrecta.");
        }


        return new Fecha(dia, mes, anio, hora, minuto, segundo);
    }

    /**
     * TODO: Imprime por pantalla el String pasado por parámetro
     * @param teclado
     * @param s
     * @return
     */
    public static String leerCadena(Scanner teclado, String s) {
        System.out.print(s);
        return teclado.next();
    }
}
