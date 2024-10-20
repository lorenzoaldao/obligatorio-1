/*
Nombre: Lorenzo Aldao
Nro. de estudiante: 307239
Nombre: Francisco Nin
Nro. de estudiante: 322776
*/
package grantateti;

import java.util.*;

public class Grantateti {
    static class Jugador {
        String alias;
        int edad;
        int partidasGanadas = 0;

        public Jugador(String alias, int edad) {
            this.alias = alias;
            this.edad = edad;
        }

        @Override
        public String toString() {
            return alias + " | " + "#".repeat(partidasGanadas);
        }
    }

    static class MiniTateti {
        char[][] tablero = {{' ',' ',' '}, {' ',' ', ' '}, {' ',' ',' '}};
        char ganador = ' '; // Indica el ganador del mini-tablero (si lo hay)

        boolean mover(int fila, int columna, char simbolo) {
            if (tablero[fila][columna] == ' ') {
                tablero[fila][columna] = simbolo;
                return true;
            }
            return false;
        }

        boolean verificarGanador(char simbolo) {
            // Verifica si hay un ganador en este mini-tablero
            for (int i = 0; i < 3; i++) {
                if (tablero[i][0] == simbolo && tablero[i][1] == simbolo && tablero[i][2] == simbolo)
                    return true;
                if (tablero[0][i] == simbolo && tablero[1][i] == simbolo && tablero[2][i] == simbolo)
                    return true;
            }
            if (tablero[0][0] == simbolo && tablero[1][1] == simbolo && tablero[2][2] == simbolo)
                return true;
            if (tablero[0][2] == simbolo && tablero[1][1] == simbolo && tablero[2][0] == simbolo)
                return true;
            return false;
        }

        boolean tableroLleno() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tablero[i][j] == ' ') {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    static MiniTateti[][] tableroGrande = new MiniTateti[3][3];
    static List<Jugador> jugadores = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Inicializar los mini-tableros
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tableroGrande[i][j] = new MiniTateti();
            }
        }

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
                    jugarGrantateti();
                    break;
                case 3:
                    mostrarRanking();
                    break;
                case 4:
                    System.out.println("Gracias por jugar. ¡Adiós!");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void jugarGrantateti() {
        reiniciarTableroGrande();
        Jugador jugador1 = seleccionarJugador(1);
        Jugador jugador2 = seleccionarJugador(2);

        char turnoActual = 'X';
        Jugador jugadorActual = jugador1;

        // Variables para controlar el tablero en que se debe jugar
        int tableroX = -1, tableroY = -1;

        while (true) {
            mostrarTableroGrande();
            System.out.println(jugadorActual.alias + " (" + turnoActual + "), tu turno.");
            
            if (tableroX == -1 && tableroY == -1) {
                // Si es la primera jugada, el jugador elige el mini-tablero
                System.out.println("Puedes jugar en cualquier mini-tablero.");
                System.out.print("Selecciona el mini-tablero fila (1-3): ");
                tableroX = sc.nextInt() - 1;
                System.out.print("Selecciona el mini-tablero columna (1-3): ");
                tableroY = sc.nextInt() - 1;
            } else {
                // Después de la primera jugada, el jugador debe jugar en el mini-tablero correspondiente
                System.out.println("Debes jugar en el mini-tablero [" + (tableroX + 1) + "," + (tableroY + 1) + "]"); 
            }

            MiniTateti miniTateti = tableroGrande[tableroX][tableroY];

            System.out.print("Ingresa la fila (1-3): ");
            int fila = sc.nextInt() - 1;
            System.out.print("Ingresa la columna (1-3): ");
            int columna = sc.nextInt() - 1;

            if (!miniTateti.mover(fila, columna, turnoActual)) {
                System.out.println("Movimiento no válido. Inténtalo de nuevo.");
                continue;
            }

            if (miniTateti.verificarGanador(turnoActual)) {
                miniTateti.ganador = turnoActual;
                System.out.println(jugadorActual.alias + " ha ganado el mini-tablero!");
            }

            if (verificarGanadorGlobal(turnoActual)) {
                mostrarTableroGrande();
                System.out.println(jugadorActual.alias + " ha ganado el Grantateti!");
                jugadorActual.partidasGanadas++;
                break;
            }

            if (tableroLlenoGlobal()) {
                mostrarTableroGrande();
                System.out.println("Es un empate global!");
                break;
            }

            // Cambiar turno
            turnoActual = (turnoActual == 'X') ? 'O' : 'X';
            jugadorActual = (jugadorActual == jugador1) ? jugador2 : jugador1;

            // El próximo turno será en el mini-tablero correspondiente a la última jugada
            tableroX = fila;
            tableroY = columna;
        }
    }

    private static boolean verificarGanadorGlobal(char simbolo) {
        // Verificar si alguien ha ganado el tablero grande
        for (int i = 0; i < 3; i++) {
            if (tableroGrande[i][0].ganador == simbolo && tableroGrande[i][1].ganador == simbolo && tableroGrande[i][2].ganador == simbolo) {
                return true;
            }
            if (tableroGrande[0][i].ganador == simbolo && tableroGrande[1][i].ganador == simbolo && tableroGrande[2][i].ganador == simbolo) {
                return true;
            }
        }
        if (tableroGrande[0][0].ganador == simbolo && tableroGrande[1][1].ganador == simbolo && tableroGrande[2][2].ganador == simbolo) {
            return true;
        }
        if (tableroGrande[0][2].ganador == simbolo && tableroGrande[1][1].ganador == simbolo && tableroGrande[2][0].ganador == simbolo) {
            return true;
        }
        return false;
    }

    private static boolean tableroLlenoGlobal() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tableroGrande[i][j].ganador == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private static void reiniciarTableroGrande() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tableroGrande[i][j] = new MiniTateti();
            }
        }
    }
    
    private static final String ANSI_FONDO_VERDE = "\u001B[42m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_ROJO = "\u001B[31m"; // Color rojo
    private static final String ANSI_AZUL = "\u001B[34m"; // Color azul

    private static void mostrarTableroGrande() {
        // Línea superior de asteriscos
        System.out.println(ANSI_FONDO_VERDE + "*******************" + ANSI_RESET);

        for (int i = 0; i < 3; i++) {
            for (int fila = 0; fila < 3; fila++) {
                System.out.print(ANSI_FONDO_VERDE + "*" + ANSI_RESET);  // Borde izquierdo con fondo verde
                for (int j = 0; j < 3; j++) {
                    for (int columna = 0; columna < 3; columna++) {
                        // Imprime X en rojo y O en azul
                        if (tableroGrande[i][j].tablero[fila][columna] == 'X') {
                            System.out.print(ANSI_ROJO + "X" + ANSI_RESET);
                        } else if (tableroGrande[i][j].tablero[fila][columna] == 'O') {
                            System.out.print(ANSI_AZUL + "O" + ANSI_RESET);
                        } else {
                            System.out.print(tableroGrande[i][j].tablero[fila][columna]);
                        }

                        if (columna < 2) System.out.print("|");  // Separación de columnas
                    }
                    if (j < 2) System.out.print(ANSI_FONDO_VERDE + "*" + ANSI_RESET);  // Separación entre mini-tableros con fondo verde
                }
                System.out.print(ANSI_FONDO_VERDE + "*" + ANSI_RESET + "\n");  // Borde derecho con fondo verde
                
                if (fila < 2) {
                    // Separador entre filas de mini-tableros
                    System.out.print(ANSI_FONDO_VERDE + "*" + ANSI_RESET); // Asterisco verde
                    for (int j = 0; j < 3; j++) {
                        System.out.print("-+-+-"); // Separadores en texto normal
                        System.out.print(ANSI_FONDO_VERDE + "*" + ANSI_RESET); // Asterisco verde
                    }
                    System.out.println(); // Nueva línea
                }
            }
            if (i < 2) {
                // Separador entre las filas de mini-tableros
                System.out.println(ANSI_FONDO_VERDE + "*******************" + ANSI_RESET);
            }
        }

        // Línea inferior de asteriscos
        System.out.println(ANSI_FONDO_VERDE + "*******************" + ANSI_RESET);
    }

    private static void mostrarAnimacionBienvenida() {
        String mensaje = "¡Bienvenidos al Grantateti!";
        for (int i = 0; i < mensaje.length(); i++) {
            System.out.print(mensaje.charAt(i));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Menú ---");
        System.out.println("1. Registrar Jugador");
        System.out.println("2. Jugar al Gran Tateti entre 2 personas");
        System.out.println("3. Jugar al Gran Tateti vs la Computadora");
        System.out.println("4. Mostrar Ranking");
        System.out.println("5. Salir");
        System.out.print("Selecciona una opción: ");
    }

    private static void registrarJugador() {
        System.out.print("Ingresa el alias del jugador: ");
        String alias = sc.nextLine();
        System.out.print("Ingresa la edad del jugador: ");
        int edad = sc.nextInt();
        sc.nextLine(); // Limpiar buffer
        jugadores.add(new Jugador(alias, edad));
        System.out.println("Jugador registrado exitosamente.");
    }

    private static Jugador seleccionarJugador(int numeroJugador) {
        System.out.println("Selecciona el Jugador " + numeroJugador + ":");
        for (int i = 0; i < jugadores.size(); i++) {
            System.out.println((i + 1) + ". " + jugadores.get(i));
        }
        int opcion = sc.nextInt();
        return jugadores.get(opcion - 1);
    }

    private static void mostrarRanking() {
        System.out.println("\n--- Ranking ---");
        jugadores.sort(Comparator.comparingInt(j -> -j.partidasGanadas));
        for (Jugador j : jugadores) {
            System.out.println(j);
        }
    }
}
