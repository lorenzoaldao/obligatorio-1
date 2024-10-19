/*
Nombre: Lorenzo Aldao
Nro. de estudiante: 307239
Nombre: Francisco Nin
Nro. de estudiante: 322776
*/
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
                    jugarTateti(); // Mostrar tablero para dos personas
                    break;
                case 3:
                    jugarTateti(); // Mostrar tablero vs Computadora
                    break;
                case 4:
                    mostrarRanking();
                    break;
                case 5:
                    System.out.println("Gracias por jugar. Adios!");
                    return;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    // Secuencias ANSI para colores de fondo y texto
    private static final String ANSI_FONDO_VERDE = "\u001B[42m"; // Fondo verde

    private static final String ANSI_RESET = "\u001B[0m";        // Resetear color

    // Mostrar el tablero de Tateti con el fondo de los asteriscos en verde y texto blanco
    private static void jugarTateti() {
        String[] tablero = {
            "*******************",
            "* | | * | | * | | *",
            "*-+-+-*-+-+-*-+-+-*",
            "* | | * | | * | | *",
            "*-+-+-*-+-+-*-+-+-*",
            "* | | * | | * | | *",
            "*******************",
            "* | | * | | * | | *",
            "*-+-+-*-+-+-*-+-+-*",
            "* | | * | | * | | *",
            "*-+-+-*-+-+-*-+-+-*",
            "* | | * | | * | | *",
            "*******************",
            "* | | * | | * | | *",
            "*-+-+-*-+-+-*-+-+-*",
            "* | | * | | * | | *",
            "*-+-+-*-+-+-*-+-+-*",
            "* | | * | | * | | *",
            "*******************"
        };

        // Imprimir cada línea del tablero, pintando los fondos de '*' en verde con texto blanco
        for (String linea : tablero) {
            // Reemplazar '*' por '*' con fondo verde y texto blanco
            String lineaColoreada = linea.replace("*", ANSI_FONDO_VERDE + "*" + ANSI_RESET);
            System.out.println(lineaColoreada);
        }
    }

    private static void mostrarAnimacionBienvenida() {
        String mensaje = "Bienvenidos al Gran Tateti!";
        for (int i = 0; i < mensaje.length(); i++) {
            System.out.print(mensaje.charAt(i));
            try {
                Thread.sleep(100); // Pausa de 100 milisegundos entre cada letra
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(); // Salto de línea después del mensaje
        try {
            Thread.sleep(2000); // Pausa de 2 segundos antes de continuar
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1) Registrar un jugador");
        System.out.println("2) Jugar al Gran Tateti entre 2 personas.");
        System.out.println("3) Jugar al Gran Tateti vs la Computadora");
        System.out.println("4) Ranking");
        System.out.println("5) Fin");
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