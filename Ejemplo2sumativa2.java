/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ejemplo2sumativa2;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask; 

/**
 *
 * @author Rodo
 */

public class Ejemplo2sumativa2 {

static String[] menuCompras = {"[1].Ver Sala", "[2].Reservar Entrada ", "[3]. Comprar Entrada ", "[4]. Verificar Disponibilidad", "[5]. Modificar su compra", "[6].Salir"};
static String[] filaAsientos = {"A", "B", "C", "D", "E", "F", "G", "H"};
static String[][] teatroMoro = new String[filaAsientos.length][8];
static Scanner teclado = new Scanner(System.in);
static String asientoReservado = null;
static Timer timer = new Timer();
static boolean reservaEnProceso = false;


    public static void main(String[] args) {
     // Paso 1. Funcionalidades del Menu Principal

        System.out.println("===============================================");
        System.out.println("        ****Bienvenido al teatro Moro****");
        System.out.println("===============================================");
        
        abrirTeatro();       
        int menuPrincipal;
        boolean opcion;
        String asiento;

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

                }
                case 5 -> {

                }
                case 6 -> {
                    System.out.println("Gracias por visitar nuestro sitio, vuelve pronto!");
                }
                default -> {
                    System.out.println("Opción no válida, ingrese nuevamente");
                    break;
                }
            }
            System.out.println("\n");
        } while (menuPrincipal != 6);

    } // cierre del main
        
     //funcion para crear la sala del teatro y colocar sus asientos libres
    public static void abrirTeatro() {
        for (int i = 0; i < teatroMoro.length; i++) {
            for (int j = 0; j < teatroMoro[i].length; j++) {
                teatroMoro[i][j] ="L";  // L = Asiento Libre               
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
                System.out.print("\t" + filaAsientos[i] + (j+1) + "[" + simboloAsiento + "]" +"\t");
            }
            System.out.println();
        }
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
        reservaEnProceso = true;
        reservaAsiento = "";                
        asientoReservado = reservaAsiento; 
        
        TimerTask avisoTiempo = new TimerTask() {
            @Override
            public void run(){
                if(reservaEnProceso) {
                    System.out.println("Te has decidido?, Solo te quedan 15 segundos para continuar con tu reserva");
                }
            }
        };
        TimerTask cancelarReserva = new TimerTask() {
            @Override
            public void run(){
                if(reservaEnProceso) {
                    System.out.println("se te acabo el tiempo tu reserva se ha cancelado, comienza nuevamente");
                    reservaEnProceso = false;
                    asientoReservado = null;
                    return;
                }
            }
        };
        //tareas
        timer.schedule(avisoTiempo, 15000);
        timer.schedule(cancelarReserva, 30000);
        
        //continuamos con la reserva
        reservaAsiento = teclado.nextLine().toUpperCase();
        int filaReal= -1; // para que al transformar la letra en digito calce con la posicion en la matriz
        for (int i = 0; i < filaAsientos.length; i++) {
            if (filaAsientos[i].equals(reservaAsiento)) { // validando la entrada de la letra
                filaReal= i;
            }                       
        }
        if (filaReal == -1) {
            System.out.println("Fila invalida, ingrese nuevamente su fila entre A-H");
            return;
        }
        System.out.println("Ingrese numero de asiento entre 1 y 8");
        int numElegido;
        
            numElegido = teclado.nextInt();
            while (numElegido <1 || numElegido >8) {
            intentos++;
            System.out.println("Numero de asiento invalido. Intente nuevamente");
            numElegido = teclado.nextInt();
            }
        
            int colReal = numElegido - 1;
            
        if (!(teatroMoro[filaReal][colReal].equals("L"))) {
            System.out.println("Este asiento se encuentra reservado o vendido, por favor escoja otro asiento");
            return;
        } else {
            System.out.println("Deseas reservar este asiento? (Si/No): ");
            teclado.nextLine();
            String confirmacion = teclado.nextLine().toUpperCase();
            if(confirmacion.equals("SI")) {
                teatroMoro[filaReal][colReal] = "R";
                System.out.println("Asiento reservado correctamente");
                timer.cancel();
                reservaEnProceso = false;
                System.out.println("Desea proceder a comprar este asiento? (Si/No)");
                teclado.nextLine();
                String comprarAhora = teclado.nextLine().toUpperCase();
                if(comprarAhora.equals("SI")) {
                    if ((teatroMoro[filaReal][colReal].equals("R"))) {
                        teatroMoro[filaReal][colReal] = "V"; //si el asiento elegido esta libre pasara al estado vendido
                        System.out.println("Felicidades su compra se ha realizado con exito.\n Disfrute la funcion!");
                    }
                }
            }else {
                System.out.println("Tu reserva ha sido cancelada");
                timer.cancel();
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
        String filaElegida;
        teclado.nextLine();
        filaElegida = teclado.nextLine().toUpperCase();
        
        int filaReal= -1; // para que al transformar la letra en digito calce con la posicion en la matriz
        for (int i = 0; i < filaAsientos.length; i++) {
            if (filaAsientos[i].equals(filaElegida)) { // validando la entrada de la letra
                filaReal= i;
            }                       
        }
        if (filaReal == -1) {
            System.out.println("Fila invalida, ingrese nuevamente su fila entre A-H");
            return;
        }
        
        System.out.println("Ingrese numero de asiento entre 1 y 8");
        int numElegido;
        numElegido = teclado.nextInt();
        while (numElegido <1 || numElegido >8) {
            intentos++;
            System.out.println("Numero de asiento invalido. Intente nuevamente");
            numElegido = teclado.nextInt();
        }
        
        int colReal = numElegido - 1;
        
        while (!(teatroMoro[filaReal][colReal].equals("L"))) {
            System.out.println("Este asiento se encuentra reservado o vendido, por favor escoja otro asiento");
            return;
        }
        
        teatroMoro[filaReal][colReal] = "V"; //si el asiento elegido esta libre pasara al estado vendido
        System.out.println("Felicidades su compra se ha realizado con exito.\n Disfrute la funcion!");
    }   
    
}
