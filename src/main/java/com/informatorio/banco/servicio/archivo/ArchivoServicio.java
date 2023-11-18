package com.informatorio.banco.servicio.archivo;
import java.io.IOException;
import java.util.List;

import com.informatorio.banco.cliente.Cliente;

public interface ArchivoServicio {
    void exportarProductosACsv(List<Cliente> clientes , String nombreArchivo) throws IOException;
}