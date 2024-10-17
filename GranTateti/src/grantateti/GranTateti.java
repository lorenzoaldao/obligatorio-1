
package grantateti;

import java.util.*;

public class GranTateti {
    static class Jugador {
        String alias;
        int edad;
        int partidasGanadas = 0;

        public Jugador(String nombre, String alias, int edad) {
            this.alias = alias;
            this.edad = edad;
        }

        @Override
        public String toString() {
            return alias + " | " + "#".repeat(partidasGanadas);
        }
    }

    static List<Jugador> jugadores = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        mostrarAnimacionBienvenida();

        while (true) {
            mostrarMenu();
            int opcion = sc.nextInt();
            sc.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    registrarJugador();
                    break;
                case 2:
                    mostrarRanking();
                    break;
                case 3:
                    System.out.println("Gracias por jugar. Adios!");
                    return;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private static void mostrarAnimacionBienvenida() {
        System.out.println("Bienvenidos al Gran Tateti!");
        try {
            Thread.sleep(2000); // Pausa de 2 segundos para la animacion
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1) Registrar un jugador");
        System.out.println("2) Ranking");
        System.out.println("3) Fin");
        System.out.print("Selecciona una opcion: ");
    }

    private static void registrarJugador() {
        System.out.print("Ingrese el nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese el alias (unico): ");
        String alias = sc.nextLine();
        System.out.print("Ingrese la edad: ");
        int edad = sc.nextInt();
        sc.nextLine(); // Limpiar buffer

        for (Jugador j : jugadores) {
            if (j.alias.equalsIgnoreCase(alias)) {
                System.out.println("Alias ya esta en uso, intenta con otro.");
                return;
            }
        }

        jugadores.add(new Jugador(nombre, alias, edad));
        System.out.println("Jugador registrado exitosamente.");
    }

    private static void mostrarRanking() {
        if (jugadores.isEmpty()) {
            System.out.println("No hay jugadores registrados.");
            return;
        }

        jugadores.sort(Comparator.comparingInt(j -> -j.partidasGanadas)); // Ordenar por partidas ganadas
        System.out.println("\n--- Ranking ---");
        for (Jugador j : jugadores) {
            System.out.println(j);
        }
    }
}
