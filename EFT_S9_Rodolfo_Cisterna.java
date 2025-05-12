/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package eft_s9_rodolfo_cisterna;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rodo
 */
public class EFT_S9_Rodolfo_Cisterna {

    static ArrayList<Cliente> ventas = new ArrayList<>();
    static String[] menuCompras = {"[1]. Ver Sala", "[2]. Comprar Entrada ", "[3]. Verificar Disponibilidad", "[4]. Modificar su compra", "[5]. Generar boleta de compra", "[6].Salir"};
    static Scanner teclado = new Scanner(System.in);

    // inicio del Main
    public static void main(String[] args) {

        // Paso 1. Funcionalidades del Menu Principal
        System.out.println("===============================================");
        System.out.println("        ****Bienvenido al teatro Moro****");
        System.out.println("===============================================");

        TeatroMoro.inicializarTeatro();
        int menuPrincipal;
        TeatroMoro.inicializarTeatro();

        do {
            for (String string : menuCompras) {   //mostrar el menu con un ciclo for
                System.out.println(string);
            }
            menuPrincipal = teclado.nextInt();
            switch (menuPrincipal) {
                case 1 -> {
                    TeatroMoro.mostrarMapa();
                }                
                case 2 -> {
                    comprarEntrada(teclado);
                }
                case 3 -> {
                    verDisponibilidad(teclado);
                }
                case 4 -> {
                    System.out.print("Desea modificar o eliminar alguna compra? (M/E): ");
                    char modificacion = teclado.nextLine().toUpperCase().charAt(0);
                    if (modificacion == 'M') {
                        modificarCompra(teclado);
                    } else {
                        eliminarCompra(teclado);
                    }

                }
                case 5 -> {
                    imprimirBoleta();
                }
                case 6 -> {
                    System.out.println("Gracias por visitar nuestro sitio, vuelve pronto!");
                }
                default -> {
                    System.out.println("Opción no válida, ingrese nuevamente");
                    break;
                }
            }
            System.out.println();

        } while (6 != menuPrincipal);

    } // cierre del main 

    static void comprarEntrada(Scanner teclado) {

        teclado.nextLine();
        Cliente cliente = new Cliente();

        System.out.println("Ingrese nombre");
        cliente.nombre = teclado.nextLine();
        
        do {
            System.out.println("Ingrese sexo (M/F): ");
            cliente.sexo = teclado.nextLine().toUpperCase();
        } while (!((cliente.sexo.equals("M")) || (cliente.sexo.equals("F"))));
        
        do {
            System.out.println("Estudiante? (Si/NO): ");
            cliente.tipoCliente = teclado.nextLine().toUpperCase();
        } while (!((cliente.tipoCliente.equals("SI")) || (cliente.tipoCliente.equals("NO"))));
        if (cliente.tipoCliente.equals("SI")) {
            cliente.tipoCliente = "Estudiante";
        } else {
            cliente.tipoCliente = "General";
        }        

        do {
            System.out.println("Edad: ");
            while (!teclado.hasNextInt()) {
                System.out.println("Edad invalida intente nuevamente: ");
                teclado.nextInt();
            }

            cliente.edad = teclado.nextInt();
        } while (cliente.edad <= 0);

        //creamos la logica para comprar varias veces por entrada
        boolean seguirComprando = true;
        while (seguirComprando) {
            TeatroMoro.mostrarMapa();

            char tipoSeccion;
            int fila, columna;

            boolean asientoValido = false;
            do {
                System.out.println("Ingrese el codigo de la seccion que desea? (V = VIP, P = Palco, B = Platea Baja, A = Platea Alta, G = Galeria");
                tipoSeccion = teclado.next().toUpperCase().charAt(0);

                System.out.print("Ingrese su Fila (desde 1 hasta 3 inclusive para platea alta o galeria. Revise nuestro mapa): ");
                fila = teclado.nextInt() - 1;
                System.out.print("Ingrese su Columna (desde 1 hasta 8 inclusive para galeria. Revise nuestro mapa: ");
                columna = teclado.nextInt() - 1;

                asientoValido = TeatroMoro.ocuparAsiento(tipoSeccion, fila, columna);

                if (!asientoValido) {
                    System.out.println("Asiento ocupado o inválido. Intente nuevamente.");
                }
            } while (!asientoValido);

            int precioBase = Precios.obtenerPrecio(tipoSeccion);
            double precioFinal = Precios.aplicarDescuento(precioBase, cliente.sexo, cliente.edad, cliente.tipoCliente);
            Entrada entrada = new Entrada(tipoSeccion, fila, columna, precioFinal);
            cliente.entradas.add(entrada);

            System.out.print("¿Desea comprar otro asiento? (S/N): ");
            seguirComprando = teclado.next().equalsIgnoreCase("S");

        }
        ventas.add(cliente); // Guardamos la venta       
    }

    static void imprimirBoleta() {
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas aun.");
            return;
        }
        for (Cliente cliente : ventas) {
            cliente.mostrarBoleta();
            System.out.println("---------------");
        }
    }

    static void modificarCompra(Scanner nombre) {
        teclado.nextLine(); // limpiar buffer
        System.out.print("Ingrese nombre del cliente a modificar: ");
        String nombreBuscar = teclado.nextLine();

        for (Cliente cliente : ventas) {
            if (cliente.nombre.equalsIgnoreCase(nombreBuscar)) {
                System.out.println("Entrada encontrada:");
                cliente.mostrarBoleta();
                System.out.print("Ingrese nueva edad: ");
                cliente.edad = teclado.nextInt();
                for (Entrada entrada : cliente.entradas) {
                    int precioBase = Precios.obtenerPrecio(entrada.tipoSeccion);
                    entrada.precioFinal = Precios.aplicarDescuento(precioBase, cliente.sexo, cliente.edad, cliente.tipoCliente);
                }
                System.out.println("Compra modificada.");
                return;
            }
        }
        System.out.println("Cliente no encontrado.");

    }

    static void eliminarCompra(Scanner nombre) {
        teclado.nextLine();
        System.out.print("Ingrese nombre del cliente a eliminar: ");
        String nombreEliminar = teclado.nextLine();

        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).nombre.equalsIgnoreCase(nombreEliminar)) {
                Cliente cliente = ventas.remove(i);
                for (Entrada entrada : cliente.entradas) {
                    TeatroMoro.liberarAsiento(entrada.tipoSeccion, entrada.fila, entrada.columna);
                }
                System.out.println("Compra eliminada.");
                return;
            }
        }
        System.out.println("Cliente no encontrado.");
    }
    
    static void verDisponibilidad(Scanner letra) {
        
        TeatroMoro.mostrarMapa();
        System.out.print("Ingrese el codigo de la seccion que desea verificar? (V = VIP, P = Palco, B = Platea Baja, A = Platea Alta, G = Galeria");
        char tipo = teclado.next().toUpperCase().charAt(0);

        System.out.print("Ingrese su Fila (desde 1 hasta 3 inclusive para platea alta o galeria. Revise nuestro mapa): ");
        int fila = teclado.nextInt()-1;
        System.out.print("Ingrese su Columna (desde 1 hasta 8 inclusive para galeria. Revise nuestro mapa: ");
        int columna = teclado.nextInt() -1;

        if (TeatroMoro.estaDisponible(tipo, fila, columna)) {
        System.out.println("El asiento está disponible.");
        } else {
        System.out.println("El asiento está ocupado o es inválido.");
        }
    }
}

class TeatroMoro {

    static char[][] vip = new char[1][5];
    static char[][] palco = new char[2][6];
    static char[][] plateaBaja = new char[2][6];
    static char[][] plateaAlta = new char[3][6];
    static char[][] galeria = new char[3][8];

    static int asientosDisponiblesVip;
    static int asientosDisponiblesPalco;
    static int asientosDisponiblesPlateaBaja;
    static int asientosDisponiblesPlateaAlta;
    static int asientosDisponiblesGaleria;

    public static void inicializarTeatro() {
        llenar(vip, 'V');
        llenar(palco, 'P');
        llenar(plateaBaja, 'B');
        llenar(plateaAlta, 'A');
        llenar(galeria, 'G');

        asientosDisponiblesVip = vip.length * vip[0].length;
        asientosDisponiblesPalco = palco.length * palco[0].length;
        asientosDisponiblesPlateaBaja = plateaBaja.length * plateaBaja[0].length;
        asientosDisponiblesPlateaAlta = plateaAlta.length * plateaAlta[0].length;
        asientosDisponiblesGaleria = galeria.length * galeria[0].length;
    }

    private static void llenar(char[][] seccion, char letra) {
        for (int i = 0; i < seccion.length; i++) {
            for (int j = 0; j < seccion[i].length; j++) {
                seccion[i][j] = letra;
            }
        }
    }

    private static void mostrar(char[][] seccion) {
        for (char[] fila : seccion) {
            for (char asiento : fila) {
                char estadoAsiento = asiento;
                char simboloAsiento;
                switch (estadoAsiento) {

                    case 'R' -> {
                        simboloAsiento = 'R';
                    }
                    case 'C' -> {
                        simboloAsiento = 'C';
                    }
                    default -> {
                        simboloAsiento = 'L';
                    }
                }
                System.out.print("\t" + asiento + "[" + simboloAsiento + "]" + "\t");
            }
            System.out.println();
        }
    }

    public static void mostrarMapa() {
        System.out.println("\t\t\t\t\t\t\t\t=== MAPA DE ASIENTOS ===");
        System.out.println("VIP (Disponibles: " + asientosDisponiblesVip + ")");
        mostrar(vip);
        System.out.println("PALCO (Disponibles: " + asientosDisponiblesPalco + ")");
        mostrar(palco);
        System.out.println("PLaTEA BAJA: (Disponibles: " + asientosDisponiblesPlateaBaja + ")");
        mostrar(plateaBaja);
        System.out.println("PLATEA ALTA: (Disponibles: " + asientosDisponiblesPlateaAlta + ")");
        mostrar(plateaAlta);
        System.out.println("GALERIA: (Disponibles: " + asientosDisponiblesGaleria + ")");
        mostrar(galeria);
    }

    public static boolean ocuparAsiento(char tipo, int fila, int col) {
        char[][] seccion = switch (tipo) {
            case 'V' ->
                vip;
            case 'P' ->
                palco;
            case 'B' ->
                plateaBaja;
            case 'A' ->
                plateaAlta;
            case 'G' ->
                galeria;
            default ->
                null;
        };

        if ((seccion != null && fila >= 0) && (fila < seccion.length) && (col >= 0 && col < seccion[0].length)) {
            if (seccion[fila][col] == tipo) {
                seccion[fila][col] = 'C';

                // Restar disponible
                switch (tipo) {
                    case 'V' -> asientosDisponiblesVip--;
                    case 'P' -> asientosDisponiblesPalco--;
                    case 'B' -> asientosDisponiblesPlateaBaja--;
                    case 'A' -> asientosDisponiblesPlateaAlta--;
                    case 'G' -> asientosDisponiblesGaleria--;
                }

                return true;
            }
        }
        return false;
    }

    public static void liberarAsiento(char tipo, int fila, int col) {
        char[][] seccion = switch (tipo) {
            case 'V' -> vip;
            case 'P' -> palco;
            case 'B' -> plateaBaja;
            case 'A' -> plateaAlta;
            case 'G' -> galeria;
            default ->  null;
        };

        if (seccion != null && fila >= 0 && fila < seccion.length && col >= 0 && col < seccion[0].length) {
            if (seccion[fila][col] == 'C') {
                seccion[fila][col] = tipo;

                // Sumar disponible
                switch (tipo) {
                    case 'V' -> asientosDisponiblesVip++;
                    case 'P' -> asientosDisponiblesPalco++;
                    case 'B' -> asientosDisponiblesPlateaBaja++;
                    case 'A' -> asientosDisponiblesPlateaAlta++;
                    case 'G' -> asientosDisponiblesGaleria++;
                }
            }
        }
    }
    public static boolean estaDisponible(char tipo, int fila, int col) {
        char[][] seccion = switch (tipo) {
            case 'V' -> vip;
            case 'P' -> palco;
            case 'B' -> plateaBaja;
            case 'A' -> plateaAlta;
            case 'G' -> galeria;
            default ->  null;
        };

        if (seccion != null && fila >= 0 && fila < seccion.length && col >= 0 && col < seccion[0].length) {
            return seccion[fila][col] == tipo;
        }
        return false;
    }
}

class Cliente {

    String nombre, sexo, tipoCliente;
    int edad;
    ArrayList<Entrada> entradas = new ArrayList<>();

    public void mostrarBoleta() {
        System.out.println("\t\t\t\t\t\t=== BOLETA ===");
        System.out.println("Nombre: " + nombre);
        System.out.println("Tipo: " + tipoCliente);
        System.out.println("Sexo: " + sexo + " | Edad: " + edad);
        double total = 0;
        for (Entrada entrada : entradas) {
            entrada.mostrarDetalle();
            total += entrada.precioFinal;
        }
        System.out.printf("Total a pagar: $%.2f%n", total);
        System.out.println("Felicidades su compra se ha realizado con exito.\n Disfrute la funcion!");
    }
}

class Precios {

    static int precioEntradaVip = 30000;

    public static int obtenerPrecio(char tipoSeccion) {
        return switch (tipoSeccion) {
            case 'V' ->
                precioEntradaVip;
            case 'P' ->
                (precioEntradaVip - 5000);
            case 'B' ->
                (precioEntradaVip - 10000);
            case 'A' ->
                (precioEntradaVip - 15000);
            case 'G' ->
                (precioEntradaVip - 18000);
            default ->
                0;
        };
    }

    public static double aplicarDescuento(int precio, String sexo, int edad, String tipoCliente) {
        double descuento = 0;
        if (sexo.equalsIgnoreCase("F")) {
            descuento += 0.20;
        }
        if (tipoCliente.equalsIgnoreCase("Estudiante")) {
            descuento += 0.15;
        }
        if (edad < 12) {
            descuento += 0.10;
        }               
        if (edad >= 65) {
            descuento += 0.25;
        }
        return precio * (1 - descuento);
    }
}

class Entrada {

    char tipoSeccion;
    int fila;
    int columna;
    double precioFinal;

    //generamos el constructor de la clase
    public Entrada(char tipoSeccion, int fila, int columna, double precioFinal) {
        this.tipoSeccion = tipoSeccion;
        this.fila = fila;
        this.columna = columna;
        this.precioFinal = precioFinal;
    }

    void mostrarDetalle() {
        System.out.println("Sección: " + tipoSeccion + " | Asiento: [" + (fila) + "," + (columna) + "] | Precio: $" + precioFinal);
    }
}
