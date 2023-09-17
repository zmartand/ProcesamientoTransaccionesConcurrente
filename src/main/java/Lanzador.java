import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Lanzador {
    public static void main(String[] args) {
        String[] archivos = { "informatica.txt", "gerencia.txt", "contabilidad.txt", "comercio.txt", "recursos_humanos.txt" };
        // Generar datos de transacciones de ejemplo y almacenarlos en archivos de departamento
        for (String archivo : archivos) {
            generarTransacciones(archivo);
        }
        // Crear hilos para procesar cada archivo
        Thread[] threads = new Thread[archivos.length];
        for (int i = 0; i < archivos.length; i++) {
            threads[i] = new Thread(new ProcesadorContabilidad(archivos[i]));
            threads[i].start();
        }

        // Esperar a que todos los hilos terminen
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Sumar todas las sumas de departamentos
        long sumaGlobal = UtilidadesFicheros.obtenerSumaTransacciones(archivos);

        // Guardar resultado global
        try (PrintWriter pw = new PrintWriter(new FileWriter("Resultado_global.txt"))) {
            pw.println(sumaGlobal);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Generar transacciones "falsas"" para un archivo
    private static void generarTransacciones(String archivo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (int i = 0; i < 100; i++) {
                long transaccion = (long) (Math.random() * 1000);
                pw.println(transaccion);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


