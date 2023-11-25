package com.informatorio.banco.servicio.archivo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ExportadorCSV {
    public static void exportarACSV(Map<String, String> datos, String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            for (Map.Entry<String, String> entry : datos.entrySet()) {
                writer.append(entry.getKey()).append(",");
            }
            writer.append("\n");

            for (Map.Entry<String, String> entry : datos.entrySet()) {
                writer.append(entry.getValue()).append(",");
            }
            writer.append("\n");

        } catch (IOException e) {
            System.err.println("Error al exportar a CSV: " + e.getMessage());
        }
    }
}
