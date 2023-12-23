import java.util.Scanner;

/**
 * Description of the class
 * Clase que contiene distintos métodos para leer números, letras o fechas, obligando a que se introduzcan datos dentro de los límites deseados.
 *
 * @author Jorge Jiménez Navas
 * @author Carlos Gonzalez Diaz
 * @version     1.0
 */
public class Utilidades {

    /**
     * TODO: Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado Entrada estandar.
     * @param mensaje Mensaje que se le muestra al usuario por pantalla.
     * @param minimo Valor mínimo que puede tomar el numero.
     * @param maximo Valor máximo que puede tomar el numero.
     * @return int número Valor del número que devuelve en caso de que sea correcto.
     */
    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo) {
        System.out.print(mensaje);
        int numero;
        numero = teclado.nextInt();
        //Introducir -1 equivale a CANCELAR.
        if(numero != -1 && (numero < minimo || numero > maximo)){
            numero = leerNumero(teclado, mensaje, minimo, maximo);
        }
        teclado.nextLine();
        return numero;
    }

    /**
     * TODO: Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado Entrada estandar.
     * @param mensaje Ensaje que se muestra al usuario por pantalla.
     * @param minimo Valor mínimo que puede tomar el numero.
     * @param maximo Valor máximo que puede tomar el numero.
     * @return long número Valor del número que devuelve en caso de que sea correcto.
     */
    public static long leerNumero(Scanner teclado, String mensaje, long minimo, long maximo) {
        System.out.print(mensaje);
        long numero;
        numero = teclado.nextLong();
        //Introducir -1 equivale a CANCELAR.
        if(numero != -1 && (numero < minimo || numero > maximo)){
            numero = leerNumero(teclado, mensaje, minimo, maximo);
        }
        teclado.nextLine();
        return numero;
    }

    /**
     * TODO: Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado Entrada estandar.
     * @param mensaje Mensaje que se muestra al usuario por pantalla.
     * @param minimo Valor mínimo que puede tomar el numero.
     * @param maximo Valor máximo que puede tomar el numero.
     * @return double número Valor del número que devuelve en caso de que sea correcto.
     */
    public static double leerNumero(Scanner teclado, String mensaje, double minimo, double maximo) {
        System.out.print(mensaje);
        double numero;
        numero = teclado.nextDouble();
        //Introducir -1 equivale a CANCELAR.
        if(numero != -1 && (numero < minimo || numero > maximo)){
            numero = leerNumero(teclado, mensaje, minimo, maximo);
        }
        teclado.nextLine();
        return numero;
    }

    /**
     * TODO: TODO: Solicita una letra repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado Entrada estandar.
     * @param mensaje Mensaje que se muestra al usuario por pantalla.
     * @param minimo Valor mínimo que puede tomar el numero.
     * @param maximo Valor máximo que puede tomar el numero.
     * @return char letra Devuelve la letra que ha leido en caso de que sea correcta.
     */
    public static char leerLetra(Scanner teclado, String mensaje, char minimo, char maximo) {
        System.out.println(mensaje);
        char letra;
        letra = teclado.next().charAt(0);
        if (letra < minimo || letra > maximo){
            letra = leerLetra(teclado, mensaje, minimo, maximo);
        }
        return letra;
    }


    /**
     * TODO: Solicita una fecha repetidamente hasta que se introduzca una correcta
     * @param teclado Entrada estandar.
     * @param mensaje Mensaje que se muestra por pantalla al usuario.
     * @return Fecha Devuelve la fecha que ga leido en caso de que sea correcta.
     */
    public static Fecha leerFecha(Scanner teclado, String mensaje) {
        System.out.println(mensaje);
        Fecha fecha = null;
        int dia, mes = -1, anio = -1;
        do {
            dia = leerNumero(teclado, "Introduzca el día: ", 1, 31);
            if(dia != -1) {
                mes = leerNumero(teclado, "Introduzca el mes: ", 1, 12);
                if(mes != -1) {
                    anio = leerNumero(teclado, "Introduzca el año: ", Fecha.PRIMER_ANIO, Fecha.ULTIMO_ANIO);
                }
            }
        }while (!Fecha.comprobarFecha(dia, mes, anio));
        if (anio != -1 && mes != -1 && dia != -1 && !Fecha.comprobarFecha(dia, mes, anio)) {
            fecha = leerFechaHora(teclado, "Fecha incorrecta.\n" + mensaje);
        } else fecha = new Fecha(dia, mes, anio);
        return fecha;
    }


    /**
     * TODO: Solicita una fecha y hora repetidamente hasta que se introduzcan unas correctas
     * @param teclado Entrada estandar.
     * @param mensaje Mensaje que se muestra por pantalla al usuario.
     * @return Fecha Devuelve la fecha que ha leido en caso de que sea correcta.
     */
    public static Fecha leerFechaHora(Scanner teclado, String mensaje) {
        System.out.println(mensaje);
        Fecha fecha = null;
        int dia, mes = -1, anio = -1, hora = -1, minuto = -1, segundo = -1;
        do {
            dia = leerNumero(teclado, "Introduzca el día: ", 1, 31);
            if(dia != -1) {
                mes = leerNumero(teclado, "Introduzca el mes: ", 1, 12);
                if (mes != -1) {
                    anio = leerNumero(teclado, "Introduzca el año: ", Fecha.PRIMER_ANIO, Fecha.ULTIMO_ANIO);
                    if(anio != -1) {
                        hora = leerNumero(teclado, "Introduzca hora: ", 0, Fecha.HORAS_DIA);
                        if(hora != -1) {
                            minuto = leerNumero(teclado, "Introduzca minuto: ", 0, Fecha.MINUTOS_HORA);
                            if(minuto != -1) {
                                segundo = leerNumero(teclado, "Introduzca segundo: ", 0, Fecha.SEGUNDOS_MINUTO);
                            }
                        }
                    }
                }
            }
        }while (!Fecha.comprobarFecha(dia, mes, anio) || !Fecha.comprobarHora(hora, minuto, segundo));
        if (dia != -1 && mes != -1 && anio != -1 && hora != -1 && minuto != -1 && segundo != -1 && (!Fecha.comprobarFecha(dia, mes, anio) || !Fecha.comprobarHora(hora, minuto, segundo))) {
            fecha = leerFechaHora(teclado, "Fecha incorrecta.\n" + mensaje);
        } else fecha = new Fecha(dia, mes, anio, hora, minuto, segundo);
        return fecha;
    }
    /**
     * TODO: Imprime por pantalla el String pasado por parámetro
     * @param teclado Entrada estandar.
     * @param s Cadena que queremos leer.
     * @return Devuelve la cadena leida.
     */
    public static String leerCadena(Scanner teclado, String s) {
        System.out.print(s);
        return teclado.nextLine();
    }
}
