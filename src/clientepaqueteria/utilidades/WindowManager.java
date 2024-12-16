package clientepaqueteria.utilidades;

import java.util.HashMap;
import java.util.Map;
import javafx.stage.Stage;

public class WindowManager {
    private static final Map<String, Stage> ventanas = new HashMap<>();

    // Agregar una ventana al administrador
    public static void registrarVentana(String nombre, Stage ventana) {
        ventanas.put(nombre, ventana);
    }

    // Obtener una ventana por su nombre
    public static Stage obtenerVentana(String nombre) {
        return ventanas.get(nombre);
    }

    // Cerrar una ventana por su nombre
    public static void cerrarVentana(String nombre) {
        Stage ventana = ventanas.get(nombre);
        if (ventana != null) {
            ventana.close();
            ventanas.remove(nombre);
        }
    }

    // Cerrar todas las ventanas
    public static void cerrarTodasLasVentanas() {
        for (Stage ventana : ventanas.values()) {
            ventana.close();
        }
        ventanas.clear();
    }
    
    

}