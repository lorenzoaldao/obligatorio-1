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
        String nombre;
        String alias;
        int edad;
        int partidasGanadas = 0;

        public Jugador(String nombre, String alias, int edad) {
            this.nombre = nombre;
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
    static char[][] tablero = {
        {' ',' ',' '},
        {' ',' ',' '},
        {' ',' ',' '}
    };

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
                    jugarTatetiDosJugadores();
                    break;
                case 3:
                    // Lógica para jugar contra la computadora (pendiente)
                    System.out.println("La opción contra la computadora está en desarrollo.");
                    break;
                case 4:
                    mostrarRanking();
                    break;
                case 5:
                    System.out.println("Gracias por jugar. ¡Adiós!");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    // Secuencias ANSI para colores de fondo y texto
    private static final String ANSI_FONDO_VERDE = "\u001B[42m"; // Fondo verde
    private static final String ANSI_RESET = "\u001B[0m";        // Resetear color

    private static void mostrarTablero() {
        System.out.println(ANSI_FONDO_VERDE + "*******" + ANSI_RESET);
        for (int i = 0; i < 3; i++) {
            System.out.print(ANSI_FONDO_VERDE + "*" + ANSI_RESET);
            for (int j = 0; j < 3; j++) {
                System.out.print(tablero[i][j]);
                if (j < 2) System.out.print("|");
            }
            System.out.print(ANSI_FONDO_VERDE + "*" + ANSI_RESET + "\n");
            if (i < 2) System.out.println(ANSI_FONDO_VERDE + "*-+-+-*" + ANSI_RESET);
        }
        System.out.println(ANSI_FONDO_VERDE + "*******" + ANSI_RESET);
    }

    private static void jugarTatetiDosJugadores() {
        reiniciarTablero();
        Jugador jugador1 = seleccionarJugador(1);
        Jugador jugador2 = seleccionarJugador(2);

        char turnoActual = 'X'; // Jugador 1 es 'X', Jugador 2 es 'O'
        Jugador jugadorActual = jugador1;

        while (true) {
            mostrarTablero();
            System.out.println(jugadorActual.alias + " (" + turnoActual + "), tu turno.");
            System.out.print("Ingresa la fila (1-3): ");
            int fila = sc.nextInt() - 1;
            System.out.print("Ingresa la columna (1-3): ");
            int columna = sc.nextInt() - 1;

            if (fila < 0 || fila > 2 || columna < 0 || columna > 2 || tablero[fila][columna] != ' ') {
                System.out.println("Movimiento no válido. Inténtalo de nuevo.");
                continue;
            }

            tablero[fila][columna] = turnoActual;

            if (verificarGanador(turnoActual)) {
                mostrarTablero();
                System.out.println(jugadorActual.alias + " ha ganado!");
                jugadorActual.partidasGanadas++;
                break;
            }

            if (tableroLleno()) {
                mostrarTablero();
                System.out.println("Es un empate!");
                break;
            }

            // Cambiar turno
            turnoActual = (turnoActual == 'X') ? 'O' : 'X';
            jugadorActual = (jugadorActual == jugador1) ? jugador2 : jugador1;
        }
    }

    private static boolean verificarGanador(char simbolo) {
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0] == simbolo && tablero[i][1] == simbolo && tablero[i][2] == simbolo) {
                return true;
            }
            if (tablero[0][i] == simbolo && tablero[1][i] == simbolo && tablero[2][i] == simbolo) {
                return true;
            }
        }
        if (tablero[0][0] == simbolo && tablero[1][1] == simbolo && tablero[2][2] == simbolo) {
            return true;
        }
        if (tablero[0][2] == simbolo && tablero[1][1] == simbolo && tablero[2][0] == simbolo) {
            return true;
        }
        return false;
    }

    private static boolean tableroLleno() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private static void reiniciarTablero() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = ' ';
            }
        }
    }

    private static Jugador seleccionarJugador(int numeroJugador) {
        System.out.println("Selecciona el jugador " + numeroJugador + ":");
        for (int i = 0; i < jugadores.size(); i++) {
            System.out.println((i + 1) + ") " + jugadores.get(i).alias);
        }
        int seleccion = sc.nextInt();
        return jugadores.get(seleccion - 1);
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
        System.out.println("\n--- Menú ---");
        System.out.println("1) Registrar un jugador");
        System.out.println("2) Jugar al Gran Tateti entre 2 personas");
        System.out.println("3) Jugar al Gran Tateti vs la Computadora");
        System.out.println("4) Ranking");
        System.out.println("5) Fin");
        System.out.print("Selecciona una opción: ");
    }

    private static void registrarJugador() {
        System.out.println("Ingrese su nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese el alias (único): ");
        String alias = sc.nextLine();
        System.out.print("Ingrese la edad: ");
        int edad = sc.nextInt();
        sc.nextLine(); // Limpiar buffer

        for (Jugador j : jugadores) {
            if (j.alias.equalsIgnoreCase(alias)) {
                System.out.println("Alias ya está en uso, intenta con otro.");
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
