import java.io.*;

public class UtilidadesFicheros {
    public static long obtenerSumaTransacciones(String[] archivos) {
        long sumaTotal = 0;
        for (String archivo : archivos) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    try {
                        long transaccion = Long.parseLong(linea);
                        sumaTotal += transaccion;
                    } catch (NumberFormatException e) {
                        System.err.println("Error: Formato de transacción no válido en el archivo " + archivo);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error de lectura en el archivo " + archivo);
            }
        }
        return sumaTotal;
    }
}

