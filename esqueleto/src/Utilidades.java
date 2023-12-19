import java.util.Scanner;

/**
 * Description of the class
 * Clase que contiene distintos metodos para leer numeros, letras o fechas, obligando a que se introduzcan datos dentro de los limites deseados
 *
 * @author Jorge Jiménez Navas
 * @author Carlos Gonzalez Diaz
 * @version     1.0
 */
public class Utilidades {

    /**
     * TODO: Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado Entrada estandar
     * @param mensaje Mensaje que se le muestra al usuario por pantalla
     * @param minimo Valor minimo que puede tomar el numero
     * @param maximo Valor maximo que puede tomar el numero
     * @return int numero Valor del numero que devuelve en caso de que sea correcto
     */
    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo) {

        String numero;
        numero = teclado.nextLine();
        int num;
        if (numero.equals("CANCELAR")){
            return -1;
        } else {
            if (numero.length() > 1) {
                num = leerNumero(teclado, mensaje, minimo, maximo);
            }
            num = Integer.parseInt(numero);
            if (num < minimo || num > maximo) {
                System.out.println(mensaje);
                num = leerNumero(teclado, mensaje, minimo, maximo);
            }
            return num;
        }
    }

    /**
     * TODO: Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado Entrada estandar
     * @param mensaje Ensaje que se muestra al usuario por pantalla
     * @param minimo Valor minimo que puede tomar el numero
     * @param maximo Valor maximo que puede tomar el numero
     * @return long numero Valor del numero que devuelve en caso de que sea correcto
     */
    public static long leerNumero(Scanner teclado, String mensaje, long minimo, long maximo) {
        String numero;
        numero = teclado.nextLine();
        long num;
        if (numero.equals("CANCELAR")){
            return -1;
        } else {
            if (numero.length() > 1) {
                num = leerNumero(teclado, mensaje, minimo, maximo);
            }
            num = Long.parseLong(numero);
            if (num < minimo || num > maximo) {
                System.out.println(mensaje);
                num = leerNumero(teclado, mensaje, minimo, maximo);
            }
            return num;
        }
    }

    /**
     * TODO: Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado Entrada estandar
     * @param mensaje Mensaje que se muestra al usuario por pantalla
     * @param minimo Valor minimo que puede tomar el numero
     * @param maximo Valor maximo que puede tomar el numero
     * @return double numero Valor del numero que devuelve en caso de que sea correcto
     */
    public static double leerNumero(Scanner teclado, String mensaje, double minimo, double maximo) {
        String numero;
        numero = teclado.nextLine();
        double num;
        if (numero.equals("CANCELAR")){
            return -1;
        } else {
            if (numero.length() > 1) {
                num = leerNumero(teclado, mensaje, minimo, maximo);
            }
            num = Double.parseDouble(numero);
            if (num < minimo || num > maximo) {
                System.out.println(mensaje);
                num = leerNumero(teclado, mensaje, minimo, maximo);
            }
            return num;
        }
    }

    /**
     * TODO: TODO: Solicita una letra repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     * @param teclado Entrada estandar
     * @param mensaje Mensaje que se muestra al usuario por pantalla
     * @param minimo Valor minimo que puede tomar el numero
     * @param maximo Valor maximo que puede tomar el numero
     * @return char letra Devuelve la letra que ha leido en caso de que sea correcta
     */
    public static char leerLetra(Scanner teclado, String mensaje, char minimo, char maximo) {
        char letra;
        letra = teclado.next().charAt(0);
        if (letra < minimo || letra > maximo){
            System.out.println(mensaje);
            letra = leerLetra(teclado, mensaje, minimo, maximo);
        }
        return letra;
    }


    /**
     * TODO: Solicita una fecha repetidamente hasta que se introduzca una correcta
     * @param teclado Entrada estandar
     * @param mensaje Mensaje que se muestra por pantalla al usuario
     * @return Fecha Devuelve la fecha que ga leido en caso de que sea correcta
     */
    public static Fecha leerFecha(Scanner teclado, String mensaje) {
        System.out.println(mensaje);
        Fecha fecha = null;

        String dia1;
        System.out.print("Ingrese día: ");
        dia1 = teclado.nextLine();
        if (dia1.equals("CANCELAR")){
            leerFecha(teclado, mensaje);
        }
        int dia = Integer.parseInt(dia1);

        String mes1;
        System.out.print("Ingrese mes: ");
        mes1 = teclado.nextLine();
        if (mes1.equals("CANCELAR")){
            leerFecha(teclado, mensaje);
        }
        int mes = Integer.parseInt(mes1);

        String anio1;
        System.out.print("Ingrese año: ");
        anio1 = teclado.nextLine();
        if (anio1.equals("CANCELAR")){
            leerFecha(teclado, mensaje);
        }
        int anio = Integer.parseInt(anio1);


        fecha = new Fecha(dia, mes, anio);

        if (!Fecha.comprobarFecha(dia, mes, anio)){
            fecha = leerFecha(teclado, mensaje);
        }

        return fecha;
    }


    /**
     * TODO: Solicita una fecha y hora repetidamente hasta que se introduzcan unas correctas
     * @param teclado Entrada estandar
     * @param mensaje Mensaje que se muestra por pantalla al usuario
     * @return Fecha Devuelve la fecha que ha leido en caso de que sea correcta
     */
    public static Fecha leerFechaHora(Scanner teclado, String mensaje) {
        System.out.println(mensaje);
        Fecha fecha = null;
        int dia;
        do {
            System.out.print("Ingrese día: ");
            dia = teclado.nextInt();
        } while (dia >= 1 && dia <= 31);

        int mes = 0;
        if (dia >= 28) {
            do {
                System.out.print("Ingrese mes: ");
                mes = teclado.nextInt();
            } while (mes != 2);
            if (dia == 31) {
                do {
                    System.out.print("Ingrese mes: ");
                    mes = teclado.nextInt();
                } while (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12);
            } else {
                do {
                    System.out.print("Ingrese mes: ");
                    mes = teclado.nextInt();
                } while (mes == 4 || mes == 6 || mes == 9 || mes == 11);
            }
        }

            int anio;
            do {
                System.out.print("Ingrese año: ");
                anio = teclado.nextInt();
            } while (anio >= Fecha.PRIMER_ANIO && anio <= Fecha.ULTIMO_ANIO);

            if (!Fecha.esBisiesto(anio)) {
                if (mes == 2) {
                    if (dia == 29) {
                        do {
                            System.out.println("Fecha incorrecta");
                            System.out.print("Ingrese año bisiesto: ");
                            anio = leerNumero(teclado, "Introduzca año: ", 1900, 3000);
                        } while (Fecha.esBisiesto(anio));
                    }
                }
            }

            int hora;
            do {
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
            if ((!Fecha.comprobarFecha(dia, mes, anio)) || (!Fecha.comprobarHora(hora, minuto, segundo))) {
                fecha = leerFechaHora(teclado, "Fecha incorrecta.");
            }
            return new Fecha(dia, mes, anio, hora, minuto, segundo);
    }

    /**
     * TODO: Imprime por pantalla el String pasado por parámetro
     * @param teclado Entrada estandar
     * @param s Cadena que queremos leer
     * @return Devuelve la cadena leida
     */
    public static String leerCadena(Scanner teclado, String s) {
        System.out.print(s);
        s = teclado.nextLine();
        return s;
    }
}
