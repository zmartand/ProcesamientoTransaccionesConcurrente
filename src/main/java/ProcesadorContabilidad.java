import java.io.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProcesadorContabilidad implements Runnable {
    private String archivo;
    private final Lock lock = new ReentrantLock();

    public ProcesadorContabilidad(String archivo) {
        this.archivo = archivo;
    }

    @Override
    public void run() {
        long sumaDepartamento = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                try {
                    long transaccion = Long.parseLong(linea);
                    sumaDepartamento += transaccion;
                } catch (NumberFormatException e) {
                    System.err.println("Error: Formato de transacción no válido en el archivo " + archivo);
                }
            }
        } catch (IOException e) {
            System.err.println("Error de lectura en el archivo " + archivo);
        }

        // Guardar resultado en el archivo correspondiente
        String resultadoArchivo = archivo + ".res";
        try {
            lock.lock();
            try (PrintWriter pw = new PrintWriter(new FileWriter(resultadoArchivo))) {
                pw.println(sumaDepartamento);
            } catch (IOException e) {
                System.err.println("Error de escritura en el archivo " + resultadoArchivo);
            }
        } finally {
            lock.unlock();
        }
    }
}
