package tarea3;
/**
 *
 * Cristian Alejandro Melgar Ordoñez 
 * 7690 21 8342
 */
import java.util.Scanner;
public class Tarea3 {
public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArbolB arbolB = new ArbolB(30);

        int opcion;
        do {
            System.out.println("Menú:");
            System.out.println("1. Insertar una clave");
            System.out.println("2. Eliminar una clave");
            System.out.println("3. Buscar una clave");
            System.out.println("4. Imprimir el árbol B");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese la cantidad de números que desea ingresar: ");
                    int cantidadNumeros = Integer.parseInt(scanner.nextLine());
                    System.out.println("Ingrese los números uno por uno:");
                    for (int i = 0; i < cantidadNumeros; i++) {
                        System.out.print("Número " + (i + 1) + ": ");
                        int numero = Integer.parseInt(scanner.nextLine());
                        arbolB.insert(numero);
                    }
                    System.out.println("Números insertados correctamente.");
                    break;
                case 2:
                    System.out.print("Ingrese el número que desea eliminar: ");
                    int numeroEliminar = Integer.parseInt(scanner.nextLine());
                    arbolB.delete(numeroEliminar);
                    System.out.println("Número eliminado correctamente.");
                    break;
                case 3:
                    System.out.print("Ingrese la clave que desea buscar: ");
                    int claveBuscar = Integer.parseInt(scanner.nextLine());
                    if (arbolB.search(claveBuscar)) {
                        System.out.println("La clave " + claveBuscar + " está presente en el árbol.");
                    } else {
                        System.out.println("La clave " + claveBuscar + " no está presente en el árbol.");
                    }
                    break;
                case 4:
                    System.out.println("Árbol B:");
                    arbolB.print();
                    break;
                case 5:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 5);

        scanner.close();
    }

}