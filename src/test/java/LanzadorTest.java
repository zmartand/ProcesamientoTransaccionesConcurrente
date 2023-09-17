import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LanzadorTest {
    @Test
    public void testLanzador() {
        // Definir rutas de archivo para pruebas
        String[] archivosPrueba = {
                "src/data/informatica.txt",
                "src/data/gerencia.txt",
                "src/data/contabilidad.txt",
                "src/data/comercio.txt",
                "src/data/recursos_humanos.txt"
        };

        // Ejecutar el programa principal (Lanzador)
        Lanzador.main(new String[]{});

        // Verificar que los archivos de resultados se hayan creado y tengan valores correctos
        for (String archivo : archivosPrueba) {
            long sumaEsperada = calcularSumaEsperada(archivo);
            long sumaObtenida = obtenerSumaDesdeArchivo(archivo + ".res");
            assertEquals(sumaEsperada, sumaObtenida);
        }

        // Verificar el archivo de resultado global
        long sumaGlobalEsperada = calcularSumaGlobal(archivosPrueba);
        long sumaGlobalObtenida = obtenerSumaDesdeArchivo("src/data/Resultado_global.txt");
        assertEquals(sumaGlobalEsperada, sumaGlobalObtenida);
    }

    private long calcularSumaEsperada(String archivo) {
        long suma = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                long transaccion = Long.parseLong(linea);
                suma += transaccion;
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return suma;
    }

    private long obtenerSumaDesdeArchivo(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            return Long.parseLong(br.readLine());
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return -1; // Valor de error
        }
    }

    private long calcularSumaGlobal(String[] archivos) {
        long sumaTotal = 0;
        for (String archivo : archivos) {
            sumaTotal += calcularSumaEsperada(archivo);
        }
        return sumaTotal;
    }
}
