/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exp3_s8_rodolfo_cisterna;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.List;




public class Exp3_S8_Rodolfo_Cisterna {
static String[] menuCompras = {"[1]. Ver Sala", "[2]. Reservar Entrada ", "[3]. Comprar Entrada ", "[4]. Verificar Disponibilidad", "[5]. Modificar su compra", "[6]. Generar boleta de compra", "[7].Salir"};
    static String[] filaAsientos = {"A", "B", "C", "D", "E", "F", "G", "H"};
    static String[][] teatroMoro = new String[filaAsientos.length][8];
    static Scanner teclado = new Scanner(System.in);
    static String asientoReservado = null;
    static Timer timerReserva = new Timer();
    static boolean reservaEnProceso = false;
    
    //Listas para promociones y reservaciones
    static List<Integer> promos;
    static List <Integer> reservaciones;
    static List <Integer> idCliente;

    //variables globales para registrar ventas
    static int totalEntradasVendidas = 0; //para contar entradas vendidas
    static double ingresoTotal = 0.0;   //total acumulado
    static int precioEntrada = 25000;    //precio entrada general
    static int totalAsientosReservados = 0;
    static int[] descuentos = {10, 15};
    static int capacidadSala = 64;
    static int[] idVentas = new int[99];
    static String[] ubicacionAsiento = new String[99];
    static String[][] detallesCliente = new String[99][4];
    
    //variables globales de eleccion
    static String filaElegida;
    static int numElegido;

    public static void main(String[] args) {
        // Paso 1. Funcionalidades del Menu Principal

        System.out.println("===============================================");
        System.out.println("        ****Bienvenido al teatro Moro****");
        System.out.println("===============================================");

        abrirTeatro();
        int menuPrincipal;

        do {
            for (String string : menuCompras) {   //mostrar el menu con un ciclo for
                System.out.println(string);
            }
            menuPrincipal = teclado.nextInt();
            switch (menuPrincipal) {
                case 1 -> {
                    imprimirSala();
                }
                case 2 -> {
                    reservarAsiento();
                }
                case 3 -> {
                    comprarEntrada();
                }
                case 4 -> {
                    verDisponibilidad();
                }
                case 5 -> {
                    modificarCompra();
                }
                case 6 -> {
                    verificarCliente();
                }
                case 7 -> {
                    System.out.println("Gracias por visitar nuestro sitio, vuelve pronto!");
                }
                default -> {
                    System.out.println("Opción no válida, ingrese nuevamente");
                    break;
                }
            }
            System.out.println();

        } while (7 != menuPrincipal);

    } // cierre del main

    //funcion para crear la sala del teatro y colocar sus asientos libres
    public static void abrirTeatro() {
        for (int i = 0; i < teatroMoro.length; i++) {
            for (int j = 0; j < teatroMoro[i].length; j++) {
                teatroMoro[i][j] = "L";  // L = Asiento Libre               
            }
        }
    }

    //funcion para mostrar la sala el teatro al usuario
    public static void imprimirSala() {
        System.out.println("\t\t\t\t\t\t\tPANTALLA\n\t===============================================================================================================\n\n");
        for (int i = 0; i < filaAsientos.length; i++) {
            for (int j = 0; j < teatroMoro[i].length; j++) {
                String estadoAsiento = teatroMoro[i][j];
                String simboloAsiento;
                switch (estadoAsiento) {
                    case "L" -> {
                        simboloAsiento = "L";
                    }
                    case "R" -> {
                        simboloAsiento = "R";
                    }
                    case "V" -> {
                        simboloAsiento = "V";
                    }
                    default -> {
                        simboloAsiento = "X";
                    }
                }
                System.out.print("\t" + filaAsientos[i] + (j + 1) + "[" + simboloAsiento + "]" + "\t");
            }
            System.out.println();
        }
        System.out.println("\t\tLa capacidad actual de la sala es: " +capacidadSala);
        System.out.println("\n\n\t===============================================================================================================\n\t\t\t\t\t\t\tSALIDA SALA\n");
    }

    //funcion para reservar asiento
    public static void reservarAsiento() {
        imprimirSala();
        System.out.println("Ingrese la fila del asiento que desea reservar (desde A hasta H), tiene 30 segundos para reservar: ");
        int intentos = 0;
        String reservaAsiento;
        teclado.nextLine();

        //Iniciaremos el temporizador 
        timerReserva = new Timer();
        reservaEnProceso = true;
        reservaAsiento = "";
        asientoReservado = reservaAsiento;

        TimerTask avisoTiempo = new TimerTask() {
            @Override
            public void run() {
                if (reservaEnProceso) {
                    System.out.println("Te has decidido?, Solo te quedan 15 segundos para continuar con tu reserva");
                }
            }
        };
        TimerTask cancelarReserva = new TimerTask() {
            @Override
            public void run() {
                if (reservaEnProceso) {
                    System.out.println("se te acabo el tiempo tu reserva se ha cancelado, comienza nuevamente");
                    reservaEnProceso = false;
                    asientoReservado = null;                    
                }
            }
        };
        //tareas
        timerReserva.schedule(avisoTiempo, 15000);
        timerReserva.schedule(cancelarReserva, 30000);

        //continuamos con la reserva
        reservaAsiento = teclado.nextLine().toUpperCase();
        int filaReal = -1; // para que al transformar la letra en digito calce con la posicion en la matriz
        for (int i = 0; i < filaAsientos.length; i++) {
            if (filaAsientos[i].equals(reservaAsiento)) { // validando la entrada de la letra
                filaReal = i;
            }
        }
        if (filaReal == -1) {
            System.out.println("Fila invalida, ingrese nuevamente su fila entre A-H");
            return;
        }
        System.out.println("Ingrese numero de asiento entre 1 y 8");   
        numElegido = teclado.nextInt();
        while (numElegido < 1 || numElegido > 8) {
            intentos++;
            System.out.println("Numero de asiento invalido. Intente nuevamente");
            numElegido = teclado.nextInt();
        }

        int colReal = numElegido - 1;

        if (!(teatroMoro[filaReal][colReal].equals("L"))) {
            System.out.println("Este asiento se encuentra reservado o vendido, por favor escoja otro asiento");
            
        } else {
            System.out.println("Deseas reservar este asiento? (Si/No): ");
            teclado.nextLine();
            String confirmacion = teclado.nextLine().toUpperCase();
            if (confirmacion.equals("SI")) {
                totalAsientosReservados++;
                teatroMoro[filaReal][colReal] = "R";
                System.out.println("Asiento reservado correctamente");
                timerReserva.cancel();
                timerReserva.purge();
                reservaEnProceso = false;
                System.out.println("Desea proceder a comprar este asiento? (Si/No)");

                String comprarAhora = teclado.nextLine().toUpperCase();
                if (comprarAhora.equals("SI")) {
                    if ((teatroMoro[filaReal][colReal].equals("R"))) {
                        System.out.println("tienes algun descuento (Si/No): ");
                        String descuento = teclado.nextLine().toUpperCase();
                        while (!((descuento.equals("Si")) || (descuento.equals("No")))) {
                            intentos++;
                            System.out.println("Ingresa una opcion correcta, intentalo nuevamente");
                            descuento = teclado.nextLine().toUpperCase();
                        }
                        if (descuento.equals("SI")) {
                            System.out.println("Que descuento tienes Estudiante/Adulto Mayor");
                            descuento = teclado.nextLine().toUpperCase();
                            while (!((descuento.equals("ESTUDIANTE")) || (descuento.equals("ADULTO MAYOR")))) {
                                intentos++;
                                System.out.println("Ingresa una opcion correcta, intentalo nuevamente");
                                descuento = teclado.nextLine().toUpperCase();
                            }
                            if (descuento.equals("ESTUDIANTE")) {
                                System.out.println("Tu entrada es estudiante y su valor es: " + precioEntrada * 0.9);
                                ingresoTotal += precioEntrada * 0.9;
                            } else {
                                System.out.println("Tu entrada es Adulto Mayor y su valor es: " + precioEntrada * 0.85);
                                ingresoTotal += precioEntrada * 0.85;
                            }
                        } else {
                            System.out.println("Tu entrada es publico general y su valor es : " + precioEntrada);
                            ingresoTotal += precioEntrada;
                        }
                        teatroMoro[filaReal][colReal] = "V"; //si el asiento elegido estaba reservado pasara al estado vendido
                        capacidadSala--;
                        totalAsientosReservados--;
                        totalEntradasVendidas++;
                        System.out.println("Felicidades su compra se ha realizado con exito.\n Disfrute la funcion!");
                    }
                }
            } else {
                System.out.println("Tu reserva ha sido cancelada");
                timerReserva.cancel();
                timerReserva.purge();
                reservaEnProceso = false;
            }

        }
    }

    //funcion para comprar una entrada directamente
    public static void comprarEntrada() {
        imprimirSala();
        System.out.println("Revise asientos disponibles (marcados con una L). A continuacion ingrese la posicion que desea");
        System.out.println("Ingrese su fila (A-H): ");
        int intentos = 0;        
        teclado.nextLine();
        filaElegida = teclado.nextLine().toUpperCase();

        int filaReal = -1; // para que al transformar la letra en digito calce con la posicion en la matriz
        for (int i = 0; i < filaAsientos.length; i++) {
            if (filaAsientos[i].equals(filaElegida)) { // validando la entrada de la letra
                filaReal = i;
            }
        }
        if (filaReal == -1) {
            System.out.println("Fila invalida, ingrese nuevamente su fila entre A-H");
            return;
        }

        System.out.println("Ingrese numero de asiento entre 1 y 8");
        numElegido = teclado.nextInt();
        while (numElegido < 1 || numElegido > 8) {
            intentos++;
            System.out.println("Numero de asiento invalido. Intente nuevamente");
            numElegido = teclado.nextInt();
        }

        int colReal = numElegido - 1;

        if (!teatroMoro[filaReal][colReal].equals("L")) {
            System.out.println("Este asiento se encuentra reservado o vendido, por favor escoja otro asiento");

        } else {      //Pasamos la verificacion
            System.out.println("tienes algun descuento (Si/No): ");
            String descuento = teclado.nextLine().toUpperCase();
            while (!((descuento.equals("Si")) || (descuento.equals("No")))) {
                intentos++;
                System.out.println("Ingresa una opcion correcta, intentalo nuevamente");
                descuento = teclado.nextLine().toUpperCase();
            }
            if (descuento.equals("SI")) {
                System.out.println("Que descuento tienes Estudiante/Adulto Mayor");
                descuento = teclado.nextLine().toUpperCase();
                while (!((descuento.equals("ESTUDIANTE")) || (descuento.equals("ADULTO MAYOR")))) {
                    intentos++;
                    System.out.println("Ingresa una opcion correcta, intentalo nuevamente");
                    descuento = teclado.nextLine().toUpperCase();
                }
                if (descuento.equals("ESTUDIANTE")) {
                    System.out.println("Tu entrada es estudiante y su valor es: " + precioEntrada * 0.9);
                    ingresoTotal += precioEntrada * 0.9;
                } else {
                    System.out.println("Tu entrada es Adulto Mayor y su valor es: " + precioEntrada * 0.85);
                    ingresoTotal += precioEntrada * 0.85;
                }
            } else {
                System.out.println("Tu entrada es publico general y su valor es : " + precioEntrada);
                ingresoTotal += precioEntrada;
            }
            teatroMoro[filaReal][colReal] = "V"; //si el asiento elegido esta libre pasara al estado vendido
            capacidadSala--;
            totalEntradasVendidas++;
            System.out.println("Felicidades su compra se ha realizado con exito.\n Disfrute la funcion!");
        }
    }

    public static void verDisponibilidad() {
        System.out.println("Que asiento quieres verificar?. A continuacion ingrese la posicion que desea");
        System.out.println("Ingrese su fila (A-H): ");
        int intentos = 0;
        teclado.nextLine();
        filaElegida = teclado.nextLine().toUpperCase();

        int filaReal = -1; // para que al transformar la letra en digito calce con la posicion en la matriz
        for (int i = 0; i < filaAsientos.length; i++) {
            if (filaAsientos[i].equals(filaElegida)) { // validando la entrada de la letra
                filaReal = i;
            }
        }
        if (filaReal == -1) {
            System.out.println("Fila invalida, ingrese nuevamente su fila entre A-H");
            return;
        }

        System.out.println("Ingrese numero de asiento entre 1 y 8");
        numElegido = teclado.nextInt();
        while (numElegido < 1 || numElegido > 8) {
            intentos++;
            System.out.println("Numero de asiento invalido. Intente nuevamente");
            numElegido = teclado.nextInt();
        }

        int colReal = numElegido - 1;

        if (teatroMoro[filaReal][colReal].equals("L")) {
            System.out.println("El asiento se encuentra libre");
        } else {
            System.out.println("Este asiento se encuentra ocupado");

        }
    }

    //Modificar una venta o reserva existente
    public static void modificarCompra() {
        imprimirSala();
        System.out.println("Indique si quiere modificar una reserva o una venta existente (reserva/venta): \n");
        teclado.nextLine();
        String modificacionFinal;
        String modificacion = teclado.nextLine().toUpperCase();
        int intentos = 0;
        while (!((modificacion.equals("RESERVA")) || (modificacion.equals("VENTA")))) {
            intentos++;
            System.out.println("Ingresa una opcion correcta, intentalo nuevamente");
            modificacion = teclado.nextLine().toUpperCase();
        }
        if (modificacion.equals("RESERVA")) {
            System.out.println("Ingresa la fila del asiento a modificar (A-H)");

            filaElegida = teclado.nextLine().toUpperCase();

            int filaReal = -1; // para que al transformar la letra en digito calce con la posicion en la matriz
            for (int i = 0; i < filaAsientos.length; i++) {
                if (filaAsientos[i].equals(filaElegida)) { // validando la entrada de la letra
                    filaReal = i;
                }
            }
            if (filaReal == -1) {
                System.out.println("Fila invalida, ingrese nuevamente su fila entre A-H");
                return;
            }

            System.out.println("Ingrese numero de asiento entre 1 y 8");
            numElegido = teclado.nextInt();
            while (numElegido < 1 || numElegido > 8) {
                intentos++;
                System.out.println("Numero de asiento invalido. Intente nuevamente");
                numElegido = teclado.nextInt();
            }
            int colReal = numElegido - 1;

            if (teatroMoro[filaReal][colReal].equals("R")) {
                System.out.println("Quieres modificar este asiento? Si/No");
                teclado.nextLine();
                modificacion = teclado.nextLine().toUpperCase();
                while (!((modificacion.equals("SI")) || (modificacion.equals("NO")))) {
                    intentos++;
                    System.out.println("Ingresa una opcion correcta, intentalo nuevamente");
                    modificacion = teclado.nextLine().toUpperCase();
                }
                if (modificacion.equals("SI")) {
                    System.out.println("Deseas comprar o liberar el asiento?(Comprar/Liberar): ");
                    modificacionFinal = teclado.nextLine().toUpperCase();
                    while (!((modificacionFinal.equals("COMPRAR")) || (modificacionFinal.equals("LIBERAR")))) {
                        intentos++;
                        teclado.nextLine();
                        System.out.println("Ingresa una opcion correcta, intentalo nuevamente");
                        modificacionFinal = teclado.nextLine().toUpperCase();
                    }
                    if (modificacionFinal.equals("COMPRAR")) {
                        System.out.println("tienes algun descuento (Si/No): ");
                        String descuento = teclado.nextLine().toUpperCase();
                        while (!((descuento.equals("Si")) || (descuento.equals("No")))) {
                            intentos++;
                            System.out.println("Ingresa una opcion correcta, intentalo nuevamente");
                            descuento = teclado.nextLine().toUpperCase();
                        }
                        if (descuento.equals("SI")) {
                            System.out.println("Que descuento tienes Estudiante/Adulto Mayor");
                            descuento = teclado.nextLine().toUpperCase();
                            while (!((descuento.equals("ESTUDIANTE")) || (descuento.equals("ADULTO MAYOR")))) {
                                intentos++;
                                System.out.println("Ingresa una opcion correcta, intentalo nuevamente");
                                descuento = teclado.nextLine().toUpperCase();
                            }
                            if (descuento.equals("ESTUDIANTE")) {
                                System.out.println("Tu entrada es estudiante y su valor es: " + precioEntrada * 0.9);
                                ingresoTotal += precioEntrada * 0.9;
                            } else {
                                System.out.println("Tu entrada es Adulto Mayor y su valor es: " + precioEntrada * 0.85);
                                ingresoTotal += precioEntrada * 0.85;
                            }
                        } else {
                            System.out.println("Tu entrada es publico general y su valor es : " + precioEntrada);
                            ingresoTotal += precioEntrada;
                        }
                        capacidadSala--;
                        totalEntradasVendidas++;
                        totalAsientosReservados--;
                        teatroMoro[filaReal][colReal] = "V"; //si el asiento elegido estaba reservado pasara al estado vendido
                        System.out.println("Perfecto, su compra se ha realizado con exito.\nDisfrute la funcion!");

                    } else {
                        teatroMoro[filaReal][colReal] = "L";
                        capacidadSala++;
                        totalAsientosReservados--;
                        System.out.println("Tu asiento se ha liberado con exito. \nVolveras al menu principal");
                    }

                } else {

                }
            }
        } else {
            System.out.println("Ingresa la fila del asiento a modificar (A-H)");

            filaElegida = teclado.nextLine().toUpperCase();

            int filaReal = -1; // para que al transformar la letra en digito calce con la posicion en la matriz
            for (int i = 0; i < filaAsientos.length; i++) {
                if (filaAsientos[i].equals(filaElegida)) { // validando la entrada de la letra
                    filaReal = i;
                }
            }
            if (filaReal == -1) {
                System.out.println("Fila invalida, ingrese nuevamente su fila entre A-H");
                return;
            }

            System.out.println("Ingrese numero de asiento entre 1 y 8");
            numElegido = teclado.nextInt();
            while (numElegido < 1 || numElegido > 8) {
                intentos++;
                System.out.println("Numero de asiento invalido. Intente nuevamente");
                numElegido = teclado.nextInt();
            }
            int colReal = numElegido - 1;

            if (teatroMoro[filaReal][colReal].equals("V")) {
                System.out.println("Quieres liberar este asiento? Si/No");
                teclado.nextLine();
                modificacion = teclado.nextLine().toUpperCase();
                while (!((modificacion.equals("SI")) || (modificacion.equals("NO")))) {
                    intentos++;
                    System.out.println("Ingresa una opcion correcta, intentalo nuevamente");
                    modificacion = teclado.nextLine().toUpperCase();
                }
                if (modificacion.equals("SI")) {
                    capacidadSala++;
                    totalEntradasVendidas--;
                    teatroMoro[filaReal][colReal] = "L";
                    System.out.println("Tu asiento se ha liberado con exito. \nVolveras al menu principal");
                } else {
                    System.out.println("Disfruta la funcion!");
                }

            }
        }
    }
  
    public static void verificarCliente() {
        System.out.println("Ingrese el numero de identificacion a consultar");
        String id;
        id = teclado.nextLine();
        
        for (int i = 0; i < detallesCliente.length; i++) {
            if (detallesCliente[i][0].contains(id)) {
                System.out.println("\n Cliente Encontrado ");
                System.out.println("ID " + detallesCliente[i][0]);
                System.out.println("Nombre " + detallesCliente[i][1]);
                System.out.println("Entradas " + detallesCliente[i][2]);
                System.out.println("Total pagado " + detallesCliente[i][3]);
                
            }else {
                System.out.println("Cliente no encontrado, desea ingresarlo?");
                
                
               
            }
            
        }
    }
}
    

