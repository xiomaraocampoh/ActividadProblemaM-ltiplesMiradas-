import java.util.ArrayList;
import java.util.Scanner;

public class GestorTareasEstructurado {

    static ArrayList<String> tareas = new ArrayList<>();

    public static void agregarTarea(String descripcion) {
        tareas.add(descripcion);
    }

    public static void mostrarTareas() {
        if (tareas.isEmpty()) {
            System.out.println("No hay tareas para mostrar.");
        } else {
            System.out.println("Lista de tareas:");
            for (int i = 0; i < tareas.size(); i++) {
                System.out.println((i + 1) + ". [ ] " + tareas.get(i));
            }
        }
    }

    public static boolean eliminarTarea(int indice) {
        if (indice > 0 && indice <= tareas.size()) {
            tareas.remove(indice - 1);
            return true;
        }
        return false;
    }

    public static boolean actualizarTarea(int indice, String nuevaDescripcion) {
        if (indice > 0 && indice <= tareas.size()) {
            tareas.set(indice - 1, nuevaDescripcion);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- Gestor de Tareas (Estructurado) ---");
            System.out.println("1. Agregar tarea");
            System.out.println("2. Mostrar tareas");
            System.out.println("3. Eliminar tarea");
            System.out.println("4. Actualizar tarea");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingresa la nueva tarea: ");
                    String tarea = scanner.nextLine();
                    agregarTarea(tarea);
                    System.out.println("Tarea agregada.");
                    break;
                case 2:
                    mostrarTareas();
                    break;
                case 3:
                    mostrarTareas();
                    if (!tareas.isEmpty()) {
                        System.out.print("Número de la tarea a eliminar: ");
                        int indice = scanner.nextInt();
                        if (eliminarTarea(indice)) {
                            System.out.println("Tarea eliminada.");
                        } else {
                            System.out.println("Número inválido.");
                        }
                    }
                    break;
                case 4:
                    mostrarTareas();
                    if (!tareas.isEmpty()) {
                        System.out.print("Número de la tarea a actualizar: ");
                        int indiceActualizar = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Nueva descripción: ");
                        String nuevaDescripcion = scanner.nextLine();
                        if (actualizarTarea(indiceActualizar, nuevaDescripcion)) {
                            System.out.println("Tarea actualizada.");
                        } else {
                            System.out.println("Número inválido.");
                        }
                    }
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 5);

        scanner.close();
    }
}