package com.informatorio.banco.servicio.archivo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.informatorio.banco.cliente.Cliente;
import com.opencsv.CSVWriter;
public class ArchivoServicioImpl implements ArchivoServicio {

    @Override
    public void exportarProductosACsv(List<Cliente> clientes, String nombreArchivo) throws IOException {
        
        try (CSVWriter writer = new CSVWriter(new FileWriter(nombreArchivo))) {

            String[] encabezado = {"nombre", "direccion", "numeroUnico"};
            writer.writeNext(encabezado);
            for (Cliente cliente : clientes) {
                String[] datos = {
                    cliente.getNombre(),
                    cliente.getDireccion(),
                    cliente.getNumeroUnico(),
                };
                writer.writeNext(datos);
            }
            System.out.println("Exportación a CSV exitosa");
            
        } catch (IOException e) {
            System.out.println("Algo salió mal, motivo: " + e.getMessage());
            throw new RuntimeException(e);
        } 
    }
}

 