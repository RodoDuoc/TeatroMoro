/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Exp3_S4_Rodolfo_Cisterna;
import java.util.Scanner;
/**
 *
 * @author Rodo
 */
public class Exp3_S4_Rodolfo_Cisterna {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Paso 1: Despliegue del menu principal: usar ciclo for para crear menu principal con opciones 1. Comprar entrada 2. Salir
        Scanner teclado = new Scanner(System.in);
        System.out.println("===============================================");
        System.out.println("        ****Bienvenido al teatro Moro****");
        System.out.println("===============================================");
        System.out.println("==Por favor ingrese una opcion==");

        String[] menuMoro = {"[1]. Comprar entrada", "[2]. Salir"};
        String[] menuCompras = {"Vip ", "Platea Baja ", "Platea Alta ", "Palcos "};
        int[] descuentos = {0, 10, 15};

        for (String string : menuMoro) {   //mostrar el menu con un ciclo for
            System.out.println(string);
        }

        int intentos = 0;
        int menuPrincipal;
        menuPrincipal = teclado.nextInt();

        while ((0 >= menuPrincipal) || (2 < menuPrincipal)) {  //validar la entrada de datos
            intentos++;
            System.out.println("Opción no válida, ingrese nuevamente");
            menuPrincipal = teclado.nextInt();
        }

        // Paso 2: Compra de entradas: 
        if (menuPrincipal == 1) {

            boolean nuevaCompra = true;
            boolean seleccion = true;
            int edad, precioEntradaVip, precioEntradaPBaja, precioEntradaPAlta, precioEntradaPalco, filas, columnas;
            double precioEstudiante, precioAdultoMayor;
            precioEntradaVip = 30000;
            precioEntradaPBaja = (precioEntradaVip - 10000);
            precioEntradaPAlta = (precioEntradaVip - 12000);
            precioEntradaPalco = (precioEntradaVip - 17000);
            int tipoEntrada = 0;

            do {
                System.out.println("**Por favor ingrese el tipo de entrada a comprar**");
                System.out.println("[1]. VIP");
                System.out.println("[2]. Platea Baja");
                System.out.println("[3]. Platea Alta");
                System.out.println("[4]. Palcos");
                tipoEntrada = teclado.nextInt();
                switch (tipoEntrada) {
                    case 1 -> {
                        System.out.println("          ==Seccion VIP== ");
                        char[][] salonMoro = new char[4][4];
                        System.out.println("Asientos disponibles estan marcados con 0");
                        for (int i = 0; i < salonMoro.length; i++) {  //ciclo para recorrer filas
                            for (int j = 0; j < salonMoro[i].length; j++) { //ciclo para mostrar colummnas
                                salonMoro[i][j] = '0';               // rellenando matriz con ceros
                                System.out.print("   " + salonMoro[i][j] + "   "); //imprimiendo matriz

                            }
                            System.out.println();
                        }
                        System.out.println("       Asientos VIP");
                        System.out.println();
                        System.out.println("Desea seleccionar un asiento? (true/false)");

                        seleccion = teclado.nextBoolean();

                        if (seleccion != false) {
                            System.out.println("Elija su fila");
                            filas = teclado.nextInt();
                            System.out.println("Elija su columna");
                            columnas = teclado.nextInt();
                            for (int i = 0; i < salonMoro.length; i++) {
                                for (int j = 0; j < salonMoro[i].length; j++) {
                                    while (((0 >= filas) || (4 < filas)) || ((0 >= columnas) || (4 < columnas))) {  //validar la entrada de datos
                                        intentos++;
                                        System.out.println("Opción no válida, ingrese nuevamente");
                                        System.out.println();
                                        System.out.println("Elija su fila");
                                        filas = teclado.nextInt();
                                        System.out.println("Elija su columna");
                                        columnas = teclado.nextInt();
                                    }
                                    if ((i == filas - 1) && (j == columnas - 1)) {
                                        salonMoro[i][j] = 'X';
                                    } else {
                                        salonMoro[i][j] = '0';
                                    }
                                    System.out.print("   " + salonMoro[i][j] + "   ");
                                }
                                System.out.println();
                            }

                        } else {
                            System.out.println("Elija una nueva ubicacion");
                            break;
                        }

                        // Paso 3: Visualizacion del resumen de compra: Ubicacion del asiento, Precio base de la entrada, descuento aplicado, Precio final a pagar
                        System.out.println("Ingrese su edad");

                        edad = teclado.nextInt();

                        while (0 > edad) {  //validar la entrada de datos
                            intentos++;
                            System.out.println("Opción no válida, ingrese una edad valida");
                            edad = teclado.nextInt();
                        }

                        if (edad > 0 && edad < 18) {
                            precioEstudiante = precioEntradaVip * 0.9;
                            System.out.println("Tu ubicacion es: " + menuCompras[0]);
                            System.out.println("El precio base de tu entrada es: " + precioEntradaVip);
                            System.out.println("Tu descuento aplicado es: " + descuentos[1] + "%");
                            System.out.println("Precio final a pagar: " + precioEstudiante);

                        } else if (edad >= 18 && edad < 60) {
                            System.out.println("Tu ubicacion es: " + menuCompras[0]);
                            System.out.println("El precio base de tu entrada es: " + precioEntradaVip);
                            System.out.println("Tu descuento aplicado es: " + descuentos[0] + "%");
                            System.out.println("Precio final a pagar: " + precioEntradaVip);

                        } else {
                            precioAdultoMayor = precioEntradaVip * 0.85;
                            System.out.println("Tu ubicacion es: " + menuCompras[0]);
                            System.out.println("El precio base de tu entrada es: " + precioEntradaVip);
                            System.out.println("Tu descuento aplicado es: " + descuentos[2] + "%");
                            System.out.println("Precio final a pagar: " + precioAdultoMayor);

                        }

                        System.out.println("Gracias por su compra\n\nDesea realizar una nueva compra? (True/False)");
                        //vamos a permitir al usuario realizar multiples compras
                        teclado.nextLine();
                        nuevaCompra = teclado.nextBoolean();

                        if (nuevaCompra != false) {
                            System.out.println("Perfecto vamos alla!");
                        } else {
                            System.out.println("Gracias por visitar nuestro teatro, disfruta la funcion!");
                            tipoEntrada = 5;

                        }

                    }

                    case 2 -> {
                        System.out.println("          ==Seccion Platea Baja== ");
                        char[][] salonMoro = new char[4][4];
                        System.out.println("Asientos disponibles estan marcados con 0");
                        for (int i = 0; i < salonMoro.length; i++) {  //ciclo para recorrer filas
                            for (int j = 0; j < salonMoro[i].length; j++) { //ciclo para mostrar colummnas
                                salonMoro[i][j] = '0';               // rellenando matriz con ceros
                                System.out.print("   " + salonMoro[i][j] + "   "); //imprimiendo matriz

                            }
                            System.out.println();
                        }
                        System.out.println("     Asientos Platea Baja");
                        System.out.println();
                        System.out.println("Desea seleccionar un asiento? (true/false)");

                        seleccion = teclado.nextBoolean();

                        if (seleccion != false) {
                            System.out.println("Elija su fila");
                            filas = teclado.nextInt();
                            System.out.println("Elija su columna");
                            columnas = teclado.nextInt();
                            for (int i = 0; i < salonMoro.length; i++) {
                                for (int j = 0; j < salonMoro[i].length; j++) {
                                    while (((0 >= filas) || (4 < filas)) || ((0 >= columnas) || (4 < columnas))) {  //validar la entrada de datos
                                        intentos++;
                                        System.out.println("Opción no válida, ingrese nuevamente");
                                        System.out.println();
                                        System.out.println("Elija su fila");
                                        filas = teclado.nextInt();
                                        System.out.println("Elija su columna");
                                        columnas = teclado.nextInt();
                                    }
                                    if ((i == filas - 1) && (j == columnas - 1)) {
                                        salonMoro[i][j] = 'X';
                                    } else {
                                        salonMoro[i][j] = '0';
                                    }
                                    System.out.print("   " + salonMoro[i][j] + "   ");
                                }
                                System.out.println();
                            }

                        } else {
                            System.out.println("Elija una nueva ubicacion");
                            break;
                        }

                        System.out.println("Ingrese su edad");
                        edad = teclado.nextInt();

                        while (0 > edad) {  //validar la entrada de datos
                            intentos++;
                            System.out.println("Opción no válida, ingrese una edad valida");
                            edad = teclado.nextInt();
                        }

                        if (edad > 0 && edad < 18) {
                            precioEstudiante = precioEntradaPBaja * 0.9;
                            System.out.println("Tu ubicacion es: " + menuCompras[1]);
                            System.out.println("El precio base de tu entrada es: " + precioEntradaPBaja);
                            System.out.println("Tu descuento aplicado es: " + descuentos[1] + "%");
                            System.out.println("Precio final a pagar: " + precioEstudiante);

                        } else if (edad >= 18 && edad < 60) {
                            System.out.println("Tu ubicacion es: " + menuCompras[1]);
                            System.out.println("El precio base de tu entrada es: " + precioEntradaPBaja);
                            System.out.println("Tu descuento aplicado es: " + descuentos[0] + "%");
                            System.out.println("Precio final a pagar: " + precioEntradaPBaja);

                        } else {
                            precioAdultoMayor = precioEntradaPBaja * 0.85;
                            System.out.println("Tu ubicacion es: " + menuCompras[1]);
                            System.out.println("El precio base de tu entrada es: " + precioEntradaPBaja);
                            System.out.println("Tu descuento aplicado es: " + descuentos[2] + "%");
                            System.out.println("Precio final a pagar: " + precioAdultoMayor);
                        }

                        System.out.println("Gracias por su compra\n\nDesea realizar una nueva compra? (True/False)");
                        //vamos a permitir al usuario realizar multiples compras
                        teclado.nextLine();
                        nuevaCompra = teclado.nextBoolean();

                        if (nuevaCompra != false) {
                            System.out.println("Perfecto vamos alla!");
                        } else {
                            System.out.println("Gracias por visitar nuestro teatro, disfruta la funcion!");
                            tipoEntrada = 5;
                        }
                    }
                    case 3 -> {
                        System.out.println("          ==Seccion Platea Alta== ");
                        char[][] salonMoro = new char[4][4];
                        System.out.println("Asientos disponibles estan marcados con 0");
                        for (int i = 0; i < salonMoro.length; i++) {  //ciclo para recorrer filas
                            for (int j = 0; j < salonMoro[i].length; j++) { //ciclo para mostrar colummnas
                                salonMoro[i][j] = '0';               // rellenando matriz con ceros
                                System.out.print("   " + salonMoro[i][j] + "   "); //imprimiendo matriz

                            }
                            System.out.println();
                        }
                        System.out.println("     Asientos Platea Alta");
                        System.out.println();
                        System.out.println("Desea seleccionar un asiento? (true/false)");

                        seleccion = teclado.nextBoolean();

                        if (seleccion != false) {
                            System.out.println("Elija su fila");
                            filas = teclado.nextInt();
                            System.out.println("Elija su columna");
                            columnas = teclado.nextInt();
                            for (int i = 0; i < salonMoro.length; i++) {
                                for (int j = 0; j < salonMoro[i].length; j++) {
                                    while (((0 >= filas) || (4 < filas)) || ((0 >= columnas) || (4 < columnas))) {  //validar la entrada de datos
                                        intentos++;
                                        System.out.println("Opción no válida, ingrese nuevamente");
                                        System.out.println();
                                        System.out.println("Elija su fila");
                                        filas = teclado.nextInt();
                                        System.out.println("Elija su columna");
                                        columnas = teclado.nextInt();
                                    }
                                    if ((i == filas - 1) && (j == columnas - 1)) {
                                        salonMoro[i][j] = 'X';
                                    } else {
                                        salonMoro[i][j] = '0';
                                    }
                                    System.out.print("   " + salonMoro[i][j] + "   ");
                                }
                                System.out.println();
                            }

                        } else {
                            System.out.println("Elija una nueva ubicacion");
                            break;
                        }

                        System.out.println("Ingrese su edad");
                        edad = teclado.nextInt();

                        while (0 > edad) {  //validar la entrada de datos
                            intentos++;
                            System.out.println("Opción no válida, ingrese una edad valida");
                            edad = teclado.nextInt();
                        }

                        if (edad > 0 && edad < 18) {
                            precioEstudiante = precioEntradaPAlta * 0.9;
                            System.out.println("Tu ubicacion es: " + menuCompras[2]);
                            System.out.println("El precio base de tu entrada es: " + precioEntradaPAlta);
                            System.out.println("Tu descuento aplicado es: " + descuentos[1] + "%");
                            System.out.println("Precio final a pagar: " + precioEstudiante);

                        } else if (edad >= 18 && edad < 60) {
                            System.out.println("Tu ubicacion es: " + menuCompras[2]);
                            System.out.println("El precio base de tu entrada es: " + precioEntradaPAlta);
                            System.out.println("Tu descuento aplicado es: " + descuentos[0] + "%");
                            System.out.println("Precio final a pagar: " + precioEntradaPAlta);

                        } else {
                            precioAdultoMayor = precioEntradaPAlta * 0.85;
                            System.out.println("Tu ubicacion es: " + menuCompras[2]);
                            System.out.println("El precio base de tu entrada es: " + precioEntradaPAlta);
                            System.out.println("Tu descuento aplicado es: " + descuentos[2] + "%");
                            System.out.println("Precio final a pagar: " + precioAdultoMayor);
                        }

                        System.out.println("Gracias por su compra\n\nDesea realizar una nueva compra? (True/False)");
                        //vamos a permitir al usuario realizar multiples compras
                        teclado.nextLine();
                        nuevaCompra = teclado.nextBoolean();

                        if (nuevaCompra != false) {
                            System.out.println("Perfecto vamos alla!");
                        } else {
                            System.out.println("Gracias por visitar nuestro teatro, disfruta la funcion!");
                            tipoEntrada = 5;;
                        }
                    }

                    case 4 -> {
                        System.out.println("          ==Seccion Palcos== ");
                        char[][] salonMoro = new char[4][4];
                        System.out.println("Asientos disponibles estan marcados con 0");
                        for (int i = 0; i < salonMoro.length; i++) {  //ciclo para recorrer filas
                            for (int j = 0; j < salonMoro[i].length; j++) { //ciclo para mostrar colummnas
                                salonMoro[i][j] = '0';               // rellenando matriz con ceros
                                System.out.print("   " + salonMoro[i][j] + "   "); //imprimiendo matriz

                            }
                            System.out.println();
                        }
                        System.out.println("      Asientos Palcos");
                        System.out.println();
                        System.out.println("Desea seleccionar un asiento? (true/false)");

                        seleccion = teclado.nextBoolean();

                        if (seleccion != false) {
                            System.out.println("Elija su fila");
                            filas = teclado.nextInt();
                            System.out.println("Elija su columna");
                            columnas = teclado.nextInt();
                            for (int i = 0; i < salonMoro.length; i++) {
                                for (int j = 0; j < salonMoro[i].length; j++) {
                                    while (((0 >= filas) || (4 < filas)) || ((0 >= columnas) || (4 < columnas))) {  //validar la entrada de datos
                                        intentos++;
                                        System.out.println("Opción no válida, ingrese nuevamente");
                                        System.out.println();
                                        System.out.println("Elija su fila");
                                        filas = teclado.nextInt();
                                        System.out.println("Elija su columna");
                                        columnas = teclado.nextInt();
                                    }
                                    if ((i == filas - 1) && (j == columnas - 1)) {
                                        salonMoro[i][j] = 'X';
                                    } else {
                                        salonMoro[i][j] = '0';
                                    }
                                    System.out.print("   " + salonMoro[i][j] + "   ");
                                }
                                System.out.println();
                            }

                        } else {
                            System.out.println("Elija una nueva ubicacion");
                            break;
                        }

                        System.out.println("Ingrese su edad");
                        edad = teclado.nextInt();

                        while (0 > edad) {  //validar la entrada de datos
                            intentos++;
                            System.out.println("Opción no válida, ingrese una edad valida");
                            edad = teclado.nextInt();
                        }

                        if (edad > 0 && edad < 18) {
                            precioEstudiante = precioEntradaPalco * 0.9;
                            System.out.println("Tu ubicacion es: " + menuCompras[3]);
                            System.out.println("El precio base de tu entrada es: " + precioEntradaPalco);
                            System.out.println("Tu descuento aplicado es: " + descuentos[1] + "%");
                            System.out.println("Precio final a pagar: " + precioEstudiante);

                        } else if (edad >= 18 && edad < 60) {
                            System.out.println("Tu ubicacion es: " + menuCompras[3]);
                            System.out.println("El precio base de tu entrada es: " + precioEntradaPalco);
                            System.out.println("Tu descuento aplicado es: " + descuentos[0] + "%");
                            System.out.println("Precio final a pagar: " + precioEntradaPalco);

                        } else {
                            precioAdultoMayor = precioEntradaPalco * 0.85;
                            System.out.println("Tu ubicacion es: " + menuCompras[3]);
                            System.out.println("El precio base de tu entrada es: " + precioEntradaPalco);
                            System.out.println("Tu descuento aplicado es: " + descuentos[2] + "%");
                            System.out.println("Precio final a pagar: " + precioAdultoMayor);

                        }

                        System.out.println("Gracias por su compra\n\nDesea realizar una nueva compra? (True/False)");
                        //vamos a permitir al usuario realizar multiples compras
                        teclado.nextLine();
                        nuevaCompra = teclado.nextBoolean();

                        if (nuevaCompra != false) {
                            System.out.println("Perfecto vamos alla!");
                        } else {
                            System.out.println("Gracias por visitar nuestro teatro, disfruta la funcion!");
                            tipoEntrada = 5;
                        }
                    }

                    default -> {
                        System.out.println("Opción no válida, ingrese nuevamente");
                    }

                }
            } while (tipoEntrada != 5);

        } else {
            System.out.println("Gracias por visitar nuestro sitio, vuelve pronto!");
        }
                       
    }
    
    
}
